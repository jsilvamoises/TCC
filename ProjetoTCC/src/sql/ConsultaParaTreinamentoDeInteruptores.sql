/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ 0.10 = ALARME DE MAGNETISMO                        @
@ 0.20 = ALARME DE INCENDIO                          @
@ 0.30 = ARCONDICIONADO                              @
@ 0.40 = AQUECEDOR                                   @
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
select 
       DISTINCT
       0.10 as ID_INTERUPTOR,
       d1.luminosidade as LUMINOSIDADE , 
       d1.magnetismo as MAGNETISMO, 
		 d1.temperatura as TEMPERATURA , 
		 d1.umidade as UMIDADE,
		 d1.statusGeral as ST_GERAL ,            
		 d1.statusAlarmeMagnestismo as  ST_INTERUPTOR   
from  tbl_dado as d1

where d1.statusAlarmeMagnestismo = 0 or d1.statusAlarmeMagnestismo = 1

union

select 
       DISTINCT                                                           
       0.20 as ID_INTERUPTOR,                                                            
       d2.luminosidade as LUMINOSIDADE ,                                                  
       d2.magnetismo as MAGNETISMO,                                                       
		 d2.temperatura as TEMPERATURA ,                                                
		 d2.umidade as UMIDADE,                                                         
		 d2.statusGeral as ST_GERAL ,                       
       d2.statusAlarmeIncencio as  ST_INTERUPTOR 
from  tbl_dado as d2                                                                                     
where d2.statusAlarmeIncencio  = 0 or d2.statusAlarmeIncencio = 1 

union                                                                 
                                                                  
select 
       DISTINCT                                                           
       0.30 as ID_INTERUPTOR,                                     
       d3.luminosidade as LUMINOSIDADE ,                           
       d3.magnetismo as MAGNETISMO,                                
		 d3.temperatura as TEMPERATURA ,                             
		 d3.umidade as UMIDADE,                                      
		 d3.statusGeral as ST_GERAL ,                                
       d3.statusArcondicionado as  ST_INTERUPTOR              
from  tbl_dado as d3                                               
where d3.statusArcondicionado  = 0 or d3.statusArcondicionado = 1

union                                                           
                                                               
select 
       DISTINCT                                                      
       0.40 as ID_INTERUPTOR,                                  
       d4.luminosidade as LUMINOSIDADE ,                        
       d4.magnetismo as MAGNETISMO,                             
		 d4.temperatura as TEMPERATURA ,                          
		 d4.umidade as UMIDADE,                                   
		 d4.statusGeral as ST_GERAL ,                             
       d4.statusAquecedor as  ST_INTERUPTOR           
from  tbl_dado as d4                                            
where d4.statusAquecedor  = 0 or d4.statusAquecedor = 1
