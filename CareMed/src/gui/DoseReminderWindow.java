package gui;

import javax.swing.*;
import java.awt.*;

/**
 * GUI window to display dose reminder log or summary.
 * Background reminder popup is handled by DoseReminderService.
 */
public class DoseReminderWindow extends JFrame {

    private JTextArea reminderLog;

    public DoseReminderWindow() {
        setTitle("Dose Reminder Log");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        reminderLog = new JTextArea();
        reminderLog.setEditable(false);
        reminderLog.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(reminderLog);

        add(scrollPane, BorderLayout.CENTER);

        // Add dummy reminder log for now
        reminderLog.setText("Reminders will pop up when scheduled time matches.\n\n"
                + "This is a log window.\n\n"
                + "Make sure DoseReminderService is running in background.");
    }
}
