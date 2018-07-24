import login
import sender
import re
import time
import json



# json key： uname, pword, death, time, other
# 对应账号，密码，死号，时间戳，密码错误

str = "18475547852----Qarsy4646," \
      "13809224847----Pydmn9522," \
      "15899726233----Ifwpo0105," \
      "13719374004----Kgatz2318," \
      "15818491041----Bunrj0132," \
      "15820260069----Woqif6852," \
      "13924002255----Mvsqz1228," \
      "18318979697----Qvaef3842," \
      "18476180943----Uduvz7947," \
      "13798982710----Qgaao9471,"

list = []


def write(jsa):

    with open('user.json', 'w', encoding='utf-8') as f:
        json.dump(jsa, f)
        f.close()


def read():

    with open('user.json','r',encoding='utf-8') as f:
        jsa = json.load(f)
        f.close()

    return jsa


def update(jsa):

    for i in range(len(jsa)):

        if jsa[i]['death']:
            del jsa[i]


def insert(str, jsa):

    dict_list = str.split('----')
    dict = {}
    dict['uname'] = dict_list[0]
    dict['pword'] = dict_list[-1]
    dict['death'] = False
    dict['time'] = 0
    dict['other'] = True
    dict_str = json.dumps(dict)
    jsa.append(json.loads(dict_str))

    return jsa


def test(js):

    js['other'] = False




if __name__ == '__main__':

    jsa = read()
    insert("1----3", jsa)
    print(jsa[-1])

    test(jsa[-1])

    print(jsa[-1])







