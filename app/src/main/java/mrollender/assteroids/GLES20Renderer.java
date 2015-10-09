package mrollender.assteroids;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by student on 7/30/2015.
 */
public class GLES20Renderer extends GLRenderer {

    private long lastTime;
    private boolean drawn = false;
    private double delta;
    private static final double ticks = 60D;
    private static final double tickTime = 1000000000 / ticks;

    private final float[] mvpMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public void onCreate(int width, int height, boolean contextLost) {
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        updateScreen(width, height);
    }

    public void onDrawFrame(GL10 notUsed) {
        if(!drawn) {
            drawn = true;
            lastTime = System.nanoTime();
        }
        // Clears the screen and depth buffer.
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);
        long thisTime = System.nanoTime();
        delta += (thisTime - lastTime) / tickTime;
        lastTime = thisTime;
        while(delta >=1) {
            delta--;
            Gun.updateFireRate(1);
            MainActivity.getGame().update();
        }
        // Calculate the projection and view transformation
        GLCamera.updateCameraPos(viewMatrix);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        MainActivity.getGame().draw(mvpMatrix);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        updateScreen(width, height);
    }

    public void updateScreen(int width, int height) {
        GLES20.glClearColor(0.05f, 0.28f, 0.63f, 1f);
        GLES20.glViewport(0, 0, width, height);
        float ratio = ((float)width / (float)height);
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(boolean firstDraw) {
    }
    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader); return shader;
    }
}
