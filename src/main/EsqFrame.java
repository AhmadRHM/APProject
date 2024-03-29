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
import java.io.File;
import java.net.MalformedURLException;

public class EsqFrame extends JFrame {
    private MainFrame mainFrame;
    private JPanel mainPanel;
    private final int width=800, height=500, arcw=100, arch=100;
    private JTextField usernameTextField;
    private Button okButton;
    private Button continueButton, settingsButton, quitButton, addBossButton, addGroupButton;
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

    private RoundJTextField classNameTextField;
    private JFileChooser fileChooser;

    public MainFrame getMainFrame() {
        return mainFrame;
    }
    public void initForAddingBoss(){
        clear();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        classNameTextField = new RoundJTextField("enter class name",15);
        classNameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                classNameTextField.setText("");
            }
        });
        classNameTextField.setBackground(new Color(0, 49, 145));
        classNameTextField.setForeground(Color.WHITE);
        classNameTextField.setFont(new Font("ELLA", Font.PLAIN, 40));

        classNameTextField.setMaximumSize(new Dimension(classNameTextField.getSize().width, 60));
//        System.out.println(usernameTextField.getMinimumSize().height * 3);

        Assets assets = mainFrame.getAssets();
        okButton = new Button(assets.getOkButtonDefault(), assets.getOkButtonHovered(), assets.getOkButtonPressed(), mainFrame);
        okButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int returnVal = fileChooser.showOpenDialog(mainPanel);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if(!mainFrame.isMultiplayer() || mainFrame.isServer()) {
                        mainFrame.addBigegg(file, classNameTextField.getText());
                    }else{
                        mainFrame.setShouldSendBigEgg(true);
                        MyClassLoader myClassLoader = new MyClassLoader();
                        try {
                            mainFrame.setData(myClassLoader.getClassData(file.toURI().toURL(), classNameTextField.getText()));
                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                        }
                        mainFrame.setClassName(classNameTextField.getText());
                    }
                    setVisible(false);
                    unPause();
                }
            }
        });

        fileChooser = new JFileChooser();

        mainPanel.add(Box.createGlue());
        mainPanel.add(classNameTextField);
        mainPanel.add(Box.createGlue());
        mainPanel.add(okButton);
        mainPanel.add(Box.createGlue());

        revalidate();
        repaint();
    }
    public void initForAddingGroup(){
        clear();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        classNameTextField = new RoundJTextField("enter class name",15);
        classNameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                classNameTextField.setText("");
            }
        });
        classNameTextField.setBackground(new Color(0, 49, 145));
        classNameTextField.setForeground(Color.WHITE);
        classNameTextField.setFont(new Font("ELLA", Font.PLAIN, 40));

        classNameTextField.setMaximumSize(new Dimension(classNameTextField.getSize().width, 60));
//        System.out.println(usernameTextField.getMinimumSize().height * 3);

        Assets assets = mainFrame.getAssets();
        okButton = new Button(assets.getOkButtonDefault(), assets.getOkButtonHovered(), assets.getOkButtonPressed(), mainFrame);
        okButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int returnVal = fileChooser.showOpenDialog(mainPanel);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if(!mainFrame.isMultiplayer() || mainFrame.isServer()) {
                        mainFrame.addChickenGroup(file, classNameTextField.getText());
                    }else{
                        mainFrame.setShouldSendChickenGroup(true);
                        MyClassLoader myClassLoader = new MyClassLoader();
                        try {
                            mainFrame.setData(myClassLoader.getClassData(file.toURI().toURL(), classNameTextField.getText()));
                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                        }
                        mainFrame.setClassName(classNameTextField.getText());
                    }
                    setVisible(false);
                    unPause();
                }
            }
        });

        fileChooser = new JFileChooser();

        mainPanel.add(Box.createGlue());
        mainPanel.add(classNameTextField);
        mainPanel.add(Box.createGlue());
        mainPanel.add(okButton);
        mainPanel.add(Box.createGlue());

        revalidate();
        repaint();
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
        addBossButton = new Button(assets.getAddBossButtonDefault(), assets.getAddBossButtonHovered(), assets.getAddBossButtonPressed(), mainFrame);
        addGroupButton = new Button(assets.getAddGroupButtonDefault(), assets.getAddGroupButtonHovered(), assets.getAddGroupButtonPressed(), mainFrame);

        continueButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        addBossButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        addGroupButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        mainPanel.add(Box.createGlue());
        mainPanel.add(continueButton);
        mainPanel.add(Box.createGlue());
        mainPanel.add(settingsButton);
        mainPanel.add(Box.createGlue());
        mainPanel.add(addBossButton);
        mainPanel.add(Box.createGlue());
        mainPanel.add(addGroupButton);
        mainPanel.add(Box.createGlue());
        mainPanel.add(quitButton);
        mainPanel.add(Box.createGlue());

        continueButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mainFrame.isMultiplayer() && !mainFrame.isServer()){
                    mainFrame.setPaused(false);
                }
                unPause();
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
        addBossButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initForAddingBoss();
            }
        });
        addGroupButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initForAddingGroup();
            }
        });

    }
    public void unPause(){
        MainPanel mainPanel = mainFrame.getMainPanel();
        mainPanel.setInGameMode(true);
        mainFrame.setPaused(false);
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
}
