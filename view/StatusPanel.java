package view;

import model.Game;
import controller.GameObserver;
import javax.swing.*;
import java.awt.*;

/**
 * A StatusPanel kizárólag a játék állásának (pénz, körök) megjelenítéséért felelős.
 * Ez a tiszta VIEW réteg része, amely frissül a Modell változásakor.
 */
public class StatusPanel extends JPanel implements GameObserver {
    private Game game;
    private JLabel activePlayerLabel;
    private JLabel roundLabel;
    private JLabel statusLabel;

    public StatusPanel(Game game) {
        this.game = game;
        game.addObserver(this); // Feliratkozás a modellre

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

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    @Override
    public void update() {
        if (game == null) return;

        // Körök frissítése
        roundLabel.setText(game.getCurrentRound() + " rounds");
        model.Player activePlayer = game.getCurrentPlayer();

        // Pénz és név frissítése
        if (activePlayer != null) {
            activePlayerLabel.setText(activePlayer.getMainInfo());
            statusLabel.setText(activePlayer.getSubStatusInfo());
        }
    }
}