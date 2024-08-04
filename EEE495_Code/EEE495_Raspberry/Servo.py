import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
GPIO.setup(11,GPIO.OUT)
servo1 = GPIO.PWM(11,50)

#17.pin calismiyor

def opening_Gate():
    servo1.start(5)
    time.sleep(3)
    servo1.ChangeDutyCycle(10)
    time.sleep(1)
    servo1.stop()
    GPIO.cleanup()
opening_Gate()