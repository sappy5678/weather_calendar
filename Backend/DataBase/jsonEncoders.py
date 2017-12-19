import json

import datetime

import DataBase.DBModel as model


class RainEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, model.RainPercent):
            return {"startTime": o.starttime.strftime("%Y-%m-%d %H:%M:%S"),
                    "endTime": o.endtime.strftime("%Y-%m-%d %H:%M:%S"),
                    "location": o.location,
                    "value": o.value
                    }
        if isinstance(o, datetime.datetime):
            return o.strftime("%Y-%m-%d %H:%M:%S")


        return json.JSONEncoder.default(self, o)
