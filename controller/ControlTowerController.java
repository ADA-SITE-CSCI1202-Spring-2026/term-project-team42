package controller;

import gui.ControlTowerGUI; 

import javax.swing.*;

public class ControlTowerController {

    private ControlTowerGUI view; // will work after completing the ControlTowerGUI

    public ControlTowerController(ControlTowerGUI view) {
        this.view = view;
        initHandlers();
        initSystem();
    }

    private void initHandlers() {
        view.getClearBtn().addActionListener(e -> handleClearFlight());

        view.getBuyBtn().addActionListener(e -> handlePurchase());
    }

    private void initSystem() {
        log("System Online. Monitoring...");
    }

    private void handleClearFlight() {

        DefaultListModel<String> queue = view.getQueueModel();

        if (queue.isEmpty()) {
            log("WARNING: No flights in holding pattern.");
            return;
        }

        String flight = queue.remove(0);

        log("SUCCESS: Cleared " + flight);
    }

    private void handlePurchase() {

        String selected = (String) view.getSupplyDrop().getSelectedItem();

        if (selected == null) {
            log("ERROR: No supply selected!");
            return;
        }

        switch (selected) {
            case "Jet Fuel":
                log("Purchased 1000L Jet Fuel");
                break;

            case "Meals":
                log("Purchased 200 Meals");
                break;

            case "Luggage Carts":
                log("Purchased 5 Luggage Carts");
                break;

            default:
                log("ERROR: Unknown supply type.");
        }
    }

    private void log(String message) {
        JTextArea logArea = view.getLogArea();
        logArea.append(message + "\n");
    }
}