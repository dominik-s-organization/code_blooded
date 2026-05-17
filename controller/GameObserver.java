package controller;

/**
 * A GameObserver interfész a megfigyelő minta (Observer) részét képezi.
 * Ezt az interfészt valósítják meg a grafikus nézetek (View), 
 * amelyek értesülni szeretnének a modell (Game) állapotváltozásairól.
 */
public interface GameObserver {
    
    /**
     * Ez a metódus hívódik meg a modellen (Subject), amikor a játék állapota megváltozik.
     * A nézet implementálja ezt a metódust a képernyő frissítéséhez (repaint).
     */
    public void update();
}