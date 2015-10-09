package mrollender.assteroids;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by student on 7/30/2015.
 */
public class GameSurfaceView extends GLSurfaceView {
    final GLES20Renderer renderer;
    public GameSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        renderer = new GLES20Renderer();
        setRenderer(renderer);
    }
}
