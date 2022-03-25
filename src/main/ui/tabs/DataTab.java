package ui.tabs;

import model.Computation;
import model.RiemmanSum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 Functionality for the data tab/window for viewing a history of previous Riemman Sum computations
 in a scrollable pane.

 NOTE:
 The DataTab borrows a similar program structure from the SmartHome project in
 https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
 */

public class DataTab extends JPanel implements ActionListener {
    private RiemmanSum currentRiemmanSum;
    private JScrollPane dataPane;
    private JTextArea textArea;
    private JButton removeBtn;

    // EFFECTS: constructs a new panel for housing previous sum history data in a text area attached
    //          to a scroll pane
    public DataTab() {
        super();

        this.setLayout(new BorderLayout());
        textArea = new JTextArea(15, 10);
        textArea.setEditable(false);
        dataPane = new JScrollPane(textArea);

        removeBtn = new JButton();
        removeBtn.setText("Remove Latest Riemman Sum");
        removeBtn.addActionListener(this);
        removeBtn.setVisible(true);

        this.setLayout(new BorderLayout());
        this.add(dataPane, BorderLayout.NORTH);
        this.add(removeBtn, BorderLayout.AFTER_LAST_LINE);
    }

    // MODIFIES: this
    // EFFECTS: sets currentRiemmanSum to given sum
    public void setRiemmanSum(RiemmanSum sum) {
        this.currentRiemmanSum = sum;
    }

    // MODIFIES: this
    // EFFECTS: performs a removal of the newest riemman sum computation when the remove
    //          button is pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(removeBtn)) {
            int historySize = currentRiemmanSum.getComputationHistorySize();
            currentRiemmanSum.getComputationHistory().remove(historySize - 1);
            printFromLoadBtn();
        }
    }

    // MODIFIES: this
    // EFFECTS: prints computation history to data panel in a specific way based on
    //          whether it's incurred from a load operation or not
    public void printDataToGUI(boolean isLoadOperation) {
        if (isLoadOperation && currentRiemmanSum != null) {
            printFromLoadBtn();
        } else if (currentRiemmanSum != null) {
            printFromComputationBtn();
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the entire computation history incurred by a load operation
    private void printFromLoadBtn() {
        textArea.setText("");
        textArea.append("\n");

        for (Computation c : currentRiemmanSum.getComputationHistory()) {
            for (String s : c.produceStats()) {
                textArea.append(s);
                textArea.append("\n");
            }
            textArea.append("\n");
        }

    }

    // MODIFIES: this
    // EFFECTS: prints a newly formed computation to the data panel incurred by
    //          the riemman sum compute button
    private void printFromComputationBtn() {
        int historySize = currentRiemmanSum.getComputationHistorySize();
        Computation c = currentRiemmanSum.getComputationHistory().get(historySize - 1);

        textArea.append("\n");

        for (String s : c.produceStats()) {
            textArea.append(s);
            textArea.append("\n");
        }

        textArea.append("\n");
    }
}

