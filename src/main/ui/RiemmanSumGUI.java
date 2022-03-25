package ui;

import ui.tabs.ComputingTab;
import ui.tabs.DataTab;

import javax.swing.*;
import java.awt.*;

/*
 Main GUI class that instantiates the JFrame application window (including the splash screen),
 and adds the relevant tabs and panels such as the computing tab and data tab to the
 window's contents.

 NOTE:
 The SplashScreen implementation is largely modelled off of user Alya'a Gamal's implementation
 https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
 */

public class RiemmanSumGUI {
    private static final int COMPUTING_TAB_INDEX = 0;
    private static final int DATA_TAB_INDEX = 1;

    private JFrame window;
    private JPanel calcPanel;
    private JPanel dataPanel;
    private JTabbedPane tabBar;

    // EFFECTS: Initializes the application window and adds the computation and data panels to the main frame
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

    // MODIFIES: this
    // EFFECTS: Creates a splash screen with the application logo that appears for ~1 second
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

    // MODIFIES: this
    // EFFECTS: initializes JFrame window including setting its size, visibility, position, etc.
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
