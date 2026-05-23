package view;

import model.Game;
import model.Player;
import model.SnowCleaner;
import controller.GameObserver;
import javax.swing.*;
import java.awt.*;

/**
 * A StatusPanel kizárólag a játék állásának (pénz, körök) megjelenítéséért felelős.
 * Ez a tiszta VIEW réteg része, amely frissül a Modell változásakor.
 */
public class StatusPanel extends JPanel implements GameObserver {
    private Game game;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel statusLabel;

    public StatusPanel(Game game) {
        this.game = game;
        game.addObserver(this); // Feliratkozás a modellre

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        player1Label = new JLabel("Player1  1500$");
        player1Label.setFont(new Font("Arial", Font.BOLD, 22));
        player1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        player2Label = new JLabel("0 rounds");
        player2Label.setFont(new Font("Arial", Font.BOLD, 22));
        player2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(player1Label);
        this.add(player2Label);
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
        player2Label.setText(game.getCurrentRound() + " rounds");

        Player currenPlayer = game.getCurrentPlayer();
        if (currenPlayer != null) {
            setStatusText("Current Player: " + currenPlayer.getName());
        } else {
            setStatusText("Current Player: None");
        }

        // Pénz frissítése
        if (game.getPlayers() != null && !game.getPlayers().isEmpty()) {
            SnowCleaner p1 = (SnowCleaner) game.getPlayers().get(0);
            player1Label.setText("Player1  " + p1.getMoney() + "$");
        }
    }
}
