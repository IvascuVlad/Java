import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        ConfigPanel configPanel;

        MainFrame da = new MainFrame();
        da.setVisible(true);
        configPanel = new ConfigPanel(da);
        //da.add(configPanel);
        //da.configPanel.adaptingToShapes();
        da.pack();


    }
}
