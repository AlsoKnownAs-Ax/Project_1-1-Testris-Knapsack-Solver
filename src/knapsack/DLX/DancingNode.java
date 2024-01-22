package knapsack.DLX;

class DancingNode {

    public DancingNode left, right, upper, down;
    public DancingColumn header;
    public int inputRow;

    DancingNode() {
        left = this;
        right = this;
        upper = this;
        down = this;

        inputRow = -1;
    }

    DancingNode(int inputRow) {
        left = this;
        right = this;
        upper = this;
        down = this;

        this.inputRow = inputRow;
    }

    public DancingNode linkDown(DancingNode node) {
        node.down = down;
        node.down.upper = node;
        node.upper = this;
        down = node;
        return node;
    }

    public DancingNode linkRight(DancingNode node) {
        node.right = right;
        node.right.left = node;
        node.left = this;
        right = node;
        return node;
    }


    void unlinkFromRow() {
        this.left.right = this.right;
        this.right.left = this.left;
    }


    void relinkToRow() {
        this.left.right = this.right.left = this;
    }


    void unlinkFromColumn() {
        this.upper.down = this.down;
        this.down.upper = this.upper;
        this.header.size--;
    }

    void relinkToColumn() {
        this.upper.down = this.down.upper = this;
        this.header.size++;
    }
}