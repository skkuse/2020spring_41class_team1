from bs4 import BeautifulSoup
from selenium import webdriver
import requests
import time

# look through sitemap and find urls of laptop from <loc> elements
r = requests.get("https://noteb.com/sitemap/sitemap.xml")
xml = r.text
soup = BeautifulSoup(xml, features="html.parser")
sitemapTags = soup.find_all("url")

# print("The number of sitemaps are {0}".format(len(sitemapTags)))

urls = []
for sitemap in sitemapTags:
    url = sitemap.findNext("loc").text
    if 'model_id' in url:
        urls.append(url)

# crawl pages ony by one using sitemap (urls)
# total 22
# -------------
# laptop_model
# min_price
# max_price
# min_battery_life
# max_battery_life
# cpu_model
# cpu_speed    (GHz)
# cpu_cores
# cpu_class
# gpu_model
# gpu_speed    (GHz)
# gpu_memspeed (GHz)
# gpu_memsize  (MB)
# gpu_class
# display_size
# display_hres
# display_vres
# display_touch
# storage_size
# storage_type
# mem_size
# os_model
# --------------

driver = webdriver.Chrome(executable_path=r"chromedriver_win32\chromedriver.exe")
f = open("model.txt", "w+")

count = 0
for url in urls:
    if count == 10000:
        break
    print(count)

    driver.get(url)
    time.sleep(3)  # to be not forbidden by server
                   # also, to avoid NAN value when page not fully loaded

    laptop_model = driver.find_elements_by_xpath('//*[(@id = "model_title")]')
    if len(laptop_model) != 0:
        e = laptop_model[0]
        f.write(e.text + '\n')
        print(e.text)
    else:
        print("no element")
        continue
    min_price = driver.find_element_by_xpath('//*[(@id = "config_price1")]')
    f.write(min_price.text + '\n')

    max_price = driver.find_element_by_xpath('//*[(@id = "config_price2")]')
    f.write(max_price.text + '\n')

    min_battery_life = driver.find_element_by_xpath('//*[(@id = "bat_life1")]')
    f.write(min_battery_life.text + '\n')

    max_battery_life = driver.find_element_by_xpath('//*[(@id = "bat_life2")]')
    f.write(max_battery_life.text + '\n')

    cpu_title = driver.find_element_by_xpath('//*[(@id = "cpu_title")]')
    f.write(cpu_title.text + '\n')

    cpu_speed = driver.find_element_by_xpath('//*[(@id = "cpu_turbo")]')
    f.write(cpu_speed.text + '\n')

    cpu_cores = driver.find_element_by_xpath('//*[(@id = "cpu_cores")]')
    f.write(cpu_cores.text + '\n')

    cpu_class = driver.find_element_by_xpath('//*[(@id = "cpu_class")]')
    f.write(cpu_class.text + '\n')

    gpu_model = driver.find_element_by_xpath('//*[(@id = "gpu_title")]')
    f.write(gpu_model.text + '\n')

    gpu_speed = driver.find_element_by_xpath('//*[(@id = "gpu_speed")]')
    gpu_speed = str(gpu_speed.text).split()[2]
    f.write(gpu_speed + '\n')

    gpu_memspeed = driver.find_element_by_xpath('//*[(@id = "gpu_memspeed")]')
    f.write(gpu_memspeed.text + '\n')

    gpu_memsize = driver.find_element_by_xpath('//*[(@id = "gpu_mem")]')
    gpu_memsize = str(gpu_memsize.text).split()[0]
    f.write(gpu_memsize + '\n')

    gpu_class = driver.find_element_by_xpath('//*[(@id = "gpu_class")]')
    f.write(gpu_class.text + '\n')

    display_size = driver.find_element_by_xpath('//*[(@id = "display_size")]')
    f.write(display_size.text + '\n')

    display_hres = driver.find_element_by_xpath('//*[(@id = "display_hres")]')
    f.write(display_hres.text + '\n')

    display_vres = driver.find_element_by_xpath('//*[(@id = "display_vres")]')
    f.write(display_vres.text + '\n')

    display_touch = driver.find_element_by_xpath('//*[(@id = "display_touch")]')
    f.write(display_touch.text + '\n')

    hdd_title = driver.find_element_by_xpath('//*[(@id = "hdd_title")]')
    hdd_title = str(hdd_title.text).split()
    storage_size = hdd_title[0].replace('GB', '')
    storage_type = hdd_title[1]
    f.write(storage_size + '\n')
    f.write(storage_type + '\n')

    mem_title = driver.find_element_by_xpath('//*[(@id = "mem_title")]')
    mem_size = str(mem_title.text).split()[0].replace('GB', '')
    f.write(mem_size + '\n')

    os_model = driver.find_element_by_xpath('//*[(@id = "sist_title")]')
    os_model = str(os_model.text).replace(', ', '')
    f.write(os_model + '\n')

    count = count + 1

f.close()
driver.close()


# another way to do
# model_info = driver.find_elements_by_xpath(
#         '//*[contains(concat( " ", @class, " " ), concat( " ", "headerComponents", " " ))] | //*[contains(concat( " ", @class, " " ), concat( " ", "rows", " " ))]')
#     if len(model_info) != 0:  # if there is model_info
#         num_elem = 0
#         for e in model_info:
#             if (not (len(e.text) == 0 or e.text.isspace())):  # remove whitespace
#                 num_elem = num_elem + 1
#                 if e.find_elements_by_tag_name('option'): # if there is select tag
#                     for option in e.find_elements_by_tag_name('option'):
#                         if option.get_attribute('selected'): # choose one with attribute selected='selected'
#                             text = str(option.text).replace('\U0001f44b', '')
#                             text = text.rstrip()
#                             f.write(text + '\n')
#                 else:
#                     f.write(e.text + '\n')
#         print(num_elem)
#     else:
#         print("no element")
#         print("no element")
#         continue
#     count = count + 1