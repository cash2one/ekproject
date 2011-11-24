#!/bin/bash

##���߽ű��������Զ�����
##���裺
##1����fps2�½�Ŀ¼���������ļ�zip��upload.sh��rollback.sh�ϴ�����Ŀ¼��ע��Ϊ�����ͻ���ǵȣ�ÿ�����߲������½�һ��Ŀ¼�����˲����˹�����������Ϊ���ű��Զ�����
##2������web1����ļ���/home/zjyw/backup������_���к�
##3���������ߴ���/ҳ��ȴ����zip�ļ�����/home/zjyw/�½�Ŀ¼������½��web1���н�ѹ����/data/zjxxt
##4��ͬ���������web2

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