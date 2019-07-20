package Network;

import assets.Assets;
import main.MainFrame;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;
import overridedSwingComponents.RoundJTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ClientDataPanel extends JPanel {
    MainFrame mainFrame;
    JLabel ipLabel, portLabel, spectatorLabel;
    JTextField ipTextField, portTextField;
    JCheckBox spectatorCheckBox;
    Button connectButton;

    public ClientDataPanel(MainFrame mainFrame){
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
        ipLabel = new JLabel("ip:  ");
        portLabel = new JLabel("port:  ");
        spectatorLabel = new JLabel("spectator:  ");
        ipLabel.setFont(font);
        ipLabel.setForeground(Color.WHITE);
        portLabel.setFont(font);
        portLabel.setForeground(Color.WHITE);
        spectatorLabel.setFont(font);
        spectatorLabel.setForeground(Color.WHITE);


        ipTextField = new RoundJTextField("127.0.0.1", 10);
        ipTextField.setBackground(new Color(0, 49, 145));
        ipTextField.setForeground(Color.WHITE);
        ipTextField.setFont(font);

        portTextField = new RoundJTextField("9090", 10);
        portTextField.setBackground(new Color(0, 49, 145));
        portTextField.setForeground(Color.WHITE);
        portTextField.setFont(font);

        spectatorCheckBox = new JCheckBox();
//        spectatorCheckBox.setBackground(new Color(0, 49, 145));
//        spectatorCheckBox.setForeground(Color.WHITE);
//        spectatorCheckBox.setFont(font);

        connectButton = new Button(assets.getconnectButtonDefault(), assets.getconnectButtonHovered(), assets.getconnectButtonPressed(), mainFrame);

        connectButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.startClient(ipTextField.getText(), Integer.valueOf(portTextField.getText()), spectatorCheckBox.isSelected());
                System.out.println(mainFrame.getPlayingUsers().size());
                mainFrame.initForServerDetalePanel();
            }
        });

        gc.weightx = 1;
        gc.weighty = 1;


        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;

        gc.gridy = 0;
        this.add(ipLabel, gc);

        gc.gridy = 1;
        this.add(portLabel, gc);

        gc.gridy = 2;
        this.add(spectatorLabel, gc);

        gc.gridy = 3;
        this.add(connectButton, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridy = 0;
        this.add(ipTextField, gc);

        gc.gridy = 1;
        this.add(portTextField, gc);

        gc.gridy = 2;
        this.add(spectatorCheckBox, gc);
    }
}
