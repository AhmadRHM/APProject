package main;

import assets.Assets;
import chickenGroups.Chicken;
import chickenGroups.Egg;
import shuttles.DataBar;
import shuttles.Shuttle;
import shuttles.Tir;
import shuttles.HeatBar;
import users.MenuPanel;
import users.User;
import users.Users;
import users.UsersPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ConcurrentModificationException;

public class MainFrame extends JFrame {
    private int width = 1600, height = 1000;
    private Assets assets;
    private MainPanel mainPanel;
    private double backgroundSpeed;
    private Users users;
    private User user;
    private EsqFrame esqFrame;
    private Items items;
    MainFrame(){
        super();
        init();
    }
    private void init(){
        this.setTitle("Chicken Invaders AhmadRH Edition :D");

        this.setSize(new Dimension(width, height));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setResizable(false);

        this.assets = new Assets();

        mainPanel = new MainPanel(this);
        this.add(mainPanel);

        users = new Users();
//        users.addUser("ahmad", this);
//        users.addUser("rahim", this);
//        users.addUser("ali", this);

        initForUserSelection();

        backgroundSpeed = 1;

        items = new Items();
//        removeList = new ArrayList<>();
    }
    public void setBackgroundSpeed(double backgroundSpeed){
        this.backgroundSpeed = backgroundSpeed;
    }
    public Assets getAssets() {
        return assets;
    }
    private Thread animationThread;
    private void initAnimationThread(){
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        mainPanel.setYOfBackground((int)(2.5*backgroundSpeed));

                        if(mainPanel.isInGameMode()) {
                            try {
                                for (Drawable drawable : items.getItems()) {
                                    drawable.update(0.005);

                                    //deleting bad items
                                    if (drawable instanceof Tir) {
                                        if (((Tir) drawable).remove())
                                            items.remove(drawable);
                                    }else if(drawable instanceof Egg)
                                        if(((Egg) drawable).remove())
                                            items.remove(drawable);
                                }
                            } catch (ConcurrentModificationException e) {
                                e.printStackTrace();
                            }
                        }
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        animationThread.sleep(5);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void startAnimation(){
        if(animationThread == null)
            initAnimationThread();
        animationThread.start();
    }
    public void stopAnimation(){
        if(animationThread != null){
            animationThread.stop();
        }
    }

    public void userSelected(String user){
        //TODO fill this shit :D
        System.out.println("User " + user + " is selected! :))");
    }

    public Users getUsers() {
        return users;
    }

    private void initForUserSelection(){
        mainPanel.setLayout(new BorderLayout());
        UsersPanel usersPanel = new UsersPanel(this);
        mainPanel.setUsersPanel(usersPanel);
        mainPanel.add(usersPanel, BorderLayout.NORTH);
        System.out.println("UsersPanel added to mainPanel");
        MenuPanel menuPanel = new MenuPanel(this);
        mainPanel.setMenuPanel(menuPanel);
        mainPanel.add(menuPanel, BorderLayout.SOUTH);
    }

    public void initForStartGameMenu(){
        mainPanel.clear();
        revalidate();
        repaint();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        main.MenuPanel menuPanel = new main.MenuPanel(this);
        mainPanel.setMainMenuPanel(menuPanel);
        mainPanel.add(Box.createGlue());
        mainPanel.add(mainPanel.getMainMenuPanel());
        mainPanel.add(Box.createGlue());
    }

    public void initForGame(){
        mainPanel.clear();
        revalidate();
        repaint();

        mainPanel.setInGameMode(true);
        mainPanel.setShuttle(new Shuttle(user.getShuttleType(), assets, this, user.getFireType(), user.getFirePower()));
        items.add(new HeatBar(mainPanel.getShuttle()));
        items.add(mainPanel.getShuttle());
        items.add(new DataBar(this));

//        items.add(new Chicken(500, 500, 20, 0, 1, assets.getChicken(1, 1), this));

        //mouse listeners
        mainPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                mainPanel.getShuttle().fire();
                mouseMoved(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mainPanel.getShuttle().setX(e.getX());
                mainPanel.getShuttle().setY(e.getY());
            }
        });
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                mainPanel.getShuttle().fire();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainPanel.getShuttle().fire();
            }
        });
        //key listener
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == 'a' || e.getKeyCode() == 37)
                    mainPanel.getShuttle().toLeft();
                if (e.getKeyChar() == 's' || e.getKeyCode() == 40)
                    mainPanel.getShuttle().toDown();
                if (e.getKeyChar() == 'd' || e.getKeyCode() == 39)
                    mainPanel.getShuttle().toRight();
                if (e.getKeyChar() == 'w' || e.getKeyCode() == 38)
                    mainPanel.getShuttle().toUp();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == ' ')
                    mainPanel.getShuttle().fire();
            }
        });
    }
    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public EsqFrame getEsqFrame() {
        return esqFrame;
    }

    public void setEsqFrame(EsqFrame esqFrame) {
        this.esqFrame = esqFrame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Items getItems() {
        return items;
    }

    public void save() {
        System.out.println("saving");
        users.save();
    }
    public void rocketBoomed(){
        //TODO
        System.out.println("rocket Boomeddd...!! :D");
    }
}
