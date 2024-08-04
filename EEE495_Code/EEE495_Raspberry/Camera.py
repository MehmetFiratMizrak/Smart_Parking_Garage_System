from picamera import PiCamera
from time import sleep
from PIL import Image
camera = PiCamera()

def taking_Pictures():
    camera.start_preview()
    for i in range(3):
        sleep(3)
        camera.capture('/home/raspberry2/Desktop/Images/TESTIMAGE%s.jpg' % i)
    camera.stop_preview()
taking_Pictures()