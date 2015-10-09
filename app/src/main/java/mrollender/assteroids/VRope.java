package mrollender.assteroids;

/**
 * Created by student on 7/21/2015.
 */
public class VRope extends VStructure{
    private int segments;
    private double segmentLength;
    private double theta;
    private VPoint start;
    private VPoint end;

    /*type 1 */
    public VRope(double x, double y, int segments, double segmentLength) {
        this(x, y, segments, segmentLength, 0);
    }

    public VRope(double x, double y, int segments, double segmentLength, double theta) {
        this.x = x;
        this.y = y;
        this.segments = segments;
        this.segmentLength = segmentLength;
        this.theta = theta;
    }
    /* type 2 */
    public VRope(VPoint start, int segments, double segmentLength, double theta) {
        this.start = start;
        this.theta = theta;
        this.segments = segments;
        this.segmentLength = segmentLength;
    }
    /* type 3 */
    public VRope(VPoint start, VPoint end, int segments) {
        this.start = start;
        this.end = end;
        this.segments = segments;
    }

    public void generateStructure() {
        double theta;
        double segmentLength;
        //type 2 (start,segments,segmentLength, theta)
        if(start !=null && end==null) {
            theta = this.theta;
            segmentLength = this.segmentLength;
            for(int i = 0; i<segments - 1; i++) {
                constructPoint(new VPoint(x + segmentLength*(i+1)*Math.cos(theta), y+segmentLength*(i+1)*Math.sin(theta)));
                constructSpring(new VSpring(points.get(i), points.get(i+1)));
            }
        } else
        //type 3 (start,end,segments)
        if(start != null && end !=null) {
            theta = start.angleTo(end);
            segmentLength = start.distanceTo(end) / segments;
            constructPoint(start);
            int i;
            for(i = 0; i<segments - 1; i++) {
                constructPoint(new VPoint(start.position.x + segmentLength*(i+1)*Math.cos(theta), start.position.y+segmentLength*(i+1)*Math.sin(theta)));
                constructSpring(new VSpring(points.get(i), points.get(i+1)));
            }
            constructPoint(end);
            constructSpring(new VSpring(points.get(i), points.get(i+1)));
        } else
        //type 1 (x,y,segment,segmentLength,theta)
        {
            theta = this.theta;
            segmentLength = this.segmentLength;
            constructPoint(new VPoint(x,y));
            for(int i = 0; i<segments; i++) {
                constructPoint(new VPoint(x + segmentLength*(i+1)*Math.cos(theta), y+segmentLength*(i+1)*Math.sin(theta)));
                constructSpring(new VSpring(points.get(i), points.get(i+1)));
            }
        }
    }
}
