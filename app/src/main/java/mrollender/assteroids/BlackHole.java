package mrollender.assteroids;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by student on 7/22/2015.
 */
public class BlackHole extends VPoint {
    private Paint paint = new Paint();
    private static int height;
    private static int width = 10;
    public BlackHole(double x, double y) {
        super(x,y,true);
        this.setFixed(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3.0f);
    }

    public static void LoadBitmap() {

    }

    @Override
    public void draw(Canvas canvas) {
        float[] coords = Camera.convertWorldToScreen(position.x,position.y);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.scale((float) Camera.getZoom(), (float) Camera.getZoom(), coords[0], coords[1]);

        canvas.drawCircle(coords[0], coords[1], width, paint);
        canvas.restore();
        //TODO load the blackhole png image draw it instead of dot
    }
}

