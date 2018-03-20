package exelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Stream;

import exelReader.reader.ReaderXlsx;

public class Main {
	private boolean work = true;
	private boolean iscopy = true;
	private final static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//--------------------------properties-------------------------------------
	Properties properties = new Properties();
	//OutputStream os = new FileOutputStream("C:\\Users\\Tomek\\OneDrive\\Public\\WorkSpace\\Eclipse\\exelReader\\src\\main\\java\\exelReader\\reader\\dataConfig.properties");
	InputStream is = new FileInputStream("src\\main\\java\\exelReader\\reader\\dataConfig.properties");
	public Main() throws SecurityException, IOException {
		//---------------------------logger------------------------------------
		LogManager.getLogManager().reset();
		logr.setLevel(Level.FINE);
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.INFO);
		logr.addHandler(ch);
		
		FileHandler fh = new FileHandler("mylogger.log",true);
		fh.setLevel(Level.FINER);
		logr.addHandler(fh);
		//-------------------------------------------------------------------------
	    //--------------------------properties-------------------------------------
		properties.load(is);
		System.out.println(properties.getProperty("path"));
	}	
	public void work() {
		
		Path path = Paths.get(properties.getProperty("path"));
		System.out.println(path.toString());
		Path pathFile = null;
		//Thread workT = new Thread(new Start(this));
		//workT.start();
		while(work) {
			logr.log(Level.INFO,"In while work");
			if(iscopy) {
				try (Stream<Path> paths = Files.list(path)){
					Optional<Path> opath = paths.filter(p -> p.toString().endsWith("xlsx")).findAny();
					if(opath.isPresent()) {
						pathFile = opath.get();
						File xlsxFile = new File(pathFile.toString());
						Thread thread = new Thread(new ReaderXlsx(xlsxFile,this));
						thread.start();
						iscopy = false;
						work = false;
					}else {
						System.err.println("No xlsx file in directory");
					}
					
				}catch(IOException e) {
					System.err.println("No Such directory");
				}
			}
			work = false;
		}
	}
	
	public synchronized void stopWork() {
		this.work = false;
	}
	public  synchronized void setCopy() {
		this.iscopy = true;
	}

	public static void main(String[] args) {
		System.out.println("xlnx parser start");
		Main main;
		
		try {
			main = new Main();
			main.work();
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
