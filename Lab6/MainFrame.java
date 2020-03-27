import javax.swing.*;

import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.JSplitPane.BOTTOM;
import static javax.swing.JSplitPane.TOP;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame() {
        super("My Drawing Application");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create the components
        canvas = new DrawingPanel(this);
        configPanel = new ConfigPanel(this);
        controlPanel = new ControlPanel(this);

        //arrange the components in the container (frame)
        //JFrame uses a BorderLayout by default
        add(canvas, CENTER); //this is BorderLayout.CENTER
        add(configPanel, BorderLayout.NORTH);
        add(controlPanel,BorderLayout.SOUTH);

        //invoke the layout manager
        pack();
    }
}
