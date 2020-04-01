import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class ControlPanel<optione> extends JPanel {
    final MainFrame frame;
    JButton saveBtn = new JButton("Save");
    JButton loadBtn = new JButton("Load");
    JButton resetBtn = new JButton("Reset");
    JButton exitBtn = new JButton("Exit");
    //create all buttons (Load, Reset, Exit)

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        setLayout(new GridLayout(1, 4));
        //add all buttons
        add(saveBtn); add(loadBtn); add(resetBtn); add(exitBtn);
        //configure listeners for all buttons
        saveBtn.addActionListener(this::save);
        loadBtn.addActionListener(this::load);
        resetBtn.addActionListener(this::reset);
        exitBtn.addActionListener(this::exit);
    }

    private void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void reset(ActionEvent actionEvent) {
        frame.canvas.image = new BufferedImage(frame.canvas.W, frame.canvas.H, BufferedImage.TYPE_INT_ARGB);
        frame.canvas.graphics = frame.canvas.image.createGraphics();
        frame.canvas.graphics.setColor(Color.WHITE); //fill the image with white
        frame.canvas.graphics.fillRect(0, 0, frame.canvas.W, frame.canvas.H);
        frame.canvas.repaint();
    }

    private void load(ActionEvent actionEvent) {
        try {
            JFrame jFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose File");
            int option = fileChooser.showSaveDialog(jFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                BufferedImage image = ImageIO.read(file);
                frame.canvas.image = image;
                frame.canvas.graphics = image.createGraphics();
                frame.canvas.repaint();
            }
        } catch (IOException ex) { System.err.println(ex); }
    }

    private void save(ActionEvent e) {
        try {
            JFrame jFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose File");
            int option = fileChooser.showSaveDialog(jFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(frame.canvas.image, "PNG", file);
            }
        } catch (IOException ex) { System.err.println(ex); }
    }
}