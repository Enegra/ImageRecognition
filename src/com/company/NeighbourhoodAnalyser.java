package com.company;

import com.company.util.Math;

import java.util.ArrayList;

/**
 * Created by agnie on 10/15/2016.
 */
public class NeighbourhoodAnalyser {

    private ArrayList<ArrayList<KeyPoint>> pairedKeyPoints;

    NeighbourhoodAnalyser(ArrayList<ArrayList<KeyPoint>> pairedKeyPoints){
        this.pairedKeyPoints = pairedKeyPoints;
    }


    ArrayList<ArrayList<KeyPoint>> searchNeighbours(ArrayList<KeyPoint> chosenPair, int neighbourhoodSize, int side){
        ArrayList<ArrayList<KeyPoint>> neighbours = new ArrayList<ArrayList<KeyPoint>>(neighbourhoodSize);
        ArrayList<Double> distances = new ArrayList<Double>(pairedKeyPoints.size());
        KeyPoint chosenPoint = chosenPair.get(side);
        for (int i=0; i<pairedKeyPoints.size(); i++){
            double distance = Math.euclideanDistance(pairedKeyPoints.get(i).get(side), chosenPoint);
            distances.add(distance);
        }
        quickSort(distances);
        for (int i=1; i<neighbourhoodSize+1; i++){
            neighbours.add(pairedKeyPoints.get(i));
        }
        return neighbours;
    }

    void quickSort(ArrayList<Double> unsortedArray) {
        quickSort(unsortedArray, pairedKeyPoints, 0, unsortedArray.size() - 1);
    }

    private void quickSort(ArrayList<Double> unsortedArray, ArrayList<ArrayList<KeyPoint>> keyPoints, int left, int right) {
        if (right <= left) {
            return;
        }
        int i = left, j = right;
        double pivot = unsortedArray.get(left + (right - left) / 2);
        while (i <= j) {
            while (unsortedArray.get(i) < pivot) {
                i++;
            }
            while (unsortedArray.get(j) > pivot) {
                j--;
            }
            if (i <= j) {
                double temp = unsortedArray.get(i);
                ArrayList<KeyPoint> tempPair = keyPoints.get(i);
                unsortedArray.set(i, unsortedArray.get(j));
                keyPoints.set(i, keyPoints.get(j));
                unsortedArray.set(j,temp);
                keyPoints.set(i,tempPair);
                i++;
                j--;
            }
        }
        if (left < j) {
            quickSort(unsortedArray, keyPoints, left, j);
        }
        if (i < right) {
            quickSort(unsortedArray, keyPoints, i, right);
        }
    }

    boolean isCoherent(ArrayList<KeyPoint> pair, int neighbourhoodSize, double threshold){
        KeyPoint firstKeypoint = pair.get(0);
        KeyPoint secondKeypoint = pair.get(1);
        ArrayList<ArrayList<KeyPoint>> firstPointNeighbours = searchNeighbours(pair, neighbourhoodSize, 0);
        ArrayList<ArrayList<KeyPoint>> secondPointNeighbours = searchNeighbours(pair, neighbourhoodSize, 1);
        ArrayList<ArrayList<KeyPoint>> commonNeigbours = (ArrayList<ArrayList<KeyPoint>>) firstPointNeighbours.clone();
        commonNeigbours.retainAll(secondPointNeighbours);
        double coherence = commonNeigbours.size()/firstPointNeighbours.size();
        return coherence>=threshold;
    }

    ArrayList<ArrayList<KeyPoint>> coherentPairs(ArrayList<ArrayList<KeyPoint>> pairedKeypoints, int neighbourhoodSize, double threshold){
        ArrayList<ArrayList<KeyPoint>> coherentPairs = new ArrayList<ArrayList<KeyPoint>>();
        for (ArrayList<KeyPoint> pair : pairedKeypoints){
            if (isCoherent(pair, neighbourhoodSize, threshold)){
                coherentPairs.add(pair);
            }
        }
        return coherentPairs;
    }

}
