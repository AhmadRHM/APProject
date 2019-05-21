package users;

import assets.Assets;
import main.EsqFrame;
import main.MainFrame;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    private overridedSwingComponents.Button addPlayerButton, deletePlayerButton, selectPlayerButton;
    private MainFrame mainFrame;
    public MenuPanel(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        init();
    }
    private void init(){
        this.setPreferredSize(new Dimension(1600, 200));
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setOpaque(false);
//        this.setOp
        Assets assets = mainFrame.getAssets();

        addPlayerButton = new overridedSwingComponents.Button(assets.getAddPlayerButtonDefault(), assets.getAddPlayerButtonHovered(), assets.getAddPlayerButtonPressed(), mainFrame);
        deletePlayerButton = new overridedSwingComponents.Button(assets.getDeletePlayerButtonDefault(), assets.getDeletePlayerButtonHovered(), assets.getDeletePlayerButtonPressed(), mainFrame);
        selectPlayerButton = new Button(assets.getSelectPlayerButtonDefault(), assets.getSelectPlayerButtonHovered(), assets.getSelectPlayerButtonPressed(), mainFrame);

        selectPlayerButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selected = mainFrame.getMainPanel().getUsersPanel().getSelected();
                mainFrame.setUser(mainFrame.getUsers().getUsers().get(selected));
                mainFrame.initForStartGameMenu();
            }
        });
        addPlayerButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EsqFrame esqFrame = mainFrame.getEsqFrame();
                esqFrame.initForGettingUser();
                esqFrame.setVisible(true);
            }
        });
        deletePlayerButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selected = mainFrame.getMainPanel().getUsersPanel().getSelected();
                if(selected != -1){
                    mainFrame.getUsers().deleteUser(selected);
                    mainFrame.getMainPanel().getUsersPanel().addUserLabels();
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    mainFrame.getMainPanel().getUsersPanel().setFocus(-1);
                    mainFrame.getMainPanel().getUsersPanel().setSelected();
                }
            }
        });

        this.add(Box.createGlue());
        this.add(selectPlayerButton);
        this.add(Box.createGlue());
        this.add(addPlayerButton);
        this.add(Box.createGlue());
        this.add(deletePlayerButton);
        this.add(Box.createGlue());
    }
}
