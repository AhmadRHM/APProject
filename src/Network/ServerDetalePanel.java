package Network;

import assets.Assets;
import main.MainFrame;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;
import users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ServerDetalePanel extends JPanel {
    MainFrame mainFrame;
    JLabel joinedLabel, spectatorsLabel;
    ArrayList<JLabel> userLabels, spectatorLabels;
    Button startGameButton;
    public ServerDetalePanel(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        userLabels = new ArrayList<>();
        spectatorLabels = new ArrayList<>();
        this.setOpaque(false);

        this.setPreferredSize(new Dimension(1000, 1000));

        this.setLayout(new GridBagLayout());

        Font font = new Font("ELLA", Font.PLAIN, 40);

        joinedLabel = new JLabel("joined players");
        joinedLabel.setFont(font);
        joinedLabel.setForeground(Color.WHITE);

        spectatorsLabel = new JLabel("spectators");
        spectatorsLabel.setFont(font);
        spectatorsLabel.setForeground(Color.WHITE);
        initLabels();


    }
    public void initLabels(){
        if(mainFrame.getPlayingUsers().size() == userLabels.size() && mainFrame.getSpectatingUsers().size() == spectatorLabels.size()) {
            return;
        }
        removeAll();
        userLabels.clear();
        spectatorLabels.clear();
        GridBagConstraints gc = new GridBagConstraints();

        Assets assets = mainFrame.getAssets();

        Font font = new Font("ELLA", Font.PLAIN, 40);
        for(User user : mainFrame.getPlayingUsers()) {
            JLabel label = new JLabel(user.getUsername());
            label.setFont(font);
            label.setForeground(Color.WHITE);
            userLabels.add(label);
        }
        for(User user : mainFrame.getSpectatingUsers()) {
            JLabel label = new JLabel(user.getUsername());
            label.setFont(font);
            label.setForeground(Color.WHITE);
            spectatorLabels.add(label);
        }

        gc.weighty = 1;
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.CENTER;

        gc.gridx = 0;

        gc.gridy = 0;
        this.add(joinedLabel, gc);

        for(int i=0; i<userLabels.size(); i++){
            gc.gridy = i+1;
            this.add(userLabels.get(i), gc);
        }

        if(mainFrame.getId() == 0){
            startGameButton = new Button(assets.getmakegameButtonDefault(), assets.getmakegameButtonHovered(), assets.getmakegameButtonPressed(), mainFrame);
            startGameButton.addMouseListener(new MouseListenerForButton() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mainFrame.initForGame();
                }
            });
            gc.gridy = 1 + Math.max(userLabels.size(), spectatorLabels.size());
            this.add(startGameButton, gc);
        }

        gc.gridx = 1;

        gc.gridy = 0;
        this.add(spectatorsLabel, gc);

        for(int i=0; i<spectatorLabels.size(); i++){
            gc.gridy = i+1;
            this.add(spectatorLabels.get(i), gc);
        }
    }
}
