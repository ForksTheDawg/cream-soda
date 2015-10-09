package mrollender.assteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by student on 7/27/2015.
 */
public class GUIHud extends GUI {
    private static final int height = 30;
    private static final int width = 100;
    private static Paint linePaint = new Paint(Color.WHITE);
    private static Paint fillPaint = new Paint(Color.WHITE);

    public static void render(Canvas canvas) {
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3.0f);
        linePaint.setColor(Color.WHITE);
        canvas.drawRect((float) (GameView.getScreenWidth() - width), (float) (GameView.getScreenHeight() - height), (float) (GameView.getScreenWidth()), (float) (GameView.getScreenHeight()), linePaint);
        linePaint.setStyle(Paint.Style.FILL);
        canvas.drawRect((float)(GameView.getScreenWidth() - width*Gun.getFirePortion()), (float)(GameView.getScreenHeight() - height), (float)(GameView.getScreenWidth()), (float)(GameView.getScreenHeight()), linePaint);
    }
}
