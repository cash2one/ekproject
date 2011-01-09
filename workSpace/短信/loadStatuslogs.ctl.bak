load data CHARACTERSET ZHS16GBK 
infile '/var/oracle/statelogs/pase_state_logs.txt' 
append into table sms_status_temp 
FIELDS TERMINATED BY "|" 
(
   SMID,
   STATE_RESULT,
   SEND_TIME DATE "YYYY-MM-DD HH24:MI:SS",
   REC_TIME DATE "YYYY-MM-DD HH24:MI:SS",
   PHONE
)