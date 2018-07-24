#encoding:utf-8

import login
import sender
import pickle
from keras.models import load_model
import time


MODEL_FILENAME = "captcha_model.hdf5"
MODEL_LABELS_FILENAME = "model_labels.dat"



if __name__ == '__main__':

    #url = "https://weibo.com/6456846508/GqnV3s4FL?from=page_1005056456846508_profile&wvr=6&mod=weibotime&type=comment"
    url = "https://weibo.com/5473085545/Gqn21cJwJ?from=page_1005055473085545_profile&wvr=6&mod=weibotime&type=comment#_rnd1531839476120"

    name1 = "18475547852"
    pw1 = "Qarsy4646"


    sess1 = login.get_session()



    with open(MODEL_LABELS_FILENAME, "rb") as f:
        lb = pickle.load(f)

    model = load_model(MODEL_FILENAME)

    login.login_post(name1,pw1, sess1, model, lb)

    print(sess1.headers)
    print(sess1.cookies)
    print(sess1.adapters)

    #code1 = 0

    #mid1 = sender.get_mid(url, sess1)




    #while(int(code1) != 100001):


        #code1 = int(sender.repost(sess1, mid1, url))
        #print("转发成功")
        #time.sleep(300)


    #if(int(code1) == 100001):
       #print("%s转发失败"%name1)





    #if(int(code1) == 100000):
        #print("%s转发成功"%name1)



    #mid2 = sender.get_mid(url, sess2)
    #code2 = sender.repost(sess2, mid2, url)



    #if(int(code2) == 100000):
        #print("%s转发成功"%name1)

    #if(int(code2) == 100001):
        #print("%s转发失败"%name1)





    #login_index = sess.get(loop_url) # 重定向

    #print(login_index.content)

