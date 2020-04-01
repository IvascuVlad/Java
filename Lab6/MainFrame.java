import javax.swing.*;

import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.JSplitPane.BOTTOM;
import static javax.swing.JSplitPane.TOP;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    ShapePanel shapePanel;

    public MainFrame() {
        super("My Drawing Application");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create the components
        canvas = new DrawingPanel(this);
        controlPanel = new ControlPanel(this);
        shapePanel = new ShapePanel(this);
        configPanel = new ConfigPanel(this);

        //arrange the components in the container (frame)
        //JFrame uses a BorderLayout by default
        add(canvas, CENTER); //this is BorderLayout.CENTER
        add(configPanel, BorderLayout.NORTH);
        add(controlPanel,BorderLayout.SOUTH);
        add(shapePanel,BorderLayout.EAST);

        //invoke the layout manager
        pack();
    }
}
