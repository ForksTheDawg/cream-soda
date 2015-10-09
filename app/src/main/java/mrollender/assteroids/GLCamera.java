package mrollender.assteroids;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Created by student on 7/30/2015.
 */
public class GLCamera {
    private static float[] pos = new float[2];
    private static VStructure focus;
    public static void setPos(float x, float y, float z) {
        pos = new float[]{x, y, z};
    }

    public static void updateCameraPos(float[] viewMatrix) {
        if(focus==null) return;
        VPoint focusPoint = focus.getAnchor();
        setPos((float)focusPoint.position.x, (float)focusPoint.position.y, 0.0f);
        Matrix.setLookAtM(viewMatrix, 0, pos[0], pos[1], -3, pos[0], pos[1], 0f, 0f, 1.0f, 0.0f);
    }

    public static void setFocus(VStructure focus) {
        GLCamera.focus = focus;
    }
}
