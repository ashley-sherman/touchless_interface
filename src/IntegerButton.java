import java.awt.*;

public class IntegerButton extends NotClickableButton{

    private int number;
    public IntegerButton(String name, Point topLeft, int width, int height, Color regularColor, int number) {
        super(name, topLeft, width, height, regularColor);
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }

    public void add(){
        number++;
        setName(String.valueOf(number));
    }

    public void subtract(){
        if (number > 0){
            number--;
        }
        setName(String.valueOf(number));
    }

    public void addMoreThanOne(int amount){
        number += amount;
        setName(String.valueOf(number));
    }
    public void subtractLessThanOne(int amount){
        if (number > 0){
            number -= amount;
        }
        setName(String.valueOf(number));
    }

}
