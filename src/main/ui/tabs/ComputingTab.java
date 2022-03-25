package ui.tabs;

import model.RiemmanSum;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 Functionality for the main computing tab/window for calculating riemman sums, and loading/saving
 JSON data from disk

 NOTE:
 The ComputingTab borrows a similar program structure from the SmartHome project in
 https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
 */

public class ComputingTab extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/riemmanSumData.json";

    private DataTab dataTab;

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

    private RiemmanSum riSum;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Constructs all panels to necessitate Riemman Sum computation functionality in addition
    //          to JSON writing/reading access
    public ComputingTab(DataTab dataTab) {
        super();
        createFnInputPanel();
        createBtnAndResultPanel();
        createSumTypePanel();

        this.setLayout(new BorderLayout());
        this.add(functionInputPanel, BorderLayout.WEST);
        this.add(buttonAndResultPanel, BorderLayout.AFTER_LAST_LINE);
        this.add(riemmanSumTypePanel, BorderLayout.EAST);

        this.dataTab = dataTab;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: creates the entire panel for inputting function properties (type, name, a, b, n)
    private void createFnInputPanel() {
        GridLayout panelGridLayoutForFn = new GridLayout(6, 2);
        panelGridLayoutForFn.setHgap(-200);
        panelGridLayoutForFn.setVgap(0);

        initializeLabels();
        initializeTextFields();
        initializeAllTypeBoxes();

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

    // MODIFIES: this
    // EFFECTS: creates the entire panel for selecting riemman sum type, computing, loading, and saving
    //          functionality
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

    // MODIFIES: this
    // EFFECTS: creates the panel housing the riemman sum type picture visualization
    private void createSumTypePanel() {
        riemmanSumTypePanel = new JPanel();
        riemmanSumTypePanel.setPreferredSize(new Dimension(200, 220));

        rightSumPicture = new JLabel(new ImageIcon("./data/RightRiemmanSumTwo.png"));
        leftSumPicture = new JLabel(new ImageIcon("./data/LeftRiemmanSumTwo.png"));
        midpointSumPicture = new JLabel(new ImageIcon("./data/MidpointSumTwo.png"));

        currentPicture = leftSumPicture;
        riemmanSumTypePanel.add(currentPicture);
    }

    // MODIFIES: this
    // EFFECTS: initializes all JLabels with the relevant string fields
    private void initializeLabels() {
        blankRow = new JLabel("  ");
        fnInput = new JLabel("  Function:");
        fnType = new JLabel("  Function Type:");
        intervalAValue = new JLabel("  Lower Limit (A):");
        intervalBValue = new JLabel("  Upper Limit (B):");
        numOfRectsN = new JLabel("  Rectangles (N):");
        riemmanSumType = new JLabel("  Riemman Type:");
    }

    // MODIFIES: this
    // EFFECTS: initializes all JTextFields with the relevant size information
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

    // MODIFIES: this
    // EFFECTS: initializes all JButtons to be primed for an actionListener, and a relevant label
    private void initializeBtnsAndLabels() {
        computeBtn = new JButton("Compute Riemman Sum");
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");

        computeBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        loadBtn.addActionListener(this);

        result = new JLabel(" Result:");
    }

    // MODIFIES: this
    // EFFECTS: initializes all JTypeBoxes with the relevant options, colour data,
    //           and action listener attachment
    private void initializeAllTypeBoxes() {
        String[] functionTypes = {"Trigonometric", "Logarithmic", "Linear"};
        String[] sumTypes = {"Left Riemman", "Midpoint Riemman", "Right Riemman"};

        functionTypeSelector = new JComboBox(functionTypes);
        functionTypeSelector.setBackground(new Color(190, 177, 177));
        functionTypeSelector.addActionListener(this);
        functionTypeSelector.setVisible(true);

        riemmanSumSelector = new JComboBox(sumTypes);
        riemmanSumSelector.setBackground(new Color(190, 177, 177));
        riemmanSumSelector.addActionListener(e -> swapRiemmanPicture((String) riemmanSumSelector.getSelectedItem()));
        riemmanSumSelector.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: swaps out Riemman Sum Type picture (left, mid, right), based on current type selected by user
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

    // EFFECTS: produces true if any entry text field is not empty
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

    // EFFECTS: produces true if any of the text fields (a, b, and n) do not produce an invalid
    //          result when parsing
    private boolean notInvalid() {

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

    // MODIFIES: this
    // EFFECTS: computes riemman sum and displays it on the result label in the GUI
    private void computeResult() {
        result.setText("Result: " + riSum.computeRiemmanSum());
    }

    // EFFECTS: saves the Riemman Sum to json file in data folder
    private void saveRiemmanSum() {

        if (riSum == null) {
            return;
        }

        try {
            jsonWriter.open();
            jsonWriter.write(riSum);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved Riemman Sum to " + JSON_STORE,
                    "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Failed to save to " + JSON_STORE,
                    "Save Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Riemman Sum from json file in data folder
    private void loadRiemmanSum() {
        try {
            riSum = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded Riemman Sum from " + JSON_STORE,
                    "Load Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load data from " + JSON_STORE,
                    "Load Failed", JOptionPane.ERROR_MESSAGE);
        } catch (JSONException e) {
            JOptionPane.showMessageDialog(null, "Data is empty in " + JSON_STORE,
                    "Load Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: performs computation, save, and load operations when the appropriate buttons are pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(computeBtn) && notInvalid() && notEmpty()) {

            String sumType = (String) riemmanSumSelector.getSelectedItem();
            String mathFnType = (String) functionTypeSelector.getSelectedItem();
            double intervalA = Double.parseDouble(intervalAEntryField.getText());
            double intervalB = Double.parseDouble(intervalBEntryField.getText());
            int rectN = Integer.parseInt(numOfRectsNField.getText());

            if (riSum == null) {
                riSum = new RiemmanSum(sumType, mathFnType, functionEntryField.getText(), intervalA, intervalB, rectN);
                dataTab.setRiemmanSum(riSum);
            } else {
                riSum.addNewRiemmanSum(sumType, mathFnType, functionEntryField.getText(), intervalA, intervalB, rectN);
            }

            computeResult();
            dataTab.printDataToGUI(false);

        } else if (e.getSource().equals(functionTypeSelector)) {
            swapRiemmanPicture((String) riemmanSumSelector.getSelectedItem());
        } else if (e.getSource().equals(saveBtn)) {
            saveRiemmanSum();
        } else if (e.getSource().equals(loadBtn)) {
            loadRiemmanSum();
            dataTab.setRiemmanSum(riSum);
            dataTab.printDataToGUI(true);
        }
    }
}
