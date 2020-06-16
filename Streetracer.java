package sew_projekt;

import processing.core.*;
import java.io.*;

/**
 * Die Main Klasse mit draw, setup, settings und Steuerung.
 */
public class Streetracer extends PApplet {

    //Menü
    Menu menue;
    PImage img;
    PImage iconStart;
    PImage iconOption;
    boolean start = false;
    Boolean mode = null;
    boolean showMenue = true;
    int startCounter;

    //SecondCar: Car/Controls/Sprite
    Car secondCar;
    boolean left, right, up, down = false;
    int up2 = 38;
    int left2 = 37;
    int down2 = 40;
    int right2 = 39;
    PImage carSprite;

    //FirstCar Car/Controls/Sprite
    Car firstCar;
    boolean w, a, s, d = false;
    int up1 = 87;
    int left1 = 65;
    int down1 = 83;
    int right1 = 68;
    PImage carSprite2;

    //Route
    Route route;
    PImage routeImage;
    int winner = 0;
    PImage pokal;

    //Time
    Time time;
    boolean timerStarted = false;
    String stoppedTime = "";

    //RouteCollisionMakerTool
    RouteCollisionMakerTool tool = new RouteCollisionMakerTool();

    //HUD
    HUD hud;
    PImage driver;
    PImage driver2;

    //Car collision
    CarCollision carCollision = new CarCollision();

    //Key personalisieren
    boolean changeKeySingle = false;
    boolean s1, s2, s3, s4 = false;
    char s1N = 'w';
    char s2N = 's';
    char s3N = 'd';
    char s4N = 'a';
    boolean changeKeyDuo = false;
    boolean d1, d2, d3, d4 = false;
    char d1N = '↑';
    char d2N = '↓';
    char d3N = '→';
    char d4N = '←';

    //Tutorial
    boolean showTutorial = false;
    PImage controlsWASD;
    PImage controlsArrows;

    /**
     * In dieser Methode wird die Fenstergröße festgelegt.
     */
    @Override
    public void settings() {
        fullScreen();
        //size(1600, 900);
    }

    /**
     * In dieser Methode wird die entsprechende Route, sowie die Bilder
     * vorgeladen.
     */
    @Override
    public void setup() {
        background(0);

        //Menü
        menue = new Menu(displayWidth, displayHeight);

        //Routes
        createTestRoute();

        //Images
        carSprite = loadImage("Auto_sprite.png");
        carSprite2 = loadImage("Auto_sprite2.png");

        img = loadImage("background.jpg");
        iconStart = loadImage("icon_Start.png");
        iconOption = loadImage("icon_Options.png");
        pokal = loadImage("Pokal.png");

        //Timer
        time = new Time();

        //fps
        frameRate(60);

        //HUD
        hud = new HUD();
        driver = loadImage("RacingDriver.png");
        driver2 = loadImage("RacingDriver_2.png");

        //cars
        secondCar = new Car(displayWidth / (float) 3, displayHeight / (float) 1.07, 90, (float) -5, (float) 0.025, (float) 0.1, (float) 2);
        firstCar = new Car(displayWidth / (float) 3, displayHeight / (float) 1.11, 90, (float) -5, (float) 0.025, (float) 0.1, (float) 2);

        //Get Information from startFile.txt
        readStartFile();

        //Tutorial
        controlsWASD = loadImage("controlsWASD.png");
        controlsArrows = loadImage("controlsArrows.png");
    }

    /**
     * Die draw Methode wird immer wieder aufgerufen. In dieser Methode werden
     * Bildschirmausgaben durchgeführt und alle Möglichen Methoden für Auto,
     * Strecke, Tastendrücke, ... aufgerufen.
     */
    @Override
    public void draw() {
        if (showMenue) {
            //Menü anzeigen
            menue.mouseX = mouseX;
            menue.mouseY = mouseY;
            mode = menue.drawMenu(this, img, iconStart, iconOption, s1N, s2N, s4N, s3N, d1N, d2N, d4N, d3N); //true bei Mehrspieler

            //Tutorial anzeigen
            if (showTutorial) {
                fill(255);
                stroke(0);
                rect(0, 0, displayWidth, displayHeight);

                fill(0);
                stroke(0);
                textAlign(CENTER);
                textSize(displayWidth / 40);
                text("How to play!", displayWidth / 2, displayHeight / 10);

                textSize(displayWidth / 50);
                text("Singleplayer", displayWidth / 4, displayHeight / 6);
                textSize(displayWidth / 80);
                text("In Singleplayer Mode you\ncan train your skills and\nget faster and faster.\nBeat your best Time and drive\neven quicker around the curve!", displayWidth / 4, displayHeight / 4);
                image(controlsWASD, (displayWidth/4)-(displayWidth/10/2), (int) (displayHeight/1.7), displayWidth/10, displayHeight/10);
                
                textSize(displayWidth / 50);
                text("Multiplayer", (displayWidth / 4) * 3, displayHeight / 6);
                textSize(displayWidth / 80);
                text("In Multiplayer Mode you\ncan drive against other people\nWho can drive the 3 laps faster?\nIf your opponent is anoying,\nbump him outside the track!", displayWidth / 4 * 3, displayHeight / 4);
                image(controlsArrows, (displayWidth/4*3)-(displayWidth/10/2), (int) (displayHeight/1.7), displayWidth/10, displayHeight/10);
                
                textSize(displayWidth/90);
                text("Tip: You can change the controls in the settings!", displayWidth/2, (int) (displayHeight/1.07));
                
                fill(11, 205, 5);
                stroke(11, 205, 5);
            }

            //Start
            if (mode != null) {
                showMenue = false;
                menue.option = false;
                start = true;
            }
        } else {
            //Strecke als Hintergrundbild
            routeImage.resize(displayWidth, displayHeight);
            background(routeImage);
            fill(250);
            stroke(200);
            rect(displayWidth / 3, (int) (displayHeight / 1.13), displayWidth / 200, (int) (displayHeight / 15.5));
            //route.drawLines(this); //Hilfslinien
        }

        if (start) {
            if (startCounter >= 180) {

                //neuen Timer
                if (!timerStarted) {
                    time.startNewTimer();
                    timerStarted = true;
                }

                //Autos bewegen und Route Collision
                firstCar.moveCar(this, carSprite2);
                if (mode) {
                    secondCar.moveCar(this, carSprite);
                }
                route.routeCollidor(firstCar);
                if (mode) {
                    route.routeCollidor(secondCar);
                }

                //Car Collision bei zwei Spielern
                if (mode) {
                    boolean overlap = carCollision.carsOverlap(firstCar.carX, firstCar.carY, secondCar.carX, secondCar.carY, 12, 20, 12, 20);
                    if (overlap) {
                        PVector vCar1 = new PVector(firstCar.carX, firstCar.carY);
                        PVector vCar2 = new PVector(secondCar.carX, secondCar.carY);

                        PVector C1C2 = PVector.sub(vCar2, vCar1);
                        C1C2.setMag(C1C2.mag() + 2);
                        PVector nP = PVector.add(vCar1, C1C2);
                        secondCar.carX = nP.x;
                        secondCar.carY = nP.y;

                        PVector C2C1 = PVector.sub(vCar1, vCar2);
                        C2C1.setMag(C2C1.mag() + 2);
                        nP = PVector.add(vCar2, C2C1);
                        firstCar.carX = nP.x;
                        firstCar.carY = nP.y;
                    }
                }

            } else {
                //StartCounter
                startCounter++;
                textSize(displayWidth / 20);
                if (startCounter < 60) {
                    fill(255);
                    textAlign(CENTER);
                    text("3", displayWidth / 2, displayHeight / 2);
                } else if (startCounter < 120) {
                    fill(255);
                    textAlign(CENTER);
                    text("2", displayWidth / 2, displayHeight / 2);
                } else if (startCounter < 180) {
                    fill(255);
                    textAlign(CENTER);
                    text("1", displayWidth / 2, displayHeight / 2);
                    firstCar.speed = 0;
                    if (mode) {
                        secondCar.speed = 0;
                    }
                }
            }
        }

        //Timer updaten
        if (timerStarted) {
            time.updateTimer(this, displayWidth, displayHeight);
            hud.firstPlayerHUD(this, displayHeight, displayWidth, driver, firstCar);
            winner = route.lapCounter(firstCar, displayWidth, displayHeight, this, 1);
            if (mode) {
                hud.secondPlayerHUD(this, displayHeight, displayWidth, driver2, secondCar);
                route.lapCounter(secondCar, displayWidth, displayHeight, this, 2);
            }
        }

        //Gewinner Anzeige
        if (winner != 0) {
            start = false;
            if (time.stopped != true) {
                time.stopTimer();
                stoppedTime = time.stoppedTime;
            }
            fill(100, 100, 100);
            stroke(0);
            rect(displayWidth / 4, displayHeight / 4, displayWidth / 2, displayHeight / 2);
            textAlign(CENTER);
            image(pokal, (displayWidth / 2) - (displayWidth / 20), (displayHeight / 2) - (displayHeight / 7), displayWidth / 10, displayHeight / 10);
            textSize(displayWidth / 80);
            if (winner == 1 && mode) {
                fill(255);
                stroke(255);
                text("The winner is: Player 1! \n Great!!! \n Time: " + stoppedTime, displayWidth / 2, displayHeight / 2);
            } else if (winner == 2 && mode) {
                fill(255);
                stroke(255);
                text("The winner is: Player 2! \n Great!!! \n Time: " + stoppedTime, displayWidth / 2, displayHeight / 2);
            } else {
                fill(255);
                stroke(255);
                text("You drove the 3 laps in \n" + stoppedTime + ".\n Great!", displayWidth / 2, displayHeight / 2);
            }

            fill(255);
            stroke(255);
            textSize(displayWidth / 80);
            rect((displayWidth / 2) - ((displayWidth / 16) * 2), (int) (displayHeight / 1.5), displayWidth / 16, displayHeight / 16);
            fill(0);
            text("Home", (displayWidth / 2) - ((displayWidth / 16) * 2) + ((displayWidth / 16) / 2), (int) (displayHeight / 1.5) + (int) ((displayHeight / 16) / 1.5));
            fill(255);
            stroke(255);
            rect((displayWidth / 2) + (displayWidth / 16), (int) (displayHeight / 1.5), displayWidth / 16, displayHeight / 16);
            fill(0);
            fill(0);
            text("Again", (displayWidth / 2) + ((displayWidth / 16)) + ((displayWidth / 16) / 2), (int) (displayHeight / 1.5) + (int) ((displayHeight / 16) / 1.5));

            menue.mouseX = mouseX;
            menue.mouseY = mouseY;
            boolean homeHover = menue.hover((displayWidth / 2) - ((displayWidth / 16) * 2), (int) (displayHeight / 1.5), displayWidth / 16, displayHeight / 16);
            if (homeHover) {
                fill(100);
                text("Home", (displayWidth / 2) - ((displayWidth / 16) * 2) + ((displayWidth / 16) / 2), (int) (displayHeight / 1.5) + (int) ((displayHeight / 16) / 1.5));
            }

            boolean againHover = menue.hover((displayWidth / 2) + (displayWidth / 16), (int) (displayHeight / 1.5), displayWidth / 16, displayHeight / 16);
            if (againHover) {
                fill(100);
                text("Again", (displayWidth / 2) + ((displayWidth / 16)) + ((displayWidth / 16) / 2), (int) (displayHeight / 1.5) + (int) ((displayHeight / 16) / 1.5));
            }

        }

        //Schließen Knopf
        fill(255, 0, 0);
        textSize(displayWidth / 60);
        textAlign(LEFT, TOP);
        text("X", 0, (displayWidth / 300) * (-1));
        menue.mouseX = mouseX;
        menue.mouseY = mouseY;
        boolean XHover = menue.hover(0, 0, (int) (displayWidth / 71.11), (int) (displayHeight / 32.73));
        if (XHover) {
            fill(100);
            text("X", 0, (displayWidth / 300) * (-1));
        }
        
        //Tutorial Knopf
        fill(0);
        textSize(displayWidth/60);
        textAlign(LEFT, TOP);
        text("?", (int)(displayWidth/55.17), (displayWidth / 300) * (-1));
        menue.mouseX = mouseX;
        menue.mouseY = mouseY;
        boolean THover = menue.hover((int)(displayWidth/65.17), 0, (int) (displayWidth / 71.11), (int) (displayHeight / 32.73));
        if (THover) {
            fill(100);
            text("?", (int)(displayWidth/55.17), (displayWidth / 300) * (-1));
        }

        //Zweites Auto
        if (left) {
            secondCar.turnLeft();
        }
        if (right) {
            secondCar.turnRight();
        }
        if (up) {
            secondCar.accelerate();
        }
        if (down) {
            secondCar.brake();
        }
        if (!down && !up) {
            secondCar.roll();
        }

        //Erstes Auto
        if (a) {
            firstCar.turnLeft();
        }
        if (d) {
            firstCar.turnRight();
        }
        if (w) {
            firstCar.accelerate();
        }
        if (s) {
            firstCar.brake();
        }
        if (!s && !w) {
            firstCar.roll();
        }

        //Tasten Einstellung
        if (menue.option) {

            boolean singlePlayerKey = menue.hover((int) (displayWidth / 1.31), (int) (displayHeight / 4.94), (int) (displayWidth / 16 * 3.7), (int) (displayHeight / 16 * 4));
            if (singlePlayerKey) {
                noFill();
                stroke(255, 0, 0);
                rect((int) (displayWidth / 1.31), (int) (displayHeight / 4.94), (int) (displayWidth / 16 * 3.7), (int) (displayHeight / 16 * 4));
            }

            boolean duoPlayerKey = menue.hover((int) (displayWidth / 1.31), (int) (displayHeight / 2.22), (int) (displayWidth / 16 * 3.7), (int) (displayHeight / 16 * 4));
            if (duoPlayerKey) {
                noFill();
                stroke(255, 0, 0);
                rect((int) (displayWidth / 1.31), (int) (displayHeight / 2.22), (int) (displayWidth / 16 * 3.7), (int) (displayHeight / 16 * 4));
            }

            textSize((displayWidth / 16) / 4);
            if (changeKeySingle) {
                if (!s1) {
                    fill(255, 0, 0);
                    text("FORWARD                    " + s1N, (int) ((int) (displayWidth / 16) * 12.5), (int) ((int) (displayHeight / 16) * 4.17));
                } else if (!s2) {
                    fill(255, 0, 0);
                    text("BACKWARD                  " + s2N, (int) ((int) (displayWidth / 16) * 12.5), (int) ((int) (displayHeight / 16) * 4.17) + (int) ((int) (displayHeight / 16) / 1.3));
                } else if (!s3) {
                    fill(255, 0, 0);
                    text("RIGHT                          " + s3N, (int) ((int) (displayWidth / 16) * 12.5), (int) ((int) (displayHeight / 16) * 4.17) + ((int) ((int) (displayHeight / 16) / 1.3) * 2));
                } else if (!s4) {
                    fill(255, 0, 0);
                    text("LEFT                             " + s4N, (int) ((int) (displayWidth / 16) * 12.5), (int) ((int) (displayHeight / 16) * 4.17) + ((int) ((int) (displayHeight / 16) / 1.3) * 3));
                }
            }

            if (changeKeyDuo) {
                if (!d1) {
                    fill(255, 0, 0);
                    text("FORWARD                    " + d1N, (int) ((displayWidth / 16) * 12.5), (int) ((displayHeight / 16) * 8.26));
                } else if (!d2) {
                    fill(255, 0, 0);
                    text("BACKWARD                  " + d2N, (int) ((displayWidth / 16) * 12.5), (int) ((displayHeight / 16) * 8.26) + (int) ((displayHeight / 16) / 1.3));
                } else if (!d3) {
                    fill(255, 0, 0);
                    text("RIGHT                          " + d3N, (int) ((displayWidth / 16) * 12.5), (int) ((displayHeight / 16) * 8.26) + ((int) ((displayHeight / 16) / 1.3) * 2));
                } else if (!d4) {
                    fill(255, 0, 0);
                    text("LEFT                             " + d4N, (int) ((displayWidth / 16) * 12.5), (int) ((displayHeight / 16) * 8.26) + ((int) ((displayHeight / 16) / 1.3) * 3));
                }
            }

        }

        //RouteCollisionMakerTool (Zeigt Koordinaten der Maus an)
        //tool.getCoordinates(this, mouseX, mouseY, displayWidth, displayHeight);
    }

    /**
     * Diese Methode wird aufgerufen wenn eine Taste gedrückt wird. Sie
     * überprüft welche Taste und setzt den Entsprechenden Boolischen Wert auf
     * true.
     */
    @Override
    public void keyPressed() {

        //fill(255);
        //text(keyCode, 100, 100);
        if (keyCode == left2) {
            left = true;
        }
        if (keyCode == right2) {
            right = true;
        }
        if (keyCode == up2) {
            up = true;
        }
        if (keyCode == down2) {
            down = true;
        }

        if (keyCode == left1) {
            a = true;
        }
        if (keyCode == right1) {
            d = true;
        }
        if (keyCode == up1) {
            w = true;
        }
        if (keyCode == down1) {
            s = true;
        }

        //Pfeiltasten zum Anzeigen
        switch (keyCode) {
            case 38:
                key = '↑';
                break;
            case 40:
                key = '↓';
                break;
            case 39:
                key = '→';
                break;
            case 37:
                key = '←';
                break;
            default:
                break;
        }

        //Spieler 1 tasten verändern
        if (changeKeySingle) {
            if (!s1) {
                up1 = keyCode;
                s1 = true;
                s1N = key;
            } else if (!s2) {
                down1 = keyCode;
                s2 = true;
                s2N = key;
            } else if (!s3) {
                right1 = keyCode;
                s3 = true;
                s3N = key;
            } else if (!s4) {
                left1 = keyCode;
                s4 = true;
                s4N = key;
                changeKeySingle = false;
            }
        }

        //Spieler 2 Tasten verändern
        if (changeKeyDuo) {
            if (!d1) {
                up2 = keyCode;
                d1 = true;
                d1N = key;
            } else if (!d2) {
                down2 = keyCode;
                d2 = true;
                d2N = key;
            } else if (!d3) {
                right2 = keyCode;
                d3 = true;
                d3N = key;
            } else if (!d4) {
                left2 = keyCode;
                d4 = true;
                d4N = key;
                changeKeyDuo = false;
            }
        }

    }

    /**
     * Diese Methode wird aufgerufen, wenn eine Taste losgelassen wird. Sie
     * überprüft welche Taste und setzt den Entsprechenden Boolischen Wert auf
     * false.
     */
    @Override
    public void keyReleased() {

        if (keyCode == left2) {
            left = false;
        }
        if (keyCode == right2) {
            right = false;
        }
        if (keyCode == up2) {
            up = false;
        }
        if (keyCode == down2) {
            down = false;
        }

        if (keyCode == left1) {
            a = false;
        }
        if (keyCode == right1) {
            d = false;
        }
        if (keyCode == up1) {
            w = false;
        }
        if (keyCode == down1) {
            s = false;
        }

    }

    /**
     * Diese Methode erstellt die Punkte der Strecke
     */
    public void createTestRoute() {

        //außen
        PVector P11 = new PVector((int) (displayWidth / 3.27), (int) (displayHeight / 1.15));
        PVector P11V = new PVector((int) (displayWidth / 2.56), (int) (displayHeight / 1.15));
        PVector P12 = new PVector((int) (displayWidth / 2.03), (int) (displayHeight / 1.14));
        PVector P13 = new PVector((int) (displayWidth / 1.71), (int) (displayHeight / 1.17));
        PVector P13V = new PVector((int) (displayWidth / 1.48), (int) (displayHeight / 1.18));
        PVector P14 = new PVector((int) (displayWidth / 1.27), (int) (displayHeight / 1.18));
        PVector P14V = new PVector((int) (displayWidth / 1.19), (int) (displayHeight / 1.23));
        PVector P15 = new PVector((int) (displayWidth / 1.15), (int) (displayHeight / 1.33));
        PVector P16 = new PVector((int) (displayWidth / 1.13), (int) (displayHeight / 1.67));
        PVector P17 = new PVector((int) (displayWidth / 1.15), (int) (displayHeight / 4.05));
        PVector P17V = new PVector((int) (displayWidth / 1.14), (int) (displayHeight / 1.47));
        PVector P16V = new PVector((int) (displayWidth / 1.14), (int) (displayHeight / 2.24));
        PVector P18 = new PVector((int) (displayWidth / 1.17), (int) (displayHeight / 4.37));
        PVector P19 = new PVector((int) (displayWidth / 1.19), (int) (displayHeight / 4.57));
        PVector P110 = new PVector((int) (displayWidth / 1.21), (int) (displayHeight / 4.46));
        PVector P111 = new PVector((int) (displayWidth / 1.21), (int) (displayHeight / 3.66));
        PVector P112 = new PVector((int) (displayWidth / 1.18), (int) (displayHeight / 2.26));
        PVector P113 = new PVector((int) (displayWidth / 1.18), (int) (displayHeight / 1.81));
        PVector P114 = new PVector((int) (displayWidth / 1.21), (int) (displayHeight / 1.5));
        PVector P115 = new PVector((int) (displayWidth / 1.25), (int) (displayHeight / 1.37));
        PVector P116 = new PVector((int) (displayWidth / 1.33), (int) (displayHeight / 1.3));
        PVector P116V = new PVector((int) (displayWidth / 1.53), (int) (displayHeight / 1.29));
        PVector P117 = new PVector((int) (displayWidth / 1.72), (int) (displayHeight / 1.28));
        PVector P118 = new PVector((int) (displayWidth / 2.04), (int) (displayHeight / 1.28));
        PVector P119 = new PVector((int) (displayWidth / 3.05), (int) (displayHeight / 1.3));
        PVector P30 = new PVector((int) (displayWidth / 4.31), (int) (displayHeight / 1.48));
        PVector P31 = new PVector((int) (displayWidth / 4.88), (int) (displayHeight / 1.69));
        PVector P31V = new PVector((int) (displayWidth / 2.56), (int) (displayHeight / 1.29));
        PVector P32 = new PVector((int) (displayWidth / 4.61), (int) (displayHeight / 1.89));
        PVector P33 = new PVector((int) (displayWidth / 3.19), (int) (displayHeight / 2.03));
        PVector P33V = new PVector((int) (displayWidth / 2.52), (int) (displayHeight / 2.03));
        PVector P34 = new PVector((int) (displayWidth / 2.14), (int) (displayHeight / 2.12));
        PVector P35 = new PVector((int) (displayWidth / 1.77), (int) (displayHeight / 2.1));
        PVector P36 = new PVector((int) (displayWidth / 1.54), (int) (displayHeight / 2.09));
        PVector P37 = new PVector((int) (displayWidth / 1.48), (int) (displayHeight / 2.13));
        PVector P38 = new PVector((int) (displayWidth / 1.45), (int) (displayHeight / 2.24));
        PVector P39 = new PVector((int) (displayWidth / 1.45), (int) (displayHeight / 2.41));
        PVector P330 = new PVector((int) (displayWidth / 1.5), (int) (displayHeight / 3.13));
        PVector P331 = new PVector((int) (displayWidth / 1.56), (int) (displayHeight / 4.11));
        PVector P332 = new PVector((int) (displayWidth / 1.88), (int) (displayHeight / 5.66));
        PVector P333 = new PVector((int) (displayWidth / 2.23), (int) (displayHeight / 5.88));
        PVector P334 = new PVector((int) (displayWidth / 2.83), (int) (displayHeight / 4.62));
        PVector P335 = new PVector((int) (displayWidth / 3.69), (int) (displayHeight / 2.7));
        PVector P336 = new PVector((int) (displayWidth / 5.56), (int) (displayHeight / 2.16));
        PVector P337 = new PVector((int) (displayWidth / 7.16), (int) (displayHeight / 1.99));
        PVector P338 = new PVector((int) (displayWidth / 7.62), (int) (displayHeight / 1.69));
        PVector P339 = new PVector((int) (displayWidth / 7.3), (int) (displayHeight / 1.31));
        PVector P339V = new PVector((int) (displayWidth / 6.81), (int) (displayHeight / 1.23));
        PVector P50 = new PVector((int) (displayWidth / 5.82), (int) (displayHeight / 1.18));
        PVector[] routeIn = {P11, P11V, P12, P13, P13V, P14, P14V, P15, P17V, P16, P16V, P17, P18, P19, P110, P111,
            P112, P113, P114, P115, P116, P116V, P117, P118, P31V, P119, P30, P31, P32, P33, P33V, P34, P35, P36, P37, P38,
            P39, P330, P331, P332, P333, P334, P335, P336, P337, P338, P339, P339V, P50};

        //innen
        PVector P21 = new PVector((int) (displayWidth / 3.27), (int) (displayHeight / 1.04));
        PVector P21V = new PVector((int) (displayWidth / 2.56), (int) (displayHeight / 1.04));
        PVector P22 = new PVector((int) (displayWidth / 2.02), (int) (displayHeight / 1.03));
        PVector P23 = new PVector((int) (displayWidth / 1.71), (int) (displayHeight / 1.05));
        PVector P23V = new PVector((int) (displayWidth / 1.48), (int) (displayHeight / 1.06));
        PVector P24 = new PVector((int) (displayWidth / 1.24), (int) (displayHeight / 1.07));
        PVector P24V = new PVector((int) (displayWidth / 1.14), (int) (displayHeight / 1.13));
        PVector P25 = new PVector((int) (displayWidth / 1.09), (int) (displayHeight / 1.26));
        PVector P26 = new PVector((int) (displayWidth / 1.06), (int) (displayHeight / 1.67));
        PVector P27 = new PVector((int) (displayWidth / 1.08), (int) (displayHeight / 4.04));
        PVector P27V = new PVector((int) (displayWidth / 1.06), (int) (displayHeight / 1.42));
        PVector P26V = new PVector((int) (displayWidth / 1.06), (int) (displayHeight / 2.24));
        PVector P28 = new PVector((int) (displayWidth / 1.12), (int) (displayHeight / 9.28));
        PVector P29 = new PVector((int) (displayWidth / 1.19), (int) (displayHeight / 12.33));
        PVector P220 = new PVector((int) (displayWidth / 1.29), (int) (displayHeight / 7.96));
        PVector P221 = new PVector((int) (displayWidth / 1.31), (int) (displayHeight / 3.6));
        PVector P222 = new PVector((int) (displayWidth / 1.27), (int) (displayHeight / 2.25));
        PVector P223 = new PVector((int) (displayWidth / 1.27), (int) (displayHeight / 1.86));
        PVector P224 = new PVector((int) (displayWidth / 1.3), (int) (displayHeight / 1.59));
        PVector P225 = new PVector((int) (displayWidth / 1.32), (int) (displayHeight / 1.52));
        PVector P226 = new PVector((int) (displayWidth / 1.35), (int) (displayHeight / 1.48));
        PVector P226V = new PVector((int) (displayWidth / 1.54), (int) (displayHeight / 1.47));
        PVector P227 = new PVector((int) (displayWidth / 1.73), (int) (displayHeight / 1.46));
        PVector P228 = new PVector((int) (displayWidth / 2.03), (int) (displayHeight / 1.45));
        PVector P229 = new PVector((int) (displayWidth / 2.82), (int) (displayHeight / 1.51));
        PVector P40 = new PVector((int) (displayWidth / 3.17), (int) (displayHeight / 1.55));
        PVector P41 = new PVector((int) (displayWidth / 3.23), (int) (displayHeight / 1.68));
        PVector P41V = new PVector((int) (displayWidth / 2.56), (int) (displayHeight / 1.47));
        PVector P42 = new PVector((int) (displayWidth / 3.21), (int) (displayHeight / 1.7));
        PVector P43 = new PVector((int) (displayWidth / 3.08), (int) (displayHeight / 1.7));
        PVector P43V = new PVector((int) (displayWidth / 2.52), (int) (displayHeight / 1.62));
        PVector P44 = new PVector((int) (displayWidth / 2.11), (int) (displayHeight / 1.6));
        PVector P45 = new PVector((int) (displayWidth / 1.76), (int) (displayHeight / 1.69));
        PVector P46 = new PVector((int) (displayWidth / 1.54), (int) (displayHeight / 1.71));
        PVector P47 = new PVector((int) (displayWidth / 1.39), (int) (displayHeight / 1.79));
        PVector P48 = new PVector((int) (displayWidth / 1.34), (int) (displayHeight / 1.97));
        PVector P49 = new PVector((int) (displayWidth / 1.31), (int) (displayHeight / 2.24));
        PVector P440 = new PVector((int) (displayWidth / 1.35), (int) (displayHeight / 3.42));
        PVector P441 = new PVector((int) (displayWidth / 1.47), (int) (displayHeight / 6.77));
        PVector P442 = new PVector((int) (displayWidth / 1.84), (int) (displayHeight / 15.79));
        PVector P443 = new PVector((int) (displayWidth / 2.22), (int) (displayHeight / 16.98));
        PVector P444 = new PVector((int) (displayWidth / 3.19), (int) (displayHeight / 8.11));
        PVector P445 = new PVector((int) (displayWidth / 5.54), (int) (displayHeight / 4.35));
        PVector P446 = new PVector((int) (displayWidth / 10.39), (int) (displayHeight / 2.79));
        PVector P447 = new PVector((int) (displayWidth / 21.1), (int) (displayHeight / 2.01));
        PVector P448 = new PVector((int) (displayWidth / 43.64), (int) (displayHeight / 1.6));
        PVector P449 = new PVector((int) (displayWidth / 26.67), (int) (displayHeight / 1.29));
        PVector P449V = new PVector((int) (displayWidth / 14.66), (int) (displayHeight / 1.14));
        PVector P60 = new PVector((int) (displayWidth / 6.76), (int) (displayHeight / 1.05));
        PVector[] routeOut = {P21, P21V, P22, P23, P23V, P24, P24V, P25, P27V, P26, P26V, P27, P28, P29, P220, P221,
            P222, P223, P224, P225, P226, P226V, P227, P228, P41V, P229, P40, P41, P42, P43, P43V, P44, P45, P46, P47, P48,
            P49, P440, P441, P442, P443, P444, P445, P446, P447, P448, P449, P449V, P60};

        route = new Route(routeOut, routeIn);
        routeImage = loadImage("TestTrack_2.JPG");

    }

    /**
     * Wird aufgerufen wenn eine Maustaste gedrückt wird und übergibt diese
     * Information an das Menü.
     */
    @Override
    public void mousePressed() {
        if (showTutorial) {
            showTutorial = false;
        }else {
            menue.mouse();
        }

        //Knöpfe in der Gewinner Anzeige werden Gedrückt
        if (winner != 0) {
            menue.mouseX = mouseX;
            menue.mouseY = mouseY;
            boolean homeHover = menue.hover((displayWidth / 2) - ((displayWidth / 16) * 2), (int) (displayHeight / 1.5), displayWidth / 16, displayHeight / 16);
            if (homeHover) {
                menue.start = false;
                mode = null;
                winner = 0;
                showMenue = true;
                timerStarted = false;
                route.lapCounter1 = 1;
                route.lapCounter2 = 1;
                secondCar = new Car(displayWidth / (float) 3, displayHeight / (float) 1.07, 90, (float) -5, (float) 0.025, (float) 0.1, (float) 2);
                firstCar = new Car(displayWidth / (float) 3, displayHeight / (float) 1.11, 90, (float) -5, (float) 0.025, (float) 0.1, (float) 2);
                startCounter = 0;
                time.stopped = false;
            }

            boolean againHover = menue.hover((displayWidth / 2) + (displayWidth / 16), (int) (displayHeight / 1.5), displayWidth / 16, displayHeight / 16);
            if (againHover) {
                menue.start = true;
                winner = 0;
                showMenue = true;
                timerStarted = false;
                route.lapCounter1 = 1;
                route.lapCounter2 = 1;
                secondCar = new Car(displayWidth / (float) 3, displayHeight / (float) 1.07, 90, (float) -5, (float) 0.025, (float) 0.1, (float) 2);
                firstCar = new Car(displayWidth / (float) 3, displayHeight / (float) 1.11, 90, (float) -5, (float) 0.025, (float) 0.1, (float) 2);
                startCounter = 0;
                time.stopped = false;
            }
        }

        //Schließen Knopf wird gedrückt
        menue.mouseX = mouseX;
        menue.mouseY = mouseY;
        boolean XHover = menue.hover(0, 0, (int) (displayWidth / 71.11), (int) (displayHeight / 32.73));
        if (XHover) {
            exit();
        }
        
        //Tutorial Knopf wird gedrückt
        boolean THover = menue.hover((int)(displayWidth/65.17), 0, (int) (displayWidth / 71.11), (int) (displayHeight / 32.73));
        if (THover) {
            showTutorial = true;
        }

        //Verändern der Spieler 1 Keys
        boolean singlePlayerKey = menue.hover((int) (displayWidth / 1.31), (int) (displayHeight / 4.94), (int) (displayWidth / 16 * 3.7), (int) (displayHeight / 16 * 4));
        if (singlePlayerKey) {
            changeKeySingle = true;
            s1 = false;
            s2 = false;
            s3 = false;
            s4 = false;
        }

        //Verändern der Spieler 2 Keys
        boolean duoPlayerKey = menue.hover((int) (displayWidth / 1.31), (int) (displayHeight / 2.22), (int) (displayWidth / 16 * 3.7), (int) (displayHeight / 16 * 4));
        if (duoPlayerKey) {
            changeKeyDuo = true;
            d1 = false;
            d2 = false;
            d3 = false;
            d4 = false;
        }

    }

    /**
     * Lest das Textfile startFile.txt ein und schaut ob das Programm zum ersten mal gestartet wird.
     */
    public void readStartFile() {
        String fileName = "startFile.txt";
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("true")) {
                    showTutorial = true;
                    writeStartFile();
                }
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    /**
     * Schreibt in der Datei startFile.txt den Wert false. Bedeutet, dass das
     * Programm bereits mindestens einmal gestartet worden ist.
     */
    public void writeStartFile() {
        String fileName = "startFile.txt";

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("false");
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

}
