#!/bin/sh
LANG="zh_CN.GB18030"
export LANG

JAVA_HOME="/usr/local/jdk1.5.0_19"
echo "Killing old process..."
kill -9 `cat revertsync.pid`
sleep 5
echo "Process killed"

echo "Starting RevertSync program...."
nohup $JAVA_HOME/bin/java -classpath ./WebContent/WEB-INF/classes:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/testng-5.9-jdk15.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/commons-codec-1.3.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/easymock.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/commons-dbutils-1.2-sources.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/jcip-annotations.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/proxool-cglib.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/ojdbc14.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/dom4j-1.6.1.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/activation.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/commons-lang-2.4.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/commons-dbutils-1.2.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/groovy-all-1.6.3.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/commons-logging-1.1.1.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/mail.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/servlet-api.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/commons-io-1.4.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/qtone-mail.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/jdom-1.0.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/proxool-0.9.1.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/log4j-1.2.15.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/apache-mime4j-0.6.jar:/home/ytfei/workspace/revert-sync/WebContent/WEB-INF/lib/commons-email-1.1.jar -Djava.security.policy=all.policy  -Dfile.encoding=GBK com.qtone.datasync.misc.client.RevertSyncMain 2>>$0.log &

V_PID=$!

if [ $V_PID -gt 0 ];
then
	echo "RvertSync started."
fi

echo $V_PID > revertsync.pid
