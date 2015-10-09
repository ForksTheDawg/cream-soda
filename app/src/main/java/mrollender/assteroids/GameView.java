package mrollender.assteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private VCircle player;
    private VRope rope;
    public static float cursorX;
    public static float cursorY;
    public boolean cursorDown = false;

    private static final double ticks = 60D;
    private static final double tickTime = 1000000000 / ticks;
    private static double delta = 0D;
    private long lastTime = System.nanoTime();
    private VRope controlRope;
    private VPoint controlPoint;
    public static Scene scene;
    protected static Bitmap blackholebmp;
    private static Point size = new Point();

    private Organism testOrganism = new Organism(500, 500, Organism.TYPE_CIRCULAR);

    public double controlsX;
    public double controlsY;

    public GameView(Context context) {
        super(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(size);
        GUI.init(context);
        loadBmp();

        scene = new Scene();
        scene.setBoundary(new Boundary(0, 0, 3000, 3000));
        //

        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        player = new VCircle(0, 0, 200, 5, false);
        player.generateStructure();
        player.setColor(Color.WHITE);
        player.armPoint(1);
        player.armPoint(3);
        player.setParticleMass(2.0);
        player.getAnchor().setDrag(0.5f);
        scene.addStructure(player);
        scene.setTarget(player);
        testOrganism.generateStructure();
        scene.addStructure(testOrganism);
        scene.addFeature(new BlackHole(200, 200));
        controlPoint = new VPoint(0,0, true);
        Camera.setSize(size);
        Camera.setFocus(player);
        Camera.attachScene(scene);
        //rope = new VRope(200,200,20,20);
        //rope.generateStructure();
    }

    private void loadBmp() {
        blackholebmp = BitmapFactory.decodeResource(getResources(), R.drawable.blackhole);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onMouseDown(event);
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            cursorX = event.getX();
            cursorY = event.getY();
        }

        if(event.getAction()==MotionEvent.ACTION_UP) {

            onMouseUp(event);
        }
        return true;
    }

    private void onMouseUp(MotionEvent e) {
        cursorDown = false;
    }

    private void onMouseDown(MotionEvent e) {
        cursorDown = true;
        cursorX = e.getX();
        cursorY = e.getY();
        scene.onClick(cursorX, cursorY);
        if(Gun.canFire()){
            Gun.refresh();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        long thisTime = System.nanoTime();
        delta += (thisTime - lastTime) / tickTime;
        lastTime = thisTime;
        while(delta >=1) {
            delta--;
            Gun.updateFireRate(1);
            update();
        }

        canvas.drawColor(Integer.parseInt("0D47A1", 16)+0xFF000000);

        //Camera.draw(canvas);
        drawHud(canvas);
    }

    public static int getScreenWidth() {
        return size.x;
    }

    public static int getScreenHeight() {
        return size.y;
    }

    private void drawHud(Canvas canvas) {
        GUIHud.render(canvas);
    }

    private void update() {
        //controlsX = MainActivity.xSensor * -25;
        //controlsY = MainActivity.ySensor * -25;
        player.move(controlsX, controlsY);
        Camera.update();
        scene.update();
    }

    public static double getDelta() {
        return delta;
    }

}