import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ControlPanel extends JPanel {
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
            BufferedImage image = ImageIO.read(new FileInputStream("C:\\Users\\ivasc\\Desktop\\Anul_II\\semestrul 2\\Java\\lab6\\test.png"));
            frame.canvas.image = image;
            frame.canvas.graphics = image.createGraphics();
            frame.canvas.repaint();
        } catch (IOException ex) { System.err.println(ex); }
    }

    private void save(ActionEvent e) {
        try {
            ImageIO.write(frame.canvas.image, "PNG", new FileOutputStream("C:\\Users\\ivasc\\Desktop\\Anul_II\\semestrul 2\\Java\\lab6\\test.png"));
        } catch (IOException ex) { System.err.println(ex); }
    }
}