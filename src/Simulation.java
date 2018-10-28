import java.util.Objects;
import java.util.Scanner;

public class Simulation {

    private double maximum;
    private VisualAsciiGrid grid;

    // Constructor
    public Simulation() {
        this.maximum = VisualAsciiGrid.MAX;
    }

    // Method Name: run
    // Behaviour:
    public void run() {
        Integer input;
        Boolean answer;
        int xEndPoint, yEndPoint, zEndPoint;
        Line3D line;
        while(true) {
            System.out.println("Please enter an end-point for a line from the origin ...");
            input = null;
            while(input == null) {
                System.out.print("X: ");
                input = getIntegerInput();
                if(input != null && input > this.maximum) {
                    input = null;
                }
            }
            xEndPoint = input;
            input = null;
            while(input == null) {
                System.out.print("Y: ");
                input = getIntegerInput();
                if(input != null && input > this.maximum) {
                    input = null;
                }
            }
            yEndPoint = input;
            input = null;
            while(input == null) {
                System.out.print("Z: ");
                input = getIntegerInput();
                if(input != null && input > this.maximum) {
                    input = null;
                }
            }
            zEndPoint = input;
            grid = new VisualAsciiGrid();
            line = new Line3D(xEndPoint, yEndPoint, zEndPoint);
            grid.applyLine3D(line);
            grid.draw();
            System.out.println("Would you like to try again?");
            answer = null;
            while(answer == null) {
                answer = getYesOrNo();
                if (answer != null && !answer) {
                    System.exit(0);
                }
            }
        }
    }

    // Behaviour:   Returns true if user input was yes. Returns false if user input was no. Returns null otherwise.
    private Boolean getYesOrNo() {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        if (Objects.equals(input.toUpperCase(), "YES")) {
            return true;
        } else if(Objects.equals(input.toUpperCase(), "NO")) {
            return false;
        } else {
            return null;
        }
    }

    // Behaviour:   Returns Integer instance representation of user input. Returns null if user input is invalid.
    private Integer getIntegerInput() {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        try{
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }
}
