package mrollender.assteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by student on 7/23/2015.
 */
public class Boundary {
    double x;
    double y;
    double height;
    double width;
    Paint paint;

    public Boundary(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public void draw(Canvas canvas) {
        double[] boxCoords = new double[]{this.x - width/2, this.y - height/2, this.x + width/2, this.y - height/2, this.x + width/2, this.y+ height/2, this.x - width/2, this.y + height/2 };
        float[] convertedStart;
        float[] convertedEnd;
        convertedStart = Camera.convertWorldToScreen(boxCoords[0], boxCoords[1]);
        convertedEnd = Camera.convertWorldToScreen(boxCoords[2], boxCoords[3]);
        canvas.drawLine(convertedStart[0], convertedStart[1], convertedEnd[0], convertedEnd[1], paint);

        convertedStart = Camera.convertWorldToScreen(boxCoords[2], boxCoords[3]);
        convertedEnd = Camera.convertWorldToScreen(boxCoords[4], boxCoords[5]);
        canvas.drawLine(convertedStart[0], convertedStart[1], convertedEnd[0], convertedEnd[1], paint);

        convertedStart = Camera.convertWorldToScreen(boxCoords[4], boxCoords[5]);
        convertedEnd = Camera.convertWorldToScreen(boxCoords[6], boxCoords[7]);
        canvas.drawLine(convertedStart[0], convertedStart[1], convertedEnd[0], convertedEnd[1], paint);

        convertedStart = Camera.convertWorldToScreen(boxCoords[6], boxCoords[7]);
        convertedEnd = Camera.convertWorldToScreen(boxCoords[0], boxCoords[1]);
        canvas.drawLine(convertedStart[0], convertedStart[1], convertedEnd[0], convertedEnd[1], paint);
    }

    public void draw(float[] mvpMatrix) {
        float[] boxCoords = new float[]{(float)(this.x - width/2), (float)(this.y - height/2), (float)(this.x + width/2), (float)(this.y - height/2), (float)(this.x + width/2), (float)(this.y+ height/2), (float)(this.x - width/2), (float)(this.y + height/2) };
        GLLine line = new GLLine();
        line.SetColor(1.0f, 1.0f, 1.0f, 1.0f);
        line.SetVerts(boxCoords[0], boxCoords[1], 0f, boxCoords[2], boxCoords[3], 0f);
        line.draw(mvpMatrix);
        line.SetVerts(boxCoords[2], boxCoords[3], 0f, boxCoords[4], boxCoords[5], 0f);
        line.draw(mvpMatrix);
        line.SetVerts(boxCoords[4], boxCoords[5], 0f, boxCoords[6], boxCoords[7], 0f);
        line.draw(mvpMatrix);
        line.SetVerts(boxCoords[6], boxCoords[7], 0f, boxCoords[0], boxCoords[1], 0f);
        line.draw(mvpMatrix);
    }

    /*
     * 0 = top, 1 = right, 2= bottom, 3=left
     */
    public double[] getBound() {
        return new double[] {this.y - this.height / 2, this.x + this.width / 2, this.y + this.height / 2, this.x - this.width / 2};
    }
}
