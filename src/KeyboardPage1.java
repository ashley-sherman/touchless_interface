import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class KeyboardPage1 extends Screen{
    private final JLabel imageLabel;
    private final Webcam webcam;

    final Button displayButton = new NotClickableButton("", new Point(0,600), 1275, 60, Color.RED);

    public KeyboardPage1(BufferedImage image, JLabel imageLabel, Webcam webcam) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
    }
    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        //butons go here
        Button a = new Button("A", new Point(140, 150), 100, 50, Color.BLUE);
        a.setTextStart(new Point(38,38));
        addButton(a);
        Button b = new Button("B", new Point(290, 150), 100, 50, Color.BLUE);
        b.setTextStart(new Point(38,38));
        addButton(b);
        Button c = new Button("C", new Point(440, 150), 100, 50, Color.BLUE);
        c.setTextStart(new Point(38,38));
        addButton(c);
        Button d = new Button("D", new Point(590, 150), 100, 50, Color.BLUE);
        d.setTextStart(new Point(38,38));
        addButton(d);
        Button e = new Button("E", new Point(740, 150), 100, 50, Color.BLUE);
        e.setTextStart(new Point(38,38));
        addButton(e);
        Button f = new Button("F", new Point(890, 150), 100, 50, Color.BLUE);
        f.setTextStart(new Point(38,38));
        addButton(f);
        Button g = new Button("G", new Point(1050, 150), 100, 50, Color.BLUE);
        g.setTextStart(new Point(38,38));
        addButton(g);
        Button h = new Button("H", new Point(225, 285), 100, 50, Color.BLUE);
        h.setTextStart(new Point(38,38));
        addButton(h);
        Button i = new Button("I", new Point(375, 285), 100, 50, Color.BLUE);
        i.setTextStart(new Point(42,38));
        addButton(i);
        Button j = new Button("J", new Point(525, 285), 100, 50, Color.BLUE);
        j.setTextStart(new Point(38,38));
        addButton(j);
        Button k = new Button("K", new Point(675, 285), 100, 50, Color.BLUE);
        k.setTextStart(new Point(38,38));
        addButton(k);
        Button l = new Button("L", new Point(825, 285), 100, 50, Color.BLUE);
        l.setTextStart(new Point(38,38));
        addButton(l);
        Button m = new Button("M", new Point(975, 285), 100, 50, Color.BLUE);
        m.setTextStart(new Point(38,38));
        addButton(m);
        Button spacebar = new Button("SPACEBAR", new Point(475,450), 350, 50, Color.BLUE);
        spacebar.setTextStart(new Point(80,38));
        addButton(spacebar);
        Button next = new Button("Next", new Point(940, 450), 180, 50, Color.BLUE);
        next.setTextStart(new Point(50,38));
        addButton(next);
        Button backspace = new Button("Backspace", new Point(160,450), 200, 50, Color.BLUE);
        backspace.setTextStart(new Point(12,38));
        addButton(backspace);

        ArrayList<Button> letterButtons = new ArrayList<>();
        letterButtons.add(a);
        letterButtons.add(b);
        letterButtons.add(c);
        letterButtons.add(d);
        letterButtons.add(e);
        letterButtons.add(f);
        letterButtons.add(g);
        letterButtons.add(h);
        letterButtons.add(i);
        letterButtons.add(j);
        letterButtons.add(k);
        letterButtons.add(l);
        letterButtons.add(m);


        displayButton.setTextStart(new Point(20,37));
        addButton(displayButton);

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
                if(clickedButton.equals(next)){
                    KeyboardPage2 keyboardPage2 = new KeyboardPage2(this.getDisplayImage(), imageLabel, webcam, this);
                    keyboardPage2.start();
                    break;
                } else {
                    for (Button button : letterButtons) {
                        if (clickedButton.equals(button) ) {
                            displayButton.setName(displayButton.getName() + button.getName());
                            break;
                        } else if (clickedButton.equals(spacebar)){
                            displayButton.setName(displayButton.getName() + " ");
                            break;
                        } else if (clickedButton.equals(backspace)) {
                            if (displayButton.getName().length() > 0){
                                displayButton.setName(displayButton.getName().substring(0,displayButton.getName().length() - 1));
                            }
                        }
                    }
                }

            }
            draw();
            imageLabel.repaint();
        }
    }
}
