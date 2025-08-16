package services;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class DoseReminderWindow extends JFrame {

    private JTextArea logArea;
    private JButton toggleButton;
    private Timer timer;
    private boolean isRunning = false;

    public DoseReminderWindow() {
        setTitle("Dose Reminder Service");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        toggleButton = new JButton("Start Reminder");
        toggleButton.setFocusPainted(false);
        toggleButton.setBackground(new Color(0, 153, 76));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        toggleButton.addActionListener(this::toggleReminder);
        add(toggleButton, BorderLayout.SOUTH);
    }

    private void toggleReminder(ActionEvent e) {
        if (!isRunning) {
            startReminderService();
            toggleButton.setText("Stop Reminder");
            toggleButton.setBackground(new Color(204, 0, 0));
            isRunning = true;
        } else {
            stopReminderService();
            toggleButton.setText("Start Reminder");
            toggleButton.setBackground(new Color(0, 153, 76));
            isRunning = false;
        }
    }

    private void startReminderService() {
        timer = new Timer();
        timer.schedule(new ReminderTask(), 0, 60000); // Every 60 seconds
        log("Reminder service started...");
    }

    private void stopReminderService() {
        if (timer != null) {
            timer.cancel();
            log("Reminder service stopped.");
        }
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append("[" + LocalTime.now().withNano(0) + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private class ReminderTask extends TimerTask {
        @Override
        public void run() {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String now = LocalTime.now().withSecond(0).withNano(0).toString();

                String sql = "SELECT p.name, pr.medicine_name FROM prescriptions pr " +
                             "JOIN patients p ON pr.patient_id = p.id " +
                             "WHERE pr.schedule_time = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, now);
                ResultSet rs = ps.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String reminder = "Give " + rs.getString("medicine_name") + " to " + rs.getString("name");
                    log(reminder);
                    JOptionPane.showMessageDialog(DoseReminderWindow.this, reminder, "Medicine Reminder", JOptionPane.INFORMATION_MESSAGE);
                }

                if (!found) {
                    log("No reminders at this time.");
                }

            } catch (Exception ex) {
                log("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // For standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DoseReminderWindow().setVisible(true);
        });
    }
}
