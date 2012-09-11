#coding=utf-8
__author__ = 'Ethanlam'

#旅游网站类 View Spot

import os.path
import re
import tornado.auth
import tornado.database
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web

from tornado.options import define, options
from uiModules import AreaEntryModule,ViewspotEntryModule,CityEntryModule
from viewSpot import BaseHandler,HomeHandler,ViewSpotHandler,CityHandler


#应用的基础配置
define("port", default=80, help="run on the given port", type=int)
define("mysql_host", default="127.0.0.1:3306", help="blog database host")
define("mysql_database", default="test", help="blog database name")
define("mysql_user", default="root", help="blog database user")
define("mysql_password", default="123456", help="blog database password")


#应用设置
class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r"/", HomeHandler),
            (r"/view/([^/]+)",ViewSpotHandler),
            (r"/area/([^/]+)",CityHandler),
        ]
        settings = dict(
            view_title=u"旅游信息",
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
            ui_modules={"AreaEntry": AreaEntryModule,"ViewspotEntry":ViewspotEntryModule,"CityEntry":CityEntryModule},
            xsrf_cookies=True,
            cookie_secret="11oETzKXQAGaYdkL5gEmGeJJFuYh7EQnp2XdTP1o/Vo=",
            login_url="/auth/login",
            autoescape=None,
            debug=True
        )
        tornado.web.Application.__init__(self, handlers, **settings)

        # Have one global connection to the DB across all handlers
        self.db = tornado.database.Connection(
            host=options.mysql_host, database=options.mysql_database,
            user=options.mysql_user, password=options.mysql_password)


#运行程序
def main():
    tornado.options.parse_command_line()
    http_server = tornado.httpserver.HTTPServer(Application())
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()


if __name__ == "__main__":
    main()
