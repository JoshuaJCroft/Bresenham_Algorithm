public class Simulation {

    private VisualAsciiGrid grid;
    private int xInitial, yInitial, zInitial, xEndPoint, yEndPoint, zEndPoint, maximum;
    private Line3D line;
    private Character lineChar;
    private Boolean answer;

    // Constructor
    public Simulation() {
        grid = new VisualAsciiGrid();
        this.maximum = VisualAsciiGrid.MAX;
    }

    // Method Name: run
    // Behaviour:
    // TODO: fix out of bounds exception in some way ... (if point is outside of VisualAsciiGrid's grid).
    public void run() {
        boolean running = true;
        while(running) {
            while(true) {
                System.out.println("Enter an initial-point for the line ...");
                this.xInitial = Input.LoopUntilInputIsInteger("X: ");
                this.yInitial = Input.LoopUntilInputIsInteger("Y: ");
                this.zInitial = Input.LoopUntilInputIsInteger("Z: ");
                System.out.println("Enter an end-point for the line ...");
                this.xEndPoint = Input.LoopUntilInputIsInteger("X: ");
                this.yEndPoint = Input.LoopUntilInputIsInteger("Y: ");
                this.zEndPoint = Input.LoopUntilInputIsInteger("Z: ");
                line = new Line3D(-1, -1, -1, xEndPoint, yEndPoint, zEndPoint);
                System.out.print("Enter a character for your line (Will use default character otherwise).\n > ");
                this.lineChar = Input.getCharacterInput();
                if(lineChar == null){
                    grid.applyLine3D(line);
                } else {
                    grid.applyLine3D(line, lineChar);
                }
                grid.draw();
                grid.printSolidGridPoints();
                System.out.println("Would you like to add another line?");
                answer = Input.LoopUntilYesOrNo(" > ");
                if(!answer) {break;}
            }
            grid.clear();
            System.out.println("Would you like to try again?");
            answer = Input.LoopUntilYesOrNo(" > ");
            running = (answer)? true : false;
        }
        System.exit(0);
    }
}
