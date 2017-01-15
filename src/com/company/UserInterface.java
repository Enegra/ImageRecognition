package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by agnie on 10/7/2016.
 */
public class UserInterface extends JFrame {

    private ControlPanel controlPanel;
    private ImagePanel imagePanel;
    private int frameWidth=1230;
    private int frameHeight=820;

    UserInterface(){
        prepareGUI();
    }

    private void prepareGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth,frameHeight);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("Image recognition");
        displayControlPanel();
        displayImagePanel();
        this.setVisible(true);
    }


    private void displayControlPanel(){
        controlPanel = new ControlPanel(this);
        controlPanel.setBounds(5,5, 200, 800);
        this.add(controlPanel);
    }

    private void displayImagePanel(){
        imagePanel = new ImagePanel(1000,800);
        imagePanel.setBounds(210, 5, 1000, 800);
        imagePanel.setBackground(new Color(184, 217, 224));
        this.add(imagePanel);
        imagePanel.setVisible(true);
    }

    void displayFirstImage(File imageFile){
        imagePanel.drawImage(imageFile, 0);
    }

    void displaySecondImage(File imageFile) {
        imagePanel.drawImage(imageFile, 1);
    }

    int getProblemSize(){
        return java.lang.Math.min(imagePanel.getImageOneSize(), imagePanel.getImageTwoSize());
    }

    ImagePanel getImagePanel(){
        return imagePanel;
    }

    void analyzeImages(File imageOne, File imageTwo){
        //todo
    }






}
