package TASK2;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoanManager extends JFrame {
    private Connection connect;
    private JTextField bookIdField, borrowerIdField, dueDateField;

    public LoanManager(Connection connect) {
        this.connect = connect;
        createUI();
    }

    private void createUI() {
        setTitle("Loan Management");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(10, 20, 80, 25);
        panel.add(bookIdLabel);

        bookIdField = new JTextField(20);
        bookIdField.setBounds(100, 20, 165, 25);
        panel.add(bookIdField);

        JLabel borrowerIdLabel = new JLabel("Borrower ID:");
        borrowerIdLabel.setBounds(10, 50, 80, 25);
        panel.add(borrowerIdLabel);

        borrowerIdField = new JTextField(20);
        borrowerIdField.setBounds(100, 50, 165, 25);
        panel.add(borrowerIdField);

        JLabel dueDateLabel = new JLabel("Due Date (yyyy-mm-dd):");
        dueDateLabel.setBounds(10, 80, 150, 25);
        panel.add(dueDateLabel);

        dueDateField = new JTextField(20);
        dueDateField.setBounds(160, 80, 105, 25);
        panel.add(dueDateField);

        JButton issueButton = new JButton("Issue Loan");
        issueButton.setBounds(10, 110, 150, 25);
        panel.add(issueButton);
        issueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                issueLoan();
            }
        });
    }

    private void issueLoan() {
        int bookId = Integer.parseInt(bookIdField.getText());
        int borrowerId = Integer.parseInt(borrowerIdField.getText());
        String dueDate = dueDateField.getText();

        try {
            PreparedStatement stmt = connect.prepareStatement(
                "INSERT INTO loans (book_id, borrower_id, loan_date, due_date) VALUES (?, ?, CURDATE(), ?)"
            );
            stmt.setInt(1, bookId);
            stmt.setInt(2, borrowerId);
            stmt.setDate(3, java.sql.Date.valueOf(dueDate));
            stmt.executeUpdate();

            PreparedStatement updateBookStmt = connect.prepareStatement(
                "UPDATE books SET available = FALSE WHERE book_id = ?"
            );
            updateBookStmt.setInt(1, bookId);
            updateBookStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Loan issued successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

