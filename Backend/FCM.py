# Send to single device.
from pyfcm import FCMNotification

id = "c9l9U-Ouno8:APA91bFnGnXTuVnhR53A-kzKyjyu-eiZV-GX2fmj88n7USRovW3qF1vefABugVjN3Jud59AczXA5m6zvAWNM5uEEyDuMXDPAb1oe5CUIZDfTY8gN10nJmFnh3EZqtOWVJ6OBPL_KsJek"

api_key = "AAAANK4gBFI:APA91bG8kJiR9vI_7iGJ1C0VMlKXfAjaKKZDWspLSrknvKX3HichKvWPHk8JX-cXxOBRVzz0tN-6EYyo3rRyP2MBD2Ck9g88fhr3tXcMz7QlgiWXQXLTTAwBl2hUDDW7umvwOVo8th09"
push_service = FCMNotification(api_key=api_key)

message_title = "Uber update"
message_body = "Hi john, your customized news for today is ready"
push_service.notify_single_device(id, message_title, message_body)

