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

    }

    public MapCell nextCell(MapCell cell) {
        return cell;
        
    }


    public static void main(String[] args) {
        
    }

    
}