package org.example.gui;

import org.example.alarms.Alarm;
import org.example.alarms.FireAlarm;
import org.example.alarms.GasAlarm;
import org.example.alarms.RadiationAlarm;
import org.example.listeners.AlarmEventListener;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AlarmGeneratorPanel extends JPanel
{
    private JTextField buildingField;
    private JComboBox<String> typeComboBox;
    private JTextField gasTypeField;
    private JTextField radiationLevelField;
    private JSlider importanceSlider;
    private List<AlarmEventListener> listeners;
    private List<Alarm> allAlarms;
    private ConfigurationPanel configurationPanel;

    public AlarmGeneratorPanel(List<AlarmEventListener> listeners, List<Alarm> allAlarms, ConfigurationPanel configurationPanel) {
        this.listeners = listeners;
        this.allAlarms = allAlarms;
        this.configurationPanel = configurationPanel;

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

        JLabel importanceLabel = new JLabel("Importance Level:");
        importanceSlider = new JSlider(1, 10, 2); // Slider range from 1 to 10, default value 2
        importanceSlider.setMajorTickSpacing(1);
        importanceSlider.setPaintTicks(true);
        importanceSlider.setPaintLabels(true);

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

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(importanceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(importanceSlider, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
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
        String building = buildingField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        int importanceLevel = importanceSlider.getValue();

        // Get the configuration data
        Map<String, String[]> roomSensorConfig = configurationPanel.getRoomSensorConfig();
        if (!roomSensorConfig.containsKey(building)) {
            JOptionPane.showMessageDialog(this, "Building not configured!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] configuredSensors = roomSensorConfig.get(building);
        boolean sensorConfigured = false;
        for (String sensorType : configuredSensors) {
            if (sensorType != null && sensorType.equalsIgnoreCase(type)) {
                sensorConfigured = true;
                break;
            }
        }

        if (!sensorConfigured) {
            JOptionPane.showMessageDialog(this, "Sensor type not configured for this building!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Sensor sensor;
        Alarm alarm = null;
        if ("Fire".equals(type)) {
            sensor = new FireSensor(building);
            alarm = sensor.generateAlarm(this, importanceLevel);
        } else if ("Gas".equals(type)) {
            String gasType = gasTypeField.getText();
            sensor = new GasSensor(building, gasType);

            // Create two alarms for gas type, one for each intended service
            Alarm environmentalAlarm = new GasAlarm(this, new Date(), building, importanceLevel, gasType) {
                @Override
                public String getIntendedService() {
                    return "Environmental Service";
                }
            };

            Alarm firefighterAlarm = new GasAlarm(this, new Date(), building, importanceLevel, gasType) {
                @Override
                public String getIntendedService() {
                    return "Firefighter";
                }
            };

            triggerAlarm(environmentalAlarm);
            triggerAlarm(firefighterAlarm);
            return;
        } else if ("Radiation".equals(type)) {
            int radiationLevel = Integer.parseInt(radiationLevelField.getText());
            sensor = new RadiationSensor(building, radiationLevel);
            alarm = sensor.generateAlarm(this, importanceLevel);
        }

        if (alarm != null) {
            triggerAlarm(alarm);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to generate alarm.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void triggerAlarm(Alarm alarm) {
        for (AlarmEventListener listener : listeners) {
            listener.alarmGenerated(alarm);
        }
        allAlarms.add(alarm); // Track the alarm for statistics
        JOptionPane.showMessageDialog(this, "Alarm generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Refresh statistics
        configurationPanel.updateStatistics();
    }

}
