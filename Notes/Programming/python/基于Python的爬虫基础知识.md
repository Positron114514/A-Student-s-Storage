#python #程序设计 #互联网 #爬虫

# 爬虫基础知识

> created by lxx11451 on 2024/1/30



## 一. HTML相关知识

pass





## 二. Python 中的爬虫相关库的应用



### 获取网页

#### 1. requests库

pass



#### 2. pyppeteer库

- 新兴库, 反反爬能力优秀
- 需要用到Chrominum
- 大部分函数都是携程

example :

```python
# pyppeteer爬取百度图片

import re
import pyppeteer as pyp
import asyncio


async def save_picture_from_url(url, browser, output_path='./爬虫训练/graph/screenshot.png'):
    page = await browser.newPage()

    try:
        await page.goto(url)
        await page.screenshot({'path': output_path})
        print(f'Screenshot saved to: {output_path}')
        await page.close()
    except Exception as e:
        print(f'Error: {e}')

async def get_content(url, browser):
    page = await browser.newPage()
    await page.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36")
    await page.evaluateOnNewDocument('() => { Object.defineProperty(navigator, {webdriver:{ get: () => false } }) }')
    await page.goto(url)
    text = await page.content()
    return text


async def get_baidu_picture(name, browser, number=10):
    url = "https://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&dyTabStr=MCwzLDEsMiw2LDUsNCw3LDgsOQ%3D%3D&word=" + name
    html = await get_content(url, browser)
    picture_url = re.findall(r'\"thumbURL\":.*?\"(.*?)\"', html)
    # print("ok")
    i = 1
    for urls in picture_url:
        try:
            await save_picture_from_url(urls, browser, "./爬虫训练/graph/" + name + str(i) + ".png")
            i += 1
        except Exception as e:
            print(f"Error processing image {urls}: {e}")

        if i > number:
            break


async def main():
    name = str(input("Enter the object you want to search: "))
    number = int(input("Enter the number of the picture you want: "))

    browser = await pyp.launch(headless=False)
    await get_baidu_picture(name, browser, number)
    await browser.close()


if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())
```

- 在`async`修饰函数中调用`async`函数 : 前加await修饰

- 在正常函数中调用 :

  ```py
  func = asyncio.ensure_future(function(url))
  asyncio.get_event_loop().run_until_complete(func)
  ```



### 分析网页

#### 1. 正则表达式 :

相关知识 : 见[[../Miscell/Regular Expressions|Regular Expressions]]


- **优点** : 速度最快
- **缺点** : 适应变化较差



#### 2. BeautifulSoup库

- **优点** : 能适应变化

`html` 中的 `tag` : 

```html
<X attr1='xxx' att2='yyy' ...>
    nnnnnn
</X>
```



##### **分析步骤** :

1. 将`html`装入一个`bs`对象`x`
2. 用`x` 对象的`find`, `find_all` 函数寻找想要的`tag`对象
3. 对找到的对象, 还可以继续重复步骤2寻找嵌套的对象
4. `tag`内部的`text`即为该对象里的正文, `tag`对象也可看做为字典, 内包含各种属性及其值		

​	

### 练习

#### 1. 爬取每日股票信息





## 三. 需要登陆的爬虫操作

