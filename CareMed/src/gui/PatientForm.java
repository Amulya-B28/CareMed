package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import database.DatabaseConnection;

public class PatientForm extends JFrame {
    private JTextField txtName, txtAge, txtContact;
    private JButton btnSave;

    public PatientForm() {
        setTitle("Add New Patient");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        txtName = new JTextField(20);
        gbc.gridx = 1;
        add(txtName, gbc);

        // Age
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Age:"), gbc);
        txtAge = new JTextField(20);
        gbc.gridx = 1;
        add(txtAge, gbc);

        // Contact
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Contact:"), gbc);
        txtContact = new JTextField(20);
        gbc.gridx = 1;
        add(txtContact, gbc);

        // Save button
        btnSave = new JButton("Save Patient");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnSave, gbc);

        btnSave.addActionListener(e -> savePatient());
    }

    private void savePatient() {
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String contact = txtContact.getText().trim();

        if (name.isEmpty() || ageStr.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age entered.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO patients (name, age, contact) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, contact);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtAge.setText("");
        txtContact.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PatientForm().setVisible(true);
        });
    }
}
