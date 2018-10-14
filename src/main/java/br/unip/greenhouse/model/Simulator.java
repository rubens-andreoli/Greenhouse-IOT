package br.unip.greenhouse.model;

import java.util.Random;

public class Simulator {

    private static final Random RANDOM = new Random();
    
    private final float startAirTemperature;
    private final float startAirHumidity;
    private final float startSoilHumidity;
    private final float startSoilPh;
    
    public Simulator() {
	startAirTemperature = RANDOM.nextInt(70)-10;
	startAirHumidity = RANDOM.nextInt(80-20)+20;
	startSoilHumidity = RANDOM.nextInt(70-35)+35;
	startSoilPh = RANDOM.nextFloat()*(7.5F-5F)+5F;
    }

    public Sensors createSensors(){
	return new Sensors(startAirTemperature, startAirHumidity, startSoilHumidity, startSoilPh);
    }
    
    public Sensors updateSensors(Actions a, Sensors s){
	float airT = s.airTemperature;
	float airH = s.airHumidity;
	float soilH = s.soilHumidity;
	if(a.exaust){
	    if((Math.abs(airT)-2) > Math.abs(startAirTemperature*0.7) 
		    && (Math.abs(airT)-2) < Math.abs(startAirTemperature*1.3)){
		airT-=2;
	    }
	}else{
	   if(airT+2 < startAirTemperature) airT+=2;
	   else if(airT+1 < startAirTemperature) airT++;
	}
	
	if(a.light){
	    if(Math.abs(airT+1) < Math.abs(startAirTemperature*1.2) 
		    && Math.abs(airT+1) > Math.abs(startAirTemperature*0.8)){
		airT++;
	    }
	}else{
	    if(airT-1 > startAirTemperature) airT--;
	}
	
	if(a.water){ 
	    if((airH+1)/(float)startAirHumidity < 1.5) airH++;
	    if((soilH+1)/(float)startSoilHumidity < 1.3) soilH++;
	}else{
	    if(airH-1 > startAirHumidity) airH--;
	    if(soilH-1 > startSoilHumidity) soilH--;
	}
	return new Sensors(airT, airH, soilH, s.soilPh);
    }
    
}
