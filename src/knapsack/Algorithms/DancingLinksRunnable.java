package knapsack.Algorithms;

import java.util.*;


public class DancingLinksRunnable implements Runnable {
        private String type;

        public DancingLinksRunnable(String type){
                this.type = type;
        }

        @Override
        public void run() {
                CoverMatrixGenerator coverMatrixGenerator = new CoverMatrixGenerator(this.type);
                boolean[][] exactCoverMatrix = coverMatrixGenerator.getExactCoverMatrix();
                List<Integer> rowTypes = coverMatrixGenerator.getRowTypes();
                DancingLinks dancingLinks = new DancingLinks(exactCoverMatrix, rowTypes);
                dancingLinks.startSolve();
        }
}