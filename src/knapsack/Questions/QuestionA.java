package knapsack.Questions;

public class QuestionA {
    private Box boxA = new Box(10, 20, 10, 1);
    private Box boxB = new Box(10, 20, 15, 2);
    private Box boxC = new Box(10, 20, 15, 3);

    private Container container;

    public QuestionA(int[] cargoSize){
        int length =  cargoSize[0];
        int height =  cargoSize[1];
        int width =  cargoSize[2];
        this.container = new Container(length, width, height);
    }

    static class Box {
       private int length, width, height;
       //Might need it for 3D Rendering
       private int type;

        Box(int length, int width, int height, int type) {
            this.length = length;
            this.width = width;
            this.height = height;
            this.type = type;
        }

        int getVolume() {
            return length * width * height;
        }
    }
    
    static class Container {
        int length, width, height;
        boolean[][][] space;

        Container(int length, int width, int height) {
            this.length = length;
            this.width = width;
            this.height = height;
            this.space = new boolean[length][width][height];
        }

        int getVolume() {
            return length * width * height;
        }
    }

    public void runAlgorithm(){
        if (canFillContainer(container, boxA, boxB, boxC)) {
            System.out.println("The container can be filled without having any gaps.");
        } else {
            System.out.println("The container cannot be fully filled.");
        }
    }

    public static boolean canFillContainer(Container container, Box... boxTypes) {
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
}
