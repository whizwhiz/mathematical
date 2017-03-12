package com.example.android.mathematical.util;

/**
 * Created by Matthew Johnson on 11/03/2017.
 *
 * <p>Simple stopwatch for timer how long the users answer a sum</p>
 */
public class Stopwatch {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    /**
     * start stopwatch
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        running = true;
    }

    /**
     * stop stopwatch
     */
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        running = false;
    }

    /**
     * @return The elapsed time in seconds e.g. 3.242
     */
    public double getElapsedTime() {
        double elapsed;

        if (running) {
            elapsed = ((System.currentTimeMillis() - stopTime) / 1000.0);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000.0);
        }

        return elapsed;
    }
}
