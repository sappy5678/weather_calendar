import threading

import time

from datetime import datetime

from weather_calendar import db
from pyfcm import FCMNotification

api_key = "AAAANK4gBFI:APA91bG8kJiR9vI_7iGJ1C0VMlKXfAjaKKZDWspLSrknvKX3HichKvWPHk8JX-cXxOBRVzz0tN-6EYyo3rRyP2MBD2Ck9g88fhr3tXcMz7QlgiWXQXLTTAwBl2hUDDW7umvwOVo8th09"
push_service = FCMNotification(api_key=api_key)


class WeeklyAlert(threading.Thread):
    def __init__(self, users):
        threading.Thread.__init__(self)
        self.users = users

        pass

    def run(self):
        message_title = "下雨警報"
        while True:
            print("Start Push to user")
            message_body = "以下事件有可能下雨喔\n"
            # 查詢天氣
            d = db.session.execute("SELECT * FROM public.rain_percent ")
            rain = []
            for e in d:
                if dict(e.items())["value"] < 30:
                    continue
                rain.append(dict(e.items()))

            # 判斷行事曆
            for user, events in self.users.items():
                for event in events:
                    # if event["organizer"] != "sappy5678@gmail.com":
                    #     continue
                    for r in rain:
                        if (event["begin"] <= r["starttime"] <= event["end"]) \
                                or (event["begin"] <= r["endtime"] <= event["end"]) \
                                or (r["starttime"] <= event["begin"] <= r["endtime"]) \
                                or (r["starttime"] <= event["end"] <= r["endtime"]):
                            message_body += event["title"] + " , "
                            break

                    pass

                push_service.notify_single_device(user, message_title, message_body)
                # print("Push to " + user)
                print("Push to user" + message_body)

            time.sleep(60 * 60*6)

        pass
