#encoding:utf-8
import requests
import json
import re
import urllib
import base64
import time
import rsa
import binascii
import math
import random
from PIL import Image
from io import BytesIO



# 初始post
post_data = {
    'entry': 'openapi',
    'gateway': '1',
    'from': '',
    'savestate': 0,
    'useticket': 1,
    'pagerefer': 'http://www.xunyee.cn/passport-login.html',
    'ct':1800,
    's':1800,
    'vsnal': '',
    'door':'',
    'appkey':'6Af379',
    'service': 'miniblog',
    'pwencode': 'rsa2',
    'sr': '1920*1080',
    'encoding': 'UTF-8',
    'cdult': 2,
    'domain': 'weibo.com',
    'prelt': '157',
    'returntype':'TEXT',
}


# 登陆入口url
#login_url = 'http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.18)'


def get_session():
    sess = requests.session()
    sess.headers['User-Agent'] = (
      'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
)
    return sess


# 获取经base64编码的用户名
def encode_name(username):

    username_urllike = urllib.request.quote(username)
    username_encrypted = base64.b64encode(bytes(username_urllike, encoding='utf-8'))
    return username_encrypted.decode('utf-8')


# 获取预登陆返回的信息
def get_prelogin_info(prelogin_url, session):
    json_pattern = r'.*?\((.*)\)'
    repose_str = session.get(prelogin_url).text
    m = re.match(json_pattern, repose_str)
    return json.loads(m.group(1))


# 获取加密后的密码
def encrypted_pw(password, data):
    rsa_e = 65537  # 0x10001
    pw_string = str(data['servertime']) + '\t' + str(data['nonce']) + '\n' + str(password)
    key = rsa.PublicKey(int(data['pubkey'], 16), rsa_e)
    pw_encypted = rsa.encrypt(pw_string.encode('utf-8'), key)
    password = ''
    passwd = binascii.b2a_hex(pw_encypted)
    return passwd

# 获取验证码url
def get_pincode_url(pcid):
    size = 0
    url = "http://login.sina.com.cn/cgi/pin.php"
    pincode_url = '{}?r={}&s={}&p={}'.format(url, math.floor(random.random() * 100000000), size, pcid)
    return pincode_url


# 获取验证码图片 pcid_url = "http://login.sina.com.cn/cgi/pin.php?r=40222527&s=0&p=1"
def get_pcid_image(pcid_url):

    resp = requests.get(pcid_url, stream=True)
    image = Image.open(BytesIO(resp.content))

    return image


# 登陆并获取重定向session
def login_post(uname, pwrod, session):

    #uname = js['uname']
    #pwrod = js['pwrod']

    print("%s正在登陆..."%uname)
    su = encode_name(uname)

    #https://login.sina.com.cn/sso/prelogin.php?entry=openapi&callback=sinaSSOController.preloginCallBack&
    # su=NDI1MzA2OTExJTQwcXEuY29t&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.18)&_=1531906890470

    prelogin_url = "https://login.sina.com.cn/sso/prelogin.php?entry=openapi&callback=sinaSSOController.preloginCallBack&" \
                   "su=" + su + "&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.18)"

    pre_obj = get_prelogin_info(prelogin_url, session)


    while(True):

        sp = encrypted_pw(pwrod, pre_obj)
        #print(pre_obj)

        post_data['su']=su
        post_data['servertime'] = pre_obj['servertime']
        post_data['rsakv'] = pre_obj['rsakv']
        post_data['sp'] = sp

        if ('showpin' in pre_obj):

            if(pre_obj['showpin'] == 1):

                pcid = pre_obj['pcid']
                img_url = get_pincode_url(pcid)
                image = get_pcid_image(img_url)
                image.save('1.png')

                # 验证码识别
                cap = input("输入验证码")

                post_data['pcid'] = pcid
                post_data['door'] = cap

        t = int(time.time()*1000)
        login_url = 'https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.18)&_=%s&openapilogin=qrcode' % t
        login_page = session.post(login_url, data=post_data).text

        pa_1 = r'"retcode":"(.*?)"'
        retcode = re.findall(pa_1, login_page)
        print(retcode)

        code = int(retcode[0])

        if (code == 0):
            return xunyee_login(session, login_page)

        if (code == 101):
            print("%s 密码错误" % uname)
            break

        else:
            pre_obj['showpin'] = 1
            print("验证码错误")
            # js['other'] = False



def xunyee_login(session, text):

        print(text)

        pa = r'"ticket":"(.*?)"'
        ticket = re.findall(pa, text)[0]
        print(ticket)

        data = {
            'action': 'login',
            'display': 'default',
            'withOfficalFlag': 0,
            'quick_auth': 'false',
            'withOfficalAccount': '',
            'scope': 'email,direct_messages_write,direct_messages_read,invitation_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read',
            'ticket': ticket,
            'isLoginSina': '',
            'response_type': 'code',
            'regCallback': 'https%3A%2F%2Fapi.weibo.com%2F2%2Foauth2%2Fauthorize%3Fclient_id%3D4083586895%26response_type%3Dcode%26display%3Ddefault%26redirect_uri%3Dhttp%253A%252F%252Fwww.xunyee.cn%252Fopenid%252Fsina%252Ftoken.html%26from%3D%26with_cookie%3D',
            'redirect_uri': 'http://www.xunyee.cn/openid/sina/token.html',
            'client_id': '4083586895',
            'appkey62': '6Af379',
            'state': 'a%3A0%3A%7B%7D',
            'verifyToken': 'null',
            'from': '',
            'switchLogin': 0,
            'userId': '',
            'passwd': '',
        }

        while (True):

            # 跳转
            sec_resp = session.post('https://api.weibo.com/oauth2/authorize', data)

            # 登录测试
            login_url = 'http://www.xunyee.cn/passport/is_login'
            islogin = session.get(login_url)
            print(islogin.text)
            res = re.findall('"res":"(.*?)"', islogin.text)
            if (len(res) != 0):
                if (res[0] == "succ"):
                    break


        print("登录成功")
        return session









    