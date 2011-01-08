#!/bin/bash

#######################################
#从短信状态报告中解析出可导入mysql的文本,然后远程上传到指定服务器
#需要与目标服务器做好匿名登录，使之可以直接用scp指令
#每个短信网关需建立/home/thinker/getsmsstate/smsfile目录
#@qtone.cn
#nwq 2009-01-07
#######################################

############################################################################
###
###	declare some utility functions
###
function p(){
	for loop in "$@"
	do
		echo -e "$loop"
	done
}

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
# The short message status file's location
_sms_status_file="/home/thinker/CMPPGW1/smsstate.log"

## declare current directory and log file
_workdir="/home/mysql/smsstate1"
_smsfile_dir="${_workdir}/smsfile"
_archived_dir="${_workdir}/archived"
_ts=`date +%Y%m%d%H%M%S`

_log="${_workdir}/updateLog.log"
_parsed_status_file="${_smsfile_dir}/parsed_status_file.txt.${_ts}"

#if the required dirs is not exists,create new ones
create_dir_if_not_exists $_workdir $_smsfile_dir $_archived_dir

cd $_workdir
echo `date +"%Y-%m-%d %H:%M:%S.%N"` >> ${_log}

if [ -z $1 ]
then
	p "Begin to parse!"
	######### get last  csmid
	lastCSMID=`grep -i "CSMID" ${_log} | tail -1 | awk -F"=" '{print $2}' |  sed  's/-//g' `
	lastRowNumber=0

	if [ -z $lastCSMID ]
	then
			lastCSMID="9999999999999999999999999"
	fi

	lastRowNumber=`grep -an $lastCSMID $_sms_status_file |  awk  -F":" '{print $1}' | head -1`

	#### from log file get Row Number>lastRowNumber write to sqlLoadFile.txt
	awk -F"|" '
	{
		if((NR+0)>("'$lastRowNumber'"+0) && $2 != "DELIVRD" && length($5) >= 13){
			if(substr($5,1,2) == "86"){
				print ","$1 "," $2 "," $3 "," $4 "," substr($5,3,11)
			}else
				print ","$1 "," $2 "," $3 "," $4 "," substr($5,1,11)
		}
	}
	' $_sms_status_file > $_parsed_status_file

	p "_sms_status_file = $_sms_status_file	_parsed_status_file = $_parsed_status_file"

	delelte_file_if_exists "/home/mysql/raw_smsstatus1.txt"
	cp "$_parsed_status_file" "/home/mysql/raw_smsstatus1.txt"
	
	newLastCSMID=`tail -1 $_parsed_status_file | awk -F"," '{print $2}'`

	if [ -z $newLastCSMID ]
	then
		echo "`date +"%Y-%m-%d %H:%M:%S.%N"` no found new sms state records ">>${_log}
	else
		echo "`date` lastRowNumber is $lastRowNumber CSMID=$newLastCSMID">>${_log}
	fi

	mv ${_smsfile_dir}/* "${_archived_dir}/"

	p "lastCSMID = $lastCSMID" "lastRowNumber = $lastRowNumber" "newLastCSMID = $newLastCSMID"

	#current hourmin
	curTime=`date +%k%M`

	##每天早上5点27到5点35之前删除文件
	if [ $curTime -gt 525 ]
	then
	  if [ $curTime -lt 535 ]
		then        
			#rm -f /home/thinker/smsstate1/smsfile/*curLog*       
			echo -e "Archive the parsed sms state file in dir ${_archived_dir}"
			cd ${_archived_dir} && tar --bzip -cf `date +%Y%m%d%H%M%S`.tar.bz2 *.txt && rm -fr *.txt
	  fi
	fi

	#chown -R mysql:mysql * 

	echo -e "The file parsing is finished!"
else
	echo -e "Begin to Load status log into mysql...."

	#mysql param
	DBUSER=sms
	DBPASSWORD=zjsms2010
	DATABASE=sms

	cd $_smsfile_dir
	tablename="tbSmsSendState"

	echo "LOAD DATA INFILE '/home/mysql/raw1.txt' INTO TABLE tbSmsSendState FIELDS TERMINATED BY ',' " | \
	/usr/bin/mysql -u${DBUSER} -D${DATABASE} -p${DBPASSWORD} 

	delelte_file_if_exists "/home/mysql/smsstatus1.txt"

/usr/bin/mysql   -u${DBUSER} -D${DATABASE} -p${DBPASSWORD} <<!
select distinct a.cSMID,a.cState stat_result,b.cDest phone,b.cState stat_sn into outfile '/home/mysql/smsstatus1.txt' fields terminated by '|' from tbSmsSendState a join tbDest b where a.cSMID = b.cSMID and b.cState is not null ;
truncate table tbSmsSendState ;
!

	rm -fr /home/mysql/raw_smsstatus1.txt
fi

exit


