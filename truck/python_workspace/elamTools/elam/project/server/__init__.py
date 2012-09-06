from bson.code import Code

__author__ = 'Ethanlam'

import tornado.ioloop
import tornado.web
import tornado.database

from elam.project.server.CodeMainHandler import MainHandlerb;

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        name = self.get_argument("name")
        print 'request argument :%s' % name
        db = database.Connection("localhost", "mydatabase")
        self.write("--MainHandler Hello, world....")

application = tornado.web.Application([
    (r"/code",MainHandlerb),
    (r"/test",MainHandler),
])

if __name__ == "__main__":
    application.listen(80)
    tornado.ioloop.IOLoop.instance().start()


