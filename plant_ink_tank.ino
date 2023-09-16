/**
 * @Author : Sukarna Jana
 * @Date   : 16/09/2023
 * @Title  : Plant Ink Tank
 * @Device : Arduino Mega
 * @Version: 0.0.1
 * @Detail : This code is fault proof alart for device
 *           Any unwated things happends,device will get into alart mode
 *           like:
 *           1. Change in oriantation of the Ultasonic Sensor
 *           2. Pump got Jam
 *           3. Constent status of Tank level
 *           It has one more feature:
 *           1. we can connect to device through USB and we can get all details of Device
 *              it's like LOG Report of device
 */

/* Configering all the requirment based on need BEGINS */
// Chemical Tank 01 
#define ultrasonicSensor_01_trig 2
#define ultrasonicSensor_01_echo 3
// Chemical Tank 02
#define ultrasonicSensor_02_trig 4
#define ultrasonicSensor_02_echo 5
// Chemical Tank 03
#define ultrasonicSensor_03_trig 6
#define ultrasonicSensor_03_echo 7
// Chemical Tank 04
#define ultrasonicSensor_04_trig 8
#define ultrasonicSensor_04_echo 9
// Tune Sensor
#define maxDepth 35 // it's in CM unit
#define minDepth 4  // it's in CM unit
/*
// Water Tank 01
#define ultrasonicSensor_05_trig 10
#define ultrasonicSensor_05_echo 11
*/
// Pump 01
#define pump_01_A 12
#define pump_01_B 13
// Pump 02
#define pump_02_A 14
#define pump_02_B 15
// Pump 03
#define pump_03_A 16
#define pump_03_B 17
// Pump 04
#define pump_04_A 18
#define pump_04_B 19
// Tune Pump
#define pump_speed 127 // 0 - 255
#define puching_interval 100 // in ms unit. for per ML
// Debug Mode
#define usb_baudRate 9600
#define uart_baudRate 9600
#define uart_tx 20
#define uart_rx 21
#define buzzer 22
/* Configering all the requirment based on need ENDS */

int getTankLevel();
void error();
void startPump();

/* Init all the Pins and get the board ready BEGIN */
void setup(){
  // setuping debuging
  Serial.begin(usb_baudRate);
  //----- Software Serial will come here -----//

  // setuping Pumps 
  pinMode(pump_01_A, OUTPUT);
  pinMode(pump_01_B, OUTPUT);
  pinMode(pump_02_A, OUTPUT);
  pinMode(pump_02_B, OUTPUT);
  pinMode(pump_03_A, OUTPUT);
  pinMode(pump_03_B, OUTPUT);
  pinMode(pump_04_A, OUTPUT);
  pinMode(pump_04_B, OUTPUT);

  // setuping sensor
}
/* Init all the Pins and get the board ready ENDS */

/* Run Device */
void loop(){
  
}

int getTankLevel(int tankNo = -1){
  /*
   * get data from sensor and and get the data
   * if it is extemly looks off track then oriantation of sensor might 
   * got change So, through error : -1
   */
}

void error(int priority = -1){
  /* it will keep beeping but machine will work without stoping, based on priority 
   * if priority is less then it will beap but machine will keep doing his work without  
   * intaruption, but it priority is more then machine will stop and it will buzz...
   */
}

void startPump(int pumpNo = -1, int unit = -1, boolean rotation = true){
  /* the pump will run based on the input
   * which pump to activate
   * how much liquid to flow
   * rotaion Clock or Anti Clock
   * 
   * for Safety it will have a reading of tank weater it pumping or not 
   * if not it will through a error 
   * it will identify based on init liquid level and after liquid level if no change 
   * then through error or else all Okay
   */
}
