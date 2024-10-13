import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;



public class TestImageDisplayer {
    public static void main(String args[]) throws Exception {
        BufferedImage myPicture = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        myPicture.setRGB(100, 200, Color.RED.getRGB());
        myPicture.setRGB(110, 210, Color.GREEN.getRGB());

        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);
        JFrame f = new JFrame();
        f.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
        f.add(jPanel);
        f.setVisible(true);

        for (int i=0; i<100; i++) {
            myPicture.setRGB(50, i, Color.BLUE.getRGB());
            picLabel.repaint();
            Thread.sleep(10);
        }
    }
}
