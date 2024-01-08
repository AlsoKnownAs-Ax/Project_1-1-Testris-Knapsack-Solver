package knapsack;

import java.util.Random;

public class GeneticAlgorithm {
    
    private int[] cargo;
    private int PARCEL_SIZE;
    private int populationSize;


    public GeneticAlgorithm(int[] cargo, int parcelSize){
        this.cargo = cargo;
        this.PARCEL_SIZE = parcelSize;
        this.populationSize = 0;
    }

    public void runAlghorithm(int maxSize, String startPopulation){

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
