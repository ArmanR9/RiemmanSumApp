package ui.tabs;

import model.RiemmanSum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataTab extends JPanel implements ActionListener {
    private RiemmanSum currentRiemmanSum;
    private JScrollPane dataPane;

    public DataTab() {
        super();
        this.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(5, 10);
        textArea.setEditable(false);
        dataPane = new JScrollPane(textArea);

        this.add(dataPane);


        /*
        for(int i = 0; i < 100; ++i) {
            textArea.append("ggg\n");
        }
         */

    }

    public void actionPerformed(ActionEvent e) {
      //  if (e.getSource().equals(computeBtn)) {
      //      //   functionEntryLabel.setText("Your Riemman Sum has been computed!");
      //  } else if (e.getSource().equals(functionTypeSelector)) {
      //      System.out.println(functionTypeSelector.getSelectedItem());
      //  }
    }
}
