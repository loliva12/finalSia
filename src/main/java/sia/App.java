package sia;

import sia.ui.DroneOptimizationUI;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DroneOptimizationUI ui = new DroneOptimizationUI();
            ui.createAndShowUI();
        });
    }
}
