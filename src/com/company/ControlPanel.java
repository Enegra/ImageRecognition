package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by agnie on 10/7/2016.
 */
class ControlPanel extends JPanel {

    private UserInterface userInterface;
    private JLabel imageOneLabel, imageOneName, imageTwoLabel, imageTwoName, transformChoiceLabel, neighbourhoodSliderLabel, coherenceThresholdLabel, iterationsLabel, errorThresholdLabel, startPointHeuristicLabel;
    private JButton imageOneButton, imageTwoButton, analyzeImageButton, showKeyPointsButton, showPairedKeyPointsButton, showNeighboursButton, showRansacButton, clearButton;
    private JSlider neighbourhoodSizeSlider, coherenceThresholdSlider, ransacErrorThresholdSlider;
    private JComboBox transformChoiceCombobox, startPointHeuristicCombobox;
    private JTextField iterationsTextfield;
    private File imageOne, imageTwo;
    private ImageAnalyser imageAnalyser;


    ControlPanel(UserInterface userInterface) {
        this.userInterface = userInterface;
        setupPanel();
    }

    private void setupPanel() {
        this.setLayout(new FlowLayout());
        setupImageOneLabel();
        setupImageOneName();
        setupImageOneButton();
        setupImageTwoLabel();
        setupImageTwoName();
        setupImageTwoButton();
        setupNeighbourhoodSliderLabel();
        setupNeighbourhoodSizeSlider();
        setupCoherenceThresholdLabel();
        setupCoherenceThresholdSlider();
        setupIterationsLabel();
        setupIterationsTextField();
        setupErrorThresholdLabel();
        setupErrorThresholdSlider();
        setupTransformChoiceLabel();
        setupTransformChoiceComboBox();
        setupStartPointHeuristicLabel();
        setupStartPointHeuristicComboBox();
        setupAnalyzeImageButton();
        setupShowKeyPointsButton();
        setupShowPairedKeyPointsButton();
        setupShowNeighboursButton();
        setupShowRansacButton();
        setupClearButton();
        this.setAlignmentX(LEFT_ALIGNMENT);
        this.setVisible(true);
    }

    private void setupImageOneLabel() {
        imageOneLabel = new JLabel("First image: ");
        this.add(imageOneLabel);
        imageOneLabel.setPreferredSize(new Dimension(200, 20));
    }

    private void setupImageOneName() {
        imageOneName = new JLabel("Choose first image");
        this.add(imageOneName);
        imageOneName.setPreferredSize(new Dimension(200, 20));
    }

    private void setupImageTwoLabel() {
        imageTwoLabel = new JLabel("Second image: ");
        this.add(imageTwoLabel);
        imageTwoLabel.setPreferredSize(new Dimension(200, 20));
        imageTwoLabel.setVisible(false);
    }

    private void setupImageTwoName() {
        imageTwoName = new JLabel("Choose second image");
        this.add(imageTwoName);
        imageTwoName.setPreferredSize(new Dimension(200, 20));
        imageTwoName.setVisible(false);
    }

    private void setupTransformChoiceLabel() {
        transformChoiceLabel = new JLabel("Choose RANSAC transform");
        this.add(transformChoiceLabel);
        transformChoiceLabel.setPreferredSize(new Dimension(200, 20));
        transformChoiceLabel.setVisible(false);
    }

    private void setupNeighbourhoodSliderLabel() {
        neighbourhoodSliderLabel = new JLabel("Choose the size of neighbourhood");
        this.add(neighbourhoodSliderLabel);
        neighbourhoodSliderLabel.setPreferredSize(new Dimension(200, 20));
        neighbourhoodSliderLabel.setVisible(false);
    }

    private void setupCoherenceThresholdLabel() {
        coherenceThresholdLabel = new JLabel("Select the coherence threshold");
        this.add(coherenceThresholdLabel);
        coherenceThresholdLabel.setPreferredSize(new Dimension(200, 20));
        coherenceThresholdLabel.setVisible(false);
    }

    private void setupIterationsLabel() {
        iterationsLabel = new JLabel("Set number of iterations");
        this.add(iterationsLabel);
        iterationsLabel.setPreferredSize(new Dimension(200, 20));
        iterationsLabel.setVisible(false);
    }

    private void setupErrorThresholdLabel() {
        errorThresholdLabel = new JLabel("Set error threshold");
        this.add(errorThresholdLabel);
        errorThresholdLabel.setPreferredSize(new Dimension(200, 20));
        errorThresholdLabel.setVisible(false);
    }

    private void setupStartPointHeuristicLabel(){
        startPointHeuristicLabel = new JLabel("Choose start point heuristic");
        this.add(startPointHeuristicLabel);
        startPointHeuristicLabel.setPreferredSize(new Dimension(200, 20));
        startPointHeuristicLabel.setVisible(false);
    }

    private void setupImageOneButton() {
        imageOneButton = new JButton("Browse");
        imageOneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == imageOneButton) {
                    FilePicker filePicker = new FilePicker();
                    imageOne = filePicker.getFilePath();
                    imageOneName.setText(imageOne.getName());
                    imageTwoLabel.setVisible(true);
                    imageTwoName.setVisible(true);
                    imageTwoButton.setVisible(true);
                    userInterface.displayFirstImage(imageOne);
                }
            }
        });
        this.add(imageOneButton);
        imageOneButton.setPreferredSize(new Dimension(200, 30));
        imageOneButton.setVisible(true);
    }

    private void setupImageTwoButton() {
        imageTwoButton = new JButton("Browse");
        imageTwoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == imageTwoButton) {
                    FilePicker filePicker = new FilePicker();
                    imageTwo = filePicker.getFilePath();
                    imageTwoName.setText(imageTwo.getName());
                    showParameterTools(true);
                    userInterface.displaySecondImage(imageTwo);
                }
            }
        });
        this.add(imageTwoButton);
        imageTwoButton.setPreferredSize(new Dimension(200, 30));
        imageTwoButton.setVisible(false);
    }

    private void showParameterTools(boolean visible){
        neighbourhoodSliderLabel.setVisible(visible);
        neighbourhoodSizeSlider.setVisible(visible);
        coherenceThresholdLabel.setVisible(visible);
        coherenceThresholdSlider.setVisible(visible);
        iterationsLabel.setVisible(visible);
        iterationsTextfield.setVisible(visible);
        errorThresholdLabel.setVisible(visible);
        ransacErrorThresholdSlider.setVisible(visible);
        transformChoiceLabel.setVisible(visible);
        transformChoiceCombobox.setVisible(visible);
        startPointHeuristicLabel.setVisible(visible);
        startPointHeuristicCombobox.setVisible(visible);
        analyzeImageButton.setVisible(visible);
    }

    private void setupAnalyzeImageButton() {
        analyzeImageButton = new JButton("Analyze images");
        analyzeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getParent(), "Calculating the keypoints of the selected images.", "Please wait.", JOptionPane.WARNING_MESSAGE);
                analyseImages();
                showDrawingButtons(true);
                String message = "Time of executing neighbourhood algorithm: " + imageAnalyser.getNeighbourTime() + System.lineSeparator() +  "Time of executing ransac algorithm: " + imageAnalyser.getRansacTime() + System.lineSeparator() + "First image keypoints: " + imageAnalyser.getFirstImageKeyPoints().size() + System.lineSeparator() + "Second image keypoints: " + imageAnalyser.getSecondImageKeyPoints().size() + System.lineSeparator() + "Paired keypoints: " + imageAnalyser.getPairedKeyPoints().size() + System.lineSeparator() + "Coherent pairs: " +imageAnalyser.getCoherentPairs().size() + System.lineSeparator() + "Ransac pairs: " + imageAnalyser.getRansacPairs().size();
                JOptionPane.showMessageDialog(getParent(), message, "Calculating finished", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.add(analyzeImageButton);
        analyzeImageButton.setPreferredSize(new Dimension(200, 30));
        analyzeImageButton.setVisible(false);
    }

    private void analyseImages(){
        int neighbourhoodSize = neighbourhoodSizeSlider.getValue();
        double coherenceThreshold = coherenceThresholdSlider.getValue()/10;
        int ransacIterations;
        if (!iterationsTextfield.getText().matches("\\d+$")){
            ransacIterations = 100;
        } else {
            ransacIterations = Integer.parseInt(iterationsTextfield.getText());
        }
        double ransacError = (double)ransacErrorThresholdSlider.getValue()/1000;
        int ransacTransform = transformChoiceCombobox.getSelectedIndex();
        imageAnalyser = new ImageAnalyser();
        imageAnalyser.generateKeyPoints(imageOne, imageTwo);
        imageAnalyser.fetchKeyPoints(imageOne, imageTwo);
        imageAnalyser.runNeighbours(neighbourhoodSize, coherenceThreshold);
        imageAnalyser.runRansac(userInterface.getProblemSize(), ransacIterations, ransacError, ransacTransform, startPointHeuristicCombobox.getSelectedIndex());
    }

    private void showDrawingButtons(boolean visible){
        showKeyPointsButton.setVisible(visible);
        showPairedKeyPointsButton.setVisible(visible);
        showNeighboursButton.setVisible(visible);
        showRansacButton.setVisible(visible);
        clearButton.setVisible(visible);
    }

    private void setupShowKeyPointsButton() {
        showKeyPointsButton = new JButton("Show Keypoints");
        showKeyPointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.getImagePanel().setFirstImageKeyPoints(imageAnalyser.getFirstImageKeyPoints());
                userInterface.getImagePanel().setSecondImageKeyPoints(imageAnalyser.getSecondImageKeyPoints());
            }
        });
        this.add(showKeyPointsButton);
        showKeyPointsButton.setPreferredSize(new Dimension(200, 30));
        showKeyPointsButton.setVisible(false);
    }

    private void setupShowPairedKeyPointsButton() {
        showPairedKeyPointsButton = new JButton("Show Pairs");
        showPairedKeyPointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.getImagePanel().setPairedKeyPoints(imageAnalyser.getPairedKeyPoints());
            }
        });
        this.add(showPairedKeyPointsButton);
        showPairedKeyPointsButton.setPreferredSize(new Dimension(200, 30));
        showPairedKeyPointsButton.setVisible(false);
    }

    private void setupShowNeighboursButton() {
        showNeighboursButton = new JButton("Show Coherent Pairs");
        showNeighboursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.getImagePanel().setCoherentKeyPoints(imageAnalyser.getCoherentPairs());
            }
        });
        this.add(showNeighboursButton);
        showNeighboursButton.setPreferredSize(new Dimension(200, 30));
        showNeighboursButton.setVisible(false);
    }

    private void setupShowRansacButton() {
        showRansacButton = new JButton("Show Ransac Pairs");
        showRansacButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.getImagePanel().setRansacKeyPoints(imageAnalyser.getRansacPairs());
            }
        });
        this.add(showRansacButton);
        showRansacButton.setPreferredSize(new Dimension(200, 30));
        showRansacButton.setVisible(false);
    }

    private void setupClearButton() {
        clearButton = new JButton("Clear marks");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.getImagePanel().setPairedKeyPoints(null);
                userInterface.getImagePanel().setCoherentKeyPoints(null);
                userInterface.getImagePanel().setRansacKeyPoints(null);
                userInterface.getImagePanel().setFirstImageKeyPoints(null);
                userInterface.getImagePanel().setSecondImageKeyPoints(null);
            }
        });
        this.add(clearButton);
        clearButton.setPreferredSize(new Dimension(200, 30));
        clearButton.setVisible(false);
    }

    private void setupTransformChoiceComboBox() {
        String[] transforms = {"Affine transform", "Perspective transform"};
        transformChoiceCombobox = new JComboBox(transforms);
        transformChoiceCombobox.setPreferredSize(new Dimension(200, 30));
        transformChoiceCombobox.setSelectedIndex(0);
        this.add(transformChoiceCombobox);
        transformChoiceCombobox.setVisible(false);
    }

    private void setupStartPointHeuristicComboBox(){
        String[] heuristicOptions = {"none", "near points"};
        startPointHeuristicCombobox = new JComboBox(heuristicOptions);
        startPointHeuristicCombobox.setPreferredSize(new Dimension(200, 30));
        startPointHeuristicCombobox.setSelectedIndex(1);
        this.add(startPointHeuristicCombobox);
        startPointHeuristicCombobox.setVisible(false);
    }

    private void setupNeighbourhoodSizeSlider() {
        neighbourhoodSizeSlider = new JSlider(JSlider.HORIZONTAL, 2, 8, 5);
        neighbourhoodSizeSlider.setMajorTickSpacing(2);
        neighbourhoodSizeSlider.setMinorTickSpacing(1);
        neighbourhoodSizeSlider.createStandardLabels(2);
        neighbourhoodSizeSlider.setSnapToTicks(true);
        neighbourhoodSizeSlider.setPaintTicks(true);
        neighbourhoodSizeSlider.setPaintLabels(true);
        this.add(neighbourhoodSizeSlider);
        neighbourhoodSizeSlider.setVisible(false);
    }

    private void setupCoherenceThresholdSlider() {
        coherenceThresholdSlider = new JSlider(JSlider.HORIZONTAL, 4, 9, 6);
        coherenceThresholdSlider.setMajorTickSpacing(2);
        coherenceThresholdSlider.setMinorTickSpacing(1);
        coherenceThresholdSlider.createStandardLabels(5);
        coherenceThresholdSlider.setSnapToTicks(true);
        coherenceThresholdSlider.setPaintTicks(true);
        coherenceThresholdSlider.setPaintLabels(true);
        this.add(coherenceThresholdSlider);
        coherenceThresholdSlider.setVisible(false);
    }

    private void setupErrorThresholdSlider() {
        ransacErrorThresholdSlider = new JSlider(JSlider.HORIZONTAL, 5, 65, 10);
        ransacErrorThresholdSlider.setMajorTickSpacing(10);
        ransacErrorThresholdSlider.setMinorTickSpacing(1);
        ransacErrorThresholdSlider.createStandardLabels(5);
        ransacErrorThresholdSlider.setSnapToTicks(true);
        ransacErrorThresholdSlider.setPaintTicks(true);
        ransacErrorThresholdSlider.setPaintLabels(true);
        this.add(ransacErrorThresholdSlider);
        ransacErrorThresholdSlider.setVisible(false);
    }

    private void setupIterationsTextField() {
        iterationsTextfield = new JTextField("Set the number of iterations");
        this.add(iterationsTextfield);
        iterationsTextfield.setPreferredSize(new Dimension(200, 30));
        iterationsTextfield.setVisible(false);
    }

}
