load data CHARACTERSET ZHS16GBK 
infile '/var/oracle/tbdest/tbdest.txt' 
append into table sms_status_temp FIELDS TERMINATED BY "|" 
( smid,
  stat_result,
  phone,
  stat_sn
)