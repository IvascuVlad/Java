import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class DesignPanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 600;

    public DesignPanel(MainFrame frame){
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(null);
        setPreferredSize(new Dimension(W, H));
    }

    public void addComponent(String className, String name) {
        try {
            if(className == null)
                throw new NullPointerException();
            Class[] signature = new Class[]{String.class};
            Constructor ctor  = Class.forName(className).getConstructor(signature);
            JComponent jComponent = (JComponent) ctor.newInstance(name);

            Dimension size = jComponent.getPreferredSize();
            Random random = new Random();
            jComponent.setBounds(random.nextInt(W-1), random.nextInt(H-1), size.width, size.height);
            jComponent.setToolTipText(jComponent.getClass().getName());
            add(jComponent);
            frame.repaint();

        } catch ( ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Clasa invalida " + className);
        } catch (NullPointerException e){
            System.out.println("Apasa enter dupa introducerea numelui");
        }
    }
    @Override
    public void update(Graphics g) { }
}
