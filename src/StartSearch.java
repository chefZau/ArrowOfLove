import java.io.IOException;

public class StartSearch {

    private Map targetMap; // the map. has a reference of start and target locations
    private int numArrows; // arrows cupid has fired so far

    private int direction; // the direction of the arrow
    private int inertia; // how many times an arrow has travelled in the same direction

    private final int NUMNEIGHBOURS = 4; // number of neighbours can have

    public StartSearch(String filename) {

        try {
            this.targetMap = new Map(filename);
            this.numArrows = targetMap.quiverSize();
            this.direction = -1;
            this.inertia = 0;

        } catch (InvalidMapException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * return the best cell base on precedence
     * 
     * @param cell
     * @return
     */
    public int getBestDirection(MapCell cell) {

        int[] scores = new int[NUMNEIGHBOURS];
        for (int i = 0; i < NUMNEIGHBOURS; i++) {

            MapCell cur = cell.getNeighbour(i);

            if (cur == null) {
                scores[i] = -1;
            } else if (cur.isMarked() || cur.isBlackHole()) {
                scores[i] = 0;
            } else if (cur.isTarget()) {
                scores[i] = 5;
            } else if (cur.isCrossPath()) {
                scores[i] = 4;
            } else if (cur.isVerticalPath() || cur.isHorizontalPath()) {
                scores[i] = 3;
            }

        }

        int maxElement = scores[0], maxIndex = 0;
        for (int j = 0; j < scores.length; j++) {
            if (scores[j] > maxElement) {
                maxElement = scores[j];
                maxIndex = j;
            }
        }

        // everthing is unmarked or block
        if (maxElement == 0) {
            return -1;
        }

        int nextIndex;
        if (cell.isHorizontalPath()) {
            nextIndex = (scores[3] > scores[1]) ? 3 : 1;
        } else if (cell.isVerticalPath()) {
            nextIndex = (scores[0] > scores[2]) ? 0 : 2;
        } else {
            nextIndex = maxIndex;
        }

        return nextIndex;
    }

    /**
     * This method returns the best cell to continue the path from the current one.
     * 
     * xmap3 result: push0 push1 push2 push3 push8 push13 push18 push23 pop23 pop18
     * pop13 pop8 pop3 pop2 pop1 pop0
     * 
     * @param cell
     * @return the best cell to continue
     */
    public MapCell nextCell(MapCell cell) {

        // if direction has not been initialize, find the best direction
        // set direction
        if (direction == -1) {
            direction = getBestDirection(cell);
            return (direction != -1) ? cell.getNeighbour(direction) : null;
        }

        // if the same direction has cell at front
        MapCell next = cell.getNeighbour(direction);
        if (next != null) {
            inertia++;
            return next;
        }

        if (next == null && inertia < 3) { // nothing is in the same direction (next is null), are we able to turn?
            inertia = 0;
            direction = getBestDirection(cell);
            return (direction != -1) ? cell.getNeighbour(direction) : null;
        }

        return null;

    }

    /**
     * check if a cell is next to Cupid
     * 
     * @param cell the cell
     * @return true if the cell is next to cupid, false otherwise
     */
    private boolean isAdjacentToCupid(MapCell cell) {

        for (int i = 0; i < NUMNEIGHBOURS; i++) {

            MapCell neighour = cell.getNeighbour(i);
            if (neighour != null && neighour.isStart()) {
                return true;
            }

        }

        return false;
    }

    /**
     * args[0]: file name args[1](optinal): the number of cells can travel
     * 
     * @param args
     */
    public static void main(String[] args) {

        int maxPathLength = Integer.MAX_VALUE; // the number of cell that arrow can travel

        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        } else if (args.length == 2) {
            maxPathLength = Integer.parseInt(args[1]);
        }

        int steps = 0; // number of steps has performed
        int found = 0; // the number of targets found

        StartSearch game = new StartSearch(args[0]);
        ArrayStack<MapCell> stack = new ArrayStack<MapCell>();

        MapCell initial = game.targetMap.getStart();
        stack.push(initial); // Inserting initial in stack
        initial.markInStack(); // mark initial as visited.

        while (!stack.isEmpty() && found < game.numArrows && steps < maxPathLength) {

            MapCell top = stack.peek();

            System.out.println(ArrayStack.sequence);

            MapCell next = game.nextCell(top);
            if (next != null) {

                stack.push(next);
                next.markInStack();

                if (next.isTarget())
                    found++;

                if (args.length == 2) {
                    steps++;
                }

            } else {
                stack.pop();
                steps++;
            }

        }

        System.out.println("------ * ------");

        while (!stack.isEmpty()) {

            MapCell top = stack.peek();

            if (game.isAdjacentToCupid(top)) {
                top.markOutStack();
            }

            stack.pop();
            System.out.println(ArrayStack.sequence);
        }

    }

}
