import java.text.DecimalFormat;
import java.util.Arrays;

public class    Logic {
    private String[][] logs;
    private double[][][] storedProcess;

    public void gaussianElimination(double[][] processGivenArray) {
        DecimalFormat fourDecimalPlaces = new DecimalFormat("#0.0000");

        int pivotRow = 0;

        int matrixRow = processGivenArray.length;
        int matrixColumn = processGivenArray[0].length;

        logs = new String[matrixRow][matrixColumn - 1];

        //Fill the array with -
        for (String[] row : logs)
            Arrays.fill(row, "-");

        storedProcess = new double[matrixColumn - 1][matrixRow][matrixColumn];

        for (int columnCounter = 0; columnCounter < matrixColumn - 1; ++columnCounter) {
            int pivot = pivotRow;
            int logsColumnCounter = 0;

            //Find the maximum element
            for (int rowCounter = pivotRow + 1; rowCounter < matrixRow; ++rowCounter) {
                if (Math.abs(processGivenArray[rowCounter][columnCounter]) > Math.abs(processGivenArray[pivot][columnCounter])) {
                    pivot = rowCounter;
                }
            }

            //Check if the matrix can be solved
            if (processGivenArray[pivot][columnCounter] == 0) {
                System.err.println("The matrix is singular");
                continue;
            }

            //If pivot is not the max number, swap
            if (pivotRow != pivot) {
                //Save the log into the array
                String swapLog = "Swap row " + (columnCounter + 1) + " with row " + (pivot + 1);
                logs[columnCounter][logsColumnCounter] = swapLog;
                ++logsColumnCounter;

                swapMatrixRow(processGivenArray, columnCounter, pivot);
            }

            for (int rowCounter = pivotRow + 1; rowCounter < matrixRow; ++rowCounter) {
                //Look fo the inverse
                double scale = processGivenArray[rowCounter][columnCounter] / processGivenArray[pivotRow][columnCounter];

                //Save the operations log into the array
                String operationLog = "Row " + (rowCounter + 1) + " - " + fourDecimalPlaces.format(scale) + " * Row " + (pivotRow + 1);
                logs[columnCounter][rowCounter] = operationLog;
                ++logsColumnCounter;

                //Loop through the columns
                for (int matrixColumnCounter = columnCounter + 1; matrixColumnCounter < matrixColumn; ++matrixColumnCounter) {
                    //Subtract the rows
                    processGivenArray[rowCounter][matrixColumnCounter] -= processGivenArray[pivotRow][matrixColumnCounter] * scale;
                }
                //Make lower elements zero
                processGivenArray[rowCounter][columnCounter] = 0;
            }
            ++pivotRow;
            storeProcess(processGivenArray, columnCounter);
        }
    }

    public void swapMatrixRow (double[][] processGivenArray, int firstRow, int secondRow) {
        double [] tempArray = processGivenArray[firstRow];
        processGivenArray[firstRow] = processGivenArray[secondRow];
        processGivenArray[secondRow] = tempArray;
    }

    public void storeProcess (double[][] processGivenArray, int count) {
        for (int rowCounter = 0; rowCounter < processGivenArray.length; ++rowCounter) {
            for (int colCounter = 0; colCounter < processGivenArray[rowCounter].length; ++colCounter) {
                storedProcess[count][rowCounter][colCounter] = processGivenArray[rowCounter][colCounter];
            }
        }
    }

    public void displayArray () {
        System.out.println();
        for (double[][] a : storedProcess) {
            for (double[] b : a) {
                for (double c : b) {
                    System.out.print(c + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }


    public String[] backSubstitution(double[][] processGivenMatrix) {
        DecimalFormat fourDecimalPlaces = new DecimalFormat("#0.0000");

        int matrixRowSize = processGivenMatrix.length;
        int matrixColumnSize = processGivenMatrix[0].length;

        double[] solutionMatrix = new double[matrixRowSize];
        String[] solutions = new String[matrixRowSize];

        for (int rowCounter = matrixRowSize - 1; rowCounter >= 0; --rowCounter) {
            double sum = 0;

            for (int columnCounter = matrixColumnSize - 2; columnCounter > rowCounter; --columnCounter) {
                sum += solutionMatrix[columnCounter] * processGivenMatrix[rowCounter][columnCounter];
            }
            solutionMatrix[rowCounter] = (processGivenMatrix[rowCounter][matrixColumnSize - 1] - sum) / processGivenMatrix[rowCounter][rowCounter];
            String solution = fourDecimalPlaces.format((processGivenMatrix[rowCounter][matrixColumnSize - 1] - sum) / processGivenMatrix[rowCounter][rowCounter]);
            solutions[rowCounter] = solution;
        }
        return solutions;
    }

    public double[][][] getStoredProcess() {
        return storedProcess;
    }

    public String[][] getLogs() {
        return logs;
    }
}