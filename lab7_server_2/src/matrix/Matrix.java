package matrix;

import exception.ColumnIndexOutOfBoundException;
import exception.MatrixSizeOutOfBoundException;
import exception.RowIndexOutOfBoundException;

import java.io.Serializable;
import java.util.Random;

public class Matrix implements Serializable {
    private double[][] matrix;
    private int rows;
    private int columns;

    public Matrix(int r, int c){
        if(r < 1 || r > 1000 ) throw new MatrixSizeOutOfBoundException();
        if(c < 1 || c > 1000)  throw new MatrixSizeOutOfBoundException();
        rows = r;
        columns = c;
        Random random = new Random();
        this.matrix = new double[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                matrix[i][j] = random.nextDouble()*1000;
            }
        }
    }
    public int getRowCount(){
        return rows;
    }
    public int getColumnCount(){
        return columns;
    }
    public double getElement(int r, int c){
        checkIndexes(r,c);
        return matrix[r][c];
    }
    public void setElement(int r, int c, double value){
        checkIndexes(r,c);
        matrix[r][c] = value;
    }
    private void checkIndexes(int r, int c){
        if(r < 0 || r >= rows) throw new RowIndexOutOfBoundException();
        if(c < 0 || c >= columns) throw new ColumnIndexOutOfBoundException();
    }
    public double getSumOfOddElements(){
        double res = 0;
        for(int i = 0; i < rows;i++){
            for(int j = 0; j < columns; j++){
                if(matrix[i][j] % 2 != 0){
                    res += matrix[i][j];
                }
            }
        }
        return res;
    }

}
