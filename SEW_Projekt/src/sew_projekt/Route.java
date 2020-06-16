package sew_projekt;

import processing.core.*;

/**
 * Klasse für eine Strecke.
 */
public class Route extends PApplet {

    //Vielecke
    public PVector[] routeOut;
    public PVector[] routeIn;
    int temp = 0;
    boolean changeSpeed = true;
    int counter = 0;

    //lap counter
    boolean inB1 = false;
    boolean inB2 = false;
    boolean inB3 = false;
    boolean inB12 = false;
    boolean inB22 = false;
    boolean inB32 = false;
    int lapCounter1 = 1;
    int lapCounter2 = 1;

    //Car collision for bug
    PVector lastGoodPos;

    /**
     * Konstruktor
     *
     * @param routeOut PVector[]
     * @param routeIn PVector[]
     */
    public Route(PVector[] routeOut, PVector[] routeIn) {
        this.routeOut = routeOut;
        this.routeIn = routeIn;
    }

    /**
     * Überprüft ob ein Auto auf der Strecke ist und wenn dies nicht der Fall
     * ist, wird die speed dieses Autos auf 0 gesetzt
     *
     * @param playerCar Car
     */
    public void routeCollidor(Car playerCar) {

        PVector OCar = new PVector(playerCar.carX, playerCar.carY);
        PVector nearestIn;
        PVector nearestOut;
        nearestIn = nearestPoint(OCar, routeIn).copy();
        int indexIn = temp;
        nearestOut = nearestPoint(OCar, routeOut).copy();
        int indexOut = temp;
        if (nearestOut.dist(OCar) < nearestIn.dist(OCar)) {
            nearestIn = routeIn[indexOut];
            indexIn = indexOut;
        } else {
            nearestOut = routeOut[indexIn];
            indexOut = indexIn;
        }

        boolean leftOrRight = rightOfVector(OCar, nearestIn, nearestOut);

        boolean result = onTrack(OCar, nearestIn, nearestOut, leftOrRight, indexOut, indexIn);

        if (!result) {
            if (changeSpeed) {
                if (playerCar.speed > 0) {
                    playerCar.speed = -2;
                } else if (playerCar.speed < 0){
                    playerCar.speed = 2;
                }else {
                    playerCar.carX = lastGoodPos.x;
                    playerCar.carY = lastGoodPos.y;
                }
                changeSpeed = false;
            }

        } else {
            counter++;
            if (counter >= 10) {
                counter = 0;
                changeSpeed = true;
            }
            lastGoodPos = OCar.copy();
        }
    }

    /**
     * Überprüft ob das Auto zwischen Zwei Vektoren ist.
     *
     * @param OCar PVector
     * @param nearestIn Pvector
     * @param nearestOut Pvector
     * @param leftOrRight boolean
     * @param indexOut int
     * @param indexIn int
     * @return boolean
     */
    public boolean onTrack(PVector OCar, PVector nearestIn, PVector nearestOut, boolean leftOrRight, int indexOut, int indexIn) {

        PVector secPointOut = new PVector(0, 0);
        PVector secPointIn = new PVector(0, 0);
        nearestIn.set(routeIn[indexIn]);
        nearestOut.set(routeOut[indexOut]);

        if (leftOrRight) {
            if (indexOut == 0) {
                secPointOut.set(routeOut[routeOut.length - 1]);
            } else {
                secPointOut.set(routeOut[indexOut - 1]);
            }

            if (indexIn == 0) {
                secPointIn.set(routeIn[routeIn.length - 1]);
            } else {
                secPointIn.set(routeIn[indexIn - 1]);
            }

            boolean left = rightOfVector(OCar, secPointOut, nearestOut);
            boolean right = rightOfVector(OCar, secPointIn, nearestIn);

            if (right != true || left != false) {
                return false;
            }

            return true;
        }

        if (!leftOrRight) {
            if (indexOut == routeOut.length - 1) {
                secPointOut.set(routeOut[0]);
            } else {
                secPointOut.set(routeOut[indexOut + 1]);
            }

            if (indexIn == routeIn.length - 1) {
                secPointIn.set(routeIn[0]);
            } else {
                secPointIn.set(routeIn[indexIn + 1]);
            }

            boolean left = rightOfVector(OCar, nearestOut, secPointOut);
            boolean right = rightOfVector(OCar, nearestIn, secPointIn);

            if (right != true || left != false) {
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * Überprüft ob der Punkt OC links oder rechts vom Vektor AB ist.
     *
     * @param OC Pvector
     * @param A PVector
     * @param B PVector
     * @return boolean
     */
    public boolean rightOfVector(PVector OC, PVector A, PVector B) {

        PVector AB = PVector.sub(B, A);
        PVector AC = PVector.sub(OC, A);

        float result = (AB.x * AC.y) - (AB.y * AC.x);
        if (result >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Sucht den näherste am Auto liegenden Punkt der Strecke.
     *
     * @param OCar PVector
     * @param polygon PVector[]
     * @return PVector
     */
    public PVector nearestPoint(PVector OCar, PVector[] polygon) {
        PVector nearest = new PVector(0, 0);
        for (int i = 0; i < polygon.length; i++) {
            if (i == 0) {
                nearest.set(polygon[i].x, polygon[i].y);
                temp = i;
            }

            if (OCar.dist(polygon[i]) < OCar.dist(nearest)) {
                nearest.set(polygon[i].x, polygon[i].y);
                temp = i;
            }
        }
        return nearest;
    }

    /**
     * Zählt die gefahrenen Runden und returnt den Gewinner oder 0.
     *
     * @param car Car
     * @param width int
     * @param height int
     * @param window PApplet
     * @param player int
     * @return int
     */
    public int lapCounter(Car car, int width, int height, PApplet window, int player) {

        //Bereich 1
        PVector B11 = new PVector((int) (width / 3.26), (int) (height / 1.19));
        PVector B12 = new PVector((int) (width / 3.26) + 10, (int) (height / 1.02));

        //window.stroke(255);
        //window.line(B11.x, B11.y, B12.x, B12.y);
        if (car.carX > B11.x && car.carX < B12.x) {
            if (car.carY > B11.y && car.carY < B12.y) {

                if (inB2 && player == 1) {
                    if (lapCounter1 > 0) {
                        lapCounter1--;
                    }
                }

                if (inB22 && player == 2) {
                    if (lapCounter2 > 0) {
                        lapCounter2--;
                    }
                }

                if (player == 1) {
                    inB1 = true;
                    inB2 = false;
                    inB3 = false;
                }

                if (player == 2) {
                    inB12 = true;
                    inB22 = false;
                    inB32 = false;
                }

            }
        }

        //Bereich 3
        PVector B31 = new PVector((int) (width / 2.79), (int) (height / 1.19));
        PVector B32 = new PVector((int) (width / 2.79) + 10, (int) (height / 1.02));

        //window.stroke(255);
        //window.line(B31.x, B31.y, B32.x, B32.y);
        if (car.carX > B31.x && car.carX < B32.x) {
            if (car.carY > B31.y && car.carY < B32.y) {
                if (player == 1) {
                    inB1 = false;
                    inB2 = false;
                    inB3 = true;
                }
                if (player == 2) {
                    inB12 = false;
                    inB22 = false;
                    inB32 = true;
                }
            }
        }

        //Bereich 2
        PVector B21 = new PVector((int) (width / 3.01), (int) (height / 1.19));
        PVector B22 = new PVector((int) (width / 3.01) + 10, (int) (height / 1.02));

        //window.stroke(255);
        //window.line(B21.x, B21.y, B22.x, B22.y);
        if (car.carX > B21.x && car.carX < B22.x) {
            if (car.carY > B21.y && car.carY < B22.y) {

                if (inB1 && player == 1) {
                    lapCounter1++;
                }

                if (inB12 && player == 2) {
                    lapCounter2++;
                }

                if (player == 1) {
                    inB1 = false;
                    inB2 = true;
                    inB3 = false;
                }

                if (player == 2) {
                    inB12 = false;
                    inB22 = true;
                    inB32 = false;
                }
            }
        }

        if (lapCounter1 >= 4) {
            return 1;
        }
        if (lapCounter2 >= 4) {
            return 2;
        }

        if (player == 1) {
            window.fill(11, 205, 5);
            window.stroke(11, 205, 5);
            window.circle((width / 2) - (width / 10), 0, width / 20);

            window.fill(255);
            window.textSize(width / 80);
            window.textAlign(CENTER);
            window.text(lapCounter1 + "/3", (width / 2) - (width / 10), (width / 80) + (width / 200));
        }

        if (player == 2) {
            window.fill(0, 162, 232);
            window.stroke(0, 162, 232);
            window.circle((width / 2) + (width / 10), 0, width / 20);

            window.fill(255);
            window.textSize(width / 80);
            window.textAlign(CENTER);
            window.text(lapCounter2 + "/3", (width / 2) + (width / 10), (width / 80) + (width / 200));
        }
        
        return 0;
    }

    /**
     * Die Strecke in einfachen Linien darstellen.
     *
     * @param window PApplet
     */
    public void drawLines(PApplet window) {
        window.fill(0);
        window.stroke(0);

        //Außen
        for (int i = 0; i < routeOut.length - 1; i++) {
            window.line(routeOut[i].x, routeOut[i].y, routeOut[i + 1].x, routeOut[i + 1].y);
            window.rect(routeOut[i].x, routeOut[i].y, 5, 5);
        }
        window.line(routeOut[routeOut.length - 1].x, routeOut[routeOut.length - 1].y, routeOut[0].x, routeOut[0].y);
        window.rect(routeOut[routeOut.length - 1].x, routeOut[routeOut.length - 1].y, 5, 5);

        //Innen
        for (int i = 0; i < routeIn.length - 1; i++) {
            window.line(routeIn[i].x, routeIn[i].y, routeIn[i + 1].x, routeIn[i + 1].y);
            window.rect(routeIn[i].x, routeIn[i].y, 5, 5);
        }
        window.line(routeIn[routeIn.length - 1].x, routeIn[routeIn.length - 1].y, routeIn[0].x, routeIn[0].y);
        window.rect(routeIn[routeIn.length - 1].x, routeIn[routeIn.length - 1].y, 5, 5);
    }

}
