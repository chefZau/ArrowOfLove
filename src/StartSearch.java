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

    Map targetMap;  
    int numArrows;      // how many arrow has fired so far, how many target has found
    int inertia;        // how many times an arrow has travelled in the same direction
    int direction;      // tracking the direction of the arrow
    

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

    public MapCell nextCell(MapCell cell) {
        return cell;
    }

    /**
     * return either found or not found
     * @param start
     * @return
     */
    public boolean findTarget(MapCell start) {
        return false;
        
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

        

    }

    
}