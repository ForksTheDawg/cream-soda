package mrollender.assteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by student on 7/20/2015.
 */
public class VSpring extends VObject{
    public VPoint pointA;
    public VPoint pointB;
    private double targetLength;
    private double length;
    private double springConst = 1.0;
    public boolean active = true;
    private int color = Color.BLUE;
    Paint paint = new Paint();
    private GLLine line;

    public VSpring(VPoint pointA, VPoint pointB){
        this(pointA, pointB, -1.0);
    }

    public VSpring(VPoint pointA, VPoint pointB, double targetLength) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.targetLength = targetLength;
        if(targetLength==-1.0) {
            this.targetLength = getLength();
        } else {
            this.targetLength = targetLength;
        }
    }

    public void updateV() {
        VVector pos1 = pointA.position;
        VVector pos2 = pointB.position;
        VVector direction = pos2.sub(pos1);
        double length = direction.length();
        double factor = (length-this.targetLength)/(length*2.1);
        VVector correction = direction.mul(factor);

        pointA.correct(correction);
        correction.imul(-1);
        pointB.correct(correction);
    }

    public double getLength() {
        return pointA.distanceTo(pointB);
    }

    public double getAngle() {
        return pointA.angleTo(pointB);
    }

    public void draw(Canvas canvas) {
        paint.setColor(this.color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public void draw(float[] mvpMatrix) {
        line = new GLLine();
        line.SetVerts((float)this.pointA.position.x, (float)this.pointA.position.y, 0.0f,(float)this.pointB.position.x, (float)this.pointB.position.y, 0.0f);
        line.SetColor(1.0f, 1.0f, 1.0f, 1.0f);
        line.draw(mvpMatrix);
    }

    public void updateB() {
        pointB.updateV();
    }

    public void updateA() {
        pointA.updateV();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void onClick(float xScreen, float yScreen) { return; }
}
