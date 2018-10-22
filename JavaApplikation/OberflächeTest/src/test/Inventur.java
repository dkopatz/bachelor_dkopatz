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

public class Inventur {
	
	public static void ReadInvFile(String file, String date, String url, String user, String password) throws InvalidFormatException, FileNotFoundException, IOException{
		//Workbook workbook = WorkbookFactory.create(new File(file));
	  FileInputStream in = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(in);
		workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Sheet sheet = workbook.getSheetAt(0);
		
		String[][] inv_matrix = new String[5][100];
		String kategorie;
		Connection conn1 = null;
	  PreparedStatement prepStmt = null;
	  String query;
	  int redrow = 0;
	  Row row;
	  
	  //Zeile 1 & 2 iterieren
	  for(int s=0; s < 6; s=s+5){
	  //Spalte durch iterieren
	  	for(int i=0; i <= 100; i++){
	  		row = sheet.getRow(i);
	  		if (row != null && row.getCell(s+0) != null && row.getCell(s+0).getCellTypeEnum().equals(CellType.STRING)) {
	  			//Annahme dass Kategorie eingelesen wurde
	  			kategorie = row.getCell(s+0).getStringCellValue();
	  			row = sheet.getRow(i+1);
	  			if (row != null && row.getCell(s+0) != null && row.getCell(s+0).getCellTypeEnum().equals(CellType.STRING) && row != null && row.getCell(s+1) != null && row.getCell(s+1).getCellTypeEnum().equals(CellType.STRING) && row != null && row.getCell(s+2) != null && row.getCell(s+2).getCellTypeEnum().equals(CellType.STRING) && row != null && row.getCell(s+3) != null && row.getCell(s+3).getCellTypeEnum().equals(CellType.STRING)) {

	  				if(row.getCell(s+0).getStringCellValue().equals("Produkt") && row.getCell(s+1).getStringCellValue().equals("Anzahl") && row.getCell(s+2).getStringCellValue().equals("Einheit") && row.getCell(s+3).getStringCellValue().equals("Bemerkung")){
	  					// Wenn Kategorie gefunden wurde muss die nächste Zeile ein bestimmtes layout haben (Produkt;Anzahl,Einheit;Bemerkung)	
	  					for(int j=i+2; j<=100; j++){
	  								row = sheet.getRow(j);
	  									if (row != null && row.getCell(s+0) != null && row.getCell(s+0).getCellTypeEnum().equals(CellType.STRING)){
	  										if (row != null && row.getCell(s+2) != null && row.getCell(s+2).getCellTypeEnum().equals(CellType.STRING)){
	  													inv_matrix[0][redrow] = kategorie;
	  													inv_matrix[1][redrow] = row.getCell(s+0).getStringCellValue();
	  													inv_matrix[3][redrow] = row.getCell(s+2).getStringCellValue();
	  													if (row != null && row.getCell(s+3) != null && row.getCell(s+3).getCellTypeEnum().equals(CellType.STRING)){
	  														inv_matrix[4][redrow] = row.getCell(s+3).getStringCellValue();
	  													}
	  													else {
	  														inv_matrix[4][redrow] = "";
	  													}
	  													if (row.getCell(s+1) != null && row.getCell(s+1).getCellTypeEnum().equals(CellType.NUMERIC)) {
	  														inv_matrix[2][redrow] = String.valueOf(row.getCell(s+1).getNumericCellValue());
	  													}
	  													else {
	  														inv_matrix[2][redrow] = "0";
	  													}
	  													redrow++;
	  											}
	  											
	  									}
	  									else{
	  										i = j;
	  										j = 101;
	  									}
	  						}
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
	    	System.out.println(redrow);
	    	query = "INSERT INTO inventurlisten_items (Datum, Kategorie, Produkt, "
 					+ " Anzahl, Einheit, Bemerkung) "
	    			+ "VALUES";
	    	
	    	for(int i = 1; i <= redrow; i++){	
	    			query = query +  " (?, ?, ?, ?, ?, ?),";
	    	}
	    	query = query.substring(0, query.length()-1);
	    	
	    	prepStmt = conn1.prepareStatement(query);
	    	int zähler = 0;
	    	for(int i = 0; i < redrow; i++){	
	    			prepStmt.setString(1 + 6*zähler, date);
	    			prepStmt.setString(2 + 6*zähler, inv_matrix[0][i]);
	    			prepStmt.setString(3 + 6*zähler, inv_matrix[1][i]);
	    			prepStmt.setString(4 + 6*zähler, inv_matrix[2][i]);
	    			prepStmt.setString(5 + 6*zähler, inv_matrix[3][i]);
	    			prepStmt.setString(6 + 6*zähler, inv_matrix[4][i]);
	    			zähler++;
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
	
	public static void WriteInvFile(String file, String date, String url, String user, String password) throws InvalidFormatException, FileNotFoundException, IOException{
	  
	  FileInputStream in = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(in);
		workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Sheet sheet = workbook.getSheetAt(0);
		
		String[][] inv_matrix = new String[5][100];
		String kategorie;
		Connection conn1 = null;
	  PreparedStatement prepStmt = null;
	  String query;
	  int redrow = 0;
	  Row row;

	  //Setze Datum
	  row = sheet.getRow(3);
		Cell cell = row.createCell(6);
		cell.setCellValue(date);
		
	  for(int s=0; s < 6; s=s+5){
	  //Spalte durch iterieren
	  	for(int i=0; i <= 100; i++){
	  		//jede Zeile
	  		row = sheet.getRow(i);
	  		if (row != null && row.getCell(s+0) != null && row.getCell(s+0).getCellTypeEnum().equals(CellType.STRING)) {
	  			// Wenn Text in Zeile Annahme dass dies eine Kategorie ist
	  			kategorie = row.getCell(s+0).getStringCellValue();
	  			row = sheet.getRow(i+1);
	  			if (row != null && row.getCell(s+0) != null && row.getCell(s+0).getCellTypeEnum().equals(CellType.STRING) && row != null && row.getCell(s+1) != null && row.getCell(s+1).getCellTypeEnum().equals(CellType.STRING) && row != null && row.getCell(s+2) != null && row.getCell(s+2).getCellTypeEnum().equals(CellType.STRING) && row != null && row.getCell(s+3) != null && row.getCell(s+3).getCellTypeEnum().equals(CellType.STRING)) {

	  				if(row.getCell(s+0).getStringCellValue().equals("Produkt") && row.getCell(s+1).getStringCellValue().equals("Anzahl") && row.getCell(s+2).getStringCellValue().equals("Einheit") && row.getCell(s+3).getStringCellValue().equals("Bemerkung")){
	  					//Wenn Kategorie dann muss nächste Zeile ein bestimtmes layout habe (Produkt, Anzahl, Einheit, Bemerkung)
	  					try {  
	  				  	conn1 = DriverManager.getConnection(url, user, password);
	  			    	if (conn1 != null) {
	  			    		//System.out.println("Connected to the database login");
	  			    	}
	  			    	
	  			    	i = i+2;
	  			    	query = "Select Produkt, Anzahl, Einheit, Bemerkung  FROM inventurlisten_items WHERE Datum = '" + date + "' AND Kategorie = '" + kategorie + "'";
	  			    	//System.out.println(query);
	  			    	prepStmt = conn1.prepareStatement(query);
	  			    	ResultSet rs = prepStmt.executeQuery();
	  			    	
	  			    	while ( rs.next() ){
	  			    	  System.out.printf( "%s, %s, %s, %s%n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
	  			    		row = sheet.getRow(i++);
	  			    		Cell cell0 = row.createCell(s+ 0);
	  			    		Cell cell1 = row.createCell(s+ 1);
	  			    		Cell cell2 = row.createCell(s+ 2);
	  			    		Cell cell3 = row.createCell(s+ 3);
	  			    		cell0.setCellValue(rs.getString(1));
	  			    		cell1.setCellValue(rs.getString(2));
	  			    		cell2.setCellValue(rs.getString(3));
	  			    		cell3.setCellValue(rs.getString(4));
	  			    	}	

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
	  				  
	  					
					}
	  		}	
	  	}
	 } 	
	 
	  in.close();
	  FileOutputStream fileOut = new FileOutputStream(file);
    workbook.write(fileOut);
    fileOut.close(); 
	 workbook.close();
	}

}
