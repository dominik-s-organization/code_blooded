package view;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.Box;

import model.Console;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 * A MenuPanel osztály a játék főmenüjét reprezentálja.
 * Ez a nézet tartalmazza az új játék indításához, a korábbi állások betöltéséhez, 
 * a mentéshez, valamint a kilépéshez szükséges grafikus elemeket (gombokat).
 */
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

    private MainFrame mainFrame;

    private Console console;
   /**
    * A MenuPanel konstruktora.
    * Inicializálja a gombokat és a címkéket, beállítja az eseménykezelőket,
    * valamint konfigurálja a panel GridBagLayout elrendezését és hátterét.
    * @param console A konzol objektum a mentési parancsok feldolgozásához.
    * @param mainframe A főablak referenciája a nézetek közötti váltáshoz.
    *//
   public MenuPanel(Console console, MainFrame mainframe) {
        this.console = console;
        this.mainFrame = mainframe;
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

        saveGameButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Game save");
            int userSelection = fileChooser.showSaveDialog(this);

            if(userSelection == JFileChooser.APPROVE_OPTION){
                // ÚJRA A TELJES UTAT KÉRJÜK (getAbsolutePath)
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String command = "save " + filePath;
                if(this.console != null){
                    this.console.processCommand(command);
                    System.out.println("Mentés parancs kiadva: " + command);
                } 
            }
        });

        loadGameButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Game load");
            int userSelection = fileChooser.showOpenDialog(this);

            if(userSelection == JFileChooser.APPROVE_OPTION){
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String command = "load " + filePath;
                if(this.console != null){
                    this.console.processCommand(command);
                    System.out.println("Betöltés parancs kiadva: " + command);
                }
                if(this.mainFrame != null){
                    this.mainFrame.showGamePanel();
                }
            }
        });

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