import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class ShapePanel extends JPanel {
    final MainFrame frame;
    String shapeNames[] = {"Square","Circle"};
    JList shapeList = new JList(shapeNames);
    JButton squareBtn = new JButton("Polygon");
    JButton circleBtn = new JButton("Circle");
    JButton undoBtn = new JButton("Undo");
    int shapeContor = 0;

    public ShapePanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new GridLayout(2, 1));
        add(shapeList);
        shapeList.addListSelectionListener(this::lista);
        add(undoBtn);
        undoBtn.addActionListener(this::undo);
        /*add(squareBtn); add(circleBtn);
        squareBtn.addActionListener(this::square);
        circleBtn.addActionListener(this::circle);*/
    }

    private void undo(ActionEvent actionEvent) {
        if(frame.canvas.shapes.size()>0) {
            frame.canvas.shapes.remove(frame.canvas.shapes.size() - 1);
            frame.canvas.image = new BufferedImage(frame.canvas.W, frame.canvas.H, BufferedImage.TYPE_INT_ARGB);
            frame.canvas.graphics = frame.canvas.image.createGraphics();
            frame.canvas.graphics.setColor(Color.WHITE); //fill the image with white
            frame.canvas.graphics.fillRect(0, 0, frame.canvas.W, frame.canvas.H);
            frame.canvas.repaint();
            frame.canvas.drawComponents();
        }
    }

    private void lista(ListSelectionEvent listSelectionEvent) {
        frame.configPanel.deletingToShapes();
        shapeContor = shapeList.getSelectedIndex();
        frame.configPanel.adaptingToShapes();
        frame.pack();
    }
}
