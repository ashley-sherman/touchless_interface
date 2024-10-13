import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class KeyboardPage2 extends Screen{
    private final JLabel imageLabel;
    private final Webcam webcam;
    private Button displayButton;
    private KeyboardPage1 previousScreen;

    public KeyboardPage2(BufferedImage image, JLabel imageLabel, Webcam webcam,  KeyboardPage1 previousScreen) {
        super(image);
        this.imageLabel = imageLabel;
        this.webcam = webcam;
        this.displayButton = previousScreen.displayButton;
        this.previousScreen = previousScreen;
    }
    public void start() {
        clearDisplayImage();
        imageLabel.setVisible(true);

        //butons go here
        addButton(displayButton);
        Button n = new Button("N", new Point(140, 150), 100, 50, Color.BLUE);
        n.setTextStart(new Point(38,38));
        addButton(n);
        Button o = new Button("O", new Point(290, 150), 100, 50, Color.BLUE);
        o.setTextStart(new Point(38,38));
        addButton(o);
        Button p = new Button("P", new Point(440, 150), 100, 50, Color.BLUE);
        p.setTextStart(new Point(38,38));
        addButton(p);
        Button q = new Button("Q", new Point(590, 150), 100, 50, Color.BLUE);
        q.setTextStart(new Point(38,38));
        addButton(q);
        Button r = new Button("R", new Point(740, 150), 100, 50, Color.BLUE);
        r.setTextStart(new Point(38,38));
        addButton(r);
        Button s = new Button("S", new Point(890, 150), 100, 50, Color.BLUE);
        s.setTextStart(new Point(38,38));
        addButton(s);
        Button t = new Button("T", new Point(1050, 150), 100, 50, Color.BLUE);
        t.setTextStart(new Point(38,38));
        addButton(t);
        Button u = new Button("U", new Point(225, 285), 100, 50, Color.BLUE);
        u.setTextStart(new Point(38,38));
        addButton(u);
        Button v = new Button("V", new Point(375, 285), 100, 50, Color.BLUE);
        v.setTextStart(new Point(42,38));
        addButton(v);
        Button w = new Button("W", new Point(525, 285), 100, 50, Color.BLUE);
        w.setTextStart(new Point(38,38));
        addButton(w);
        Button x = new Button("X", new Point(675, 285), 100, 50, Color.BLUE);
        x.setTextStart(new Point(38,38));
        addButton(x);
        Button y = new Button("Y", new Point(825, 285), 100, 50, Color.BLUE);
        y.setTextStart(new Point(38,38));
        addButton(y);
        Button z = new Button("Z", new Point(975, 285), 100, 50, Color.BLUE);
        z.setTextStart(new Point(38,38));
        addButton(z);
        Button spacebar = new Button("SPACEBAR", new Point(475,450), 350, 50, Color.BLUE);
        spacebar.setTextStart(new Point(80,38));
        addButton(spacebar);
        Button back = new Button("Back", new Point(940, 450), 180, 50, Color.BLUE);
        back.setTextStart(new Point(50,38));
        addButton(back);
        Button backspace = new Button("Backspace", new Point(160,450), 200, 50, Color.BLUE);
        backspace.setTextStart(new Point(12,38));
        addButton(backspace);

        ArrayList<Button> letterButtons = new ArrayList<>();
        letterButtons.add(n);
        letterButtons.add(o);
        letterButtons.add(p);
        letterButtons.add(q);
        letterButtons.add(r);
        letterButtons.add(s);
        letterButtons.add(t);
        letterButtons.add(u);
        letterButtons.add(v);
        letterButtons.add(w);
        letterButtons.add(x);
        letterButtons.add(y);
        letterButtons.add(z);



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
                if(clickedButton.equals(back)){
                    this.previousScreen.start();
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
