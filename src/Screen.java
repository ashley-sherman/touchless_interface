import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;

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

    public void onPointerObserved(Point point) {
        recordObservation(new Observation(point));
        selectedButton = findSelectedButton(point);
        //if (clickedButton == null) {
        clickedButton = findClickedButton();
        //}
        if (isSwipedRight(observations)){
            JOptionPane.showMessageDialog(null, "Swiped Right", "Swipe", JOptionPane.INFORMATION_MESSAGE);
        }
        if (isSwipedLeft(observations)) {
            JOptionPane.showMessageDialog(null, "Swiped Left", "Swipe", JOptionPane.INFORMATION_MESSAGE);
        }
        draw();

    }

    public void draw() {
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

    private boolean isInBounds(int x, int y) {
        //return true;
        return x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight();
    }

    public void drawObservation() {
        graphics.setColor(Color.BLACK);
        if (observations.size() > 50) {
            Observation toDelete = observations.get(observations.size() - 51);
            graphics.drawOval(toDelete.getPoint().x, toDelete.getPoint().y, 5, 5);
//            for (int j = -1; j < 2; j++) {
//                for (int i = -1; i < 2; i++) {
//                    if (isInBounds(toDelete.getPoint().x + i, toDelete.getPoint().y + j)) {
//                        image.setRGB(toDelete.getPoint().x + i, toDelete.getPoint().y + j, Color.BLACK.getRGB());
//                    }
//
//                }
//            }
        }

        graphics.setColor(Color.RED);
        for (int k = observations.size() - 1; k > observations.size() - 11 && k >= 0; k--) {
            Observation toDisplay = observations.get(k);
            graphics.drawOval(toDisplay.getPoint().x, toDisplay.getPoint().y, 5, 5);

//            for (int j = -1; j < 2; j++) {
//                for (int i = -1; i < 2; i++) {
//                    if (isInBounds(toDisplay.getPoint().x + i, toDisplay.getPoint().y + j)) {
//                        image.setRGB(toDisplay.getPoint().x + i, toDisplay.getPoint().y + j, Color.RED.getRGB());
//                    }
//
//                }
//            }

        }
    }

    public void recordObservation(Observation observation) {
        observations.addLast(observation);
        if (observations.size() > 100) {
            observations.removeFirst();
        }
    }
    public boolean isSwipedRight(LinkedList <Observation> observations) {
        LocalTime threeSecAgo = LocalTime.now().minusSeconds(3);
        boolean tooOld = true;
        boolean start = false;
        boolean downRight = false;
        boolean upRight = false;
        Observation startObservation = null;
        Observation previousObservation = null;
        Observation currentObservation = null;
        Observation lowestObservation = null;

        for (int i = 0; i < observations.size(); i++){
            previousObservation = currentObservation;
            currentObservation = observations.get(i);

            if (tooOld && previousObservation != null && currentObservation.getTime().isAfter(threeSecAgo)){
                tooOld = false;
                start = true;
                startObservation = currentObservation;
                System.out.println("At start!!!");
            } else if (start && isMovingRight(previousObservation, currentObservation)
                    && isMovingDown(previousObservation, currentObservation)
            ){
                start = false;
                downRight = true;
                System.out.println("At Down Right!!!");
            } else if (downRight){
                if (isMovingRight(previousObservation, currentObservation)
                        && isMovingDown(previousObservation, currentObservation)){
//                    System.out.println("Staying at Down Right!!!");
                    // do nothing - stay in downRight
                } else if (isMovingRight(previousObservation, currentObservation)
                        && isMovingUp(previousObservation, currentObservation)
                        && movedRightEnough(startObservation, currentObservation)
                        && movedDownEnough(startObservation, previousObservation)
                ){
                    System.out.println("At Up Right!!!");
                    downRight = false;
                    upRight = true;
                    lowestObservation = previousObservation;
                } else{
                    System.out.println("Back to start");
                    downRight = false;
                    start = true;
                }
            } else if (upRight){
                if (movedRightEnough(lowestObservation, currentObservation)
                        && movedUpEnough(lowestObservation, currentObservation)
                ) {
                    return true;
                } else if (isMovingRight(previousObservation, currentObservation)
                        && isMovingUp(previousObservation, currentObservation)) {
//                    System.out.println("Staying at up right!");
                    // do nothing - stay in upRight
                } else {
                    System.out.println("Back to start from up right");
                    upRight = false;
                    start = true;
                }
            }
        }
        return false;
    }
    public boolean isSwipedLeft(LinkedList <Observation> observations) {
        LocalTime threeSecAgo = LocalTime.now().minusSeconds(3);
//        System.out.println("******************************************");
//        for (int i=0; i<observations.size(); i++) {
//            System.out.println("Observation[" + i + "]=" + observations.get(i));
//        }
//        System.out.println("******************************************");

        boolean tooOld = true;
        boolean start = false;
        boolean downLeft = false;
        boolean upLeft = false;

        Observation startObservation = null;
        Observation previousObservation = null;
        Observation currentObservation = null;
        Observation lowestObservation = null;
//        System.out.println("******************************");
//        System.out.println("Num observations: " + observations.size());
//        System.out.println("******************************");
        for (int i = 0; i < observations.size(); i++){
            previousObservation = currentObservation;
            currentObservation = observations.get(i);

//            System.out.println("Current: " + currentObservation);
//            System.out.println("Previous: " + previousObservation);
//            System.out.println("Start: " + startObservation);

            if (tooOld && previousObservation != null && currentObservation.getTime().isAfter(threeSecAgo)){
                tooOld = false;
                start = true;
                startObservation = currentObservation;
                System.out.println("At start!!!");
            } else if (start && isMovingLeft(previousObservation, currentObservation)
                    && isMovingDown(previousObservation, currentObservation)
            ){
                start = false;
                downLeft = true;
                System.out.println("At Down Left!!!");
            } else if (downLeft){
                if (isMovingLeft(previousObservation, currentObservation)
                        && isMovingDown(previousObservation, currentObservation)){
//                    System.out.println("Staying at Down Left!!!");
                    // do nothing - stay in downLeft
                } else if (isMovingLeft(previousObservation, currentObservation)
                        && isMovingUp(previousObservation, currentObservation)
                        && movedLeftEnough(startObservation, currentObservation)
                        && movedDownEnough(startObservation, previousObservation)
                ){
                    System.out.println("At Up Left!!!");
                    downLeft = false;
                    upLeft = true;
                    lowestObservation = previousObservation;
                } else{
                    System.out.println("Back to start");
                    downLeft = false;
                    start = true;
                }
            } else if (upLeft){
                if (movedLeftEnough(lowestObservation, currentObservation)
                        && movedUpEnough(lowestObservation, currentObservation)
                ) {
                    return true;
                } else if (isMovingLeft(previousObservation, currentObservation)
                        && isMovingUp(previousObservation, currentObservation)) {
//                    System.out.println("Staying at up left!");
                    // do nothing - stay in upLeft
                } else {
                    System.out.println("Back to start from up left");
                    upLeft = false;
                    start = true;
                }
            }

        }

        return false;
    }

    private boolean isMovingUp(Observation a, Observation b){
        return b.getPoint().y < a.getPoint().y;
    }

    private boolean isMovingDown(Observation a, Observation b){
        return b.getPoint().y > a.getPoint().y;
    }

    private boolean isMovingRight(Observation a, Observation b){
        return b.getPoint().x > a.getPoint().x;
    }

    private boolean isMovingLeft(Observation a, Observation b){ return b.getPoint().x < a.getPoint().x;}

    boolean movedRightEnough(Observation a, Observation b){
        return b.getPoint().x - a.getPoint().x > 150;
    }

    boolean movedLeftEnough(Observation a, Observation b) {
        return a.getPoint().x - b.getPoint().x > 150;
        //return b.getPoint().x < (a.getPoint().x - 150);
    }

    boolean movedDownEnough(Observation a, Observation b){
        return b.getPoint().y > a.getPoint().y + 300;
    }

    boolean movedUpEnough(Observation a, Observation b){
        //return b.getPoint().y > a.getPoint().y + 300;
        return b.getPoint().y < a.getPoint().y - 300;
    }

    protected Point mirrorAndScale(Point point, BufferedImage image, double hScale, double vScale) {
        Point scaledPoint = new Point( (int) (point.x * hScale), (int) (point.y * vScale));
        return new Point(image.getWidth() - scaledPoint.x, scaledPoint.y);
    }

}
