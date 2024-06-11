package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuView extends JFrame {
    
    private JButton addBookButton;
    private JButton addUserButton;

    public MenuView() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(180, 220, 255);
                Color color2 = new Color(100, 150, 255); 
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); 

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setOpaque(false); 

        addBookButton = new JButton("Add Book");
        addBookButton.setBackground(new Color(255, 200, 100)); 
        addBookButton.setForeground(Color.WHITE); 

        addUserButton = new JButton("Add User");
        addUserButton.setBackground(new Color(255, 150, 150)); 
        addUserButton.setForeground(Color.WHITE); 
        
        Dimension buttonSize = new Dimension(150, 40);
        addBookButton.setPreferredSize(buttonSize);
        addUserButton.setPreferredSize(buttonSize);

        buttonPanel.add(addBookButton);
        buttonPanel.add(addUserButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1; 
        backgroundPanel.add(buttonPanel, gbc);

        add(backgroundPanel);

        setLocationRelativeTo(null);

        setVisible(true);
        
        addAddBookListener(null); 
        addAddUserListener(null);
    }

public void addAddBookListener(ActionListener listener) {
    addBookButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
            dispose(); 
        }
    });
}


public void addAddUserListener(ActionListener listener) {
    addUserButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MemberView memberView = new MemberView();
            memberView.setVisible(true);
            dispose();
        }
    });
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuView();
        });
    }
}
