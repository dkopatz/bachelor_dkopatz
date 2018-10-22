package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;


public class GInventur {
	
	public static void ReadGinvFile(String file, String date, String url, String user, String password) throws InvalidFormatException, FileNotFoundException, IOException{
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
	  Row row = sheet.getRow(5);
		for(int j=1; j <= 50; j= j+1){
			if (row.getCell(j) != null && row.getCell(j).getCellTypeEnum().equals(CellType.STRING)) {
				sorten[(j-1)] = row.getCell(j).getStringCellValue();
				sortenzahl++;
			} else {
						
			}
		}
		int[][] item_matrix = new int[100][sortenzahl+1];
		int redrow = 0;

		//for(int i=6; i <= 100; i++){
			int  rcount =6;
			row = sheet.getRow(rcount);
			while (row != null && row.getCell(0) != null && row.getCell(0).getCellTypeEnum().equals(CellType.STRING)) {
				
				//if(row.getCell(0).getStringCellValue().length() <= 4){
					redrow++;
					stand[redrow] = row.getCell(0).getStringCellValue();
					for(int j=1; j <= (sortenzahl + 1); j= j+1){
						//Iteriere durch Zeile
						if (row.getCell(j) != null && row.getCell(j).getCellTypeEnum().equals(CellType.NUMERIC)) {
							item_matrix[redrow][(j-1) ] = (int) row.getCell(j).getNumericCellValue();
						} else {
							item_matrix[redrow][(j-1)] = 0;
						}
					}
				//}	
					row = sheet.getRow(++rcount);
			}
		//}
		workbook.close();
		
		 try {  
		  	conn1 = DriverManager.getConnection(url, user, password);
	    	if (conn1 != null) {
	    		System.out.println("Connected to the database login");
	    	}
	 
	    	query = "INSERT INTO getränke_inventur (Datum, Stand_nr,"
 					+ " Position, Sorte, Menge) "
	    			+ "VALUES";
	    	
	    	for(int i = 1; i <= redrow; i++){	
	    		for(int c = 1; c <= sortenzahl; c++)
	    			query = query +  " (?, ?, ?, ?, ?),";
	    	}
	    	query = query.substring(0, query.length()-1);
	    	
	    	prepStmt = conn1.prepareStatement(query);
	    	int zähler = 0;
	    	for(int i = 1; i <= redrow; i++){	
	    		for(int c = 1; c <= sortenzahl; c++){
	    			prepStmt.setString(1 + 5*zähler, date);
	    			prepStmt.setString(2 + 5*zähler, stand[i]);
	    			//System.out.println(stand[i]);
	    			prepStmt.setString(3 + 5*zähler, String.valueOf(c));
	    			//System.out.println(String.valueOf(c));
	    			prepStmt.setString(4 + 5*zähler, sorten[c-1]);
	    			prepStmt.setString(5 + 5*zähler, String.valueOf(item_matrix[i][c-1]));
	    			zähler++;
	    		}
	    	}
	    	prepStmt.execute();
	 

		  } catch (SQLException ex) {
	    	System.out.println("An error occurred. Maybe user/password is invalid");
	    	ex.printStackTrace();
			}
		  finally {
		  	in.close();
				try{
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
	
	public static void WriteGinvFile(String file, String date, String url, String user, String password) throws InvalidFormatException, FileNotFoundException, IOException{
	  
	  FileInputStream in = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(in);
		workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Sheet sheet = workbook.getSheetAt(0);
		String stand;
		Connection conn1 = null;
	  PreparedStatement prepStmt = null;
	  String query;
	  Row row;
	  
	  //Setze Datum
	  row = sheet.getRow(3);
		Cell celld = row.createCell(3);
		celld.setCellValue(date);
	  
		int  rcount =6;
	  row = sheet.getRow(rcount);
		while (row != null && row.getCell(0) != null && row.getCell(0).getCellTypeEnum().equals(CellType.STRING)) {
				stand = row.getCell(0).getStringCellValue();
				
				try {  
			  	conn1 = DriverManager.getConnection(url, user, password);
		    	if (conn1 != null) {
		    		System.out.println("Connected to the database login");
		    	}
		    	
		    	query = "Select Menge FROM getränke_inventur WHERE Datum = '" + date + "' AND Stand_nr = '" + stand + "' ORDER BY Position";
		    	//System.out.println(query);
		    	prepStmt = conn1.prepareStatement(query);
		    	ResultSet rs = prepStmt.executeQuery();
		    	int spalte = 1;
		    	
		    	while ( rs.next() ){
		    		
		    		Cell cell = row.createCell(spalte);
		    		cell.setCellValue(rs.getString(1));
		    		
		    		spalte++;
		    	}	

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

				
				row = sheet.getRow(++rcount);
		}
		
	  in.close();
	  FileOutputStream fileOut = new FileOutputStream(file);
    workbook.write(fileOut);
    fileOut.close(); 	
    workbook.close();
		
		
	}

}
