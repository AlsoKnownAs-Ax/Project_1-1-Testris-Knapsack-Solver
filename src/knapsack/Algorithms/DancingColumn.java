package knapsack.Algorithms;

class DancingColumn extends DancingNode {
    int size; 
    String name;


    DancingColumn() {
        super();
        this.header = this;
        this.size = 0;
        this.name = "";
    }

    void unlink() {
        this.unlinkFromRow();

        for (DancingNode i = this.down; i != this.header; i = i.down) {
            for (DancingNode j = i.right; j != i; j = j.right) {
                j.unlinkFromColumn();
            }
        }
    }
    
    void link() {
        for (DancingNode i = this.upper; i != this.header; i = i.upper) {
            for (DancingNode j = i.left; j != i; j = j.left) {
                j.relinkToColumn();
            }
        }
        this.relinkToRow();
    }
}