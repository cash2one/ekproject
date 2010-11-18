/*
	湖南平台同步程序测试协助
    
	准备数据：
    	为数据库准备相应测试数据
*/
DECLARE
	type STR_ARRAY is table of varchar2(30 char) index by binary_integer;
	
	v_area_arr STR_ARRAY;
	
	
BEGIN
	v_area_arr(1) := 'cs'; --长沙
	v_area_arr(2) := 'ts'; --调试地区
	
	
EXCEPTION
   WHEN NO_DATA_FOUND THEN
      NULL;
   WHEN OTHERS THEN
      NULL;
END;