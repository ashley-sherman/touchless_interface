import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class ATMPickActionScreen extends Screen {
    private final JLabel imageLabel;
    private final Webcam webcam;

    public ATMPickActionScreen(BufferedImage image, JLabel imageLabel, Webcam webcam) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
    }

    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        Button welcomeMessage = new NotClickableButton("What would you like to do?", new Point(400, 50), 500, 60, Color.BLACK);
        welcomeMessage.setTextStart(new Point(40, 42));
        addButton(welcomeMessage);
        Button withdraw = new Button("Withdraw", new Point(520, 240), 300, 60, Color.BLUE);
        withdraw.setTextStart(new Point(75, 40));
        addButton(withdraw);
        Button deposit = new Button("Deposit", new Point(260, 450), 300, 60, Color.BLUE);
        deposit.setTextStart(new Point(80, 40));
        addButton(deposit);
        Button checkBalance = new Button("Check Balance", new Point(780, 450), 300, 60, Color.BLUE);
        checkBalance.setTextStart(new Point(30, 40));
        addButton(checkBalance);

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
                if (clickedButton.equals(withdraw)) {
                    ATMWithdrawScreen atmWithdrawScreen = new ATMWithdrawScreen(this.getDisplayImage(), imageLabel, webcam);
                    atmWithdrawScreen.start();
                    break;
                } else if (clickedButton.equals(deposit)) {
                    ATMDepositScreen atmDepositScreen = new ATMDepositScreen(this.getDisplayImage(), imageLabel, webcam);
                    atmDepositScreen.start();
                    break;
                }
                if (clickedButton.equals(checkBalance)) {
                    ATMCheckBalanceScreen atmCheckBalanceScreen = new ATMCheckBalanceScreen(this.getDisplayImage(), imageLabel, webcam);
                    atmCheckBalanceScreen.start();
                    break;
                }
            }
            draw();
            imageLabel.repaint();
        }
    }
}