package br.unip.greenhouse;

import br.unip.greenhouse.model.Actions;
import br.unip.greenhouse.model.Sensors;
import br.unip.greenhouse.model.Simulator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Greenhouse {
    
    private static final int LOOP_SLEEP = 20000; //ms
    private static final boolean DEBUG = true;
    
    private View view;
    private Sensors sensors;
    private Simulator simulator;
    private Actions actions;
    
    private boolean running;
    
    public void start(){
	running = true;
	simulator = new Simulator();
	sensors = simulator.createSensors();
	
	if(DEBUG){
	    view = new View();
	    java.awt.EventQueue.invokeLater(new Runnable() {
		public void run() {
		    view.setVisible(true);
		}
	    });
	}
	
	createFiles();
	
	new Thread(new Runnable(){
	    @Override
	    public void run() {
		while(running){
		    actions = read(Actions.ACTIONS_FILE, Actions.class);
		    if(actions == null) createFiles();
		    if(DEBUG) view.appendText(actions.toString());
		    /*IOT do actions then read sensors*/
		    sensors = simulator.updateSensors(actions, sensors);
		    try {
			save(sensors, Sensors.SENSORS_FILE);
		    } catch (IOException ex) {
			showError(ex);
			stop();
		    }
		    if(DEBUG){
			view.appendText(sensors.toString());
			view.breakText();
		    }
		    try {
			Thread.sleep(LOOP_SLEEP);
		    } catch (InterruptedException ex) {}
		}			
	    }
	}).start();
    }
    
    public void stop(){
	running = false;
	if(DEBUG) view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
    }
    
    private void createFiles(){
    	try {
	    if(!Sensors.SENSORS_FILE.exists()) save(new Sensors(0F,0F,0F,0F), Sensors.SENSORS_FILE);
	    if(!Actions.ACTIONS_FILE.exists()) save(new Actions(false, false, false), Actions.ACTIONS_FILE);
	} catch (IOException ex) {
	    showError(ex);
	    stop();
	}
    }
    
    private void showError(Exception ex){
	if(DEBUG){
	    JOptionPane.showMessageDialog(
		null,
		ex.getMessage(),
		"Erro!",
		JOptionPane.WARNING_MESSAGE
	    );
	}
    }
    
    private void save(Object obj, File file) throws IOException{
	try(FileWriter writer = new FileWriter(file)){
	    Gson gson = new GsonBuilder()
		    .serializeNulls()
		    .setPrettyPrinting()
		    .create();
	    gson.toJson(obj, writer);
	}
    }
    
    private <T extends Object> T read(File file, Class<T> clazz){
	try(FileReader reader = new FileReader(file)){
	    return new Gson().fromJson(reader, clazz);
	} catch (IOException ex) {
	    return null;
	}
    }
     
    public static void main(String[] args) {
	Greenhouse m = new Greenhouse();
	m.start();
    }
}
