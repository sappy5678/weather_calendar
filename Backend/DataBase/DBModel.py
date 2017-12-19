from weather_calendar import db


class Temperature(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    starttime = db.Column(db.TIMESTAMP, nullable=False)
    endtime = db.Column(db.TIMESTAMP, nullable=False)
    value = db.Column(db.Integer, nullable=False)
    location = db.Column(db.Text, nullable=False)

    def __repr__(self):
        return '< Temperature > in '


class RainPercent(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    starttime = db.Column(db.TIMESTAMP, nullable=False)
    endtime = db.Column(db.TIMESTAMP, nullable=False)
    value = db.Column(db.Integer, nullable=False)
    location = db.Column(db.Text, nullable=False)

    def __repr__(self):
        return '< Temperature > in '