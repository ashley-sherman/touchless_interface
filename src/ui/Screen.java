package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import gestures.Observation;

//Base class for all screens
public class Screen {
    private BufferedImage image;

    private Button selectedButton;
    private Button clickedButton;
    private ArrayList<Button> buttons;

    private LinkedList<Observation> observations;

    private final Graphics graphics;

    public Screen(BufferedImage image) {
        this.image = image;
        this.buttons = new ArrayList<>();
        this.observations = new LinkedList<>();
        this.graphics = image.getGraphics();
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    protected BufferedImage getDisplayImage() {
        return image;
    }

    protected LinkedList<Observation> getObservations() {
        return observations;
    }

    protected void setSelectedButton(Button button) {
        this.selectedButton = button;
    }

    protected Button findSelectedButton(Point point) {
        for (int i = buttons.size() - 1; i >= 0; i--) {
            Button button = buttons.get(i);
            if (button.inButton(point)) {
                return button;
            }
        }
        return null;
    }

    protected Button findClickedButton() {
        for (int i = buttons.size() - 1; i >= 0; i--) {
            Button button = buttons.get(i);
            if (button.isClicked(observations)) {
                return button;
            }
        }
        return null;
    }

    protected void clearDisplayImage() {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }


    //Draws buttons and observations on the screen
    public void draw() {
        //First draw all buttons and then draw observations on top of the buttons.
        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            if (button == clickedButton) {
                button.drawBlack(graphics, image);
            } else if (button == selectedButton) {
                button.drawSelected(graphics);
            } else {
                button.drawRegular(graphics);
            }
        }
        drawObservation();
    }

    public void drawObservation() {
        graphics.setColor(Color.BLACK);
        if (observations.size() > 50) {
            //Delete older observations from the screen
            //This will draw a shadow over the button if the user holds the pointer
            //inside the button for a long time, need to fix by checking if this observation is inside a button
            Observation toDelete = observations.get(observations.size() - 51);
            graphics.drawOval(toDelete.getPoint().x, toDelete.getPoint().y, 5, 5);
        }

        graphics.setColor(Color.RED);
        for (int k = observations.size() - 1; k > observations.size() - 11 && k >= 0; k--) {
            //Draw the last 10
            Observation toDisplay = observations.get(k);
            graphics.drawOval(toDisplay.getPoint().x, toDisplay.getPoint().y, 5, 5);
        }
    }

    //Record the latest observation of the pointer.
    //We only use up to 100 observations to detect a gestures, so older observations are dropped.
    public void recordObservation(Observation observation) {
        //Using a linked list to efficiently add last and remove first
        observations.addLast(observation);
        if (observations.size() > 100) {
            observations.removeFirst();
        }
    }



    protected Point mirrorAndScale(Point point, BufferedImage image, double hScale, double vScale) {
        Point scaledPoint = new Point( (int) (point.x * hScale), (int) (point.y * vScale));
        return new Point(image.getWidth() - scaledPoint.x, scaledPoint.y);
    }

}
