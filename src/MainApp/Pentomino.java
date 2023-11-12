package MainApp;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * We will make our own pentominos based on our pentominos Database in order to avoid
 * fliping pentominos
 */

public class Pentomino {
    private Rectangle a,b,c,d,e;
    private Color color;
    private int PentoID;
    private int state = 0;

    public Pentomino(Rectangle a, Rectangle b, Rectangle c, Rectangle d, Rectangle e, int pentoID){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.PentoID = pentoID;

        switch (this.PentoID) {
            case 0: this.color = Color.BLUE;
                    break;
            case 1: this.color = Color.ORANGE;
                    break;
            case 2: this.color = Color.CYAN;
                    break;
            case 3: this.color = Color.GREEN;
                    break;
            case 4: this.color = Color.MAGENTA;
                    break;
            case 5: this.color = Color.PINK;
                    break;
            case 6: this.color = Color.RED;
                    break;
            case 7: this.color = Color.YELLOW;
                    break;
            case 8: this.color = Color.rgb(0, 0, 0);
                    break;
            case 9: this.color = Color.rgb(0, 0, 100);
                    break;
            case 10: this.color = Color.rgb(100, 0,0);
                    break;
            case 11: this.color = Color.rgb(0, 100, 0);
                    break;                    
            default:
                break;
        }

        this.a.setFill(this.color);
        this.b.setFill(this.color);
        this.c.setFill(this.color);
        this.d.setFill(this.color);
        this.e.setFill(this.color);

    }

    public void changeForm(){
        if(this.state > 4) 
            this.state = 0;
        else 
            this.state++;
    }

}
