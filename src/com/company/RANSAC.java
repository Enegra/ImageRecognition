package com.company;

import Jama.Matrix;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by agnie on 10/17/2016.
 */
class RANSAC {

    private ArrayList<ArrayList<KeyPoint>> pairedKeyPoints;
    private double smallRadius, bigRadius;
    private int errorThreshold, numberOfIterations;

    RANSAC(ArrayList<ArrayList<KeyPoint>> pairedKeyPoints, double smallRadius, double bigRadius, int errorThreshold, int numberOfIterations) {
        this.pairedKeyPoints = pairedKeyPoints;
        this.smallRadius = smallRadius;
        this.bigRadius = bigRadius;
        this.errorThreshold = errorThreshold;
        this.numberOfIterations = numberOfIterations;
    }

    ArrayList<ArrayList<KeyPoint>> getMatchingPairs(int transformType) {
        ArrayList<ArrayList<KeyPoint>> matchingPairs = new ArrayList<>();
        Matrix transformModel = getTransformModel(transformType);
        for (ArrayList<KeyPoint> pair : pairedKeyPoints) {
            double error = getErrorValue(pair, transformModel);
            if (error < errorThreshold) {
                matchingPairs.add(pair);
            }
        }
        return matchingPairs;
    }

    Matrix getTransformModel(int transformType) {
        Matrix bestTransformModel = null;
        int highScore = 0;
        for (int i = 0; i < numberOfIterations; i++) {
            Matrix transformModel = getTransform(transformType);
            int score = 0;
            for (ArrayList<KeyPoint> pair : pairedKeyPoints) {
                double errorValue = getErrorValue(pair, transformModel);
                if (errorValue < errorThreshold) {
                    score++;
                }
            }
            if (score > highScore) {
                bestTransformModel = transformModel;
                highScore = score;
            }
        }
        return bestTransformModel;
    }

    private Matrix getTransform(int transformType) {
        if (transformType == 0) {
            return getAffineTransform();
        } else return getPerspectiveTransform();
    }

    private ArrayList<ArrayList<KeyPoint>> getStartingPoints(ArrayList<ArrayList<KeyPoint>> startingPoints) {
        int attempts = 20;
        Random random = new Random();
        if (startingPoints.isEmpty()) {
            ArrayList<KeyPoint> pair = pairedKeyPoints.get(random.nextInt(pairedKeyPoints.size()));
            startingPoints.add(pair);
        } else {
            //problem here
            ArrayList<KeyPoint> pair = pairedKeyPoints.get(random.nextInt(pairedKeyPoints.size()));
            while (startingPoints.contains(pair) || !isNear(pair, startingPoints) && attempts > 0) {
                pair = pairedKeyPoints.get(random.nextInt(pairedKeyPoints.size()));
                attempts--;
            }
            if (attempts == 0 && startingPoints.size() < 2) {
                startingPoints.clear();
                getStartingPoints(startingPoints);
            }
            startingPoints.add(pair);
        }
        return startingPoints;
    }

    private boolean isNear(ArrayList<KeyPoint> pair, ArrayList<ArrayList<KeyPoint>> otherPoints) {
        double smallRadius2 = Math.pow(smallRadius, 2);
        double bigRadius2 = Math.pow(bigRadius, 2);
        for (int i = 0; i < otherPoints.size(); i++) {
            double xDistanceA = Math.pow(pair.get(0).getX() - otherPoints.get(i).get(0).getX(), 2);
            double xDistanceB = Math.pow(pair.get(1).getX() - otherPoints.get(i).get(1).getX(), 2);
            double yDistanceA = Math.pow(pair.get(0).getY() - otherPoints.get(i).get(0).getY(), 2);
            double yDistanceB = Math.pow(pair.get(1).getY() - otherPoints.get(i).get(1).getY(), 2);
            double distanceA = xDistanceA + yDistanceA;
            double distanceB = xDistanceB + yDistanceB;
            if (smallRadius2 >= distanceA || bigRadius2 <= distanceA || smallRadius2 >= distanceB || bigRadius2 <= distanceB) {
                return false;
            }
        }
        return true;
    }

    private double getErrorValue(ArrayList<KeyPoint> pair, Matrix transformMatrix) {
        double[][] pointOneMatrixData = {{pair.get(0).getX()}, {pair.get(0).getY()}, {1}};
        Matrix pointOneMatrix = new Matrix(pointOneMatrixData);
        Matrix calculatedPointTwoMatrix = transformMatrix.times(pointOneMatrix);
        double errorValue = Math.sqrt(Math.pow(calculatedPointTwoMatrix.get(0, 0) - pair.get(1).getX(), 2) + Math.pow(calculatedPointTwoMatrix.get(1, 0) - pair.get(1).getY(), 2));
        return errorValue;
    }

    private Matrix getAffineTransform() {
        ArrayList<Matrix> affineMatrices = getAffineData();
        while (affineMatrices.get(0).det() == 0) {
            affineMatrices = getAffineData();
        }
        Matrix firstMatrix = affineMatrices.get(0).inverse();
        Matrix unknowns = firstMatrix.times(affineMatrices.get(1));
        double[][] affineMatrixData = {
                {unknowns.get(0, 0), unknowns.get(1, 0), unknowns.get(2, 0)},
                {unknowns.get(3, 0), unknowns.get(4, 0), unknowns.get(5, 0)},
                {0, 0, 1}
        };
        Matrix affineMatrix = new Matrix(affineMatrixData);
        return affineMatrix;
    }

    private Matrix getPerspectiveTransform() {
        ArrayList<Matrix> perspectiveMatrices = getPerspectiveData();
        while (perspectiveMatrices.get(0).det() == 0) {
            perspectiveMatrices = getPerspectiveData();
        }
        Matrix firstMatrix = perspectiveMatrices.get(0).inverse();
        Matrix unknowns = firstMatrix.times(perspectiveMatrices.get(1));
        double[][] transformMatrixData = {
                {unknowns.get(0, 0), unknowns.get(1, 0), unknowns.get(2, 0)},
                {unknowns.get(3, 0), unknowns.get(4, 0), unknowns.get(5, 0)},
                {unknowns.get(6, 0), unknowns.get(7, 0), 1},
        };
        Matrix transformMatrix = new Matrix(transformMatrixData);
        return transformMatrix;
    }

    private ArrayList<Matrix> getAffineData() {
        ArrayList<ArrayList<KeyPoint>> startingPoints = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            startingPoints = getStartingPoints(startingPoints);
        }
        double[][] firstMatrixData = {
                {startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getY(), 1, 0, 0, 0},
                {startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getY(), 1, 0, 0, 0},
                {startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getY(), 1, 0, 0, 0},
                {0, 0, 0, startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getY(), 1},
                {0, 0, 0, startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getY(), 1},
                {0, 0, 0, startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getY(), 1},

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
        ArrayList<Matrix> matrices = new ArrayList<>();
        matrices.add(firstMatrix);
        matrices.add(secondMatrix);
        return matrices;
    }

    private ArrayList<Matrix> getPerspectiveData() {
        ArrayList<ArrayList<KeyPoint>> startingPoints = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            startingPoints = getStartingPoints(startingPoints);
        }
        double[][] firstMatrixData = {
                {startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getY(), 1, 0, 0, 0, -startingPoints.get(0).get(1).getX() * startingPoints.get(0).get(0).getX(), -startingPoints.get(0).get(1).getX() * startingPoints.get(0).get(0).getY()},
                {startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getY(), 1, 0, 0, 0, -startingPoints.get(1).get(1).getX() * startingPoints.get(1).get(0).getX(), -startingPoints.get(1).get(1).getX() * startingPoints.get(1).get(0).getY()},
                {startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getY(), 1, 0, 0, 0, -startingPoints.get(2).get(1).getX() * startingPoints.get(2).get(0).getX(), -startingPoints.get(2).get(1).getX() * startingPoints.get(2).get(0).getY()},
                {startingPoints.get(3).get(0).getX(), startingPoints.get(3).get(0).getY(), 1, 0, 0, 0, -startingPoints.get(3).get(1).getX() * startingPoints.get(3).get(0).getX(), -startingPoints.get(3).get(1).getX() * startingPoints.get(3).get(0).getY()},
                {0, 0, 0, startingPoints.get(0).get(0).getX(), startingPoints.get(0).get(0).getY(), 1, -startingPoints.get(0).get(1).getY() * startingPoints.get(0).get(0).getX(), -startingPoints.get(0).get(1).getY() * startingPoints.get(0).get(0).getY()},
                {0, 0, 0, startingPoints.get(1).get(0).getX(), startingPoints.get(1).get(0).getY(), 1, -startingPoints.get(1).get(1).getY() * startingPoints.get(1).get(0).getX(), -startingPoints.get(1).get(1).getY() * startingPoints.get(1).get(0).getY()},
                {0, 0, 0, startingPoints.get(2).get(0).getX(), startingPoints.get(2).get(0).getY(), 1, -startingPoints.get(2).get(1).getY() * startingPoints.get(2).get(0).getX(), -startingPoints.get(2).get(1).getY() * startingPoints.get(2).get(0).getY()},
                {0, 0, 0, startingPoints.get(3).get(0).getX(), startingPoints.get(3).get(0).getY(), 1, -startingPoints.get(3).get(1).getY() * startingPoints.get(3).get(0).getX(), -startingPoints.get(3).get(1).getY() * startingPoints.get(3).get(0).getY()}
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
        ArrayList<Matrix> matrices = new ArrayList<>();
        matrices.add(firstMatrix);
        matrices.add(secondMatrix);
        return matrices;
    }

}

