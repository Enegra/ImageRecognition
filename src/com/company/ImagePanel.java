package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by agnie on 10/7/2016.
 */
public class ImagePanel extends JPanel {

    private int panelWidth;
    private int panelHeight;
    private BufferedImage imageOne;
    private BufferedImage imageTwo;
    private int imageOneScaleFactor = 0;
    private int imageTwoScaleFactor = 0;
    private int scale = 1;

    private ArrayList<ArrayList<KeyPoint>> pairedKeyPoints;
    private ArrayList<ArrayList<KeyPoint>> coherentKeyPoints;
    private ArrayList<ArrayList<KeyPoint>> ransacKeyPoints;

    private ArrayList<KeyPoint> firstImageKeyPoints;
    private ArrayList<KeyPoint> secondImageKeyPoints;

    ImagePanel(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        setupPanel();
        //todo
    }

    private void setupPanel() {
        this.setSize(panelWidth, panelHeight);
        this.setLayout(null);
    }

    void drawImage(File file, int number) {
        if (file == null) {
            if (number == 0) {
                imageOne = null;
            } else {
                imageTwo = null;
            }
        } else {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                scale = 1;
                if (bufferedImage.getHeight() > 400 || bufferedImage.getWidth() > 1000) {
                    bufferedImage = resizeImage(bufferedImage);
                }
                if (number == 0) {
                    imageOne = bufferedImage;
                    imageOneScaleFactor = scale;
                    repaint();
                } else {
                    imageTwo = bufferedImage;
                    imageTwoScaleFactor = scale;
                    repaint();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void paintImages(Graphics2D graphics2D) {
        if (imageOne != null) {
            graphics2D.drawImage(imageOne, 0, 0, null);
            if (imageTwo != null) {
                graphics2D.drawImage(imageTwo, 0, imageOne.getHeight() + 30, null);
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage) {
        int widthScale = originalImage.getWidth() / 1000 + 1;
        int heightScale = originalImage.getHeight() / 400 + 1;
        scale = java.lang.Math.max(widthScale, heightScale);
        int scaledWidth = originalImage.getWidth() / scale;
        int scaledHeight = originalImage.getHeight() / scale;
        BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, originalImage.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        graphics.dispose();
        return resizedImage;
    }

    int getImageOneScaleFactor() {
        return imageOneScaleFactor;
    }

    int getImageTwoScaleFactor() {
        return imageTwoScaleFactor;
    }

    int getImageOneSize() {
        return imageOne.getHeight();
    }

    int getImageTwoSize() {
        return imageTwo.getHeight();
    }

    void displayPairedKeyPoints(Graphics2D graphics2D) {
        drawKeyPointPairs(pairedKeyPoints, new Color(237, 156, 26), graphics2D);
    }

    void displayCoherentPairs(Graphics2D graphics2D) {
        drawKeyPointPairs(coherentKeyPoints, new Color(237, 26, 156), graphics2D);
    }

    void displayRansacPairs(Graphics2D graphics2D) {
        drawKeyPointPairs(ransacKeyPoints, new Color(26, 201, 237), graphics2D);
    }

    private void drawKeyPointPairs(ArrayList<ArrayList<KeyPoint>> pairs, Color color, Graphics2D graphics2D) {
        if (pairs != null && pairs.size() > 0) {
            graphics2D.setColor(color);
            for (ArrayList<KeyPoint> keyPoints : pairs) {
                int oneX = (int) keyPoints.get(0).getX() / imageOneScaleFactor;
                int oneY = (int) keyPoints.get(0).getY() / imageOneScaleFactor;
                int twoX = (int) keyPoints.get(1).getX() / imageTwoScaleFactor;
                int twoY = (int) keyPoints.get(1).getY() / imageTwoScaleFactor + imageOne.getHeight() + 30;
                graphics2D.drawLine(oneX, oneY, twoX, twoY);
            }
        }
    }


    private void drawKeyPoints(Graphics2D graphics2D) {
        if (firstImageKeyPoints != null) {
            graphics2D.setColor(new Color(17, 214, 148));
            for (KeyPoint keyPoint : firstImageKeyPoints) {
                int x = (int) keyPoint.getX() / imageOneScaleFactor;
                int y = (int) keyPoint.getY() / imageOneScaleFactor;
                graphics2D.drawLine(x, y, x, y);
            }
        }
        if (secondImageKeyPoints != null) {
//            graphics2D.setColor(new Color(95, 17, 214));
            for (KeyPoint keyPoint : secondImageKeyPoints) {
                int x = (int) keyPoint.getX() / imageTwoScaleFactor;
                int y = (int) keyPoint.getY() / imageTwoScaleFactor + imageOne.getHeight() + 30;
                graphics2D.drawLine(x, y, x, y);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintImages(graphics2D);
        displayPairedKeyPoints(graphics2D);
        displayCoherentPairs(graphics2D);
        displayRansacPairs(graphics2D);
        drawKeyPoints(graphics2D);
    }

    void setPairedKeyPoints(ArrayList<ArrayList<KeyPoint>> pairedKeyPoints) {
        this.pairedKeyPoints = pairedKeyPoints;
        revalidate();
        repaint();
    }

    void setCoherentKeyPoints(ArrayList<ArrayList<KeyPoint>> coherentKeyPoints) {
        this.coherentKeyPoints = coherentKeyPoints;
        revalidate();
        repaint();
    }

    void setRansacKeyPoints(ArrayList<ArrayList<KeyPoint>> ransacKeyPoints) {
        this.ransacKeyPoints = ransacKeyPoints;
        revalidate();
        repaint();
    }

    void setFirstImageKeyPoints(ArrayList<KeyPoint> firstImageKeyPoints) {
        this.firstImageKeyPoints = firstImageKeyPoints;
        revalidate();
        repaint();
    }

    void setSecondImageKeyPoints(ArrayList<KeyPoint> secondImageKeyPoints) {
        this.secondImageKeyPoints = secondImageKeyPoints;
        revalidate();
        repaint();
    }

}
