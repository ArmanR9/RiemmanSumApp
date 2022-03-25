package ui;

import ui.tabs.ComputingTab;
import ui.tabs.DataTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// idea. Have three imageicons. flip between having them visible or not visible when set to east side of
// computing window

// left riemman
// mid riemman
// right riemman

public class RiemmanSumGUI {
    private static final int COMPUTING_TAB_INDEX = 0;
    private static final int DATA_TAB_INDEX = 1;

    private JFrame window;
    private JPanel calcPanel;
    private JPanel dataPanel;
    private JTabbedPane tabBar;

    // jTavs
    // jpanel for previous data
    // another jpanel for maybe a graph?

    // jpanel for the buttons, one for the textbox, and one for the data output
    // multiple JButtons. One for Left Riemman, One for Midpoint Riemman, One for Right Riemman.
    // buttons for saving and loading

    // use border layout for the compute left,  right mid riemman sums

    // use joptionpane for popups regarding errors

    public RiemmanSumGUI() {
        initializeSplashScreen();
        initializeFrame();

        tabBar = new JTabbedPane();
        dataPanel = new DataTab();
        calcPanel = new ComputingTab((DataTab)dataPanel);

        tabBar.add(calcPanel, COMPUTING_TAB_INDEX);
        tabBar.setTitleAt(COMPUTING_TAB_INDEX, "Calculate");
        tabBar.setBackgroundAt(COMPUTING_TAB_INDEX, Color.BLACK);

        tabBar.add(dataPanel, DATA_TAB_INDEX);
        tabBar.setTitleAt(DATA_TAB_INDEX, "Previous Calculations");
        tabBar.setBackgroundAt(DATA_TAB_INDEX, Color.BLACK);

        tabBar.setVisible(true);

        window.add(tabBar, BorderLayout.NORTH);
        window.pack();

    }

    private void initializeSplashScreen() {
        JWindow splashWindow = new JWindow();
        JLabel splashImage = new JLabel(new ImageIcon("./data/SplashScreen.png"));
        splashWindow.getContentPane().add(splashImage, SwingConstants.CENTER);
        splashWindow.setSize(450, 450);
        splashWindow.setLocationRelativeTo(null);
        splashWindow.setVisible(true);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splashWindow.setVisible(false);
        splashWindow.dispose();
    }

    private void initializeFrame() {
        window = new JFrame();
        window.setTitle("Riemman Sum Calculator");
        window.setResizable(false);
        window.setSize(500, 500);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setIconImage(new ImageIcon("./data/RiemmanSumIcon.png").getImage());
        window.setVisible(true);
    }


}
