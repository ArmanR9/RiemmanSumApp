package ui.tabs;

import model.RiemmanSum;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Scanner;

public class ComputingTab extends JPanel implements ActionListener {

    private JTextField functionEntryField;
    private JTextField intervalAEntryField;
    private JTextField intervalBEntryField;
    private JTextField numOfRectsNField;

    private JComboBox functionTypeSelector;
    private JComboBox riemmanSumSelector;

    private JButton computeBtn;
    private JButton saveBtn;
    private JButton loadBtn;

    private JPanel functionInputPanel;
    private JPanel buttonAndResultPanel;
    private JPanel riemmanSumTypePanel;

    private JLabel blankRow;
    private JLabel fnInput;
    private JLabel fnType;
    private JLabel intervalAValue;
    private JLabel intervalBValue;
    private JLabel numOfRectsN;
    private JLabel riemmanSumType;
    private JLabel result;

    private JLabel currentPicture;
    private JLabel leftSumPicture;
    private JLabel midpointSumPicture;
    private JLabel rightSumPicture;

    private static final String JSON_STORE = "./data/riemmanSumData.json";

    private RiemmanSum riSum;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean hasSaved;


    public ComputingTab() {
        super();
        createFnInputPanel();
        createBtnAndResultPanel();
        createSumTypePanel();


        this.setLayout(new BorderLayout());
        this.add(functionInputPanel, BorderLayout.WEST);
        this.add(buttonAndResultPanel, BorderLayout.AFTER_LAST_LINE);
        this.add(riemmanSumTypePanel, BorderLayout.EAST);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        hasSaved = false;
    }

    private void createFnInputPanel() {
        GridLayout panelGridLayoutForFn = new GridLayout(6, 2);
        panelGridLayoutForFn.setHgap(-200);
        panelGridLayoutForFn.setVgap(0);

        initializeLabels();
        initializeTextFields();
        initializeFnTypeBox();
        initializeSumTypeBox();

        functionInputPanel = new JPanel();
        functionInputPanel.setPreferredSize(new Dimension(450, 150));
        functionInputPanel.setLayout(panelGridLayoutForFn);

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
    }

    private void createBtnAndResultPanel() {
        GridLayout panelGridLayoutForBtns = new GridLayout(2, 4);
        panelGridLayoutForBtns.setHgap(0);
        panelGridLayoutForBtns.setVgap(0);

        initializeBtnsAndLabels();

        buttonAndResultPanel = new JPanel();
        buttonAndResultPanel.setPreferredSize(new Dimension(450, 50));
        buttonAndResultPanel.setLayout(panelGridLayoutForBtns);

        buttonAndResultPanel.add(computeBtn);
        buttonAndResultPanel.add(result);
        buttonAndResultPanel.add(saveBtn);
        buttonAndResultPanel.add(loadBtn);
    }

    private void createSumTypePanel() {
        riemmanSumTypePanel = new JPanel();
        riemmanSumTypePanel.setPreferredSize(new Dimension(200, 220));

        rightSumPicture = new JLabel(new ImageIcon("./data/RightRiemmanSumTwo.png"));
        leftSumPicture = new JLabel(new ImageIcon("./data/LeftRiemmanSumTwo.png"));
        midpointSumPicture = new JLabel(new ImageIcon("./data/MidpointSumTwo.png"));

        currentPicture = leftSumPicture;
        riemmanSumTypePanel.add(currentPicture);
    }

    private void initializeLabels() {
        blankRow = new JLabel("  ");
        fnInput = new JLabel("  Function:");
        fnType = new JLabel("  Function Type:");
        intervalAValue = new JLabel("  Lower Limit (A):");
        intervalBValue = new JLabel("  Upper Limit (B):");
        numOfRectsN = new JLabel("  Rectangles (N):");
        riemmanSumType = new JLabel("  Riemman Type:");
    }

    private void initializeTextFields() {
        functionEntryField = new JTextField();
        functionEntryField.setPreferredSize(new Dimension(50, 25));

        intervalAEntryField = new JTextField();
        intervalAEntryField.setPreferredSize(new Dimension(20, 25));

        intervalBEntryField = new JTextField();
        intervalBEntryField.setPreferredSize(new Dimension(20, 25));

        numOfRectsNField = new JTextField();
        numOfRectsNField.setPreferredSize(new Dimension(20, 25));
    }

    private void initializeBtnsAndLabels() {
        computeBtn = new JButton("Compute Riemman Sum");
        computeBtn.addActionListener(this);
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");
        result = new JLabel(" Result:");
    }

    private void initializeFnTypeBox() {
        String[] functionTypes = { "Trigonometric", "Logarithmic", "Linear"};

        functionTypeSelector = new JComboBox(functionTypes);
        functionTypeSelector.setBackground(new Color(190, 177, 177));
        functionTypeSelector.addActionListener(this);
        functionTypeSelector.setVisible(true);
    }

    private void initializeSumTypeBox() {
        String[] sumTypes = { "Left Riemman", "Midpoint Riemman", "Right Riemman"};

        riemmanSumSelector = new JComboBox(sumTypes);
        riemmanSumSelector.setBackground(new Color(190, 177, 177));
        riemmanSumSelector.addActionListener(e -> {
            swapRiemmanPicture((String)riemmanSumSelector.getSelectedItem());
        });
        riemmanSumSelector.setVisible(true);
    }

    private void swapRiemmanPicture(String sumType) {
        riemmanSumTypePanel.remove(currentPicture);
        riemmanSumTypePanel.revalidate();
        riemmanSumTypePanel.repaint();

        if (sumType.toLowerCase().contains("right")) {
            currentPicture = rightSumPicture;
        } else if (sumType.toLowerCase().contains("mid")) {
            currentPicture = midpointSumPicture;
        } else {
            currentPicture = leftSumPicture;
        }

        riemmanSumTypePanel.add(currentPicture);
    }

    private boolean notEmpty() {
        boolean valid = true;

        if (functionEntryField.getText().isEmpty()) {
            valid = false;
        } else if (intervalAEntryField.getText().isEmpty()) {
            valid = false;
        } else if (intervalBEntryField.getText().isEmpty()) {
            valid = false;
        } else if (numOfRectsNField.getText().isEmpty()) {
            valid = false;
        }

        if (!valid) {
            JOptionPane.showMessageDialog(null, "One or more fields are empty.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return valid;
    }

    private boolean notValid() {

        try {
            Double.parseDouble(intervalAEntryField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid A value", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(intervalBEntryField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid B value", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(numOfRectsNField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid N value", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        System.out.println("yo 0");
        if (e.getSource().equals(computeBtn) && notValid() && notEmpty()) {
            System.out.println("yo");
            String sumType = (String)riemmanSumSelector.getSelectedItem();
            String mathFunctionType = (String)functionTypeSelector.getSelectedItem();
            String mathFunction = functionEntryField.getText();
            Double intervalA = Double.parseDouble(intervalAEntryField.getText());
            Double intervalB = Double.parseDouble(intervalBEntryField.getText());
            Integer numOfRectsN = Integer.parseInt(numOfRectsNField.getText());

            riSum = new RiemmanSum(sumType, mathFunctionType, mathFunction, intervalA, intervalB, numOfRectsN);
           // computeResult();
        } else if (e.getSource().equals(functionTypeSelector)) {
            swapRiemmanPicture((String) riemmanSumSelector.getSelectedItem());
            System.out.println(functionTypeSelector.getSelectedItem());
        }
    }
}
