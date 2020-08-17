package source;

import java.io.File;
import java.util.ArrayList;

public class Controller {

    private static Controller singletonController;

    private ArrayList<File> files;

    /**
     * Class constructor
     */
    public Controller() {
        files = new ArrayList<>();
    }

    /**
     * Singleton pattern
     *
     * @return Controller
     */
    public static Controller getSingletonController() {
        if (singletonController == null)
            singletonController = new Controller();

        return singletonController;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
}
