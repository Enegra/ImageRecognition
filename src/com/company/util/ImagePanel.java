package com.company.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by agnie on 10/7/2016.
 */
public class ImagePanel extends JPanel {

    private int panelWidth;
    private int panelHeight;
    private JLabel imageOneLabel;
    private JLabel imageTwoLabel;

    ImagePanel(int panelWidth, int panelHeight){
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        //todo
    }

    private void setupPanel(){
        this.setSize(panelWidth,panelHeight);
    }

    void drawImage(File file, int number){
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (number==0){
                if (imageOneLabel!=null){
                    this.remove(imageOneLabel);
                }
                JLabel imageOneLabel = new JLabel(new ImageIcon(bufferedImage));
                this.add(imageOneLabel);
            }
            else {
                if (imageTwoLabel!=null){
                    this.remove(imageTwoLabel);
                }
                JLabel imageTwoLabel = new JLabel(new ImageIcon(bufferedImage));
                this.add(imageTwoLabel);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
