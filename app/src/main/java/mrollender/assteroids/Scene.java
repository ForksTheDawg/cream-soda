package mrollender.assteroids;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by student on 7/23/2015.
 */
public class Scene {

    public ArrayList<VStructure> structures = new ArrayList<VStructure>();
    private Boundary boundary;
    public ArrayList<BlackHole> blackholes = new ArrayList<BlackHole>();
    private static VStructure target;
    private VStructure player;
    private double speed = 0.1;

    public static int FEATURE_BLACK_HOLE = 0;

    public Scene() {

    }

    public void addStructure(VStructure structure) {
        this.structures.add(structure);
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    public Boundary getBoundary() {
        return this.boundary;
    }

    public void update() {
        player.getAnchor().move(new VVector(MainActivity.xSensor*speed, MainActivity.ySensor*speed));
        for(int i = 0; i<structures.size(); i++) {
            structures.get(i).updateV();
        }
        /*
        for(int i = 0; i<blackholes.size(); i++) {
            blackholes.get(i).update();
        }
        */
    }

    public VPoint getPointNearest(VPoint testPoint) throws Error{
        VPoint point = null;
        double distance = -1;
        for(int i = 0; i<structures.size(); i++) {
            int size = structures.get(i).points.size();
            for(int j = 0; j<size; j++) {
                VPoint nextPoint = structures.get(i).points.get(j);
                double distanceTest = testPoint.distanceTo(nextPoint);
                if(distance == -1 || distanceTest < distance) {
                    point = nextPoint;
                    distance = distanceTest;
                }
            }
        }
        if(point!=null) {
            return point;
        } else {
            throw new Error("No points found in stage");
        }
    }

    public void onClick(float xScreen, float yScreen) {
        for(int i = 0; i<structures.size(); i++) {
            structures.get(i).onClick(xScreen, yScreen);
        }

    }

    public void addFeature(VPoint feature) {
        if(feature instanceof BlackHole) {
            blackholes.add((BlackHole)feature);
        }
    }

    public void setPlayer(VStructure structure) {
        this.player = structure;
    }

    public void setTarget(VStructure target) {
        this.target = target;
    }

    public VStructure getTarget() {
        return this.target;
    }
}
