import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingPanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 600;
    BufferedImage image; //the offscreen image
    Graphics2D graphics; //the "tools" needed to draw in the image
    List<paintComponent> shapes = new ArrayList<>();
    
    public DrawingPanel(MainFrame frame) {
        this.frame = frame; createOffscreenImage(); init();
    }
    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE); //fill the image with white
        graphics.fillRect(0, 0, W, H);
    }
    //...NEXT SLIDE
    private void init() {
        setPreferredSize(new Dimension(W, H)); //don’t use setSize. Why?
        setBorder(BorderFactory.createEtchedBorder()); //for fun
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawShape(e.getX(), e.getY()); repaint();
            } //Can’t use lambdas, JavaFX does a better job in these cases
        });
    }

    public void drawComponents(){
        for (paintComponent shape : shapes) {
            graphics.setColor(shape.color);
            graphics.fill(shape.shape);
        }
    }

    private void drawShape(int x, int y) {
        Random rand = new Random();
        int radius = rand.nextInt(100); //generate a random number
        int sides = (int) frame.configPanel.sidesField.getValue(); //get the value from UI (in ConfigPanel)
        Color color;
        if(frame.configPanel.colorCombo.getSelectedItem()=="Black")
            color = Color.black;
        else {
            Random random = new Random();
            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();
            color = Color.getHSBColor(r,g,b);
        }
         //create a transparent random Color.
        if(frame.shapePanel.shapeContor == 0)//square
        {
            paintComponent paintComponent = new paintComponent(new RegularPolygon(x, y, radius, sides),color);
            shapes.add(paintComponent);
            drawComponents();
        }
        if(frame.shapePanel.shapeContor == 1)//circle
        {
            paintComponent paintComponent = new paintComponent(new NodeShape(x, y, radius),color);
            shapes.add(paintComponent);
            drawComponents();
        }
    }
    @Override
    public void update(Graphics g) { } //Why did I do that?

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

}
