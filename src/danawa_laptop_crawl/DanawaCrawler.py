import re
import json
import requests as rq
import time
from bs4 import BeautifulSoup


User_Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36"
def isNotEmpty(string):
    return string and string.strip()

def getList(page):
    list = []
    headers = {"Referer": "http://prod.danawa.com/", "User-Agent": User_Agent}
    params = {
        "page": page,
        "priceRangeMinPrice": "",
        "priceRangeMaxPrice": "",
        "btnAllOptUse": "true",
        "searchAttributeValueRep[]": "758|1208|39485|OR",
        "searchAttributeValue[]": "758|1208|39485|OR",
        "listCategoryCode": "758",
        "categoryCode": "758",
        "physicsCate1": "860",
        "physicsCate2": "869",
        "physicsCate3": "0",
        "physicsCate4": "0",
        "viewMethod": "LIST",
        "sortMethod": "BEST",
        "listCount": "30",
        "group": "11",
        "depth": "2",
        "brandName": "",
        "makerName": "",
        "searchOptionName": "758|1208|8GB|OR",
        "sDiscountProductRate": "0",
        "sInitialPriceDisplay": "N",
        "sPowerLinkKeyword": "노트북",
        "oCurrentCategoryCode": "a:2:{i:1;i:96;i:2;i:758;}",
        "innerSearchKeyword": "",
        "listPackageType": "1",
        "categoryMappingCode": "710",
        "priceUnit": "0",
        "priceUnitValue": "0",
        "priceUnitClass": "",
        "cmRecommendSort": "N",
        "cmRecommendSortDefault": "N",
        "bundleImagePreview": "N",
        "nPackageLimit": "5",
        "nPriceUnit": "0",
        "bMakerDisplayYN": "Y",
        "isDpgZoneUICategory": "N",
        "isAssemblyGalleryCategory": "N",
        "sProductListApi": "search"}

    res = rq.post("http://prod.danawa.com/list/ajax/getProductList.ajax.php", headers=headers, data=params)
    soup = BeautifulSoup(res.text, "html.parser")
    list_product = soup.select("div.main_prodlist > ul.product_list > li.prod_item")
    for item in list_product:
        url = item.select(".prod_name > a")[0]['href']
        items = {**getBrand(url), **getInfo(url)}
        items["minPrice"] = int(item.select(".price_sect strong")[0].getText().replace(",", ""))
        temp = {}
        temp[(items["product_code"].replace(" ", "_") + "_" + str(items["ram_size"]))] = items
        list.append(temp)

        print(json.dumps(items, indent=4, sort_keys=True))

    return list

#상품 정보1
def getBrand(url):
    headers = {"User-Agent": User_Agent}

    res = rq.post(url, headers=headers)
    soup = BeautifulSoup(res.text, "html.parser")
    scripts = soup.find_all("script")

    p = re.compile("var oProductDescriptionInfo = (.*?);")
    for i in range(len(scripts)):
        code = scripts[i].string
        #print("==== "+str(i)+" ====")
        #print(code)

        if isNotEmpty(code):
            match = p.findall(code)
            if len(match) > 0:
                data = json.loads(match[0])
                #print(data)
                return {
                    "manufacture": data["makerName"],
                    "brand": data["brandName"]+" "+data["productName"],
                    "year": data["displayMakeDate"],
                    "product_code": data["productName"]
                }

#상품 정보2
def getInfo(url):
    headers = {"Referer": url, "User-Agent": User_Agent}
    #카테고리 설정
    params = {
        "pcode": re.compile("pcode=(.*?)&").findall(url)[0],
        # 컴퓨터/노트북/조립PC
        "cate1": "860",
        # 노트북/태블릿PC
        "cate2": "869",
        # 노트북
        #"cate3": "10585",
        # (인치구분)
        #"cate4": "0"
    }

    res = rq.post("http://prod.danawa.com/info/ajax/getProductDescription.ajax.php", headers=headers, data=params)
    soup = BeautifulSoup(res.text, "html.parser")
    list_product = soup.select("tbody > tr")

    list = {}
    for i in range(len(list_product)):
        item = list_product[i]
        key = item.select("th")
        value = item.select("td")
        for j in range(len(key)):
            header = key[j].getText();
            #print(header)
            if header == "CPU 넘버":
                list["cpu_type"] = getValue(value[j])
            elif header == "CPU 제조사":
                if getValue(value[j]) == "인텔": list["cpu_manu"] = "Intel"
                elif getValue(value[j]) == "퀄컴": list["cpu_manu"] = "Qualcomm"
                elif getValue(value[j]) == "AMD": list["cpu_manu"] = "AMD"
                else: list["cpu_manu"] = None
            elif header == "GPU 종류":
                    item1 = list_product[i + 1]
                    value1 = item1.select("td")

                    if isNotEmpty(getValue(value1[0])): 
                        list["gpu_manu"] = "NVIDIA"
                        list["gpu_type"] = getValue(value1[0])

                    elif isNotEmpty(getValue(value1[1])):
                        list["gpu_manu"] = "AMD"
                        list["gpu_type"] = getValue(value1[1])
                    elif isNotEmpty(getValue(value[1])):
                        list["gpu_manu"] = "Intel"
                        list["gpu_type"] = getValue(value[1])
                    else:
                        list["gpu_manu"] = "Qualcomm"
                        list["gpu_type"] = None

                    i += 2
            elif header == "화면 크기":
                list["display_size"] = float(re.compile("\((.*?)\)").findall(getValue(value[j]))[0].replace("인치", ""))
            elif header == "터치스크린":
                list["display_type"] = "true" if isNotEmpty(getValue(value[j])) else "false"
            elif header == "무게":
                list["weight"] = float(getValue(value[j]).replace("kg", "").replace("g", "").replace("1.3.", "1.3"))
            elif header == "메모리 용량":
                list["ram_size"] = int(getValue(value[j]).replace("GB", ""))
            elif header == "배터리":
                list["battery_size"] = -1 if not isNotEmpty(getValue(value[j])) else float(getValue(value[j]).replace("Wh", ""))
            elif header == "SSD 용량":
                list["storage_size"] = int(getValue(value[j]).replace("GB", ""))
            elif header == "운영체제":
                #작업필요
                OS = getValue(value[j])
                if OS == "리눅스": list["OS"] = 2
                elif "macOS" in OS: list["OS"] = 1
                elif "윈도우" in OS or "운영체제 선택" in OS: list["OS"] = 0
                else: list["OS"] = -1

    return list

def getValue(ResltSet):
    return ResltSet.getText()


data = []
start = time.time()  # 시작 시간 설정

#크롤러 시작
for i in range(0, 6):
    print("===", i+1, "페이지 ===")
    data += getList(i)


print("====================")
print("총 갯수:", len(data),"개")
print("총 시간:", time.time() - start,"초")

with open("danawa_laptop.json", 'w') as outfile:
    json.dump(data, outfile, indent=4, sort_keys=True)
