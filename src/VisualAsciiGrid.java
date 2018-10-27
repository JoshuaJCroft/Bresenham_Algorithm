/*
    Visual Ascii Grid creates a 2D cartesian grid of range X: [-MAX,+MAX] Y: [-MAX, +MAX]
*/
public class VisualAsciiGrid {

    final static int MAX = 10;
    private final char LINE_CHARACTER = '#';

    private int range;
    private char[][] charArray;

    public VisualAsciiGrid() {
        this.range = (2 * this.MAX) + 1;
        charArray = new char[range][range];
        fillCharArrayWithSpace();
        addGridLinesToCharArray();
    }

    public void draw() {
        for (int y = 0; y < charArray.length; y++) {
            for (int x = 0; x < charArray[0].length; x++) {
                System.out.print(charArray[x][y]);
            }
            System.out.println();   // New Line.
        }
    }

    // Adds a line from the origin to the specified point in the charArray.
    public void applyLineFromOrigin(double xEndPoint, double yEndPoint) {
        int xIndex, yIndex, offset;                             // To traverse the charArray.
        int quadrant;                                           // The quadrant that the end points fall within.
        double slope;                                           // The slope of the line.
        quadrant = getQuadrant(xEndPoint, yEndPoint);           // Find the quadrant that the endpoint is within.
        if(xEndPoint == 0) {                                    // Edge case: vertical line.
            if(quadrantIsTopRight(quadrant)) {                  // The line is moving in a positive direction on the y-axis.
                for (int y = 0; y <= yEndPoint; y++) {          // Draw the vertical line.
                    xIndex = convertCartesianToIndex(0, y)[0];
                    yIndex = convertCartesianToIndex(0, y)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                }
            } else {                                            // The line is moving in a negative direction on the y-axis.
                for (int y = 0; y >= yEndPoint; y--) {          // Draw the vertical line.
                    xIndex = convertCartesianToIndex(0, y)[0];
                    yIndex = convertCartesianToIndex(0, y)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                }
            }
            return;                                             // Return to conclude the edge case and the method.
        }
        slope = yEndPoint / xEndPoint;                          // Calculate slope from the origin.
        offset = 0;
        if(quadrantIsTopRight(quadrant)) {
            if(slope >= 1) {
                // Moving upwards, conditionally moving right (from the origin).
                for (int y = 0; y <= yEndPoint; y++) {
                    xIndex = convertCartesianToIndex(offset, y)[0];
                    yIndex = convertCartesianToIndex(offset, y)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getXCoordinate(y + 1, slope) > offset + 0.5) {
                        offset++;
                    }
                }
            } else {
                // Moving right, conditionally moving upwards (from the origin).
                for (int x = 0; x <= xEndPoint; x++) {
                    xIndex = convertCartesianToIndex(x, offset)[0];
                    yIndex = convertCartesianToIndex(x, offset)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getYCoordinate(x + 1, slope) > offset + 0.5) {
                        offset++;
                    }
                }
            }
        }
        if(quadrantIsTopLeft(quadrant)) {
            if(slope <= -1) {
                // Moving upwards, conditionally moving left (from the origin).
                for (int y = 0; y <= yEndPoint; y++) {
                    xIndex = convertCartesianToIndex(offset, y)[0];
                    yIndex = convertCartesianToIndex(offset, y)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getXCoordinate(y + 1, slope) < offset - 0.5) {
                        offset--;
                    }
                }
            } else {
                // Moving left, conditionally moving upwards (from the origin).
                for (int x = 0; x >= xEndPoint; x--) {
                    xIndex = convertCartesianToIndex(x, offset)[0];
                    yIndex = convertCartesianToIndex(x, offset)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getYCoordinate(x - 1, slope) > offset + 0.5) {
                        offset++;
                    }
                }
            }
        }
        if(quadrantIsBottomLeft(quadrant)) {
            if(slope >= 1) {
                // Moving downwards, conditionally moving left (from the origin).
                for (int y = 0; y >= yEndPoint; y--) {
                    xIndex = convertCartesianToIndex(offset, y)[0];
                    yIndex = convertCartesianToIndex(offset, y)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getXCoordinate(y - 1, slope) < offset - 0.5) {
                        offset--;
                    }
                }
            } else {
                // Moving left, conditionally moving downwards (from the origin).
                for (int x = 0; x >= xEndPoint; x--) {
                    xIndex = convertCartesianToIndex(x, offset)[0];
                    yIndex = convertCartesianToIndex(x, offset)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getYCoordinate(x - 1, slope) < offset - 0.5) {
                        offset--;
                    }
                }
            }
        }
        if(quadrantIsBottomRight(quadrant)) {
            if(slope <= -1) {
                // Moving downwards, conditionally moving right (from the origin).
                for (int y = 0; y >= yEndPoint; y--) {
                    xIndex = convertCartesianToIndex(offset, y)[0];
                    yIndex = convertCartesianToIndex(offset, y)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getXCoordinate(y - 1, slope) > offset + 0.5) {
                        offset++;
                    }
                }
            } else {
                // Moving right, conditionally moving downwards (from the origin).
                for (int x = 0; x <= xEndPoint; x++) {
                    xIndex = convertCartesianToIndex(x, offset)[0];
                    yIndex = convertCartesianToIndex(x, offset)[1];
                    this.charArray[xIndex][yIndex] = this.LINE_CHARACTER;
                    if(getYCoordinate(x + 1, slope) < offset - 0.5) {
                        offset--;
                    }
                }
            }
        }
    }

    // No 'b' value is necessary; all lines pass through the origin.
    private double getXCoordinate(double y, double slope) {
        return y / slope;
    }

    private double getYCoordinate(double x, double slope) {
        return slope * x;
    }

    // Converts a cartesian coordinate to its respective charArrayIndex.
    // Returns int[] of size 2, with int[0] as the x index, and int[1] as the y index.
    private int[] convertCartesianToIndex(int xPos, int yPos) {
        int[] index = new int[2];
        index[0] = this.MAX + xPos;
        index[1] = this.MAX - yPos;
        return index;
    }

    private boolean quadrantIsTopRight(int quadrantNumber) {
        if(quadrantNumber == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean quadrantIsTopLeft(int quadrantNumber) {
        if(quadrantNumber == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean quadrantIsBottomLeft(int quadrantNumber) {
        if(quadrantNumber == 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean quadrantIsBottomRight(int quadrantNumber) {
        if(quadrantNumber == 3) {
            return true;
        } else {
            return false;
        }
    }

    // Returns the quadrant number of a given line from the origin to the given end points.
    private int getQuadrant(double xEndPoint, double yEndPoint) {
        int quadrant = -1;
        boolean top, right;
        // this.MAX is equivalent to the origin.
        if(xEndPoint >= 0) {
            right = true;
        } else {
            right = false;
        }
        if(yEndPoint >= 0) {
            top = true;
        } else {
            top = false;
        }
        // Quadrants are numbered counter-clockwise 0-4 starting at the top-right quadrant.
        if(top && right) {
            quadrant = 0;
        }
        if(top && !right) {
            quadrant = 1;
        }
        if(!top && !right) {
            quadrant = 2;
        }
        if(!top && right) {
            quadrant = 3;
        }
        return quadrant;
    }

    private void fillCharArrayWithSpace() {
        for (int i = 0; i < charArray.length; i++) {
            for (int j = 0; j < charArray[0].length; j++) {
                charArray[i][j] = ' ';
            }
        }
    }

    private void addGridLinesToCharArray() {
        for (int i = 0; i < this.range; i++) {
            charArray[i][this.MAX] = '-';
            charArray[this.MAX][i] = '|';
        }
        charArray[this.MAX][this.MAX] = '+';
    }
}
