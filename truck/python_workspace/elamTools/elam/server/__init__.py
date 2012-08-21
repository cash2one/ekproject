__author__ = 'Ethanlam'


import tornado.web
import tornado.ioloop
from web.TestRequest import TestHandler


class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("Hello, world")


application = tornado.web.Application([
    (r"/", MainHandler),
    (r"/t",TestHandler),
])


if __name__ == "__main__":
    application.listen(80)
    tornado.ioloop.IOLoop.instance().start()
