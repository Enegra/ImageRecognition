package com.company.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by agnie on 10/7/2016.
 */
class ControlPanel extends JPanel {

    private UserInterface userInterface;
    private JLabel imageOneLabel, imageOneName, imageTwoLabel, imageTwoName, analyzeButtonLabel;
    private JButton imageOneButton, imageTwoButton, analyzeImageButton;
    private File imageOne, imageTwo;

    ControlPanel(UserInterface userInterface){
        this.userInterface = userInterface;
        setupPanel();
    }

    private void setupPanel(){
        this.setLayout(new FlowLayout());
        setupImageOneLabel();
        setupImageOneName();
        setupImageOneButton();
        setupImageTwoLabel();
        setupImageTwoName();
        setupImageTwoButton();
        setupAnalyzeImageButton();
        this.setAlignmentX(LEFT_ALIGNMENT);
        setBackground(Color.cyan);
        this.setVisible(true);
    }

    private void setupImageOneLabel(){
        imageOneLabel = new JLabel("First image: ");
        this.add(imageOneLabel);
        imageOneLabel.setBackground(Color.ORANGE);
        imageOneLabel.setOpaque(true);
        imageOneLabel.setPreferredSize(new Dimension(200, 20));
    }

    private void setupImageOneName(){
        imageOneName = new JLabel("Choose first image");
        this.add(imageOneName);
        imageOneName.setBackground(Color.ORANGE);
        imageOneName.setOpaque(true);
        imageOneName.setPreferredSize(new Dimension(200, 20));
    }

    private void setupImageTwoLabel(){
        imageTwoLabel = new JLabel("Second image: ");
        this.add(imageTwoLabel);
        imageTwoLabel.setBackground(Color.ORANGE);
        imageTwoLabel.setPreferredSize(new Dimension(200, 20));
        imageTwoLabel.setVisible(false);
    }

    private void setupImageTwoName(){
        imageTwoName = new JLabel("Choose second image");
        this.add(imageTwoName);
        imageTwoName.setBackground(Color.ORANGE);
        imageTwoName.setOpaque(true);
        imageTwoName.setPreferredSize(new Dimension(200, 20));
        imageTwoName.setVisible(false);
    }

    private void setupLoadImageButtonLabel(){

    }


    private void setupImageOneButton(){
        imageOneButton = new JButton("Browse");
        imageOneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == imageOneButton){
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
        imageOneButton.setPreferredSize(new Dimension(150, 20));
        imageOneButton.setVisible(true);
    }

    private void setupImageTwoButton(){
        imageTwoButton = new JButton("Browse");
        imageTwoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == imageTwoButton){
                    FilePicker filePicker = new FilePicker();
                    imageTwo = filePicker.getFilePath();
                    imageTwoName.setText(imageTwo.getName());
                    analyzeImageButton.setVisible(true);
                    userInterface.displaySecondImage(imageTwo);
                }
            }
        });
        this.add(imageTwoButton);
        imageTwoButton.setPreferredSize(new Dimension(150, 20));
        imageTwoButton.setVisible(false);
    }

    private void setupAnalyzeImageButton(){
        analyzeImageButton = new JButton("Analyze images");
        analyzeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getKeyPoints(imageOne,imageTwo);
            }
        });
        this.add(analyzeImageButton);
        analyzeImageButton.setPreferredSize(new Dimension(150, 20));
        analyzeImageButton.setVisible(false);
    }

    void getKeyPoints(File imageOne, File imageTwo){
        String imageOneName = imageOne.getName();
        String imageTwoName = imageTwo.getName();
        String commands[] = new String[4];
        commands[0] = "c:\\cygwin\\cygwin.bat";
        commands[1] = "cd extract_features";
        commands[2] = "./extract_features.exe -haraff -sift -i " + imageOneName + " -DE ";
        commands[3] = "./extract_features.exe -haraff -sift -i " + imageTwoName + " -DE ";
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
