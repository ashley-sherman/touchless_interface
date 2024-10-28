package moviekiosk;

import com.github.sarxos.webcam.Webcam;
import pointerdetector.FindPointer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MovieKioskMain {
    private static int SCREEN_WIDTH = 1280;
    private static int SCREEN_HEIGHT= 720;
    private static int WEBCAM_WIDTH= 640;
    private static int WEBCAM_HEIGHT = 480;

    //Finds the first available HD webcam, or any webcam if HD is not available.
    static Webcam findWebcam() {
        if (Webcam.getWebcams().size() == 1) {
            return Webcam.getDefault();
        }
        for (int i=0 ; i<Webcam.getWebcams().size(); i++) {
            Webcam webcam = Webcam.getWebcams().get(i);
            if (webcam.getName().startsWith("HD")) {
                return webcam;
            }
        }
        return Webcam.getDefault();
    }


    //main takes one parameter: ColorSelectionMode
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please supply a ColorSelectionMode.");
            return;
        }
        FindPointer.colorSelectionMode = FindPointer.ColorSelectionMode.valueOf(args[0]);

        Dimension screenSize = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
        Webcam webcam = findWebcam();
        webcam.setViewSize(new Dimension(WEBCAM_WIDTH, WEBCAM_HEIGHT));
        webcam.open();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);

        JPanel jPanel = new JPanel();
        //Empty image on which everything will be drawn
        BufferedImage displayImage = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
        JLabel picLabel = new JLabel(new ImageIcon(displayImage));

        frame.add(jPanel);
        frame.setVisible(true);
        jPanel.add(picLabel);

        HomeScreen homeScreen = new HomeScreen(displayImage, picLabel, webcam);
        homeScreen.start();
    }


}
