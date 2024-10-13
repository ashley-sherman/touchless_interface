import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalTime;
import java.util.LinkedList;

public class Button {
    private String name;
    private Point topLeft;
    private int width;
    private int height;
    private Color regularColor;
    private LocalTime lastClicked;
    private Point textStart = new Point(110,45);


    private Color selectedColor = Color.PINK;

    private Image image;

    public Button(String name, Point topLeft, int width, int height, Color regularColor) {
        this.name = name;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.regularColor = regularColor;
    }

    public void setTextStart(Point textStart) {
        this.textStart = textStart;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void drawRegular(Graphics graphics) {
        graphics.setColor(regularColor);
        graphics.fillRect(topLeft.x, topLeft.y, width, height);

        if (image != null) {
            graphics.drawImage(image, topLeft.x + 97, topLeft.y + 57, null);
        }

        graphics.setColor(Color.WHITE);
        graphics.setFont (new Font ("TimesRoman", Font.BOLD, 35));
        graphics.drawString(this.name, topLeft.x + textStart.x, topLeft.y + textStart.y);
    }

    private static final int BORDER_THICKNESS = 4;
    public void drawSelected(Graphics graphics) {
        graphics.setColor(selectedColor);

        for (int i =0; i<BORDER_THICKNESS; i++) {
            graphics.drawRect(topLeft.x+i, topLeft.y+i, width-1-(i*2), height-1-(i*2));
        }
    }

    public void drawBlack(Graphics graphics, BufferedImage image) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(topLeft.x, topLeft.y, width, height);
    }

    public boolean inButton(Point point) {
        return point.x >= topLeft.x && point.x < topLeft.x + width
                && point.y >= topLeft.y && point.y < topLeft.y + height;
    }

    public boolean isClicked(LinkedList<Observation> observations) {
        LocalTime twoSecAgo = LocalTime.now().minusSeconds(2);

        boolean tooOld = true;
        boolean start = false;
        boolean aboveButtonBeforeClick = false;
        boolean insideButtonDown = false;
        boolean belowButton = false;
        boolean insideButtonUp = false;

        if(lastClicked != null && LocalTime.now().isBefore(lastClicked.plusSeconds(2))){
            return false;
        }

        for (int i = 0; i < observations.size(); i++) {
            Observation currentObservation = observations.get(i);
            if (currentObservation.getTime().isBefore(twoSecAgo)) {
                continue;
            }
            if (tooOld) {
                tooOld = false;
                start = true;
            }
            if (start && currentObservation.getPoint().y < topLeft.y) {
                start = false;
                aboveButtonBeforeClick = true;
            }
            if (aboveButtonBeforeClick) {
                if (inButton(currentObservation.getPoint())) {
                    aboveButtonBeforeClick = false;
                    insideButtonDown = true;
                } else if (currentObservation.getPoint().y >= topLeft.y) {
                    start = true;
                    aboveButtonBeforeClick = false;
                }
                // else stay above button before click
            }
            int bottomY = topLeft.y + height;
            if (insideButtonDown) {
                if (currentObservation.getPoint().y > bottomY) {
                    insideButtonDown = false;
                    belowButton = true;
                }
                if (!inButton(currentObservation.getPoint())) {
                    start = true;
                    insideButtonDown = false;
                }
                // else stay
            }
            if (belowButton) {
                if (inButton(currentObservation.getPoint())) {
                    belowButton = false;
                    insideButtonUp = true;
                } else if (currentObservation.getPoint().y < bottomY && !inButton(currentObservation.getPoint())) {
                    start = true;
                    belowButton = false;
                }
                // else stay
            }
            if (insideButtonUp) {
                if (currentObservation.getPoint().y < topLeft.y) {
                    lastClicked = currentObservation.getTime();
                    return true; // yayayay!!!
                } else if (!inButton(currentObservation.getPoint())) {
                    start = true;
                    insideButtonUp = false; // aww :(
                }
                // else stay
            }
        }
        return false;
    }

}