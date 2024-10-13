import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeScreen extends Screen {
    private final JLabel imageLabel;
    private final Webcam webcam;
    public HomeScreen(BufferedImage image, JLabel imageLabel, Webcam webcam) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
    }

    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        Button welcomeMessage = new NotClickableButton("Welcome to the Movies. Select a movie.", new Point(125, 20), 1000, 60, Color.RED);
        welcomeMessage.setTextStart(new Point(40,42));
        addButton(welcomeMessage);
        Button movieAButton = new Button("Movie A", new Point(150, 150), 350, 150, Color.BLUE);
        movieAButton.setImage(findMovieImage("Pictures/space.jpg").getScaledInstance(155, 80, BufferedImage.SCALE_DEFAULT));
        addButton(movieAButton);
        Button movieBButton = new Button("Movie B", new Point(150, 400), 350, 150, Color.BLUE);
        movieBButton.setImage(findMovieImage("Pictures/flowers.jpeg").getScaledInstance(155, 80, BufferedImage.SCALE_DEFAULT));
        addButton(movieBButton);
        Button movieCButton = new Button("Movie C", new Point(750,150), 350, 150, Color.BLUE);
        movieCButton.setImage(findMovieImage("Pictures/water.jpeg").getScaledInstance(155, 80, BufferedImage.SCALE_DEFAULT));
        addButton(movieCButton);
        Button movieDButton = new Button("Movie D", new Point(750, 400), 350, 150, Color.BLUE);
        movieDButton.setImage(findMovieImage("Pictures/beach.jpeg").getScaledInstance(155, 80, BufferedImage.SCALE_DEFAULT));
        addButton(movieDButton);
        draw();
        imageLabel.repaint();

        double horizontalScale = getDisplayImage().getWidth() / webcam.getViewSize().getWidth();
        double verticalScale = getDisplayImage().getHeight() / webcam.getViewSize().getHeight();


        while (true) {
            BufferedImage webcamImage = webcam.getImage();
            FindPointer borderFinder = new FindPointer();
            Point pointerLocation = borderFinder.findPointerLocation(webcamImage);
            if (pointerLocation == null){
                continue;
            }
            pointerLocation = mirrorAndScale(pointerLocation, super.getDisplayImage(), horizontalScale, verticalScale);
            recordObservation(new Observation(pointerLocation));
            setSelectedButton(findSelectedButton(pointerLocation) );
            Button clickedButton = findClickedButton();
            if (clickedButton != null) {
                SelectShowtimeScreen selectShowtimeScreen = new SelectShowtimeScreen(getDisplayImage(), imageLabel, webcam, clickedButton.getName());
                selectShowtimeScreen.start();
                break;
            }

            draw();

            imageLabel.repaint();
        }
    }

    BufferedImage findMovieImage(String imageName) {
        try {
            return ImageIO.read(new File(imageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
