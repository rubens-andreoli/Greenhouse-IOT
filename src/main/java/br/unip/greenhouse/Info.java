package br.unip.greenhouse;

import java.util.Random;

public class Info {

    private final byte airTemperature; //celcius
    private final byte airHumidity; //percent
    private final byte soilHumidity; //percent
    private final float soilPh; //5.5-7.5

    public Info(byte airTemperature, byte airHumidity, byte soilHumidity, float soilPh) {
	this.airTemperature = airTemperature;
	this.soilHumidity = soilHumidity;
	this.airHumidity = airHumidity;
	this.soilPh = soilPh;
    }

    @Override
    public String toString() {
	return "Info{" + "airTemperature=" + airTemperature + 
		", airHumidity=" + airHumidity + 
		", soilHumidity=" + soilHumidity + 
		", soilPh=" + soilPh + 
		'}';
    }

    //Simulated Info:
    private static final Random random = new Random();
    private static byte startAirTemperature;
    private static byte startAirHumidity;
    private static byte startSoilHumidity;
    private static float startSoilPh;
    
    public static Info create(){
	startAirTemperature = (byte)(random.nextInt(70)-10);
	startAirHumidity = (byte)(random.nextInt(80-20)+20);
	startSoilHumidity = (byte)(random.nextInt(70-35)+35);
	startSoilPh = random.nextFloat()*(7.5F-5F)+5F;
	return new Info(startAirTemperature, startAirHumidity, startSoilHumidity, startSoilPh);
    }
    
    public static Info update(Action a, Info i){
	byte airT = i.airTemperature;
	byte airH = i.airHumidity;
	byte soilH = i.soilHumidity;
	if(a.isExaust()){
	    if((Math.abs(airT)-2)/Math.abs((float)startAirTemperature) > 0.8) airT-=2;
	}else{
	   if(airT+2 < startAirTemperature) airT+=2;
	}
	
	if(a.isLight()){
	    if((Math.abs(airT)+1)/Math.abs((float)startAirTemperature) < 1.2) airT++;
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
	return new Info(airT, airH, soilH, i.soilPh);
    }
}
