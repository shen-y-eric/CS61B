package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites;
    private int virtual_top;
    private int virtual_bottom;
    private int openSites;
    private int N;
    public Percolation(int N) {
        this.N = N;
        sites = new boolean[N][N];
        virtual_top = N * N;
        virtual_bottom = N * N + 1;
        openSites = 0;
    }
    public void open(int row, int col) {
        
    }
    public boolean isOpen(int row, int col) {

    }
    public boolean isFull(int row, int col)  // is the site (row, col) full?
    public int numberOfOpenSites() {
        return openSites;
    }
    public boolean percolates()
    public int
    public static void main(String[] args)

}
