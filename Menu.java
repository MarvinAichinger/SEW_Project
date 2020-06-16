package sew_projekt;

import processing.core.*;

/**
 * Klasse für das Menü
 */
public class Menu extends PApplet {

    //Variablen
    int rectXStart, rectYStart, startSizeX, startSizeY;
    int rectXSolo, rectYSolo, soloSizeX, soloSizeY, borderSolo;
    int rectXDuo, rectYDuo, duoSizeX, duoSizeY, borderDuo;
    int rectXOption, rectYOption, optionSizeX, optionSizeY;
    int startColor, soloColor, duoColor, optionColor;
    int hoverColor;
    boolean OverStart, OverSolo, OverDuo, OverOption;
    boolean DuoMode, SoloMode;
    boolean solo, duo, start, option;
    int mouseX;
    int mouseY;
    int width;
    int height;
    
    /**
     * Legt positionen der Rechtecke von Knöpfen im Menü, abhängig von der
     * Bildschirmgröße fest. (Konstruktor)
     * @param width int
     * @param height int
     */
    public Menu(int width, int height) {
        
        width = (int) width/16;
        height = (int) height/16;
        this.width = width;
        this.height = height;
        
        rectXStart = width * 12;
        rectYStart = (int) (height * 12.5);
        startSizeX = width * 4;
        startSizeY = height * 4;
        
        rectXSolo = width * 7;
        rectYSolo = height * 6;
        soloSizeX = width * 2;
        soloSizeY = (int) (height * 1.5);
        
        rectXDuo = width * 7;
        rectYDuo = height * 10;
        duoSizeX = width * 2;
        duoSizeY = (int) (height * 1.5);
        
        rectXOption = width * 15;
        rectYOption = 0;
        optionSizeX = width;
        optionSizeY = width;
        
        hoverColor = color(100);
        startColor = color(0, 255, 0);
        soloColor = color(255, 165, 0);
        duoColor = color(255, 165, 0);
        optionColor = color(150);
        
        borderSolo = 0;
        borderDuo = 0;
        
        OverStart = false;
        OverSolo = false;
        OverDuo = false;
        OverOption = false;
        DuoMode = false;
        SoloMode = false;
        start = false;
        option = false;
    }

    /**
     Zeichnet das Komplette Menü mit Knöpfen, Hibtergrundbild und Icons.
     * @param window PApplet
     * @param img PImage
     * @param iconStart PImage
     * @param iconOption PImage
     * @param up1 char
     * @param down1 char
     * @param left1 char
     * @param right1 char
     * @param up2 char
     * @param down2 char
     * @param left2 char
     * @param right2 char
     * @return Boolean
     */
    public Boolean drawMenu(PApplet window, PImage img, PImage iconStart, PImage iconOption, char up1, char down1, char left1, char right1, char up2, char down2, char left2, char right2) {

        window.textAlign(LEFT);
        
        if (start) {
            return DuoMode;
        }

        update();
        window.image(img, 0, 0);

        window.fill(0);
        window.noStroke();
        window.textSize(width);
        window.text("Street Racer", (int) (width*5.5), (int) (height*2));

        if (OverStart) {
            window.fill(hoverColor);
        } else if (DuoMode || SoloMode) {
            window.fill(startColor);
        } else {
            window.fill(150);
        }

        window.noStroke();
        window.rect(rectXStart, rectYStart, startSizeX, startSizeY);

        window.image(iconStart, rectXStart + width, rectYStart + (height/2), width * 2, height * 3);

        if (OverSolo) {
            window.fill(hoverColor);
        } else {
            window.fill(soloColor);
        }

        window.stroke(borderSolo);
        window.rect(rectXSolo, rectYSolo, soloSizeX, soloSizeY);

        window.fill(borderSolo);
        window.noStroke();
        window.textSize(width/4);
        window.text("Singleplayer", rectXSolo + (width/4), rectYSolo + (int) (height/1.1));

        if (OverDuo) {
            window.fill(hoverColor);
        } else {
            window.fill(duoColor);
        }

        window.stroke(borderDuo);
        window.rect(rectXDuo, rectYDuo, duoSizeX, duoSizeY);

        window.fill(borderDuo);
        window.noStroke();
        window.textSize(width/4);
        window.text("Multiplayer", rectXDuo + (width/4), rectYDuo + (int) (height/1.1));

        if (OverOption) {
            window.fill(hoverColor);
        } else {
            window.fill(optionColor);
        }

        window.stroke(255);
        window.rect(rectXOption, rectYOption, optionSizeX, optionSizeY);

        window.image(iconOption, rectXOption + (width/5), rectYOption + (int) (height/2.5), (int) (width/1.5), (int) (height*1.2));

        if (option) {
            window.stroke(255);
            window.rect(width*12, height*3, width*4, (int) (height*8.5));

            window.fill(0);
            window.textSize(width/4);
            window.text("Singleplayer", (int) (width*13.5), (int) (height*3.8));

            /*
            window.stroke(255);
            window.rect(1470, 275, 450, 50);
            window.rect(1470, 325, 450, 50);
            window.rect(1470, 375, 450, 50);
            window.rect(1470, 425, 450, 50);
            */

            window.fill(0);
            window.textSize(width/4);
            window.text("+Multiplayer", (int) (width*13.5), (int) (height*7.8));

            /*
            window.stroke(255);
            window.rect(1470, 550, 450, 50);
            window.rect(1470, 600, 450, 50);
            window.rect(1470, 650, 450, 50);
            window.rect(1470, 700, 450, 50);
            */

            window.fill(255);
            window.textSize(width/4);

            window.text("FORWARD                    "+up1, (int) (width*12.5), (int) (height*4.6));
            window.text("BACKWARD                  "+down1, (int) (width*12.5), (int) (height*4.6) + (int) (height/1.3));
            window.text("RIGHT                          "+right1, (int) (width*12.5), (int) (height*4.6) + ((int) (height/1.3)*2));
            window.text("LEFT                             "+left1, (int) (width*12.5), (int) (height*4.6) + ((int) (height/1.3)*3));

            window.text("FORWARD                    "+up2, (int) (width*12.5), (int) (height*8.7));
            window.text("BACKWARD                  "+down2, (int) (width*12.5), (int) (height*8.7) + (int) (height/1.3));
            window.text("RIGHT                          "+right2, (int) (width*12.5), (int) (height*8.7) + ((int) (height/1.3)*2));
            window.text("LEFT                             "+left2, (int) (width*12.5), (int) (height*8.7) + ((int) (height/1.3)*3));

        }

        return null;
    }

    /**
     * Verändert aussehen von Knöpfen beim Hovern
     */
    private void update() {
        if (hover(rectXStart, rectYStart, startSizeX, startSizeY)) {
            OverStart = true;
        } else if (hover(rectXSolo, rectYSolo, soloSizeX, soloSizeY)) {
            OverSolo = true;
        } else if (hover(rectXDuo, rectYDuo, duoSizeX, duoSizeY)) {
            OverDuo = true;
        } else if (hover(rectXOption, rectYOption, optionSizeX, optionSizeY)) {
            OverOption = true;
        } else {
            OverStart = OverSolo = OverDuo = OverOption = false;
        }
    }

    /**
     * Schaut ob über ein Knopf gehovert wird.
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @return boolean
     */
    boolean hover(int x, int y, int width, int height) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Überprüft gedrückte Knöpfe und setzt dazugehörige Boolische Werte.
     */
    public void mouse() {
        if (OverSolo) {
            if (DuoMode == true) {
                borderDuo = 0;
                DuoMode = false;
            }
            if (SoloMode == false) {
                borderSolo = 255;
                SoloMode = true;
            } else {
                borderSolo = 0;
                SoloMode = false;
            }
        }
        if (OverDuo) {
            if (SoloMode == true) {
                borderSolo = 0;
                SoloMode = false;
            }
            if (DuoMode == false) {
                borderDuo = 255;
                DuoMode = true;
            } else {
                borderDuo = 0;
                DuoMode = false;
            }
        }
        if (OverOption) {

            if (option == false) {
                option = true;
            } else {
                option = false;
            }
        }
        if (OverStart && SoloMode || OverStart && DuoMode) {
            start = true;
        }
    }
}
