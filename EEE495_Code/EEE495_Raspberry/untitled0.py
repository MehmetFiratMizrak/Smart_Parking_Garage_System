#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed May 10 12:24:22 2023

@author: raspberry2
"""

#import PySerial module  
#wait for data from serial port and print on console
#Context: Raspberry Pi
 
import serial  
#assign serial port address  
port = "/dev/rfcomm0"   
   
#open serial port at 9600 baud rate and create serial communication object  
serialData = serial.Serial(port,9600) 
a=[] 
#main function  
def run(a):  
   
    while True:  
   
    #wait for incoming data from the serial port  
      if (serialData.inWaiting() > 0):  
   
         #read incoming data  
         input1 = serialData.read()
         # input1 = int.from_bytes(input1,"big")
         input1 = input1.decode()
         print("mesaj girdi")
         print(type(input1))
         # data = b'\xf9'
         # print()
         #print incoming data  
         print (input1)
         a.append(input1)
         while ("\n" in a):
             a.remove("\n")
         
            
        
run(a)