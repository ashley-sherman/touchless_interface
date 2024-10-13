import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class ATMPinScreen extends Screen {
    private final JLabel imageLabel;
    private final Webcam webcam;

    private ArrayList<Button> starButtons = new ArrayList<>();
    private int nextPinDigitIndex = 0;

    public ATMPinScreen(BufferedImage image, JLabel imageLabel, Webcam webcam) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
    }

    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        Button enterPinMessage = new NotClickableButton("Enter your pin:", new Point(50, 30), 100,50, Color.BLACK);
        enterPinMessage.setTextStart(new Point(50,40));
        addButton(enterPinMessage);
        Button enter = new Button("Enter", new Point(1040, 540), 150,50, Color.BLUE);
        enter.setTextStart(new Point(30,40));
        addButton(enter);
        Button one = new Button("1", new Point(100,150), 100, 50, Color.RED);
        one.setTextStart(new Point(40,40));
        addButton(one);
        Button two = new Button("2", new Point(500,150), 100, 50, Color.RED);
        two.setTextStart(new Point(40,40));
        addButton(two);
        Button three = new Button("3", new Point(900,150), 100, 50, Color.RED);
        three.setTextStart(new Point(40,40));
        addButton(three);
        Button four = new Button("4", new Point(100,300), 100, 50, Color.RED);
        four.setTextStart(new Point(40,40));
        addButton(four);
        Button five = new Button("5", new Point(500,300), 100, 50, Color.RED);
        five.setTextStart(new Point(40,40));
        addButton(five);
        Button six = new Button("6", new Point(900,300), 100, 50, Color.RED);
        six.setTextStart(new Point(40,40));
        addButton(six);
        Button seven = new Button("7", new Point(100,450), 100, 50, Color.RED);
        seven.setTextStart(new Point(40,40));
        addButton(seven);
        Button eight = new Button("8", new Point(500,450), 100, 50, Color.RED);
        eight.setTextStart(new Point(40,40));
        addButton(eight);
        Button nine = new Button("9", new Point(900,450), 100, 50, Color.RED);
        nine.setTextStart(new Point(40,40));
        addButton(nine);
        Button star1 = new NotClickableButton("", new Point(50,600), 50,40,Color.BLUE);
        star1.setTextStart(new Point(18,40));
        addButton(star1);
        starButtons.add(star1);
        Button star2 = new NotClickableButton("", new Point(200,600), 50,40, Color.BLUE);
        star2.setTextStart(new Point(18,40));
        addButton(star2);
        starButtons.add(star2);
        Button star3 = new NotClickableButton("", new Point(350,600), 50,40, Color.BLUE);
        star3.setTextStart(new Point(18,40));
        addButton(star3);
        starButtons.add(star3);
        Button star4 = new NotClickableButton("", new Point(500,600), 50,40, Color.BLUE);
        star4.setTextStart(new Point(18,40));
        addButton(star4);
        starButtons.add(star4);

        draw();
        imageLabel.repaint();

        double horizontalScale = getDisplayImage().getWidth() / webcam.getViewSize().getWidth();
        double verticalScale = getDisplayImage().getHeight() / webcam.getViewSize().getHeight();


        LocalTime first = LocalTime.now();
        while (true) {
            BufferedImage webcamImage = webcam.getImage();
            FindPointer borderFinder = new FindPointer();
            Point pointerLocation = borderFinder.findPointerLocation(webcamImage);
            if (pointerLocation == null) {
                continue;
            }
            pointerLocation = mirrorAndScale(pointerLocation, super.getDisplayImage(), horizontalScale, verticalScale);
            recordObservation(new Observation(pointerLocation));
            Button selectedButton = findSelectedButton(pointerLocation);
            setSelectedButton(selectedButton);
            Button clickedButton = findClickedButton();
            if (clickedButton != null) {
                if (clickedButton.equals(enter)) {
                    ATMPickActionScreen atmPickActionScreen = new ATMPickActionScreen(this.getDisplayImage(), imageLabel, webcam);
                    atmPickActionScreen.start();
                }
                else{
                    starButtons.get(nextPinDigitIndex).setName("*");
                    nextPinDigitIndex++;
                }
            }
            draw();
            imageLabel.repaint();
        }
    }
}