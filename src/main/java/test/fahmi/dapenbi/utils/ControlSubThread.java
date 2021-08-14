package test.fahmi.dapenbi.utils;

import java.util.concurrent.atomic.AtomicBoolean;

import net.andreinc.mockneat.MockNeat;

public class ControlSubThread implements Runnable {
	

    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int interval;


    public ControlSubThread(int sleepInterval) {
        interval = sleepInterval;
        
    }
 
    public void start() {
        worker = new Thread(this);
        worker.start();
    }
 
    public void stop() {
        running.set(false);
    }

    public void run() { 
        running.set(true);
        while (running.get()) {
            try { 
                Thread.sleep(interval); 
            } catch (InterruptedException e){ 
                Thread.currentThread().interrupt();
                System.out.println(
                  "Thread was interrupted, Failed to complete operation");
            }
        
         } 
    } 
}