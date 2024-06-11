package view;

import controller.UserDAO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {
        setTitle("Library Management System - Login");
         setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
      
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(72, 61, 139);
                Color color2 = new Color(123, 104, 238);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        mainPanel.add(titleLabel, gbc);
      
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(customFont);
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(customFont);
        usernameField.setBorder(new LineBorder(Color.WHITE, 1));
        usernameField.setBackground(new Color(255, 255, 255, 200));
        usernameField.setForeground(Color.BLACK);
        usernameField.setCaretColor(Color.BLACK);
        usernameField.setMargin(new Insets(5, 5, 5, 5));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(customFont);
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(customFont);
        passwordField.setBorder(new LineBorder(Color.WHITE, 1));
        passwordField.setBackground(new Color(255, 255, 255, 200));
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setMargin(new Insets(5, 5, 5, 5));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(customFont);
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new LineBorder(Color.WHITE, 1));
        loginButton.setOpaque(true);
        loginButton.setMargin(new Insets(10, 20, 10, 20)); 
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(50, 30, 50, 30);
        gbc.anchor = GridBagConstraints.CENTER;
        Dimension buttonSize = new Dimension(100, 40);
        loginButton.setPreferredSize(buttonSize);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserDAO userDAO = new UserDAO();
                    boolean valid = userDAO.validateUser(usernameField.getText(), new String(passwordField.getPassword()));
                    if (valid) {
                        MenuView menuView = new MenuView();
                        menuView.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        });
        mainPanel.add(loginButton, gbc);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
