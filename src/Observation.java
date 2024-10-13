import java.awt.*;
import java.time.LocalTime;

public class Observation {
    private Point point;
    private LocalTime time;

    public Observation(Point point){
        this.point = point;
        this.time = LocalTime.now();
    }

    public Observation(Point point, LocalTime time){
        this.point = point;
        this.time = time;
    }

    public Point getPoint(){
        return point;
    }

    public LocalTime getTime(){
        return time;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "point=" + point +
                ", time=" + time +
                '}';
    }
}
