


import java.util.Random;

public class Grid {
    private boolean[][] bombGrid;
    private int[][] countGrid;
    private int numRows;
    private int numColumns;
    private int numBombs;
    private Random random;

    public Grid() {
        this.numRows = 10;
        this.numColumns = 10;
        this.numBombs = 25;
        this.random = new Random();
        createBombGrid();
        createCountGrid();
    }

    public Grid(int rows, int columns) {
        this.numRows = rows;
        this.numColumns = columns;
        this.numBombs = 25;
        this.random = new Random();
        createBombGrid();
        createCountGrid();
    }

    public Grid(int rows, int columns, int numBombs) {
        this.numRows = rows;
        this.numColumns = columns;
        this.numBombs = numBombs;
        this.random = new Random();
        createBombGrid();
        createCountGrid();
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public boolean[][] getBombGrid() {
        boolean[][] copy = new boolean[numRows][numColumns];
        for (int i = 0; i < numRows; i++) {
            System.arraycopy(bombGrid[i], 0, copy[i], 0, numColumns);
        }
        return copy;
    }

    public int[][] getCountGrid() {
        int[][] copy = new int[numRows][numColumns];
        for (int i = 0; i < numRows; i++) {
            System.arraycopy(countGrid[i], 0, copy[i], 0, numColumns);
        }
        return copy;
    }

    public boolean isBombAtLocation(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numColumns)
            return false;
        return bombGrid[row][col];
    }

    public int getCountAtLocation(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numColumns)
            return -1;
        return countGrid[row][col];
    }

    private void createBombGrid() {
        bombGrid = new boolean[numRows][numColumns];
        int placed = 0;

        while (placed < numBombs) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numColumns);

            if (!bombGrid[r][c]) {
                bombGrid[r][c] = true;
                placed++;
            }
        }
    }

    private void createCountGrid() {
        countGrid = new int[numRows][numColumns];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                countGrid[r][c] = countAdjacentBombs(r, c);
            }
        }
    }

    private int countAdjacentBombs(int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nr = row + i;
                int nc = col + j;

                if (nr >= 0 && nr < numRows && nc >= 0 && nc < numColumns) {
                    if (bombGrid[nr][nc]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
