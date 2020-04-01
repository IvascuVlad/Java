import javafx.scene.control.ComboBox;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel sidesLabel; // we’re drawing regular polygons
    JSpinner sidesField; // number of sides
    JComboBox colorCombo; // the color of the shape

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        //create the label and the spinner
        sidesLabel = new JLabel("Number of sides:");
        sidesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        sidesField.setValue(6); //default number of sides
        //create the colorCombo, containing the values: Random and Black
        String [] values = {"Random","Black"};
        colorCombo = new JComboBox(values);
        if(frame.shapePanel.shapeContor == 3)
        {
            add(sidesLabel); //JPanel uses FlowLayout by default
            add(sidesField);
            sidesField.addChangeListener(this::change);
            add(colorCombo);
            colorCombo.addActionListener(this::actiune);
        }
    }

    public void deletingToShapes(){
        if(frame.shapePanel.shapeContor == 1){
            this.remove(frame.configPanel.colorCombo);
        }
        if(frame.shapePanel.shapeContor == 0){
            this.remove(frame.configPanel.colorCombo);
            this.remove(frame.configPanel.sidesLabel);
            this.remove(frame.configPanel.sidesField);
        }
    }

    public void adaptingToShapes(){
        //this.frame = frame;
        if(frame.shapePanel.shapeContor == 1)
        {
            String [] values = {"Random","Black"};
            colorCombo = new JComboBox(values);
            add(colorCombo);
            colorCombo.addActionListener(this::actiune);
        }
        if(frame.shapePanel.shapeContor == 0)
        {
            sidesLabel = new JLabel("Number of sides:");
            sidesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            sidesField.setValue(6); //default number of sides
            //create the colorCombo, containing the values: Random and Black
            String [] values = {"Random","Black"};
            colorCombo = new JComboBox(values);
            add(sidesLabel); //JPanel uses FlowLayout by default
            add(sidesField);
            sidesField.addChangeListener(this::change);
            add(colorCombo);
            colorCombo.addActionListener(this::actiune);
        }
    }

    private void change(ChangeEvent changeEvent) {
        int result = (int) sidesField.getValue();
    }

    private void actiune(ActionEvent actionEvent) {
        String result = colorCombo.getSelectedItem().toString();
    }
}
