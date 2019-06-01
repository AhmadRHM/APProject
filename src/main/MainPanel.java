package main;

import assets.Assets;
import shuttles.DataBar;
import shuttles.HeatBar;
import shuttles.Shuttle;
import users.MenuPanel;
import users.Users;
import users.UsersPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;

public class MainPanel extends JPanel {
    private MainFrame mainFrame;
    private Assets assets;
    private int yOfBackground = 2000;
    private UsersPanel usersPanel;
    private MenuPanel menuPanel;
    private main.MenuPanel mainMenuPanel;
    private boolean inGameMode;
    private Shuttle shuttle;
    MainPanel(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        inGameMode = false;
        init();
    }
    private void init(){
        assets = mainFrame.getAssets();
        this.setPreferredSize(new Dimension(mainFrame.getWidth(), mainFrame.getHeight()));
        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
//                System.out.println(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //38->up  40->down 10->enter 32->space
//                System.out.println(e.getKeyCode());
                int code = e.getKeyCode();
                if(!inGameMode) {
                    if (code == 38)
                        usersPanel.focusDown();
                    if (code == 40)
                        usersPanel.focusUp();
                    if (code == 10 || code == 32)
                        usersPanel.setSelected();
                }
                if(inGameMode) {
                    if (code == 27) {
                        inGameMode = false;
                        mainFrame.getEsqFrame().initForInGameMenu();
                        int x = MouseInfo.getPointerInfo().getLocation().x, y = MouseInfo.getPointerInfo().getLocation().y;
                        mainFrame.setLastMouseX(x);
                        mainFrame.setLastMouseY(y);
                        mainFrame.getEsqFrame().setVisible(true);
                    }
                    if(code == 10){
//                        System.out.println("dooo");
                        shuttle.shootRocket();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("assets/mouse.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Cursor c = toolkit.createCustomCursor(img, new Point(getX(), getY()), "image");
//        setCursor(c);
    }
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setYOfBackground(int deltaYOfBackground) {
        this.yOfBackground -= deltaYOfBackground;
        int height = assets.getBackgroundImage().getHeight();
        if(yOfBackground<=0)
            yOfBackground += 2000;
    }

    @Override
    public void paintComponent(Graphics G){
        super.paintComponent(G);
        Graphics2D g2 = (Graphics2D)G;
        g2.drawImage(assets.getBackgroundImage(), 0, -1*yOfBackground, this);
//        if(inGameMode){
        for(Drawable drawable : mainFrame.getItems().getItems())
            if(!(drawable instanceof DataBar || drawable instanceof HeatBar))
                drawable.draw(g2);
        for (Drawable drawable : mainFrame.getItems().getItems())
            if(drawable instanceof DataBar || drawable instanceof HeatBar)
                drawable.draw(g2);
//        }
    }

    public UsersPanel getUsersPanel() {
        return usersPanel;
    }

    public void setUsersPanel(UsersPanel usersPanel) {
        this.usersPanel = usersPanel;
    }

    public void setMenuPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }
    public void clear(){
        this.removeAll();
    }
    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public main.MenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public void setMainMenuPanel(main.MenuPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
    }

    public void setShuttle(Shuttle shuttle) {
        this.shuttle = shuttle;
    }

    public void setInGameMode(boolean inGameMode) {
        this.inGameMode = inGameMode;
    }

    public boolean isInGameMode(){
        return inGameMode;
    }

    public Shuttle getShuttle(){
        return  shuttle;

    }
}
