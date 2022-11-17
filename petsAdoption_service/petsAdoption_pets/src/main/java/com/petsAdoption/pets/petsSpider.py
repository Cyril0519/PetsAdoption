import datetime
import json
import random

import requests
from lxml import etree
import pymysql
from IDWorker import Snow

class PetsSpider:

    def __init__(self,url):
        self.url = url

    # url = 'http://maomijiaoyi.com/index.php?/chanpinliebiao_c_2_1--24.html'

    header = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                      "(KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.63",
       }

    db = pymysql.Connect(user='root',password='20010519Smxy',host="43.142.28.107", database="petsAdoption_pets",port=3306, charset='utf8')

    cursor = db.cursor()

    def get_content_list(self):
        res = requests.get(self.url, headers=self.header).text
        tree = etree.HTML(res)
        content_list = tree.xpath('//*[@id="content"]/div[3]/div/div/a')
        return content_list

    def get_content_from_list(self):
        content_list = self.get_content_list()
        for content in content_list:
            img = "http://maomijiaoyi.com/" + content.xpath('./div/img/@src')[0]     # 图片地址
            content_link = "http://maomijiaoyi.com" + content.xpath("./@href")[0]   # 内容详情url
            content_detail = requests.get(content_link,headers=self.header).text     # 内容详情
            content_tree = etree.HTML(content_detail)
            num = content_tree.xpath('//*[@id="content"]/div[3]/div[1]/div[2]/div[3]/div[1]/div[2]/text()')[0].replace("只","")  # 数量
            age = content_tree.xpath('//*[@id="content"]/div[3]/div[1]/div[2]/div[3]/div[2]/div[2]/text()')[0]  # 年龄
            category = content_tree.xpath('//*[@id="content"]/div[3]/div[1]/div[2]/div[3]/div[3]/div[2]/text()')[0] #种类
            title = content_tree.xpath('//*[@id="content"]/div[3]/div[1]/div[2]/div[1]/text()')[0].replace("出售","").strip () # 标题
            cat_desc_row = content_tree.xpath('//*[@id="post_neirong"]//text()')
            cat_desc = [x.strip() for x in cat_desc_row if x.strip() != '']
            cat_desc = ''.join(cat_desc)
            weight = random.uniform(0, 20)
            height = random.uniform(0, 50)

            color_list = ['红色','白色','紫色','黑色','绿色','蓝色']
            maochang_list = ["长毛","短毛","无毛"]
            chate_list = ['活泼',"安静",'急躁','外向','内向']

            detail_info = {"颜色": random.choice(color_list), "毛长": random.choice(maochang_list) ,"性格":random.choice(chate_list)}
            detail_info = json.dumps(detail_info,ensure_ascii=False)

            pets_id = Snow.get_guid()
            print(title)
            sql = '''INSERT INTO t_pets_detail(id,pets_id,number,image,images,post_time,cat_desc,title,weight,height,detail_info) 
                        VALUE ("%s","%s","%s","%s","%s",'%s','%s',"%s","%.3f","%.3f",'%s')''' \
                  % (pets_id,'9',num, img, img, datetime.datetime.now(),cat_desc,title,weight,height,detail_info)
            try:
                self.cursor.execute(sql)
                self.db.commit()
            except Exception as e:
                print(e)
                self.db.rollback()

if __name__ == '__main__':
    # 爬取信息
    # for i in range(5,100):
    #     url = "http://maomijiaoyi.com/index.php?/chanpinliebiao_pinzhong_42_%s--24.html" % i
    #     # url = 'http://maomijiaoyi.com/index.php?/chanpinliebiao_c_2_%s--24.html' %i
    #     pet = PetsSpider(url)
    #     pet.get_content_from_list()

    # 修改宠物的uid
    owner_id_list = [1566075818025795586,1566745963266080770,1567356366383845377,1567357627447480321,1567887450146144257,1569975457493938178]

    db = pymysql.Connect(user='root', password='20010519Smxy', host="43.142.28.107", database="petsAdoption_pets",
                         port=3306, charset='utf8')
    cursor1 = db.cursor()
    cursor2 = db.cursor()
    sql_findAll = "select id from t_pets_detail"
    cursor1.execute(sql_findAll)
    for i in range(702):
        pid = cursor1.fetchone()[0]
        sql = """update t_pets_detail set owner_id = %s where id=%s""" % (random.choice(owner_id_list),pid)
        cursor2.execute(sql)
        db.commit()
        print(pid)