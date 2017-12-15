import xml.etree.ElementTree as ET
import requests
# import DBModel.Temperature
from weather_calendar import db
from .DBModel import *
import DataBase.DBModel
import threading,time


class WeeklyWeatherCrawler(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        print("Get Start")
        pass

    def run(self):

        t = DataBase.DBModel.Temperature()
        while True:

            URI = "{urn:cwb:gov:tw:cwbcommon:0.1}"
            api = "http://opendata.cwb.gov.tw/govdownload?dataid=F-D0047-091&authorizationkey=rdec-key-123-45678-011121314"
            respond = requests.get(api)
            with open("w.xml",mode='w') as f:
                f.write(respond.text)
            tree = ET.ElementTree(file="./w.xml")
            root = tree.getroot()

            db.session.execute('''ALTER SEQUENCE temperature_id_seq RESTART WITH 1''')
            db.session.execute('''TRUNCATE TABLE temperature''')
            db.session.execute('''ALTER SEQUENCE rain_percent_id_seq RESTART WITH 1''')
            db.session.execute('''TRUNCATE TABLE rain_percent''')
            db.session.commit()

            for i in root.iter(URI + 'location'):

                # strip is remove space tab and newline
                location = i.find(URI + "locationName").text.strip()
                elements = i.findall(URI + "weatherElement")

                # insert weather element to each table
                for element in elements:
                    if element.find(URI + "elementName").text.strip() == "T":
                        for times in element.findall(URI + "time"):
                            t = DataBase.DBModel.Temperature()
                            t.location = location
                            t.starttime = times.find(URI + "startTime").text.strip()
                            t.endtime = times.find(URI + "endTime").text.strip()
                            t.value = times.find(URI + "elementValue").find(URI + "value").text.strip()
                            db.session.add(t)
                    if element.find(URI + "elementName").text.strip() == "PoP":
                        for times in element.findall(URI + "time"):
                            t = DataBase.DBModel.RainPercent()
                            t.location = location
                            t.starttime = times.find(URI + "startTime").text.strip()
                            t.endtime = times.find(URI + "endTime").text.strip()
                            t.value = times.find(URI + "elementValue").find(URI + "value").text.strip()
                            if t.value == '"' or t.value == "":
                                continue
                            db.session.add(t)

                # print(t.location)
                # t.starttime = "2017-12-09T12:00:00+08:00"
                # t.endtime = "2017-12-09T18:00:00+08:00"
                # t.temperature = 16
                # db.session.add(t)

            db.session.commit()
            print("All in")
            time.sleep(60*60*3)
