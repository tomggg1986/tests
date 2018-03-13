package exelReader;

import java.util.Scanner;

public class Start implements Runnable{
	
	private Main main;
    public Start(Main main) {
    	this.main = main;
    }
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		String exit = "";
		while(true) {
			exit = sc.next();
			if(exit.equalsIgnoreCase("q")) {
				main.stopWork();
				break;
			}
		}
		
	}
	

}
