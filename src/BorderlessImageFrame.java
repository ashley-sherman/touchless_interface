import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public

class BorderlessImageFrame {

    public static void main(String[] args) throws IOException {
        // Load the image
        BufferedImage image = ImageIO.read(new File("ashley.png"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calculate scaled image dimensions, preserving aspect ratio
        Image scaledImage = image.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);


        // Create a borderless frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Set the frame size to match the image dimensions
        frame.setSize(screenWidth, screenHeight);

        // Create a label to display the image
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        // Add the label to the frame
        frame.add(imageLabel);

        // Make the frame visible
        frame.setVisible(true);
    }
}