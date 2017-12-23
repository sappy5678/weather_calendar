// import * as admin from "firebase-admin";

var admin = require("firebase-admin");

var serviceAccount = require("../serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://clean-healer-189213.firebaseio.com/"
});

// See the "Managing device groups" link above on how to generate a
// notification key.
var notificationKey = "AIzaSyDBz0KYDpi4doN_6msToOnmTjcmoTFR5CY ";

// See the "Defining the message payload" section below for details
// on how to define a message payload.
var payload = {
  data: {
    score: "850",
    time: "2:45"
  },
  notification: {
    title: "GG up 1.43% on the day",
    body: "GG gained 11.80 points to close at 835.67, up 1.43% on the day."
  }

};

// Send a message to the device group corresponding to the provided
// notification key.
admin.messaging().sendToDeviceGroup(notificationKey, payload)
  .then(function(response) {
    // See the MessagingDeviceGroupResponse reference documentation for
    // the contents of response.
    console.log("Successfully sent message:", response);
  })
  .catch(function(error) {
    console.log("Error sending message:", error);
  });
