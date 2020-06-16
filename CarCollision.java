package sew_projekt;

import processing.core.*;

public class CarCollision extends PApplet {
    
    /**
     * Schaut ob zwei Rechtecke überlappen.
     * @param car1X float
     * @param car1Y float
     * @param car2X float
     * @param car2Y float
     * @param car1W float
     * @param car1H float
     * @param car2W float
     * @param car2H float
     * @return boolean
     */
    public boolean carsOverlap(float car1X, float car1Y, float car2X, float car2Y, float car1W, float car1H, float car2W, float car2H) {
        
        if (car1X + 12 > car2X && car1X < car2X + 12 && car1Y + 20 > car2Y && car1Y < car2Y + 20) {
            return true;
        }
        return false;
    }
    
}
