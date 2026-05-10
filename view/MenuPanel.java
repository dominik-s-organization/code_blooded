package view;

import javax.swing.JPanel;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;


// A MenuPanel osztály a játék főmenüjét reprezentálja, amely tartalmazza az új játék, betöltés, mentés és kilépés gombokat.
public class MenuPanel extends JPanel {
    JButton newGameButton;
    JButton LoadGameButton;
    JButton SaveGameButton;
    JButton exitButton;
    JLabel titleLabel;

    // A MenuPanel konstruktorában inicializáljuk a gombokat és a címkéket, valamint beállítjuk a panel elrendezését és háttérszínét.
    public MenuPanel() {
        newGameButton = new JButton("New Game");
        LoadGameButton = new JButton("Load Game");
        SaveGameButton = new JButton("Save Game");
        exitButton = new JButton("Exit");
        titleLabel = new JLabel("Plow Masters");
        this.add(titleLabel);
        this.add(newGameButton);
        this.add(LoadGameButton);
        this.add(SaveGameButton);
        this.add(exitButton);
        this.setBackground(new Color(0, 0, 0));
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
    }

}
