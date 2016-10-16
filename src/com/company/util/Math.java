package com.company.util;

import com.company.KeyPoint;

/**
 * Created by agnie on 10/15/2016.
 */
public class Math {

    public static double euclideanDistance(int[] p, int[] q){
        if (p.length == q.length){
            double sum=0;
            for (int i=0; i<p.length; i++){
                double difference = p[i] - q[i];
                sum = sum + difference*difference;
            }
            return java.lang.Math.sqrt(sum);
        }
        return 0;
    }

    public static double euclideanDistance(KeyPoint p, KeyPoint q){
        double xDistance = p.getX() - q.getX();
        double yDistance = p.getY() - q.getY();
        return java.lang.Math.sqrt(xDistance*xDistance + yDistance*yDistance);
    }
}
