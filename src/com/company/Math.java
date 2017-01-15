package com.company;

/**
 * Created by agnie on 10/15/2016.
 */
class Math {

    static double euclideanDistance(int[] p, int[] q) {
        if (p.length == q.length) {
            double sum = 0;
            for (int i = 0; i < p.length; i++) {
                double difference = p[i] - q[i];
                sum = sum + difference * difference;
            }
            return java.lang.Math.sqrt(sum);
        }
        return 0;
    }

    static double euclideanDistance(KeyPoint p, KeyPoint q) {
        double xDistance = p.getX() - q.getX();
        double yDistance = p.getY() - q.getY();
        return java.lang.Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }

}
