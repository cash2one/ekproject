__author__ = 'Ethanlam'

import tornado.ioloop
import tornado.web

class MainHandlerb(tornado.web.RequestHandler):
    def get(self):
        name = self.get_argument("name")
        print 'request argument :%s' % name
        self.write("CodeMainHandler:Hello, world....")


