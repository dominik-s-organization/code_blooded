package view;

import model.Game;
import controller.GameObserver;
import javax.swing.*;
import java.awt.*;

/**
 * A StatusPanel kizárólag a játék állásának (játékos neve, vagyona, körök száma) 
 * megjelenítéséért felelős. Ez a tiszta VIEW réteg része, amely az Observer 
 * mintának köszönhetően automatikusan frissül a Modell változásakor.
 */
public class StatusPanel extends JPanel implements GameObserver {
    private Game game;
    private JLabel activePlayerLabel;
    private JLabel roundLabel;
    private JLabel statusLabel;
    /**
     * A StatusPanel konstruktora. Inicializálja a címkéket, beállítja a tipográfiát
     * és azonnal feliratkozik a Modell változásaira.
     * @param game A Game objektum, amelyből a panel kiolvassa a státuszokat.
     */
    public StatusPanel(Game game) {
        this.game = game;
        game.addObserver(this); 

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        activePlayerLabel = new JLabel("Active Player: Player1");
        activePlayerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        activePlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        roundLabel = new JLabel("0 rounds");
        roundLabel.setFont(new Font("Arial", Font.BOLD, 22));
        roundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(activePlayerLabel);
        this.add(roundLabel);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(statusLabel);

        update(); // Első frissítés
    }
    /**
     * Manuálisan frissíti a státusz szövegét (pl. interakció visszajelzések).
     * @param text A kiírandó státusz szöveg.
     */
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }
    /**
     * Az Observer minta metódusa. Ha a Game osztály jelzi, hogy változás történt 
     * (pl. kör vége vagy játékosváltás), ez a metódus frissíti a kijelzőt.
     */
    @Override
    public void update() {
        if (game == null) return;

        roundLabel.setText(game.getCurrentRound() + " rounds");
        model.Player activePlayer = game.getCurrentPlayer();

        if (activePlayer != null) {
            activePlayerLabel.setText(activePlayer.getMainInfo());
            statusLabel.setText(activePlayer.getSubStatusInfo());
        }
    }
}