package mrollender.assteroids;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by student on 7/20/2015.
 */
public class VCircle extends VStructure{
    protected int sides;
    protected double radius;
    protected boolean anchor;
    public VCircle(double x, double y, double radius, int sides, boolean anchor) {
        this.sides = sides;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.anchor = anchor;
    }


    public void generateStructure() {
        constructPoint(new VPoint(x,y));
         for(int i=1; i<sides+1; i++) {
             double angle = Math.PI * 2.0 / (double)sides * (double)i;
             constructPoint(new VPoint(radius * Math.cos(angle) + x, radius * Math.sin(angle) + y));
             if(i!=1) constructSpring(new VSpring(points.get(i), points.get(i - 1)));
            constructSpring(new VSpring(points.get(0), points.get(i)));
         }
        constructSpring(new VSpring(points.get(1), points.get(sides)));
        this.getAnchor().setFixed(anchor);
    }

    public int getRadiusPoint(int i) {
        return i + 1;
    }
}
