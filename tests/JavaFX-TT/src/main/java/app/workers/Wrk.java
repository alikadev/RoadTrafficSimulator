package app.workers;

import app.controllers.MainViewCtrl;
import java.util.ArrayList;

/**
 * Worker of the application.
 * @author kucie
 */
public class Wrk {
    
    /**
     * Create the worker.
     */
    public Wrk() {
        ctrl = null;
        counter = 0;
    }

    /**
     * Set the controller reference.
     * @param ctrl The Controller
     */
    public void setCtrl(MainViewCtrl ctrl) {
        this.ctrl = ctrl;
    }
    
    /**
     * Fetch the data.
     */
    public void fetchData() {
        // Long operation, maybe an API fetch...
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("Unreachable");
        }
        
        // Send the list to the controller
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 5; ++i){
            list.add("Value " + counter++);
        }
        ctrl.updateList(list);
    }
    
    /**
     * A counter to not create multiple time the same ressources.
     */
    private int counter;

    /**
     * The reference to the controller
     */
    private MainViewCtrl ctrl;
}
