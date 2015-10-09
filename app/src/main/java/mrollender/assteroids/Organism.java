package mrollender.assteroids;

import android.util.Log;

/**
 * Created by student on 7/29/2015.
 */
public class Organism extends VStructure{
    public static final int TYPE_CIRCULAR = 0;
    public static final int TYPE_CIRCULAR_COMPLEX = 1;
    private double x;
    private double y;
    private int type;
    private double speed;

    public Organism(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.speed = Math.random()*1;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void generateStructure() {

        if(this.type==TYPE_CIRCULAR) {
            VCircle circle = new VCircle(this.x, this.y, Math.random()*100 + 70, (int)Math.ceil(Math.random()*5 + 3), false);
            circle.generateStructure();
            for(int i = 0; i<circle.sides; i++) {

                if(Math.random()*circle.sides < 2) {
                    int index = circle.getRadiusPoint(i);
                    VPoint pointAppend = circle.points.get(index);
                    VCircle outerStructure = new VCircle(pointAppend.position.x, pointAppend.position.y, Math.random()*30+50,(int)Math.ceil(Math.random()*3 + 3), false);
                    outerStructure.generateStructure();
                    outerStructure.getAnchor().setDebugColor(0xffff0000);
                    circle.appendStructureAtPoint(outerStructure, index);
                }


            }
            circle.getAnchor().setDebugColor(0xffffff00);
            this.appendStructure(circle);
        }
        //this.getAnchor().setDrag(0.5f);
        this.getAnchor().setDebugColor(0x00ffff);
    }

    @Override
    public void updateV() {
        /*
        VPoint target = GameView.scene.getTarget().getAnchor();
        double theta = this.getAnchor().angleTo(target);
        target = null;
        this.getAnchor().moveA(this.speed, theta);
        this.getAnchor().setDebugColor(0xffffff00);
        super.updateV();
        */
    }

}
