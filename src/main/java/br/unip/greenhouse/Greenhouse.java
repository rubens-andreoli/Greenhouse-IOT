package br.unip.greenhouse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Greenhouse {
    
    private static final byte LOOP_SLEEP = 10; //seconds
    private static final boolean DEBUG = true;
    private View view;
    
    private static final File INFO_FILE = new File("info.json");
    private Info info;
    private static final File ACTION_FILE = new File("action.json");
    private Action action;
    
    private boolean running;
    
    public void start(){
	running = true;
	
	createFiles();
	
	if(DEBUG){
	    view = new View();
	    java.awt.EventQueue.invokeLater(new Runnable() {
		public void run() {
		    view.setVisible(true);
		}
	    });
	}
	
	new Thread(new Runnable(){
	    @Override
	    public void run() {
		info = Info.create(); //simulate sensor
		while(running){
		    action = (Action) read(ACTION_FILE, Action.class);
		    if(action == null) createFiles();
		    if(DEBUG) view.appendText(action.toString());
		    /*IOT do actions then read sensors*/
		    info = Info.update(action, info); //simulate sensor reacting to actions
		    try {
			save(info, INFO_FILE);
		    } catch (IOException ex) {
			showError(ex);
			System.exit(1);
		    }
		    if(DEBUG){
			view.appendText(info.toString());
			view.breakText();
		    }
		    try {
			Thread.sleep(LOOP_SLEEP*1000);
		    } catch (InterruptedException ex) {}
		}			
	    }
	}).start();
    }
    
    public void stop(){
	running = false;
    }
    
    private void createFiles(){
    	try {
	    if(!INFO_FILE.exists()) save(new Info((byte)0,(byte)0,(byte)0,0F), INFO_FILE);
	    if(!ACTION_FILE.exists()) save(new Action(false, false, false), ACTION_FILE);
	} catch (IOException ex) {
	    showError(ex);
	    System.exit(1);
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
