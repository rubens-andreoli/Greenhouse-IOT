package br.unip.greenhouse.model;

import java.util.Random;

public class Simulator {

    private static final Random random = new Random();
    
    private final byte id;
    private final byte startAirTemperature;
    private final byte startAirHumidity;
    private final byte startSoilHumidity;
    private final float startSoilPh;
    
    public Simulator() {
    	id = 1;
	startAirTemperature = (byte)(random.nextInt(70)-10);
	startAirHumidity = (byte)(random.nextInt(80-20)+20);
	startSoilHumidity = (byte)(random.nextInt(70-35)+35);
	startSoilPh = random.nextFloat()*(7.5F-5F)+5F;
    }

    public Info createInfo(){
	return new Info(id, startAirTemperature, startAirHumidity, startSoilHumidity, startSoilPh);
    }
    
    public Info updateInfo(Action a, Info i){
	byte airT = i.airTemperature;
	byte airH = i.airHumidity;
	byte soilH = i.soilHumidity;
	if(a.isExaust()){
	    if((Math.abs(airT)-2) > Math.abs(startAirTemperature*0.7) 
		    && (Math.abs(airT)-2) < Math.abs(startAirTemperature*1.3)){
		airT-=2;
	    }
	}else{
	   if(airT+2 < startAirTemperature) airT+=2;
	   else if(airT+1 < startAirTemperature) airT++;
	}
	
	if(a.isLight()){
	    if(Math.abs(airT+1) < Math.abs(startAirTemperature*1.2) 
		    && Math.abs(airT+1) > Math.abs(startAirTemperature*0.8)){
		airT++;
	    }
	}else{
	    if(airT-1 > startAirTemperature) airT--;
	}
	
	if(a.isWater()){ 
	    if((airH+1)/(float)startAirHumidity < 1.5) airH++;
	    if((soilH+1)/(float)startSoilHumidity < 1.3) soilH++;
	}else{
	    if(airH-1 > startAirHumidity) airH--;
	    if(soilH-1 > startSoilHumidity) soilH--;
	}
	return new Info(id, airT, airH, soilH, i.soilPh);
    }
    
}
