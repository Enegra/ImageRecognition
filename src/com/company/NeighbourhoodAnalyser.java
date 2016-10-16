package com.company;

import com.company.util.Math;

import java.util.ArrayList;

/**
 * Created by agnie on 10/15/2016.
 */
public class NeighbourhoodAnalyser {

    ArrayList<KeyPoint> searchNeighbours(ArrayList<KeyPoint> keyPoints, KeyPoint chosenPoint, int neighbourhoodSize){
        ArrayList<Double> distances = new ArrayList<Double>(keyPoints.size());
        for (KeyPoint keyPoint : keyPoints){
            double distance = Math.euclideanDistance(keyPoint, chosenPoint);
            distances.add(distance);
        }
        quickSort(distances, keyPoints);
        return null;
    }

    void quickSort(ArrayList<Double> unsortedArray, ArrayList<KeyPoint> keypoints) {
        quickSort(unsortedArray, keypoints, 0, unsortedArray.size() - 1);
    }

    private void quickSort(ArrayList<Double> unsortedArray, ArrayList<KeyPoint> keyPoints, int left, int right) {
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
                KeyPoint tempPoint = keyPoints.get(i);
                unsortedArray.set(i, unsortedArray.get(j));
                keyPoints.set(i, keyPoints.get(j));
                unsortedArray.set(j,temp);
                keyPoints.set(i,tempPoint);
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

}
