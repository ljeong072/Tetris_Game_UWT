package view;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import model.Appearance;
import model.AppearanceClass;

final class MenuBar extends JMenuBar {
    /**
     * Instance of application's appearance class.
     */
    private static final AppearanceClass APPEARANCE = AppearanceClass.getInstance();

    MenuBar() {
        super();
        add(buildFileMenu());
        add(buildWindowMenu());
        add(buildHelpMenu());
    }

    /**
     * Build a file menu.
     * @return the menu.
     */
    private JMenuItem buildFileMenu() {
        final JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);

        menu.add(buildMenuItem("New Game", KeyEvent.VK_N, theEvent -> Tetris.startGame()));
        menu.add(buildMenuItem("Toggle Pause",
                KeyEvent.VK_P, theEvent -> Tetris.togglePause()));
        menu.add(buildMenuItem("End Game", KeyEvent.VK_E, theEvent -> Tetris.endGame(true)));

        menu.add(new JSeparator());
        menu.add(buildMenuItem("Toggle Mute", KeyEvent.VK_M,
                theEvent -> Tetris.muteMusic()));

        menu.add(new JSeparator());
        menu.add(buildMenuItem("Change Player Name", theEvent -> NameFrame.toggleShow()));

        menu.add(new JSeparator());
        menu.add(buildMenuItem("Close", KeyEvent.VK_X,
            theEvent -> Tetris.WINDOW.dispatchEvent(
                new WindowEvent(Tetris.WINDOW, WindowEvent.WINDOW_CLOSING)
            )
        ));

        return menu;
    }

    /**
     * Build a view menu.
     * @return the menu.
     */
    private JMenuItem buildWindowMenu() {
        final JMenu menu = new JMenu("View");
        menu.setMnemonic(KeyEvent.VK_V);

        menu.add(buildMenuItem("Leaderboard", KeyEvent.VK_L,
                theEvent -> LeaderboardFrame.toggleShow()));
        menu.add(new JSeparator());
        menu.add(buildThemeSubmenu());

        return menu;
    }

    /**
     * Build a help menu.
     * @return the menu.
     */
    private JMenuItem buildHelpMenu() {
        final JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        menu.add(buildMenuItem("About Music and Sounds",
                theEvent -> AboutMusicFrame.toggleShow()));
        menu.add(buildMenuItem("About Scoring",
                theEvent -> AboutScoreFrame.toggleShow()));
        menu.add(buildMenuItem("Game Controls", KeyEvent.VK_G,
                theEvent -> GameControlsFrame.toggleShow()));
        return menu;
    }

    /**
     * Build an appearance submenu.
     * @return the submenu.
     */
    private JMenu buildThemeSubmenu() {
        final JMenu themeMenu = new JMenu("Appearance");

        themeMenu.add(buildMenuItem("Light",
                theEvent -> APPEARANCE.setTheme(Appearance.LIGHT)));
        themeMenu.add(buildMenuItem("Dark",
                theEvent -> APPEARANCE.setTheme(Appearance.DARK)));

        return themeMenu;
    }

    /**
     * Builds a menu item.
     * @return a menu item.
     */
    private JMenuItem buildMenuItem(
            final String theTitle,
            final ActionListener theAction
    ) {
        final JMenuItem item = new JMenuItem(theTitle);
        item.addActionListener(theAction);

        return item;
    }

    /**
     * Builds a menu item.
     * @return a menu item.
     */
    private JMenuItem buildMenuItem(
        final String theTitle,
        final int theKeyCode,
        final ActionListener theAction
    ) {
        final JMenuItem item = new JMenuItem(theTitle);
        item.setAccelerator(
            KeyStroke.getKeyStroke(
                theKeyCode,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()
            )
        );
        item.addActionListener(theAction);

        return item;
    }
}