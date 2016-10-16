package com.company;

/**
 * Created by agnie on 10/12/2016.
 */
public class KeyPoint {

    private double x;
    private double y;
    private int[] traits;

    KeyPoint(double x, double y){
        this.x = x;
        this.y = y;
        traits = new int[128];
    }

    int[] getTraits(){
        return traits;
    }

    void setTrait(int index, int trait){
        traits[index] = trait;
    }

    int getTrait(int index){
        return traits[index];
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

}
