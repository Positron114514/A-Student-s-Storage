# -*- codeing = utf-8 -*-
from bs4 import BeautifulSoup  # 网页解析，获取数据
import re  # 正则表达式，进行文字匹配`
import urllib.request, urllib.error  # 制定URL，获取网页数据

find_link = re.compile(r'https://www.bilibili.com/video/\w+')
def askURL(url):
    head = {
        "User-Agent": "Mozilla / 5.0(Windows NT 10.0; Win64; x64) AppleWebKit / 537.36(KHTML, like Gecko) Chrome / 80.0.3987.122  Safari / 537.36"
    }

    request = urllib.request.Request(url, headers=head)
    html = ""
    try:
        response = urllib.request.urlopen(request)
        html = response.read().decode("utf-8")
    except urllib.error.URLError as e:
        if hasattr(e, "code"):
            print(e.code)
        if hasattr(e, "reason"):
            print(e.reason)
    # print("ok")
    return html


def main():
    base_url = "https://www.bilibili.com/"
    print("Analyzing...")
    html = askURL(base_url)
    soup = BeautifulSoup(html, "html.parser")
    ls = soup.find_all('div')
    item_set = set()
    for item in ls:
        item = str(item)
        link = re.findall(find_link, item)
        for i in link:
            item_set.add(i)

    for link in item_set:
        print(link)

    print("Finished")


if __name__ == "__main__":
    main()