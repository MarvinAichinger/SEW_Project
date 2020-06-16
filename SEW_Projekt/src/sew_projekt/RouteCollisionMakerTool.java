package sew_projekt;

import processing.core.*;

public class RouteCollisionMakerTool extends PApplet {
    
    /**
     * Zeigt Koordinaten einmal normal und einmal abhängig von der Bildschirmgröße an.
     * @param window PApplet
     * @param mouseX int
     * @param mouseY int
     * @param width int
     * @param height int
     */
    public void getCoordinates(PApplet window, int mouseX, int mouseY, int width, int height) {
        window.fill(255);
        window.textSize((width/16)/4);
        float resultX = (float) width/mouseX;
        float resultY = (float) height/mouseY;
        if(mouseX != 0 && mouseY != 0) {
            window.text("(" + (float) Math.round(resultX*100)/100 + "/" + (float) Math.round(resultY*100)/100 + ")", width - 200, 50);
            window.text("(" + mouseX + "/" + mouseY + ")", width-200, 100);
        }
        
    }
    
}
