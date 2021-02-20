import java.io.IOException;

public class StartSearch {

    private Map targetMap;      // the map. has a reference of start and target locations
    private int numArrows;      // arrows cupid has fired so far
    
    private int inertia;        // how many times an arrow has travelled in the same direction
    private int direction;      // the direction of the arrow

    private final int NUMNEIGHBOURS = 4; // number of neighbours can have    
    public StartSearch(String filename) {

        try {
            this.targetMap = new Map(filename);
            this.numArrows = targetMap.quiverSize();
        } catch (InvalidMapException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method returns the best cell to continue the path from the current one.
     * 
     * @param cell
     * @return the best cell to continue
     */
    public MapCell nextCell(MapCell cell) {
        
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
            return null;
        }

        MapCell next;
        if (cell.isHorizontalPath()) {
            next = (scores[3] > scores[1]) ? cell.getNeighbour(3) : cell.getNeighbour(1);
        } else if (cell.isVerticalPath()) {
            next = (scores[0] > scores[2]) ? cell.getNeighbour(0) : cell.getNeighbour(2);
        } else {
            next = cell.getNeighbour(maxIndex);
        }

        return next;
    }

    /**
     * check if a cell is next to Cupid
     * @param cell the cell
     * @return true if the cell is next to cupid, false otherwise
     */
    private boolean isAdjacentToCupid(MapCell cell) {
        
        for (int i = 0; i < NUMNEIGHBOURS; i++) {
            
            MapCell neighour = cell.getNeighbour(i);
            if (neighour != null && neighour.getIdentifier() == 0) {
                return true;
            }

        }
        
        return false;
    }


    /**
     * args[0]: file name
     * args[1](optinal): the number of cells can travel
     * 
     * @param args
     */
    public static void main(String[] args) {

        int travelLimit = Integer.MAX_VALUE;    // the number of cell that arrow can travel
        int steps = 0;      // number of steps has performed
        int found = 0; // the number of targets found

        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        } else if (args.length == 2) {
            travelLimit = Integer.parseInt(args[1]);
        }

        StartSearch game = new StartSearch(args[0]);
        ArrayStack<MapCell> stack = new ArrayStack<MapCell>();


        MapCell initial =  game.targetMap.getStart();
        stack.push(initial);        //Inserting initial in stack 
        initial.markInStack();      // mark initial as visited.

        while (!stack.isEmpty() && found < game.numArrows && steps < travelLimit) {
            
            MapCell top = stack.peek();
            
            MapCell next = game.nextCell(top);
            if (next != null) {
                
                stack.push(next);
                next.markInStack();
                
                if (next.isTarget())
                    found++;
                
            } else {
                top.markOutStack();
                stack.pop();
            }

            steps++;
        }

        while (!stack.isEmpty()) {

            MapCell top = stack.peek();
            if (game.isAdjacentToCupid(top)) {
                top.markOutStack();
            }

            stack.pop();
        }


    }

}
