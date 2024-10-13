import java.awt.*;

public class NotClickableButton extends Button{
    public NotClickableButton(String name, Point topLeft, int width, int height, Color regularColor) {
        super(name, topLeft, width, height, regularColor);
    }

    @Override
    public boolean inButton(Point point) {
        return false;
    }
}
