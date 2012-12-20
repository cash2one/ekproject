#coding=utf-8
#http://pysvn.tigris.org/docs/pysvn_prog_ref.html#pysvn_revision
#http://pysvn.tigris.org/docs/pysvn_prog_guide.html
__author__ = 'Ethanlam'

import pysvn
client = pysvn.Client()
DEBUG = True

#代码合并:
class codeutil(object):
    def __init__(self):
        pass

    #控制台日志输出:
    def console(self,msg):
        if DEBUG:
            print 'CodeManager debug :%s' % msg

    #代码检出
    def checkout(self,codeUrl,localPath):
        self.console("checkout Ing %s to localPath save :%s" % (codeUrl,localPath))
        tips = client.checkout(codeUrl,localPath)
        self.console("checkout finished....%s" % tips)
        pass


    #分支合并操作
    def merge(self,url1,revision1,url2,revision2,local_path):
        revnum1 = pysvn.Revision( pysvn.opt_revision_kind.number,revision1)
        revnum2 = pysvn.Revision( pysvn.opt_revision_kind.number,revision2)
        self.console("merge  revision from %s:r%d to %s:r%d  " % (url1,revision1,url2,revision2,) )
        tips = client.merge(url1,revnum1,url2,revnum2,local_path)
        self.console("merge finished....%s" %  tips)
        pass

#多分支进行开发，按照版本号的积累进行逐步合并的操作
def mergeOfStepbyStep():
    t = codeutil()
    #下载代码
    mRootPath = 'svn://192.168.1.104:36900/svn/xxt/xxt2_zj/'
    codeBase = 'D:/code_svn/test3/TeacherApp/'
    print 'mergeOfStepbyStep start'

    t.checkout(mRootPath+'/trunk/04newxxt/webApps/TeacherApp/',codeBase)

    branPath='/branches/03新版开发分支/TeacherWebApp/20121203-20121207/'

    t.merge(mRootPath+branPath+'/src',23465,mRootPath+branPath+'/src',23552,codeBase+'/src')
#    t.merge('svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/01',19172,'svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/01',19198,'d:/svntest/examples/p1/')
    #分支3
#    t.merge('svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/03',19261,'svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/03',19262,'d:/svntest/examples/p1/')
    #分支2 - 后来再有修改的情况
#    t.merge('svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/02',19175,'svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/02',19263,'d:/svntest/examples/p1/')
    #分支1
#    t.merge('svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/01',19172,'svn://192.168.1.104:36900/svn/xxt/xxt2_zj/branches/test/01',19198,'d:/svntest/examples/p1/')
#    t.console('操作完成....')
    print 'mergeOfStepbyStep finished'


mergeOfStepbyStep();