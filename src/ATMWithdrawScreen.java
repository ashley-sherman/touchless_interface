import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
public class ATMWithdrawScreen extends Screen{
    private final JLabel imageLabel;
    private final Webcam webcam;

    public ATMWithdrawScreen(BufferedImage image, JLabel imageLabel, Webcam webcam) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
    }

    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        Button withdrawMessage = new NotClickableButton("Select the amount you would like to withdraw.", new Point(225, 50), 100, 50, Color.BLACK);
        withdrawMessage.setTextStart(new Point(40,20));
        addButton(withdrawMessage);
        Button total = new NotClickableButton("Total: ", new Point(270,100), 150, 50, Color.BLUE);
        total.setTextStart(new Point(35,40));
        addButton(total);
        IntegerButton totalCount = new IntegerButton("0", new Point(420, 100), 75, 50, Color.BLUE, 0);
        totalCount.setTextStart(new Point(5,40));
        addButton(totalCount);
        Button oneHundredButton = new NotClickableButton("$100", new Point(550,200),200,50,Color.BLUE);
        oneHundredButton.setTextStart(new Point(60,38));
        addButton(oneHundredButton);
        IntegerButton oneHundredCount = new IntegerButton("0", new Point(550, 250), 100, 50, Color.MAGENTA, 0);
        oneHundredCount.setTextStart(new Point(40,39));
        addButton(oneHundredCount);
        Button plus = new Button("+", new Point(850, 200), 150, 50, Color.GRAY);
        plus.setTextStart(new Point(63,40));
        addButton(plus);
        Button minus = new Button("-", new Point(300, 200), 150, 50, Color.GRAY);
        minus.setTextStart(new Point(63,35));
        addButton(minus);
        Button twentyButton = new NotClickableButton("$20", new Point(550,350),200,50,Color.BLUE);
        twentyButton.setTextStart(new Point(65,38));
        addButton(twentyButton);
        IntegerButton twentyCount = new IntegerButton("0", new Point(550, 400), 100, 50, Color.MAGENTA, 0);
        twentyCount.setTextStart(new Point(40,39));
        addButton(twentyCount);
        Button plus1 = new Button("+", new Point(850, 350), 150, 50, Color.GRAY);
        plus1.setTextStart(new Point(63,40));
        addButton(plus1);
        Button minus1 = new Button("-", new Point(300, 350), 150, 50, Color.GRAY);
        minus1.setTextStart(new Point(63,35));
        addButton(minus1);
        Button fiveButton = new NotClickableButton("$5", new Point(550,500),200,50,Color.BLUE);
        fiveButton.setTextStart(new Point(75,38));
        addButton(fiveButton);
        IntegerButton fiveCount = new IntegerButton("0", new Point(550, 550), 100, 50, Color.MAGENTA, 0);
        fiveCount.setTextStart(new Point(40,39));
        addButton(fiveCount);
        Button plus2 = new Button("+", new Point(850, 500), 150, 50, Color.GRAY);
        plus2.setTextStart(new Point(63,40));
        addButton(plus2);
        Button minus2 = new Button("-", new Point(300, 500), 150, 50, Color.GRAY);
        minus2.setTextStart(new Point(63,35));
        addButton(minus2);
        Button enter = new Button("Enter", new Point(1050, 500), 150, 50, Color.PINK);
        enter.setTextStart(new Point(30,40));
        addButton(enter);



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
            if (isSwipedLeft(getObservations())){
                ATMPickActionScreen atmPickActionScreen = new ATMPickActionScreen(this.getDisplayImage(), imageLabel, webcam);
                atmPickActionScreen.start();
                break;
            }
            if (isSwipedRight(getObservations())){
                ATMWithdrawMessageScreen atmWithdrawMessageScreen = new ATMWithdrawMessageScreen(this.getDisplayImage(), imageLabel, webcam, totalCount.getNumber());
                atmWithdrawMessageScreen.start();
                break;
            }
            recordObservation(new Observation(pointerLocation));
            Button selectedButton = findSelectedButton(pointerLocation);
            setSelectedButton(selectedButton);
            Button clickedButton = findClickedButton();
            if (clickedButton != null) {
                if (clickedButton.equals(plus)){
                    oneHundredCount.add();
                    totalCount.addMoreThanOne(100);
                } else if (clickedButton.equals(plus1)) {
                    twentyCount.add();
                    totalCount.addMoreThanOne(20);
                } else if(clickedButton.equals(plus2)){
                    fiveCount.add();
                    totalCount.addMoreThanOne(5);
                } else if(clickedButton.equals(minus)){
                    if (totalCount.getNumber() > 99){
                        oneHundredCount.subtract();
                        totalCount.subtractLessThanOne(100);
                    }
                } else if(clickedButton.equals(minus1)){
                    if (totalCount.getNumber() > 19){
                        twentyCount.subtract();
                        totalCount.subtractLessThanOne(20);
                    }
                } else if(clickedButton.equals(minus2)){
                    if (totalCount.getNumber() > 4){
                        fiveCount.subtract();
                        totalCount.subtractLessThanOne(5);
                    }
                } else if(clickedButton.equals(enter)){
                    ATMWithdrawMessageScreen atmWithdrawMessageScreen = new ATMWithdrawMessageScreen(this.getDisplayImage(), imageLabel, webcam, totalCount.getNumber());
                    atmWithdrawMessageScreen.start();
                    break;
                }
            }
            draw();
            imageLabel.repaint();
        }
    }
}