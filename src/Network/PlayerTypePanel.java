package Network;

import assets.Assets;
import main.MainFrame;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PlayerTypePanel extends JPanel {
    private MainFrame mainFrame;
    private Button serverButton, clientButton;

    public PlayerTypePanel(MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
        init();
    }
    private void init(){
        this.setOpaque(false);

        this.setPreferredSize(new Dimension(1600, 500));

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        Assets assets = mainFrame.getAssets();

        serverButton = new Button(assets.getserverButtonDefault(), assets.getserverButtonHovered(), assets.getserverButtonPressed(), mainFrame);
        clientButton = new Button(assets.getclientButtonDefault(), assets.getclientButtonHovered(), assets.getclientButtonPressed(), mainFrame);

        serverButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.initForServerDataPanel();
            }
        });

        clientButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.initForClientDataPanel();
            }
        });

        this.add(Box.createGlue());
        this.add(clientButton);
        this.add(Box.createGlue());
        this.add(serverButton);
        this.add(Box.createGlue());
    }
}
