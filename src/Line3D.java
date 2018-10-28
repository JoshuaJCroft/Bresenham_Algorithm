import javafx.geometry.Point3D;
import java.util.ArrayList;

public class Line3D {

    private ArrayList<Point3D> points;

    public Line3D(int xEndPoint, int yEndPoint, int zEndpoint) {
        points = new ArrayList<>();
        this.createLinePointsFromOrigin(xEndPoint, yEndPoint, zEndpoint);
    }

    public ArrayList<Point3D> getPoints() {
        return points;
    }

    private void createLinePointsFromOrigin(int xEndPoint, int yEndPoint, int zEndPoint) {
        boolean xIsMain, yIsMain, zIsMain;
        int dxAbs, dyAbs, dzAbs;                                // The absolute distance for each axis.
        int maxDelta;                                           // The largest distance for any axis.
        int secondaryDelta, tertiaryDelta;
        int xIncrement, yIncrement, zIncrement;                 // The value (1 or -1) which each axis will increment.
        int mainInc, secondaryInc, tertiaryInc;                 // For dynamic incrementing within for-loop.
        int xIndex, yIndex, zIndex;                             // The point indexers for the line.
        int mainIndex, secondaryIndex, tertiaryIndex;           // For dynamic indexing within for-loop.
        int error_1, error_2;
        dxAbs = Math.abs(xEndPoint);    dyAbs = Math.abs(yEndPoint);    dzAbs = Math.abs(zEndPoint);
        xIsMain = dxAbs >= dyAbs && dxAbs >= dzAbs;
        yIsMain = dyAbs >= dxAbs && dyAbs >= dzAbs;
        zIsMain = dzAbs >= dxAbs && dzAbs >= dyAbs;
        xIncrement = (xEndPoint >= 0)?  1 : -1;
        yIncrement = (yEndPoint >= 0)?  1 : -1;
        zIncrement = (zEndPoint >= 0)?  1 : -1;
        if(xIsMain) {
            maxDelta = dxAbs;
            mainInc = xIncrement;
            secondaryInc = yIncrement;
            tertiaryInc = zIncrement;
            secondaryDelta = dyAbs;
            tertiaryDelta = dzAbs;
        } else if (yIsMain) {
            maxDelta = dyAbs;
            mainInc = yIncrement;
            secondaryInc = xIncrement;
            tertiaryInc = zIncrement;
            secondaryDelta = dxAbs;
            tertiaryDelta = dzAbs;
        } else {// zIsMain
            maxDelta = dzAbs;
            mainInc = zIncrement;
            secondaryInc = xIncrement;
            tertiaryInc = yIncrement;
            secondaryDelta = dxAbs;
            tertiaryDelta = dyAbs;
        }

        mainIndex = 0; secondaryIndex = 0; tertiaryIndex = 0;
        error_1 = (secondaryDelta << 1) - maxDelta;
        error_2 = (tertiaryDelta << 1) - maxDelta;
        for (int i = 0; i < maxDelta; i++) {
            mainIndex += mainInc;
            if(error_1 >= 0) {
                secondaryIndex += secondaryInc;
                error_1 -= maxDelta << 1;
            }
            if (error_2 >= 0) {
                tertiaryIndex += tertiaryInc;
                error_2 -= maxDelta << 1;
            }
            error_1 += secondaryDelta << 1;
            error_2 += tertiaryDelta << 1;
            if(xIsMain) {
                xIndex = mainIndex;
                yIndex = secondaryIndex;
                zIndex = tertiaryIndex;
            } else if (yIsMain) {
                yIndex = mainIndex;
                xIndex = secondaryIndex;
                zIndex = tertiaryIndex;
            } else { // zIsMain
                zIndex = mainIndex;
                xIndex = secondaryIndex;
                yIndex = tertiaryIndex;
            }
            // Add point.
            this.points.add(new Point3D(xIndex, yIndex, zIndex));
        }
    }
}
