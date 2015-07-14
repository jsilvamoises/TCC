float LDR_INPUT = 0;
float LDRValue = 0;
float lightSensitivity = 500;
void setup() {
  Serial.begin(9600);
  pinMode(13,OUTPUT);

}

void loop() {
  LDRValue = analogRead(LDR_INPUT);
  Serial.println(LDRValue);
  delay(2000);
}
