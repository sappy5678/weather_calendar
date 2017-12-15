from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import json
from DataBase.jsonEncoders import *

app = Flask(__name__)
app.config.from_pyfile("config.py")
db = SQLAlchemy(app)

from DataBase.DBModel import *
from DataBase.crawler import WeeklyWeatherCrawler

db.create_all()


@app.route('/')
def hello_world():
    return '<(_ _)> Yeeeeee大大好神<(_ _)>!!!' \
           '<br> <(_ _)> yeee大大千秋萬載 一統江湖 <(_ _)>' \
           '<br><(_ _)> yeeee大大萬歲萬歲萬萬歲 <(_ _)>' \
           '<br><(_ _)> 感謝 yeeee 大大的凱瑞 <(_ _)>' \
           '<br><(_ _)> 祈求大大能祝福我早日脫魯 <(_ _)>'


@app.route('/api/v1/rain/<date>/<location>')
def rain(date, location):
    d = db.session.execute("SELECT * FROM public.rain_percent " +
                              "WHERE (starttime::date=date :d or endtime::date = date :d) " +
                                    " AND location = :l ;"
                              ,

                           {
                               "d": date,
                               "l": location
                           }
                           )
    r = []
    for e in d:
        r.append(dict(e.items()))
    d = json.dumps(r, cls=RainEncoder, ensure_ascii=False).encode('utf8')
    return d

@app.route('/api/v1/temperature/<date>/<location>')
def temperature(date, location):
    d = db.session.execute("SELECT * FROM public.temperature " +
                              "WHERE (starttime::date=date :d or endtime::date = date :d) " +
                                    " AND location = :l ;"
                              ,

                           {
                               "d": date,
                               "l": location
                           }
                           )
    r = []
    for e in d:
        r.append(dict(e.items()))
    d = json.dumps(r, cls=RainEncoder, ensure_ascii=False).encode('utf8')
    return d


if __name__ == '__main__':
    WeeklyWeatherCrawler().start()


    app.run(host='0.0.0.0', port='5000')
