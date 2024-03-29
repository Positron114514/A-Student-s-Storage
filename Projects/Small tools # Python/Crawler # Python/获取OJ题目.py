import asyncio
import bs4
import pyppeteer as pyp
import requests
import time
import getpass
import re


async def anti_anti_crawler(page):
    await page.setUserAgent(
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36")
    await page.evaluateOnNewDocument('() => { Object.defineProperty(navigator, {webdriver:{ get: () => false } }) }')


async def make_session(page):
    cookies = await page.cookies()
    cookies1 = {}
    for cookie in cookies:  # requests中的cookies只要"name"属性
        cookies1[cookie['name']] = cookie['value']
    session = requests.Session()
    session.cookies.update(cookies1)
    return session


def get_html_through_session(session, url):
    fake_headers = {
        "User-Agent": "Mozilla / 5.0(Windows NT 10.0; Win64; x64) AppleWebKit / 537.36(KHTML, like Gecko) Chrome / 80.0.3987.122  Safari / 537.36",
        "Accept": "text/html,application/xhtml+xml,*/*;q=0.9,*/*;q=0"
    }

    try:
        result = session.get(url, headers=fake_headers)
        result.encoding = result.apparent_encoding
        return result.text
    except Exception as e:
        print(e)
        return ""


async def get_oj_questions(login_url, nju_code, secret_code):
    width, height = 1400, 800
    # open a browser
    browser = await pyp.launch(headless=False, args=[f'--window-size={width},{height}'])
    # open new page and anti-anti-crawler it
    page = await browser.newPage()
    await anti_anti_crawler(page)
    # set page place
    await page.setViewport({'width': width, 'height': height})
    # load login url
    await page.goto(login_url)

    await page.waitForNavigation()
    # auto login service
    # enter zhanghao and secret code
    element = await page.querySelector("body > app-root > auth-login > div.form-wrapper > div.input-wrapper > form > div:nth-child(1) > input")
    await element.type(nju_code)
    element = await page.querySelector('body > app-root > auth-login > div.form-wrapper > div.input-wrapper > form > div:nth-child(2) > input')
    await element.type(secret_code)
    # click the button
    element = await page.querySelector('body > app-root > auth-login > div.form-wrapper > div.input-wrapper > form > div.action')
    await element.click()
    # wait for login movement
    await page.waitForSelector('body > app-root > app-welcome-page > div > div > div.col.col-12.col-xl-4 > app-welcome-contests > div:nth-child(2) > div > p.mb-2 > a > span',
                               timeout=10000)

    # go to lists
    # Happy_Winter_Vacation
    element = await page.querySelector('body > app-root > app-welcome-page > div > div > div.col.col-12.col-xl-4 > app-welcome-contests > div:nth-child(4) > div > p.mb-2 > a > span')
    await element.click()
    await page.waitForNavigation()
    time.sleep(2)
    # select all questions
    text = await page.content()
    # print(text)
    soup = bs4.BeautifulSoup(text, 'html.parser')
    # print(soup)

    # get all links to the question and question names
    body = soup.find('tbody')
    links = []
    for texts in body.find_all("tr"):
        # print(texts)
        question = {}
        td = texts.find("td")
        href = re.findall(r'/contest/\d+/problem/[^"]*', str(td))
        content = td.text
        question['name'] = content
        question['url'] = "https://public.oj.cpl.icu" + href[0]
        links.append(question)

    # get contents
    new_page = await browser.newPage()
    await anti_anti_crawler(new_page)
    for element in links:
        question_content = ""
        url = element['url']
        await new_page.goto(url, {'waitUntil' : ['load', 'domcontentloaded', 'networkidle0', 'networkidle2']})
        html = await new_page.content()
        soup = bs4.BeautifulSoup(html, 'html.parser')
        mds = soup.find_all('markdown')
        # print("mds =", mds)

        md_text = []
        for md in mds:
            if md.text is not None:
                md_text.append(md.text)
        for md in md_text:
            question_content = question_content + md

        element['content'] = question_content

    return links


async def save_as_txt(links, file_link):
    print("saving...")
    try:
        f = open(file_link, 'w', encoding='utf-8')
        i = 1
        for link in links:
            f.write("题目" + str(i) + ": " + link['name'] + "\n")
            f.write(link['content'] + "\n\n")
            i += 1

        print("saved successfully")
    except Exception as e:
        print(e)
        exit(1)
    finally:
        f.close()


async def main():
    login_url = "https://public.oj.cpl.icu/auth/login"
    nju_code = str(input("Enter your NJU id:"))
    password = getpass.getpass("Enter your password: ")

    oj_questions = await get_oj_questions(login_url, nju_code, password)
    await save_as_txt(oj_questions, './爬虫训练/OJ题目列表 HappyWinterVacation.txt')


if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())