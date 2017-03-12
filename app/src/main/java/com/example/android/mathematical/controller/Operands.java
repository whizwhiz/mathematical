package com.example.android.mathematical.controller;

import java.util.Random;

/**
 * Created by Matthew Johnson on 11/03/2017.
 *
 * <p>This class is for handling operand task</p>
 */
public class Operands {
    // TODO Change this to the operand bucket concept.
    /** Number of operands to be return by getOperands */
    private final int NUMBER_OF_OPERANDS = 2;
    /** Maximum number of each operand */
    private final int OPERANDS_MAX = 10;

    private Random rng = new Random(); // Random number generator

    /**
     * This class handles all operand tasks
     */
    public Operands(){

    }

    /**
     * For retrieving a set of operands. Size set determined by NUMBER_OF_OPERANDS constant.
     * @return int array contain operands
     */
    public int[] getOperands(){
        // int array to return the necessary operands for a sum.
        int[] ops = new int[NUMBER_OF_OPERANDS];

        // assign random operands to int array
        for (int i = 0; i < NUMBER_OF_OPERANDS; i++) {
            ops[i] = rng.nextInt(OPERANDS_MAX) + 1; // exclude zeros
        }

        return ops;
    }

    /**
     * Converts the given sum operands and operation into a sum string e.g "2 x 4 ="
     * @param sumOperands The operands for the sum
     * @param operation The operation for the sum
     * @return The sum string
     */
    public static String sumToString(int[] sumOperands, String operation) {
        String sumLine = "";

        // Create sum string from sumOperannds[] and operation
        for (int i = 0; i < sumOperands.length; i++) {
            if (i == sumOperands.length-1) {
                sumLine = sumLine + sumOperands[i] + " =";
            } else {
                sumLine = sumLine + sumOperands[i] + " " + operation + " ";
            }
        }

        return sumLine;
    }
}
