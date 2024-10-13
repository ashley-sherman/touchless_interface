import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

public class ExitScreen extends Screen{
    private final JLabel imageLabel;
    private final Webcam webcam;
    private final String movieName;

    public ExitScreen(BufferedImage image, JLabel imageLabel, Webcam webcam, String movieName) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
        this.movieName = movieName;
    }


    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        Button enjoyMessage = new NotClickableButton("Enjoy " + movieName + " !!!", new Point(400, 200), 500, 200, Color.BLUE);
        enjoyMessage.setTextStart(new Point(110,110));
        addButton(enjoyMessage);
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
            if (now.isAfter(first.plusSeconds(3))) {
                HomeScreen homeScreen = new HomeScreen(this.getDisplayImage(), imageLabel, webcam);
                homeScreen.start();
                break;
            }

            draw();

            imageLabel.repaint();
        }
    }
}

