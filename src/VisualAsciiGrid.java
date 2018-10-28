import javafx.geometry.Point3D;
import java.util.ArrayList;

/*
    Visual Ascii Grid creates a 2D cartesian grid of range X: [-MAX,+MAX] Y: [-MAX, +MAX]
*/
public class VisualAsciiGrid {

    final static int MAX = 10;
    private final char LINE_CHARACTER = '#';

    private int range;
    private char[][][] charArray;

    public VisualAsciiGrid() {
        this.range = (2 * this.MAX) + 1;
        charArray = new char[range][range][range];
        fillCharArrayWithSpace();
        addGridLinesToCharArray();
    }

    public void draw() {
        for (int y = 0; y < charArray.length; y++) {
            for (int x = 0; x < charArray[0].length; x++) {
                for (int z = 0; z < charArray[0][0].length; z++) {
                    // Print the first non-space character, or the last character otherwise.
                    if(charArray[x][y][z] != ' ' || z == charArray[0][0].length - 1) {
                        System.out.print(charArray[x][y][z]);
                        break;
                    }
                }
            }
            System.out.println();   // New Line.
        }
    }

    public void applyLine3D(Line3D line) {
        ArrayList<Point3D> points = line.getPoints();
        for (Point3D p : points) {
            applyPointToArray((int)p.getX(), (int)p.getY(), (int)p.getZ());
        }
    }

    // Adds a point to the character array with the given cartesian coordinates.
    private void applyPointToArray(int x, int y, int z) {
        int xIndex, yIndex, zIndex;
        xIndex = convertCartesianToIndex(x, y, z)[0];
        yIndex = convertCartesianToIndex(x, y, z)[1];
        zIndex = convertCartesianToIndex(x, y, z)[2];
        this.charArray[xIndex][yIndex][zIndex] = this.LINE_CHARACTER;
    }

    // Converts a cartesian coordinate to its respective charArrayIndex.
    // Returns int[] of size 3, with int[0] as the x index, int[1] as the y index, and int[2] as the z index.
    private int[] convertCartesianToIndex(int xPos, int yPos, int zPos) {
        int[] index = new int[3];
        index[0] = this.MAX + xPos;
        index[1] = this.MAX - yPos;
        index[2] = this.MAX + zPos;
        return index;
    }

    private void fillCharArrayWithSpace() {
        for (int i = 0; i < charArray.length; i++) {
            for (int j = 0; j < charArray[0].length; j++) {
                for (int k = 0; k < charArray[0][0].length; k++) {
                    charArray[i][j][k] = ' ';
                }
            }
        }
    }

    private void addGridLinesToCharArray() {
        for (int i = 0; i < this.range; i++) {
            charArray[i][this.MAX][this.MAX] = '-';
            charArray[this.MAX][i][this.MAX] = '|';
            // No need to add grid lines on the 'z' axis. Will never be seen.
        }
        charArray[this.MAX][this.MAX][this.MAX] = '+';
    }
}
