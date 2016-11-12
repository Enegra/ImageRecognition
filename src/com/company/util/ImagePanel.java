package com.company.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private int imageOneScaleFactor = 0;
    private int imageTwoScaleFactor = 0;
    private int scale = 1;

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
            scale = 1;
            if (bufferedImage.getHeight() > 400 || bufferedImage.getWidth() > 1000){
                bufferedImage = resizeImage(bufferedImage);
            }
            if (number==0){
                if (imageOneLabel!=null){
                    this.remove(imageOneLabel);
                }
                imageOneLabel = new JLabel(new ImageIcon(bufferedImage));
                imageOneScaleFactor = scale;
                this.add(imageOneLabel);
            }
            else {
                if (imageTwoLabel!=null){
                    this.remove(imageTwoLabel);
                }
                imageTwoLabel = new JLabel(new ImageIcon(bufferedImage));
                imageTwoScaleFactor = scale;
                this.add(imageTwoLabel);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage){
        int widthScale = originalImage.getWidth()/1000 +1;
        int heightScale = originalImage.getHeight()/400+1;
        scale = java.lang.Math.max(widthScale,heightScale);
        int scaledWidth = originalImage.getWidth() / scale;
        int scaledHeight = originalImage.getHeight() / scale;
        BufferedImage resizedImage = new BufferedImage(scaledWidth,scaledHeight, originalImage.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        graphics.dispose();
        return resizedImage;
    }

    int getImageOneScaleFactor(){
        return imageOneScaleFactor;
    }

    int getImageTwoScaleFactor(){
        return imageTwoScaleFactor;
    }

}
