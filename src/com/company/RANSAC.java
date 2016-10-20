package com.company;

import Jama.Matrix;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by agnie on 10/17/2016.
 */
class RANSAC {

    ArrayList<ArrayList<KeyPoint>> pairedKeyPoints;
    double smallRadius, bigRadius;
    int errorThreshold, numberOfIterations;

    RANSAC(ArrayList<ArrayList<KeyPoint>> pairedKeyPoints, double smallRadius, double bigRadius, int errorThreshold, int numberOfIterations){
        this.pairedKeyPoints = pairedKeyPoints;
        this.smallRadius = smallRadius;
        this.bigRadius = bigRadius;
        this.errorThreshold = errorThreshold;
        this.numberOfIterations = numberOfIterations;
    }

    public Matrix getTransformModel(int transformType){
        Matrix transformModel = null;
        int score = 0;
        if (transformType==0){
            for (int i=0; i<numberOfIterations; i++){
            score = 0;
                transformModel = getAffineTransform();
                for (ArrayList<KeyPoint> pair : pairedKeyPoints){
                    //TODO - ERROR
                }
            }
        }
        else {

        }
        return null;
    }

    ArrayList<ArrayList<KeyPoint>> getStartingPoints(ArrayList<ArrayList<KeyPoint>> startingPoints){
        Random random = new Random();
        if (startingPoints.isEmpty()){
            ArrayList<KeyPoint> pair = pairedKeyPoints.get(random.nextInt(pairedKeyPoints.size()));
            startingPoints.add(pair);
        }
        else {
            ArrayList<KeyPoint> pair = pairedKeyPoints.get(random.nextInt(pairedKeyPoints.size()));
            while (startingPoints.contains(pair) || !isNear(pair, startingPoints)){
                pair = pairedKeyPoints.get(random.nextInt(pairedKeyPoints.size()));
            }
            startingPoints.add(pair);
        }
        return startingPoints;
    }

    private boolean isNear(ArrayList<KeyPoint> pair, ArrayList<ArrayList<KeyPoint>> otherPoints){
        double smallRadius2 = Math.pow(smallRadius,2);
        double bigRadius2 = Math.pow(bigRadius,2);
        for (int i=0; i<otherPoints.size(); i++){
            double xDistanceA = Math.pow(pair.get(0).getX()  - otherPoints.get(i).get(0).getX(),2);
            double xDistanceB = Math.pow(pair.get(1).getX()  - otherPoints.get(i).get(1).getX(),2);
            double yDistanceA =  Math.pow(pair.get(0).getY()  - otherPoints.get(i).get(0).getY(),2);
            double yDistanceB = Math.pow(pair.get(1).getY()  - otherPoints.get(i).get(1).getY(),2);
            double distanceA = xDistanceA + yDistanceA;
            double distanceB = xDistanceB + yDistanceB;
            if (smallRadius2 >= distanceA || bigRadius2 <= distanceA || smallRadius2 >= distanceB || bigRadius2 <= distanceB){
                return false;
            }
        }
        return true;
    }

    private double getErrorValue(){
        return 0;
    }

    private Matrix getAffineTransform(){
        ArrayList<ArrayList<KeyPoint>> startingPoints = new ArrayList<ArrayList<KeyPoint>>();
        for (int i=0; i<3; i++){
            startingPoints = getStartingPoints(startingPoints);
        }
        double[][] firstMatrixData = {
                {startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getX(), 1, 0, 0, 0},
                {startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getX(), 1, 0, 0, 0},
                {startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getX(), 1, 0, 0, 0},
                {0, 0, 0, startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getX(), 1},
                {0, 0, 0, startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getX(), 1},
                {0, 0, 0, startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getX(), 1},

        };
        double[][] secondMatrixData = {
                {startingPoints.get(0).get(1).getX()},
                {startingPoints.get(1).get(1).getX()},
                {startingPoints.get(2).get(1).getX()},
                {startingPoints.get(0).get(1).getY()},
                {startingPoints.get(1).get(1).getY()},
                {startingPoints.get(2).get(1).getY()}
        };
        Matrix firstMatrix = new Matrix(firstMatrixData);
        Matrix secondMatrix = new Matrix(secondMatrixData);
        firstMatrix = firstMatrix.inverse();
        Matrix unknowns = firstMatrix.times(secondMatrix);
        double[][] affineMatrixData = {
                {unknowns.get(0,0), unknowns.get(1,0), unknowns.get(2,0)},
                {unknowns.get(3,0), unknowns.get(4,0), unknowns.get(5,0)},
                {0, 0, 1}
        };
        Matrix affineMatrix = new Matrix(affineMatrixData);
        return affineMatrix;
    }

    private Matrix getPerspectiveTransform(){
        ArrayList<ArrayList<KeyPoint>> startingPoints = new ArrayList<ArrayList<KeyPoint>>();
        for (int i=0; i<4; i++){
            startingPoints = getStartingPoints(startingPoints);
        }
        double[][] firstMatrixData = {
                {startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getX(), 1, 0, 0, 0, -startingPoints.get(0).get(1).getX()*startingPoints.get(0).get(0).getX(), -startingPoints.get(0).get(1).getX()*startingPoints.get(0).get(0).getY()},
                {startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getX(), 1, 0, 0, 0, -startingPoints.get(1).get(1).getX()*startingPoints.get(1).get(0).getX(), -startingPoints.get(1).get(1).getX()*startingPoints.get(1).get(0).getY()},
                {startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getX(), 1, 0, 0, 0, -startingPoints.get(2).get(1).getX()*startingPoints.get(2).get(0).getX(), -startingPoints.get(2).get(1).getX()*startingPoints.get(2).get(0).getY()},
                {startingPoints.get(3).get(0).getX(), startingPoints.get(3).get(0).getX(), 1, 0, 0, 0, -startingPoints.get(3).get(1).getX()*startingPoints.get(3).get(0).getX(), -startingPoints.get(3).get(1).getX()*startingPoints.get(3).get(0).getY()},
                {0, 0, 0, startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getX(), 1, -startingPoints.get(0).get(1).getY()*startingPoints.get(0).get(0).getX(), -startingPoints.get(0).get(1).getY()*startingPoints.get(0).get(0).getY()},
                {0, 0, 0, startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getX(), 1, -startingPoints.get(1).get(1).getY()*startingPoints.get(1).get(0).getX(), -startingPoints.get(1).get(1).getY()*startingPoints.get(1).get(0).getY()},
                {0, 0, 0, startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getX(), 1, -startingPoints.get(2).get(1).getY()*startingPoints.get(2).get(0).getX(), -startingPoints.get(2).get(1).getY()*startingPoints.get(2).get(0).getY()},
                {0, 0, 0, startingPoints.get(3).get(0).getX(), startingPoints.get(3).get(0).getX(), 1, -startingPoints.get(3).get(1).getY()*startingPoints.get(3).get(0).getX(), -startingPoints.get(3).get(1).getY()*startingPoints.get(3).get(0).getY()}
        };
        double[][] secondMatrixData = {
                {startingPoints.get(0).get(1).getX()},
                {startingPoints.get(1).get(1).getX()},
                {startingPoints.get(2).get(1).getX()},
                {startingPoints.get(3).get(1).getX()},
                {startingPoints.get(0).get(1).getY()},
                {startingPoints.get(1).get(1).getY()},
                {startingPoints.get(2).get(1).getY()},
                {startingPoints.get(3).get(1).getY()}
        };
        Matrix firstMatrix = new Matrix(firstMatrixData);
        Matrix secondMatrix = new Matrix(secondMatrixData);
        firstMatrix = firstMatrix.inverse();
        Matrix unknowns = firstMatrix.times(secondMatrix);
        double[][] transformMatrixData = {
                {unknowns.get(0,0), unknowns.get(1,0), unknowns.get(2,0)},
                {unknowns.get(3,0), unknowns.get(4,0), unknowns.get(5,0)},
                {unknowns.get(6,0), unknowns.get(7,0), 1},
        };
        Matrix transformMatrix = new Matrix(transformMatrixData);
        return transformMatrix;
    }

}

