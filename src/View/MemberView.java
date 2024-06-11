package view;

import controller.MemberDAO;
import model.Member;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MemberView extends JFrame {
    private JTable memberTable;
    private JTextField nameField, mobileField, addressField;
    private JButton addMemberButton, updateMemberButton, deleteMemberButton, clearButton,exitButton,backButton;
    private MemberDAO memberDAO;
    private DefaultTableModel model;

    public MemberView() {
        setTitle("Member Management System");
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
        model.addColumn("Name");
        model.addColumn("Mobile");
        model.addColumn("Address");

        memberTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(memberTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Mobile:"));
        mobileField = new JTextField();
        inputPanel.add(mobileField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        addMemberButton = new JButton("Add Member");
        updateMemberButton = new JButton("Update Member");
        deleteMemberButton = new JButton("Delete Member");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        inputPanel.add(addMemberButton);
        inputPanel.add(updateMemberButton);
        inputPanel.add(deleteMemberButton);
        inputPanel.add(clearButton);
         inputPanel.add(exitButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        add(panel);
        
        contentPane.setBackground(new Color(240, 240, 240)); 
        inputPanel.setBackground(new Color(240, 240, 240));  
        addMemberButton.setBackground(new Color(30, 144, 255)); 
        addMemberButton.setForeground(Color.WHITE); 
        updateMemberButton.setBackground(new Color(46, 139, 87)); 
        updateMemberButton.setForeground(Color.WHITE); 
        deleteMemberButton.setBackground(new Color(220, 20, 60)); 
        deleteMemberButton.setForeground(Color.WHITE); 
        clearButton.setBackground(new Color(128, 128, 128)); 
        clearButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.yellow);

        try {
            memberDAO = new MemberDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
            return;
        }

        backButton = new JButton("Back");
         inputPanel.add(backButton, BorderLayout.PAGE_END);
        updateMemberList();
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuView menuView = new MenuView();
                menuView.setVisible(true);
                dispose(); 
            }
        });

        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });

        updateMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMember();
            }
        });

        deleteMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        memberTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = memberTable.getSelectedRow();
                if (selectedRow != -1) {
                    nameField.setText((String) model.getValueAt(selectedRow, 1));
                    mobileField.setText((String) model.getValueAt(selectedRow, 2));
                    addressField.setText((String) model.getValueAt(selectedRow, 3));
                    addMemberButton.setEnabled(false); 
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

    public void updateMemberList() {
        try {
            List<Member> members = memberDAO.getAllMembers();

            model.setRowCount(0);

            for (Member member : members) {
                model.addRow(new Object[]{member.getMemberId(), member.getName(), member.getMobile(), member.getAddress()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void addMember() {
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member member = new Member();
        member.setName(name);
        member.setMobile(mobile);
        member.setAddress(address);

        try {
            memberDAO.addMember(member);
            updateMemberList();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void updateMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String mobile = mobileField.getText();
            String address = addressField.getText();

            // Validate input fields
            if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Member member = new Member();
            member.setMemberId(id);
            member.setName(name);
            member.setMobile(mobile);
            member.setAddress(address);

            try {
                memberDAO.updateMember(member);
                updateMemberList();
                clearFields();
                       } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        }
    }

    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);

            try {
                memberDAO.deleteMember(id);
                updateMemberList();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        mobileField.setText("");
        addressField.setText("");
        addMemberButton.setEnabled(true); 
        memberTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MemberView memberView = new MemberView();
            memberView.setVisible(true);
        });
    }
}

