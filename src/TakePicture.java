import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TakePicture {
    public static void main(String[] args) throws Exception {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
        BufferedImage image = webcam.getImage();
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < image.getHeight(); y++) {
//                int rgb = image.getRGB(x,y);
//                Color color = new Color(rgb);
//                System.out.printf("Color at %s, %s = red: %s, green: %s, blue: %s",
//                        x, y, color.getRed(), color.getGreen(), color.getBlue());
//                System.out.println();
//            }
//        }
        //save image to file
        ImageIO.write(image, "PNG", new File("ashley.png"));
    }
}





