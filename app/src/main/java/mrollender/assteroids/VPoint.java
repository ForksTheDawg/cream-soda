package mrollender.assteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by student on 7/20/2015.
 */
public class VPoint extends VObject{
    private double mass = 2.0;
    private boolean fixed;
    protected double drag = 1.0;
    private Paint paint;
    public VVector position;
    public VVector previous;
    public VVector acceleration;

    public VPoint(double x, double y) {
        this(x,y,false);
    }

    public VPoint(double x, double y, boolean fixed) {
        this.position = new VVector(x, y);
        this.previous = new VVector(x, y);
        this.acceleration = new VVector(0, 0);
        paint = new Paint(Color.WHITE);
    }

    public void move(VVector movement) {
        if(fixed) return;
        this.acceleration.iadd(movement);
    }

    public void moveA(double magnitude, double theta) {
        this.move(new VVector(Math.cos(theta)*magnitude, Math.sin(theta)*magnitude));
    }
    /*
    public void updateV() {
        double xDiff = x-xPrev;
        double yDiff = y-yPrev;
        //Log.d("xChange", x+ "");
        //Log.d("xChange", y+ "");
        xPrev = x;
        yPrev = y;
        x += xDiff;
        y += yDiff;
    }
    */

    public void updateV() {
        VVector position = this.position.mul(2).sub(this.previous).add(this.acceleration);
        this.previous = this.position;
        this.position = position;
        this.acceleration.zero();
        restrain();
    }

    public void draw(Canvas canvas) {
        float[] position = Camera.convertWorldToScreen(this.position);
        canvas.drawCircle(position[0], position[1], 3.0f, new Paint(Color.WHITE));
    }

    public double distanceTo(VPoint point) {
        return Math.sqrt(Math.pow(point.position.x - this.position.x, 2) + Math.pow(point.position.y - this.position.y, 2));
    }

    public double angleTo(VPoint point) {
        return Math.atan2(point.position.y - this.position.y, point.position.x - this.position.x);
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return this.mass;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public double estimateSpeed() {
        return Math.sqrt(Math.pow(estimateXVel(), 2) + Math.pow(estimateYVel(), 2));
    }

    public double estimateXVel() {
        return this.position.x - this.previous.x;
    }

    public double estimateYVel() {
        return this.position.y - this.previous.y;
    }

    public void onClick(float x, float y) { return; }

    public void restrain() {
        double[] bounds = Game.getScene().getBoundary().getBound();
        if(this.position.y < bounds[0]) {
            collideUpperBound(bounds[0]);
        }
        if(this.position.x > bounds[1]) {
            collideRightBound(bounds[1]);
        }
        if(this.position.y > bounds[2]) {
            collideLowerBound(bounds[2]);
        }
        if(this.position.x < bounds[3]) {
            collideLeftBound(bounds[3]);
        }
    }

    protected void collideLeftBound(double bound) {
        this.position.x = bound;
    }

    protected void collideLowerBound(double bound) {
        this.position.y = bound;
    }

    protected void collideRightBound(double bound) {
        this.position.x = bound;
    }

    protected void collideUpperBound(double bound) {
        this.position.y = bound;
    }

    protected void setDrag(float drag) {
        this.drag = drag;
    }

    public void setDebugColor(int color) {
        this.paint.setColor(color);
    }

    public void correct(VVector correction) {
        this.position.iadd(correction);
    }
}
