package gui;

import airplanes.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import logic.controller.ControlTowerController;


public class ControlTowerGUI extends JFrame {
    private DefaultListModel<FlightRequest> queueModel = new DefaultListModel<>();
    private JList<FlightRequest> flightList = new JList<>(queueModel);

    private JLabel budgetVal = new JLabel("$0.00");
    private JLabel fuelVal   = new JLabel("0L");
    private JLabel mealsVal  = new JLabel("0");
    private JLabel cartsVal  = new JLabel("0");

    private static final int MAX_FUEL = 100000;
    private static final int MAX_MEALS = 1000;
    private static final int MAX_CARTS = 1000;

    private JProgressBar fuelBar = new JProgressBar(SwingConstants.VERTICAL, 0, MAX_FUEL);
    private JProgressBar mealsBar = new JProgressBar(SwingConstants.VERTICAL, 0, MAX_MEALS);
    private JProgressBar cartsBar = new JProgressBar(SwingConstants.VERTICAL, 0, MAX_CARTS);


    private JComboBox<String> supplyDrop = new JComboBox<>(new String[]{"Select Supply Item", "Jet Fuel", "Meals", "Luggage Carts"});
    private JTextPane logPane = new JTextPane();
    
    private JButton clearBtn = new JButton("Clear Next Flight");
    private JButton buyBtn = new JButton("Purchase Cargo");

    private JButton saveBtn = new JButton("SAVE");
    private JButton loadBtn = new JButton("LOAD");
    

    public ControlTowerGUI() {
        setTitle("Team 42 - Skyways Airport Control Tower");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));

        logPane.setBackground(new Color(30, 30, 30));
        logPane.setCaretColor(Color.GREEN);
        logPane.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(logPane);



        // --- ZONE 1: HOLDING PATTERN (flightQueue) ---
        JPanel flightQueue = new JPanel(new BorderLayout());
        flightQueue.setBorder(BorderFactory.createTitledBorder("1. Holding Pattern"));
        flightQueue.add(new JScrollPane(flightList), BorderLayout.CENTER);

        clearBtn.setBackground(new Color(200, 230, 201));
        flightQueue.add(clearBtn, BorderLayout.SOUTH);

/* */
        // --- ZONE 2: TERMINAL DEPOT (resourceState) ---
        JPanel resourceState = new JPanel(new BorderLayout());
        resourceState.setBorder(BorderFactory.createTitledBorder("2. Terminal Depot"));
        updateStats(0, 0, 0, 0);

        JPanel depotPanel = new JPanel(new GridLayout(1, 4, 15, 0)); 
        depotPanel.setBackground(resourceState.getBackground()); 

        saveBtn.setBackground(new Color(200, 230, 201));
        loadBtn.setBackground(new Color(225, 230, 240));

        JPanel budgetCard = createMetricCard("Budget", budgetVal, Color.BLUE, null);
        JPanel fuelCard   = createMetricCard("Fuel", fuelVal, Color.BLACK, fuelBar);
        JPanel mealsCard  = createMetricCard("Meals", mealsVal, Color.BLACK, mealsBar);
        JPanel cartsCard  = createMetricCard("Carts", cartsVal, Color.BLACK, cartsBar);

        depotPanel.add(budgetCard);
        depotPanel.add(fuelCard);
        depotPanel.add(mealsCard);
        depotPanel.add(cartsCard);

        resourceState.add(depotPanel, BorderLayout.CENTER);

/* */        
        // --- ZONE 3: SUPPLY REQUISITION (supplyChain) ---
        JPanel supplyChain = new JPanel(new BorderLayout());
        supplyChain.setBorder(BorderFactory.createTitledBorder("3. Supply Requisition"));

        buyBtn.setBackground(new Color(225, 230, 240));
        supplyChain.add(buyBtn, BorderLayout.SOUTH);

        supplyChain.add(supplyDrop, BorderLayout.CENTER);

/* */
        // --- ZONE 4: DISPATCH RADIO (systemLog) ---
        JPanel systemLog = new JPanel(new BorderLayout());
        systemLog.setBorder(BorderFactory.createTitledBorder("4. Dispatch Radio"));
        logPane.setEditable(false);
        logPane.setBackground(Color.BLACK);
        logPane.setForeground(Color.GREEN);
        systemLog.add(new JScrollPane(logPane), BorderLayout.CENTER);

        add(flightQueue); add(resourceState); add(supplyChain); add(systemLog);
    }

    private void setBarHealth(JProgressBar bar, int current, int max) {
        bar.setMaximum(max);
        bar.setValue(current);
    
        double percent = (double) current / max;

        if (percent < 0.2) {
            bar.setForeground(new Color(220, 20, 60)); // Red
        } else if (percent < 0.5) {
            bar.setForeground(new Color(255, 165, 0)); // Orange
        } else {
            bar.setForeground(new Color(34, 139, 34)); // Green
        }
        bar.setToolTipText(current + " / " + max);
    }

    public void updateStats(double budget, int fuel, int meals, int carts) {
        budgetVal.setText(String.format("$%.2f", budget));
        fuelVal.setText(fuel + "L");
        mealsVal.setText(String.valueOf(meals));
        cartsVal.setText(String.valueOf(carts));

        fuelBar.setValue(fuel);
        mealsBar.setValue(meals);
        cartsBar.setValue(carts);

        setBarHealth(fuelBar, fuel, MAX_FUEL);
        setBarHealth(mealsBar, meals, MAX_MEALS);
        setBarHealth(cartsBar, carts, MAX_CARTS);
    }
    
    private JPanel createMetricCard(String title, JLabel valueLabel, Color valueColor, JProgressBar bar) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(5));

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        valueLabel.setForeground(valueColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(valueLabel);

        if (title.equalsIgnoreCase("Budget")) {
            card.add(Box.createVerticalGlue());

            JPanel bodySpace = new JPanel();
            bodySpace.setOpaque(false);
            bodySpace.setMaximumSize(new Dimension(Short.MAX_VALUE, 60)); 
            bodySpace.setMinimumSize(new Dimension(0, 60));
            bodySpace.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, new Color(0, 102, 204)));
        
            card.add(bodySpace);

            card.add(Box.createVerticalGlue()); 
            card.add(Box.createVerticalStrut(10));

            JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 5));
            btnPanel.setOpaque(false);
            btnPanel.setMaximumSize(new Dimension(120, 100));
        
            saveBtn.setPreferredSize(new Dimension(100, 45));
            loadBtn.setPreferredSize(new Dimension(100, 45));
        
            btnPanel.add(saveBtn);
            btnPanel.add(loadBtn);

            card.add(btnPanel);
            card.add(Box.createVerticalStrut(5));

        } else if (bar != null) {
            bar.setOrientation(SwingConstants.VERTICAL);
            bar.setAlignmentX(Component.CENTER_ALIGNMENT);
            bar.setPreferredSize(new Dimension(20, 100)); 
            bar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            bar.setBackground(new Color(230, 230, 230));
            bar.setStringPainted(true);

            card.add(Box.createVerticalGlue());
            card.add(bar);
        }

        return card;
    }

    public void log(String message) {
        log(message, new Color(51, 255, 51)); 
    }
    public void log(String message, Color color) {
        if (message == null || message.isEmpty()) return;
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        StyleConstants.setFontFamily(style, "Monospaced");
        StyleConstants.setBold(style, true);

        try {
            Document doc = logPane.getStyledDocument();
            String[] lines = message.split("\n");
        
            for (String line : lines) {
                String cleanLine = line.trim();
                if (cleanLine.length() > 0) {
                    doc.insertString(doc.getLength(), " > " + cleanLine + "\n", style);
                }
            }
            logPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public JButton getClearBtn() { return clearBtn; }
    public JButton getBuyBtn() { return buyBtn; }
    public JButton getSaveBtn() { return saveBtn; }
    public JButton getLoadBtn() { return loadBtn; }
    public DefaultListModel<FlightRequest> getQueueModel() { return queueModel; }
    public JComboBox<String> getSupplyDrop() { return supplyDrop; }
    public JTextPane getLogPane() { return logPane; }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControlTowerGUI gui = new ControlTowerGUI();
            new ControlTowerController(gui);
            gui.setVisible(true);
        });
    }
}
