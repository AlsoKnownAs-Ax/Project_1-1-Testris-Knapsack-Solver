package knapsack.SmartObjects;

public class Parcel {
    private int length, width, height, value;

    public Parcel(int length, int width, int height, int value) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.value = value;
    }

    public int getVolume() {
        return this.length * this.width * this.height;
    }

    public int getValue(){
        return this.value;
    }
}
