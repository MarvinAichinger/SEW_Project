package sew_projekt;

import processing.core.*;

public class Route extends PApplet {

    //Vielecke
    public PVector[] routeOut;
    public PVector[] routeIn;

    //Konstruktor
    public Route(PVector[] routeOut, PVector[] routeIn) {
        this.routeOut = routeOut;
        this.routeIn = routeIn;
    }

    //Überprüft ob ein Auto außerhalb eines Vielecks ist
    public boolean outOfPolygon(Car playerCar, PVector[] route) {

        PVector car = new PVector(playerCar.carX, playerCar.carY);

        int onTrack = 0;
        for (int i = 0; i < route.length - 1; i++) {
            car.x = playerCar.carX;
            car.y = playerCar.carY;

            float PX = route[i + 1].x;
            float PY = route[i + 1].y;

            route[i + 1].sub(route[i]);
            car.sub(route[i]);
            float result = (route[i + 1].x * car.y) - (route[i + 1].y * car.x);
            if (result >= 0) {
                onTrack++;
            }
            route[i + 1].x = PX;
            route[i + 1].y = PY;
        }
        car.x = playerCar.carX;
        car.y = playerCar.carY;
        float PX = route[0].x;
        float PY = route[0].y;
        route[0].sub(route[route.length - 1]);
        car.sub(route[route.length - 1]);
        float result = (route[0].x * car.y) - (route[0].y * car.x);
        if (result >= 0) {
            onTrack++;
        }
        route[0].x = PX;
        route[0].y = PY;

        return onTrack != 0;
    }

    //Überprüft ob ein Auto auf der Strecke ist und wenn dies nicht der Fall ist, wird die speed
    //dieses Autos auf 0 gesetzt
    public void routeCollidor(Car playerCar) {
        boolean polygon1 = outOfPolygon(playerCar, routeOut);
        boolean polygon2 = outOfPolygon(playerCar, routeIn);

        if (polygon1 == true || polygon2 == false) {
            playerCar.speed = 0;
        }
    }

    //Die Strecke in einfachen Linien Zeichnen
    public void drawLines(PApplet window) {
        //Außen
        for (int i = 0; i < routeOut.length - 1; i++) {
            window.line(routeOut[i].x, routeOut[i].y, routeOut[i + 1].x, routeOut[i + 1].y);
        }
        window.line(routeOut[routeOut.length - 1].x, routeOut[routeOut.length - 1].y, routeOut[0].x, routeOut[0].y);

        //Innen
        for (int i = 0; i < routeIn.length - 1; i++) {
            window.line(routeIn[i].x, routeIn[i].y, routeIn[i + 1].x, routeIn[i + 1].y);
        }
        window.line(routeIn[routeIn.length - 1].x, routeIn[routeIn.length - 1].y, routeIn[0].x, routeIn[0].y);

    }

}
