package org.example.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationPanel extends JPanel
{
    private JTextField roomField;
    private JCheckBox fireSensorCheckbox;
    private JCheckBox gasSensorCheckbox;
    private JCheckBox radiationSensorCheckbox;
    private JButton addButton;
    private JTable configurationTable;
    private DefaultTableModel tableModel;
    private Map<String, String[]> roomSensorConfig;
    private StatisticsPanel statisticsPanel;

    public ConfigurationPanel(StatisticsPanel statisticsPanel) {
        this.statisticsPanel = statisticsPanel;
        roomSensorConfig = new HashMap<>();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel for sensor configuration
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new GridBagLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Configure Sensors", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Room input
        JLabel roomLabel = new JLabel("Room:");
        roomField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        configPanel.add(roomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(roomField, gbc);

        // Sensor checkboxes
        fireSensorCheckbox = new JCheckBox("Fire Sensor");
        gasSensorCheckbox = new JCheckBox("Gas Sensor");
        radiationSensorCheckbox = new JCheckBox("Radiation Sensor");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        configPanel.add(new JLabel("Sensors:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(fireSensorCheckbox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(gasSensorCheckbox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(radiationSensorCheckbox, gbc);

        // Add button
        addButton = new JButton("Add Configuration");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addConfiguration();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        configPanel.add(addButton, gbc);

        add(configPanel, BorderLayout.NORTH);

        // Table for displaying configurations
        String[] columnNames = {"Room", "Sensors", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0);
        configurationTable = new JTable(tableModel);
        configurationTable.setFillsViewportHeight(true);

        // Set custom renderer and editor for the actions column
        TableColumnModel columnModel = configurationTable.getColumnModel();
        columnModel.getColumn(2).setCellRenderer(new ButtonRenderer());
        columnModel.getColumn(2).setCellEditor(new ButtonEditor(this, configurationTable));

        JScrollPane scrollPane = new JScrollPane(configurationTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Current Configurations", TitledBorder.LEFT, TitledBorder.TOP));
        add(scrollPane, BorderLayout.CENTER);

        // Initialize configuration table
        updateConfigurationTable();
    }

    private void addConfiguration() {
        String room = roomField.getText().trim();
        if (room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room field cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean fireSensor = fireSensorCheckbox.isSelected();
        boolean gasSensor = gasSensorCheckbox.isSelected();
        boolean radiationSensor = radiationSensorCheckbox.isSelected();

        if (!fireSensor && !gasSensor && !radiationSensor) {
            JOptionPane.showMessageDialog(this, "At least one sensor must be selected.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] sensors = new String[]{
                fireSensor ? "Fire" : null,
                gasSensor ? "Gas" : null,
                radiationSensor ? "Radiation" : null
        };

        roomSensorConfig.put(room, sensors);
        updateConfigurationTable();

        // Clear input fields
        roomField.setText("");
        fireSensorCheckbox.setSelected(false);
        gasSensorCheckbox.setSelected(false);
        radiationSensorCheckbox.setSelected(false);
    }

    private void updateConfigurationTable() {
        if (configurationTable.isEditing()) {
            int editingRow = configurationTable.getEditingRow();
            TableCellEditor editor = configurationTable.getCellEditor();
            editor.stopCellEditing();
            tableModel.fireTableRowsUpdated(editingRow, editingRow);
        }

        tableModel.setRowCount(0);
        for (Map.Entry<String, String[]> entry : roomSensorConfig.entrySet()) {
            String room = entry.getKey();
            StringBuilder sensors = new StringBuilder();
            for (String sensor : entry.getValue()) {
                if (sensor != null) {
                    if (sensors.length() > 0) {
                        sensors.append(", ");
                    }
                    sensors.append(sensor);
                }
            }
            tableModel.addRow(new Object[]{room, sensors.toString(), ""});
        }
    }

    public void editConfiguration(String room) {
        if (configurationTable.isEditing()) {
            TableCellEditor editor = configurationTable.getCellEditor();
            editor.stopCellEditing();
        }

        String[] sensors = roomSensorConfig.get(room);
        roomField.setText(room);
        fireSensorCheckbox.setSelected(sensors[0] != null);
        gasSensorCheckbox.setSelected(sensors[1] != null);
        radiationSensorCheckbox.setSelected(sensors[2] != null);
        roomSensorConfig.remove(room);
        updateConfigurationTable();
    }

    public void deleteConfiguration(String room) {
        if (configurationTable.isEditing()) {
            TableCellEditor editor = configurationTable.getCellEditor();
            editor.stopCellEditing();
        }

        roomSensorConfig.remove(room);
        updateConfigurationTable();
    }

    public Map<String, String[]> getRoomSensorConfig() {
        return roomSensorConfig;
    }

    public void updateStatistics() {
        statisticsPanel.updateStatistics();
    }
}
