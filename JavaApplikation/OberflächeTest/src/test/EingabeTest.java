package test;

import java.awt.EventQueue;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.nio.charset.StandardCharsets;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.Properties;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;

public class EingabeTest {

	private JFrame frmMillemodiDatenbankVerwalten;
	private JTextField ToDO;
	private JTextField Name;
	private JPasswordField Nutzerpasswort;
	
  private static String url1 = "****";
  private static String user = "****";
  private static String password = "****";
  private JTextField best_path;
  private JTextField best_date;
  private JTextField inv_path;
  private JTextField inv_date;
  private JTextField spiel_date;
  private JTextField ginv_date;
  private JTextField ginv_path;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EingabeTest window = new EingabeTest();
					window.frmMillemodiDatenbankVerwalten.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EingabeTest() {
		initialize();
	}
	
	private static String bytesToHex(byte[] hash) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++) {
    String hex = Integer.toHexString(0xff & hash[i]);
    if(hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMillemodiDatenbankVerwalten = new JFrame();
		frmMillemodiDatenbankVerwalten.setTitle("Millemodi Datenbank verwalten");
		frmMillemodiDatenbankVerwalten.setBounds(100, 100, 877, 447);
		frmMillemodiDatenbankVerwalten.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMillemodiDatenbankVerwalten.getContentPane().setLayout(null);
		
		ToDO = new JTextField();
		ToDO.setBounds(10, 39, 169, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(ToDO);
		ToDO.setColumns(10);
		
		JSpinner priorität = new JSpinner();
		priorität.setBounds(189, 40, 29, 22);
		frmMillemodiDatenbankVerwalten.getContentPane().add(priorität);
		
		JButton Absenden = new JButton("Absenden");
		Absenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String action;
				String prio;
				Connection conn1 = null;
			  PreparedStatement prepStmt = null;
				
				action = ToDO.getText();
				ToDO.setText("");
				prio = String.valueOf(priorität.getValue());
				System.out.println(prio);
				
				if(action.trim().length() != 0){
					
					try {

		        conn1 = DriverManager.getConnection(EingabeTest.url1, EingabeTest.user, EingabeTest.password);
		        if (conn1 != null) {
		            System.out.println("Connected to the database ToDo");
		        }
		     
		        String query = "INSERT INTO aufgaben (aufgabe, priorität) VALUES (?,?)"; 
		        prepStmt = conn1.prepareStatement(query);
		        prepStmt.setString(1, action);
		        prepStmt.setString(2, prio);
		        prepStmt.execute();
		     

					} catch (SQLException ex) {
		        System.out.println("An error occurred. Maybe user/password is invalid");
		        ex.printStackTrace();
					}
					finally {
						try{
							if(conn1 != null){
								conn1.close();
							}
							if(prepStmt != null){
								prepStmt.close();
							}
						}	catch (SQLException ex) {
			        ex.printStackTrace();
						}
						
					}
				}
				
			}
		});
		Absenden.setBounds(42, 73, 110, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(Absenden);
		
		JLabel lblNewLabel = new JLabel("Neuen Todo-Eintrag");
		lblNewLabel.setBounds(35, 14, 117, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblNewLabel);
		
		Name = new JTextField();
		Name.setBounds(731, 39, 86, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(Name);
		Name.setColumns(10);
		
		JButton btnNewButton = new JButton("Nutzer Erstellen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name, pw, hashpw;
				hashpw = "";
				Connection conn1 = null;
			  PreparedStatement prepStmt = null;
				
				name = Name.getText();
				Name.setText("");
				pw = String.valueOf(Nutzerpasswort.getPassword());
				Nutzerpasswort.setText("");
				
				MessageDigest digest;
				try {
					System.out.println(Security.getProviders());
					digest = MessageDigest.getInstance("SHA-256");
					byte[] encodedhash = digest.digest(
							pw.getBytes(StandardCharsets.UTF_8));
					hashpw = bytesToHex(encodedhash);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				if(name.trim().length() != 0 && pw.trim().length() != 0&& hashpw.trim().length() != 0){
					
					try {
		        // connect way #1


		        conn1 = DriverManager.getConnection(EingabeTest.url1, EingabeTest.user, EingabeTest.password);
		        if (conn1 != null) {
		            System.out.println("Connected to the database login");
		        }
		     
		        String query = "INSERT INTO login (username, password) VALUES (?, ?)"; 
		        prepStmt = conn1.prepareStatement(query);
		        prepStmt.setString(1, name);
		        prepStmt.setString(2, hashpw);
		        prepStmt.execute();
		     

					} catch (SQLException ex) {
		        System.out.println("An error occurred. Maybe user/password is invalid");
		        ex.printStackTrace();
					}
					finally {
						try{
							if(conn1 != null){
								conn1.close();
							}
							if(prepStmt != null){
								prepStmt.close();
							}
						}	catch (SQLException ex) {
			        ex.printStackTrace();
						}
						
					}
				}
				
			}
		});
		btnNewButton.setBounds(692, 113, 125, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(btnNewButton);
		
		JLabel lblNeuenNutzerErstellen = new JLabel("Neuen Nutzer Erstellen");
		lblNeuenNutzerErstellen.setBounds(692, 14, 142, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblNeuenNutzerErstellen);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(675, 43, 46, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblName);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(655, 77, 66, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblPasswort);
		
		Nutzerpasswort = new JPasswordField();
		Nutzerpasswort.setBounds(731, 73, 86, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(Nutzerpasswort);
		
		best_path = new JTextField();
		best_path.setBounds(21, 343, 206, 20);
		frmMillemodiDatenbankVerwalten.getContentPane().add(best_path);
		best_path.setColumns(10);
		
		final JFileChooser fc = new JFileChooser();
		JButton search_best_path = new JButton("...");
		search_best_path.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(frmMillemodiDatenbankVerwalten);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          best_path.setText(file.getPath());
          //This is where a real application would open the file.
          //log.append("Opening: " + file.getName() + "." + newline);
				} else {
          //log.append("Open command cancelled by user." + newline);
				}
			}
		});
		search_best_path.setBounds(237, 342, 28, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(search_best_path);
		
		JButton btnBestAbschicken = new JButton("Best\u00FCckung abschicken");
		btnBestAbschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Bestückung.ReadBestFile(best_path.getText(), best_date.getText(), EingabeTest.url1, EingabeTest.user, EingabeTest.password);
			
				} catch (Exception ex) {
	        ex.printStackTrace();
				}
			}	
		});
		btnBestAbschicken.setBounds(35, 374, 169, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(btnBestAbschicken);
		
		JLabel lblBestlisteHinzufgen = new JLabel("Best\u00FCckungsliste hinzuf\u00FCgen");
		lblBestlisteHinzufgen.setBounds(42, 286, 162, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblBestlisteHinzufgen);
		
		best_date = new JTextField();
		best_date.setBounds(140, 312, 86, 20);
		frmMillemodiDatenbankVerwalten.getContentPane().add(best_date);
		best_date.setColumns(10);
		
		JLabel lblDatum = new JLabel("Datum (YYYY-MM-DD)");
		lblDatum.setBounds(10, 311, 131, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblDatum);
		
		inv_path = new JTextField();
		inv_path.setColumns(10);
		inv_path.setBounds(334, 312, 206, 20);
		frmMillemodiDatenbankVerwalten.getContentPane().add(inv_path);
		
		JLabel lblDatumyyyymmdd = new JLabel("Datum (YYYY-MM-DD)");
		lblDatumyyyymmdd.setBounds(334, 286, 131, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblDatumyyyymmdd);
		
		inv_date = new JTextField();
		inv_date.setColumns(10);
		inv_date.setBounds(454, 283, 86, 20);
		frmMillemodiDatenbankVerwalten.getContentPane().add(inv_date);
		
		JButton btnInventurAbschicken = new JButton("Lebensmittel Inventur abschicken");
		btnInventurAbschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Inventur.ReadInvFile(inv_path.getText(), inv_date.getText(), EingabeTest.url1, EingabeTest.user, EingabeTest.password);
			
				} catch (Exception ex) {
	        ex.printStackTrace();
				}
			}	
		});
		btnInventurAbschicken.setBounds(334, 342, 252, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(btnInventurAbschicken);
		
		JLabel lblInventurlisteHinzufgen = new JLabel("Inventurliste hinzuf\u00FCgen");
		lblInventurlisteHinzufgen.setBounds(355, 139, 162, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblInventurlisteHinzufgen);
		
		JButton search_inv_path = new JButton("...");
		search_inv_path.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(frmMillemodiDatenbankVerwalten);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          inv_path.setText(file.getPath());
          //This is where a real application would open the file.
          //log.append("Opening: " + file.getName() + "." + newline);
				} else {
          //log.append("Open command cancelled by user." + newline);
				}
			}
		});
		search_inv_path.setBounds(550, 311, 28, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(search_inv_path);
		
		spiel_date = new JTextField();
		spiel_date.setBounds(267, 39, 186, 22);
		frmMillemodiDatenbankVerwalten.getContentPane().add(spiel_date);
		spiel_date.setColumns(10);
		
		JButton btnSpieltag_abschicken = new JButton("Absenden");
		btnSpieltag_abschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String date;
				Connection conn1 = null;
			  PreparedStatement prepStmt = null;
			  Statement stmt = null;
			  String[] aufgaben = new String[50];
			  String[] prio = new String[50];
				
				date = spiel_date.getText();
				
				if(date.trim().length() != 0){
					
					try {

		        conn1 = DriverManager.getConnection(EingabeTest.url1, EingabeTest.user, EingabeTest.password);
		        if (conn1 != null) {
		            System.out.println("Connected to the database ToDo");
		        }
		     
		        stmt = conn1.createStatement();
		        String sql;
		        sql = "SELECT Aufgabe, Priorität FROM aufgaben";
		        ResultSet rs = stmt.executeQuery(sql);
		        
		        int count = 0;
		        while(rs.next()){
		          //Retrieve by column name
		        	aufgaben[count] = rs.getString("Aufgabe");
		        	prio[count] = rs.getString("Priorität");
		        	count++;
		        }  
		        
		        
		        String query = "INSERT INTO todoliste (Datum, Aufgabe, Priorität) VALUES";
		        
			    	for(int i = 1; i <= count; i++){
		    			query = query +  " (?, ?, ?),";
			    	}
			    	query = query.substring(0, query.length()-1);
		        
		        prepStmt = conn1.prepareStatement(query);
		        
		        int zähler = 0;
			    	for(int i = 1; i <= count; i++){
			    		prepStmt.setString(1 + zähler*3, date);
			    		prepStmt.setString(2 + zähler*3, aufgaben[i-1]);
			    		prepStmt.setString(3 + zähler*3, prio[i-1]);
			    		zähler++;
			    	}	
		        prepStmt.execute();
		     

					} catch (SQLException ex) {
		        System.out.println("An error occurred. Maybe user/password is invalid");
		        ex.printStackTrace();
					}
					finally {
						try{
							if(conn1 != null){
								conn1.close();
							}
							if(prepStmt != null){
								prepStmt.close();
							}
						}	catch (SQLException ex) {
			        ex.printStackTrace();
						}
						
					}
				}
				
			}
		});
		btnSpieltag_abschicken.setBounds(317, 73, 89, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(btnSpieltag_abschicken);
		
		
		JLabel lblSpieltagHinzufgen = new JLabel("Spieltag hinzuf\u00FCgen (YYYY-MM-DD)");
		lblSpieltagHinzufgen.setBounds(267, 14, 198, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblSpieltagHinzufgen);
		
		
		JLabel lblPrioritt = new JLabel("Priorit\u00E4t");
		lblPrioritt.setBounds(181, 14, 46, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(lblPrioritt);
		
		JLabel label = new JLabel("Datum (YYYY-MM-DD)");
		label.setBounds(317, 164, 131, 14);
		frmMillemodiDatenbankVerwalten.getContentPane().add(label);
		
		ginv_date = new JTextField();
		ginv_date.setColumns(10);
		ginv_date.setBounds(454, 164, 86, 20);
		frmMillemodiDatenbankVerwalten.getContentPane().add(ginv_date);
		
		ginv_path = new JTextField();
		ginv_path.setColumns(10);
		ginv_path.setBounds(334, 189, 206, 20);
		frmMillemodiDatenbankVerwalten.getContentPane().add(ginv_path);
		
		JButton search_ginv_path = new JButton("...");
		search_ginv_path.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(frmMillemodiDatenbankVerwalten);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          ginv_path.setText(file.getPath());
          //This is where a real application would open the file.
          //log.append("Opening: " + file.getName() + "." + newline);
				} else {
          //log.append("Open command cancelled by user." + newline);
				}
			}
		});
		search_ginv_path.setBounds(558, 188, 28, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(search_ginv_path);
		
		JButton btnGetrnkeInventurAbschicken = new JButton(" Getr\u00E4nke Inventur abschicken");
		btnGetrnkeInventurAbschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					GInventur.ReadGinvFile(ginv_path.getText(), ginv_date.getText(), EingabeTest.url1, EingabeTest.user, EingabeTest.password);
			
				} catch (Exception ex) {
	        ex.printStackTrace();
				}
			}	
		});
		btnGetrnkeInventurAbschicken.setBounds(334, 220, 252, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(btnGetrnkeInventurAbschicken);
		
		JButton btnGetrnkeInventurEinlesen = new JButton(" Getr\u00E4nke Inventur einlesen");
		btnGetrnkeInventurEinlesen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					GInventur.WriteGinvFile(ginv_path.getText(), ginv_date.getText(), EingabeTest.url1, EingabeTest.user, EingabeTest.password);
			
				} catch (Exception ex) {
	        ex.printStackTrace();
				}
			}	
		});
		btnGetrnkeInventurEinlesen.setBounds(334, 254, 252, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(btnGetrnkeInventurEinlesen);
		
		JButton btnLebensmittelInventurEinlesen = new JButton("Lebensmittel Inventur einlesen");
		btnLebensmittelInventurEinlesen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Inventur.WriteInvFile(inv_path.getText(), inv_date.getText(), EingabeTest.url1, EingabeTest.user, EingabeTest.password);
			
				} catch (Exception ex) {
	        ex.printStackTrace();
				}
			}	
		});
		btnLebensmittelInventurEinlesen.setBounds(334, 374, 252, 23);
		frmMillemodiDatenbankVerwalten.getContentPane().add(btnLebensmittelInventurEinlesen);
	}
}
