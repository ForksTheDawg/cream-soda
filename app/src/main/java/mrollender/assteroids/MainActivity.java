package mrollender.assteroids;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

//GL_LINE_LOOP
public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    public static float ySensor;
    public static float xSensor;
    private float[] gravityValues = new float[3];
    private float[] magneticValues = new float[3];

    private GameSurfaceView gameView;
    private static Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);

    }

    private void initialize() {
        if(hasGLES20()) {
            game = new Game();
            gameView = new GameSurfaceView(this);
            this.setContentView(gameView);
        } else {
           //get  new phone ya dingus
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static Game getGame() {
        return game;
    }

    private boolean hasGLES20() {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }

    //TODO unregister listeners when they aren't needed
    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[9];
        float[] I = new float[9];
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravityValues = event.values;
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticValues = event.values;
        }
        float[] orientation = new float[3];
        if(SensorManager.getRotationMatrix(rotationMatrix, I, gravityValues, magneticValues)) {
            SensorManager.getOrientation(rotationMatrix,orientation);
        } else {
            //TODO add failure message ('is your device in freefall?')
        }
        xSensor = orientation[1];
        ySensor = orientation[2];
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
