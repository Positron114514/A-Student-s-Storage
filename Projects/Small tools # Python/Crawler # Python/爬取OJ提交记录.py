import pyppeteer as pyp
import asyncio
import bs4
import xlwt
import getpass
import time


async def anti_anti_crawler(page):
    await page.setUserAgent(
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36")
    await page.evaluateOnNewDocument('() => { Object.defineProperty(navigator, {webdriver:{ get: () => false } }) }')


async def get_oj_submit_list(login_url, nju_code, secret_code):
    oj_submit_list = []
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
    # 2023-project
    element = await page.querySelector('body > app-root > app-welcome-page > div > div > div.col.col-12.col-xl-4 > app-welcome-contests > div:nth-child(2) > div > p.mb-2 > a > span')
    await element.click()
    await page.waitForNavigation()
    # 评测情况
    element = await page.querySelector('body > app-root > app-contest-view > app-header-contest > header > nav > div > div > ul > li:nth-child(2) > a > span')
    await element.click()
    await page.waitForNavigation()

    time.sleep(5)
    # 读取界面数据
    text = await page.content()
    soup = bs4.BeautifulSoup(text, 'html.parser')

    # 分析界面
    lists = soup.find_all('tr')
    # print(lists)
    for list in lists:
        person = []
        number = list.find('th').text
        # print("number =", number)
        person.append(number)

        for elements in list.find_all('td'):
            target = elements.text
            # print("target =", target)
            person.append(target)

        oj_submit_list.append(person)

    oj_submit_list.remove(["#"])
    return oj_submit_list


def save_as_excel(data_list, save_path, file_name='oj_submit'):
    print("saving...")
    time_start = time.time()
    book = xlwt.Workbook(encoding="utf-8", style_compression=0)  # 创建workbook对象
    sheet = book.add_sheet(file_name, cell_overwrite_ok=True)  # 创建工作表
    col = ('编号', '选手', '题目', '评测结果', '得分', '样例', '运行时间', '运行内存', '编程语言', '字节数', '提交时间', '自定测试')
    for i in range(0, len(col)):
        sheet.write(0, i, col[i])  # 列名
    for i in range(0, len(data_list)):
        # print("第%d条" %(i+1))       #输出语句，用来测试
        data = data_list[i]
        for j in range(0, len(col)):
            sheet.write(i + 1, j, data[j])  # 数据
    book.save(save_path)  # 保存
    time_end = time.time()
    print("saved successfully in", str(time_end - time_start), "seconds")


async def main():
    login_url = "https://public.oj.cpl.icu/auth/login"
    nju_code = str(input("Enter your NJU id:"))
    password = getpass.getpass("Enter your password: ")

    oj_submit_list = await get_oj_submit_list(login_url, nju_code, password)
    time_now = time.asctime()
    time_now = time_now.replace(":", ".")
    file_name = "OJ提交记录" + time_now
    save_as_excel(oj_submit_list, './爬虫训练/' + file_name + '.xls')


if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())