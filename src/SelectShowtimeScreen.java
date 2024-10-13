import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectShowtimeScreen extends Screen {
    private final JLabel imageLabel;
    private final Webcam webcam;

    private final String movieName;

    public SelectShowtimeScreen(BufferedImage image, JLabel imageLabel, Webcam webcam, String movieName) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
        this.movieName = movieName;
    }

    public void start() {
        imageLabel.setVisible(true);
        clearDisplayImage();
        addButton(new NotClickableButton("Select showtime for " + movieName, new Point(40, 40), 800, 55, Color.GREEN));
        Button one = new Button("7:00 PM", new Point(300, 200), 600, 100, Color.BLUE);
        one.setTextStart(new Point(240,60));
        addButton(one);
        Button two = new Button("9:15 PM", new Point(300, 450), 600, 100, Color.BLUE);
        two.setTextStart(new Point(240,60));
        addButton(two);
        double horizontalScale = getDisplayImage().getWidth() / webcam.getViewSize().getWidth();
        double verticalScale = getDisplayImage().getHeight() / webcam.getViewSize().getHeight();
        draw();
        imageLabel.repaint();

        while (true) {
            BufferedImage webcamImage = webcam.getImage();
            FindPointer borderFinder = new FindPointer();
            Point pointerLocation = borderFinder.findPointerLocation(webcamImage);
            if (pointerLocation == null){
                continue;
            }
            pointerLocation = mirrorAndScale(pointerLocation, super.getDisplayImage(), horizontalScale, verticalScale);
            recordObservation(new Observation(pointerLocation));
            if (isSwipedLeft(getObservations())) {
                HomeScreen homeScreen = new HomeScreen(this.getDisplayImage(), imageLabel, webcam);
                homeScreen.start();
                break;
            }

            Button selectedButton = findSelectedButton(pointerLocation);
            /*if (selectedButton != null && selectedButton.getName().startsWith("Select showtime")) {
                selectedButton = null;
            }*/
            setSelectedButton(selectedButton);
            Button clickedButton = findClickedButton();
            if (clickedButton != null) {
                SelectTicketsScreen selectTicketsScreen = new SelectTicketsScreen(this.getDisplayImage(), imageLabel, webcam, movieName, clickedButton.getName());
                selectTicketsScreen.start();
                break;
            }

            draw();
            imageLabel.repaint();
        }
    }

}
