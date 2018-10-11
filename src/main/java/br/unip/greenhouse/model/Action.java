package br.unip.greenhouse.model;

public class Action {
        
    private final boolean light;
    private final boolean water;
    private final boolean exaust;

    public Action(boolean light, boolean water, boolean exaust) {
	this.light = light;
	this.water = water;
	this.exaust = exaust;
    }

    public boolean isLight() {return light;}
    public boolean isWater() {return water;}
    public boolean isExaust() {return exaust;}

    @Override
    public String toString() {
	return "Action{" + 
		"light=" + light + 
		", water=" + water + 
		", exaust=" + exaust + 
		'}';
    }
    
}
