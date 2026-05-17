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
        // Háttérszín beállítása feketére
        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout()); // Középre igazítja a teljes menüdobozt az ablakban

        // Címke beállítása extra nagy (60-as méretű), BOLD, FEHÉR betűkkel
        titleLabel = new JLabel("Plow Masters");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));

        // Gombok példányosítása
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        saveGameButton = new JButton("Save Game");
        exitButton = new JButton("Exit");

        // Kilépés gomb logikája
        exitButton.addActionListener(e -> System.exit(0));

        // --- DINAMIKUS MÉRETEZÉS ÉS STÍLUSOK MEGNÖVELÉSE ---
        // Egységes nagy méret a gomboknak (szélesség: 250 pixel, magasság: 50 pixel)
        Dimension buttonSize = new Dimension(250, 50);
        // Nagyobb, tisztán olvasható betűtípus a gombok szövegeinek
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);

        // Egy ciklussal végigmegyünk a gombokon, és mindegyikre alkalmazzuk a nagy méretet
        JButton[] buttons = {newGameButton, loadGameButton, saveGameButton, exitButton};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setPreferredSize(buttonSize);
            btn.setMinimumSize(buttonSize);
            btn.setMaximumSize(buttonSize); // Küszöböli a BoxLayout átméretezési hibáját
            btn.setAlignmentX(Component.CENTER_ALIGNMENT); // Középre igazítás a dobozon belül
        }

        // Címke belső dobozon belüli középre igazítása
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Elemek elrendezése egy függőleges dobozban (Box)
        Box box = Box.createVerticalBox();
        
        // Összeállítás nagyobb térközökkel (RigidArea)
        box.add(titleLabel);
        box.add(Box.createRigidArea(new Dimension(0, 50))); // 50 pixel térköz a cím alatt
        box.add(newGameButton);
        box.add(Box.createRigidArea(new Dimension(0, 15))); // 15 pixel térköz a gombok között
        box.add(loadGameButton);
        box.add(Box.createRigidArea(new Dimension(0, 15)));
        box.add(saveGameButton);
        box.add(Box.createRigidArea(new Dimension(0, 15)));
        box.add(exitButton);

        this.add(box);
    }
    
    /*
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }*/

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