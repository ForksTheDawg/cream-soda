package mrollender.assteroids;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by student on 7/23/2015.
 */
public class StructureBullets extends VStructure {
    private double x;
    private double y;
    private double theta;
    private Bullet bullet;
    private double initX;
    private double initY;
    public StructureBullets() {
        this.generateStructure();
    }

    public void generateStructure() {
        return;
    }

    public void fireBullet(double x, double y, double theta, double xVel, double yVel) {

        theta += Math.PI;
        bullet = new Bullet(x, y);
        bullet.previous.x = x + Math.cos(theta) * Bullet.bulletSpeed /*- xVel*/;
        bullet.previous.y = y + Math.sin(theta) * Bullet.bulletSpeed /*- yVel*/;
        constructPoint(bullet);
    }
}
