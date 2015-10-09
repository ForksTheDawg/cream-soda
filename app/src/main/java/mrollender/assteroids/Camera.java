package mrollender.assteroids;

import android.graphics.Canvas;
import android.graphics.Point;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by student on 7/22/2015.
 */
public class Camera {
    private static double lead;
    private static VStructure focus;
    private static double x;
    private static double y;
    private static double zoom = 1;
    private static double targetZoom;
    private static Scene scene;
    private static Point size;

    public Camera() {
        this.lead = 5.0;
        targetZoom = zoom;
    }

    public static void init() {
        Camera.lead = 5.0;
        Camera.targetZoom = zoom = 10;
    }

    public static void update() {
        if(Camera.focus == null) {
            return;
        }
        VPoint anchor = focus.getAnchor();
        Camera.targetZoom =  1 / (1.0 + Math.log(anchor.estimateSpeed() + 3)*2 - 1) + 0.4;
        zoom += (targetZoom - zoom) / 10;
        Camera.x = anchor.position.x - Camera.size.x / 2 / Camera.zoom;
        Camera.y = anchor.position.y - Camera.size.y / 2 / Camera.zoom;
    }

    public static void setSize(Point size) {
        Camera.size = size;
    }

    public static float[] convertWorldToScreen(double x, double y) {
        return new float[]{(float)((x-Camera.x)*zoom), (float)((y-Camera.y)*zoom)};
    }

    public static float[] convertWorldToScreen(VVector vector) {
        return convertWorldToScreen(vector.x, vector.y);
    }

    public static double[] convertScreenToWorld(float x, float y) {
        return new double[] {x/zoom + Camera.x, y/zoom + Camera.y};
    }

    public static void setFocus(VStructure focus) {
        Camera.focus = focus;
    }

    public static void setLead(double lead) {
        Camera.lead = lead;
    }

    public static void attachScene(Scene scene) {
        Camera.scene = scene;
    }

    public static void releaseScene() {
        Camera.scene = null;
    }

    public static void draw(float[] mvpMatrix) {
        for(int i = 0; i<scene.structures.size(); i++) {
            scene.structures.get(i).draw(mvpMatrix);
        }
        for(int i = 0; i<scene.blackholes.size(); i++) {
            //scene.blackholes.get(i).draw(gl);
        }
        scene.getBoundary().draw(mvpMatrix);
    }

    public static double getZoom() {
        return Camera.zoom;
    }

    //TODO set camera position relative to focus
}
