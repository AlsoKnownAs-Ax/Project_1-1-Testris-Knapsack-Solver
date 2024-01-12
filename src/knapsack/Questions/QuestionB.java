package knapsack.Questions;

import knapsack.SmartObjects.CargoSpace;
import knapsack.SmartObjects.Parcel;

public class QuestionB {
    CargoSpace cargoSpace;

    private Parcel boxA = new Parcel(10, 20, 10, 3);
    private Parcel boxB = new Parcel(10, 20, 15, 4);
    private Parcel boxC = new Parcel(10, 20, 15, 5);

    public QuestionB(int[] cargoSize){
        int length =  cargoSize[0];
        int height =  cargoSize[1];
        int width =  cargoSize[2];

        cargoSpace = new CargoSpace(length, height, width);
    }

    public void runAlgorithm(){
        System.out.println("Maximum value: " + getMaxValue(cargoSpace, boxA, boxB, boxC));
    }

    public static int getMaxValue(CargoSpace cargoSpace, Parcel... parcels) {
        int cargoVolume = cargoSpace.getVolume();
        int[] dp = new int[cargoVolume + 1];

        for (int i = 1; i <= cargoVolume; i++) {
            for (Parcel parcel : parcels) {
                if (parcel.getVolume() <= i) {
                    dp[i] = Math.max(dp[i], dp[i - parcel.getVolume()] + parcel.getValue());
                }
            }
        }

        return dp[cargoVolume];
    }
}

