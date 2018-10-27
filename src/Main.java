/*
Program Name: Bresenham Algorithm
Program author: Joshua J. Croft
Written for COIS 4480 Computer Graphics assignment #1 question #2
Trent University Fall 2018

Description:    The program takes in input for an x and a y (double) representing an end-point for a line.
                Displays that line on a cartesian grid from the origin to the given end-point.
                The default range is [-10,10] in both axis.
*/
public class Main {
    public static void main(String args[]) {
        Simulation sim = new Simulation();
        sim.run();
    }
}
