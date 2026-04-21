package gui;

import airplanes.Aircraft;
import java.awt.*;
import javax.swing.*;

public class ControlTowerGUI extends JFrame {
    private DefaultListModel<Aircraft> queueModel = new DefaultListModel<>();
    private JList<Aircraft> flightList = new JList<>(queueModel);
    
    private JLabel statsLabel = new JLabel();
    private JComboBox<String> supplyDrop = new JComboBox<>(new String[]{"Jet Fuel", "Meals", "Luggage Carts"});
    private JTextArea logArea = new JTextArea();
    
    private JButton clearBtn = new JButton("Clear Next Flight");
    private JButton buyBtn = new JButton("Purchase Cargo");

    public ControlTowerGUI() {
        setTitle("Team 42 - Skyways Airport Control Tower");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));

        // --- ZONE 1: HOLDING PATTERN (flightQueue) ---
        JPanel flightQueue = new JPanel(new BorderLayout());
        flightQueue.setBorder(BorderFactory.createTitledBorder("1. Holding Pattern"));
        flightQueue.add(new JScrollPane(flightList), BorderLayout.CENTER);
        flightQueue.add(clearBtn, BorderLayout.SOUTH);

/* */

        // --- ZONE 2: TERMINAL DEPOT (resourceState) ---
        JPanel resourceState = new JPanel(new BorderLayout());
        resourceState.setBorder(BorderFactory.createTitledBorder("2. Terminal Depot"));
        updateStats(0, 0, 0, 0);
        resourceState.add(statsLabel, BorderLayout.NORTH);

/* */        
        // --- ZONE 3: SUPPLY REQUISITION (supplyChain) ---
        JPanel supplyChain = new JPanel(new BorderLayout());
        supplyChain.setBorder(BorderFactory.createTitledBorder("3. Supply Requisition"));
        supplyChain.add(buyBtn, BorderLayout.SOUTH);
        supplyChain.add(supplyDrop, BorderLayout.CENTER);

/* */
        // --- ZONE 4: DISPATCH RADIO (systemLog) ---
        JPanel systemLog = new JPanel(new BorderLayout());
        systemLog.setBorder(BorderFactory.createTitledBorder("4. Dispatch Radio"));
        logArea.setEditable(false);
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.GREEN);
        systemLog.add(new JScrollPane(logArea), BorderLayout.CENTER);

        add(flightQueue); add(resourceState); add(supplyChain); add(systemLog);
    }

    public void updateStats(double budget, int fuel, int meals, int carts) {
        String status = String.format(
            "<html>Budget: <font color='blue'>$%.2f</font><br>" +
            "Fuel: %dL<br>Meals: %d<br>Carts: %d</html>", 
            budget, fuel, meals, carts
        );
        this.statsLabel.setText(status);
    }

    public JButton getClearBtn() { return clearBtn; }
    public JButton getBuyBtn() { return buyBtn; }
    public DefaultListModel<Aircraft> getQueueModel() { return queueModel; }
    public JComboBox<String> getSupplyDrop() { return supplyDrop; }
    public JTextArea getLogArea() { return logArea; }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControlTowerGUI gui = new ControlTowerGUI();
            new ControlTowerController(gui);
            gui.setVisible(true);
        });
    }
}
