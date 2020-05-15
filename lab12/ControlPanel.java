import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JTextField classText = new JTextField("Enter class");
    JTextField nameText = new JTextField("Enter name ( 'default' if not set)");
    JButton submitBtn = new JButton("Submit");
    String className, name;

    public ControlPanel(MainFrame frame){
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 3));
        add(classText); add(nameText); add(submitBtn);
        classText.addActionListener(this::classInput);
        nameText.addActionListener(this::nameInput);
        submitBtn.addActionListener(this::submit);
    }

    private void submit(ActionEvent actionEvent) {
        if(name == null || name.equals("Enter name ( 'Default' if not set)"))
            name = "Default";
        frame.designPanel.addComponent(className,name);

    }

    private void nameInput(ActionEvent actionEvent) {
        name = nameText.getText();
        nameText.selectAll();
    }

    private void classInput(ActionEvent actionEvent) {
        className = classText.getText();
        classText.selectAll();
    }
}
