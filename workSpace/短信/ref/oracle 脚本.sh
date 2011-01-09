## 2010-1-12
## Author: ytfei
## Description:
##1.parse the sms status file located on sms1 and sms2;
##2.download the result file from the both server and merge locale
##3.upload the merged file to sms1 and sms2 and load to each mysql installed on the sms1 and sms2
##4.doing match and export matched data
##5.download the martched data and merge local
##6.install the martched data as oracle external file
##

echo -e "`date +"%Y-%m-%d %H:%M:%S.%N"` begin to deal sms status "

#declare some variable
_begin_time=`date +%s`
_sms1="sms"
_sms2="sms"

echo "`date` begin to deal sms status "
_ts=`date +%Y%m%d%H%M%S`

cd /var/oracle/smsstatus
##login into the sms1 and doing parse
ssh zjyw@${_sms1} <<!
cd /home/mysql/smsstate1
sh getSS.sh
!

# get the raw smsstatus file and the parsed smsstatus file from sms1
sftp zjyw@${_sms1} <<!
get /home/mysql/raw_smsstatus1.txt /var/oracle/smsstatus/raw_sms1.txt
!

##login into the sms2 and doing parse
ssh zjyw@${_sms2} <<!
cd /home/mysql/smsstate2
sh getSS.sh
!

sftp zjyw@${_sms2} <<!
get /home/mysql/raw_smsstatus2.txt /var/oracle/smsstatus/raw_sms2.txt
!

cd /var/oracle/smsstatus
cat raw_sms1.txt >> raw.txt
cat raw_sms2.txt >> raw.txt

# upload the raw sms status file to sms1
sftp zjyw@${_sms1} <<!
put /var/oracle/smsstatus/raw.txt /home/mysql/raw1.txt
!

sftp zjyw@${_sms2} <<!
put /var/oracle/smsstatus/raw.txt /home/mysql/raw2.txt
!

mv /var/oracle/smsstatus/raw.txt "/var/oracle/smsstatus/archived/raw.txt.${_ts}"
mv /var/oracle/smsstatus/raw_sms2.txt "/var/oracle/smsstatus/archived/raw_sms2.txt.${_ts}"
mv /var/oracle/smsstatus/raw_sms1.txt "/var/oracle/smsstatus/archived/raw_sms1.txt.${_ts}"

# load file into sms1 mysql and do a match operation
ssh zjyw@${_sms1} <<!
cd /home/mysql/smsstate1
sh getSS.sh DoMatch
rm -fr /home/mysql/raw1.txt
!

ssh zjyw@${_sms2} <<!
cd /home/mysql/smsstate2
sh getSS.sh DoMatch
rm -fr /home/mysql/raw2.txt
!

# download the matched sms status file from sms1
sftp zjyw@${_sms1} <<!
get /home/mysql/smsstatus1.txt /var/oracle/smsstatus/sms1.txt
!

sftp zjyw@${_sms2} <<!
get /home/mysql/smsstatus2.txt /var/oracle/smsstatus/sms2.txt
!

##merge the matched file download from sms1 and sms2 as the
##new smsstatus.txt file.
cd /var/oracle/smsstatus
mv smsstatus.txt "/var/oracle/smsstatus/archived/smsstatus.txt.${_ts}" && touch smsstatus.txt 
cat sms1.txt >> smsstatus.txt
cat sms2.txt >> smsstatus.txt

mv sms1.txt "/var/oracle/smsstatus/archived/sms1.txt.${_ts}"
mv sms2.txt "/var/oracle/smsstatus/archived/sms2.txt.${_ts}"
## load data into oracle
echo -e  "Dispatch sms status into *_dx_sms.... "
su - oracle -c '
cd /var/oracle/smsstatus/
sqlldr userid=zjxxt/zjxxt100602 control=loadstatus.ctl log=resulthis.out

sqlplus -S /nolog <<!
conn zjxxt/zjxxt100602
exec sms_status_handler
!
'
_end_time=`date +%s`
_used_time=`expr $_end_time - $_begin_time`
echo -e "Elapsed $_used_time seconds!"
echo "`date +"%Y-%m-%d %H:%M:%S.%N"` sms status dealing finished!"