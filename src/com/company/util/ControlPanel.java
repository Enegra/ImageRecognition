package com.company.util;

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
    private JLabel imageOneLabel, imageOneName, imageTwoLabel, imageTwoName, loadImageButtonLabel, analyzeButtonLabel;
    private JButton imageOneButton, imageTwoButton, loadImageButton, analyzeButton;
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
        setupLoadImageButton();
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

    private void setupAnalyzeButtonLabel(){

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
                    loadImageButton.setVisible(true);
                }
            }
        });
        this.add(imageTwoButton);
        imageTwoButton.setPreferredSize(new Dimension(150, 20));
        imageTwoButton.setVisible(false);
    }

    private void setupLoadImageButton(){
        loadImageButton = new JButton("Load images");
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo
            }
        });
        this.add(loadImageButton);
        loadImageButton.setPreferredSize(new Dimension(150, 20));
        loadImageButton.setVisible(false);
    }


}
