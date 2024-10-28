package gestures;

import java.time.LocalTime;
import java.util.LinkedList;

public class SwipeGesture {
    public static boolean isSwipedRight(LinkedList<Observation> observations) {
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
            } else if (start && isMovingRight(previousObservation, currentObservation)
                    && isMovingDown(previousObservation, currentObservation)
            ){
                start = false;
                downRight = true;
            } else if (downRight){
                if (isMovingRight(previousObservation, currentObservation)
                        && isMovingDown(previousObservation, currentObservation)){
                } else if (isMovingRight(previousObservation, currentObservation)
                        && isMovingUp(previousObservation, currentObservation)
                        && movedRightEnough(startObservation, currentObservation)
                        && movedDownEnough(startObservation, previousObservation)
                ){
                    downRight = false;
                    upRight = true;
                    lowestObservation = previousObservation;
                } else{
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
                } else {
                    upRight = false;
                    start = true;
                }
            }
        }
        return false;
    }
    public static boolean isSwipedLeft(LinkedList<Observation> observations) {
        LocalTime threeSecAgo = LocalTime.now().minusSeconds(3);

        boolean tooOld = true;
        boolean start = false;
        boolean downLeft = false;
        boolean upLeft = false;

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
            } else if (start && isMovingLeft(previousObservation, currentObservation)
                    && isMovingDown(previousObservation, currentObservation)
            ){
                start = false;
                downLeft = true;
            } else if (downLeft){
                if (isMovingLeft(previousObservation, currentObservation)
                        && isMovingDown(previousObservation, currentObservation)){
                    // do nothing - stay in downLeft
                } else if (isMovingLeft(previousObservation, currentObservation)
                        && isMovingUp(previousObservation, currentObservation)
                        && movedLeftEnough(startObservation, currentObservation)
                        && movedDownEnough(startObservation, previousObservation)
                ){
                    downLeft = false;
                    upLeft = true;
                    lowestObservation = previousObservation;
                } else{
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
                    // do nothing - stay in upLeft
                } else {
                    upLeft = false;
                    start = true;
                }
            }

        }

        return false;
    }

    private static boolean isMovingUp(Observation a, Observation b){
        return b.getPoint().y < a.getPoint().y;
    }

    private static boolean isMovingDown(Observation a, Observation b){
        return b.getPoint().y > a.getPoint().y;
    }

    private static boolean isMovingRight(Observation a, Observation b){
        return b.getPoint().x > a.getPoint().x;
    }

    private static boolean isMovingLeft(Observation a, Observation b){ return b.getPoint().x < a.getPoint().x;}

    static boolean movedRightEnough(Observation a, Observation b){
        return b.getPoint().x - a.getPoint().x > 150;
    }

    static boolean movedLeftEnough(Observation a, Observation b) {
        return a.getPoint().x - b.getPoint().x > 150;
        //return b.getPoint().x < (a.getPoint().x - 150);
    }

    public static boolean movedDownEnough(Observation a, Observation b){
        return b.getPoint().y > a.getPoint().y + 300;
    }

    static public boolean movedUpEnough(Observation a, Observation b){
        //return b.getPoint().y > a.getPoint().y + 300;
        return b.getPoint().y < a.getPoint().y - 300;
    }

}
