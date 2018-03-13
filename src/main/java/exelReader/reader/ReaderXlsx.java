package exelReader.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.logging.*;

import exelReader.Main;

public class ReaderXlsx implements Runnable {

	File xlnxFile;
	Main main;
	//--------to copy----------------------
	Path target = Paths.get("C:\\Users\\Tomek\\Documents\\arkusze\\copy\\Zeszyt1.xlsx");
	private final static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public ReaderXlsx(File file, Main main) {
		this.xlnxFile = file;
		this.main = main;
		LogManager.getLogManager().reset();
		logr.setLevel(Level.FINE);
	}
	@Override
	public void run() {
		FileInputStream xlnxStream;
		XSSFWorkbook workbook = null;
		try {
			xlnxStream = new FileInputStream(xlnxFile);
			workbook = new XSSFWorkbook(xlnxStream);
		} catch (FileNotFoundException e) {		
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(workbook.getNumberOfSheets());
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFSheet sheet2 = workbook.getSheetAt(1);
		Date date; 
		date = sheet.getRow(1).getCell(4).getDateCellValue();
		System.out.println(date);
		
		Iterator<Row> rowIterator = sheet.iterator();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				
			    if(cell.getCellTypeEnum() == CellType.STRING) {
				    System.out.print(cell.getStringCellValue());
			    }else if(cell.getCellTypeEnum() == CellType.NUMERIC) {
				    System.out.print(cell.getNumericCellValue());
			    }else if(cell.getCellTypeEnum() == CellType.BOOLEAN) {
				    System.out.print(cell.getBooleanCellValue());
			    }else if(cell.getCellTypeEnum() == CellType._NONE)	{
			    	//cell.get
			    }
			}
			System.out.println();
			logr.log(Level.INFO,"Read complet");
		}
		
//		try {
//			//Files.move(this.xlnxFile.toPath(),this.target);
			this.main.setCopy();
			//Thread.sleep(5000);
//		}catch (InterruptedException e) {
//			System.err.println(e);
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			System.err.println("Move operation field");
//			e.printStackTrace();
//		}
		

	}

}
