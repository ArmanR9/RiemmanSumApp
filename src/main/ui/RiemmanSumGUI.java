package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiemmanSumGUI implements ActionListener {
    private JFrame window;
    private JLabel functionEntryLabel;
    private JTextField functionEntryField;
    private JComboBox functionTypeSelector;
    private JButton testButton;
    private JPanel functionInputPanel;
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
        initializeComboBox();

        tabBar = new JTabbedPane();
        functionInputPanel = new JPanel();

        JLabel fnType = new JLabel();
        fnType.setText("Function Type:");

        functionInputPanel.setBackground(new Color(190, 177, 177));
        functionInputPanel.setPreferredSize(new Dimension(400, 100));
        functionInputPanel.add(fnType);
        functionInputPanel.add(functionTypeSelector, fnType);//, SwingConstants.LEFT);
        tabBar.setTabPlacement(JTabbedPane.TOP);
        window.add(tabBar, BorderLayout.NORTH);
        window.add(functionInputPanel, BorderLayout.WEST);
        window.pack();

        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click


    }

    private void initializeSplashScreen() {
        JWindow splashWindow = new JWindow();
        JLabel splashImage = new JLabel(new ImageIcon("./data/SplashScreen.png"));
        splashWindow.getContentPane().add(splashImage, SwingConstants.CENTER);
        splashWindow.setSize(450, 450);
        splashWindow.setLocationRelativeTo(null);
        splashWindow.setVisible(true);

        try {
            Thread.sleep(1000);
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


    private void initializeComboBox() {
        String[] functionTypes = { "Trigonometric", "Logarithmic", "Linear"};

        functionTypeSelector = new JComboBox(functionTypes);
        functionTypeSelector.addActionListener(this);
        functionTypeSelector.setVisible(true);
    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(testButton)) {
            functionEntryLabel.setText("Your Riemman Sum has been computed!");
        } else if (e.getSource().equals(functionTypeSelector)) {
            System.out.println(functionTypeSelector.getSelectedItem());
        }
    }

}
