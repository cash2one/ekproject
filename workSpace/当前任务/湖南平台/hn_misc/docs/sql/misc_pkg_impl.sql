CREATE OR REPLACE package body HNXXT.misc_pkg as
    /*
    	�û�������Ϣ--�Ծ���family_phone���е����� 
    */
    type USER_INFO_REC is record(
        abb area.abb%type := '',
        family_id cs_tranpackage_customer.family_id%type := ''
    );         
                    
    type USER_INFO_ARRAY is table of USER_INFO_REC 
        index by binary_integer;
	
	/*
		�û�������ҵ����Ϣ��������Ϣ������ʷ�
	*/
	type USER_TRAN_INFO_REC IS RECORD(
    	customer_id cs_tranpackage_customer.id%type := 0,
    	phone cs_tranpackage_customer.phone%type := '',
    	family_id cs_tranpackage_customer.family_id%type := 0,        
        boss_salemodalid tranpackage_define.salemodalid%type ,
        xxt_salemodalid tranpackage_define.salemodalid%type ,
        fee tranpackage_define.fee%type := 0
    );
    
    type USER_TRAN_INFO_REC_ARRAY is table of USER_TRAN_INFO_REC
    	index by binary_integer;

	/*
		��¼ҵ��ͨ/ȡ����־.
		������־��¼��ҵ����������ͬ��һ��ԭ�Ӳ�����
		��˸ò������ṩ������ƻ���
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
			v_action number(1) := '';--������ʽ
			
			v_family_tab varchar2(30 char) := '_xj_family';
			v_log_tab varchar2(30 char) := '_transaction_log';
		begin
			v_family_tab := trim(p_abb)||v_family_tab;
			v_log_tab := trim(p_abb)||v_log_tab;
		
			case p_action
				when 1 then --��ͨ
					v_action := 1;
				when 2 then --ȡ��
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
			�����û���������:
            1.���(phone,family_id,abb)û�ж���ҵ����Ϊ(phone,family_id,abb)����ҵ��;
            2.���(phone,family_id,abb)���������ҵ����Ϊ(phone,family_id,abb)����ҵ��;
            3.���(phone,family_id,abb)�������շ�ҵ����������������������false
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

                user_info_arr USER_TRAN_INFO_REC_ARRAY;             
			begin
				v_customer_tab := trim(p_abb)||v_customer_tab;
				v_family_tab := trim(p_abb)||v_family_tab;
				v_log_tab := trim(p_abb) || v_log_tab;
				
--				v_stmts := 'select id from '||v_customer_tab||' where phone = :1 '
--					||' and family_id = :2 and xxt_salemodalid = :3 ';
				v_stmts := 'select customer.id,family.phone,family.id family_id,'
                	||' customer.boss_salemodalid,customer.xxt_salemodalid,tdef.fee from '||v_family_tab||' family '
                    ||' left join '||v_customer_tab||' customer on family.id = customer.family_id '
                    ||' left join tranpackage_define tdef on '
                    ||' customer.xxt_salemodalid = tdef.salemodalid '
                    ||' where family.phone = :1 and family.id = :2 '	;
                    
				--�жϹ�ϵ�Ƿ����
				begin
					execute immediate v_stmts
						bulk collect into user_info_arr
						using p_phone_use,p_family_id;
                        
                    if user_info_arr is not null and user_info_arr.count > 0 then
                    	for i in user_info_arr.first..user_info_arr.last loop
                        	--������û�(phone,family_id,abb)�������������շ��ײͣ�
                            --�����������붩�������շ��ײͣ����Ȳ�����
                        	if  p_action = 1 
                            	and user_info_arr(i).boss_salemodalid <> p_sp_service_id
                            	and user_info_arr(i).fee > 0  then
                                
                                return false;
                            end if;
                            
                            --�û��Ƿ��Ѿ�������ǰҵ��
                            if user_info_arr(i).xxt_salemodalid = p_sp_service_id then
                            	v_customer_id := user_info_arr(i).customer_id;
                            end if;
                            
                    	end loop;
                    end if;
				exception
					when no_data_found then
						v_customer_id := 0;
				end;
				
				--�����ϵ
				begin
					--д����־
					log_order_operation(p_abb,p_family_id ,p_phone_use ,p_sp_service_id ,p_action ,p_access_mode );
					
					case p_action
						--�����ж�������ʱ
						when 1 then
							if v_customer_id is not null and v_customer_id > 0 then
								--�û�������ϵ�Ѿ����ڣ�ֱ�ӽ��и��²���
								v_stmts := 'update '||v_customer_tab||' set xxt_salemodalid = :1 ,'
									||' boss_salemodalid = :2,adcdeal_date = sysdate,adcdeal_suc=1, adcdeal_result= ''�ɹ�'' ,del = 0 where id = :3  ';
									
								execute immediate v_stmts
									using p_sp_service_id,p_sp_service_id,v_customer_id;
                             
							else
								--�û�������ϵ�����ڣ�д�뵽����
								v_stmts := 'insert into '||v_customer_tab||' (family_id,phone,boss_salemodalid, '
									||' xxt_salemodalid,start_date,adcdeal_date,adcdeal_result,adcdeal_suc) '
									||' values(:1,:2,:3,:4,sysdate,sysdate,''�ɹ�'',1) ';
								
								execute immediate v_stmts
									using p_family_id,p_phone_use,p_sp_service_id,p_sp_service_id;
							end if;					
						--������ȡ������ʱ
						when 2 then
							if v_customer_id is not null and v_customer_id > 0 then
								v_stmts := 'delete from '||v_customer_tab||' where id = :1 ';
								
								execute immediate v_stmts
									using v_customer_id;
							else --�����¼�����ڣ��򷵻سɹ�
								return true;
							end if;
						
						--�������͵Ĳ���ֱ�ӷ��سɹ�
						else
							return true;
					end case;
				exception
            		when CASE_NOT_FOUND then
                    	return true;
					when others then
						qtone.log_error('misc_pkg.handle_misc_order.deal_user_order_relation',
							'�û�������ϵ�ڸ���/д��ʱ�����쳣',sqlerrm||' '||sqlcode||' '||v_stmts);
						return false;
				end;		
				
				return true;
			exception
				when others then
					return false;
			end;

		/*
			����û���ǰ��������УѶͨƽ̨�����������ж��Ŷ���ҵ��;
			��ô����ҵ�񶩹����̾��ɸú��������ˡ�
			
			��������н��漰��misc_order_relation�����ɾ�Ĳ�����
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
				v_exists boolean := false; --�û��Ƿ��Ѿ�������ҵ��
				v_id misc_order_relation.id%type := 0;
			begin
				--�ж��Ƿ����ظ�����
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
				
				--������
				case p_action 
					when 1 then --����ҵ��
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
					when 2 then --ȡ��ҵ��
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
						'�����û������ڵ����ж�����ȡ��',sqlcode||' '||sqlerrm);
					
					return ;
			end;
		
	/* 
		����CPҵ��ͬ������ 
		
		��������
		if salemodalid exists
			if action = order then
				if user exists then				
					begin tran
						log order
						deal order
					end tran
				else if user not exists then
					begin tran
						create new user
						log order
						deal order
					end tran
				end if;			
			else if action = cancel then
				if user exists then				
					begin tran
						log order
						deal order
					end tran
				else if user not exists then
					return error_code
				end if;				
			end if
		else
			return error_code
		end if
		
		
		1.�û���ϵ�����ڣ�д���ռ��Լ�ҵ�񶩹���ϵ
		2.�û���ϵ�Ѿ����ڣ������쳣���루�Ѿ�������
	*/
	procedure deal_cp_order_relation(
		p_phone_use in varchar2,
		p_action in number,
		p_sp_service_id in varchar2,
		p_ret_code out nocopy varchar2
	)as
		--CPҵ����Ϣ
		TYPE T_CP_TRAN IS RECORD(
			tran_id tranpackage_define.id%type,
			tran_name tranpackage_define.name%type,
			salemodalid tranpackage_define.salemodalid%type
		);
		
		r_tran_info T_CP_TRAN; --�û���������ҵ�����Ϣ
		
		v_customer_id number(11) := 0; --�û�ID
		/* -------------------------------------------------------------------------------- */
		/* ����û��˺��Ƿ���ڣ�������ڣ��򷵻ظ��˺ŵ�id�����򣬽����˺���ӵ��û����������û���ID */
		function chk_user(p_phone in varchar2) return number
		is
			CURSOR c_user(p_phone varchar2)
				is select id from cp_customer where phone = p_phone;
				
			v_customer_id number(11) := 0;
		begin
			open c_user(p_phone);
                loop
                    fetch c_user into v_customer_id;
                    exit when c_user%notfound;  
                end loop;
			close c_user;
					
			return v_customer_id;
		end chk_user;
		
/* 		function has_order(p_phone in varchar2,p_tran_code in varchar2)
			return number 
		as
			CURSOR c_order(p_phone varchar2,p_tran_code varchar2) is
				select rel.id from cp_tran_customer rel,cp_customer customer,tranpackage_define tdef
				where customer.id = rel.customer_id and rel.transaction_id = tdef.id and customer.phone = p_phone
				and tdef.salemodalid = p_tran_code;
			
			v_rel_id number(11) := 0;		
		begin
			open c_order(p_phone,p_tran_code);
				loop
					fetch c_order into v_rel_id;
					exit when c_order%notfound;
				end loop;
			close c_order;
			
			return v_rel_id;
		end has_order; */
		
		/* ����CPҵ�� */
		procedure do_order(
			p_customer_id in number,
			p_phone in varchar2,
			p_tran_info in T_CP_TRAN)
		as
			v_customer_id number(11) := 0;
			v_rel_id number(11) := 0;
		begin
			v_customer_id := p_customer_id;
			
			if v_customer_id = 0 then --�û�������
				insert into cp_customer(userid,password,phone,add_date,res_psw)
					values(p_phone,'123456',p_phone,sysdate,to_char(trunc(dbms_random.value(100000,999999))))
					returning id into v_customer_id;
			end if;
			
			--v_rel_id := has_order(p_phone,p_tran_info.tran_code);
			
			insert into cp_tran_customer(customer_id,transaction_id) values(v_customer_id,p_tran_info.tran_id);
			insert into cp_tran_customer_log(customer_id,transaction_id,operate_type) values(v_customer_id,p_tran_info.tran_id,1);
		exception
			when DUP_VAL_ON_INDEX then
				raise e_HAS_ORDER_TRAN;
			when others then
				qtone.log_error('do_order','CPҵ�񶨹��쳣',sqlerrm||' '||sqlcode);
				raise e_CP_ORDER;				
		end do_order;
		
		/* ȡ��CPҵ�� */
		procedure do_cancel(
			p_customer_id in number,
			p_phone in varchar2,
			p_tran_info in T_CP_TRAN)		
		as
			v_customer_id number(11) := 0;
		begin
			v_customer_id := p_customer_id;
			
			if v_customer_id = 0 then --�û�������
				return;
			end if;
			
			delete from cp_tran_customer where customer_id = v_customer_id;
			insert into cp_tran_customer_log(customer_id,transaction_id,operate_type) values(v_customer_id,p_tran_info.tran_id,0);
		exception
			when others then
				qtone.log_error('do_cancel','CPҵ��ȡ���쳣',sqlerrm||' '||sqlcode);
				raise e_CP_CANCEL;
		end do_cancel;		
	begin		
		select id,name,salemodalid into r_tran_info
			from tranpackage_define where salemodalid = p_sp_service_id;		
		
		--�û��Ƿ����
		v_customer_id := chk_user(p_phone_use);
		
		savepoint sp_1;
		case p_action
			when 1 then --����
				do_order(v_customer_id,p_phone_use,r_tran_info);
			when 2 then
				do_cancel(v_customer_id,p_phone_use,r_tran_info);
			else
				NULL;
		end case;
		commit;
	exception
		when CASE_NOT_FOUND then
			rollback to sp_1;
		when e_HAS_ORDER_TRAN then
			rollback to sp_1;
		when e_CP_ORDER then
			rollback to sp_1;
		when e_CP_CANCEL then
			rollback to sp_1;
		when others then
			qtone.log_error('deal_cp_order_relation','CPҵ�񶨹��쳣',sqlerrm);			
			rollback to sp_1;
	end deal_cp_order_relation;
		
	/*
		�ж��Ƿ�����Է���ͬ����������Ϣ
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
				'misc_order_relation���ж෴��ͬ������������¼',
				sqlerrm||' '||sqlcode);
			return false;
		when NO_DATA_FOUND then
			return false;
	end is_revert_msg;

	/*
		�ж�ҵ���Ƿ����
		
		���� 
		0 - ҵ�񲻴���
		1 - УѶͨҵ��
		2 - CPҵ��
	*/
	function is_exists(p_sp_service_id in varchar2)
		return integer
		as
			v_saleModalId varchar2(50 char) := '';
			v_is_cp integer := -1;
		begin
			select salemodalid,is_cp into v_saleModalId,v_is_cp
				from tranpackage_define
				where salemodalid = p_sp_service_id;
				
			if length(v_saleModalId) > 0 then
				if v_is_cp > 0 then
					return 2;
				else
					return 1;
				end if;
			else
				return 0;
			end if;
		exception
			when NO_DATA_FOUND then
				return 0; 
		end is_exists;


	/*
		����MISC����ͬ������������
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

		v_deal_succ boolean :=  true;
		v_deal_tmp boolean := true;
		v_tmp integer := '';								
		
	begin
		--����¼д�뵽misc_order_relation����		
		write_to_misc_log_tab(p_msg_type,
			p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,
			p_access_mode,p_feature_str,p_sp_service_id,v_order_id);
        
		--�ж��û�Ҫ������ҵ���Ƿ����
		case is_exists(p_sp_service_id)
			when 0 then
				p_ret_code := '4004';
				return;
			when 2 then
				deal_cp_order_relation(p_phone_use,p_action,p_sp_service_id,p_ret_code);
				return;
			else
				NULL;
		end case;
/* 		if is_exists(p_sp_service_id) = false then
			p_ret_code := '4004';
			return;
		end if; */
		
		--�鿴�ú����Ӧ���û���¼
		begin
            select abb,family_id bulk collect into v_user_arr
            	from family_phone
                where phone = p_phone_use; 
            
            --�����ǰ�û������ϲ�������УѶͨƽ̨����ֱ��д�뵽misc_order_relation��
            --�ȴ�SIȥ����
            if v_user_arr is null or v_user_arr.count = 0 then                
				deal_user_order_relation(p_msg_type,
					p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,
					p_access_mode,p_feature_str,p_sp_service_id,p_ret_code);
			
				--��һ���жϣ����ܽ����еķ��ش��븲��
				if p_ret_code is not null and length(p_ret_code) = 0 then
					p_ret_code := '';			
				end if;
				
				return;            
            end if;       
        exception
            when others then			
				qtone.log_error('misc_pkg.handle_misc_order',
                	'�ڻ�ȡ�û�ʱ�����쳣',sqlcode||' '||sqlerrm);
			
				--��һ���жϣ����ܽ����еķ��ش��븲��
				if p_ret_code is not null and length(p_ret_code) = 0 then
					p_ret_code := '9015';			
				end if;
				
    			commit;
				return;
        end;
		
		--��ʼ����ҵ�������		
		begin		
			savepoint sp_1;
			
        	--����Ƿ���ͬ��	
			if is_revert_msg(p_transaction_id) = true then
				--��������״̬
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
					using p_sp_service_id,'ͬ���ɹ�',1,v_customer_id;
					
				--qtone.log_error('','','��������״̬'||v_stmt||' '||p_sp_service_id||' '||v_customer_id);
			else --����ͬ����¼
				/*
					���ڶ�����¼ѭ��������������ʹֻ��һ������ɹ���
					��ôҲ������ɹ���
				 */
				if v_user_arr is not null and v_user_arr.count > 0 then
				
					for i in v_user_arr.first..v_user_arr.last loop
						v_deal_tmp := deal_user_order_relation(v_user_arr(i).abb,v_user_arr(i).family_id,p_phone_use,p_sp_service_id,p_action,p_access_mode);
					
						--v_deal_tmp = false��ʾ��Ҫ��ֵд�뵽misc_order_relation
						if v_deal_tmp = false then
							v_deal_succ := false;
						end if;
					end loop;   
					
					--����ʧ�ܣ��û��Ѿ������������շ�ҵ��
					--д�뵽misc_order_relation����
					if v_deal_succ = false then
						deal_user_order_relation(p_msg_type,
							p_transaction_id,p_phone_fee,p_phone_use,p_action,p_action_reason,
							p_access_mode,p_feature_str,p_sp_service_id,p_ret_code);	
						
						p_ret_code := '';
						return ;
					end if;					
					
                else--û���ҵ��û���¼����
                    p_ret_code := '';
                    return;
                end if;
			end if;
			
			commit;    
		exception
			when others then
				rollback to sp_1;      
				qtone.log_error('misc_pkg.handle_misc_order','Misc����ͬ��,�޸�����״̬ʧ��',
					sqlcode||' '||sqlerrm);
				p_ret_code := '4008';     
		end;
		
		if p_ret_code is not null and length(p_ret_code) = 0 then
			p_ret_code := '';			
		end if;
	exception
		when others then			    
			p_ret_code := '4008';
			qtone.log_error('misc_pkg.handle_misc_order','Misc����ͬ��ҵ�񶩹�ʧ�ܣ�',
				sqlcode||' '||sqlerrm);
	end handle_misc_order;
    
    /*
    	������д�뵽misc_order_relation���С��ù���ʹ����
        PRAGMA AUTONOMOUS_TRANSACTION���ƣ����ڲ����û��Ķ���ҵ���߼�
        ����ɹ�����ʧ�ܣ�����Ҫ����־д�뵽������У����Խ���ʹ�ö���
        ������ơ�
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
					'MISC����ͬ�������޷�����д����ϵ��־��(misc_order_log)',
					sqlcode||' '||sqlerrm);
		end;
    
    /*
    	����ͬ��ʱ����ʼ���������ݣ�����״̬����ȷ�����ݽ����޸���
		
		p_ret	����ֵ��0Ϊ�����ɹ�����0��ʾ����ʧ��
    */
    procedure init_revert_order(p_ret out number)
	as
		cursor v_c_area 
			is select abb from area where abb <> 'ts';
		
		--v_row area%rowtype;  
		v_stmt varchar2(1000 char) := '';
	begin
		savepoint sp_1;
		for v_row in v_c_area loop
			v_stmt := 'update '||v_row.abb||'_tranpackage_customer customer '
				||' set adcdeal_suc = 2,adcdeal_result = ''δͬ��'' '
				||' where adcdeal_suc = 3 ';
            
	--            ||' and exists(select 1 from  '
	--            ||' misc_order_relation rel where rel.customer_id = customer.id '
	--            ||' and rel.handled = 0) ';
			
			execute immediate v_stmt;
		end loop;
		
		--ɾ������ͬ�������д���ʧ�ܵ�����
		delete from misc_order_relation
			where sync_seq is not null and is_handled = 0;
		
		commit;
		p_ret := 0;
	exception
		when others then
			rollback to sp_1;
			qtone.log_error('misc_pkg.init_revert_order','MISC����ͬ����������ʧ��',
				sqlcode||' '||sqlerrm);
			p_ret := 1;    	    
	end init_revert_order;
    
    /*
    	����ͬ��ʧ�ܵ����ݣ�����״̬����Ϊͬ��ʧ��
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
        
        --����ҵ���Ӧ���кŵ�����
        if sql%found then
        	v_stmt := 'update '||v_abb||'_tranpackage_customer '
            	||' set adcdeal_date = sysdate,adcdeal_result=''ͬ��ʧ��'', '
                ||' adcdeal_suc = 4  where id = :1 ';
            
            execute immediate v_stmt
            	using v_customer_id;
        end if;
        
    exception
    	when others then
        	rollback to sp_1;
            qtone.log_error('misc_pkg.change_status_to_fail',
            	'����״̬������"ͬ��ʧ��"ʱʧ�ܣ�',
                sqlcode||' '||sqlerrm);
            raise_application_error(-20001,'���ݸ���ʧ��');
    end;
end misc_pkg;
/
