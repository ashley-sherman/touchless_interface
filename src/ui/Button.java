package ui;

import gestures.ClickGesture;
import gestures.Observation;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    public Point topLeft() {
        return topLeft;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
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

    public LocalTime lastClicked() {
        return lastClicked;
    }

    public void lastClicked(LocalTime lastClicked) {
        this.lastClicked = lastClicked;
    }

    public boolean isClicked(LinkedList<Observation> observations) {
        return ClickGesture.isClicked(observations, this);
    }

}