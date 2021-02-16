import java.io.IOException;

public class StartSearch {

    private Map targetMap;
    private int numArrows;
    private int inertia;
    private int direction;

    public StartSearch(String filename) {

        try {
            this.targetMap = new Map(filename);
        } catch (InvalidMapException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method returns the best cell to continue the path from the current one.
     * 
     * @param cell 
     * @return  the best cell to continue 
     */
    public MapCell nextCell(MapCell cell) {
        return cell;
        
    }

    public static void main(String[] args) {
        
    }
}
