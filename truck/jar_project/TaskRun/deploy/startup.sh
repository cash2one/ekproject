#!/bin/sh
#test
echo startup the TaskUtil_beta_1_1.jar app `date` >> startup.log
kill -9 `ps -aux|grep TaskUtil_beta_1_1.jar|awk '{print $2}'`
java -jar TaskUtil_beta_1_1.jar & 
echo startup finished! `date` >> startup.log
