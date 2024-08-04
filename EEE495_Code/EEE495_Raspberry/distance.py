
import Sensor
from Sensor import *
import Servo
from Servo import *
import plateRecognition
from plateRecognition import *
import Camera
from Camera import *
import re

import pyrebase

firebaseConfig = {"apiKey": "AIzaSyAB7lvM3qimnWVE2lKfb8wEvuhgnt4vdkc",
  "authDomain": "login-firebase-register-4c88e.firebaseapp.com",
  "projectId": "login-firebase-register-4c88e",
  "storageBucket": "login-firebase-register-4c88e.appspot.com",
  "messagingSenderId": "387266784860",
  "appId": "1:387266784860:web:862b0b30b14acf03b84099"}

firebase = pyrebase.initialize_app(firebaseConfig)
db = firebase.database()
data = "Hello"
db.push(data)
print("Done")

GPIO.setmode(GPIO.BCM)
registeredPlates = ["38 AH 393\x0c","38 AH 393\n\x0c","38 SU 784","38 SU 784.","38 SU 784 ."]

fire_result = 8 # Fire bilgisi bu degiskende tutalacak
result_1 = 0 # 1. otopark bilgisi
result_2 = 2 # 2. otopark bilgisi
result_3 = 4 # 3.otopark bilgisi
result_4 = 6 # 4.otopark bilgisi


while True:

  if Sensor.measuring_Distance() > 4 and Sensor.measuring_Distance() < 15:
    Camera.taking_Pictures()
    resultPlates = plateRecognition.reading_Plate()
    print(resultPlates)
    a = list(set(registeredPlates) & set(resultPlates))
    if len(a) != 0 :
      Servo.opening_Gate()
    else:
      print("Not defined Plate")
  
  if fire_result == 8:
    print("")
    # Send "There is a Fire" to the firebase and mobile app gets the message from firebase and show it in the app
  
  if result_1 == 1:
    # 1. otopark boş demek buraya yönlendircek. 
    # 1. otopark yol tarifini firebase'e yolla oradan app'e yön bilgisi olarak ver
    print("")
  elif result_2 == 1:
    # 2. otopark boş demek buraya yönlendircek. 
    # 2. otopark yol tarifini firebase'e yolla oradan app'e yön bilgisi olarak ver
    print("")
  elif result_3 == 1:
    # 3. otopark boş demek buraya yönlendircek. 
    # 3. otopark yol tarifini firebase'e yolla oradan app'e yön bilgisi olarak ver
    print("")
  elif result_4 == 1:
    # 4. otopark boş demek buraya yönlendircek. 
    # 4. otopark yol tarifini firebase'e yolla oradan app'e yön bilgisi olarak ver
    print("")
  else:
    # Otoparkların hepsi dolu demek
    # Otopark dolu diye firebase'e mesaj gidecek o da appe gidecek.
    print("")



    
  

  

    

    
        
    


    
    




    
  
    

  
  
  

  

  

  
  

  
      
       
       
  
    
    






    


  

