import java.io.IOException;

public class StartSearch {

    private Map targetMap;      // the map. has a reference of start and target locations
    private int numArrows;      // arrows cupid has fired so far
    
    private int inertia;        // how many times an arrow has travelled in the same direction
    private int direction;      // the direction of the arrow

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
        
        int[] scores = new int[4];
        for (int i = 0; i < 4; i++) {
            
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
     * args[0]: file name
     * args[1](optinal): the number of cells can travel
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        int travelLimit;
        int pathLenCounter = 0;

        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        } else if (args.length == 2) {
            travelLimit = Integer.parseInt(args[1]);
        }

        // create an object of the class StartSearch using the constuctor StartSearch(args[0]).
        StartSearch pathFinding = new StartSearch(args[0]);

        int found = 0;

        ArrayStack<MapCell> stack = new ArrayStack<MapCell>();

        MapCell initial =  pathFinding.targetMap.getStart();

        stack.push(initial);
        initial.markInStack();
        System.out.println(stack.size());

        System.out.println("\n\n");
        System.out.println(initial.getNeighbour(1).getCellType());
        System.out.println(initial.getIdentifier());
        initial.getNeighbour(1).markInStack();

        stack.peek().markOutStack();
        MapCell tmp = stack.pop();
        
        MapCell current = stack.peek();
        MapCell next = pathFinding.nextCell(current);

        System.out.println(next.getCellType());
        System.out.println(next.getIdentifier());

        System.out.println(!stack.isEmpty() && pathFinding.numArrows > 0);

        while (!stack.isEmpty() && pathFinding.numArrows > 0) {
            
            System.out.println(stack.size());

            MapCell current = stack.peek();
            MapCell next = pathFinding.nextCell(current);

            if (next != null) {
                stack.push(next);
                next.markInStack();
                
                if (next.isTarget())
                    found++;
                
            } else {
                stack.pop();
            }

            if (args.length == 2)
                pathLenCounter++;
        }

        while (!stack.isEmpty()) {
            MapCell tmp = stack.pop();
            tmp.markOutStack();
        }

        // pathFinding.targetMap.repaint();

        /*
           DFS-iterative (G, s):                                   //Where G is graph and s is source vertex
                let S be stack
                S.push( s )            //Inserting s in stack 
                mark s as visited.
                while ( S is not empty):
                    
                    Pop a vertex from stack to visit next
                    v  =  S.top( ) 
                    S.pop( )
                    
                    Push all the neighbours of v in stack that are not visited   
                    for all neighbours w of v in Graph G:
                        if w is not visited :
                                S.push( w )         
                                mark w as visited
         */

    }
}
