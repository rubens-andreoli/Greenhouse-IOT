package br.unip.greenhouse.model;

import java.io.File;

public class Action {
    
    public static final File ACTION_FILE = new File("action.json");
    
    public final boolean light;
    public final boolean water;
    public final boolean exaust;

    public Action(boolean light, boolean water, boolean exaust) {
	this.light = light;
	this.water = water;
	this.exaust = exaust;
    }

    @Override
    public String toString() {
	return "Action{" + 
		"light=" + light + 
		", water=" + water + 
		", exaust=" + exaust + 
		'}';
    }
    
}
