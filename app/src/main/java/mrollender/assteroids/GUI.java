package mrollender.assteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by student on 7/27/2015.
 */
public abstract class GUI {

    private static int screenHeight;
    private static int screenWidth;

    public static void init(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    static final int getScreenHeight() {
        return screenHeight;
    }

    static final int getScreenWidth() {
        return screenHeight;
    }
}
