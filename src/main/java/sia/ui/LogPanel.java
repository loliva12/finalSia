package sia.ui;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {
    private JTextArea logArea;

    public LogPanel() {
        setLayout(new BorderLayout());
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);
        add(scroll, BorderLayout.CENTER);
    }

    public void clearLog() {
        logArea.setText("");
    }

    public void appendLog(String text) {
        logArea.append(text);
    }

    public JTextArea getLogArea() {
        return logArea;
    }
}
