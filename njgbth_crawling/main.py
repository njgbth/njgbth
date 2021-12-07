import requests as requests
from bs4 import BeautifulSoup
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore


home_list = []
link = []
recipe_info = []
like = []
mat = []
material = []


def get_home_link():
    home_link = 'https://www.10000recipe.com/recipe/list.html?cat='
    res = requests.get(home_link)
    soup = BeautifulSoup(res.content, 'html.parser')

    temp_home = soup.select("ul.pagination > li > a")
    for j in range(len(temp_home)):
        next_home = 'https://www.10000recipe.com/' + temp_home[j].get('href')
        home_list.append(next_home)


def get_home_list():
    for go in home_list:
        res = requests.get(go)
        soup = BeautifulSoup(res.content, 'html.parser')

        temp_link = soup.select("div.common_sp_thumb > a.common_sp_link")
        for i in range(len(temp_link)):
            link_temp = 'https://www.10000recipe.com/' + temp_link[i].get('href')
            link.append(link_temp)


def get_info():
    for num in range(100):
        res = requests.get(link[num])
        soup = BeautifulSoup(res.content, 'html.parser')
        requests.get(link[num])
        time.sleep(0.1)
        recipe = soup.select(" div.view2_summary > h3")
        title = recipe[0].get_text()
        title = title.split('/')[0]
        print("레시피 명: " + recipe[0].get_text())
        temp = soup.select("div.ready_ingre3 > ul > li")
        temp2 = soup.select("a > li")
        temp3 = soup.select("a > li > span")
        source = []
        if len(temp) != 0:
            for i in range(len(temp)):
                tmp = temp[i].get_text()
                tmp3 = temp3[i].get_text()
                tmp = tmp[:(len(tmp) - (len(tmp3) + 57))]
                source.append(tmp)
                mat.append(tmp)

        if len(temp2) != 0:
            for i in range(len(temp2)):
                tmp2 = temp2[i].get_text()
                tmp3 = temp3[i].get_text()
                tmp2 = tmp2[:(len(tmp2) - (len(tmp3) + 58))]
                source.append(tmp2)
                mat.append(tmp2)

        if len(source) != 0:
            recipe_inf = link[num], title, source
            print(recipe_inf)
            recipe_info.append(recipe_inf)

    mats = set(mat)
    matss = list(mats)
    for i in range(len(matss)):
        material.append(matss[i])


def insert_data():
    # Firebase database 인증 및 앱 초기화
    # Use a service account
    cred = credentials.Certificate('finaldb-58b2c-firebase-adminsdk-l9wm9-59e2fcff29.json')
    firebase_admin.initialize_app(cred)
    db = firestore.client()

    # 선호도 insert
    for i in range(len(recipe_info)):
        doc_ref = db.collection(u'레시피db').document(u'레시피명').collection(recipe_info[i][1]).document(u'선호도')
        doc_ref.set({
            u'선호도': 0
        }, merge=True)

    # 링크 insert
    for i in range(len(recipe_info)):
        doc_ref = db.collection(u'레시피db').document(u'레시피명').collection(recipe_info[i][1]).document(u'링크')
        doc_ref.set({
            u'링크': recipe_info[i][0]
        }, merge=True)

# 레시피 이름 및 재료 insert
    for i in range(len(recipe_info)):
        print("inserting: " + recipe_info[i][1])
        doc_ref = db.collection(u'레시피db').document(u'레시피명').collection(recipe_info[i][1]).document(u'재료')
        for j in range(len(recipe_info[i][2])):
            doc_ref.set({
                recipe_info[i][2][j]: 0
            }, merge=True)

    for i in range(len(material)):
        print("inserting 재료: " + material[i])
        doc_ref = db.collection(u'재료db').document(u'재료명')
        doc_ref.set({
            material[i]: 0
        }, merge=True)



get_home_link()
get_home_list()
get_info()
insert_data()

print(material)


# 현재 recipe_info = [[이름,링크,[[재료],[재료],[재료]]], [이름,링크,[[재료],[재료],[재료]]], [이름,링크,[[재료],[재료],[재료]]]] 까지 성공