package org.example.gui;

import org.example.alarms.Alarm;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StatisticsPanel extends JPanel
{
    private JTextArea statisticsArea;
    private List<Alarm> allAlarms;
    private ChartPanel chartPanel;

    public StatisticsPanel(List<Alarm> allAlarms) {
        this.allAlarms = allAlarms;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel statsLabel = new JLabel("Alarm Statistics:");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        statisticsArea = new JTextArea(10, 30);
        statisticsArea.setEditable(false);
        statisticsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        statisticsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        add(statsLabel, BorderLayout.NORTH);
        add(new JScrollPane(statisticsArea), BorderLayout.CENTER);

        displayStatistics();

        // Add the chart
        chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.SOUTH);
    }

    public void updateStatistics() {
        displayStatistics();
    }

    private void displayStatistics() {
        Map<String, Long> alarmCounts = allAlarms.stream()
                .collect(Collectors.groupingBy(Alarm::getAlarmType, Collectors.counting()));

        StringBuilder statsText = new StringBuilder();
        for (Map.Entry<String, Long> entry : alarmCounts.entrySet()) {
            statsText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        statisticsArea.setText(statsText.toString());

        // Update the chart
        if (chartPanel != null) {
            remove(chartPanel);
        }
        chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private ChartPanel createChartPanel() {
        JFreeChart barChart = createChart();
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return chartPanel;
    }

    private JFreeChart createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Long> alarmCounts = allAlarms.stream()
                .collect(Collectors.groupingBy(Alarm::getAlarmType, Collectors.counting()));

        for (Map.Entry<String, Long> entry : alarmCounts.entrySet()) {
            dataset.addValue(entry.getValue(), entry.getKey(), entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Alarm Statistics", // Chart title
                "Alarm Type",       // Domain axis label
                "Count",            // Range axis label
                dataset,            // Data
                PlotOrientation.VERTICAL,
                false,              // Include legend
                true,
                false
        );

        // Customization of the chart
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        rangeAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(70, 130, 180));
        renderer.setShadowVisible(false);

        return chart;
    }
}
