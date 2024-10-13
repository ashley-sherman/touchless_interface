import java.awt.*;
import java.awt.image.BufferedImage;

public class FindPointer {

    //public static final Color COLOR = Color.GREEN;
    public static final int EXCESS_COLOR = 20;
    public static ColorSelectionMode colorSelectionMode = ColorSelectionMode.GREEN;
    public Point findPointerLocation(BufferedImage image) {
        int hGRun = 0;
        int leftTotal = 0;
        int leftCount = 0;
        int rightTotal = 0;
        int rightCount = 0;
        final int WINDOWSIZE = 10;



        for (int j = 0; j < image.getHeight(); j++) {
            int windowGreenTotal = 0;
            int windowRedTotal = 0;
            int windowBlueTotal = 0;
            boolean inGreen = false;
            for (int i = 0; i < image.getWidth(); i++) {
                int rgb = image.getRGB(i, j);
                Color color = new Color(rgb);
                windowGreenTotal += color.getGreen();
                windowBlueTotal += color.getBlue();
                windowRedTotal += color.getRed();
                if (i >= WINDOWSIZE) { //we are past the first full window, so we slide the left side of the window
                    Color startColor = new Color(image.getRGB(i-WINDOWSIZE,j));
                    windowGreenTotal -= startColor.getGreen();
                    windowBlueTotal -= startColor.getBlue();
                    windowRedTotal -= startColor.getRed();
                }
                if (i >= WINDOWSIZE - 1){ //we have accumulated a full window
                    if (isCorrectColor(windowGreenTotal/WINDOWSIZE, windowBlueTotal/WINDOWSIZE, windowRedTotal/WINDOWSIZE)) {
                        if (!inGreen) {
                            leftTotal += (i - WINDOWSIZE + 1);
                            leftCount ++;
                            inGreen = true;
                            //System.out.println("Found left border at i=" + (i-WINDOWSIZE+1) + " and j=" + j);
                        }
                    } else if (!isCorrectColor(windowGreenTotal/WINDOWSIZE, windowBlueTotal/WINDOWSIZE, windowRedTotal/WINDOWSIZE) && inGreen) {
                        int rightBorder = (i - WINDOWSIZE/2 + 1); //assume right border is middle of this window (just a guess).
                        rightTotal += rightBorder;
                        rightCount++;
                        inGreen = false;
                        //System.out.println("Found right border at i=" + rightBorder + " and j=" + j);
                    } else if (i == image.getWidth() - 1 && inGreen) {
                        rightTotal += i;
                        rightCount++;
                        //System.out.println("Found right border i = " + i + " and j = " + j);
                    }
                }
            }
        }

        int vGRun = 0;
        int topTotal = 0;
        int topCount = 0;

        for (int i = 0; i < image.getWidth(); i++) {
            int verGreenWindowTotal = 0;
            int verBlueWindowTotal = 0;
            int verRedWindowTotal = 0;
            boolean inVerGreen = false;
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                Color color = new Color(rgb);
                verGreenWindowTotal += color.getGreen();
                verBlueWindowTotal += color.getBlue();
                verRedWindowTotal += color.getRed();
                if (j >= WINDOWSIZE) {
                    Color startColor = new Color(image.getRGB(i, j - WINDOWSIZE));
                    verGreenWindowTotal -= startColor.getGreen();
                    verBlueWindowTotal -= startColor.getBlue();
                    verRedWindowTotal -= startColor.getRed();
                }
                if (j >= WINDOWSIZE - 1) {
                    if (isCorrectColor(verGreenWindowTotal/WINDOWSIZE, verBlueWindowTotal/WINDOWSIZE, verRedWindowTotal/WINDOWSIZE)) {
                        if (!inVerGreen) {
                            topTotal += (j - WINDOWSIZE + 1);
                            topCount++;
                            inVerGreen = true;
                            //System.out.println("Found top border at i=" + i + " and j=" + (j - WINDOWSIZE + 1));
                        }
                    }
                }
            }
        }

        //System.out.println("Left total: " + leftTotal + ", leftCount: " + leftCount + ", rightTotal: " + rightTotal + ", rightCOunt: " + rightCount);
        //System.out.println("Top total: " + topTotal + ", top count: " + topCount);

        if (leftCount == 0 || topCount == 0 || rightCount == 0){
            return null;
        }
        int leftAvg = leftTotal / leftCount;
        int rightAvg = rightTotal / rightCount;
        int horAvg = (leftAvg + rightAvg) / 2;
        int topAvg = topTotal / topCount;
        //System.out.println("left average: " + leftAvg + ", right average: " + rightAvg + ", horizontal average: " + horAvg);
        //System.out.println("Pointer at " + horAvg + ", " + topAvg);
        return new Point(horAvg, topAvg);
    }
    public boolean isCorrectColor(int green, int blue, int red){
        return colorSelectionMode.isCorrectColor(green, blue, red);
    }

    static enum ColorSelectionMode {
        GREEN {
            boolean isCorrectColor(int green, int blue, int red) {
                return (green >= blue + EXCESS_COLOR) && (green >= red + EXCESS_COLOR);
            }
        },
        RED {
            boolean isCorrectColor(int green, int blue, int red) {
                return (red >= blue + 100) && (red >= green + 100);
            }
        },
        BLUE {
            boolean isCorrectColor(int green, int blue, int red) {
                return (blue >= red + 100) && (blue >= green + 100);
            }
        },
        BLACK {
            boolean isCorrectColor(int green, int blue, int red) {
                return (blue <= 30) && (green <= 30) && (red <= 30);
            }
        },

        WHITE {
            boolean isCorrectColor(int green, int blue, int red) {
                return (blue >= 200) && (green >= 200) && (red >= 200);
            }
        };
        abstract boolean isCorrectColor(int green, int blue, int red);
    }

}
