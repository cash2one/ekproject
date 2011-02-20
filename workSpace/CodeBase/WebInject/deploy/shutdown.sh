#!/bin/sh
#description: shutdown the complaint datas of misc  fetch app
echo run MiscComplaintFetch.jar shutdown script `date` >>startup.log 
kill -9 `ps -aux |grep MiscComplaintFetch.jar |awk '{print $2}'`  
echo finished !
