package mrollender.assteroids;

import android.graphics.Canvas;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by student on 7/21/2015.
 */



public abstract class VStructure {
    public ArrayList<VPoint> points = new ArrayList<VPoint>();
    public ArrayList<VSpring> springs = new ArrayList<VSpring>();
    protected double x;
    protected double y;
    public abstract void generateStructure();
    public void updateV() {
        for(int i=0; i<springs.size();i++) {
            springs.get(i).updateV();
        }
        for(int i=0; i<points.size();i++) {
            points.get(i).updateV();
        }
    }
    public void draw(float[] mvpMatrix){
        for(int i=0; i<springs.size();i++) {
            springs.get(i).draw(mvpMatrix);
        }
        for(int i=0; i<points.size();i++) {
            //points.get(i).draw(gl);
        }
    }

    public VPoint getAnchor() {
        return points.get(0);
    }

    public void appendStructure(VStructure structure) {
        this.points.addAll(structure.points);
        this.springs.addAll(structure.springs);
    }

    protected void constructPoint(VPoint point) {
        point.setParent(this);
        points.add(point);
    }

    protected void constructSpring(VSpring spring) {
       spring.setParent(this);
       springs.add(spring);
    }

    public int getSpringIndex(VSpring spring) throws Error{
        int index = springs.indexOf(spring);
        if(index==-1) {
            throw(new Error("Bad spring index"));
        } else {
            return index;
        }
    }

    public int getPointIndex(VPoint point) throws Error{
        int index = points.indexOf(point);
        if(index==-1) {
            throw(new Error("Bad point index."));
        } else {
            return index;
        }
    }

    public void ropifyPiece(int springIndex, int segments){
        VSpring spring = springs.get(springIndex);
        VRope rope = new VRope(spring.pointA, spring.pointB, segments);
        rope.generateStructure();
        this.points.remove(getPointIndex(spring.pointA));
        this.points.remove(getPointIndex(spring.pointB));
        this.springs.remove(getSpringIndex(spring));
        this.appendStructure(rope);
    }

    public void ropifyEntire(int segments){
        int size = this.springs.size();
        for(int i = 0; i < size; i++) {
            this.ropifyPiece(size - i - 1, segments);
        }
    }

    public void removeComponents() {
        int size = this.springs.size();
        for(int i = 0; i<size; i++) {
            this.springs.remove(size - i - 1);
        }
        size = this.points.size();
        for(int i = 0; i<size; i++) {
            this.points.remove(size - i - 1);
        }
    }

    public void setTotalMass(double mass) {
        int size = points.size();
        for(int i = 0; i<size;i++) {
            points.get(i).setMass(mass/(double)size);
        }
    }

    public void setParticleMass(double mass){
        int size = points.size();
        for(int i = 0; i<size;i++) {
            points.get(i).setMass(mass);
        }
    }

    public double getTotalMass() {
        int size = points.size();
        double mass = 0;
        for(int i = 0; i<size;i++) {
            mass += points.get(i).getMass();
        }
        return mass;
    }

    public void setColor(int color) {
        for(int i = 0; i<springs.size(); i++) {
            springs.get(i).setColor(color);
        }
    }

    public void move(double x, double y) {
        this.getAnchor().move(new VVector(x,y));
    }

    public void move(VVector movement) {
        this.getAnchor().move(movement);
    }

    public void onClick(float xScreen, float yScreen) {
        for(int i = 0; i<points.size(); i++) {
            points.get(i).onClick(xScreen, yScreen);
        }
        for(int i = 0; i<springs.size(); i++) {
            springs.get(i).onClick(xScreen, yScreen);
        }
    }

    public boolean armPoint(int index) {
        if(!(this.points.get(index) instanceof Gun)) {
            VPoint replacement = points.get(index);
            Gun gun = new Gun(replacement.position.x, replacement.position.y);
            points.set(index, gun);
            for(int i = 0; i<springs.size(); i++) {
                VSpring spring = springs.get(i);
                if(spring.pointA == replacement) {
                    spring.pointA = gun;
                }
                if(spring.pointB == replacement) {
                    spring.pointB = gun;
                }
            }
            return true;
        }
        return false;
    }

    public void armAll() {
        for(int i = 0; i<points.size(); i++) {
            armPoint(i);
        }
    }

    public void destroyPoint(int index) {
        VPoint point = points.get(index);
        for(int i = 0; i<springs.size(); i++) {
            VSpring spring = springs.get(i);
            if(spring.pointA == point || spring.pointB == point) {
                destroySpring(i);
            }
        }
        points.set(index, null);
        points.remove(index);
    }

    public void destroyPoint(VPoint point) {
        try {
            this.destroyPoint(getPointIndex(point));
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public void destroySpring(int index) {
        springs.set(index, null);
        springs.remove(index);
    }

    public void destroySpring(VSpring spring) {
        this.destroySpring(getSpringIndex(spring));
    }

    public void appendStructureAtPoint(VStructure structure, int index) {
        replacePoint(structure.getAnchor(), index);
        this.appendStructure(structure);
    }

    public void replacePoint(VPoint point, int index) {

        VPoint replacement = points.get(index);
        points.set(index, point);
        for(int i = 0; i<springs.size(); i++) {
            VSpring spring = springs.get(i);
            if(spring.pointA == replacement) {
                spring.pointA = point;
            }
            if(spring.pointB == replacement) {
                spring.pointB = point;
            }
        }
    }
}
