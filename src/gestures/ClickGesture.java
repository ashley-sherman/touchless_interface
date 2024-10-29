package gestures;

import ui.Button;

import java.time.LocalTime;
import java.util.LinkedList;

//Recognizes a click gesture as a checkbox motion through a button:
//Pointer must start above button, move through the button on the way down
//and then back up the through the button and end up above the button.
public class ClickGesture {
    public static boolean isClicked(LinkedList<Observation> observations, Button button) {
        LocalTime twoSecAgo = LocalTime.now().minusSeconds(2);

        boolean tooOld = true;
        boolean start = false;
        boolean aboveButtonBeforeClick = false;
        boolean insideButtonDown = false;
        boolean belowButton = false;
        boolean insideButtonUp = false;

        //If the button was clicked within the last two seconds, it cannot be clicked again
        if(button.lastClicked() != null && LocalTime.now().isBefore(button.lastClicked().plusSeconds(2))){
            return false;
        }

        //Finite State Machine:
        //0. Initial state: Observation within last two seconds
        //1. Start state. Can transition to 1, 2
        //2. Pointer is above button. Can transition to 1, 2, 3
        //3. Pointer is in button on the way down. Can transition to 1, 3, 4
        //4. Pointer is below button. Can transition to 1, 4, 5
        //5. Pointer is inside button on the way up. Can transition to 1, 5, 6.
        //6. Pointer is above button (Exit state). Click Detected!

        for (int i = 0; i < observations.size(); i++) {
            Observation currentObservation = observations.get(i);
            if (currentObservation.getTime().isBefore(twoSecAgo)) {
                continue;
            }
            if (tooOld) {
                tooOld = false;
                start = true;
            }
            if (start && currentObservation.getPoint().y < button.topLeft().y) {
                start = false;
                aboveButtonBeforeClick = true;
            }
            if (aboveButtonBeforeClick) {
                if (button.inButton(currentObservation.getPoint())) {
                    aboveButtonBeforeClick = false;
                    insideButtonDown = true;
                } else if (currentObservation.getPoint().y >= button.topLeft().y) {
                    start = true;
                    aboveButtonBeforeClick = false;
                }
                // else stay above button before click
            }
            int bottomY = button.topLeft().y + button.height();
            if (insideButtonDown) {
                if (currentObservation.getPoint().y > bottomY) {
                    insideButtonDown = false;
                    belowButton = true;
                }
                if (!button.inButton(currentObservation.getPoint())) {
                    start = true;
                    insideButtonDown = false;
                }
                // else stay
            }
            if (belowButton) {
                if (button.inButton(currentObservation.getPoint())) {
                    belowButton = false;
                    insideButtonUp = true;
                } else if (currentObservation.getPoint().y < bottomY && !button.inButton(currentObservation.getPoint())) {
                    start = true;
                    belowButton = false;
                }
                // else stay
            }
            if (insideButtonUp) {
                if (currentObservation.getPoint().y < button.topLeft().y) {
                    button.lastClicked(currentObservation.getTime() );
                    return true; // yayayay!!!
                } else if (!button.inButton(currentObservation.getPoint())) {
                    start = true;
                    insideButtonUp = false; // aww :(
                }
                // else stay
            }
        }
        return false;
    }

}
