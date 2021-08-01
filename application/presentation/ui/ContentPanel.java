package application.presentation.ui;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import application.data.query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ContentPanel extends JPanel {
    private JSplitPane topLevelSplitPane;
    private JSplitPane querySplitPane;
    private JPanel newQueryPanel;
    private JPanel existingQueryList;
    private JMapViewer map;

    private Application app;


    public ContentPanel(Application app) {
        this.app = app;

        map = new JMapViewer();
        map.setMinimumSize(new Dimension(100, 50));
        setLayout(new BorderLayout());
        newQueryPanel = new application.presentation.ui.NewQueryPanel(app);

        // NOTE: We wrap existingQueryList in a container so it gets a pretty border.
        JPanel layerPanelContainer = layerPanelBorder();

        querySplitPane(layerPanelContainer);

        add(topLevelSplitPane, "Center");
        revalidate();

        repaint();
    }

    private JPanel layerPanelBorder() {
        JPanel layerPanelContainer = new JPanel();
        existingQueryList = new JPanel();
        existingQueryList.setLayout(new BoxLayout(existingQueryList, BoxLayout.Y_AXIS));
        layerPanelContainer.setLayout(new BorderLayout());
        layerPanelContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Current Queries"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        layerPanelContainer.add(existingQueryList, BorderLayout.NORTH);
        return layerPanelContainer;
    }

    private void querySplitPane(JPanel layerPanelContainer) {
        querySplitPane = new JSplitPane(0);
        querySplitPane.setDividerLocation(150);
        querySplitPane.setTopComponent(newQueryPanel);
        querySplitPane.setBottomComponent(layerPanelContainer);

        topLevelSplitPane = new JSplitPane(1);
        topLevelSplitPane.setDividerLocation(150);
        topLevelSplitPane.setLeftComponent(querySplitPane);
        topLevelSplitPane.setRightComponent(map);
    }

    // Add a new query to the set of queries and update the UI to reflect the new query.
    public void addQuery(Query query) {
        JPanel newQueryPanel = new JPanel();
        newQueryPanel.setLayout(new GridBagLayout());
        JPanel colorPanel = getColorPanel(query);
        JButton removeButton = new JButton("X");
        removeButton.setPreferredSize(new Dimension(30, 20));
        removeButtonActionList(query, newQueryPanel, removeButton);

        GridBagConstraints c = new GridBagConstraints();
        newQueryPanel.add(colorPanel, c);

        checkBoxGridPanel(query, newQueryPanel, removeButton);

        existingQueryList.add(newQueryPanel);
        validate();
    }

    private JPanel getColorPanel(Query query) {
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(query.getColor());
        colorPanel.setPreferredSize(new Dimension(30, 30));
        return colorPanel;
    }

    private void checkBoxGridPanel(Query query, JPanel newQueryPanel, JButton removeButton) {
        GridBagConstraints c;
        c = new GridBagConstraints();
        JCheckBox checkbox = new JCheckBox(query.getQueryString());
        checkbox.setSelected(true);
        checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.updateVisibility();
            }
        });
        query.setCheckBox(checkbox);
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        newQueryPanel.add(checkbox, c);
        newQueryPanel.add(removeButton);
    }

    private void removeButtonActionList(Query query, JPanel newQueryPanel, JButton removeButton) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.terminateQuery(query);
                query.terminate();
                existingQueryList.remove(newQueryPanel);
                revalidate();
            }
        });
    }

    public JMapViewer getViewer() {
        return map;
    }
}
