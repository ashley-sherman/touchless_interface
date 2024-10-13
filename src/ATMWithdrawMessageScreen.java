import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
public class ATMWithdrawMessageScreen extends Screen{
    private final JLabel imageLabel;
    private final Webcam webcam;
    private final int number;

    public ATMWithdrawMessageScreen(BufferedImage image, JLabel imageLabel, Webcam webcam, int number) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
        this.number = number;
    }

    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        Button message = new NotClickableButton("You have withdrawn " + number + " dollars.", new Point(300, 200), 800, 75, Color.BLACK);
        message.setTextStart(new Point(30, 50));
        addButton(message);

        draw();
        imageLabel.repaint();

        double horizontalScale = getDisplayImage().getWidth() / webcam.getViewSize().getWidth();
        double verticalScale = getDisplayImage().getHeight() / webcam.getViewSize().getHeight();


        LocalTime first = LocalTime.now();
        while (true) {
            LocalTime now = LocalTime.now();
            BufferedImage webcamImage = webcam.getImage();
            FindPointer borderFinder = new FindPointer();
            Point pointerLocation = borderFinder.findPointerLocation(webcamImage);
            if (pointerLocation == null){
                continue;
            }
            pointerLocation = mirrorAndScale(pointerLocation, super.getDisplayImage(), horizontalScale, verticalScale);
            recordObservation(new Observation(pointerLocation));
            if (now.isAfter(first.plusSeconds(4))) {
                ATMHomeScreen atmHomeScreen = new ATMHomeScreen(this.getDisplayImage(), imageLabel, webcam);
                atmHomeScreen.start();
                break;
            }

            draw();

            imageLabel.repaint();
        }
    }
}
