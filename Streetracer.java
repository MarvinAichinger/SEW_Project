package sew_projekt;

import processing.core.*;

public class Streetracer extends PApplet {

    int carY = 500;
    int carX = 500;
    int carRotation = 0;
    int speed = 0;

    boolean left, right, up, down = false;

    @Override
    public void settings() {
        size(1000, 700);
    }

    @Override
    public void setup() {
        background(255);
    }

    @Override
    public void draw() {
        background(255);

        pushMatrix();
        translate(carX, carY);
        rotate(radians(carRotation));
        translate(0, speed);
        fill(0);
        rect(-5, -12, 10, 25);
        popMatrix();

        carX += Math.sin(Math.toRadians(carRotation + 180)) * speed;
        carY += Math.cos(Math.toRadians(carRotation)) * speed;

        if (left) {
            if (speed != 0) {
                carRotation -= 5;
            }
        }

        if (right) {
            if (speed != 0) {
            carRotation += 5;
            }
        }

        if (up) {
            speed = -5;
        }

        if (down) {
            speed = 5;
        }
        
        if (!down && !up) {
            speed = 0;
        }

    }

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

    @Override
    public void keyReleased() {
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
