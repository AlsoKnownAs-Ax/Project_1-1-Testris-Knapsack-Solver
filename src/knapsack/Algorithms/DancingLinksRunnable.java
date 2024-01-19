package knapsack.Algorithms;

import java.util.*;


public class DancingLinksRunnable implements Runnable {
        private String type;
        private boolean useValues;

        public DancingLinksRunnable(String type,boolean useValues){
                this.type = type;
                this.useValues = useValues;
        }

        @Override
        public void run() {
                CoverMatrixGenerator coverMatrixGenerator = new CoverMatrixGenerator(this.type);
                boolean[][] exactCoverMatrix = coverMatrixGenerator.getExactCoverMatrix();
                List<Integer> rowTypes = coverMatrixGenerator.getRowTypes();
                DancingLinks dancingLinks = new DancingLinks(exactCoverMatrix, rowTypes);
                dancingLinks.activateValues(useValues);
                dancingLinks.startSolve();
        }
}