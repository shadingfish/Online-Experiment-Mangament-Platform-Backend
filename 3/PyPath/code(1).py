import requests
from bs4 import BeautifulSoup
import urllib.parse

def get_all_links(url):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    links = [a['href'] for a in soup.find_all('a', href=True)]
    return [urllib.parse.urljoin(url, link) for link in links]

url = "https://www.jd.com/"
links = get_all_links(url)

for link in links:
    print(link)
