package knapsack.Database;

public class PentominoesDatabase {
		
    public static boolean[][][][] Tpento = {
		{{{true}, {false}, {false}}, {{true}, {true}, {true}}, {{true}, {false}, {false}}},
		{{{true}, {true}, {true}}, {{false}, {true}, {false}}, {{false}, {true}, {false}}},
		{{{false}, {false}, {true}}, {{true}, {true}, {true}}, {{false}, {false}, {true}}},
		{{{false}, {true}, {false}}, {{false}, {true}, {false}}, {{true}, {true}, {true}}},
		{{{true, false, false}}, {{true, true, true}}, {{true, false, false}}},
		{{{false, false, true}}, {{true, true, true}}, {{false, false, true}}},
		{{{false, true, false}}, {{false, true, false}}, {{true, true, true}}},
		{{{true, true, true}}, {{false, true, false}}, {{false, true, false}}},
		{{{true, true, true}, {false, true, false}, {false, true, false}}},
		{{{true, false, false}, {true, true, true}, {true, false, false}}},
		{{{false, true, false}, {false, true, false}, {true, true, true}}},
		{{{false, false, true}, {true, true, true}, {false, false, true}}}
	};
    
    public static boolean[][][][] Lpento = {
		{{{true}, {false}}, {{true}, {false}}, {{true}, {false}}, {{true}, {true}}},
		{{{false}, {true}}, {{false}, {true}}, {{false}, {true}}, {{true}, {true}}},
		{{{true}, {true}}, {{true}, {false}}, {{true}, {false}}, {{true}, {false}}},
		{{{true}, {true}}, {{false}, {true}}, {{false}, {true}}, {{false}, {true}}},
		{{{true}, {true}, {true}, {true}}, {{true}, {false}, {false}, {false}}},
		{{{true}, {true}, {true}, {true}}, {{false}, {false}, {false}, {true}}},
		{{{true}, {false}, {false}, {false}}, {{true}, {true}, {true}, {true}}},
		{{{false}, {false}, {false}, {true}}, {{true}, {true}, {true}, {true}}},
		{{{true, false}}, {{true, false}}, {{true, false}}, {{true, true}}},
		{{{false, true}}, {{false, true}}, {{false, true}}, {{true, true}}},
		{{{true, true}}, {{true, false}}, {{true, false}}, {{true, false}}},
		{{{true, true}}, {{false, true}}, {{false, true}}, {{false, true}}},
		{{{true, true}, {true, false}, {true, false}, {true, false}}},
		{{{false, true}, {false, true}, {false, true}, {true, true}}},
		{{{true, true}, {false, true}, {false, true}, {false, true}}},
		{{{true, false}, {true, false}, {true, false}, {true, true}}},
		{{{true, true, true, true}}, {{true, false, false, false}}},
		{{{true, true, true, true}}, {{false, false, false, true}}},
		{{{true, false, false, false}}, {{true, true, true, true}}},
		{{{false, false, false, true}}, {{true, true, true, true}}},
		{{{true, false, false, false}, {true, true, true, true}}},
		{{{true, true, true, true}, {false, false, false, true}}},
		{{{true, true, true, true}, {true, false, false, false}}},
		{{{false, false, false, true}, {true, true, true, true}}}
	};
    
    public static boolean[][][][] Ppento = {
		{{{true}, {false}}, {{true}, {true}}, {{true}, {true}}},
		{{{true}, {true}}, {{true}, {true}}, {{false}, {true}}},
		{{{true}, {true}}, {{true}, {true}}, {{true}, {false}}},		
		{{{false}, {true}}, {{true}, {true}}, {{true}, {true}}},
		{{{true}, {true}, {true}}, {{true}, {true}, {false}}},
		{{{true}, {true}, {true}}, {{false}, {true}, {true}}},
		{{{false}, {true}, {true}}, {{true}, {true}, {true}}},
		{{{true}, {true}, {false}}, {{true}, {true}, {true}}},
		{{{true, false}}, {{true, true}}, {{true, true}}},
		{{{false, true}}, {{true, true}}, {{true, true}}},
		{{{true, true}}, {{true, true}}, {{false, true}}},
		{{{true, true}}, {{true, true}}, {{true, false}}},
		{{{true, true}, {true, true}, {true, false}}},
		{{{false, true}, {true, true}, {true, true}}},
		{{{true, true}, {true, true}, {false, true}}},
		{{{true, false}, {true, true}, {true, true}}},
		{{{true, true, false}}, {{true, true, true}}},
		{{{true, true, true}}, {{true, true, false}}},
		{{{true, true, true}}, {{false, true, true}}},
		{{{false, true, true}}, {{true, true, true}}},
		{{{true, true, false}, {true, true, true}}},
		{{{true, true, true}, {false, true, true}}},
		{{{true, true, true}, {true, true, false}}},
		{{{false, true, true}, {true, true, true}}}
	};

	/**
    * get pentominoes database
    * @return database of pentominoes
    */

	public static boolean[][][][][] getDatabase() {
		boolean[][][][][] pentominoes = {Lpento, Ppento, Tpento};
        return pentominoes;
    }
}
