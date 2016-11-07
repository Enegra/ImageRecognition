package com.company;

import com.company.util.FilePicker;

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int imageHeight = 440;
        double smallRadius = 0.01 * imageHeight;
        double largeRadius = 0.3 * imageHeight;
        int errorThreshold = 50;
//        UserInterface userInterface = new UserInterface();
        ImageAnalyser imageAnalyser = new ImageAnalyser();
        FilePicker filePicker = new FilePicker();
        File firstImageDoc = filePicker.getFilePath();
        File secondImageDoc = filePicker.getFilePath();
        ArrayList<KeyPoint> firstImageKeypoints = imageAnalyser.getKeyPoints(firstImageDoc);
        ArrayList<KeyPoint> secondImageKeypoints = imageAnalyser.getKeyPoints(secondImageDoc);
        ArrayList<ArrayList<KeyPoint>> pairedKeypoints = imageAnalyser.pairKeypoints(firstImageKeypoints,secondImageKeypoints);
//        NeighbourhoodAnalyser neighbourhoodAnalyser = new NeighbourhoodAnalyser(pairedKeypoints);
//        int neighbourhoodSize = (int)pairedKeypoints.size()/50;
//        ArrayList<ArrayList<KeyPoint>> coherentPairs = neighbourhoodAnalyser.coherentPairs(neighbourhoodSize, 0.6);
//        double coherence = (double) coherentPairs.size() / (double) firstImageKeypoints.size();
//        System.out.println(coherentPairs.size());
//        System.out.println(firstImageKeypoints.size());
//        System.out.println("COHERENCE: " + coherence);

        RANSAC ransac = new RANSAC(pairedKeypoints, smallRadius, largeRadius, errorThreshold, 100);
        ArrayList<ArrayList<KeyPoint>> matchingPairs = ransac.getMatchingPairs(1);




    }
}
