import json
import random
import string

import pymysql
import requests
from lxml import etree

headers = {
    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36"
}
db = pymysql.Connect(user='root', password='20010519Smxy', host="43.142.28.107", database="petsAdoption_pets",
                         port=3306, charset='utf8')
cursor = db.cursor()

def get_kind():
    res = requests.get("https://www.ixiupet.com/pinzhong/",headers=headers).text
    tree = etree.HTML(res)
    kinds = tree.xpath("/html/body/div[3]/div/div/ul/li")
    for kind in kinds:
        link = kind.xpath("./a/@href")[0]
        print(link)
        yield link


def get_category(link, size):
    response = requests.get(link,headers=headers)
    res = response.text
    tree = etree.HTML(res)
    title = tree.xpath('/html/body/div[5]/div[2]/div[1]/h1/a/text()')[0]
    print(title)

    last_page = tree.xpath('/html/body/div[5]/div[2]/div[2]/div/a[last()]/@href')[0]
    a = last_page.split(".")[0]
    a = a.split("_")[-1]
    total = int(a)

    for i in range(1,total+1):
        cate_link = link + 'list_%s_%s.html' % (size,i)
        cate_response = requests.get(cate_link, headers=headers)
        cate_response.encoding = 'gb2312'
        cate_res = cate_response.text
        cate_tree = etree.HTML(cate_res)
        category_list = cate_tree.xpath('/html/body/div[5]/div[2]/div[2]/ul/li')
        for category_list_i in category_list:
            cate = category_list_i.xpath("./p/a/text()")[0]
            write_into_db(title,cate)



def write_into_db(kind:string, category):
    sql = """ insert into t_pets values(null,'%s','%s',1) """ %(kind.replace("品种",""),category)
    cursor.execute(sql)
    print("kind:%s,category:%s" %(kind.replace("品种","") ,category))
    db.commit()

link_list = get_kind()
page_size = 8
for link in link_list:
    get_category(link, page_size)
    page_size += 1
