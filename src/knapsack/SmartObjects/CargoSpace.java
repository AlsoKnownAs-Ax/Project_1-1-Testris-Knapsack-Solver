package knapsack.SmartObjects;

public class CargoSpace {
    private int length, width, height;

    public CargoSpace(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public int getVolume() {
        return this.length * this.width * this.height;
    }
}
