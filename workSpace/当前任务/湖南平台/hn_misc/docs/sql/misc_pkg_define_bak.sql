CREATE OR REPLACE package body HNXXT.misc_pkg as
    type USER_INFO_REC is record(
        abb area.abb%type := '',
        family_id cs_tranpackage_customer.family_id%type := ''
    );         
                    
    type USER_INFO_ARRAY is table of USER_INFO_REC 
        index by binary_integer;
		
	/*
		记录业务开通/取消日志.
		由于日志记录与业务数据属于同步一个原子操作，
		因此该操作不提供事务控制机制
	*/
	procedure log_order_operation(
		p_abb in varchar2,
		p_family_id in varchar2,
		p_phone_use in varchar2,
		p_sp_service_id in varchar2,
		p_action in number,
		p_access_mode in number
	)	as
			v_stmts varchar2(500 char) := '';
			v_action number(1) := '';--操作方式
			
			v_family_tab varchar2(30 char) := '_xj_family';
			v_log_tab varchar2(30 char) := '_transaction_log';
		begin
			v_family_tab := trim(p_abb)||v_family_tab;
			v_log_tab := trim(p_abb)||v_log_tab;
		
			case p_action
				when 1 then --开通
					v_action := 1;
				when 2 then --取消
					v_action := 0;
			end case;
			
			v_stmts := 'insert into '||v_log_tab||' (family_id,stu_sequence,transaction, '
				||' open,open_date,operator,phone,book_type) '
				||' select id,stu_sequence,:1,:2,sysdate,''MISC'',:3,:4 from '
				||v_family_tab||' where id = :5 ';	
				
			execute immediate v_stmts
				using p_sp_service_id,v_action,p_phone_use,p_access_mode,p_family_id;			
		end;
		
	/*
		处理MISC正向同步过来的数据
	*/
	procedure handle_misc_order(
		p_msg_type in varchar2,
		p_transaction_id in varchar2,
		p_phone_fee in varchar2,
		p_phone_use in varchar2,
		p_action in number,
		p_action_reason in number,
		p_access_mode in number,
		p_feature_str in varchar2,
		p_sp_service_id in varchar2,
		p_ret_code out nocopy varchar2
	) as
		v_areaAbb varchar2(10 char) := '';
		v_customer_id number(11) := 0;
		v_stmt varchar2(1000 char) := '';
        
        v_user_arr USER_INFO_ARRAY ;
		v_order_id misc_order_relation.id%type := 0;

		v_deal_succ boolean := false;
		v_deal_tmp boolean := false;
		v_tmp integer := '';
		/*
			判断业务是否存在
			*/
		function is_exists(p_sp_service_id in varchar2)
			return boolean
			as
				v_saleModalId varchar2(50 char) := '';
			begin
				select salemodalid into v_saleModalId
					from tranpackage_define
					where salemodalid = p_sp_service_id;
					
				if length(v_saleModalId) > 0 then
					return true;
				else
					return false;
				end if;
			exception
				when NO_DATA_FOUND then
					return false; 
			end is_exists;
					
		/*
			处理用户订购请求
		*/
		function deal_user_order_relation(
				p_abb in varchar2,
				p_family_id in varchar2,
				p_phone_use in varchar2,
				p_sp_service_id in varchar2,
				p_action in number,
				p_access_mode in number) 
				return boolean
			as
				v_customer_tab varchar2(30 char) := '_tranpackage_customer';				
				v_family_tab varchar2(30 char) := '_xj_family';
				v_log_tab varchar2(30 char) := '_transaction_log';
				
				v_customer_id number(11) := 0;
				
				v_stmts varchar2(1000 char) := '';                
			begin
				v_customer_tab := trim(p_abb)||v_customer_tab;
				v_family_tab := trim(p_abb)||v_family_tab;
				v_log_tab := trim(p_abb) || v_log_tab;
				
				v_stmts := 'select id from '||v_customer_tab||' where phone = :1 '
					||' and family_id = :2 and xxt_salemodalid = :3 ';
				
				--判断关系是否存在
				begin
					execute immediate v_stmts
						into v_customer_id
						using p_phone_use,p_family_id,p_sp_service_id;
				exception
					when no_data_found then
						v_customer_id := 0;
				end;
				
				--处理关系
				begin
					--写入日志
					log_order_operation(p_abb,p_family_id ,p_phone_use ,p_sp_service_id ,p_action ,p_access_mode );
					
					case p_action
						--当进行订购操作时
						when 1 then
							if v_customer_id is not null and v_customer_id > 0 then
								--用户定购关系已经存在，直接进行更新操作
								v_stmts := 'update '||v_customer_tab||' set xxt_salemodalid = :1 ,'
									||' boss_salemodalid = :2,adcdeal_date = sysdate,adcdeal_suc=1, adcdeal_result= ''成功'' ,del = 0 where id = :3  ';
									
								execute immediate v_stmts
									using p_sp_service_id,p_sp_service_id,v_customer_id;
							else
								--用户定购关系不存在，写入到表中
								v_stmts := 'insert into '||v_customer_tab||' (family_id,phone,boss_salemodalid, '
									||' xxt_salemodalid,start_date,adcdeal_date,adcdeal_result,adcdeal_suc) '
									||' values(:1,:2,:3,:4,sysdate,sysdate,''成功'',1) ';
								
								execute immediate v_stmts
									using p_family_id,p_phone_use,p_sp_service_id,p_sp_service_id;
							end if;					
						--当进行取消操作时
						when 2 then
							if v_customer_id is not null and v_customer_id > 0 then
								v_stmts := 'delete from '||v_customer_tab||' where id = :1 ';
								
								execute immediate v_stmts
									using v_customer_id;
							else --如果记录不存在，则返回成功
								return true;
							end if;
						
						--其它类型的操作直接返回成功
						else
							return true;
					end case;
				exception
            		when CASE_NOT_FOUND then
                    	return true;
					when others then
						qtone.log_error('misc_pkg.handle_misc_order.deal_user_order_relation',
							'用户订购关系在更新/写入时发生异常',sqlerrm||' '||sqlcode||' '||v_stmts);
						return false;
				end;		
				
				return true;
			exception
				when others then
					return false;
			end;

		/*
			如果用户当前不存在于校讯通平台，但是他上行短信订购业务;
			那么他的业务订购流程就由该函数处理了。
			
			处理过程中仅涉及到misc_order_relation表的增删改操作。
		*/
		procedure deal_user_order_relation(
			p_msg_type in varchar2,
			p_transaction_id in varchar2,
			p_phone_fee in varchar2,
			p_phone_use in varchar2,
			p_action in number,
			p_action_reason in number,
			p_access_mode in number,
			p_feature_str in varchar2,			
			p_sp_service_id in varchar2,
            p_ret_code out nocopy varchar2) 
			as		
				v_exists boolean := false; --用户是否已经订购该业务
				v_id misc_order_relation.id%type := 0;
			begin
				--判断是否是重复订购
				begin
					select id into v_id
						from misc_order_relation where phone_use = p_phone_use
						and salemodalid = p_sp_service_id 
                        and phone_fee = p_phone_fee
                        and is_handled = 0;
				exception
					when no_data_found then
						v_id := 0;
				end;
				
				--处理订购
				case p_action 
					when 1 then --订购业务
						if v_id <> 0 then
							update misc_order_relation 
								set phone_use = p_phone_use,salemodalid = p_sp_service_id ,
                                action = p_action,create_date = sysdate
								where id = v_id;
						else
							insert into misc_order_relation(msg_type,transaction_id,phone_fee,
								phone_use,action,action_reason,access_mode,feature_str,salemodalid) values(p_msg_type,
								p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,p_access_mode,p_feature_str,
								p_sp_service_id) ; 		
						end if;
					when 2 then --取消业务
						if v_id <> 0 then
							delete from misc_order_relation where id = v_id;
						else
							return;
						end if;
					else
						return ;
				end case;
			exception
				when others then
					qtone.log_error('misc_pkg.handle_misc_order.deal_user_order_relation',
						'处理用户不存在的上行订购或取消',sqlcode||' '||sqlerrm);
					
					return ;
			end;
			
		/*
			判断是否是针对反向同步产生的消息
		*/
		function is_revert_msg(
			p_transaction_id in varchar2
		) return boolean as
			v_tran_id varchar2(50 char) := '';
		begin
			select transaction_id into v_tran_id
				from misc_order_relation 
				where transaction_id = p_transaction_id and sync_seq is not null;
			
			return true;
		exception
			when TOO_MANY_ROWS then
				qtone.log_error('misc_pkg.handle_misc_order.is_revert_msg',
					'misc_order_relation中有多反向同步产生的条记录',
					sqlerrm||' '||sqlcode);
				return false;
			when NO_DATA_FOUND then
				return false;
		end is_revert_msg;
		
	begin
		--将记录写入到misc_order_relation表中		
		write_to_misc_log_tab(p_msg_type,
			p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,
			p_access_mode,p_feature_str,p_sp_service_id,v_order_id);
			
/* 		insert into misc_order_relation(msg_type,transaction_id,phone_fee,
			phone_use,action,action_reason,salemodalid) values(p_msg_type,
			p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,
			p_sp_service_id) returning id into v_order_id;     */
        
		--判断用户要订购的业务是否存在
		if is_exists(p_sp_service_id) = false then
			p_ret_code := '4004';
			return;
		end if;
		
		--查看该号码对应的用户记录
		begin
            select abb,family_id bulk collect into v_user_arr
            	from family_phone
                where phone = p_phone_use; 
                
            if v_user_arr is null or v_user_arr.count = 0 then                
				deal_user_order_relation(p_msg_type,
					p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,
					p_access_mode,p_feature_str,p_sp_service_id,p_ret_code);
			
				--做一下判断，不能将已有的返回代码覆盖
				if p_ret_code is not null and length(p_ret_code) = 0 then
					p_ret_code := '';			
				end if;
				
				return;            
            end if;       
        exception
        	--该正向同步的号码在平台上面不存在
            when others then			
				qtone.log_error('misc_pkg.handle_misc_order',
                	'在获取用户时出现异常',sqlcode||' '||sqlerrm);
			
				--做一下判断，不能将已有的返回代码覆盖
				if p_ret_code is not null and length(p_ret_code) = 0 then
					p_ret_code := '9015';			
				end if;
				
				return;
        end;
        
		--判断用户是否已经订购了该业务
		/*if has_ordered(p_phone_use,p_sp_service_id) = true then
			p_ret_code := '4007';
			return;    	
		end if;*/
		
		--开始订购业务的流程		
		begin		
			savepoint sp_1;
			
        	--如果是反向同步	
			if is_revert_msg(p_transaction_id) = true then
				--更新数据状态
				update misc_order_relation 
					set is_handled = 1,handle_date = sysdate
					where transaction_id = p_transaction_id 
						and sync_seq is not null
					returning area_abb,customer_id 
						into v_areaAbb,v_customer_id;
					
	--            select area_abb,customer_order_id into v_areaAbb,v_customer_id
	--                from misc_order_relation where transaction_id = p_transaction_id;
					
				v_stmt := 'update '||v_areaAbb||'_tranpackage_customer'
					||' set boss_salemodalid = :1,adcdeal_date = sysdate,'
					||' adcdeal_result = :2,adcdeal_suc = :3 where id = :4 ';
					
				execute immediate v_stmt
					using p_sp_service_id,'同步成功',1,v_customer_id;
					
				--qtone.log_error('','','更新数据状态'||v_stmt||' '||p_sp_service_id||' '||v_customer_id);
			else --正向同步记录
				/*
					对于多条记录循环处理的情况，即使只有一个处理成功，
					那么也算整体成功。
				*/
				if v_user_arr is not null and v_user_arr.count > 0 then
					if v_user_arr.count = 1 then
                		v_deal_tmp := deal_user_order_relation(v_user_arr(1).abb,v_user_arr(1).family_id,p_phone_use,p_sp_service_id,p_action,p_access_mode);
    						
                        if v_deal_tmp = true then
                            v_deal_succ := true;
                        end if;            			
                    else --找到多条记录,直接写入到misc_order_relation
						if p_action = 2 then --对删除情况特别处理
                        	for i in v_user_arr.first..v_user_arr.last loop
                            	v_deal_tmp := deal_user_order_relation(v_user_arr(i).abb,v_user_arr(i).family_id,p_phone_use,p_sp_service_id,p_action,p_access_mode);
                            end loop;
                            
                            if v_deal_tmp = true then
                                v_deal_succ := true;
                            end if;                            
                        end if;

                        deal_user_order_relation(p_msg_type,
                            p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,
                            p_access_mode,p_feature_str,p_sp_service_id,p_ret_code);                        

                        return;
                    end if;
                else--没有找到用户记录内容
                    p_ret_code := '';
                    return;
                end if;
			end if;
				
			--如果正向同步记录处理成功，则将其标识设置为已经处理
			if v_deal_succ = true then
				update misc_order_relation set is_handled = 1 where id = v_order_id;
			end if;
			
			commit;    
		exception
			when others then
				rollback to sp_1;      
				qtone.log_error('misc_pkg.handle_misc_order','Misc数据同步,修改数据状态失败',
					sqlcode||' '||sqlerrm);
				p_ret_code := '4008';     
		end;
		
		if p_ret_code is not null and length(p_ret_code) = 0 then
			p_ret_code := '';			
		end if;
	exception
		when others then			    
			p_ret_code := '4008';
			qtone.log_error('misc_pkg.handle_misc_order','Misc数据同步业务订购失败！',
				sqlcode||' '||sqlerrm);
	end handle_misc_order;
    
    /*
    	将数据写入到misc_order_relation表中。该功能使用了
        PRAGMA AUTONOMOUS_TRANSACTION机制，由于不管用户的订购业务逻辑
        处理成功还是失败，都需要将日志写入到这个表中，所以将其使用独立
        事务机制。
    */
    procedure write_to_misc_log_tab(
		p_msg_type in varchar2,
		p_transaction_id in varchar2,
		p_phone_fee in varchar2,
		p_phone_use in varchar2,
		p_action in number,
		p_action_reason in number,
		p_access_mode in number,
		p_feature_str in varchar2,			
		p_sp_service_id in varchar2,
		p_order_id out nocopy number
    ) 	as
			PRAGMA AUTONOMOUS_TRANSACTION;
		begin
			savepoint sp_write_to_log_tab;
			
			insert into misc_order_log(msg_type,transaction_id,phone_fee,
				phone_use,action,action_reason,access_mode,feature_str,salemodalid) values(p_msg_type,
				p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,p_access_mode,p_feature_str,
				p_sp_service_id) returning id into p_order_id; 		
				
			commit;
		exception
			when others then
				rollback to sp_write_to_log_tab;
				qtone.log_error('misc_pkg.sp_write_to_log_tab',
					'MISC正向同步数据无法正常写到关系日志表(misc_order_log)',
					sqlcode||' '||sqlerrm);
		end;
    
    /*
    	反向同步时，初始化所有数据，并将状态不正确的数据进行修复！
		
		p_ret	返回值，0为操作成功，非0表示操作失败
    */
    procedure init_revert_order(p_ret out number)
	as
		cursor v_c_area 
			is select abb from area where abb <> 'ts';
		
		v_row area%rowtype;  
		v_stmt varchar2(1000 char) := '';
	begin
		savepoint sp_1;
		for v_row in v_c_area loop
			v_stmt := 'update '||v_row.abb||'_tranpackage_customer customer '
				||' set adcdeal_suc = 2,adcdeal_result = ''未同步'' '
				||' where adcdeal_suc = 3 ';
            
	--            ||' and exists(select 1 from  '
	--            ||' misc_order_relation rel where rel.customer_id = customer.id '
	--            ||' and rel.handled = 0) ';
			
			execute immediate v_stmt;
		end loop;
		
		--删除反向同步过程中处理失败的数据
		delete from misc_order_relation
			where sync_seq is not null and is_handled = 0;
		
		commit;
		p_ret := 0;
	exception
		when others then
			rollback to sp_1;
			qtone.log_error('misc_pkg.init_revert_order','MISC反向同步数据清理失败',
				sqlcode||' '||sqlerrm);
			p_ret := 1;    	    
	end init_revert_order;
    
    /*
    	处理同步失败的数据，将其状态设置为同步失败
    */
    procedure change_status_to_fail(p_tran_id in varchar2)
    as
    	v_abb varchar2(10 char) ;
        v_customer_id misc_order_relation.customer_id%type;
        
        v_stmt varchar2(1000 char) := '';
    begin
    	savepoint sp_1;
        update misc_order_relation
        	set is_handled = 1,handle_date = sysdate
            where transaction_id = p_tran_id
            returning area_abb,customer_id into v_abb,v_customer_id;
        
        --如果找到相应序列号的数据
        if sql%found then
        	v_stmt := 'update '||v_abb||'_tranpackage_customer '
            	||' set adcdeal_date = sysdate,adcdeal_result=''同步失败'', '
                ||' adcdeal_suc = 4  where id = :1 ';
            
            execute immediate v_stmt
            	using v_customer_id;
        end if;
        
    exception
    	when others then
        	rollback to sp_1;
            qtone.log_error('misc_pkg.change_status_to_fail',
            	'数据状态更新至"同步失败"时失败！',
                sqlcode||' '||sqlerrm);
            raise_application_error(-20001,'数据更新失败');
    end;
end misc_pkg;
/