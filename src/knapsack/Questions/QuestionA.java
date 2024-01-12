package knapsack.Questions;

import knapsack.SmartObjects.CargoSpace;
import knapsack.SmartObjects.Parcel;

public class QuestionA {
    private Parcel boxA = new Parcel(10, 20, 10, 1);
    private Parcel boxB = new Parcel(10, 20, 15, 2);
    private Parcel boxC = new Parcel(10, 20, 15, 3);

    //Cargo sizes
    private int length;
    private int height;
    private int width;

    private CargoSpace container;

    public QuestionA(int[] cargoSize){
        this.length =  cargoSize[0];
        this.height =  cargoSize[1];
        this.width =  cargoSize[2];
        this.container = new CargoSpace(this.length, this.height, this.width);
    }

    public void runAlgorithm(){
        if (canFillContainer(container, boxA, boxB, boxC)) {
            System.out.println("The container can be filled without any gaps.");
        } else {
            System.out.println("The container cannot be fully filled.");
        }
    }

    public boolean canFillContainer(CargoSpace container, Parcel... boxTypes) {
        int containerVolume = container.getVolume();

        // Check if any combination of box volumes can fill the container
        for (int a = 0; a * boxTypes[0].getVolume() <= containerVolume; a++) {
            for (int b = 0; a * boxTypes[0].getVolume() + b * boxTypes[1].getVolume() <= containerVolume; b++) {
                for (int c = 0; a * boxTypes[0].getVolume() + b * boxTypes[1].getVolume() + c * boxTypes[2].getVolume() <= containerVolume; c++) {
                    int totalVolume = a * boxTypes[0].getVolume() + b * boxTypes[1].getVolume() + c * boxTypes[2].getVolume();
                    if (totalVolume == containerVolume) {
                        System.out.println("Box A used: " + a);
                        System.out.println("Box B used: " + b);
                        System.out.println("Box C used: " + c);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // private int[][][] createVisual(int numberA,int numberB, int numberC){
    //     int[][][] matrix = new int[length][height][width];

    //             // Iterate over the x-dimension
    //     for (int x = 0; x < this.length; x++) {
    //         // Iterate over the y-dimension
    //         for (int y = 0; y < this.height; y++) {
    //             // Iterate over the z-dimension
    //             for (int z = 0; z < this.width; z++) {
                    


    //             }
    //         }
    //     }
    // }
}
