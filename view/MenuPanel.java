package view;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.Box;

import javax.swing.JButton;
import javax.swing.JLabel;

// A MenuPanel osztály a játék főmenüjét reprezentálja, amely tartalmazza az új játék, betöltés, mentés és kilépés gombokat.
public class MenuPanel extends JPanel {
    
    /** Gomb egy teljesen új szimuláció elindításához. */
    private JButton newGameButton;
    
    /** Gomb egy korábban elmentett játékállás betöltéséhez. */
    private JButton loadGameButton;
    
    /** Gomb a jelenlegi játékállás elmentéséhez. */
    private JButton saveGameButton;
    
    /** Gomb a programból való kilépéshez. */
    private JButton exitButton;
    
    /** A játék címét megjelenítő felirat a menü tetején. */
    private JLabel titleLabel;

    /**
     * A MenuPanel konstruktora.
     * Inicializálja a gombokat és a címkéket, valamint beállítja a panel GridBagLayout elrendezését és háttérszínét.
     */
    public MenuPanel() {
        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout()); // Középre rendezés

        titleLabel = new JLabel("Plow Masters");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        saveGameButton = new JButton("Save Game");
        exitButton = new JButton("Exit");

        // Gombok hozzáadása a panelhez egy vertikális dobozban
        Box box = Box.createVerticalBox();
        box.add(titleLabel);
        box.add(Box.createRigidArea(new Dimension(0, 20)));
        box.add(newGameButton);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(loadGameButton);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(saveGameButton);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(exitButton);

        this.add(box);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, g2d);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getLoadGameButton() {
        return loadGameButton;
    }

    public JButton getSaveGameButton() {
        return saveGameButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}