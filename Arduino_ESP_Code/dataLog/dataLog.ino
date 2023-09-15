/*
 * @author : Sukarna Jana
 * @aim    : Show data through Serial Communication of DHT11
 */

#include "DHT.h"

#define DHTPIN 2
#define DHTTYPE DHT11 

DHT dht(DHTPIN, DHTTYPE);

void setup(){

  Serial.begin(9600);
  dht.begin();
  
}

void loop(){
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  float f = dht.readTemperature(true);

  if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("404");
    return;
  }
  float hif = dht.computeHeatIndex(f, h);
  float hic = dht.computeHeatIndex(t, h, false);

  Serial.println((String)t + "," + (String)h);

  delay(1000);
}
