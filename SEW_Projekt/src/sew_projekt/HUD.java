package sew_projekt;

import processing.core.*;

public class HUD extends PApplet {
    
    /**
     * Erstellt die Geschwndigkeitsanzeige für Auto 1.
     * @param window PApplet
     * @param height int
     * @param width int
     * @param driver PImage
     * @param firstCar Car
     */
    public void firstPlayerHUD(PApplet window, int height, int width, PImage driver, Car firstCar) {
        window.fill(11, 205, 5);
        window.stroke(11, 205, 5);
        window.rect(0, (int) (height - (height/20)), width/8, height/20);
        window.circle(0, height, width/8);
        window.image(driver, -width/100, (int) (height/1.1), width/12, width/12);
        
        int kmh = (int) (firstCar.speed * (-40));
        if (kmh < 0) {
            kmh *= (-1);
        }
        window.fill(255);
        window.textSize(width/100);
        window.textAlign(LEFT);
        window.text(" " + kmh + " km/h", width/16, height - height/60);
        
    }
    
    /**
     * Erstellt die Geschwndigkeitsanzeige für Auto 2.
     * @param window PApplet
     * @param height int
     * @param width int
     * @param driver PImage
     * @param firstCar Car
     */
    public void secondPlayerHUD(PApplet window, int height, int width, PImage driver, Car firstCar) {
        window.fill(0, 162, 232);
        window.stroke(0, 162, 232);
        window.rect(width - width/8, (int) (height - (height/20)), width/8, height/20);
        window.circle(width, height, width/8);
        window.image(driver, (int) (width - (width/13.5)), (int) (height/1.1), width/12, width/12);
        
        int kmh = (int) (firstCar.speed * (-40));
        if (kmh < 0) {
            kmh *= (-1);
        }
        window.fill(255);
        window.textSize(width/100);
        window.textAlign(LEFT);
        window.text(" " + kmh + " km/h", width - width/8, height - height/60);
        
    }
    
}
