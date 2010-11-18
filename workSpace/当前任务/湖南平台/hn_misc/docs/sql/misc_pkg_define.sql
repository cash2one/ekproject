CREATE OR REPLACE package HNXXT.misc_pkg as
	/* �쳣���붨�� */
	--//Added by ���ڷ�	2009-09-27	START
	
	--ҵ����벻����
	e_TRAN_NOT_EXISTS EXCEPTION;
	e_TRAN_NOT_EXISTS_CODE CONSTANT integer := -20001; 
	e_TRAN_NOT_EXISTS_MSG CONSTANT varchar2(100 char) := 'ҵ����벻����';
	
	--�û�������
	e_USER_NOT_EXISTS EXCEPTION;
	e_USER_NOT_EXISTS_CODE CONSTANT integer := -20011; 
	e_USER_NOT_EXISTS_MSG CONSTANT varchar2(100 char) := '�û�������';

	--�û��Ѿ������˸�ҵ��
	e_HAS_ORDER_TRAN EXCEPTION;
	e_HAS_ORDER_TRAN_CODE CONSTANT integer := -20012; 
	e_HAS_ORDER_TRAN_MSG CONSTANT varchar2(100 char) := '�û��Ѿ������˸�ҵ��';	
	
	--CPҵ�񶨹��쳣
	e_CP_ORDER EXCEPTION;
	e_CP_ORDER_CODE CONSTANT integer := -20051; 
	e_CP_ORDER_MSG CONSTANT varchar2(100 char) := '�û�����CPҵ��ʱ�����쳣';	
	
	--CPҵ��ȡ���쳣
	e_CP_CANCEL EXCEPTION;
	e_CP_CANCEL_CODE CONSTANT integer := -20052; 
	e_CP_CANCEL_MSG CONSTANT varchar2(100 char) := '�û�ȡ��CPҵ��ʱ�����쳣';		
	
	PRAGMA EXCEPTION_INIT(e_TRAN_NOT_EXISTS,-20001);
	PRAGMA EXCEPTION_INIT(e_USER_NOT_EXISTS,-20011);
	PRAGMA EXCEPTION_INIT(e_HAS_ORDER_TRAN,-20012);	
	PRAGMA EXCEPTION_INIT(e_CP_ORDER,-20051);		
	PRAGMA EXCEPTION_INIT(e_CP_CANCEL,-20052);		
	
	--//Added by ���ڷ�	2009-09-27	 END 
	
	/*
		����MISC����ͬ������������
	*/
    procedure handle_misc_order(
        p_msg_type in varchar2,	--��Ϣ����
        p_transaction_id in varchar2,	--���к�
        p_phone_fee in varchar2,	--�����ֻ���
        p_phone_use in varchar2,	--ʹ��ҵ���ֻ���
        p_action in number,	--�������ͣ��������˶���
        p_action_reason in number, --����ԭ��
		p_access_mode in number, --������ʷ�ʽ
		p_feature_str in varchar2,	--���񶩹�����
        p_sp_service_id in varchar2,  --ҵ�����
        p_ret_code out nocopy varchar2	--�������
    );
    
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
    ) ;
	
    /*
    	����ͬ��ʱ����ʼ���������ݣ�����״̬����ȷ�����ݽ����޸���
		
		p_ret	����ֵ��0Ϊ�����ɹ�����0��ʾ����ʧ��
    */
    procedure init_revert_order(p_ret out number);
    
    /*
    	����ͬ��ʧ�ܵ����ݣ�����״̬����Ϊͬ��ʧ��
		
		p_tran_id	��Ϣ�����к�
    */
    procedure change_status_to_fail(p_tran_id in varchar2);
end misc_pkg;
/