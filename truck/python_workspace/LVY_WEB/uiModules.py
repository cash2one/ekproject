#coding=utf-8
import tornado

__author__ = 'Ethanlam'


#地区信息
class AreaEntryModule(tornado.web.UIModule):
    def render(self, entry):
        return self.render_string("modules/area.html", entry=entry)