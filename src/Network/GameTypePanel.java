package Network;

import assets.Assets;
import main.MainFrame;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GameTypePanel extends JPanel {
    private MainFrame mainFrame;
    private Button singlePlayerButton, multiplayerButton;

    public GameTypePanel(MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
        init();
    }
    private void init(){
        this.setOpaque(false);

        this.setPreferredSize(new Dimension(1600, 500));

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        Assets assets = mainFrame.getAssets();

        singlePlayerButton = new Button(assets.getSinglePlayerButtonDefault(), assets.getSinglePlayerButtonHovered(), assets.getSinglePlayerButtonPressed(), mainFrame);
        multiplayerButton = new Button(assets.getmultiplayerButtonDefault(), assets.getmultiplayerButtonHovered(), assets.getmultiplayerButtonPressed(), mainFrame);

        singlePlayerButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.getUser().startNewGame();
                mainFrame.getUsers().save();
                mainFrame.setMultiplayer(false);
                mainFrame.initForGame();
            }
        });

        multiplayerButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 mainFrame.initForPlayerTypePanel();
            }
        });

        this.add(Box.createGlue());
        this.add(singlePlayerButton);
        this.add(Box.createGlue());
        this.add(multiplayerButton);
        this.add(Box.createGlue());
    }
}
