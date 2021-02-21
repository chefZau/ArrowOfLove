import java.io.IOException;

/**
 * StartSearch
 */
public class StartSearch {
          
    /*
              0
              |
        3 -   *   - 1
              |
              2

        valid path: 
            target, cupid, cross path cell:
                0, 2 if 0,2 are vertical
                1, 3 if 1,3 are horizontal
            vertical cell: 0 and 2
            horizontal cell: 3 and 1
        
        precedence:
            target > cross > vertical, horizontal > block

        Limitation:
            the arrow will stay in the same direction it is heading
            if the arrow has traveled the same direction for three cell it can no longer move to other direction
            if there is a deadend it can only back track 3 times
            arrow should stop when the target is hit, and pop everything from the stack
            the distant the arrow can travel is configurable.
    */

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

    private int getBestDirection(MapCell cell) {
        
        int[] scores = new int[NUMNEIGHBOURS];
        for (int i = 0; i < NUMNEIGHBOURS; i++) {

            MapCell cur = cell.getNeighbour(i);
            if (cur == null || cur.isMarked() || cur.isBlackHole()) {
                scores[i] = -1;
            } else if (cur.isTarget()) {
                scores[i] = 100;
            } else if (cur.isCrossPath()) {
                scores[i] = 75;
            } else if (cur.isVerticalPath() || cur.isHorizontalPath()) {
                scores[i] = 50;
            }

        }

        int maxElement = scores[0], maxIndex = 0;
        for (int j = 0; j < scores.length; j++) {
            if (scores[j] > maxElement) {
                maxElement = scores[j];
                maxIndex = j;
            }
        }

    }

    public MapCell nextCell(MapCell cell) {
        return cell;
        /*
            if direction is not initilize, means we just start
                find the best direction
                if no direction return null

                set direction
                return cell
            
            direction is initialized:

                is the cell at the direction not null and not marked?
                    inertia++
                    return cell at the direction

                cell at the direction is null or marked:
                    if inertia > 3:
                        return null since we can't turn anymore
                    
                    we can turn:
                        find the best direction

                        if no direction: return null

                        set direction
                        inertia = 0
                        return best cell
        */
    }

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
     * 
     * @param stack
     * @param maxSteps
     * @return
     */
    private boolean findTarget(ArrayStack<MapCell> stack, int maxSteps) {
        
        MapCell initial = this.targetMap.getStart();
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
        }

        while (!stack.isEmpty()) {

            MapCell top = stack.peek();

            if (this.isAdjacentToCupid(top)) {
                top.markOutStack();
            }

            stack.pop();
        }

        return found;
    }

    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        } 

        String mapFileName = args[0];
        int maxPathLength = Integer.parseInt(args[1]);      // the number of cells that the arrow can travel
                                                            // if maxPathLength is given, we should count how many targets can be found in a path within the length

        StartSearch Cupid = new StartSearch(mapFileName);
        ArrayStack<MapCell> stack = new ArrayStack<MapCell>();

        int totalFoud = 0;
        while (Cupid.numArrows > 0) {
            Boolean found = Cupid.findTarget(stack, maxPathLength);    // shoot once
            totalFoud += (found == true) ? 1 : 0;
        }

        // output the number of targets found
        System.out.println(totalFoud);
    }

    
}