package org.example.gui;

import org.example.alarms.Alarm;
import org.example.alarms.GasAlarm;
import org.example.listeners.AlarmEventListener;
import org.example.monitors.TypeAMonitor;
import org.example.monitors.TypeBMonitor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonitorPanel extends JPanel implements AlarmEventListener
{
    private JTable alarmTable;
    private DefaultTableModel tableModel;
    private JButton handledButton;
    private boolean detailsViewed = false;
    private Set<Alarm> displayedAlarms = new HashSet<>();

    public MonitorPanel() {
        setLayout(new BorderLayout(10, 10));

        // Components for monitoring alarms
        String[] columnNames = {"Type", "Location", "Date", "Importance", "Intended Service"};
        tableModel = new DefaultTableModel(columnNames, 0);
        alarmTable = new JTable(tableModel);
        alarmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(alarmTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton detailsButton = new JButton("Details");
        handledButton = new JButton("Handled");
        handledButton.setEnabled(false);
        JButton closeButton = new JButton("Close");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailsButton);
        buttonPanel.add(handledButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener for detailsButton
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAlarmDetails();
            }
        });

        // Add action listener for handledButton
        handledButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAlarm();
            }
        });

        // Add action listener for closeButton
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void alarmGenerated(Alarm alarm) {
        addAlarmToTable(alarm);
    }

    private void addAlarmToTable(Alarm alarm) {
        tableModel.addRow(new Object[]{
                alarm.getAlarmType(),
                alarm.getLocation(),
                alarm.getDate(),
                alarm.getImportanceLevel(),
                alarm.getIntendedService()
        });
        displayedAlarms.add(alarm);
    }

    private void showAlarmDetails() {
        int selectedRow = alarmTable.getSelectedRow();
        if (selectedRow >= 0) {
            String details = "Type: " + tableModel.getValueAt(selectedRow, 0) + "\n" +
                    "Location: " + tableModel.getValueAt(selectedRow, 1) + "\n" +
                    "Date: " + tableModel.getValueAt(selectedRow, 2) + "\n" +
                    "Importance: " + tableModel.getValueAt(selectedRow, 3) + "\n" +
                    "Intended Service: " + tableModel.getValueAt(selectedRow, 4);
            JOptionPane.showMessageDialog(this, details, "Alarm Details", JOptionPane.INFORMATION_MESSAGE);
            detailsViewed = true;
            handledButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "No alarm selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAlarm() {
        int selectedRow = alarmTable.getSelectedRow();
        if (selectedRow >= 0 && detailsViewed) {
            tableModel.removeRow(selectedRow);
            detailsViewed = false;
            handledButton.setEnabled(false);
        }
    }
}
