from bs4 import BeautifulSoup

def find_url(session):

    re_url = "http://www.xunyee.cn/brandstar.html"
    page_get = session.get(re_url)
    page = page_get.text
    soup = BeautifulSoup(page, 'html.parser')
    tbody = soup.find('tbody')

    # print(tbody)

    list = tbody.find_all(class_='brandstar_p_small brandstar_p_rank')
    list_all = tbody.find_all('tr')

    # print(list)
    i = 0
    for i in range(len(list)):

        str = list[i].get_text()
        if (str.find('杨幂') != -1):
            break

    ym_tr = list_all[i]

    print(ym_tr)

    url_list = []

    for tem in ym_tr.find_all('a'):
        print(tem)
        url = tem.get('href')
        print(url)
        url_list.append(url)

    with open("link.txt", 'w') as f:
        for link in url_list:
            f.write(link + '\n')
        f.close()


    return url_list