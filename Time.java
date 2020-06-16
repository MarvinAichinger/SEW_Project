package sew_projekt;

import processing.core.*;
import java.util.Date;

/**
 * Klasse für die gefahrene Zeit.
 */
public class Time extends PApplet {

    //Variablen
    Date savedTime;
    boolean stopped = false;
    String stoppedTime = "";

    /**
     * Standartkonstruktor
     */
    public Time() {
    }

    /**
     * startet einen NEUEN Timer, der Automatisch losläuft
     */
    public void startNewTimer() {
        savedTime = new Date();
    }

    /**
     * Updated den Timer und gibt die vergangene Zeit auf dem Bildschirm aus
     *
     * @param window PApplet
     */
    public void updateTimer(PApplet window, int width, int height) {
        if (!stopped) {
            Date newTime = new Date();
            int elapsedTime = (int) savedTime.getTime() - (int) newTime.getTime();
            elapsedTime *= -1;

            int minutes = 0;
            int temp = elapsedTime;
            while (temp / 1000 >= 60) {
                minutes++;
                temp -= 60000;
            }

            window.fill(255);
            window.stroke(255);
            window.rect((width / 2) - ((width / 10) / 2), 0, width / 10, width / 60);
            window.circle((width / 2) - ((width / 10) / 2), 0, width / 30);
            window.circle((width / 2) + ((width / 10) / 2), 0, width / 30);

            window.fill(0);
            window.textSize(width / 80);
            window.textAlign(CENTER);

            int seconds = ((elapsedTime / 1000) - (minutes * 60));
            int miliseconds = (elapsedTime - (elapsedTime / 1000) * 1000);
            String secondsStr;
            String milisecondsStr;

            if (seconds < 10) {
                secondsStr = "0" + seconds;
            } else {
                secondsStr = "" + seconds;
            }

            if (miliseconds < 10) {
                milisecondsStr = "00" + miliseconds;
            } else if (miliseconds < 100) {
                milisecondsStr = "0" + miliseconds;
            } else {
                milisecondsStr = "" + miliseconds;
            }

            window.text(minutes + ":" + secondsStr + ":" + milisecondsStr, width / 2, width / 80);
        }
    }

    /**
     * Stopped den Timer
     */
    public void stopTimer() {
        Date newTime = new Date();
        int elapsedTime = (int) savedTime.getTime() - (int) newTime.getTime();
        elapsedTime *= -1;

        int minutes = 0;
        int temp = elapsedTime;
        while (temp / 1000 >= 60) {
            minutes++;
            temp -= 60000;
        }

        int seconds = ((elapsedTime / 1000) - (minutes * 60));
        int miliseconds = (elapsedTime - (elapsedTime / 1000) * 1000);
        String secondsStr;
        String milisecondsStr;

        if (seconds < 10) {
            secondsStr = "0" + seconds;
        } else {
            secondsStr = "" + seconds;
        }

        if (miliseconds < 10) {
            milisecondsStr = "00" + miliseconds;
        } else if (miliseconds < 100) {
            milisecondsStr = "0" + miliseconds;
        } else {
            milisecondsStr = "" + miliseconds;
        }
        
        stoppedTime = minutes + ":" + secondsStr + ":" + milisecondsStr;
        stopped = true;
    }

    /**
     * Startet den Timers
     */
    public void startTimer() {
        stopped = false;
    }
}
