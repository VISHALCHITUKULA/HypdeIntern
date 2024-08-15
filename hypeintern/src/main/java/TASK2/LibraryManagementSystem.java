package TASK2;
import 	java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
public class LibraryManagementSystem {
	private static  Connection  con=null;
	public static  void main(String args[])
	{
		connectDatabase();
		createUI();
	}
	private static void connectDatabase() {
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db","root","root");
			System.out.println("Database connected successfully.");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	private static void createUI() {
		JFrame frame=new JFrame("Libarary Management System");
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel=new JPanel();
		frame.add(panel);
		placeComponents(panel);
		frame.setVisible(true);
	}
	
	private static void placeComponents(JPanel panel)
	{
		panel.setLayout(null);
		
		JButton bookButton=new JButton("Manage Books");
		bookButton.setBounds(100,50,200,25);
		panel.add(bookButton);
	    bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BookManager(con);
            }
        });
	    
	    JButton borrowerButton=new JButton("Manage Borrows");
	    borrowerButton.setBounds(100,90,200,25);
	    panel.add(borrowerButton);
        borrowerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BorrowerManager(con);
            }
        });
        
        JButton loanButton=new JButton("Manage loans ");
        loanButton.setBounds(100,130,200,25);
        panel.add(loanButton);
        loanButton.addActionListener(new ActionListener()
        		{
        	public void actionPerformed(ActionEvent e)
        	{
        		new LoanManager(con);
        	}
        		});
	}
	

}
