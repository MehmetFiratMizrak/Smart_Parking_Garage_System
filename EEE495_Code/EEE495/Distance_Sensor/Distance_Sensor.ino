#include <SoftwareSerial.h>

#define trigger_1 2
#define echo_1 3

#define trigger_2 4
#define echo_2 5

#define trigger_3 6
#define echo_3 7

#define trigger_4 8
#define echo_4 9

#define RxD 0  //HC-05  Tx
#define TxD 1  //HC-05  Rx

uint16_t time_1;     
uint16_t distance_1;

uint16_t time_2;
uint16_t distance_2;

uint16_t time_3;
uint16_t distance_3;

uint16_t time_4;
uint16_t distance_4;

int result_1; // 1.Otopark durumunu gösteren variable
int result_2; // 1.Otopark durumunu gösteren variable
int result_3; // 1.Otopark durumunu gösteren variable
int result_4; // 1.Otopark durumunu gösteren variable
int fire_result; // Yangın durumunu gösteren variable

const int sensorMin = 0; 
const int sensorMax = 1024; 

unsigned long previous_time = 0;
unsigned long current_time = 0;
unsigned long seconds = 0;

SoftwareSerial blueToothSerial(RxD,TxD); 

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  blueToothSerial.begin(9600); 
  pinMode(RxD, INPUT);
  pinMode(TxD, OUTPUT); 
  
  pinMode(trigger_1,OUTPUT); 
  pinMode(echo_1,INPUT); 
  digitalWrite(trigger_1,LOW); 
  
  pinMode(trigger_2,OUTPUT);
  pinMode(echo_2,INPUT);
  digitalWrite(trigger_2,LOW);

  pinMode(trigger_3,OUTPUT);
  pinMode(echo_3,INPUT);
  digitalWrite(trigger_3,LOW);

  pinMode(trigger_4,OUTPUT);
  pinMode(echo_4,INPUT);
  digitalWrite(trigger_4,LOW);
}

void loop() 
{
  // put your main code here, to run repeatedly:
  digitalWrite(trigger_1,LOW);
  delayMicroseconds(2);
  digitalWrite(trigger_1,HIGH);
  delayMicroseconds(10);
  digitalWrite(trigger_1,LOW);

  time_1 = pulseIn(echo_1,HIGH); 
  distance_1 = time_1 * 0.034/2; 

  digitalWrite(trigger_2,LOW);
  delayMicroseconds(2);
  digitalWrite(trigger_2,HIGH);
  delayMicroseconds(10);
  digitalWrite(trigger_2,LOW);

  time_2 = pulseIn(echo_2,HIGH);
  distance_2 = time_2 * 0.034/2;

  digitalWrite(trigger_3,LOW);
  delayMicroseconds(2);
  digitalWrite(trigger_3,HIGH);
  delayMicroseconds(10);
  digitalWrite(trigger_3,LOW);

  time_3 = pulseIn(echo_3,HIGH);
  distance_3 = time_3 * 0.034/2;

  digitalWrite(trigger_4,LOW);
  delayMicroseconds(2);
  digitalWrite(trigger_4,HIGH);
  delayMicroseconds(10);
  digitalWrite(trigger_4,LOW);

  time_4 = pulseIn(echo_4,HIGH);
  distance_4 = time_4 * 0.034/2;
  

  
  // Serial.print("---");
  // Serial.print("Distance1= ");
  // Serial.print(distance_1);
  // Serial.print("cm");
  // Serial.print("---");
   Serial.print("Result1= ");
    Serial.print(result_1);
  
  // Serial.print("----");
  // Serial.print("");
 
  // Serial.print("Distance2= ");
  // Serial.print(distance_2);
  // Serial.print("cm");
  // Serial.print("---");
  Serial.print("Result2= ");
  
  Serial.print(result_2);
  
  // Serial.print("----");

  // Serial.print("Distance3= ");
  // Serial.print(distance_3);
  // Serial.print("cm");
  // Serial.print("---");
  Serial.print("Result3= ");
  
   
  
  Serial.print(result_3);
  
  // Serial.print("----");
  
  // Serial.print("Distance4= ");
  // Serial.print(distance_4);
  // Serial.print("cm");
  // Serial.print("---");
  Serial.print("Result4= ");
  
  Serial.print(result_4);
  
  // Serial.print("----");
  

  if(distance_1 >= 2 && distance_1 <= 12) // Otopark dolu ise
  {
    result_1 = 0;   
  }
  else
  {
    result_1 = 1;
  }

  if(distance_2 >= 2 && distance_2 <= 12)
  {
    result_2 = 2;   
  }
  else
  {
    result_2 = 3;
  }

  if(distance_3 >= 2 && distance_3 <= 12)
  {
    result_3 = 4;   
  }
  else
  {
    result_3 = 5;
  }

  if(distance_4 >= 2 && distance_4 <= 12)
  {
    result_4 = 6;   
  }
  else
  {
    result_4 = 7;
  }

  // Transfer edildiğinde 7 ile bolumunden kalana bakılıp spesifik otoparkın dolu ya da boş olduğu belirlenir. 

 // read the sensor on analog A0:
 int sensorReading = analogRead(A0);
 int range = map(sensorReading, sensorMin, sensorMax, 0, 3);
 
// range value:
 switch (range) {
 case 0: // A fire closer than 1.5 feet away.
 Serial.print("Close Fire");
 fire_result = 8;
//  Serial.print("---");
//   Serial.print("Fire Result: ");
//  Serial.print("---");
//  Serial.print(fire_result);
 
 break;
 case 1: // A fire between 1-3 feet away.
 Serial.println("Close Fire");
 fire_result = 8;
//  Serial.print("---");
//  Serial.print("Fire Result: ");
//  Serial.print("---");
//  Serial.print(fire_result);
 break;
 case 2: // No fire detected.
 Serial.println("No Fire");
 fire_result = 9;
//  Serial.print("---");
//  Serial.print("Fire Result: ");
//  Serial.print("---");
//  Serial.print(fire_result);
 break;
 }
 delay(1); // delay between reads
 
// current_time = millis();
//   if((current_time - previous_time)>= 1000)
//   {
//     seconds++;
  
//   previous_time = current_time;
//   Serial.println(seconds);
//   }


//if bth.serial condition'u almadık eski koddaki, almak gerekebilir dikkat et.
//https://www.youtube.com/watch?v=EfYaCYVupKI&ab_channel=AutomationHouse

if(seconds % 30 == 0)
{
  // Convert the sensor data to a string
  String dataString1 = String(result_1);
  String dataString2 = String(result_2);
  String dataString3 = String(result_3);
  String dataString4 = String(result_4);

  // Send the data to the Raspberry Pi
  blueToothSerial.println(dataString1);
  delay(100);
  blueToothSerial.println(dataString2);
  delay(100);
  blueToothSerial.println(dataString3);
  delay(100);
  blueToothSerial.println(dataString4);
  delay(100);
  
}
delay(2000);
// String dataString5 = String(fire_result);
// blueToothSerial.println(dataString5);
// delay(3000);
   
 //delay(100);

}

// ARduino SON KOD

