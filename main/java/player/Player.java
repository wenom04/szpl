package main.java.player;


import main.java.Nameable;
import main.java.Updatable;

/**
 * Az absztrakt Player osztály egy általános játékost reprezentál, amelynek van neve, pontszáma
 * és hátralévő akciói. Kétféle játékos származhat belőle: Shroomer és Insecter.
 */
public abstract class Player extends Nameable implements Updatable {

    public int score;
    public int remainingActions;

    /**
     * Létrehoz egy új játékost.
     *
     * @param name a játékos neve
     * @param testing ha true, akkor a játékos gyakorlatilag végtelen akcióval rendelkezik. Teszteléshez kell.
     */
     Player(String name, boolean testing) {
        setName(name);
        this.score = 0;
        update(testing);
    }
    
    /**
     * Visszaadja a játékos pontszámát.
     *
     * @return a játékos aktuális pontszáma
     */
    public int getScore() {
        return score;
    }

    /**
     * Beállítja a játékos pontszámát.
     *
     * @param score az új pontszám
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Visszaadja a játékos hátralévő akcióinak számát.
     *
     * @return a hátralévő akciók száma
     */
    public int getActions() {
        return remainingActions;
    }

    /**
     * Beállítja a játékos hátralévő akcióinak számát.
     *
     * @param actions az új akciószám
     */
    public void setActions(int actions) {
        this.remainingActions = actions;
    }

    /**
     * Csökkenti a hátralévő akciók számát eggyel.
     */
    public void takeAction() {
        remainingActions--;
    }

    /**
     * Passzolja a játékos körét: az akciók száma nullára csökken.
     */
    public void pass() {
        remainingActions = 0;
    }
    
    /**
     * Az Updatable interfész felüldefiniált update függvénye. 
     * Visszaállítja a játékos akcióinak számát alapértelmezettre.
     *
     * @param testing A tesztelő állapot eldöntését meghatározó boolean.
     */
    public void update(boolean testing) {
    	if(!testing) {
        	setActions(3);
        }
        else {
        	setActions(10000);
        }
    }
}