#define SENSOR_LUMINOSIDADE 1
int LDR;
int cont;
int i;
void setup() {
  pinMode(13, OUTPUT);
  Serial.begin(9600);

}

void loop() {
  //LDR = 0;
  //for(i = 0; i<=10; i++){
  //  cont = analogRead(A0);
   // LDR = LDR+cont;
   // delay(10);
  //}
  //LDR = LDR/10;
  LDR = analogRead(A0);
  Serial.println(LDR);
  delay(1000);

}
