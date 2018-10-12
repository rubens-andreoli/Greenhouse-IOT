package br.unip.greenhouse;

import br.unip.greenhouse.model.Action;
import br.unip.greenhouse.model.Info;
import br.unip.greenhouse.model.Simulator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Greenhouse {
    
    private static final int LOOP_SLEEP = 10000; //ms
    private static final boolean DEBUG = true;
    
    private View view;
    private Info info;
    private Simulator simulator;
    private Action action;
    
    private boolean running;
    
    public void start(){
	running = true;
	simulator = new Simulator();
	info = simulator.createInfo();
	
	if(DEBUG){
	    view = new View(info.id);
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
		    action = read(Action.ACTION_FILE, Action.class);
		    if(action == null) createFiles();
		    if(DEBUG) view.appendText(action.toString());
		    /*IOT do actions then read sensors*/
		    info = simulator.updateInfo(action, info);
		    try {
			save(info, Info.INFO_FILE);
		    } catch (IOException ex) {
			showError(ex);
			stop();
		    }
		    if(DEBUG){
			view.appendText(info.toString());
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
	if(DEBUG) view.dispose();
    }
    
    private void createFiles(){
    	try {
	    if(!Info.INFO_FILE.exists()) save(new Info((byte)0,(byte)0,(byte)0,(byte)0,0F), Info.INFO_FILE);
	    if(!Action.ACTION_FILE.exists()) save(new Action(false, false, false), Action.ACTION_FILE);
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
