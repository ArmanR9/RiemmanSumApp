package ui;

import ui.tabs.ComputingTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// idea. Have three imageicons. flip between having them visible or not visible when set to east side of
// computing window

// left riemman
// mid riemman
// right riemman

public class RiemmanSumGUI implements ActionListener {
    private JFrame window;
    private JTextField functionEntryField;
    private JTextField intervalAEntryField;
    private JTextField intervalBEntryField;
    private JTextField numOfRectsNField;

    private JComboBox functionTypeSelector;
    private JComboBox riemmanSumSelector;

    private JButton computeBtn;
    private JButton saveBtn;
    private JButton loadBtn;

    private JPanel calcPanel;
    private JPanel functionInputPanel;
    private JPanel buttonAndResultPanel;
    private JPanel riemmanSumTypePanel;
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
      /*  initializeFnTypeBox();
        initializeSumTypeBox();

        GridLayout testGridLayout = new GridLayout(6, 2);
        testGridLayout.setHgap(-200);
        testGridLayout.setVgap(0);

        GridLayout testGridLayout2 = new GridLayout(2, 4);
        testGridLayout2.setHgap(0);
        testGridLayout2.setVgap(0);


        functionEntryField = new JTextField();
        functionEntryField.setPreferredSize(new Dimension(50, 25));

        intervalAEntryField = new JTextField();
        intervalAEntryField.setPreferredSize(new Dimension(20, 25));

        intervalBEntryField = new JTextField();
        intervalBEntryField.setPreferredSize(new Dimension(20, 25));

        numOfRectsNField = new JTextField();
        numOfRectsNField.setPreferredSize(new Dimension(20, 25));
        */

        tabBar = new JTabbedPane();
        calcPanel = new ComputingTab();
        /*
        calcPanel.setLayout(new BorderLayout());
        functionInputPanel = new JPanel();
        buttonAndResultPanel = new JPanel();
        riemmanSumTypePanel = new JPanel();

        JLabel blankRow = new JLabel("  ");

        JLabel fnInput = new JLabel("  Function:");
        JLabel fnType = new JLabel("  Function Type:");
        JLabel intervalAValue = new JLabel("  Lower Limit (A):");
        JLabel intervalBValue = new JLabel("  Upper Limit (B):");
        JLabel numOfRectsN = new JLabel("  Rectangles (N):");
        JLabel riemmanSumType = new JLabel("  Riemman Type:");
        JLabel result = new JLabel(" Result:");

        computeBtn = new JButton("Compute Riemman Sum");
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");



        functionInputPanel.setPreferredSize(new Dimension(450, 150));
        functionInputPanel.setLayout(testGridLayout);

        functionInputPanel.add(fnInput);
        functionInputPanel.add(functionEntryField, fnInput);

        functionInputPanel.add(fnType);
        functionInputPanel.add(functionTypeSelector, fnType);

        functionInputPanel.add(intervalAValue);
        functionInputPanel.add(intervalAEntryField);

        functionInputPanel.add(intervalBValue);
        functionInputPanel.add(intervalBEntryField);

        functionInputPanel.add(numOfRectsN);
        functionInputPanel.add(numOfRectsNField);

        functionInputPanel.add(riemmanSumType);
        functionInputPanel.add(riemmanSumSelector, riemmanSumType);

        buttonAndResultPanel.setPreferredSize(new Dimension(450, 50));
        buttonAndResultPanel.setLayout(testGridLayout2);


        buttonAndResultPanel.add(computeBtn);
        buttonAndResultPanel.add(result);
        buttonAndResultPanel.add(saveBtn);
        buttonAndResultPanel.add(loadBtn);

        riemmanSumTypePanel.setPreferredSize(new Dimension(200, 220));
        riemmanSumTypePanel.add(new JLabel(new ImageIcon("./data/RightRiemmanSumTwo.png")));

        calcPanel.add(functionInputPanel, BorderLayout.WEST);
        calcPanel.add(buttonAndResultPanel, BorderLayout.AFTER_LAST_LINE);
        calcPanel.add(riemmanSumTypePanel, BorderLayout.EAST);

       // tabBar.setTabPlacement(JTabbedPane.TOP);
        */

        tabBar.add(calcPanel, 0);
        tabBar.setTitleAt(0, "Calculate");
        tabBar.setBackgroundAt(0, Color.BLACK);
        tabBar.setVisible(true);


        window.add(tabBar, BorderLayout.NORTH);
      //  window.add(calcPanel);
      //  window.add(functionInputPanel, BorderLayout.WEST);
      //  window.add(buttonAndResultPanel, BorderLayout.AFTER_LAST_LINE);
      //  window.add(riemmanSumTypePanel, BorderLayout.EAST);
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


    private void initializeFnTypeBox() {
        String[] functionTypes = { "Trigonometric", "Logarithmic", "Linear"};

        functionTypeSelector = new JComboBox(functionTypes);
        functionTypeSelector.setBackground(new Color(190, 177, 177));
        functionTypeSelector.addActionListener(this);
        functionTypeSelector.setVisible(true);
    }

    private void initializeSumTypeBox() {
        String[] sumTypes = { "Left Riemman", "Right Riemman", "Midpoint Riemman"};

        riemmanSumSelector = new JComboBox(sumTypes);
        riemmanSumSelector.setBackground(new Color(190, 177, 177));
        riemmanSumSelector.addActionListener(this);
        riemmanSumSelector.setVisible(true);
    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(computeBtn)) {
         //   functionEntryLabel.setText("Your Riemman Sum has been computed!");
        } else if (e.getSource().equals(functionTypeSelector)) {
            System.out.println(functionTypeSelector.getSelectedItem());
        }
    }

}
