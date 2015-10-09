package mrollender.assteroids;

/**
 * Created by student on 7/20/2015.
 */
public class VCircleSturdy extends VCircle{

    private double ratio;
    public VCircleSturdy(double x, double y, double radius, int sides, boolean anchor, double ratio) {
        super(x,y,radius,sides,anchor);
        this.ratio = ratio;
    }

    public VCircleSturdy(double x, double y, double radius, int sides, boolean anchor) {
        this(x,y,radius,sides,anchor, 0.8);
    }


    public void generateStructure() {
        constructPoint(new VPoint(x,y)); // center point
         for(int i=1; i<sides+1; i++) {
             double angle = Math.PI * 2.0 / (double)sides * (double)i;
             constructPoint(new VPoint(radius * Math.cos(angle) + x, radius * Math.sin(angle) + y)); // outer circle radius
             constructPoint(new VPoint(radius * ratio * Math.cos(angle) + x, radius * ratio * Math.sin(angle) + y)); // inner circle radius
             if(i!=1) {
                 constructSpring(new VSpring(points.get(i*2),points.get(i*2-2)));
                 constructSpring(new VSpring(points.get(i*2-1),points.get(i*2-3)));
                 constructSpring(new VSpring(points.get(i*2-1),points.get(i*2-2)));
             }

             constructSpring(new VSpring(points.get(i*2-1),points.get(i*2)));
             constructSpring(new VSpring(points.get(i*2),points.get(0)));
         }
        constructSpring(new VSpring(points.get(2),points.get(points.size() - 1)));
        constructSpring(new VSpring(points.get(1),points.get(points.size() - 2)));
        constructSpring(new VSpring(points.get(1),points.get(points.size() - 1)));
    }
}
