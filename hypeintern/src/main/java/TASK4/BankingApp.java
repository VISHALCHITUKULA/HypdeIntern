package TASK4;
import java.sql.*;
import java.util.Scanner;

public class BankingApp {

    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/online_banking", "root", "password");
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

    private static void createAccount(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Account Holder's Name: ");
            String holderName = scanner.nextLine();
            System.out.println("Enter Initial Balance: ");
            double initialBalance = scanner.nextDouble();
            scanner.nextLine(); 

            String sql = "INSERT INTO account (account_holder, balance) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, holderName);
            pstmt.setDouble(2, initialBalance);
            pstmt.executeUpdate();

            System.out.println("Account created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deposit(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Account ID: ");
            int accountId = scanner.nextInt();
            System.out.println("Enter Amount to Deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); 
            String sql = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
            
            String transactionSql = "INSERT INTO transaction (account_id, type, amount) VALUES (?, 'DEPOSIT', ?)";
            PreparedStatement pstmt2 = conn.prepareStatement(transactionSql);
            pstmt2.setInt(1, accountId);
            pstmt2.setDouble(2, amount);
            pstmt2.executeUpdate();

            System.out.println("Deposit successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void withdraw(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Account ID: ");
            int accountId = scanner.nextInt();
            System.out.println("Enter Amount to Withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); 

            String checkSql = "SELECT balance FROM account WHERE account_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, accountId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String updateSql = "UPDATE account SET balance = balance - ? WHERE account_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setDouble(1, amount);
                    updateStmt.setInt(2, accountId);
                    updateStmt.executeUpdate();

                    String transactionSql = "INSERT INTO transaction (account_id, type, amount) VALUES (?, 'WITHDRAWAL', ?)";
                    PreparedStatement pstmt = conn.prepareStatement(transactionSql);
                    pstmt.setInt(1, accountId);
                    pstmt.setDouble(2, amount);
                    pstmt.executeUpdate();

                    System.out.println("Withdrawal successful!");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void transfer(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Source Account ID: ");
            int sourceId = scanner.nextInt();
            System.out.println("Enter Destination Account ID: ");
            int destId = scanner.nextInt();
            System.out.println("Enter Amount to Transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); 

            String checkSql = "SELECT balance FROM account WHERE account_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, sourceId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String updateSourceSql = "UPDATE account SET balance = balance - ? WHERE account_id = ?";
                    PreparedStatement updateSourceStmt = conn.prepareStatement(updateSourceSql);
                    updateSourceStmt.setDouble(1, amount);
                    updateSourceStmt.setInt(2, sourceId);
                    updateSourceStmt.executeUpdate();

                    String updateDestSql = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
                    PreparedStatement updateDestStmt = conn.prepareStatement(updateDestSql);
                    updateDestStmt.setDouble(1, amount);
                    updateDestStmt.setInt(2, destId);
                    updateDestStmt.executeUpdate();

                    String transactionSql = "INSERT INTO transaction (account_id, type, amount) VALUES (?, 'TRANSFER', ?)";
                    PreparedStatement pstmt1 = conn.prepareStatement(transactionSql);
                    pstmt1.setInt(1, sourceId);
                    pstmt1.setDouble(2, amount);
                    pstmt1.executeUpdate();

                    PreparedStatement pstmt2 = conn.prepareStatement(transactionSql);
                    pstmt2.setInt(1, destId);
                    pstmt2.setDouble(2, amount);
                    pstmt2.executeUpdate();

                    System.out.println("Transfer successful!");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Source account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewTransactionHistory(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Account ID: ");
            int accountId = scanner.nextInt();
            scanner.nextLine();

            String sql = "SELECT * FROM transaction WHERE account_id = ? ORDER BY transaction_date DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getInt("transaction_id"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Amount: " + rs.getDouble("amount"));
                System.out.println("Date: " + rs.getTimestamp("transaction_date"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkBalance(Scanner scanner) {
        try (Connection conn = connect()) {
            System.out.println("Enter Account ID: ");
            int accountId = scanner.nextInt();
            scanner.nextLine();

            String sql = "SELECT balance FROM account WHERE account_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Current Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOnline Banking System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Transaction History");
            System.out.println("6. Check Balance");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    deposit(scanner);
                    break;
                case 3:
                    withdraw(scanner);
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    viewTransactionHistory(scanner);
                    break;
                case 6:
                    checkBalance(scanner);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
