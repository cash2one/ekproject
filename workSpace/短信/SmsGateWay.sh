## Description:
##1.从短信中网关中导出tbDest表中的数据

#declare some variable,
_begin_time=`date +%s`
_work_dir="/home/mysql/smsstate1"
_work_tbdst_dir="${_work_dir}/tbdest/"

#指定导出的数据为昨天的数据
_dataTime=`data -d last-day +%Y%m%d`

_db_user="sms"
_db_psw="zjsms2010"
_dataBase="sms"


#登录短信网关数据库，执行数据导出操作，生成 tbDest表的记录文件
echo -e "Begin to Export tbDest Data from ${_dataBase} DataBase "

/usr/bin/mysql -u${_db_user} -D${_dataBase} -P${_db_psw} <<!
select * into outfile '${_work_tbdst_dir}tbdest1.txt' fields terminated by  '|' from tbDest where time='${_dataTime}';
!


echo -e "Elapsed $_used_time seconds!"
echo "`date +"%Y-%m-%d %H:%M:%S.%N"` ${_dataBase} tbDest Data Export finished!"


exit