package br.unip.greenhouse.model;

import java.io.File;

public class Info {

    public static final File INFO_FILE = new File("info.json");
    
    public final byte id;
    public final byte airTemperature; //celcius
    public final byte airHumidity; //percent
    public final byte soilHumidity; //percent
    public final float soilPh; //5.5-7.5

    public Info(byte id, byte airTemperature, byte airHumidity, byte soilHumidity, float soilPh) {
	this.id = id;
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

}
