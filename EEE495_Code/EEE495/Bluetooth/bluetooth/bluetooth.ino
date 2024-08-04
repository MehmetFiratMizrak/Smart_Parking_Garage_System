#include <SoftwareSerial.h>

#define RxD 7  //HC-05  Tx
#define TxD 8  //HC-05  Rx

SoftwareSerial blueToothSerial(RxD,TxD); 
 
void setup()

{
   Serial.begin(9600);
   blueToothSerial.begin(9600);  
   pinMode(RxD, INPUT);
   pinMode(TxD, OUTPUT);
 } 
 
void loop() {  
  
  
  if (blueToothSerial.available()) 
  { 
    //Serial.print("2");
    int inByte = blueToothSerial.read();
    Serial.write((char)inByte); 
    //Serial.write("2");

   }
  if (Serial.available()) 
  { 
    //Serial.print("2");
    int inByte = Serial.read();
    blueToothSerial.print((char)inByte);
    //blueToothSerial.print("2");
  }
}