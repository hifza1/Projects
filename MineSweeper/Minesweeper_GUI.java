import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Minesweeper_GUI extends JFrame {

    private JPanel boardPanel;
    private JPanel infoPanel;
    private Grid gameGrid;
    private JButton[][] board;
    private boolean gameOver;
    private int cellsRevealed;
    private int totalSafe;
    private JLabel bombsLabel;
    private JLabel revealedLabel;
    private JLabel statusLabel;

    public Minesweeper_GUI() {

        gameGrid = new Grid();
        gameOver = false;
        cellsRevealed = 0;
        totalSafe = gameGrid.getNumRows() * gameGrid.getNumColumns() - gameGrid.getNumBombs();

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(gameGrid.getNumRows(), gameGrid.getNumColumns()));

        infoPanel = new JPanel();
        bombsLabel = new JLabel("Bombs: " + gameGrid.getNumBombs());
        revealedLabel = new JLabel("Revealed: 0");
        statusLabel = new JLabel("Playing");
        infoPanel.add(bombsLabel);
        infoPanel.add(revealedLabel);
        infoPanel.add(statusLabel);

        createBoard();

        add(infoPanel, "North");
        add(boardPanel, "Center");

        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createBoard() {
        board = new JButton[gameGrid.getNumRows()][gameGrid.getNumColumns()];
        for (int r = 0; r < gameGrid.getNumRows(); r++) {
            for (int c = 0; c < gameGrid.getNumColumns(); c++) {
                JButton btn = new JButton();
                btn.addActionListener(new CellListener(r, c));
                board[r][c] = btn;
                boardPanel.add(btn);
            }
        }
    }

    private class CellListener implements ActionListener {
        int row, col;

        CellListener(int r, int c) {
            row = r;
            col = c;
        }

        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;

            if (gameGrid.isBombAtLocation(row, col)) {
                board[row][col].setText("B");
                revealAll();
                gameOver = true;
                statusLabel.setText("Lost");
                endGame(false);
                return;
            }

            int count = gameGrid.getCountAtLocation(row, col);

            if (count == 0) {
                floodFill(row, col);
            } else {
                revealCell(row, col);
            }

            if (cellsRevealed == totalSafe) {
                gameOver = true;
                revealAll();
                statusLabel.setText("Won");
                endGame(true);
            }
        }
    }

    private void revealCell(int r, int c) {
        if (r < 0 || r >= gameGrid.getNumRows() || c < 0 || c >= gameGrid.getNumColumns()) return;

        JButton btn = board[r][c];
        if (!btn.isEnabled()) return;

        int count = gameGrid.getCountAtLocation(r, c);
        if (count > 0) btn.setText("" + count);
        else btn.setText("");

        btn.setEnabled(false);
        cellsRevealed++;
        revealedLabel.setText("Revealed: " + cellsRevealed);
    }

    private void floodFill(int r, int c) {
        if (r < 0 || r >= gameGrid.getNumRows() || c < 0 || c >= gameGrid.getNumColumns()) return;
        if (!board[r][c].isEnabled()) return;

        revealCell(r, c);

        if (gameGrid.getCountAtLocation(r, c) != 0) return;

        floodFill(r - 1, c);
        floodFill(r + 1, c);
        floodFill(r, c - 1);
        floodFill(r, c + 1);
        floodFill(r - 1, c - 1);
        floodFill(r - 1, c + 1);
        floodFill(r + 1, c - 1);
        floodFill(r + 1, c + 1);
    }

    private void revealAll() {
        for (int r = 0; r < gameGrid.getNumRows(); r++) {
            for (int c = 0; c < gameGrid.getNumColumns(); c++) {
                JButton btn = board[r][c];
                if (gameGrid.isBombAtLocation(r, c)) {
                    btn.setText("B");
                } else {
                    int count = gameGrid.getCountAtLocation(r, c);
                    if (count > 0) btn.setText("" + count);
                }
                btn.setEnabled(false);
            }
        }
    }

    private void endGame(boolean win) {
        String msg;
        if (win) {
            msg = "You won!";
        } else {
            msg = "You lost!";
        }

        int option = JOptionPane.showConfirmDialog(
                null,
                msg + " Play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }


    private void restartGame() {
        gameGrid = new Grid();
        cellsRevealed = 0;
        totalSafe = gameGrid.getNumRows() * gameGrid.getNumColumns() - gameGrid.getNumBombs();
        gameOver = false;

        boardPanel.removeAll();
        infoPanel.removeAll();

        bombsLabel = new JLabel("Bombs: " + gameGrid.getNumBombs());
        revealedLabel = new JLabel("Revealed: 0");
        statusLabel = new JLabel("Playing");

        infoPanel.add(bombsLabel);
        infoPanel.add(revealedLabel);
        infoPanel.add(statusLabel);

        createBoard();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new Minesweeper_GUI();
    }
}
