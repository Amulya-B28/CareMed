package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainDashboard extends JFrame {

    private String username;
    private JLabel welcomeLabel;

    public MainDashboard(String username) {
        this.username = username;

        setTitle("CareMed - Main Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 102, 204));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 102, 102));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginWindow login = new LoginWindow();
                    login.setVisible(true);
                });
            }
        });
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Main feature panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createFeatureCard("Patients", "Manage patient info", e -> openPatientsModule()));
        mainPanel.add(createFeatureCard("Prescriptions", "Manage prescriptions", e -> openPrescriptionsModule()));
        mainPanel.add(createFeatureCard("Medicine Schedules", "Set & view schedules", e -> openSchedulesModule()));
        mainPanel.add(createFeatureCard("Dose Reminders", "View upcoming doses", e -> openDoseRemindersModule()));
        mainPanel.add(createFeatureCard("Reports", "Export and view reports", e -> openReportsModule()));
        mainPanel.add(createFeatureCard("Settings", "Configure app settings", e -> openSettingsModule()));

        add(mainPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("CareMed © 2025 - Developed by You", SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createFeatureCard(String title, String description, ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(230, 240, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 51, 153));
        card.add(titleLabel, BorderLayout.NORTH);

        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(new Color(50, 50, 50));
        card.add(descLabel, BorderLayout.CENTER);

        JButton openButton = new JButton("Open");
        openButton.setBackground(new Color(0, 102, 204));
        openButton.setForeground(Color.WHITE);
        openButton.setFocusPainted(false);
        openButton.addActionListener(action);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255));
        buttonPanel.add(openButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    // Module handlers

    private void openSchedulesModule() {
        SwingUtilities.invokeLater(() -> {
            new MedicineScheduleForm().setVisible(true);
        });
    }

    private void openDoseRemindersModule() {
        SwingUtilities.invokeLater(() -> {
            try {
                new DoseReminderWindow().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to open reminders window.");
            }
        });
    }

    private void openReportsModule() {
        JOptionPane.showMessageDialog(this, "Open Reports Module (TODO)");
    }

    private void openSettingsModule() {
        JOptionPane.showMessageDialog(this, "Open Settings Module (TODO)");
    }

    private void openPatientsModule() {
    SwingUtilities.invokeLater(() -> new gui.PatientForm().setVisible(true));
}

private void openPrescriptionsModule() {
    SwingUtilities.invokeLater(() -> new gui.PrescriptionForm().setVisible(true));
}





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainDashboard dashboard = new MainDashboard("Caretaker");
            dashboard.setVisible(true);
        });
    }
}
