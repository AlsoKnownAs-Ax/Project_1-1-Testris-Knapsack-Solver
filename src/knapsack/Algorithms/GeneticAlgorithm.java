package knapsack.Algorithms;

import java.util.Random;

public class GeneticAlgorithm {
    
    private int cargoX;
    private int cargoY;
    private int cargoZ;

    private int PARCEL_SIZE;
    private int populationSize;

    /* 
    *  0 - Empty Place
    *  1 - Occupied Place
    */
    private int[][][] cargoMatrix;


    public GeneticAlgorithm(int[] cargo, int parcelSize){
        this.cargoX = cargo[0];
        this.cargoY = cargo[1];
        this.cargoZ = cargo[2];
        this.PARCEL_SIZE = parcelSize;
        this.populationSize = 0;

        this.cargoMatrix = new int[this.cargoX][this.cargoY][this.cargoZ];
    }

    public void runAlgorithm(int maxSize, String startPopulation){

    }


    class Individual {
        int fitness = 0;
        int[] genes = new int[5];
        int geneLength = 5;

        public Individual() {
            Random rn = new Random();

            //Set genes randomly for each individual
            for (int i = 0; i < genes.length; i++) {
                genes[i] = Math.abs(rn.nextInt() % 2);
            }

            fitness = 0;
        }

        //Calculate fitness
        public void calcFitness() {

            fitness = 0;
            for (int i = 0; i < 5; i++) {
                if (genes[i] == 1) {
                    ++fitness;
                }
            }
        }
    }
}
