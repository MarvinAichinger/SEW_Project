package sew_projekt;

import processing.core.*;

/**
 * Klasse für ein Auto.
 */
public class Car extends PApplet {

    //Aktuelle Position, Geschwindingkeit und Rotation
    float carY = 950;
    float carX = 400;
    int carRotation = 0;
    float speed = 0;
    
    //Werte der Geschwindigkeit, Beschleunigung, Bremsen und Lenkung
    float maxSpeed;
    float acceleration;
    float brake;
    float turnAmount;

    /**
     * Konstruktor
     * @param carX float
     * @param carY float
     * @param carRotation int
     */
    public Car(float carX, float carY, int carRotation, float maxSpeed, float acceleration, float brake, float turnAmount) {
        this.carX = carX;
        this.carY = carY;
        this.carRotation = carRotation;
        
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.brake = brake;
        this.turnAmount = turnAmount;
    }

    /**
     * In dieser Methode wird das Auto in die entsprechende Richtung bewegt und
     * Rotiert, sowie die neue Position auf dem Koordinatensystem berechnet.
     * @param window PAppplet
     * @param carSprite PImage
     */
    public void moveCar(PApplet window, PImage carSprite) {
        window.pushMatrix();
        window.translate(carX, carY);
        window.rotate(radians(carRotation));
        window.translate(0, speed);
        window.image(carSprite, (float) -7.5, (float) -12.5, 15, 25);
        //window.noFill();
        //window.stroke(0);
        //window.rect((float) -7.5, (float) -12.5, 15, 25);
        window.popMatrix();

        carX += Math.sin(Math.toRadians(carRotation + 180)) * speed;
        carY += Math.cos(Math.toRadians(carRotation)) * speed;

    }

    /**
     * Beschleunigt das Auto.
     */
    public void accelerate() {
        if (speed < maxSpeed) {
        } else {
            speed -= acceleration;
        }
    }

    /**
     * Bremst bzw. lässt das Auto Rückwärts fahren.
     */
    public void brake() {
        if (speed > (maxSpeed * (-0.2))) {
        } else {
            if (speed < 0) {
                speed += brake;
            } else {
                speed += acceleration;
            }
        }
    }

    /**
     * Verändert die Rotation des Autos nach rechts.
     */
    public void turnRight() {
        if (speed != 0) {
            carRotation += turnAmount;
        }
    }

    /**
     * Verändert die Rotation des Autos nach links.
     */
    public void turnLeft() {
        if (speed != 0) {
            carRotation -= turnAmount;
        }
    }

    /**
     * Lässt das Auto langsam ausrollen.
     */
    public void roll() {
        if (speed > acceleration || speed < (acceleration * (-1))) {
            if (speed > 0) {
                speed -= acceleration;
            }
            if (speed < 0) {
                speed += acceleration;
            }
        } else {
            speed = 0;
        }
    }
}
