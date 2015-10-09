package mrollender.assteroids;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by student on 7/30/2015.
 */
public abstract class GLRenderer implements Renderer {

    private boolean mFirstDraw;
    private boolean surfaceCreated;
    private int mWidth;
    private int mHeight;
    private long mLastTime;
    private int mFPS;

    public GLRenderer() {
        mFirstDraw = true;
        surfaceCreated = false;
        mWidth = -1;
        mHeight = -1;
        mLastTime = System.currentTimeMillis();
        mFPS = 0;
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        mWidth = width;
        mHeight = height;

        onCreate(mWidth, mHeight, surfaceCreated);
        surfaceCreated = false;
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
    }
    public abstract void onCreate(int width, int height, boolean contextLost);
    public abstract void onDrawFrame(boolean firstDraw);
}
