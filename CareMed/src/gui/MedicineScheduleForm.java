package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import database.DatabaseConnection;

public class MedicineScheduleForm extends JFrame {
    private JTextField txtPatientId, txtMedicine, txtDosage, txtTime;

    public MedicineScheduleForm() {
        setTitle("Add Medicine Schedule");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lblPatientId = new JLabel("Patient ID:");
        lblPatientId.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblPatientId, gbc);

        txtPatientId = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtPatientId, gbc);

        JLabel lblMedicine = new JLabel("Medicine Name:");
        lblMedicine.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblMedicine, gbc);

        txtMedicine = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtMedicine, gbc);

        JLabel lblDosage = new JLabel("Dosage:");
        lblDosage.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblDosage, gbc);

        txtDosage = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDosage, gbc);

        JLabel lblTime = new JLabel("Time (HH:MM):");
        lblTime.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblTime, gbc);

        txtTime = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtTime, gbc);

        JButton btnAdd = new JButton("Add Schedule");
        btnAdd.setBackground(new Color(0, 102, 204));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(150, 35));
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> saveSchedule());

        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAdd, gbc);

        add(panel);
    }

    private void saveSchedule() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO prescriptions (patient_id, medicine_name, dosage, schedule_time) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtPatientId.getText()));
            ps.setString(2, txtMedicine.getText());
            ps.setString(3, txtDosage.getText());
            ps.setString(4, txtTime.getText() + ":00");  // append seconds

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Schedule saved.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving schedule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
