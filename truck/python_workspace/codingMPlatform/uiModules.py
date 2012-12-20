#coding=utf-8
import tornado

__author__ = 'Ethanlam'

#UI_MODEL的定义

#地区信息
class AreaEntryModule(tornado.web.UIModule):
    def render(self, entry):
        return self.render_string("modules/area.html", entry=entry)


#城市信息
class CityEntryModule(tornado.web.UIModule):
    def render(self, entry):
        return self.render_string("modules/city.html", entry=entry)
  
  
  
#景点
class ViewspotEntryModule(tornado.web.UIModule):
    def render(self, entry):
        return self.render_string("modules/viewspot.html", entry=entry)    