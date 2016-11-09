package com.company.util;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by agnie on 10/7/2016.
 */
public class UserInterface extends JFrame {

    private ControlPanel controlPanel;
    private ImagePanel imagePanel;
    private int frameWidth=830;
    private int frameHeight=600;

    public UserInterface(){
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
        controlPanel.setBounds(5,5, 200, 400);
        this.add(controlPanel);
    }

    private void displayImagePanel(){
        imagePanel = new ImagePanel(600,400);
        imagePanel.setBounds(210, 5, 600, 400);
        imagePanel.setBackground(Color.red);
        this.add(imagePanel);
        imagePanel.setVisible(true);
    }

    void displayFirstImage(File imageFile, int number){
        imagePanel.drawImage(imageFile, number);
    }

    void analyzeImages(File imageOne, File imageTwo){
        //todo
    }

    private void getKeyPoints(){
        //todo
    }



}
