import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawTestMain {
    public static void main(String[] arguments) {
        BufferedImage displayImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        JLabel picLabel = new JLabel(new ImageIcon(displayImage));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);
        JFrame f = new JFrame();
        f.setSize(new Dimension(displayImage.getWidth(), displayImage.getHeight()));
        f.add(jPanel);
        Graphics graphics = displayImage.getGraphics();

        Button button = new Button("A", new Point(100, 100), 75, 20, Color.PINK);
        //button.drawRegular(graphics);

        Button button2 = new Button("B", new Point(300, 300), 50, 50, Color.GREEN);
        //button2.drawRegular(graphics);

        if (button2.inButton(new Point (305, 310))){
            System.out.println("In Button");
        } else{
            System.out.println("not in button");
        }

        Button button3 = new Button("C", new Point(400, 250), 20, 70, Color.BLUE);
        //button3.drawRegular(graphics);

        if (button3.inButton(new Point (305, 310))) {
            System.out.println("In Button");
        } else{
            System.out.println("not in button");
        }


        f.setVisible(true);
    }
}
