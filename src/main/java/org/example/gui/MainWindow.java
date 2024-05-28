package org.example.gui;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.alarms.Alarm;
import org.example.listeners.AlarmEventListener;
import org.example.monitors.TypeAMonitor;
import org.example.monitors.TypeBMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class MainWindow extends JFrame
{
    private AlarmGeneratorPanel alarmGeneratorPanel;
    private MonitorPanel monitorPanel;
    private StatisticsPanel statisticsPanel;
    private ConfigurationPanel configurationPanel;
    private List<Alarm> allAlarms;
    private List<AlarmEventListener> listeners;

    public MainWindow() {
        super("Alarm Monitoring System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        allAlarms = new ArrayList<>();
        listeners = new ArrayList<>();

        statisticsPanel = new StatisticsPanel(allAlarms);
        configurationPanel = new ConfigurationPanel(statisticsPanel);
        monitorPanel = new MonitorPanel();
        alarmGeneratorPanel = new AlarmGeneratorPanel(listeners, allAlarms, configurationPanel);

        listeners.add(monitorPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Configure", configurationPanel);
        tabbedPane.addTab("Generate Alarm", alarmGeneratorPanel);
        tabbedPane.addTab("Monitor", monitorPanel);
        tabbedPane.addTab("Statistics", statisticsPanel);

        add(tabbedPane);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup(); // Apply FlatLightLaf look and feel

        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
