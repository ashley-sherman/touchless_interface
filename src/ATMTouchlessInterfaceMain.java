import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ATMTouchlessInterfaceMain {
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
            FindPointer.colorSelectionMode = FindPointer.ColorSelectionMode.valueOf(args[0]);

            //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension screenSize = new Dimension(1280, 720);
            Webcam webcam = findWebcam();
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(screenSize.width, screenSize.height);

            JPanel jPanel = new JPanel();
            BufferedImage displayImage = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
            JLabel picLabel = new JLabel(new ImageIcon(displayImage));


            frame.add(jPanel);
            frame.setVisible(true);
            jPanel.add(picLabel);


            ATMHomeScreen ATMHomeScreen = new ATMHomeScreen(displayImage, picLabel, webcam);
            ATMHomeScreen.start();


        }


    }
