package org.example.gui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor
{
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;
    private String currentRoom;
    private ConfigurationPanel configurationPanel;
    private JTable table;

    public ButtonEditor(ConfigurationPanel configurationPanel, JTable table) {
        this.configurationPanel = configurationPanel;
        this.table = table;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        editButton.setBackground(new Color(70, 130, 180));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurationPanel.editConfiguration(currentRoom);
                fireEditingStopped(); // Notify JTable that editing has stopped
            }
        });

        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurationPanel.deleteConfiguration(currentRoom);
                fireEditingStopped(); // Notify JTable that editing has stopped
            }
        });

        panel.add(editButton);
        panel.add(deleteButton);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRoom = (String) table.getValueAt(row, 0);
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return currentRoom;
    }

    @Override
    public boolean stopCellEditing() {
        boolean result = super.stopCellEditing();
        table.repaint();
        return result;
    }
}
