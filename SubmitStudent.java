//Valid Program

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SubmitStudent {


    static int rollNo = 1;
  

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> ShowGUI());    //to ensure contents are displayed properly
    }

    private static void ShowGUI() {
    	
    	//Database variables
 	   String tableName = "Mystudents";
 	   String dbname = "SMS";
 	   String username = "postgres";
 	   String password = "YourStrong@Passw0rd";
    	//Database Coonection
    	 DBoperations database = new DBoperations();
    	database.connectDB(dbname,username, password);
    	
    	//creating table
  	   database.createTable(tableName);
  	        
    
   	   
		JFrame frame = new JFrame("Student Management System");
		frame.setSize(700,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		//For name
		JLabel lname = new JLabel("Name : ");
		lname.setBounds(50, 10, 80, 25);
		frame.add(lname);
		JTextField tname = new JTextField();
		tname.setBounds(50, 40, 100, 25);
		frame.add(tname);
		
		//For Roll no
		JLabel lroll = new JLabel("Roll No : ");
		lroll.setBounds(50, 70, 80, 25);
		frame.add(lroll);
		JTextField troll = new JTextField();
		troll.setBounds(50, 100, 100, 25);
		troll.setEditable(false);
		frame.add(troll);
		
		//For Gender
		JLabel lgender = new JLabel("Gender : ");
		lgender.setBounds(50, 150, 80, 25);
		frame.add(lgender);
		
		JRadioButton rmale = new JRadioButton("Male");
        JRadioButton rfemale = new JRadioButton("Female");
        JRadioButton rothers = new JRadioButton("Others");
        
        rmale.setBounds(50,180,80,25);
        rfemale.setBounds(130,180,80,25);
        rothers.setBounds(210,180,80,25);
        
        
        frame.add(rmale);
        frame.add(rfemale);
        frame.add(rothers);

        // Grouping the radio buttons to ensure only one can be selected
        ButtonGroup rgroup = new ButtonGroup();
        rgroup.add(rmale);
        rgroup.add(rfemale);
        rgroup.add(rothers);
        
        //For Favorite foods
        JLabel lfood = new JLabel("Favourite Foods : ");
		lfood.setBounds(50, 230, 100, 25);
		frame.add(lfood);
		
		JCheckBox cchowmin = new JCheckBox("Chowmin");
        JCheckBox csamosa = new JCheckBox("Samosa");
        JCheckBox cparothaa = new JCheckBox("Parothaa");
        
        cchowmin.setBounds(50, 260, 100, 25);
        csamosa.setBounds(50, 280, 100, 25);
        cparothaa.setBounds(50, 300, 100, 25);
        
        frame.add(cchowmin);
        frame.add(csamosa);
        frame.add(cparothaa);
        

        
        //For Submit
        
        JButton bsubmit = new JButton("Submit");
        bsubmit.setBounds(50, 350, 100, 25);
        frame.add(bsubmit);

        
        
        //Output Section
        
        //output name
        JLabel lstudent = new JLabel("Students Record");
        lstudent.setBounds(400, 10, 200, 25);
        frame.add(lstudent);
        
        //output display area
        JTextArea displayStudent = new JTextArea();
        displayStudent.setBounds(300,40,320,400);
        frame.add(displayStudent);
        
        //scroll option
        JScrollPane scroll = new JScrollPane(displayStudent);
        scroll.setBounds(300,40,320,400);
        frame.add(scroll);
      
        
        
        //for roll number assignment
      rollNo =   database.lastRollno(tableName) +1;
     
        troll.setText(String.valueOf(rollNo));
        
        //Display database for first time running application
        database.readData(tableName, displayStudent);
        
        //*******************************************//
        
//        For Updating Data through roll no
        JLabel lupdate = new JLabel("By Roll No :");
        lupdate.setBounds(50, 400, 100, 25);
        frame.add(lupdate);
        JTextField tupdate = new JTextField();
        tupdate.setBounds(180, 400, 100, 25);
        frame.add(tupdate);
        JButton bupdate = new JButton("Update");
        bupdate.setBounds(100, 450, 100, 25);
        frame.add(bupdate);
        
        bupdate.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e)
        	{
     		   String gender="";
    		   String foodChoice ="";
    		 //radio logic 
    		   if(rmale.isSelected())
    			   gender = "Male";
    		   else if(rfemale.isSelected())
    			   gender = "Female";
    		   else if(rothers.isSelected())
    			   gender = "Others";
    		   else
    			   gender = "Not Selected";
    		   
    		   //checkbox logic
    		   if(cchowmin.isSelected())
    			   foodChoice += "Chowmin ";
    		   if(csamosa.isSelected())
    			   foodChoice += "Samosa ";
    		   if(cparothaa.isSelected())
    			   foodChoice += "parothaa ";
    		
    		   displayStudent.setText(null);
    		   //assigning new values
    		String name = tname.getText();
    		int updateroll = Integer.valueOf(tupdate.getText());
        		database.updateRow(tableName,updateroll,name,gender,foodChoice);
        		database.readData(tableName, displayStudent);
        		tupdate.setText(null);
     		   tname.setText(null);
     		   rgroup.clearSelection();
     		   cchowmin.setSelected(false);
     		   csamosa.setSelected(false);
     		   cparothaa.setSelected(false);
        		
        	}
        });
       
        
        
        //***************************//
        //For delete
        JButton bdelete = new JButton("Delete");
        bdelete.setBounds(180,350,100,25);
        frame.add(bdelete);
        
        bdelete.addActionListener(new ActionListener(){
     	   @Override
     	   public void actionPerformed(ActionEvent e)
     	   {
     		  database.deleteRow(tableName, tname.getText()); 
   	       displayStudent.setText(null);
     		  database.readData(tableName,displayStudent);
   		    tname.setText(null);
   		   rgroup.clearSelection();
   		   cchowmin.setSelected(false);
   		   csamosa.setSelected(false);
   		   cparothaa.setSelected(false);
   		   //if old roll no gets vacant
   	      rollNo =   database.lastRollno(tableName) +1;
          troll.setText(String.valueOf(rollNo));
     		   
     	   }});
     //***************************************//
        
        
       
        //Logic Section for submit
       bsubmit.addActionListener(new ActionListener(){
    	   @Override
    	   public void actionPerformed(ActionEvent e)
    	   {
    		 
    		   
    		   String gender="";
    		   String foodChoice ="";
    		 //radio logic 
    		   if(rmale.isSelected())
    			   gender = "Male";
    		   else if(rfemale.isSelected())
    			   gender = "Female";
    		   else if(rothers.isSelected())
    			   gender = "Others";
    		   else
    			   gender = "Not Selected";
    		   
    		   //checkbox logic
    		   if(cchowmin.isSelected())
    			   foodChoice += "Chowmin ";
    		   if(csamosa.isSelected())
    			   foodChoice += "Samosa ";
    		   if(cparothaa.isSelected())
    			   foodChoice += "parothaa ";
    		
    		   //assigning new values
//    		String rollno =   String.valueOf(rollNo);
    		String name = tname.getText();
    		   
    		 //Inserting data in database
    		   database.insertRow(tableName,rollNo, name, gender, foodChoice);
    		   //Reading data from database
    	       displayStudent.setText(null);
    		   database.readData(tableName,displayStudent);
    		       		      
    			   
    		 // resetting values    		  
    		   rollNo++;
    		   troll.setText(String.valueOf(rollNo));
    		   tname.setText(null);
    		   rgroup.clearSelection();
    		   cchowmin.setSelected(false);
    		   csamosa.setSelected(false);
    		   cparothaa.setSelected(false);
    		   
    		   
    		  

    	    }
       });

	}
}
        