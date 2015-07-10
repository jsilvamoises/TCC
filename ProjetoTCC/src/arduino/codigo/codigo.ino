#define ALARME_MAGNETISMO 10   // define que o alarme de magnetismo sera ativado/desativado pela porta 10
#define ALARME_INCENCIO   11   // define que o alarme de incendio será ativado/desativado pela porta 11
#define AQUECED0R         12   // Define que o aquecedor será ativado/desativado pela porta 12
#define AR_CONDICIONADO   13   // Define que o ar condicionado será ativado/desativado pela porta 13



int dado; //variavel que recebera dados na porta serial

long intervaloEscritaNaPorta = 1000;
long valorUltimaEscritaNaPorta = 0;
String json = "";
byte a, b, c , d;

void setup() {
  Serial.begin(9600); // frequencia da porta serial
  pinMode(ALARME_MAGNETISMO, OUTPUT); // define o pino p ledPin como saida
  pinMode(ALARME_INCENCIO, OUTPUT); // define o pino p ledPin como saida
  pinMode(AQUECED0R, OUTPUT); // define o pino p ledPin como saida
  pinMode(AR_CONDICIONADO, OUTPUT); // define o pino p ledPin como saida

}

void loop() {
    lerComandosRecebidos();

  //GERA UMA STRING NO FORMATO JSON
  // if ((millis() % 1000) == 0) {
  if ((millis() - valorUltimaEscritaNaPorta) >= intervaloEscritaNaPorta) {
    
   
   /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      @     LÊ OS DADOS VINDOS DOS PINOS                @
    /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    // Imprime dados da temperatura
    Serial.print("{temperatura: ");
    Serial.print(random(10, 50));
    Serial.print(", ");     
    // imprime dados da umidade
    Serial.print("umidade: ");
    Serial.print(random(60, 100));
    Serial.print(", ");    
    // imprime dados da luminosidade
    Serial.print("luminosidade: ");
    Serial.print(random(0, 100));
    Serial.print(", ");     
    // imprime dados do magnetismo
    Serial.print("magnetismo: ");
    Serial.print(random(0, 100));
    Serial.print(", ");     
    // imprime dados do id dos dispositivo
    Serial.print("identificador: ");
    Serial.print("AMB-01");
    Serial.print(", ");
    /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      @     ATIVA / DESTATIVA SENSORES NO ARDUINO       @
    /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    // imprime o estado do alarme de magnetismo
    Serial.print("statusAlarmeMagnestismo: ");
    a = digitalRead(ALARME_MAGNETISMO);
    Serial.print(a);
    Serial.print(", ");
    // imprime o estado do alarme de incendio
    Serial.print("statusAlarmeIncencio: ");
    b = digitalRead(ALARME_INCENCIO);
    Serial.print(b);
    Serial.print(", ");
    // imprime o estado do aquecedor
    Serial.print("statusAquecedor: ");
    c = digitalRead(AQUECED0R);
    Serial.print(c);
    Serial.print(", ");
    // imprime o estado do ar condicionado
    Serial.print("statusArcondicionado: ");
    d = digitalRead(AR_CONDICIONADO);
    Serial.print(d);
    Serial.print(", ");
    // imprime o status geral, caso haja algum esquipamento de saida liga imprime 1 caso todos estejam desligado 0
    Serial.print("statusGeral: ");
    Serial.print(statusGeral(a,b,c,d));
   // Serial.print(", ");
    Serial.println("} ");
    Serial.println("*");
    valorUltimaEscritaNaPorta = millis();    
    Serial.flush();
  }





}


byte statusGeral(byte a, byte b, byte c, byte d){
  if(a==1 || b==1 || c==1 || d==1){
    return 1;
  }else{
    return 0;
  }
}


void lerComandosRecebidos() {
  if (Serial.available() > 0) { //verifica se existe comunicação com a porta serial
    dado = Serial.read();

    switch (dado) {

      //LIGA / DESLIGA ALARME_MAGNETISMO
      case 100:
        digitalWrite(ALARME_MAGNETISMO, LOW);// desliga o pino
        break;
      case 101:
        digitalWrite(ALARME_MAGNETISMO, HIGH);//liga o pino
        break;
      //LIGA / DESLIGA ALARME_INCENCIO
      case 110:
        digitalWrite(ALARME_INCENCIO, LOW);// desliga o pino
        break;
      case 111:
        digitalWrite(ALARME_INCENCIO, HIGH);//liga o pino
        break;
      //LIGA / DESLIGA AQUECED0R
      case 120:
        digitalWrite(AQUECED0R, LOW);// desliga o pino
        break;
      case 121:
        digitalWrite(AQUECED0R, HIGH);//liga o pino
        break;
      // LIGA / DESLIGA AR_CONDICIONADO
      case 130:
        digitalWrite(AR_CONDICIONADO, LOW);// desliga o pino
        //Serial.println("Desligando");
        break;
      case 131:
        digitalWrite(AR_CONDICIONADO, HIGH);//liga o pino
        //Serial.println("Ligando");
        break;
    }

  }

}

