package controller;

// A GameObserver interfész a megfigyelőket definiálja, akik értesülnek a játék állapotváltozásairól.
public interface GameObserver {
    // Ez a metódus hívódik meg, amikor a játék állapota megváltozik, és a nézet frissíteni szeretné magát.
    public void update();
}
