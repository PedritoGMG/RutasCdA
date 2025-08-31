package com.example.rutascda;

public class Ubicacion {
    private double x;
    private double y;

    public Ubicacion(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Ubicacion(String x, String y) {
        ParseUbicacion(x,y);
    }
    public Ubicacion(String xy) {
        String[] ubicacionSplit = xy.split(" ");
        ParseUbicacion(ubicacionSplit[0],ubicacionSplit[1]);
    }

    private void ParseUbicacion(String x, String y) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean UbicacionisInRadio(Ubicacion other, double radio) {
        double distancia = Math.sqrt(Math.pow(other.getX() - this.getX(), 2) + Math.pow(other.getY() - this.getY(), 2));
        return distancia <= radio;
    }

    @Override
    public String toString() {
        return x+" "+y;
    }
}
