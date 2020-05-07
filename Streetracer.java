package sew_projekt;

import processing.core.*;

public class Streetracer extends PApplet {

    int carY = 500;
    int carX = 500;
    int carRotation = 0;
    int speed = 0;
    
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
        speed = 0;
        
    }

    @Override
    public void keyPressed() {
        if (key == 'w') {
            speed = -5;
        }
        if (key == 's') {
            speed = 5;
        }
        if (key == 'd') {
            rotateRight();
        }
        if (key == 'a') {
            rotateLeft();
        }
    }
    
    public void rotateRight() {
        carRotation += 5;
    }
    
    public void rotateLeft() {
        carRotation -= 5;
    }

}
