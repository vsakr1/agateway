package sat.test.automation.utils;

import  java.sql.Connection;        
import  java.sql.Statement;     
import  java.sql.ResultSet;     
import  java.sql.DriverManager;     
import  java.sql.SQLException;      
import java.util.ArrayList;

import sat.test.automation.core.SuperScript;

public class  SQLConnector extends SuperScript {
	public static final String DBURL = config.get("DBURL");
	public static final String DBUSER = config.get("DBUSER");
	public static final String DBPASS = config.get("DBPASS");

	public static String QueryResult;
	public static ArrayList<String> list = new ArrayList<String>();
	String dbUrl = "jdbc:mysql:///test?allowMultiQueries=true";
	
	public static ArrayList<String> DB_ExecuteQuery(String Query) throws Exception
	{
		//Load jdbc driver        
	    Class.forName("oracle.jdbc.OracleDriver");
	
	    //Create Connection to DB       
	    Connection con = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
	    //System.out.println("Connection Established to DB");
	   System.out.println("DBURL,DBUSER,DBPASS" +DBURL+DBUSER+DBPASS);
	    //Create Statement Object       
	    Statement stmt = con.createStatement();                  
	   // System.out.println("Created Statement Object");
	    
	    // Execute the SQL Query. Store results in ResultSet 
	    if(Query.contains("DELETE") || Query.contains("UPDATE") || Query.contains("CREATE")){
	    	int rs= stmt.executeUpdate(Query);    
            System.out.println("Executed the SQL Query");
            QueryResult=rs+" rows deleted.";
            list.clear();
            list.add(QueryResult);
            con.close();
    		return list;
	    }
	    else if(Query.contains("COMMIT")){
	    	int rs= stmt.executeUpdate(Query);    
            System.out.println("Executed the SQL Query");
            QueryResult="committed";
            list.clear();
            list.add(QueryResult);
            con.close();
    		return list;
	    }
        else
        {
        	ResultSet rs= stmt.executeQuery(Query);  
        	int cc=rs.getMetaData().getColumnCount();
        	list.clear();
        	int rowCount=0;
        	while (rs.next()){
            	rowCount++;
            	for (int i = 1; i <=cc; i++) {
	            	String cn=rs.getMetaData().getColumnName(i);
	            	System.out.println("Rown Numbers: "+rs.getRow());
	    	    	QueryResult=cn+" = "+rs.getString(i);
	    	    	System.out.println(QueryResult);
	    	    	list.add(QueryResult);
	    	    	//list.add("<br>");
            	}
            list.add("<br><br>");
            }
 	    	list.add("No. Of Rows Fetched: "+rowCount+"<br>");
 	    	con.close();
            return list;
    	}
	}
}