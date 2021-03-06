## 2011-01-07
## Author: HanSheng.Lam
## Description:
##1.对昨天已经导出的文件移动到备份目录中
##2.从短信中网关中导出tbDest表中的昨天的数据

############################################################################

function create_dir_if_not_exists(){
	for loop in "$@"
	do
		if [ ! -d $loop ];
		then
			mkdir -p $loop
			p "Create dir $loop"
		fi
	done
}

function delelte_file_if_exists(){
	for loop in "$@"
	do
		if [ -f $loop ];
		then
			rm -fr $loop
			p "Delete file $loop"
		fi
	done
}

############################################################################


#declare some variable,
_fileTime=`date -d last-day +%y%m%d`


#指定导出的数据为昨天的数据
_dataTime_opt=`date -d last-day +%Y-%m-%d`


_work_dir="/home/mysql/smsstate1"
_tddst_dir="${_work_dir}/tbdest"
_tbdst_export_dir="${_tddst_dir}/raw"
_tbdst_tmp_dir="${_tddst_dir}/tmp";
_tbdst_export_file="${_tbdst_export_dir}/tbdest_${_fileTime}.txt"


_db_user="sms"
_db_psw="zjsms2010"
_dataBase="sms"


cd ${_work_dir}


#把必要的目录建立
create_dir_if_not_exists  $_tddst_dir $_tbdst_export_dir $_tbdst_tmp_dir


echo "备份导出的数据文件到 ${_work_tbdst_raw_dir} 目录下。"
mv "${_tbdst_export_dir}/*"  "${_tbdst_tmp_dir}/"


#登录短信网关数据库，执行数据导出操作，生成 tbDest表的记录文件
echo -e "Begin to Export tbDest Data from ${_dataBase} DataBase "


delelte_file_if_exists $_tbdst_export_file


/usr/bin/mysql -u${_db_user} -D${_dataBase} -P${_db_psw} <<!
select * into outfile '${_tbdst_export_file}' fields terminated by  '|' from tbDest where time='${_dataTime_opt}';
!

echo -e "Elapsed $_used_time seconds!"
echo "`date +"%Y-%m-%d %H:%M:%S.%N"` ${_dataBase} tbDest Data Export finished!"

exit