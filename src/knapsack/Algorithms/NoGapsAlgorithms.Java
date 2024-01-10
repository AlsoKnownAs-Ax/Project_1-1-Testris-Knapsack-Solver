public class NoGaspAlgorithm{

    private int cargoX;
    private int cargoY;
    private int cargoZ;

    private int parcelSize;

   /* 
    *  0 - Empty Place
    *  1 - Occupied Place
   */
    private int[][][] cargoMatrix;

    public NoGaspAlgorithm(int[] cargoSizes, int parcelSize){
        this.cargoX =  cargoSizes[0];
        this.cargoY =  cargoSizes[1];
        this.cargoZ =  cargoSizes[2];

        this.parcelSize = parcelSize;
        this.cargoMatrix = new int[this.cargoX][this.cargoY][this.cargoZ];
    }

    public int[][][] runAlgorithm(){
        
    }

}