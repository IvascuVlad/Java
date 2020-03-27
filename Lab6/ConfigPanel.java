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
        add(sidesLabel); //JPanel uses FlowLayout by default
        add(sidesField);
        add(colorCombo);
        colorCombo.addActionListener(this::actiune);
        sidesField.addChangeListener(this::change);
    }

    private void change(ChangeEvent changeEvent) {
        int result = (int) sidesField.getValue();
        System.out.println(result);
    }

    private void actiune(ActionEvent actionEvent) {
        String result = colorCombo.getSelectedItem().toString();
        System.out.println(result);
    }
}
