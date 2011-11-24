#!/bin/bash

##上线脚本，程序自动生成
##步骤：
##1、在fps2新建目录，把上线文件zip、upload.sh、rollback.sh上传到此目录（注，为避免冲突覆盖等，每次上线操作均新建一个目录），此步骤人工操作，以下为本脚本自动操作
##2、备份web1相关文件到/home/zjyw/backup年月日_序列号
##3、拷贝上线代码/页面等打包的zip文件到的/home/zjyw/新建目录，并登陆到web1进行解压覆盖/data/zjxxt
##4、同样步骤操作web2

_web1="192.168.101.37"
_web2="192.168.101.38"
_bakdir="backup20111121_1"

echo "backup web1 ..."
echo "ssh to web1 .."
ssh zjyw@${_web1} <<!
if [ ! -d ${_bakdir} ]
then
 mkdir ${_bakdir}
fi
if [ ! -d ${_bakdir}'/test1/test2/test3' ]
then
 mkdir -p ${_bakdir}'/test1/test2/test3'
fi

echo "test.sh ${_bakdir}'/test1/test2/test3'"
cp test.sh ${_bakdir}'/test1/test2/test3'
!

echo "starting upload..."
sftp zjyw@${_web1} <<!
put t.zip
!

echo "put zipfile to web1 and unzip.."
ssh zjyw@${_web1} <<!
unzip -uo t.zip
rm t.zip ${_bakdir}
!

echo "backup web2 ..."
echo "ssh to web2 .."
ssh zjyw@${_web2} <<!
if [ ! -d ${_bakdir} ]
then
 mkdir ${_bakdir}
fi
if [ ! -d ${_bakdir}'/test1/test2/test3' ]
then
 mkdir -p ${_bakdir}'/test1/test2/test3'
fi

echo "test.sh ${_bakdir}'/test1/test2/test3'"
cp test.sh ${_bakdir}'/test1/test2/test3'
!

echo "starting upload..."
sftp zjyw@${_web2} <<!
put t.zip
!

echo "put zipfile to web2 and unzip.."
ssh zjyw@${_web2} <<!
unzip -uo t.zip
rm t.zip ${_bakdir}
!

echo "end."