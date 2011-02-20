#!/bin/sh
#test
echo startup the fetch complaint datas from misc_sys app `date` >> startup.log
kill -9 `ps -aux|grep MiscComplaintFetch.jar|awk '{print $2}'`
java -jar MiscComplaintFetch.jar & 
echo startup finished! `date` >> startup.log
