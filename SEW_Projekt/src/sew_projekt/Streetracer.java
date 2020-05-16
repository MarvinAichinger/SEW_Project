package sew_projekt;

import processing.core.*;

public class Streetracer extends PApplet {
    
    Car testCar = new Car();

    //Controls
    boolean left, right, up, down = false;

    //Route
    //außen
    PVector P1 = new PVector(900, 600);
    PVector P2 = new PVector(900, 100);
    PVector P3 = new PVector(100, 100);
    PVector P4 = new PVector(100, 600);
    PVector[] route = {P1, P2, P3, P4};
    //innen
    PVector P5 = new PVector(550, 400);
    PVector P6 = new PVector(550, 300);
    PVector P7 = new PVector(450, 300);
    PVector P8 = new PVector(450, 400);
    PVector[] routeIn = {P5, P6, P7, P8};
    
    Route testRoute = new Route(route, routeIn);    

    //Images
    PImage carSprite;

    @Override
    public void settings() {
        size(1000, 700);
    }

    @Override
    public void setup() {
        background(255);

        //Images
        carSprite = loadImage("Auto_sprite.png");
    }

    @Override
    public void draw() {
        background(255);
        
        testRoute.drawLines(this);
        testCar.moveCar(this, carSprite);
        testRoute.routeCollidor(testCar);
        
        //Pfeiltasten
        if (left) {
            testCar.turnLeft();
        }
        if (right) {
            testCar.turnRight();
        }
        if (up) {
            testCar.accelerate();
        }
        if (down) {
            testCar.brake();
        }
        if (!down && !up) {
            testCar.roll();
        }
    }

    //Taste wird gedrückt
    @Override
    public void keyPressed() {
        if (key == CODED) {
            switch (keyCode) {
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true;
                    break;
                case UP:
                    up = true;
                    break;
                case DOWN:
                    down = true;
                    break;
            }
        }

    }

    //Taste wird Losgelassen
    @Override
    public void keyReleased() {
        //Pfeiltasten
        if (key == CODED) {
            switch (keyCode) {
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
                case UP:
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break;
            }
        }
    }

}
