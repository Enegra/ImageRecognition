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

    ImagePanel(int panelWidth, int panelHeight){
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        //todo
    }

    private void setupPanel(){
        this.setSize(panelWidth,panelHeight);
    }

    void drawImage(File file){
        clearPanel();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            JLabel imageLabel = new JLabel(new ImageIcon(bufferedImage));
            this.add(imageLabel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clearPanel(){
        this.removeAll();
    }

}
