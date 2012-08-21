__author__ = 'Ethanlam'

import tornado.web
import tornado.ioloop

class TestHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("TestHandler Hello, world")

