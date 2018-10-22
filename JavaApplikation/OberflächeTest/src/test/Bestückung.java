package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class Bestückung {

	public static void ReadBestFile(String file, String date, String url, String user, String password) throws InvalidFormatException, FileNotFoundException, IOException{

		//Workbook workbook = WorkbookFactory.create(new File(file));
	  FileInputStream in = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(in);
		workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Sheet sheet = workbook.getSheetAt(0);
		
		String[] sorten = new String[50];
		int sortenzahl = 0;
		String[] stand = new String[100];
		Connection conn1 = null;
	  PreparedStatement prepStmt = null;
	  String query;
	  
	  //Get Sorten
	  Row row = sheet.getRow(0);
		for(int j=2; j <= 50; j= j+2){
			if (row.getCell(j) != null && row.getCell(j).getCellTypeEnum().equals(CellType.STRING)) {
				sorten[(j-2)/2] = row.getCell(j).getStringCellValue();
				sortenzahl++;
			} else {
						
			}
		}
		int[][] item_matrix = new int[100][sortenzahl+1];
		int redrow = 0;

		for(int i=1; i <= 100; i++){
			row = sheet.getRow(i);
			if (row != null && row.getCell(0) != null && row.getCell(0).getCellTypeEnum().equals(CellType.STRING)) {
				if(row.getCell(0).getStringCellValue().length() <= 4){
					redrow++;
					stand[redrow] = row.getCell(0).getStringCellValue();
					for(int j=2; j <= (sortenzahl + 1) *2; j= j+2){
						if (row.getCell(j) != null && row.getCell(j).getCellTypeEnum().equals(CellType.NUMERIC)) {
							item_matrix[redrow][(j-2)/2] = (int) row.getCell(j).getNumericCellValue();
						} else {
							item_matrix[redrow][(j-2)/2] = 0;
						}
					}
				}	
			}
		}
		workbook.close();
		
		 try {  
		  	conn1 = DriverManager.getConnection(url, user, password);
	    	if (conn1 != null) {
	    		System.out.println("Connected to the database login");
	    	}
	 
	    	query = "INSERT INTO bestueckungs_items (Datum, Stand_nr, "
 					+ " Position, Sorte, Menge) "
	    			+ "VALUES";
	    	
	    	for(int i = 1; i <= redrow; i++){	
	    		for(int c = 1; c <= sortenzahl; c++)
	    			query = query +  " (?, ?, ?, ?, ?, ?),";
	    	}
	    	query = query.substring(0, query.length()-1);
	    	
	    	prepStmt = conn1.prepareStatement(query);
	    	int zähler = 0;
	    	for(int i = 1; i <= redrow; i++){	
	    		for(int c = 1; c <= sortenzahl; c++){
	    			prepStmt.setString(1 + 5*zähler, date);
	    			prepStmt.setString(2 + 5*zähler, stand[i]);
	    			prepStmt.setString(4 + 5*zähler, String.valueOf(c));
	    			prepStmt.setString(5 + 5*zähler, sorten[c-1]);
	    			prepStmt.setString(6 + 5*zähler, String.valueOf(item_matrix[i][c-1]));
	    			zähler++;
	    		}
	    	}
	    	prepStmt.execute();
	 

		  } catch (SQLException ex) {
	    	System.out.println("An error occurred. Maybe user/password is invalid");
	    	ex.printStackTrace();
			}
		  finally {
				try{
					in.close();
					if(conn1 != null){
						conn1.close();
					}
					if(prepStmt != null){
						prepStmt.close();
					}
				}		catch (SQLException ex) {
	      	ex.printStackTrace();
				}
		  }	
	}
	
}
