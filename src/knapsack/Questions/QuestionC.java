package knapsack.Questions;

import knapsack.SmartObjects.CargoSpace;

/*
 * Fill the pentominos in the cargo without having any gaps
 */

public class QuestionC {

    //Cargo sizes
    private int lenght;
    private int height;
    private int width;

    private CargoSpace container;

    public QuestionC(int[] cargoSize){
        this.lenght = cargoSize[0];
        this.height = cargoSize[0];
        this.width = cargoSize[0];
        this.container = new CargoSpace(this.lenght, this.width, this.height);
    }

    public void runAlgorithm(){

    }

    //public boolean canFillContainer(CargoSpace container, )

}
