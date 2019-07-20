package Network;

import assets.Assets;
import main.MainFrame;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;
import overridedSwingComponents.RoundJTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ServerDataPanel extends JPanel {
    MainFrame mainFrame;
    JLabel playerCountLabel, portLabel, numberOfLevelsLabel;
    JTextField playerCountTextField, portTextField, numberOfLevelsTextField;
    Button makeGameButton;
    public ServerDataPanel(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        this.setOpaque(false);

        this.setPreferredSize(new Dimension(600, 1000));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        Assets assets = mainFrame.getAssets();

        Font font = new Font("ELLA", Font.PLAIN, 40);
        playerCountLabel = new JLabel("maximum player count:  ");
        portLabel = new JLabel("port:  ");
        numberOfLevelsLabel = new JLabel("number of levels:  ");
        playerCountLabel.setFont(font);
        playerCountLabel.setForeground(Color.WHITE);
        portLabel.setFont(font);
        portLabel.setForeground(Color.WHITE);
        numberOfLevelsLabel.setFont(font);
        numberOfLevelsLabel.setForeground(Color.WHITE);


        playerCountTextField = new RoundJTextField("20", 5);
        playerCountTextField.setBackground(new Color(0, 49, 145));
        playerCountTextField.setForeground(Color.WHITE);
        playerCountTextField.setFont(font);

        portTextField = new RoundJTextField("9090", 5);
        portTextField.setBackground(new Color(0, 49, 145));
        portTextField.setForeground(Color.WHITE);
        portTextField.setFont(font);

        numberOfLevelsTextField = new RoundJTextField("4", 5);
        numberOfLevelsTextField.setBackground(new Color(0, 49, 145));
        numberOfLevelsTextField.setForeground(Color.WHITE);
        numberOfLevelsTextField.setFont(font);

        makeGameButton = new Button(assets.getmakegameButtonDefault(), assets.getmakegameButtonHovered(), assets.getmakegameButtonPressed(), mainFrame);

        makeGameButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.startServer(Integer.valueOf(playerCountTextField.getText()), Integer.valueOf(portTextField.getText()));
                mainFrame.setNumberOfLevels(Integer.valueOf(numberOfLevelsTextField.getText()));
                mainFrame.initForServerDetalePanel();
            }
        });

        gc.weightx = 1;
        gc.weighty = 1;


        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;

        gc.gridy = 0;
        this.add(playerCountLabel, gc);

        gc.gridy = 1;
        this.add(portLabel, gc);

        gc.gridy = 2;
        this.add(numberOfLevelsLabel, gc);

        gc.gridy = 3;
        this.add(makeGameButton, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridy = 0;
        this.add(playerCountTextField, gc);

        gc.gridy = 1;
        this.add(portTextField, gc);

        gc.gridy = 2;
        this.add(numberOfLevelsTextField, gc);


    }
}
