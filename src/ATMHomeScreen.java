import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class ATMHomeScreen extends Screen{
    private final JLabel imageLabel;
    private final Webcam webcam;
    public ATMHomeScreen(BufferedImage image, JLabel imageLabel, Webcam webcam) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
    }

    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        Button welcomeMessage = new NotClickableButton("WELCOME TO THE BANK.", new Point(400, 150), 500, 60, Color.BLUE);
        welcomeMessage.setTextStart(new Point(40,42));
        addButton(welcomeMessage);
        Button insertCardMessage = new NotClickableButton("Please insert your card.", new Point(415, 350), 470, 60, Color.BLACK);
        insertCardMessage.setTextStart(new Point(40,42));
        addButton(insertCardMessage);


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
                ATMPinScreen atmPinScreen = new ATMPinScreen(this.getDisplayImage(), imageLabel, webcam);
                atmPinScreen.start();
                break;
            }

            draw();

            imageLabel.repaint();
        }
    }
}
