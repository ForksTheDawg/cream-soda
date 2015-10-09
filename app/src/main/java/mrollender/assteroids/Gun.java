package mrollender.assteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by student on 7/23/2015.
 */
public class Gun extends VPoint{
    private static Paint paint = null;
    private StructureBullets structure;
    private double theta = 0.0;
    private float drawSize = 10.0F;
    private static float fireRate = 50.0F;
    private static float fireCooldown = 0.0F;
    private static final float fireCooldownRate = 1.0F;
    private static final float mountOffset = 3.0f;

    public Gun(double x, double y) {
        super(x, y);
        structure = new StructureBullets();
        GameView.scene.structures.add(structure);
        if(paint == null) {
            paint = new Paint();
            paint.setColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(float xScreen, float yScreen) {
        if(Gun.canFire()) {
            double[] pos = Camera.convertScreenToWorld(xScreen, yScreen);
            this.theta = Math.atan2(pos[1] - this.position.y, pos[0] - this.position.x);
            this.fireBullet(theta);
        }
    }

    private void fireBullet(double theta) {
        structure.fireBullet(this.position.x, this.position.y, theta, estimateXVel(), estimateYVel());

        this.moveA(Bullet.bulletSpeed, theta + Math.PI);
    }

    @Override
    public void draw(Canvas canvas) {
        float[] location = Camera.convertWorldToScreen(position);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate((float)Math.toDegrees(theta), location[0], location[1]);
        RectF rect = new RectF(location[0] - mountOffset, location[1] + this.drawSize / 2, location[0] + drawSize - mountOffset, location[1] - this.drawSize / 2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRoundRect(rect, mountOffset, mountOffset, paint);
        //canvas.drawCircle(location[0]+mountOffset, location[1],drawSize, paint);
        canvas.restore();
    }

    public static void updateFireRate(double delta) {
            if(fireCooldown==fireRate) return;
            fireCooldown += delta * fireCooldownRate;
        if(fireCooldown >= fireRate) { fireCooldown = fireRate; }
    }

    public static float getFirePortion() {
        return fireCooldown / fireRate;
    }

    public static boolean canFire() {
        return fireCooldown==fireRate;
    }

    public static void refresh() {
        fireCooldown = 0F;
    }

}
