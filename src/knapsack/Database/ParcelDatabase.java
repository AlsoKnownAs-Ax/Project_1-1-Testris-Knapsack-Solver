package knapsack.Database;

/**
* The following class contains all the methods and data regarding the Parcels.
*/

public class ParcelDatabase {

    // parcel type A
    public static boolean[][][][] aRotBool = {
        {{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}}},
        {{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}}},
        {{{true,true},{true,true}},{{true,true},{true,true}},{{true,true},{true,true}},{{true,true},{true,true}}}
    };

    // parcel type B
    public static boolean[][][][] bRotBool = {
        {{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}}},
        {{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}}},
        {{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}}},
        {{{true,true,true},{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true},{true,true,true}}},
        {{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}}},
        {{{true,true,true,true},{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true},{true,true,true,true}}}
    };

    //parcel type C
    public static boolean[][][][] cRotBool = {
        {{{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true}}}
    };

    /**
    * return a database of parcels in exact cover format
    * @return database
    */

    public static boolean[][][][][] getDatabase() {

        boolean[][][][][] rotations = {
            aRotBool,
            bRotBool,
            cRotBool
        };

        return rotations;
    }

    /**
    * return the value of a single parcel based on the order of the DatabaseMax
    * @param id id of the parcel
    * @return value of the parcel
    */

    public static int getValue(int id) {
        if (id == 1) return 3;
        if (id == 2) return 4;
        if (id == 3) return 5;

        return -1;
    }
}