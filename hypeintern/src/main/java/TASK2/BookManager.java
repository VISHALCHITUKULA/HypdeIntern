package TASK2;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BookManager extends JFrame {
    private Connection connect;
    private JTextField titleField, authorField, categoryField;

    public BookManager(Connection connect) {
        this.connect = connect;
        createUI();
    }

    private void createUI() {
        setTitle("Book Management");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(10, 20, 80, 25);
        panel.add(titleLabel);

        titleField = new JTextField(20);
        titleField.setBounds(100, 20, 165, 25);
        panel.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(10, 50, 80, 25);
        panel.add(authorLabel);

        authorField = new JTextField(20);
        authorField.setBounds(100, 50, 165, 25);
        panel.add(authorField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(10, 80, 80, 25);
        panel.add(categoryLabel);

        categoryField = new JTextField(20);
        categoryField.setBounds(100, 80, 165, 25);
        panel.add(categoryField);

        JButton addButton = new JButton("Add Book");
        addButton.setBounds(10, 110, 150, 25);
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();

        try {
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO books (title, author, category) VALUES (?, ?, ?)");
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
