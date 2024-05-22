package org.example.gui;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.monitors.TypeAMonitor;
import org.example.monitors.TypeBMonitor;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{
    public MainWindow() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Alarm Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        TypeAMonitor typeAMonitor = new TypeAMonitor();
        TypeBMonitor typeBMonitor = new TypeBMonitor();

        MonitorPanel monitorPanel = new MonitorPanel(typeAMonitor, typeBMonitor);
        AlarmGeneratorPanel alarmGeneratorPanel = new AlarmGeneratorPanel(typeAMonitor, typeBMonitor, monitorPanel);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(alarmGeneratorPanel, BorderLayout.NORTH);
        mainPanel.add(monitorPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
