package mrollender.assteroids;

import android.opengl.GLES20;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by student on 7/30/2015.
 */
public class Game {
    private static Scene scene = new Scene();
    public Game() {
        init();
    }

    private void init() {
        VCircle player = new VCircle(0, 0, 1.0, 5, false);
        player.generateStructure();
        player.getAnchor().setDrag(0.5f);
        scene.setBoundary(new Boundary(0, 0, 10, 10));
        scene.addStructure(player);
        Camera.attachScene(scene);
        scene.setPlayer(player);
        scene.setTarget(player);
        GLCamera.setFocus(player);
    }
    public void update() {
        scene.update();
    }
    public void draw(float[] mvpMatrix) {
        Camera.draw(mvpMatrix);
    }

    public static Scene getScene() {
        return scene;
    }
}
