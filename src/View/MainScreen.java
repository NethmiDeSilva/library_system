package view;

import controller.BookDAO;
import model.Book;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class MainScreen extends JFrame {
    private JTable bookTable;
    private JTextField titleField, authorField, yearField;
    private JButton addBookButton, updateBookButton, deleteBookButton, clearButton,backButton,exitButton;
    private BookDAO bookDAO;
    private DefaultTableModel model;

    public MainScreen() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));

        JPanel panel = new JPanel(new BorderLayout());
        
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Year Published");
        
        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        inputPanel.add(new JLabel("Year Published:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        addBookButton = new JButton("Add Book");
        updateBookButton = new JButton("Update Book");
        deleteBookButton = new JButton("Delete Book");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        inputPanel.add(addBookButton);
        inputPanel.add(updateBookButton);
        inputPanel.add(deleteBookButton);
        inputPanel.add(clearButton);
        inputPanel.add(exitButton);
       

        panel.add(inputPanel, BorderLayout.SOUTH);

        add(panel);
        
         contentPane.setBackground(new Color(240, 240, 240)); 
        inputPanel.setBackground(new Color(240, 240, 240));       
        addBookButton.setBackground(new Color(30, 144, 255)); 
        addBookButton.setForeground(Color.WHITE); 
        updateBookButton.setBackground(new Color(46, 139, 87));
        updateBookButton.setForeground(Color.WHITE); 
        deleteBookButton.setBackground(new Color(220, 20, 60)); 
        deleteBookButton.setForeground(Color.WHITE); 
        clearButton.setBackground(new Color(128, 128, 128)); 
        clearButton.setForeground(Color.WHITE); 
        exitButton.setBackground(Color.yellow);
        
        try {
            bookDAO = new BookDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
            return;
        }

        updateBookList();
        
          backButton = new JButton("Back");
        
         inputPanel.add(backButton, BorderLayout.PAGE_END);
         
         backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuView menuView = new MenuView();
                menuView.setVisible(true);
                dispose(); 
            }
        });

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        updateBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    titleField.setText((String) model.getValueAt(selectedRow, 1));
                    authorField.setText((String) model.getValueAt(selectedRow, 2));
                    yearField.setText(String.valueOf(model.getValueAt(selectedRow, 3)));
                    addBookButton.setEnabled(false); 
                }
            }
        });
        
        clearFields();
        
        
        exitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
});
    }

    public void updateBookList() {
        try {
          
            List<Book> books = bookDAO.getAllBooks();

           
            model.setRowCount(0);

        
            for (Book book : books) {
                model.addRow(new Object[]{book.getBookId(), book.getTitle(), book.getAuthor(), book.getYearPublished()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String yearText = yearField.getText();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year Published must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setYearPublished(year);

        try {
            bookDAO.addBook(book);
            updateBookList();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void updateBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String title = titleField.getText();
            String author = authorField.getText();
            String yearText = yearField.getText();

            // Validate input fields
            if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int year;
            try {
                year = Integer.parseInt(yearText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Year Published must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book book = new Book();
            book.setBookId(id);
            book.setTitle(title);
            book.setAuthor(author);
            book.setYearPublished(year);

            try {
                bookDAO.updateBook(book);
                updateBookList();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        }
    }

    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);

            try {
                bookDAO.deleteBook(id);
                updateBookList();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        addBookButton.setEnabled(true); 
        bookTable.clearSelection(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
            }
        });
    }
}
