package customUtils;

import model.Position;

public class geoUtils {
    
    public static boolean isAlligned(Position[] positions) {
        if (positions.length < 2) {
            return false;
        }
        // obtengo pendiente
        double m = (positions[0].getY() - positions[1].getY()) / (positions[0].getX() - positions[1].getX());
        // inicializo valor
        double value;
        for(int i = 2; i < positions.length; i++) {
            // aplico las nuevas posiciones en la ecuacion de la recta
            value = (positions[i].getY() - positions[0].getY()) - m * (positions[i].getX() - positions[0].getX());
            if (value > 1) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isPositionInPoligon(Position[] polygon, Position pos) {
        Position aux;
        double area = 0;
        for (int i = 0; i < polygon.length; i++) {
            aux = polygon[i];
            polygon[i] = pos;
            area += getArea(polygon);
            polygon[i] = aux;
        }
        return area == getArea(polygon);
    }

    public static double getPerimeter(Position[] pos) {
        double perimeter = 0;
        for (int i = 0, j = pos.length-1; i < pos.length; i++) {
            perimeter += getSide(pos[j], pos[i]);
            j = i;
        }
        return perimeter;
    }

    public static double getArea(Position[] pos) {
        double area = 0;
        int n = pos.length;
        for (int i = 0, j = n-1; i < n; i++) {
            area += (pos[j].getX() + pos[i].getX()) * (pos[j].getY() - pos[i].getY());
            j = i;
        }
        return area / 2;
    }

    public static double getSide(Position pos1, Position pos2) {
        double difX = getDifference(pos1.getX(), pos2.getX());
        double difY = getDifference(pos1.getY(), pos2.getY());
        return Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
    }

    public static double getDifference(double n1, double n2) {
        return n1 > n2 ? n1 - n2 : n2 - n1;      
    }
}