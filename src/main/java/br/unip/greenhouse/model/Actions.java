package br.unip.greenhouse.model;

import java.io.File;

public class Actions {
    
    public static final File ACTION_FILE = new File("actions.json");
    
    public final boolean light;
    public final boolean water;
    public final boolean exaust;

    public Actions(boolean light, boolean water, boolean exaust) {
	this.light = light;
	this.water = water;
	this.exaust = exaust;
    }

    @Override
    public String toString() {
	return "Actions{" + 
		"light=" + light + 
		", water=" + water + 
		", exaust=" + exaust + 
		'}';
    }
    
}
