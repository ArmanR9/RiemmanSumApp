package ui.tabs;

import model.Computation;
import model.RiemmanSum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataTab extends JPanel {
    private RiemmanSum currentRiemmanSum;
    private JScrollPane dataPane;
    private JTextArea textArea;

    public DataTab() {
        super();

        this.setLayout(new BorderLayout());
        textArea = new JTextArea(5, 10);
        textArea.setEditable(false);
        dataPane = new JScrollPane(textArea);

        this.add(dataPane);

    }

    public void setRiemmanSum(RiemmanSum sum) {
        this.currentRiemmanSum = sum;
    }

    public void printDataToGUI(boolean isLoadOperation) {
        if (isLoadOperation && currentRiemmanSum != null) {
            printFromLoadBtn();
        } else if (currentRiemmanSum != null) {
            printFromComputationBtn();
        }
    }

    private void printFromLoadBtn() {
        textArea.setText("");
        textArea.append("\n");

        for (Computation c : currentRiemmanSum.getComputationHistory()) {
            for (String s : c.produceStats()) {
                textArea.append(s);
                textArea.append("\n");
            }
            textArea.append("-----------");
            textArea.append("\n");
        }

    }

    private void printFromComputationBtn() {
        int historySize = currentRiemmanSum.getComputationHistorySize();
        Computation c = currentRiemmanSum.getComputationHistory().get(historySize - 1);

        textArea.append("\n");

        for (String s : c.produceStats()) {
            textArea.append(s);
            textArea.append("\n");
        }

        textArea.append("-----------");
        textArea.append("\n");
    }
}

