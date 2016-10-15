package com.company;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by agnie on 10/13/2016.
 */
public class ImageAnalyser {

    ArrayList<KeyPoint> getKeyPoints(File file){
        BufferedReader reader = null;
        ArrayList<KeyPoint> keyPoints = new ArrayList<KeyPoint>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String lineArray[] = line.split("\\s+");
                if (lineArray.length>1){
                    double x = Double.parseDouble(lineArray[0]);
                    double y = Integer.parseInt(lineArray[1]);
                    KeyPoint keyPoint = new KeyPoint(x,y);
                    for (int i=5; i<lineArray.length; i++){
                        keyPoint.setTrait(i-5, Integer.parseInt(lineArray[i]));
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

    ArrayList<ArrayList<KeyPoint>> pairKeypoints(ArrayList<KeyPoint> firstImageKeyPoints, ArrayList<KeyPoint> secondImageKeypoints){
        ArrayList<ArrayList<KeyPoint>> pairedKeypoints = new ArrayList<ArrayList<KeyPoint>>();
        for (int i=0; i<firstImageKeyPoints.size(); i++){
            double minimum = euclideanDistance(firstImageKeyPoints.get(i).getTraits(), secondImageKeypoints.get(0).getTraits());
            KeyPoint match = secondImageKeypoints.get(0);
            boolean obscure = false;
            for (int j=1; i<secondImageKeypoints.size(); j++){
                double distance = euclideanDistance(firstImageKeyPoints.get(i).getTraits(), secondImageKeypoints.get(j).getTraits());
                if (distance < minimum){
                    obscure = false;
                    minimum = distance;
                    match = secondImageKeypoints.get(j);
                }
                else if (distance==minimum){
                    obscure = true;
                }
            }
            if (!obscure){
                ArrayList<KeyPoint> pair = new ArrayList<KeyPoint>();
                pair.add(firstImageKeyPoints.get(i));
                pair.add(match);
                pairedKeypoints.add(pair);
            }
        }
        return pairedKeypoints;
    }

    private double euclideanDistance(int[] p, int[] q){
        if (p.length == q.length){
            double sum=0;
            for (int i=0; i<p.length; i++){
                double difference = p[i] - q[i];
                sum = sum + difference*difference;
            }
            return Math.sqrt(sum);
        }
        return 0;
    }



}
