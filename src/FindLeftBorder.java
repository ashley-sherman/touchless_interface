import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FindLeftBorder {
    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("TestImage.png"));
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x,y);
                Color color = new Color(rgb);
                if (color.getGreen() > 250){
                    System.out.println("left border x: " + x + ", y: " + y);
                    break;
                }
            }
        }
    }

}
