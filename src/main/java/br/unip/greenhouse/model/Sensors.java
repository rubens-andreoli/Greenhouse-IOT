package br.unip.greenhouse.model;

import java.io.File;

public class Sensors {

    public static final File INFO_FILE = new File("sensors.json");
    
    public final float airTemperature; //celcius
    public final float airHumidity; //percent
    public final float soilHumidity; //percent
    public final float soilPh; //5.5-7.5

    public Sensors(float airTemperature, float airHumidity, float soilHumidity, float soilPh) {
	this.airTemperature = airTemperature;
	this.soilHumidity = soilHumidity;
	this.airHumidity = airHumidity;
	this.soilPh = soilPh;
    }
    
    @Override
    public String toString() {
	return "Sensors{" + "airTemperature=" + airTemperature + 
		", airHumidity=" + airHumidity + 
		", soilHumidity=" + soilHumidity + 
		", soilPh=" + soilPh + 
		'}';
    }

}
