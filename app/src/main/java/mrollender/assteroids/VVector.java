package mrollender.assteroids;

/**
 * Created by student on 7/29/2015.
 */

//adapted from http://codeflow.org/entries/2010/sep/01/hard-constraints-easy-solutions/vector.js
//article by Florian Boesch @ http://codeflow.org/entries/2010/sep/01/hard-constraints-easy-solutions/

public class VVector {
    public double x;
    public double y;
    public VVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public VVector isub(VVector other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public VVector sub(VVector other) {
        return new VVector(this.x - other.x, this.y - other.y);
    }

    public VVector iadd(VVector other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }
    public VVector add(VVector other) {
        return new VVector(this.x + other.x, this.y + other.y);
    }
    public VVector imul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }
    public VVector mul(double scalar) {
        return new VVector(this.x*scalar, this.y*scalar);
    }
    public VVector idiv(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }
    public VVector div(double scalar) {
        return new VVector(this.x / scalar, this.y / scalar);
    }
    public VVector normalized(){
        double x = this.x;
        double y = this.y;
        double length = Math.sqrt(x*x + y*y);
        if(length>0) {
            return new VVector(x/length, y/length);
        } else {
            return new VVector(0,0);
        }
    }
    public VVector normalize() {
        double x = this.x;
        double y = this.y;
        double length = Math.sqrt(x*x + y*y);
        if(length > 0) {
            this.x = x/length;
            this.y = y/length;
        }
        return this;
    }

    public double length() {
        return Math.sqrt(this.x*this.x + this.y*this.y);
    }

    public double distance(VVector other) {
        double x = this.x - other.x;
        double y = this.y - other.y;
        return Math.sqrt(x*x + y*y);
    }

    public VVector copy() {
        return new VVector(this.x, this.y);
    }
    public void zero() {
        this.x = 0;
        this.y = 0;
    }
}
