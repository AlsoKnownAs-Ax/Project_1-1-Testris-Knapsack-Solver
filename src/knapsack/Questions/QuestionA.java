package knapsack.Questions;

import knapsack.SmartObjects.CargoSpace;
import knapsack.SmartObjects.Parcel;

public class QuestionA {
    private Parcel boxA = new Parcel(10, 20, 10, 1);
    private Parcel boxB = new Parcel(10, 20, 15, 2);
    private Parcel boxC = new Parcel(10, 20, 15, 3);

    private CargoSpace container;

    public QuestionA(int[] cargoSize){
        int length =  cargoSize[0];
        int height =  cargoSize[1];
        int width =  cargoSize[2];
        this.container = new CargoSpace(length, width, height);
    }

    public void runAlgorithm(){
        if (canFillContainer(container, boxA, boxB, boxC)) {
            System.out.println("The container can be filled without any gaps.");
        } else {
            System.out.println("The container cannot be fully filled.");
        }
    }

    public static boolean canFillContainer(CargoSpace container, Parcel... boxTypes) {
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

    public static boolean isItDone(){
        boolean finished = false;
        while (!finished){
            if (!(a=0) && !(b=0) && !(c=0)){
                finished = true;
                return finished;
            } 
        }
        return finished;
    }

    public static int[][][] position(int a, int b, int c, boolean isItDone){
        while (!isItDone){
            if ((a => 16)){
                a-=16;
            }
            if ((b => 16)){
                b-=16;
            }
            if (((a => 11) && (C>=1) )){
                a-=11;
                c-=1;
            }

        //x Possible combinations:
        // 16A
        // 16B
        // 11C
        // 11(A or B) + C 
            if ((a=>2)){
                a-=2;
            }
            if ((a=>1)&&(C>=1)){
                a-=11;
                c-=1;
            }
            if ((A=>1)&&(B>=1)){
                a-=11;
                b-=1;
            }

        //y Possible combinations:
        // 2A 
        // A+(B or C)
            if ((c=>2)){
                c-=2;
            }
            if (((c=>1) && (a=>1))){
                c-=1;
                a-=1;


            }
            if (((c=>1) && (b=>1))){
                c-=1;
                b-=1;
                

            }
            if ((a=>2)){
                a-=1;

            }
            if ((b=>2)){

            }
            if ((a=>1)&&(a=>1)){

            }
            

        //z Possible combination:
        // 2C 
        // C + (A or B) 
        // 2A 
        // 2B 
        // A+B 
        }
    }
}
