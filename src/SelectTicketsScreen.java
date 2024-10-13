import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class SelectTicketsScreen extends Screen{
    private final JLabel imageLabel;
    private final Webcam webcam;
    private final String movieName;
    private final String movieTime;


    public SelectTicketsScreen(BufferedImage image, JLabel imageLabel, Webcam webcam, String movieName, String movieTime) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
        this.movieName = movieName;
        this.movieTime = movieTime;
    }

    public void start() {
        imageLabel.setVisible(true);
        clearDisplayImage();
        addButton(new NotClickableButton("Select number of tickets for " + movieName + " at " + movieTime, new Point (50, 5), 1000, 50, Color.RED));
        Button adults = new NotClickableButton("Adults:", new Point(450, 150), 200, 50, Color.BLUE);
        adults.setTextStart(new Point(20,36));
        addButton(adults);
        IntegerButton adultTicketNumber = new IntegerButton("0", new Point(450, 200), 100, 40, Color.MAGENTA, 0);
        adultTicketNumber.setTextStart(new Point(20,33));
        addButton(adultTicketNumber);
        addButton(adultTicketNumber);
        IntegerButton childTicketNumber = new IntegerButton("0", new Point(450, 400), 100, 40, Color.MAGENTA, 0);
        childTicketNumber.setTextStart(new Point(20,33));
        addButton(childTicketNumber);
        IntegerButton elderlyTicketNumber = new IntegerButton("0", new Point(450, 570), 100, 40, Color.MAGENTA, 0);
        elderlyTicketNumber.setTextStart(new Point(20,33));
        addButton(elderlyTicketNumber);
        Button children = new NotClickableButton("Children:", new Point(450, 350), 200, 50, Color.BLUE);
        children.setTextStart(new Point(20,36));
        addButton(children);
        Button seniors = new NotClickableButton("Seniors:", new Point(450, 520), 200, 50, Color.BLUE);
        seniors.setTextStart(new Point(20,36));
        addButton(seniors);
        Button plus = new Button("+", new Point (750, 150), 250, 50, Color.GREEN);
        addButton(plus);
        Button minus = new Button("-", new Point (150, 150), 250, 50, Color.RED);
        addButton(minus);
        Button plus1 = new Button("+", new Point (750, 350), 250, 50, Color.GREEN);
        addButton(plus1);
        Button minus1 = new Button("-", new Point (150, 350), 250, 50, Color.RED);
        addButton(minus1);
        Button plus2 = new Button("+", new Point (750, 520), 250, 50, Color.GREEN);
        addButton(plus2);
        Button minus2 = new Button("-", new Point (150, 520), 250, 50, Color.RED);
        addButton(minus2);
        Button submit = new Button("Next", new Point (1030, 520), 200, 50, Color.BLUE);
        submit.setTextStart(new Point(62,36));
        addButton(submit);

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
            if(isSwipedLeft(getObservations())){
                SelectShowtimeScreen selectShowtimeScreen = new SelectShowtimeScreen(this.getDisplayImage(), imageLabel, webcam, movieName);
                selectShowtimeScreen.start();
                break;
            } else if(isSwipedRight(getObservations())){
                ExitScreen exitScreen = new ExitScreen(this.getDisplayImage(), imageLabel, webcam, movieName);
                exitScreen.start();
                break;
            }
            Button selectedButton = findSelectedButton(pointerLocation);
            setSelectedButton(selectedButton);
            Button clickedButton = findClickedButton();
            if (clickedButton != null) {
                if(clickedButton.equals(plus)){
                    adultTicketNumber.add();
                } else if(clickedButton.equals(minus)){
                    adultTicketNumber.subtract();
                } else if(clickedButton.equals(plus1)){
                    childTicketNumber.add();
                } else if(clickedButton.equals(minus1)){
                    childTicketNumber.subtract();
                } else if (clickedButton.equals(plus2)) {
                    elderlyTicketNumber.add();
                } else if(clickedButton.equals(minus2)){
                    elderlyTicketNumber.subtract();
                } else if(clickedButton.equals(submit)){
                    ExitScreen exitScreen = new ExitScreen(this.getDisplayImage(), imageLabel, webcam, movieName);
                    exitScreen.start();
                    break;
                }
            }

            draw();
            imageLabel.repaint();
        }
    }

}
