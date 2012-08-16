#http://pysvn.tigris.org/docs/pysvn_prog_ref.html#pysvn_revision
#http://pysvn.tigris.org/docs/pysvn_prog_guide.html

#coding=utf-8
__author__ = 'Ethanlam'

import pysvn
client = pysvn.Client()


##SVN 代码管理工具
class CodeManager(object):
    def __init__(self):
        pass

    #控制台日志输出:
    def console(self,msg):
        print 'CodeManager 控制台 :%s' % msg


    #代码检出
    def checkout(self,codeUrl,localPath):
        client.checkout(codeUrl,localPath)
        self.console("代码已经执行完检出操作....")
        pass


    #分支合并操作
    def merge(self,url1,revision1,url2,revision2,local_path):
        revnum1 = pysvn.Revision( pysvn.opt_revision_kind.number,revision1)
        revnum2 = pysvn.Revision( pysvn.opt_revision_kind.number,revision2)
        client.merge(url1,revnum1,url2,revnum2,local_path)
        self.console("代码已经执行完合并操作....")
        pass


t = CodeManager()
#t.checkout('svn://192.168.1.104:36900/svn/xxt/xxt2_zj/trunk/04newxxt/IntelligenceSuggestion/src','d:/svntest/examples/p1/')
t.merge('svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/01',19172,'svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/01',19174,'d:/svntest/examples/p1/')
t.console('test....')


