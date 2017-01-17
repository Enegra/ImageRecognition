package com.company;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by agnie on 10/13/2016.
 */
public class ImageAnalyser {

    private ArrayList<KeyPoint> firstImageKeyPoints, secondImageKeyPoints;
    private ArrayList<ArrayList<KeyPoint>> pairedKeyPoints, coherentPairs, ransacPairs;
    private int coherence;
    private int neighbourTime, ransacTime;

    private ArrayList<KeyPoint> getKeyPoints(File file) {
        BufferedReader reader = null;
        ArrayList<KeyPoint> keyPoints = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String lineArray[] = line.split("\\s+");
                if (lineArray.length > 1) {
                    double x = Double.parseDouble(lineArray[0]);
                    double y = Double.parseDouble(lineArray[1]);
                    KeyPoint keyPoint = new KeyPoint(x, y);
                    for (int i = 5; i < lineArray.length; i++) {
                        keyPoint.setTrait(i - 5, Integer.parseInt(lineArray[i]));
                    }
                    keyPoints.add(keyPoint);
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return keyPoints;
    }

    private ArrayList<ArrayList<KeyPoint>> pairKeypoints(ArrayList<KeyPoint> firstImageKeyPoints, ArrayList<KeyPoint> secondImageKeypoints) {
        ArrayList<ArrayList<KeyPoint>> pairedKeypoints = new ArrayList<>();
        for (int i = 0; i < firstImageKeyPoints.size(); i++) {
            double minimum = Math.euclideanDistance(firstImageKeyPoints.get(i).getTraits(), secondImageKeypoints.get(0).getTraits());
            KeyPoint match = secondImageKeypoints.get(0);
            boolean obscure = false;
            for (int j = 1; j < secondImageKeypoints.size(); j++) {
                double distance = Math.euclideanDistance(firstImageKeyPoints.get(i).getTraits(), secondImageKeypoints.get(j).getTraits());
                if (distance < minimum) {
                    obscure = false;
                    minimum = distance;
                    match = secondImageKeypoints.get(j);
                } else if (distance == minimum) {
                    obscure = true;
                }
            }
            if (!obscure) {
                ArrayList<KeyPoint> pair = new ArrayList<>();
                pair.add(firstImageKeyPoints.get(i));
                pair.add(match);
                pairedKeypoints.add(pair);
            }
        }
        return pairedKeypoints;
    }

    void generateKeyPoints(File imageOne, File imageTwo) {
        String imageOneName = imageOne.getName();
        String imageTwoName = imageTwo.getName();
        String commands[] = new String[2];
        commands[0] = "c:/cygwin/bin/bash -l -c 'extract_features/extract_features.exe -haraff -sift -i extract_features/" + imageOneName + " -DE";
        commands[1] = "c:/cygwin/bin/bash -l -c 'extract_features/extract_features.exe -haraff -sift -i extract_features/" + imageTwoName + " -DE";
        for (String command : commands) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process process = runtime.exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void fetchKeyPoints(File imageOne, File imageTwo) {
        File firstKeyPointsFile = new File(imageOne.getAbsolutePath() + ".haraff.sift");
        File secondKeyPointsFile = new File(imageTwo.getAbsolutePath() + ".haraff.sift");
        while (!(firstKeyPointsFile.exists() && secondKeyPointsFile.exists())) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        firstImageKeyPoints = getKeyPoints(firstKeyPointsFile);
        secondImageKeyPoints = getKeyPoints(secondKeyPointsFile);
        System.out.println(firstImageKeyPoints.size());
        System.out.println(secondImageKeyPoints.size());
        pairedKeyPoints = pairKeypoints(firstImageKeyPoints, secondImageKeyPoints);
    }

    void runNeighbours(int neighbourhoodSize, double coherenceThreshold){
        NeighbourhoodAnalyser neighbourhoodAnalyser = new NeighbourhoodAnalyser(pairedKeyPoints);
        System.out.println(pairedKeyPoints.size());
        neighbourhoodSize = (int)(neighbourhoodSize*pairedKeyPoints.size() * 0.001);
        long beforeTime = System.currentTimeMillis();
        coherentPairs = neighbourhoodAnalyser.coherentPairs(neighbourhoodSize, coherenceThreshold);
        long afterTime = System.currentTimeMillis();
        neighbourTime = (int)(afterTime-beforeTime);
        coherence = coherentPairs.size() / pairedKeyPoints.size();
    }

    void runRansac(int problemSize, int iterationsNumber, double ransacError, int transformType, int heuristic){
        double smallRadius = 0.05 * problemSize;
        double largeRadius = 0.3 * problemSize;
        int errorThreshold = (int)(pairedKeyPoints.size()*ransacError);
        RANSAC ransac = new RANSAC(pairedKeyPoints, smallRadius, largeRadius, errorThreshold, iterationsNumber);
        ransac.setStartPointHeuristic(heuristic);
        long beforeTime = System.currentTimeMillis();
        ransacPairs = ransac.getMatchingPairs(transformType);
        long afterTime = System.currentTimeMillis();
        ransacTime = (int)(afterTime-beforeTime);
    }

    int getCoherence() {
        return coherence;
    }

    ArrayList<KeyPoint> getFirstImageKeyPoints() {
        return firstImageKeyPoints;
    }

    ArrayList<KeyPoint> getSecondImageKeyPoints() {
        return secondImageKeyPoints;
    }

    ArrayList<ArrayList<KeyPoint>> getPairedKeyPoints() {
        return pairedKeyPoints;
    }

    ArrayList<ArrayList<KeyPoint>> getCoherentPairs() {
        return coherentPairs;
    }

    ArrayList<ArrayList<KeyPoint>> getRansacPairs() {
        return ransacPairs;
    }

    int getNeighbourTime(){
        return neighbourTime;
    }

    int getRansacTime(){
        return ransacTime;
    }

}
