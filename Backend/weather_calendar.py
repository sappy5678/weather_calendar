from flask import Flask

app = Flask(__name__)


@app.route('/')
def hello_world():
    return '<(_ _)> Yeeeeee大大好神<(_ _)>!!!' \
           '<br> <(_ _)> yeee大大千秋萬載 一統江湖 <(_ _)>' \
           '<br><(_ _)> yeeee大大萬歲萬歲萬萬歲 <(_ _)>' \
           '<br><(_ _)> 感謝 yeeee 大大的凱瑞 <(_ _)>' \
           '<br><(_ _)> 祈求大大能祝福我早日脫魯 <(_ _)>>'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)
