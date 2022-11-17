import json
import random

import pymysql

db = pymysql.Connect(user='root', password='20010519Smxy', host="43.142.28.107", database="petsAdoption_pets",
                     port=3306, charset='utf8')
cursor = db.cursor()



color_list = ['红色','白色','紫色','黑色','绿色','蓝色']
maochang_list = ["长毛","短毛","无毛"]
chate_list = ['活泼',"安静",'急躁','外向','内向']

cursor1 = db.cursor()
cursor2 = db.cursor()
sql_findAll = "select id from t_pets_detail"
cursor1.execute(sql_findAll)
for i in range(702):
    detail_info = {"颜色": random.choice(color_list), "毛长": random.choice(maochang_list), "性格": random.choice(chate_list)}
    detail_info = json.dumps(detail_info, ensure_ascii=False)
    weight = random.uniform(0, 20)
    height = random.uniform(0, 50)
    pid = cursor1.fetchone()[0]
    sql = """ UPDATE t_pets_detail set detail_info = '%s',weight=%d,height=%d where id=%s""" %(detail_info,weight,height, pid)
    cursor2.execute(sql)
    print(pid)

db.commit()