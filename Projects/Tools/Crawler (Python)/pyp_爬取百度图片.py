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