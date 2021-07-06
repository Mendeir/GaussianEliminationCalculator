import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class InputWindow extends JFrame implements ActionListener {
    //Calling logic class
    Logic algoProcess = new Logic();
        //Instantiation of arrays needed for displaying process.
        String[] sizes = {"3x4", "4x5", "5x6"};
        double[][] inputMatrix;
        String[] solutionMatrix;
        double[][] givenMatrix;
        String[][] logs;
        double[][][] storedProcess;
        //instantiation of counters used for traversing
        int navigationCounter = -1;
        int processCounter = -1;
        // used for instantiation of arrays
        int row = 0;
        int column = 0;
        // used for loops
        int i;
        int j;

        //Instantiation of objects for GUI
        JFrame frame = new JFrame("Gaussian Algorithm");
        JPanel panelOne = new JPanel();
        JPanel panelTwo = new JPanel();
        JPanel dropDownPanel = new JPanel();
        JPanel panelLegend = new JPanel();
        JButton enterButton = new JButton("Enter");
        JButton rightButton = new JButton(">");
        JButton leftButton = new JButton("<");
        JButton randomButton = new JButton("Random");
        JButton buttonLegend = new JButton("");
        JButton[][] display;
        JButton[] displaySolution;
        JButton[][] displayLogs;
        JComboBox matrixSize = new JComboBox(sizes);
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints gbl = new GridBagConstraints();
        GridBagConstraints gbd = new GridBagConstraints();
        JTextField[][] inputOne;
        JLabel[][] line;
        // formatting panel layout
        TitledBorder title;
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        DecimalFormat df = new DecimalFormat("###.####");


    InputWindow() {
        // Frame size and design
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(400, 100, 500, 490);

        //format for display output
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbl.fill = GridBagConstraints.BOTH;
        gbl.insets = new Insets(0, 0, 0, 0);
        gbd.fill = GridBagConstraints.BOTH;
        gbd.insets = new Insets(0, 0, 0, 0);



        // Panel size and format
        dropDownPanel.setBounds(0, 0, 500, 50);
        panelOne.setBounds(0, 50, 485, 400);
        panelOne.setLayout(new GridBagLayout());
        title = BorderFactory.createTitledBorder(blackLine, "Please Input Numbers");
        title.setTitleJustification(TitledBorder.CENTER);
        panelOne.setBorder(title);


        // Button sizes and formats
        dropDownPanel.add(leftButton);
        dropDownPanel.add(rightButton);
        rightButton.addActionListener(this);
        leftButton.addActionListener(this);
        randomButton.setBounds(0, 20, 100, 30);
        randomButton.addActionListener(this);
        dropDownPanel.add(randomButton);
        enterButton.setBounds(300, 20, 100, 25);
        enterButton.addActionListener(this);
        dropDownPanel.add(enterButton);

        // Combobox design and simulations
        matrixSize.setBounds(200, 20, 100, 25);
        matrixSize.addActionListener(this);
        dropDownPanel.add(matrixSize);

        // Adds and visibility of the frame
        frame.add(panelLegend);
        frame.add(panelOne);
        frame.add(panelTwo);
        frame.add(dropDownPanel);
        frame.setVisible(true);

         }

    @Override
    public void actionPerformed(ActionEvent e) {
        // box size action
        if (e.getSource() == matrixSize) {

            // Get the user's wanted size form the comboBox
            String size = (String) matrixSize.getSelectedItem();

            String[] s = size.split("x");
            row = Integer.parseInt(s[0]);
            column = Integer.parseInt(s[1]);
            gridLayoutOne(row, column);
        }

        // Random Button action
        if (e.getSource() == randomButton) {
            for (i = 0; i < row; i++) {
                for (j = 0; j < column; j++) {
                    inputOne[i][j].setText(String.format("%d", (int) (Math.random() * 10)));
                }
            }
        }
        // Enter Button Action
        if(e.getSource() == enterButton){
            // try catch for error handling
            try {
                //initialization of inputMatrix
                inputMatrix = new double[row][column];
                givenMatrix = new double[row][column];
                // Store all the values input by the user on Matrix A and Matrix B
                for (i = 0; i < row; i++) {
                    for (j = 0; j < column; j++) {
                        inputMatrix[i][j] = Double.parseDouble(inputOne[i][j].getText());
                        givenMatrix[i][j] = Double.parseDouble(inputOne[i][j].getText());
                    }
                }
            }catch(NumberFormatException er){
                // calling error window class
                ErrorWindow error = new ErrorWindow();
            }
            //process done calling logic class
            algoProcess.gaussianElimination(inputMatrix);
            solutionMatrix = algoProcess.backSubstitution(inputMatrix);
            logs = algoProcess.getLogs();
            storedProcess = algoProcess.getStoredProcess();
        }
        // traversing through process, forward
        if(e.getSource() == rightButton){
            panelOne.removeAll();
            panelOne.revalidate();
            panelTwo.removeAll();
            panelTwo.revalidate();
            panelLegend.removeAll();
            panelLegend.revalidate();
            frame.repaint();

            navigationCounter++;

            if(navigationCounter == 0) {
                frame.setBounds(400, 100, 500, 490);
                panelOne.setBounds(0, 50, 485, 400);
                displayGivenMatrix(givenMatrix);
                panelTwo.setBorder(new EmptyBorder(0,0,0,0));
                processCounter = -1;
            }else if(navigationCounter == 1 || processCounter != row - 1){
                frame.setBounds(100, 100, 1215, 490);
                panelOne.setBounds(0, 50, 700, 400);
                title = BorderFactory.createTitledBorder(blackLine, "Process");
                title.setTitleJustification(TitledBorder.CENTER);
                panelOne.setBorder(title);
                panelTwo.setBounds(700, 50, 500, 400);
                panelTwo.setLayout(new GridBagLayout());
                title = BorderFactory.createTitledBorder(blackLine, "Steps");
                title.setTitleJustification(TitledBorder.CENTER);
                panelTwo.setBorder(title);
                panelLegend.setBorder(new EmptyBorder(0,0,0,0));

                processCounter++;
                nextButtonProcess();
                nextButtonLogs();
            }else{
                frame.setBounds(100, 100, 800, 620);
                panelOne.setBounds(0, 50, 785, 400);
                panelLegend.setBounds(0,450,785,130);
                panelLegend.setLayout(new BorderLayout());
                title = BorderFactory.createTitledBorder(blackLine, "Legend");
                panelLegend.setBorder(title);
                title = BorderFactory.createTitledBorder(blackLine, "Final Result");
                title.setTitleJustification(TitledBorder.CENTER);
                panelOne.setBorder(title);
                panelTwo.setBorder(new EmptyBorder(0,0,0,0));

                legendDisplay();
                displayFinalResult(inputMatrix);
            }
        }
        // traversing process backwards
        if(e.getSource() == leftButton) {
            panelOne.removeAll();
            panelOne.revalidate();
            panelTwo.removeAll();
            panelTwo.revalidate();
            panelLegend.removeAll();
            panelLegend.revalidate();
            frame.repaint();
            if (navigationCounter != -2) {
                navigationCounter--;
                if (navigationCounter == 0) {
                    frame.setBounds(400, 100, 500, 490);
                    panelOne.setBounds(0, 50, 485, 400);
                    displayGivenMatrix(givenMatrix);
                    panelTwo.setBorder(new EmptyBorder(0, 0, 0, 0));
                    processCounter = -1;
                } else if (navigationCounter == 1 || processCounter != row - 1 || navigationCounter == column - 1) {
                    frame.setBounds(100, 100, 1215, 490);
                    panelOne.setBounds(0, 50, 700, 400);
                    title = BorderFactory.createTitledBorder(blackLine, "Process");
                    title.setTitleJustification(TitledBorder.CENTER);
                    panelOne.setBorder(title);
                    panelTwo.setBounds(700, 50, 500, 400);
                    panelTwo.setLayout(new GridBagLayout());
                    title = BorderFactory.createTitledBorder(blackLine, "Steps");
                    title.setTitleJustification(TitledBorder.CENTER);
                    panelTwo.setBorder(title);
                    panelLegend.setBorder(new EmptyBorder(0, 0, 0, 0));


                    nextButtonProcess();
                    nextButtonLogs();
                    processCounter--;
                } else {
                    frame.setBounds(250, 100, 800, 620);
                    panelOne.setBounds(0, 50, 785, 400);
                    panelLegend.setBounds(0, 450, 785, 130);
                    panelLegend.setLayout(new BorderLayout());
                    title = BorderFactory.createTitledBorder(blackLine, "Legend");
                    panelLegend.setBorder(title);
                    title = BorderFactory.createTitledBorder(blackLine, "Final Result");
                    title.setTitleJustification(TitledBorder.CENTER);
                    panelOne.setBorder(title);
                    panelTwo.setBorder(new EmptyBorder(0, 0, 0, 0));

                    legendDisplay();
                    displayFinalResult(inputMatrix);
                }
            }
        }


    }
    // displaying the process
    public void nextButtonProcess() {
        display = new JButton[row][column];
        for(i = 0; i < storedProcess[processCounter].length; i++ ){
            for(j = 0; j <storedProcess[processCounter][i].length; j++ ){
                display[i][j] = new JButton(df.format(storedProcess[processCounter][i][j]));
                gbd.gridx = j;
                gbd.gridy = i;
                display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                panelOne.add(display[i][j], gbd);
                display[i][j].setHorizontalAlignment(JButton.CENTER);

            }
        }
    }
    //Displaying the logs steps
    public void nextButtonLogs(){
        displayLogs = new JButton[1][row];
        for(i = 0; i < 1; i++ ){
            for(j = 0; j < row; j++ ){
                displayLogs[i][j] = new JButton(logs[processCounter][j]);
                gbd.gridx = i;
                gbd.gridy = j;
                displayLogs[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                panelTwo.add(displayLogs[i][j], gbd);
                displayLogs[i][j].setHorizontalAlignment(JButton.CENTER);
            }
        }
    }
    // displaying the matrix used for user input
    public void gridLayoutOne(int row, int column) {
        inputOne = new JTextField[row][column];
        line = new JLabel[row][column];
        panelOne.removeAll();
        panelOne.revalidate();
        frame.repaint();

        for (i = 0; i < row; i++) {
            for (j = 0; j < column; j++) {
                if (j == column - 1) {
                    inputOne[i][j] = new JTextField(3);
                    gbc.gridx = j+1;
                    gbc.gridy = i;
                    inputOne[i][j].setFont(new Font("Arial", Font.PLAIN, 15));
                    panelOne.add(inputOne[i][j], gbc);
                    inputOne[i][j].setHorizontalAlignment(JTextField.CENTER);

                    line[i][j] = new JLabel("|");
                    gbl.gridx = j;
                    gbl.gridy = i;
                    line[i][j].setFont(new Font("Arial", Font.PLAIN, 25));
                    panelOne.add(line[i][j], gbl);
                    line[i][j].setHorizontalAlignment(JLabel.CENTER);
                } else{
                    inputOne[i][j] = new JTextField(3);
                    gbc.gridx = j;
                    gbc.gridy = i;
                    inputOne[i][j].setFont(new Font("Arial", Font.PLAIN, 15));
                    panelOne.add(inputOne[i][j], gbc);
                    inputOne[i][j].setHorizontalAlignment(JTextField.CENTER);
                }
            }
        }
    }
    //displaying the final result
    public void displayFinalResult(double[][] inputMatrix){
        display = new JButton[row][column];
        displaySolution = new JButton[row];
            for (i = 0; i < row; i++) {
                for (j = 0; j < column; j++) {
                    if (j == column - 1) {
                        display[i][j] = new JButton("|");
                        gbd.gridx = j+2;
                        gbd.gridy = i;
                        display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                        panelOne.add(display[i][j], gbd);
                        display[i][j].setHorizontalAlignment(JLabel.CENTER);

                        display[i][j] = new JButton(df.format(inputMatrix[i][j]));
                        gbd.gridx = j+1;
                        gbd.gridy = i;
                        display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                        display[i][j].setBackground(Color.cyan);
                        panelOne.add(display[i][j], gbd);
                        display[i][j].setHorizontalAlignment(JButton.CENTER);

                        display[i][j] = new JButton("|");
                        gbd.gridx = j;
                        gbd.gridy = i;
                        display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                        panelOne.add(display[i][j], gbd);
                        display[i][j].setHorizontalAlignment(JLabel.CENTER);

                        displaySolution[i] = new JButton(solutionMatrix[i]);
                        gbd.gridx = j+3;
                        gbd.gridy = i;
                        displaySolution[i].setFont(new Font("Arial", Font.PLAIN, 20));
                        displaySolution[i].setBackground(Color.green);
                        panelOne.add(displaySolution[i], gbd);
                        displaySolution[i].setHorizontalAlignment(JButton.CENTER);
                    } else{
                        display[i][j] = new JButton(df.format(inputMatrix[i][j]));
                        gbd.gridx = j;
                        gbd.gridy = i;
                        display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                        display[i][j].setBackground(Color.yellow);
                        panelOne.add(display[i][j], gbd);
                        display[i][j].setHorizontalAlignment(JButton.CENTER);
                    }
                }
            }

    }
    // displaying the legend
    public void legendDisplay(){
        buttonLegend = new JButton("Solution");
        buttonLegend.setBackground(Color.green);
        panelLegend.add(buttonLegend,BorderLayout.EAST);

        buttonLegend = new JButton("Answer");
        buttonLegend.setBackground(Color.cyan);
        panelLegend.add(buttonLegend,BorderLayout.CENTER);

        buttonLegend = new JButton("Expression");
        buttonLegend.setBackground(Color.yellow);
        panelLegend.add(buttonLegend, BorderLayout.WEST);


    }
    // displaying the entered values in a matrix
    public void displayGivenMatrix(double[][] givenMatrix) {
        title = BorderFactory.createTitledBorder(blackLine, "Given Matrix");
        title.setTitleJustification(TitledBorder.CENTER);
        panelOne.setBorder(title);
        display = new JButton[row][column];
        for (i = 0; i < row; i++) {
            for (j = 0; j < column; j++) {
                if (j == column - 1) {
                    display[i][j] = new JButton(df.format(givenMatrix[i][j]));
                    gbd.gridx = j + 1;
                    gbd.gridy = i;
                    display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                    panelOne.add(display[i][j], gbd);
                    display[i][j].setHorizontalAlignment(JTextField.CENTER);

                    display[i][j] = new JButton("|");
                    gbd.gridx = j;
                    gbd.gridy = i;
                    display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                    panelOne.add(display[i][j], gbd);
                    display[i][j].setHorizontalAlignment(JLabel.CENTER);

                } else {
                    display[i][j] = new JButton(df.format(givenMatrix[i][j]));
                    gbd.gridx = j;
                    gbd.gridy = i;
                    display[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                    panelOne.add(display[i][j], gbd);
                    display[i][j].setHorizontalAlignment(JTextField.CENTER);
                }
            }
        }
    }
}
