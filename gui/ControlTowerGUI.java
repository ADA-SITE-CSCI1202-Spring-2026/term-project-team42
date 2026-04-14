package gui;
import java.awt.*;
import javax.swing.*;

public class ControlTowerGUI extends JFrame {
    // data type = custom class
    private double budget = 50000;
    private int jetFuel = 5000;
    private int meals = 400;
    private int luggageCarts = 15;

    private DefaultListModel<String> queueModel = new DefaultListModel<>();
    private JLabel statsLabel = new JLabel();
    private JComboBox<String> supplyDrop = new JComboBox<>(new String[]{"Jet Fuel", "Meals", "Luggage Carts"});
    private JTextArea logArea = new JTextArea();

    public ControlTowerGUI() {
        setTitle("Team 42 - Skyways Airport Control Tower");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));

        // --- ZONE 1: HOLDING PATTERN (flightQueue) ---
        JPanel flightQueue = new JPanel(new BorderLayout());
    
        flightQueue.setBorder(BorderFactory.createTitledBorder("1. Holding Pattern"));
       
        JList<String> flightList = new JList<>(queueModel);
      
        JButton clearBtn = new JButton("Clear Next Flight");
  //      clearBtn.addActionListener(e -> clearNextFlight());
        
        flightQueue.add(new JScrollPane(flightList), BorderLayout.CENTER);
        flightQueue.add(clearBtn, BorderLayout.SOUTH);

/* */

        // --- ZONE 2: TERMINAL DEPOT (resourceState) ---
        JPanel resourceState = new JPanel(new BorderLayout());

        resourceState.setBorder(BorderFactory.createTitledBorder("2. Terminal Depot"));
       
  //      updateStats(); // TODO

        statsLabel.setFont(new Font("Helvetica", Font.ITALIC,10));
        resourceState.add(statsLabel);

        JPanel fuelGauge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int fuelLevel = 1000; // Get actual data
                int barWidth = (int)((fuelLevel / 5000.0) * 200); // Scale to 200px
        
                g.setColor(fuelLevel > 1000 ? Color.GREEN : Color.RED);
                g.fillRect(10, 10, barWidth, 30); // Draw the filled bar
                g.setColor(Color.BLACK);
                g.drawRect(10, 10, 200, 30); // Draw the outline
            }
        };

        resourceState.add(fuelGauge);

/* */        
        // --- ZONE 3: SUPPLY REQUISITION (supplyChain) ---
        JPanel supplyChain = new JPanel(new BorderLayout());

        supplyChain.setBorder(BorderFactory.createTitledBorder("3. Supply Requisition"));

        JButton buyBtn = new JButton("Purchase Cargo");
        buyBtn.setPreferredSize(new Dimension(200, 40));
  //      buyBtn.addActionListener(e -> restock());
        supplyChain.add(buyBtn, BorderLayout.SOUTH);

        JLabel itemLabel = new JLabel("Select Item:", JLabel.CENTER);
        itemLabel.setPreferredSize(new Dimension(200, 30));
        supplyChain.add(itemLabel, BorderLayout.NORTH);
        
        JPanel dropWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropWrapper.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); 
        supplyDrop.setPreferredSize(new Dimension(200, 30));
        dropWrapper.add(supplyDrop);
        supplyChain.add(dropWrapper, BorderLayout.CENTER);

/* */
        // --- ZONE 4: DISPATCH RADIO (systemLog) ---
        JPanel systemLog = new JPanel(new BorderLayout());

        systemLog.setBorder(BorderFactory.createTitledBorder("4. Dispatch Radio"));

        logArea.setEditable(false);
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.GREEN);

        systemLog.add(new JScrollPane(logArea), BorderLayout.CENTER);


        // Add zones to frame
        add(flightQueue); add(resourceState); add(supplyChain); add(systemLog);

        // --- TIMER: Automatic Flight Arrival ---
  //      Timer arrivalTimer = new Timer(5000, e -> spawnFlight());
  //      arrivalTimer.start();

   //     addLog("System Online. Monitoring Tarmac...");
    }
/* 
    // Queue: LinkedList / Array Deque
    private void addLog(String system_Online_Monitoring_Tarmac) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // HashMap Encapsulation
    private void clearNextFlight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void updateStats() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void restock() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Technical
    private Timer spawnFlight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
*/
    public static void main(String[] args) {
        UIManager.put("TitledBorder.font", new Font("Helvetica", Font.BOLD, 20));
        // Run on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new ControlTowerGUI().setVisible(true));
    }
}
