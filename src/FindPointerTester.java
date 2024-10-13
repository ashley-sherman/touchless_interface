import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FindPointerTester {
    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("ashley.png"));
        FindPointer borderFinder = new FindPointer();
        Point center = borderFinder.findPointerLocation(image);
        System.out.println("The center is: " + center);
        Color color = new Color (image.getRGB(center.x, center.y));
        Color color1 = new Color (image.getRGB(557, 200));
        System.out.println("red:" + color1.getRed());
        System.out.println("green:" + color1.getGreen());
        System.out.println("blue:" + color1.getBlue());

        image.setRGB(center.x, center.y, Color.RED.getRGB());
        image.setRGB(center.x-1, center.y-1, Color.RED.getRGB());
        image.setRGB(center.x+1, center.y+1, Color.RED.getRGB());
        image.setRGB(center.x, center.y+1, Color.RED.getRGB());
        image.setRGB(center.x+1, center.y, Color.RED.getRGB());
        ImageIO.write(image, "PNG", new File("TestImage3.png"));

    }
}
