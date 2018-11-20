package byog.Core;

import java.util.LinkedList;

/**
 * This class implements the Bresenham's line algorithm.
 *
 * Sources:
 *   @Source : https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
 *   @Source : https://www.cs.helsinki.fi/group/goa/mallinnus/lines/bresenh.html
 *
 *   The two above sources were used to implement this class. Specifically, the Wikipedia article
 *   provided information and pseudo-code for the algorithm. The second source also had
 *   pseudo-code, but used primarily as a source of information.
 */
public class BresenhamLine implements java.io.Serializable {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private LinkedList<Point> line;

    public BresenhamLine(Point a, Point b) {
        this.startX = a.getX();
        this.startY = a.getY();
        this.endX = b.getX();
        this.endY = b.getY();
        this.line = new LinkedList<>();
    }

    /**
     * Bresenham's line algorithm works for when the line is in octant zero. In this octant, lines
     * begin at the origin and increase with a slope between 0 and 1. Therefore, we must transform
     * coordinate so that they fit this constraint. This method is the first step of this
     * transformation process. Here, we check where startX < endX or startY < endY and then
     * swap the coordinates appropriately.
     *
     * @return : A linked list containing all points on the Bresenham line.
     */
    public LinkedList<Point> plotLine() {
        if (Math.abs(endY - startY) < Math.abs(endX - startX)) {
            if (startX > endX) {
                plotLineLow(endX, endY, startX, startY);
            } else {
                plotLineLow(startX, startY, endX, endY);
            }
        } else {
            if (startY > endY) {
                plotLineHigh(endX, endY, startX, startY);
            } else {
                plotLineHigh(startX, startY, endX, endY);
            }
        }
        return line;
    }

    /**
     * The previous method swapped coordinates depending on their relative magnitude. Inputs
     * sent to this method have gradients between 0 and -1. Thus, there is a check to see
     * whether y needs to be increased or decreased via dy.
     *
     * @param x1 : The beginning x-coordinate. May have been swapped.
     * @param y1 : The beginning y-coordinate. May have been swapped.
     * @param x2 : The ending x-coordinate. May have been swapped.
     * @param y2 : The ending y-coordinate. May have been swapped.
     */
    private void plotLineLow(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        int ySlope = 1;

        if (deltaY < 0) {
            ySlope = -1;
            deltaY = -deltaY;
        }

        int error = 2 * deltaY - deltaX;
        int y = y1;

        for (int x = x1; x <= x2; x += 1) {
            line.add(new Point(x, y));

            if (error > 0) {
                y += ySlope;
                error -= 2 * deltaX;
            }

            error += 2 * deltaY;
        }
    }

    /**
     * The previous method swapped coordinates depending on their relative magnitudes. Inputs
     * sent to this method have slopes greater than 1.
     *
     * @param x1 : The beginning x-coordinate. May have been swapped.
     * @param y1 : The beginning y-coordinate. May have been swapped.
     * @param x2 : The ending x-coordinate. May have been swapped.
     * @param y2 : The ending y-coordinate. May have been swapped.
     */
    private void plotLineHigh(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        int xSlope = 1;

        if (deltaX < 0) {
            xSlope = -1;
            deltaX = -deltaX;
        }

        int error = 2 * deltaX - deltaY;
        int x = x1;

        for (int y = y1; y <= y2; y += 1) {
            line.add(new Point(x, y));

            if (error > 0) {
                x += xSlope;
                error -= 2 * deltaY;
            }

            error += 2 * deltaX;
        }
    }
}
