#coding=utf-8
# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/topics/items.html

from scrapy.item import Item, Field



#地区信息
class AreaItem(Item):
    id = Field()
    name = Field()
    description = Field()
    link_url = Field()

#地市信息
class CityItem(Item):
    id = Field()
    name = Field()
    description = Field()
    area_id = Field()
    link_url = Field()


#景点信息
class JingdianItem(Item):
    id = Field()
    name = Field()
    link_url = Field()
    description = Field()
    city_id = Field()
    type = Field()
    profile = Field()




    