
import PentominosUtils.PentominoDatabase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pentomino {
	public Rectangle a,b,c,d,e;
	Color color;
	private String name;
	public int form = 1;
	private int pentoID;
	int[][] PieceMatrix;

	public Pentomino(Rectangle a, Rectangle b, Rectangle c, Rectangle d,Rectangle e, String name, int pentoID) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.name = name;
		this.pentoID = pentoID;

		this.PieceMatrix = PentominoDatabase.data[pentoID][0];

		switch (name) {
		case "j":
			color = Color.SLATEGRAY;
			break;
		case "l":
			color = Color.DARKGOLDENROD;
			break;
		case "o":
			color = Color.INDIANRED;
			break;
		case "s":
			color = Color.FORESTGREEN;
			break;
		case "t":
			color = Color.CADETBLUE;
			break;
		case "z":
			color = Color.HOTPINK;
			break;
		case "i":
			color = Color.SANDYBROWN;
			break;
		case "x":
			color = Color.BLUEVIOLET;
			break;
		case "u":
			color = Color.MAGENTA;
			break;
		case "v":
			color = Color.PINK;
			break;
		case "w":
			color = Color.RED;
			break;
		case "y":
			color = Color.YELLOW;
			break;
		case "p":
			color = Color.rgb(0,0,100);
			break;
		case "n":
			color = Color.rgb(100, 0,0);
			break;
		case "f":
			color = Color.rgb(0, 100, 0);
			break;
		}
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
		this.e.setFill(color);
	}

	public String getName() {
		return name;
	}

	public int getID(){
		return this.pentoID;
	}

	public int[][] getPieceMatrix(){
		changeMatrixNumber(this.PieceMatrix);
		return this.PieceMatrix;
	}

	/**
	 * Fixes the form of the pentos that have less than 4 forms
	 */

	private void fixFormProblem(){
		this.form++;

		switch (this.name) {
			case "x":
				this.form = 1;
				break;
			case "z":
				if(this.form % 2 == 1) this.form = 1;

				if(this.form % 2 == 0) this.form = 2;

				break;
			case "i":
				if(this.form % 2 == 1) this.form = 1;
				if(this.form % 2 == 0) this.form = 2;

				break;
			default:
				break;
		}

	}

	/**
	 * Changes the current form of the pento and stores the current piece's matrix
	 */

	public void changeForm() {
		if (this.form != 4) {
			fixFormProblem();
			this.PieceMatrix = PentominoDatabase.data[this.pentoID][this.form-1];
		} else {
			this.form = 1;
			this.PieceMatrix = PentominoDatabase.data[this.pentoID][0];
		}
	}

	/**
	 * Changes the 1's from the matrix into 2's in order to sync it with the bot
	 * @param piece
	 */

	private void changeMatrixNumber(int[][] piece){

		for(int row = 0; row < piece.length; row++)
			for(int col = 0 ; col < piece[0].length; col++)
				if(piece[row][col] == 1)
					piece[row][col] = 2;
		
	}
}