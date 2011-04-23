#!/bin/sh
#description: shutdown the TaskUtil_beta_1_1 app
echo run TaskUtil_beta_1_1.jar shutdown script `date` >>startup.log 
kill -9 `ps -aux |grep TaskUtil_beta_1_1.jar |awk '{print $2}'`  
echo finished !
