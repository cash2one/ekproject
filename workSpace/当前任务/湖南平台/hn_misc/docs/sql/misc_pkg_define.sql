CREATE OR REPLACE package HNXXT.misc_pkg as
	/* 异常代码定义 */
	--//Added by 杨腾飞	2009-09-27	START
	
	--业务代码不存在
	e_TRAN_NOT_EXISTS EXCEPTION;
	e_TRAN_NOT_EXISTS_CODE CONSTANT integer := -20001; 
	e_TRAN_NOT_EXISTS_MSG CONSTANT varchar2(100 char) := '业务代码不存在';
	
	--用户不存在
	e_USER_NOT_EXISTS EXCEPTION;
	e_USER_NOT_EXISTS_CODE CONSTANT integer := -20011; 
	e_USER_NOT_EXISTS_MSG CONSTANT varchar2(100 char) := '用户不存在';

	--用户已经订购了该业务
	e_HAS_ORDER_TRAN EXCEPTION;
	e_HAS_ORDER_TRAN_CODE CONSTANT integer := -20012; 
	e_HAS_ORDER_TRAN_MSG CONSTANT varchar2(100 char) := '用户已经订购了该业务';	
	
	--CP业务定购异常
	e_CP_ORDER EXCEPTION;
	e_CP_ORDER_CODE CONSTANT integer := -20051; 
	e_CP_ORDER_MSG CONSTANT varchar2(100 char) := '用户订购CP业务时发生异常';	
	
	--CP业务取消异常
	e_CP_CANCEL EXCEPTION;
	e_CP_CANCEL_CODE CONSTANT integer := -20052; 
	e_CP_CANCEL_MSG CONSTANT varchar2(100 char) := '用户取消CP业务时发生异常';		
	
	PRAGMA EXCEPTION_INIT(e_TRAN_NOT_EXISTS,-20001);
	PRAGMA EXCEPTION_INIT(e_USER_NOT_EXISTS,-20011);
	PRAGMA EXCEPTION_INIT(e_HAS_ORDER_TRAN,-20012);	
	PRAGMA EXCEPTION_INIT(e_CP_ORDER,-20051);		
	PRAGMA EXCEPTION_INIT(e_CP_CANCEL,-20052);		
	
	--//Added by 杨腾飞	2009-09-27	 END 
	
	/*
		处理MISC正向同步过来的数据
	*/
    procedure handle_misc_order(
        p_msg_type in varchar2,	--消息类型
        p_transaction_id in varchar2,	--序列号
        p_phone_fee in varchar2,	--付费手机号
        p_phone_use in varchar2,	--使用业务手机号
        p_action in number,	--操作类型（订购、退订）
        p_action_reason in number, --操作原因
		p_access_mode in number, --服务访问方式
		p_feature_str in varchar2,	--服务订购参数
        p_sp_service_id in varchar2,  --业务代码
        p_ret_code out nocopy varchar2	--操作结果
    );
    
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
    ) ;
	
    /*
    	反向同步时，初始化所有数据，并将状态不正确的数据进行修复！
		
		p_ret	返回值，0为操作成功，非0表示操作失败
    */
    procedure init_revert_order(p_ret out number);
    
    /*
    	处理同步失败的数据，将其状态设置为同步失败
		
		p_tran_id	消息的序列号
    */
    procedure change_status_to_fail(p_tran_id in varchar2);
end misc_pkg;
/