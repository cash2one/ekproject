#!/bin/bash

##上线回退脚本，程序自动生成
##步骤：
##分别登陆到web1、web2 把备份目录home/zjyw/backup年月日_序列号的文件拷贝到/data/zjxxt，进行覆盖

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