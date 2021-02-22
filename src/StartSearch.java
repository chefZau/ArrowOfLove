import java.io.IOException;

/**
 * StartSearch
 */
public class StartSearch {

    private Map targetMap;  
    private int numArrows;      // how many arrow has fired so far, how many target has found
    private int inertia;        // how many times an arrow has travelled in the same direction
    private int direction;      // tracking the direction of the arrow
    
    private final int NUMNEIGHBOURS = 4; // number of neighbours can have

    public StartSearch(String filename) {
        try {
			targetMap = new Map(filename);
            numArrows = targetMap.quiverSize();
            inertia = 0;
            direction = -1;
		} catch (InvalidMapException | IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * The helper function of nextCell(). This function will return the best index
     * to continue the path from the current cell. 
     * 
     * valid path: 
     *      target, cupid, cross
     *          path cell: 0, 2 if 0,2 are vertical 
     *          1, 3 if 1,3 are horizontal 
     *      vertical cell: 0 and 2
     *      horizontal cell: 3 and 1
     * 
     * precedence: target > cross > vertical, horizontal > block
     * 
     * @param cell
     * @return
     */
    private int getBestDirection(MapCell cell) {
        
        // assign score to each neighbour
        int[] scores = new int[NUMNEIGHBOURS];
        for (int i = 0; i < NUMNEIGHBOURS; i++) {

            MapCell cur = cell.getNeighbour(i);
            if (cur == null || cur.isMarked() || cur.isBlackHole() || (i % 2 == 0 && cur.isHorizontalPath()) || (i % 2 != 0 && cur.isVerticalPath()) || (cell.isVerticalPath() && cur.isHorizontalPath()) || (cell.isHorizontalPath() && cur.isVerticalPath())) {
                scores[i] = -1;
            } else if (cur.isTarget()) {
                scores[i] = 100;
            } else if (cur.isCrossPath()) {
                scores[i] = 75;
            } else if (cur.isVerticalPath() || cur.isHorizontalPath()) {
                scores[i] = 50;
            }
        }
        
        if (cell.isVerticalPath()) {
            scores[1] = -1;
            scores[3] = -1;
        } else if (cell.isHorizontalPath()) {
            scores[0] = -1;
            scores[2] = -1;
        }

        // find the max element and its index
        int maxElement = scores[0], maxIndex = 0;
        for (int j = 0; j < scores.length; j++) {
            if (scores[j] > maxElement) {
                maxElement = scores[j];
                maxIndex = j;
            }
        }

        // all neighbour are either null, wall or marked, or invalid
        if (maxElement == -1 ) {
            return -1;
        }

        return maxIndex;

    }

    /**
     * Return the next best cell to continue the path from the current cell.
     * 
     * Limitation: 
     * 1. the arrow will stay in the same direction it is heading 
     * 2. if the arrow has traveled the same direction for three cell it can no longer move to other direction 
     * 3. if there is a deadend it can only back track 3 times arrow should stop when the target is hit, and pop everything from the
     * 4. stack the distant the arrow can travel is configurable.
     * 
     * @param cell the current location of the arrow
     * @return
     */
    public MapCell nextCell(MapCell cell) {

        if (direction == -1) {
            direction = getBestDirection(cell);
            if (direction == -1) return null;
            return cell.getNeighbour(direction);
        }

        MapCell next = cell.getNeighbour(direction);
        if (next != null && !next.isMarked()) {
            inertia++;
            return next;
        } else if (inertia < 3) {
            direction = getBestDirection(cell);
            if (direction == -1) return null;
            inertia = 0;
            return cell.getNeighbour(direction);
        }

        // inertia > 3, next is null or marked
        return null;

    }

    /**
     * Check if the arrow is next to Cupid
     * 
     * @param cell the current location of the arrow
     * @return True if the arrow is next to the Cupid
     */
    private boolean isAdjacentToCupid(MapCell cell) {
        for (int i = 0; i < NUMNEIGHBOURS; i++) {
            MapCell neighbour = cell.getNeighbour(i);
            if (neighbour != null && neighbour.isStart()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Use Depth First Search for finding a target in the map.
     * 
     * @param stack the stack of Cupid, it will use to keep track of the arrow location
     * @param maxSteps the maximum steps the arrow can travel
     * @return True if target is found, False otherwise
     */
    private boolean findTarget(ArrayStack<MapCell> stack, int maxSteps) {
        
        MapCell initial = this.targetMap.getStart();
        stack.push(initial);
        initial.markInStack();

        boolean found = false;

        while (!stack.isEmpty() && maxSteps > 0 && !found) {
            
            MapCell top = stack.peek();
            MapCell next = this.nextCell(top);

            if (next != null) {
                stack.push(next);
                next.markInStack();

                if (next.isTarget()) {
                    this.numArrows--;
                    found = true;
                }

            } else {
                if (this.isAdjacentToCupid(top)) { // if top is adjacent to cupid mark out stack
                    top.markOutStack();
                }
                stack.pop();
            }
            maxSteps--;
            System.out.println(ArrayStack.sequence);
        }

        while (!stack.isEmpty()) {

            MapCell top = stack.peek();

            if (this.isAdjacentToCupid(top)) {
                top.markOutStack();
            }

            stack.pop();
            System.out.println(ArrayStack.sequence);
        }

        this.direction = -1;
        this.inertia = 0;

        return found;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        int maxPathLength = Integer.MAX_VALUE;  // the number of cell that arrow can travel

        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        } else if (args.length == 2) {
            maxPathLength = Integer.parseInt(args[1]); 
        }

        String mapFileName = args[0];  
        StartSearch Cupid = new StartSearch(mapFileName);

        ArrayStack<MapCell> stack = new ArrayStack<MapCell>();

        Cupid.targetMap.getStart().markInitial();

        int totalFoud = 0;
        while (Cupid.numArrows > 0) {

            Boolean found = Cupid.findTarget(stack, maxPathLength);    // shoot once
            
            totalFoud += (found) ? 1 : 0;
            
            if (found) {
                totalFoud++;
            } else {
                Cupid.numArrows--;
            }

        }
        
        // output the number of targets found
        System.out.println(totalFoud);
        System.out.println(ArrayStack.sequence);
    }

    
}