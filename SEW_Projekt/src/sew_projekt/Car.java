package sew_projekt;

import processing.core.*;

public class Car extends PApplet {

    //Position, Speed, Rotation
    float carY = 500;
    float carX = 500;
    int carRotation = 0;
    float speed = 0;

    //Konstruktor
    public Car() {
    }

    //Beweget das Auto und berechnet neue Position
    public void moveCar(PApplet window, PImage carSprite) {
        window.pushMatrix();
        window.translate(carX, carY);
        window.rotate(radians(carRotation));
        window.translate(0, speed);
        window.image(carSprite, -10, -20, 20, 40);
        window.popMatrix();

        carX += Math.sin(Math.toRadians(carRotation + 180)) * speed;
        carY += Math.cos(Math.toRadians(carRotation)) * speed;

    }

    //Beschleunigen
    public void accelerate() {
        if (speed < -10) {
        } else {
            speed -= 0.1;
        }
    }

    //Bremsen
    public void brake() {
        if (speed > 2) {
        } else {
            if (speed < 0) {
                speed += 0.3;
            } else {
                speed += 0.1;
            }
        }
    }

    //Rechts Lenken
    public void turnRight() {
        if (speed != 0) {
            carRotation += 5;
        }
    }

    //Links Lenken
    public void turnLeft() {
        if (speed != 0) {
            carRotation -= 5;
        }
    }

    //Das Auto wird langsam langsamer
    public void roll() {
        if (speed > 0.5 || speed < -0.5) {
            if (speed > 0) {
                speed -= 0.1;
            }
            if (speed < 0) {
                speed += 0.1;
            }
        } else {
            speed = 0;
        }
    }

}
