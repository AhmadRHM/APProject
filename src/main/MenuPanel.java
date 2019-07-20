package main;

import assets.Assets;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    private MainFrame mainFrame;
    private Button newGameButton, loadGameButton, settingsButton, rankingButton, quitButton;
    private JLabel logoPictureLabel;
    public MenuPanel(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        init();
    }
    private void init(){
        this.setOpaque(false);

        this.setPreferredSize(new Dimension(400, 1000));

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Assets assets = mainFrame.getAssets();

        newGameButton = new Button(assets.getNewGameButtonDefault(), assets.getNewGameButtonHovered(), assets.getNewGameButtonPressed(), mainFrame);
        if(mainFrame.getUser().isHasSavedGame())
            loadGameButton = new Button(assets.getLoadGameButtonDefault(), assets.getLoadGameButtonHovered(), assets.getLoadGameButtonPressed(), mainFrame);
        else
            loadGameButton = new Button(assets.getLoadGameButtonDisabled(), assets.getLoadGameButtonDisabled(), assets.getLoadGameButtonDisabled(), mainFrame);
        settingsButton = new Button(assets.getSettingsButtonDefault(), assets.getSettingsButtonHovered(), assets.getSettingsButtonPressed(), mainFrame);
        rankingButton = new Button(assets.getRankingButtonDefault(), assets.getRankingButtonHovered(), assets.getRankingButtonPressed(), mainFrame);
        quitButton = new Button(assets.getQuitButtonDefault(), assets.getQuitButtonHovered(), assets.getQuitButtonPressed(), mainFrame);
        logoPictureLabel = new JLabel(assets.getChickenInvadersLogo());

        newGameButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        rankingButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        logoPictureLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        newGameButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.initForGameTypePanel();
            }
        });
        if(mainFrame.getUser().isHasSavedGame())
            loadGameButton.addMouseListener(new MouseListenerForButton() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mainFrame.initForGame();
                }
            });
        settingsButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        rankingButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.initForRanking();
            }
        });
        quitButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        this.add(Box.createGlue());
        this.add(logoPictureLabel);
        this.add(Box.createGlue());
        this.add(newGameButton);
        this.add(Box.createGlue());
        this.add(loadGameButton);
        this.add(Box.createGlue());
        this.add(settingsButton);
        this.add(Box.createGlue());
        this.add(rankingButton);
        this.add(Box.createGlue());
        this.add(quitButton);
        this.add(Box.createGlue());
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
