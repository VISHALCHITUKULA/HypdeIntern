package TASK3;
import java.sql.*;
import java.util.Scanner;

public class PayrollSystem {

	private static Connection connect() {
	    try {
	        // Load MySQL JDBC driver
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", "root");
	    } catch (ClassNotFoundException e) {
	        System.out.println("MySQL JDBC Driver not found.");
	        e.printStackTrace();
	        return null;
	    } catch (SQLException e) {
	        System.out.println("Connection Failed.");
	        e.printStackTrace();
	        return null;
	    }
	}


    private static void addEmployee(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Employee Name: ");
            String name = scanner.nextLine();
            System.out.println("Enter Designation: ");
            String designation = scanner.nextLine();
            System.out.println("Enter Basic Salary: ");
            double basicSalary = scanner.nextDouble();
            System.out.println("Enter HRA: ");
            double hra = scanner.nextDouble();
            System.out.println("Enter DA: ");
            double da = scanner.nextDouble();
            System.out.println("Enter Tax: ");
            double tax = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            String sql = "INSERT INTO employee (name, designation, basic_salary, hra, da, tax) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, designation);
            pstmt.setDouble(3, basicSalary);
            pstmt.setDouble(4, hra);
            pstmt.setDouble(5, da);
            pstmt.setDouble(6, tax);
            pstmt.executeUpdate();

            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generatePaySlip(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Employee ID: ");
            int empId = scanner.nextInt();
            System.out.println("Enter Month: ");
            String month = scanner.next();
            System.out.println("Enter Year: ");
            int year = scanner.nextInt();

            String sql = "SELECT * FROM employee WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double basicSalary = rs.getDouble("basic_salary");
                double hra = rs.getDouble("hra");
                double da = rs.getDouble("da");
                double tax = rs.getDouble("tax");

                double grossSalary = basicSalary + hra + da;
                double netSalary = grossSalary - tax;

                String insertPayslip = "INSERT INTO payslip (emp_id, month, year, gross_salary, net_salary) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt2 = conn.prepareStatement(insertPayslip);
                pstmt2.setInt(1, empId);
                pstmt2.setString(2, month);
                pstmt2.setInt(3, year);
                pstmt2.setDouble(4, grossSalary);
                pstmt2.setDouble(5, netSalary);
                pstmt2.executeUpdate();

                System.out.println("Pay slip generated successfully!");
                System.out.println("Employee ID: " + empId);
                System.out.println("Month: " + month + " Year: " + year);
                System.out.println("Gross Salary: " + grossSalary);
                System.out.println("Net Salary: " + netSalary);
            } else {
                System.out.println("Employee not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayEmployee(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Employee ID: ");
            int empId = scanner.nextInt();

            String sql = "SELECT * FROM employee WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Employee ID: " + rs.getInt("emp_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Basic Salary: " + rs.getDouble("basic_salary"));
                System.out.println("HRA: " + rs.getDouble("hra"));
                System.out.println("DA: " + rs.getDouble("da"));
                System.out.println("Tax: " + rs.getDouble("tax"));
            } else {
                System.out.println("Employee not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteEmployee(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Employee ID to delete: ");
            int empId = scanner.nextInt();

            String sql = "DELETE FROM employee WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEmployee Payroll System");
            System.out.println("1. Add Employee");
            System.out.println("2. Generate Pay Slip");
            System.out.println("3. Display Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    generatePaySlip(scanner);
                    break;
                case 3:
                    displayEmployee(scanner);
                    break;
                case 4:
                    deleteEmployee(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
