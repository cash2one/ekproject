--drop table misc_order_relation
CREATE TABLE MISC_ORDER_log
(
  ID              NUMBER(11) primary key,
  AREA_ABB        VARCHAR2(10 CHAR),
  CUSTOMER_ID     NUMBER(11),
  MSG_TYPE        VARCHAR2(50 CHAR),
  TRANSACTION_ID  VARCHAR2(50 CHAR),
  PHONE_FEE       VARCHAR2(30 CHAR),
  PHONE_USE       VARCHAR2(30 CHAR),
  ACTION          NUMBER(1),
  ACTION_REASON   NUMBER(1),
  access_mode	  number(1),
  feature_str	  varchar2(100 char),
  SALEMODALID     VARCHAR2(30 CHAR),
  CREATE_DATE     DATE                          DEFAULT sysdate,
  is_HANDLED         NUMBER(1)                     DEFAULT 0,
  HANDLE_DATE     DATE,
  SYNC_SEQ        VARCHAR2(50 CHAR)
);

create sequence MISC_ORDER_log_seq
start with 1 increment by 1 
maxvalue 99999999999 nocache cycle;

CREATE OR REPLACE TRIGGER MISC_ORDER_log_tri
    before insert or update ON MISC_ORDER_log     
    for each row
begin
	if inserting then
    	select MISC_ORDER_log_seq.nextval into :new.id from dual;
    elsif updating then
    	:new.handle_date := sysdate;
    end if;
end;
/