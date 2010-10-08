   
     select stu.id, stu.stu_sequence,stu.name,cl.class_id  from zs_xj_student stu join zs_xj_stu_class cl on stu.stu_sequence = cl.stu_sequence 
     where stu.id>= 2449812
     order by create_time desc
   
     select * from zs_xj_family
     where zs_xj_family.stu_sequence = 100900005011

     update zs_xj_family
     set  zs_xj_family.is_dxx = 1
     where zs_xj_family.stu_sequence = 100900005012


     select * from xj_class cl
     where cl.id = 488811 
     order by cl.create_time desc
     
     
     // 毕业处理
     update xj_class
     set xj_class.in_school = 1
     where xj_class.id = 488812
     
     
     select * from zs_stu_class_transfer
     DELETE FROM zs_stu_class_transfer
     //毕业过程
     
      insert into zs_stu_class_transfer
      select o.id,o.school_id,o.class_id,o.stu_sequence　from zs_xj_stu_class o
      where o.class_id = 488812
      
      
      //删除原来的表数据
      select * from zs_xj_stu_class
        where zs_xj_stu_class.class_id = 488812
      
      delete from zs_xj_stu_class 
      where zs_xj_stu_class.class_id = 488812


     select * from zs_xj_stu_class
     where zs_xj_stu_class.class_id = 488811
     
     




     
