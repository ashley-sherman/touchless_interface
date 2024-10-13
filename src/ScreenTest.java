import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;

public class ScreenTest {
    public static void main(String[] args) {
        testMovedLeftEnough();
        testMovedRightEnough();
        testMovedUpEnough();
        testMovedDownEnough();
        testIsSwipeLeft();
    }


    static void testIsSwipeLeft() {
        LinkedList<Observation> observations = new LinkedList<>();
        LocalTime start = LocalTime.now().minus(Duration.ofSeconds(1));
        LocalTime current = start;

        observations.add(new Observation(new Point(1616, 0), current));
        current = current.plus(Duration.ofMillis(10));
        observations.add(new Observation(new Point(1568, 237), current));
        current = current.plus(Duration.ofMillis(10));
        observations.add(new Observation(new Point(1176, 867), current));
        current = current.plus(Duration.ofMillis(10));
        observations.add(new Observation(new Point(1076, 855), current));
        current = current.plus(Duration.ofMillis(10));
        observations.add(new Observation(new Point(872, 750), current));
        current = current.plus(Duration.ofMillis(10));
        observations.add(new Observation(new Point(572, 399), current));

        Screen screen = new Screen(null);
        assertThat(screen.isSwipedLeft(observations));
    }

    static void testMovedUpEnough() {
        Screen screen = new Screen(null);
        Observation a = new Observation(new Point(400, 400));
        Observation b = new Observation(new Point(400, 0));

        assertThat(screen.movedUpEnough(a, b));

        b = new Observation(new Point(400, 300));
        assertThat(!screen.movedUpEnough(a, b));
    }

    static void testMovedDownEnough() {
        Screen screen = new Screen(null);
        Observation a = new Observation(new Point(400, 400));
        Observation b = new Observation(new Point(400, 800));

        assertThat(screen.movedDownEnough(a, b));

        b = new Observation(new Point(400, 450));
        assertThat(!screen.movedDownEnough(a, b));
    }

    static void testMovedLeftEnough() {
        Screen screen = new Screen(null);
        Observation a = new Observation(new Point(400, 200));
        Observation b = new Observation(new Point(100, 200));

        assertThat(screen.movedLeftEnough(a, b));

        b = new Observation(new Point(300, 200));
        assertThat(!screen.movedLeftEnough(a, b));
    }


    static void testMovedRightEnough() {
        Screen screen = new Screen(null);
        Observation a = new Observation(new Point(400, 200));
        Observation b = new Observation(new Point(700, 200));

        assertThat(screen.movedRightEnough(a, b));

        b = new Observation(new Point(450, 200));
        assertThat(!screen.movedRightEnough(a, b));
    }

    static void assertThat(boolean condition) {
        if (!condition) {
            throw new RuntimeException();
        }
    }
}
