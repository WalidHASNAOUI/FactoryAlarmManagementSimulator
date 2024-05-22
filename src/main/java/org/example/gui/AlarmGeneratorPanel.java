package org.example.gui;

import org.example.alarms.Alarm;
import org.example.monitors.TypeAMonitor;
import org.example.monitors.TypeBMonitor;
import org.example.sensors.FireSensor;
import org.example.sensors.GasSensor;
import org.example.sensors.RadiationSensor;
import org.example.sensors.Sensor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AlarmGeneratorPanel extends JPanel
{
    private JTextField buildingField;
    private JComboBox<String> typeComboBox;
    private JTextField gasTypeField;
    private JTextField radiationLevelField;
    private TypeAMonitor typeAMonitor;
    private TypeBMonitor typeBMonitor;
    private MonitorPanel monitorPanel;

    public AlarmGeneratorPanel(TypeAMonitor typeAMonitor, TypeBMonitor typeBMonitor, MonitorPanel monitorPanel) {
        this.typeAMonitor = typeAMonitor;
        this.typeBMonitor = typeBMonitor;
        this.monitorPanel = monitorPanel;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel buildingLabel = new JLabel("Building:");
        buildingField = new JTextField(15);

        JLabel typeLabel = new JLabel("Type of Alarm:");
        typeComboBox = new JComboBox<>(new String[]{"Fire", "Gas", "Radiation"});

        JLabel gasTypeLabel = new JLabel("Gas Type:");
        gasTypeField = new JTextField(15);
        gasTypeField.setEnabled(false);

        JLabel radiationLevelLabel = new JLabel("Radiation Level:");
        radiationLevelField = new JTextField(15);
        radiationLevelField.setEnabled(false);

        JButton generateButton = new JButton("Generate Alarm");
        generateButton.setBackground(new Color(70, 130, 180));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buildingLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(buildingField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(typeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(gasTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(gasTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(radiationLevelLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(radiationLevelField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(generateButton, gbc);

        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) typeComboBox.getSelectedItem();
                gasTypeField.setEnabled("Gas".equals(selectedType));
                radiationLevelField.setEnabled("Radiation".equals(selectedType));
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateAlarm();
            }
        });
    }

    private void generateAlarm() {
        String building = buildingField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        int importanceLevel = 2;

        Sensor sensor;
        Alarm alarm = null;

        if ("Fire".equals(type)) {
            sensor = new FireSensor(building);
            alarm = sensor.generateAlarm();
        } else if ("Gas".equals(type)) {
            String gasType = gasTypeField.getText();
            sensor = new GasSensor(building, gasType);
            alarm = sensor.generateAlarm();
        } else if ("Radiation".equals(type)) {
            int radiationLevel = Integer.parseInt(radiationLevelField.getText());
            sensor = new RadiationSensor(building, radiationLevel);
            alarm = sensor.generateAlarm();
        }

        if (alarm != null) {
            typeAMonitor.receiveAlarm(alarm);
            typeBMonitor.receiveAlarm(alarm);
            monitorPanel.updateAlarmList(); // Update the table in the monitor panel
            JOptionPane.showMessageDialog(this, "Alarm generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to generate alarm.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
