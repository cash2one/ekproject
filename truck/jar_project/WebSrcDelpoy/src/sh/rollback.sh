#!/bin/bash

##���߻��˽ű��������Զ�����
##���裺
##�ֱ��½��web1��web2 �ѱ���Ŀ¼home/zjyw/backup������_���кŵ��ļ�������/data/zjxxt�����и���

_web1="192.168.101.37"
_web2="192.168.101.38"
_bakdir="backup20111121_1"

echo "rollback ..."

echo "ssh to web1.."

ssh zjyw@${_web1} <<!
echo "cp ${bakdir}/test.txt test2.txt"
cp ${bakdir}/test.txt test2.txt
!

echo "ssh to web2.."
echo "cp ${bakdir}/test.txt test2.txt"
cp ${bakdir}/test.txt test2.txt
!

echo "end."