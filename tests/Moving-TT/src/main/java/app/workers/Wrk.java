package app.workers;

import app.controllers.MainViewCtrl;
import java.util.Date;

/**
 * Worker of the application.
 * @author kucie
 */
public class Wrk {
    
    private static double FPS = 30;
    private static double SEC_PER_ROT = 6;
    /**
     * Create the worker.
     */
    public Wrk() {
        ctrl = null;
        running = false;
        x = 0;
        y = 0;
        thr = null;
    }
    
    public void start() {
        running = true;
        thr = new Thread(() -> run());
        thr.start();
    }
    
    public void stop() {
        running = false;
        thr.isAlive();
    }
    
    private void run() {
        while(running) {
            double start = getTime();
            // Update position
            //  sin(x)        = SIN from -1 to 1
            //  sin(x)+1      = SIN from  0 to 2
            // (sin(x)+1)/2   = SIN from  0 to 1
            // (sin(x)+1)/2*n = SIN from  0 to N
            double angle = start * Math.PI * 2 / SEC_PER_ROT;
            double xMult = ctrl.getLayerWidth() - ctrl.getArrowWidth();
            double yMult = ctrl.getLayerHeight() - ctrl.getArrowHeight();
            x = (Math.sin(angle) + 1) / 2 * xMult;
            y = (Math.cos(angle) + 1) / 2 * yMult;
            ctrl.setArrowPosition(x, y);
            ctrl.setArrowRotation(-angle / Math.PI / 2 * 360);
            
            // Sleep to reach a FPS goal
            double now = getTime();
            long sleepMS = Math.round((1.0/FPS - (now - start)) * 1000);
            if (sleepMS > 0) {
                try {
                    Thread.sleep(sleepMS);
                } catch (InterruptedException e) {
                    System.err.println("Unreachable");
                }
            }
        }
    }
    
    private double getTime()
    {
        return new Date().getTime() / 1000.0;
    }

    /**
     * Set the controller reference.
     * @param ctrl The Controller
     */
    public void setCtrl(MainViewCtrl ctrl) {
        this.ctrl = ctrl;
    }
    
    private boolean running;
    private double x;
    private double y;
    private Thread thr;

    /**
     * The reference to the controller
     */
    private MainViewCtrl ctrl;
}
