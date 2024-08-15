package TASK2;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BorrowerManager extends JFrame {
    private Connection connect;
    private JTextField nameField, emailField, phoneField;

    public BorrowerManager(Connection connect) {
        this.connect = connect;
        createUI();
    }

    private void createUI() {
        setTitle("Borrower Management");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 20, 165, 25);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 50, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 50, 165, 25);
        panel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 80, 80, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField(20);
        phoneField.setBounds(100, 80, 165, 25);
        panel.add(phoneField);

        JButton addButton = new JButton("Add Borrower");
        addButton.setBounds(10, 110, 150, 25);
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBorrower();
            }
        });
    }

    private void addBorrower() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        try {
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO borrowers (name, email, phone) VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Borrower added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
