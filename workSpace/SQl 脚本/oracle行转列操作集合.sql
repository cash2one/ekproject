
--Oracle  行转列 操作 
SQL> select * from t;   
  
          I A           D   
           ---------- -------------------   
          1 b           2008-03-27 10:55:42   
          1 a           2008-03-27 10:55:46   
          1 d           2008-03-27 10:55:30   
          2 z           2008-03-27 10:55:55   
          2 t           2008-03-27 10:55:59

--- 效果
1   d,b,a   
2   z,t  


-- 
-- 方法一  sys_connect_by_path 
/* Formatted on 2010/11/26 13:36 (Formatter Plus v4.8.8) */
SELECT     i, LTRIM (MAX (SYS_CONNECT_BY_PATH (a, ',')), ',') a
      FROM (SELECT i, a, d, MIN (d) OVER (PARTITION BY i) d_min,
                     (ROW_NUMBER () OVER (ORDER BY i, d))
                   + (DENSE_RANK () OVER (ORDER BY i)) numid
              FROM t)
START WITH d = d_min
CONNECT BY numid - 1 = PRIOR numid
  GROUP BY i;



---wm_sys.wm_concat 


