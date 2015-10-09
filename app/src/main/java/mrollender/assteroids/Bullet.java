package mrollender.assteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by student on 7/23/2015.
 */
public class Bullet extends VPoint {
    public static double bulletSpeed = 25.0;
    private static Paint paint = null;
    public Bullet(double x, double y) {
        super(x,y);
        this.drag = 1.0;
        if(paint == null) {
            paint = new Paint();
            paint.setColor(Color.WHITE);
        }
    }
    @Override
    public void draw(Canvas canvas) {
        float[] location = Camera.convertWorldToScreen(position);
        canvas.drawCircle(location[0], location[1], 5, paint);
    }
    @Override
    protected void collideLeftBound(double bound) {
        this.parent.destroyPoint(this);
    }

    @Override
    protected void collideLowerBound(double bound) {
        this.parent.destroyPoint(this);
    }

    @Override
    protected void collideRightBound(double bound) {
        this.parent.destroyPoint(this);
    }

    @Override
    protected void collideUpperBound(double bound) {
        this.parent.destroyPoint(this);
    }
}
