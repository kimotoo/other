import login
import spider

if __name__ == '__main__':


    with open("user.txt", 'r') as f:
        str = f.read()

    _str = str.split(',')

    uname = _str[0]
    pwrod = _str[-1]

    print(uname)
    print(pwrod)

    sess = login.get_session()
    sess.headers['Referer'] = 'https://api.weibo.com/oauth2/authorize?response_type=code' \
                              '&client_id=4083586895&redirect_uri=http%3A%2F%2Fwww.xunyee.cn' \
                              '%2Fopenid%2Fsina%2Ftoken.html&state=a%3A0%3A%7B%7D&scope=email' \
                              '%2Cdirect_messages_write%2Cdirect_messages_read%2Cinvitation_write' \
                              '%2Cfriendships_groups_read%2Cfriendships_groups_write%2Cstatuses_to_me_read'


    # 登录
    sess = login.login_post(uname, pwrod, sess)

    urls = spider.find_url(sess)

    i=0
    # 访问链接
    while(True):

        url = urls[i%len(urls)]
        try:
            print("正在访问：%s" % url)
            resp = sess.get(url)

        except ConnectionError:
            print("%s访问失败" % url)
            continue

        except KeyboardInterrupt:
            break

        i=i+1


















