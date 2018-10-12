package br.unip.greenhouse.model;

import java.io.File;

public class Action {
    
    public static final File ACTION_FILE = new File("action.json");
    
    private boolean light;
    private boolean water;
    private boolean exaust;

    public Action(boolean light, boolean water, boolean exaust) {
	this.light = light;
	this.water = water;
	this.exaust = exaust;
    }

    public boolean isLight() {return light;}
    public boolean isWater() {return water;}
    public boolean isExaust() {return exaust;}
    public void setLight(boolean light) {this.light = light;}
    public void setWater(boolean water) {this.water = water;}
    public void setExaust(boolean exaust) {this.exaust = exaust;}

    @Override
    public String toString() {
	return "Action{" + 
		"light=" + light + 
		", water=" + water + 
		", exaust=" + exaust + 
		'}';
    }
    
}
