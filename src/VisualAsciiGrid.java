import java.util.ArrayList;

/*
    Visual Ascii Grid creates a 2D cartesian grid of range X: [-MAX,+MAX] Y: [-MAX, +MAX]
*/
// TODO: fix bug with printing and grid lines
//      Known Bugs: printing on the + x-axis with both +/- z values will not print a line unless z == 0.
//                  printing on the - x-axis with both +/- z values will always print a line.
//                  when lines are behind the grid lines (i.e. not printing to the screen), they will not be output by printSolidGridPoints().
public class VisualAsciiGrid {

    final static int MAX = 10;  // Allotted space in either direction [+/-] from the origin on both the x and y axis'.
    private final char DEFAULT_SYMBOL = '#';
    private final char VERTICAL_GRID_SYMBOL = '|';
    private final char HORIZONTAL_GRID_SYMBOL = '-';
    private final char ORIGIN_GRID_SYMBOL = '+';
    private final char EMPTY_SPACE = ' ';

    private int range;          // The total Allotted space on both the x and y axis'.
    private char[][] charArray;
    private Integer[][] zValues;

    public VisualAsciiGrid() {
        this.range = (2 * this.MAX) + 1;
        this.charArray = new char[range][range];
        this.zValues = new Integer[range][range];
        clear();
    }

    // Clears the grid and the zValues.
    public void clear() {
        setAllZValuesToNull();
        fillCharArrayWithEmptySpace();
        addGridLinesToCharArray();
    }

    public void draw() {
        for (int y = 0; y < this.charArray.length; y++) {
            for (int x = 0; x < this.charArray[0].length; x++) {
                System.out.print(this.charArray[x][y]);
            }
            System.out.println();   // New Line.
        }
    }

    // Excludes grid lines.
    public void printSolidGridPoints() {
        int xCoord, yCoord;
        System.out.print("Solid points: ");
        for (int y = 0; y < this.charArray.length; y++) {
            for (int x = 0; x < this.charArray[0].length; x++) {
                if (!isGridLine(this.charArray[x][y]) && !isEmptySpace(this.charArray[x][y])) {
                    xCoord = convertIndexToCartesian(x, y)[0];
                    yCoord = convertIndexToCartesian(x, y)[1];
                    System.out.print("(" + xCoord + ", " + yCoord + ") ");
                }
            }
        }
        System.out.println();   // New Line.
    }

    // Applies a line in cartesian space to the charArray with the given symbol.
    public void applyLine3D(Line3D line, char symbol) {
        ArrayList<Point3D> points = line.getPoints();
        for (Point3D p : points) {
            applyPoint3D(p, symbol);
        }
    }

    // Applies a line in cartesian space to the charArray with the default symbol.
    public void applyLine3D(Line3D line) {
        applyLine3D(line, this.DEFAULT_SYMBOL);
    }

    // Adds a point to the character array with the given cartesian coordinates with the given symbol.
    public void applyPoint3D(Point3D point, char symbol) {
        symbol = (isGridLine(symbol))? this.DEFAULT_SYMBOL : symbol;    // Do not allow any point to have a symbol used for grid lines.
        int[] index = convertCartesianPoint3DToIndex(point);
        int x = index[0], y = index[1], z = index[2];
        if (this.zValues[x][y] == null || this.zValues[x][y] >= z) {
            this.zValues[x][y] = z;
            this.charArray[x][y] = symbol;
        }
    }

    // Adds a point to the character array with the given cartesian coordinates with the default symbol.
    public void applyPoint3D(Point3D point) {
        applyPoint3D(point, this.DEFAULT_SYMBOL);
    }

    // Converts a cartesian coordinate to its respective charArrayIndex.
    // Returns int[] of size 3, with int[0] as the x index, int[1] as the y index, and int[2] as the z index.
    private int[] convertCartesianPoint3DToIndex(Point3D point) {
        int[] index = new int[3];
        index[0] = this.MAX + point.getX();
        index[1] = this.MAX - point.getY();
        index[2] = this.MAX + point.getZ();
        return index;
    }

    // Converts x/y index of the charArray to its respective cartesian coordinate.
    // Returns int[] of size 2, with int[0] as the x coordinate and int[1] as the y coordinate.
    private int[] convertIndexToCartesian(int xIndex, int yIndex) {
        int[] coordinate = new int[2];
        coordinate[0] = xIndex - this.MAX;
        coordinate[1] = (yIndex * -1) + this.MAX;
        return coordinate;
    }

    private void fillCharArrayWithEmptySpace() {
        for (int i = 0; i < this.charArray.length; i++) {
            for (int j = 0; j < this.charArray[0].length; j++) {
                this.charArray[i][j] = this.EMPTY_SPACE;
            }
        }
    }

    private void setAllZValuesToNull() {
        for (int i = 0; i < this.zValues.length; i++) {
            for (int j = 0; j < this.zValues[0].length; j++) {
                this.zValues[i][j] = null;
            }
        }
    }

    private void addGridLinesToCharArray() {
        for (int i = 0; i < this.range; i++) {
            this.charArray[i][this.MAX] = this.HORIZONTAL_GRID_SYMBOL;
            this.zValues[i][this.MAX] = this.MAX;
            this.charArray[this.MAX][i] = this.VERTICAL_GRID_SYMBOL;
            this.zValues[this.MAX][i] = this.MAX;
        }
        this.charArray[this.MAX][this.MAX] = this.ORIGIN_GRID_SYMBOL;
        this.zValues[this.MAX][this.MAX] = this.MAX;
    }

    private boolean isGridLine(char symbol) {
        return (symbol == this.HORIZONTAL_GRID_SYMBOL || symbol == this.VERTICAL_GRID_SYMBOL || symbol == this.ORIGIN_GRID_SYMBOL)? true : false;
    }

    private boolean isEmptySpace(char symbol) {
        return (symbol == this.EMPTY_SPACE)? true : false;
    }
}
