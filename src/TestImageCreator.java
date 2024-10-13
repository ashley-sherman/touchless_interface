import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class TestImageCreator {
    public static void main(String[] args) throws Exception {
        Color color = Color.GREEN;

        BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        Point topLeft = new Point(300, 200);
        int shapeWidth = 40;
        int shapeHeight = 25;

        int rgb = color.getRGB();
        for (int x=topLeft.x; x < (topLeft.x + shapeWidth); x++) {
            for (int y=topLeft.y; y<(topLeft.y + shapeHeight); y++) {
                int xOffset = y - topLeft.y;
                image.setRGB(x + xOffset, y, rgb);
            }
        }

        Random randomGenerator = new Random();
        for (int x=0; x<image.getWidth(); x++) {
            for (int y=0; y<image.getHeight(); y++) {
                int diceRoll = randomGenerator.nextInt(5);
                if (diceRoll == 0) {
                    int colorRoll = randomGenerator.nextInt(3);
                    Color randomColor;
                    if (colorRoll == 0) {
                        randomColor = Color.RED;
                    } else if (colorRoll == 1) {
                        randomColor = Color.GREEN;
                    } else {
                        randomColor = Color.BLUE;
                    }
                    image.setRGB(x, y, randomColor.getRGB());
                }
            }
        }

        ImageIO.write(image, "PNG", new File("TestImage3.png"));
        System.out.println("Written image");
    }
}
