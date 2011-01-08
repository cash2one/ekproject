## Description:
##1.To tftp  get sms status file located on sms1 and sms2 to local;
##2.
##3.
##

echo -e "`date +"%Y-%m-%d %H:%M:%S.%N"` begin to download sms status logs "

#declare some variable
_ts=`date +%y%m%d`
_begin_time=`date +%s`
_sms1="sms"
_sms2="sms"

_remove_logs_dir="/home/mysql/"
_remove_log_file="${_remove_logs_dir}smsstate.log${_ts}"
_local_logs_dir="/var/oracle/smsstatelogs/"
_local_log_tmp_dir="${_local_logs_dir}temp/"
_remove_tbdst_dir="${_remove_logs_dir}tbdest/"


#Ftp 短信网关状态回写日志文件 到 ORACL 服务器
echo -e " sftp sms to download  ${_remove_log_file} "
# get the smsstate logs file and the parsed smsstatus file from sms1
sftp zjyw@${_sms1} <<!
get ${_remove_log_file}  ${_local_log_tmp_dir}smsstate1.log
!


#登录到短信网关中
cd ${_local_logs_dir}
##login into the sms1 and doing parse
ssh zjyw@${_sms1} <<!


#登录短信网关数据库，执行数据导出操作，生成 tbDest表的记录文件
echo -e "Begin to Export tbDest  Data from SMS DB "
_db_user="sms"
_db_psw="zjsms2010"
_dataBase="sms"

cd ${_remove_tbdst_dir}

echo -e "Login smsDB and export tbdest table "
/usr/bin/mysql -u${_db_user} -D${_dataBase} -P${_db_psw} <<!
select * into outfile '${_remove_tbdst_dir}tbdest1.txt' fields terminated by  '|' from tbDest;
!

echo -e "Export data finished!"

















echo -e "Elapsed $_used_time seconds!"
echo "`date +"%Y-%m-%d %H:%M:%S.%N"` sms status dealing finished!"
