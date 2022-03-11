package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiemmanSumGUI extends JFrame implements ActionListener {
    private JLabel functionEntryLabel;
    private JTextField functionEntryField;

    public RiemmanSumGUI() {
        super("Riemman Sum Calculator");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));
        setLayout(new FlowLayout());


        JButton testButton = new JButton("Confirm Function");
        testButton.setActionCommand("Confirm Function");
        testButton.addActionListener(this); // Sets "this" object as an action listener for btn
        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click

        functionEntryLabel = new JLabel("Enter your function");
        functionEntryField = new JTextField(5);
        add(functionEntryLabel);
        add(functionEntryField);
        add(testButton);
        pack();
        setVisible(true);
    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Confirm Function")) {
            functionEntryLabel.setText("Your Riemman Sum has been computed!");
        }
    }

}
