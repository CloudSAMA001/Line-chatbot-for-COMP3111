package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

@Slf4j
public class SQLDatabaseEngine extends DatabaseEngine {
	@Override
	String search(String text) throws Exception {
		//Write your code here
		
		
		String result = null;
		
	    //BufferedReader br = null;
		//InputStreamReader isr = null;
		//isr = new InputStreamReader(
         //       this.getClass().getResourceAsStream(FILENAME));
		//br = new BufferedReader(isr);
		//String sCurrentLine;
		//
		//while (result == null && (sCurrentLine = br.readLine()) != null) {
		///	String[] parts = sCurrentLine.split(":");
		//	if (text.toLowerCase().equals(parts[0].toLowerCase())) {
		//		result = parts[1];
		//	}
		//}
		
		//database search
		//Connection connection=this.getConnection();
			try {
				//String username = "pzxvjnxsfhwovm";
			    //String password = "5e2d6b3fa1345405079f96e8edd1d9f0bf0268a4d46962ab317d5ee58f93645a";
				//String dbUrl = "postgres://pzxvjnxsfhwovm:5e2d6b3fa1345405079f96e8edd1d9f0bf0268a4d46962ab317d5ee58f93645a@ec2-54-163-237-25.compute-1.amazonaws.com:5432/dbrrskp31fl3jq";
				//Connection connection = DriverManager.getConnection(dbUrl,username, password);
				//Connection connection=DriverManager.getConnection(dbUrl,username,password);
				
				
				Connection connection=this.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT response FROM lab3 where keyword=?;");
				
				stmt.setString(1, text); //or some other variables
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next())
					result=rs.getString(1);
				
				
				rs.close();
				stmt.close();
				connection.close();
				} 
			catch (Exception e) {
				System.out.println(e);
				//System.out.println("!!!!!!!!  \nsomething wrong with the db account\n\n!!!!!!!\n");
			}
			
			
			//System.out.printf("\n~!!!!!!!!!!!!!!!!!!!!");

		if (result != null) {
			//System.out.println("!!!!!!!!!!FIND THE TARGET RESULT!!!\n");
			//System.out.printf("\n~~~~~~~~~~~");
			System.out.printf(result);
			
			return result;}
		//throw new Exception("NOT FOUND");
		
		
		
		return text;
	}
	
	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		log.info("Username: {} Password: {}", username, password);
		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}

}
