//Valid Program

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DBoperations {
	Connection connection = null;

public Connection connectDB(String dbname,String username,String password)
 {
	try
	{
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,username,password);
		if(connection!= null)
			System.out.println("Connection Established");
		else
			System.out.println("Connection Failed");
	}
	catch (Exception e)
	{
		System.out.println(e );
	}
	return connection;
	
 }

public void createTable(String tableName)
{
	Statement statement;
	try {
		String query = "CREATE TABLE "+tableName+" (rollno int primary key, name varchar(25), gender varchar(29), favfoods varchar(50));" ;
	    statement = connection.createStatement();
	    statement.execute(query);
	    System.out.println("Table Created");
	}
	catch (Exception e)
	{
		
		System.out.println(e);
	}
	
}

public void insertRow(String tableName,int rollno,String name,String gender,String favfoods)
{
Statement statement;
try
{
 String query = String.format("INSERT INTO %s(rollno,name,gender,favfoods) VALUES('%d','%s','%s','%s');", tableName,rollno,name,gender,favfoods);
statement = connection.createStatement();
 statement.execute(query);    //executeUpdate
 System.out.println("Data Inserted Successfully");
}
catch (Exception e)
{
	System.out.println(e);
	}
}
public void deleteRow(String tableName,String name)
{
	Statement statement;
	try
	{
		String query = String.format("DELETE FROM %s WHERE name='%s'",tableName,name);
		statement = connection.createStatement();
		statement.execute(query);     //exceuteUpdate
		System.out.println("Data Deleted Successfully");
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}

public void updateRow(String tableName,int rollno,String name,String gender,String favfoods)
{
	Statement statement;
	try {
	String query = String.format("UPDATE %s SET name='%s',gender='%s',favfoods='%s' WHERE rollno=%d;",tableName,name,gender,favfoods,rollno);
	statement = connection.createStatement();
	statement.execute(query);     //executeUpdate
	System.out.println("Updated Successfully");
	}
	catch (Exception e)
	{
		System.out.println(e);
	}
	
}

public void readData( String tableName,JTextArea textarea){
    Statement statement;
    ResultSet rs=null;
    String roll = "";
    String name = "";
    String gender = "";
    String favfoods = "";
    
    try {
    	
        String query=String.format("SELECT * FROM %s order by rollno asc",tableName);
        statement=connection.createStatement();
        rs=statement.executeQuery(query);
        while(rs.next()){
        	roll = "Roll no:"+rs.getString("rollno");
        	name = "Name:"+rs.getString("name");
        	gender = "Gender:"+rs.getString("gender");
        	favfoods = "Favourite Foods:"+rs.getString("favfoods");
        	textarea.append("\n"+roll+"\n"+name+"\n"+gender+"\n"+favfoods+"\n");
            System.out.println(roll);
            System.out.println(name);
            System.out.println(gender);
            System.out.println(favfoods);
            System.out.printf("\n");
        }

    }
    catch (Exception e){
        System.out.println(e);
    }
}

public int lastRollno( String tableName){
    Statement statement;
    int lastroll = 0;
    int temproll = 0;
    ResultSet rs=null;
    try {
    	
        String query=String.format("SELECT * FROM %s",tableName);
        statement=connection.createStatement();
        rs=statement.executeQuery(query);
        while(rs.next()){
          lastroll =   rs.getInt("rollno");
          if(temproll>lastroll)
        	  continue;
          else
        	  temproll = lastroll;
//          System.out.println(rs.getString(lastroll));
//          System.out.println("Last roll = "+lastroll);
        }
        
     
    }
    catch (Exception e){			
        System.out.println(e);
    }
    return temproll;
}


}
