package main;

import assets.Assets;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseCorrectRobot;
import overridedSwingComponents.MouseListenerForButton;
import overridedSwingComponents.RoundJTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

public class EsqFrame extends JFrame {
    private MainFrame mainFrame;
    private JPanel mainPanel;
    private final int width=800, height=500, arcw=100, arch=100;
    private JTextField usernameTextField;
    private Button okButton;
    private Button continueButton, settingsButton, quitButton;
    public EsqFrame(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        init();
    }
    private void clear(){
        if(mainPanel != null)
            mainPanel.removeAll();
    }
    private void init(){
        clear();
        this.setSize(new Dimension(800, 500));

        this.setUndecorated(true);

        this.setLocationRelativeTo(null);

        this.setResizable(false);

        this.setOpacity((float)0.5);

        this.setAlwaysOnTop(true);

        Shape shape = new RoundRectangle2D.Double(0, 0, width, height, arcw, arch);
        this.setShape(shape);

        mainPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics G){
                super.paintComponent(G);
                Graphics2D g2 = (Graphics2D)G;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(12,0, 59));
                for(int i=0; i<5; i++)
                    g2.drawRoundRect(i,i,width - 1 - 2*i,height -1 - 2*i,arcw,arch);
            }
        };
        mainPanel.setBackground(new Color(43, 13, 193));
        this.add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
    public void initForGettingUser(){
        clear();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        usernameTextField = new RoundJTextField("enter username",15);
        usernameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                usernameTextField.setText("");
            }
        });
        usernameTextField.setBackground(new Color(0, 49, 145));
        usernameTextField.setForeground(Color.WHITE);
        usernameTextField.setFont(new Font("ELLA", Font.PLAIN, 40));

        usernameTextField.setMaximumSize(new Dimension(usernameTextField.getSize().width, 60));
//        System.out.println(usernameTextField.getMinimumSize().height * 3);

        Assets assets = mainFrame.getAssets();
        okButton = new Button(assets.getOkButtonDefault(), assets.getOkButtonHovered(), assets.getOkButtonPressed(), mainFrame);
        okButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameTextField.getText();
                mainFrame.getUsers().addUser(username, mainFrame);
                mainFrame.getMainPanel().getUsersPanel().addUserLabels();
                mainFrame.revalidate();
                mainFrame.repaint();
                mainFrame.getEsqFrame().setVisible(false);
            }
        });
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(Box.createGlue());
        mainPanel.add(usernameTextField);
        mainPanel.add(Box.createGlue());
        mainPanel.add(okButton);
        mainPanel.add(Box.createGlue());
        mainPanel.add(Box.createHorizontalGlue());
    }
    public void initForInGameMenu(){
        clear();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        Assets assets = mainFrame.getAssets();

        continueButton = new Button(assets.getContinueButtonDefault(), assets.getContinueButtonHovered(), assets.getContinueButtonPressed(), mainFrame);
        settingsButton = new Button(assets.getSettingsButtonDefault(), assets.getSettingsButtonHovered(), assets.getSettingsButtonPressed(), mainFrame);
        quitButton = new Button(assets.getQuitButtonDefault(), assets.getQuitButtonHovered(), assets.getQuitButtonPressed(), mainFrame);

        continueButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        mainPanel.add(Box.createGlue());
        mainPanel.add(continueButton);
        mainPanel.add(Box.createGlue());
        mainPanel.add(settingsButton);
        mainPanel.add(Box.createGlue());
        mainPanel.add(quitButton);
        mainPanel.add(Box.createGlue());

        continueButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainPanel mainPanel = mainFrame.getMainPanel();
                mainPanel.setInGameMode(true);
                mainFrame.getEsqFrame().setVisible(false);
                try {
                    MouseCorrectRobot mouseCorrectRobot = new MouseCorrectRobot();
//                    Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
//                    mouseCorrectRobot.MoveMouseControlled((double)mainFrame.getLastMouseX()/ScreenSize.width, (double)mainFrame.getLastMouseY()/ScreenSize.height);
                    mouseCorrectRobot.myMouseMove(mainFrame.getLastMouseX(), mainFrame.getLastMouseY());
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        });
        settingsButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO fill this shit:D
            }
        });
        quitButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.save();
//                System.exit(0);
                mainFrame.getItems().clear();
                mainFrame.initForStartGameMenu();
                mainFrame.getEsqFrame().setVisible(false);
            }
        });

    }
}
