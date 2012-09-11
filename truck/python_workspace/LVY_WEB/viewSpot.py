#coding=utf-8
__author__ = 'Ethanlam'

#景点服务类

import tornado
import tornado.web


#控制器基类
class BaseHandler(tornado.web.RequestHandler):
    @property
    def db(self):
        return self.application.db

    def get_current_user(self):
        user_id = self.get_secure_cookie("user")
        if not user_id: return None
        return self.db.get("SELECT * FROM authors WHERE id = %s", int(user_id))


#应用首页列出省区信息
class HomeHandler(BaseHandler):
    def get(self):
        entries = self.db.query("SELECT * FROM area ORDER BY id "
                                "DESC LIMIT 34")
#        if not entries:
#            self.redirect("/")
#            return
        self.render("home.html", entries=entries)



#列出地市信息
class CityHandler(BaseHandler):
    def get(self,area):
        print "CityHandler :%s ------- " % area
        entries = self.db.query("SELECT * FROM city where area_id=%s order by id ",area)
#        if not entries:
#            self.redirect("/")
#            return
        self.render("city.html", entries=entries)



#应用首页
class ViewSpotHandler(BaseHandler):
    def get(self,city):
        entries = self.db.query("SELECT * FROM jingdian where city_id=%s ORDER BY id "
                                "DESC LIMIT 100 ",city)
        if not entries:
            self.redirect("/")
            return
        self.render("viewspot.html", entries=entries)

