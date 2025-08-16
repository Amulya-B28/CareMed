package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import database.DatabaseConnection;

public class PrescriptionForm extends JFrame {
    private JTextField txtPatientId, txtMedicine, txtDosage, txtTime;
    private JButton btnSave;

    public PrescriptionForm() {
        setTitle("Add New Prescription");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Patient ID
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Patient ID:"), gbc);
        txtPatientId = new JTextField(20);
        gbc.gridx = 1;
        add(txtPatientId, gbc);

        // Medicine Name
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Medicine Name:"), gbc);
        txtMedicine = new JTextField(20);
        gbc.gridx = 1;
        add(txtMedicine, gbc);

        // Dosage
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Dosage:"), gbc);
        txtDosage = new JTextField(20);
        gbc.gridx = 1;
        add(txtDosage, gbc);

        // Schedule Time (HH:mm)
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Time (HH:mm):"), gbc);
        txtTime = new JTextField(20);
        gbc.gridx = 1;
        add(txtTime, gbc);

        // Save button
        btnSave = new JButton("Save Prescription");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnSave, gbc);

        btnSave.addActionListener(e -> savePrescription());
    }

    private void savePrescription() {
        String patientIdStr = txtPatientId.getText().trim();
        String medicine = txtMedicine.getText().trim();
        String dosage = txtDosage.getText().trim();
        String time = txtTime.getText().trim();

        if (patientIdStr.isEmpty() || medicine.isEmpty() || dosage.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int patientId;
        try {
            patientId = Integer.parseInt(patientIdStr);
            if (patientId <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Patient ID.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate time format HH:mm
        if (!time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
            JOptionPane.showMessageDialog(this, "Invalid time format. Use HH:mm (24-hour).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Optional: check if patient exists
            String checkPatient = "SELECT COUNT(*) FROM patients WHERE id = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkPatient);
            psCheck.setInt(1, patientId);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                JOptionPane.showMessageDialog(this, "Patient ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO prescriptions (patient_id, medicine_name, dosage, schedule_time) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, patientId);
            ps.setString(2, medicine);
            ps.setString(3, dosage);
            ps.setString(4, time + ":00");  // add seconds to match time format HH:mm:ss

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Prescription added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add prescription.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtPatientId.setText("");
        txtMedicine.setText("");
        txtDosage.setText("");
        txtTime.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PrescriptionForm().setVisible(true);
        });
    }
}
