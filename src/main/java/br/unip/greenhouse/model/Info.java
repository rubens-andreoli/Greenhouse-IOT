package br.unip.greenhouse.model;

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

    public byte getAirTemperature() {return airTemperature;}
    public byte getAirHumidity() {return airHumidity;}
    public byte getSoilHumidity() {return soilHumidity;}
    public float getSoilPh() {return soilPh;}  
    
    @Override
    public String toString() {
	return "Info{" + "airTemperature=" + airTemperature + 
		", airHumidity=" + airHumidity + 
		", soilHumidity=" + soilHumidity + 
		", soilPh=" + soilPh + 
		'}';
    }

}
