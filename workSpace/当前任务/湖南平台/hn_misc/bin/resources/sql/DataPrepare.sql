/*
	����ƽ̨ͬ���������Э��
    
	׼�����ݣ�
    	Ϊ���ݿ�׼����Ӧ��������
*/
DECLARE
	type STR_ARRAY is table of varchar2(30 char) index by binary_integer;
	
	v_area_arr STR_ARRAY;
	
	
BEGIN
	v_area_arr(1) := 'cs'; --��ɳ
	v_area_arr(2) := 'ts'; --���Ե���
	
	
EXCEPTION
   WHEN NO_DATA_FOUND THEN
      NULL;
   WHEN OTHERS THEN
      NULL;
END;