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


_local_work_dir="/var/oracle/smsstatelogs"
_local_log_tmp_dir="${_local_work_dir}/temp/"


_remove_work_dir="/home/mysql"
_remove_log_file_sms1="${_remove_work_dir}/smsstate1/smsstate.log${_ts}"
_remove_log_file_sms2="${_remove_work_dir}/smsstate2/smsstate.log${_ts}"

_remove_tbdst_dir_sms1="${_remove_work_dir}/smsstate1/tbdest/"



#利用Ftp 下载短信网关状态回写日志文件 到 ORACL 服务器
echo -e " sftp sms1 to download  ${_remove_log_file} "
# get the smsstate logs file and the parsed smsstatus file from sms1
sftp zjyw@${_sms1} <<!
get ${_remove_log_file_sms1}  ${_local_log_tmp_dir}smsstate1.log
!


#利用Ftp 下载短信网关状态回写日志文件 到 ORACL 服务器
echo -e " sftp sms2 to download  ${_remove_log_file} "
# get the smsstate logs file and the parsed smsstatus file from sms2
sftp zjyw@${_sms2} <<!
get ${_remove_log_file_sms2}  ${_local_log_tmp_dir}smsstate2.log
!


#登录到短信网关中,执行数据导出脚本,生成tbDest数据
echo -e " login sms1 and execute export tbdest data "
ssh zjyw@${_sms1} <<!
cd ${_remove_work_dir}/smsstate1/
sh SmsGateWay.sh
!


#登录到短信网关中,执行数据导出脚本,生成tbDest数据
echo -e "login sms2 and execute export tbdest data "
ssh zjyw@${_sms2} <<!
cd ${_remove_work_dir}/smsstate2/
sh SmsGateWay.sh 
!


echo -e "Elapsed $_used_time seconds!"
echo "`date +"%Y-%m-%d %H:%M:%S.%N"` sms status dealing finished!"
