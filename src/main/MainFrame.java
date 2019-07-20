package main;

import Network.*;
import assets.Assets;
import chickenGroups.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import overridedSwingComponents.MouseCorrectRobot;
import shuttles.DataBar;
import shuttles.Shuttle;
import shuttles.Tir;
import shuttles.HeatBar;
import users.*;
import users.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MainFrame extends JFrame {
    private int width = 1600, height = 1000;
    private int lastMouseX, lastMouseY;
    private Assets assets;
    private MainPanel mainPanel;
    private double backgroundSpeed;
    private Users users;
    private Records records;
    private User user;
    private int numberOfLevels;

    public ArrayList<User> getPlayingUsers() {
        return playingUsers;
    }

    public ArrayList<User> getWaitingUsers() {
        return waitingUsers;
    }

    public ArrayList<User> getSpectatingUsers() {
        return spectatingUsers;
    }

    private ArrayList<User> playingUsers, waitingUsers, spectatingUsers;

    public ArrayList<Shuttle> getShuttles() {
        return shuttles;
    }

    private ArrayList<Shuttle> shuttles;
    private ServerSocket serverSocket;
    private Socket socket;
    private ArrayList<ClientHandler> handlers;
    private int maxUsers;
    private EsqFrame esqFrame;
    private Items items;
    private boolean setText = false;
    private String textToBeShown;
    private long timeTextShown = -1;
    private InGameText inGameText;
    private int id;
    private boolean isServer=false;
    private String dataToPrint;

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    private boolean isMultiplayer = false;

    MainFrame() {
        super();
        init();
    }

    private void init() {
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

        records = new Records();

        initForUserSelection();

        backgroundSpeed = 1;

        items = new Items();

        shuttles = new ArrayList<>();
        playingUsers = new ArrayList<>();
        waitingUsers = new ArrayList<>();
        spectatingUsers = new ArrayList<>();
    }

    public void setBackgroundSpeed(double backgroundSpeed) {
        this.backgroundSpeed = backgroundSpeed;
    }

    public Assets getAssets() {
        return assets;
    }

    private void makeRound(int round){
        if(round >= 21){
            endGame(0);
            return;
        }
        showText("Round " + round);
        int type = (round-1)/5 + 1;
        if(round % 5 == 1){
            items.add(new RectangleGroup(40, type, this));
        }else if(round % 5 == 2){
            items.add(new CircularGroup(36, type, this));
        }else if(round % 5 == 3){
            items.add(new RotationalGroup(36, type, this));
        }else if(round % 5 == 4){
            items.add(new SuicideGroup(10, type, this));
        }else{
            System.out.println("ghoule in marhale bayad biad");
            BigEgg bigEgg = new BigEgg(800, -500, 250 * (round/5 + 1), this);
            bigEgg.setNext(800, 500);
            items.add(bigEgg);
        }
    }
    private void nextRound(){
        user.setScore(user.getScore() + user.getMoney() * 3);
        user.setMoney(0);
        users.save();
        int round = user.getLastLevel();
        user.setLastLevel(round + 1);
        round += 2;
        if(round % 5 == 1 && round != 1) user.setRockets(user.getRockets()+1);
        makeRound(round);
    }

    private Thread animationThread;

    private final int delay = 5;

    private void initAnimationThread() {
        animationThread = new Thread(() -> {
            try {
                while (true) {
                    mainPanel.setYOfBackground((int) (2.5 * backgroundSpeed * delay / 5));

                    if (mainPanel.isInGameMode()) {
                        //set the mouse place
                        for(Shuttle shuttle : shuttles) {
                            if (shuttle.isDead()) {
                                shuttle.setBeginingCoord();
                                try {
                                    MouseCorrectRobot mouseCorrectRobot = new MouseCorrectRobot();
                                    mouseCorrectRobot.myMouseMove((int) shuttle.getX(), (int) shuttle.getY());
                                } catch (AWTException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //add to playing time :D
                        user.setTimePlayed(user.getTimePlayed() + 0.005);
                        //round check
                        boolean hasActiveChickenGroup = false;
                        for(Drawable drawable : items.getItems()) {
                            if (drawable instanceof ChickenGroup || drawable instanceof BigEgg)
                                hasActiveChickenGroup = true;
                        }
                        if(!hasActiveChickenGroup)
                            nextRound();

                        //Set text point
                        if(setText){
                            if(timeTextShown == -1){
                                timeTextShown = System.currentTimeMillis();
                                inGameText = new InGameText(textToBeShown);
                                items.add(inGameText);
                            }
                            if(System.currentTimeMillis() - timeTextShown > 5000){
                                setText = false;
                                timeTextShown = -1;
                                items.remove(inGameText);
                                inGameText = null;
                            }
                        }

                        //deleting bad items
                        for (Drawable drawable : items.getItems()) {
                            if ((drawable instanceof Tir) || (drawable instanceof Egg) ||
                                    (drawable instanceof TirBooster) || (drawable instanceof TirChanger)
                                    || (drawable instanceof MaxTempBooster) || (drawable instanceof Coin)){
                                if (drawable.isOutOfPage())
                                    items.remove(drawable);
                            } else if (drawable instanceof ChickenGroup) {
                                if (((ChickenGroup) drawable).getChickens().size() == 0)
                                    items.remove(drawable);
                            }
                        }
//                            System.out.println("killing bad items finished");
                        //Update
                        for (Drawable drawable : items.getItems()) {
                            drawable.update((double)delay/1000);
                        }
//                            System.out.println("updating finished");

                        //killing things! :D
                        Iterator<Drawable> chickenGroupIterator = items.getItems().iterator();
                        for (Drawable drawable : items.getItems()) {
                            if (drawable instanceof ChickenGroup) {

                                ChickenGroup chickenGroup = (ChickenGroup) drawable;
                                synchronized (chickenGroup.getChickens()) {
                                    Iterator<Chicken> chickenIterator = chickenGroup.getChickens().iterator();
                                    while (chickenIterator.hasNext()) {
                                        Chicken chicken = chickenIterator.next();
                                        boolean shouldTheChickenDie = false;
                                        //death of chickens
                                        for (Drawable drwbl : items.getItems()) {
                                            if (drwbl instanceof Tir) {
                                                Tir tir = (Tir) drwbl;
//                                                    if (isIn(chicken.getX(), chicken.getY(), chicken.getSize().width, chicken.getSize().height, tir.getX(), tir.getY(), tir.getSize().width, tir.getSize().height)) {
                                                if (conflict(chicken, tir)) {
                                                    chicken.reduceLives(tir.getPower());
                                                    items.remove(tir);
                                                    if (chicken.getLife() <= 0) {
                                                        user.setScore(user.getScore() + chicken.getType());
                                                        chicken.killed();
                                                        shouldTheChickenDie = true;
//                                                            chickenIterator.remove();
//                                                            drawableIterator.remove();
                                                    }
                                                }
                                            }
                                        }
                                        //death of shuttle
//                                            if(!shuttle.isDead() && isIn(shuttle.getX(), shuttle.getY(), shuttle.getSize().width, shuttle.getSize().height, chicken.getX(), chicken.getY(), chicken.getSize().width, chicken.getSize().height)){
                                        for(Shuttle shuttle : shuttles) {
                                            if (!shuttle.isDead() && conflict(shuttle, chicken)) {
//                                                chickenIterator.remove();
                                                shouldTheChickenDie = true;
                                                shuttle.dead();
                                                shuttleDied(shuttle.getId());
                                            }
                                            if (shouldTheChickenDie) {
                                                try {
                                                    chickenIterator.remove();
                                                    chickenGroup.removeChicken(chicken);
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (drawable instanceof Egg) {
                                Egg egg = (Egg) drawable;
//                                    if (!shuttle.isDead() && isIn(shuttle.getX(), shuttle.getY(), shuttle.getSize().width, shuttle.getSize().height, egg.getX(), egg.getY(), egg.getSize().width, egg.getSize().height)) {
                                for(Shuttle shuttle : shuttles) {
                                    if (!shuttle.isDead() && conflict(shuttle, egg)) {
                                        shuttleDied(shuttle.getId());
                                        shuttle.dead();
                                        items.remove(drawable);
                                    }
                                }
                            } else if(drawable instanceof  BigEgg){
                                BigEgg bigEgg = (BigEgg)drawable;
                                for(Drawable drwbl : items.getItems()){
                                    if(drwbl instanceof Tir){
                                        if(conflict(drawable, drwbl)) {
                                            bigEgg.reduceLives(((Tir) drwbl).getPower());
                                            items.remove(drwbl);
                                        }
                                    }else if(drwbl instanceof Shuttle){
                                        if(conflict(drawable, drwbl)) {
                                            bigEgg.reduceLives(20);
                                            Shuttle shuttle = ((Shuttle)drwbl);
                                            shuttle.dead();
                                            shuttleDied(shuttle.getId());
                                        }
                                    }
                                }
                                if(bigEgg.getLife() <= 0){
                                    showText("Congratulations!");
                                    bigEgg.killed();
                                    items.remove(bigEgg);
                                }
                            }
                        }
//                            System.out.println("killing things finished");
                        //getting boosters
                        try {
                            for(Shuttle shuttle:shuttles) {
                                if (shuttle != null && !shuttle.isDead()) {
                                    for (Drawable drawable : items.getItems()) {
                                        if (drawable.hasImage() && conflict(shuttle, drawable)) {
                                            if (drawable instanceof MaxTempBooster) {
                                                shuttle.setMaxDegree(shuttle.getMaxDegree() + 5);
                                                items.remove(drawable);
                                            } else if (drawable instanceof TirBooster) {
                                                shuttle.setFirePower(shuttle.getFirePower() + 1);
                                                user.setFirePower(user.getFirePower() + 1);
                                                items.remove(drawable);
                                            } else if (drawable instanceof TirChanger) {
                                                if (shuttle.getFireType() == ((TirChanger) drawable).getType()) {
                                                    shuttle.setFirePower(shuttle.getFirePower() + 1);
                                                    user.setFirePower(user.getFirePower() + 1);
                                                } else {
                                                    shuttle.changeTir(((TirChanger) drawable).getType());
                                                }
                                                items.remove(drawable);
                                            }
                                        }
                                    }
                                }
                            }
                        }catch (Exception e){

                            e.printStackTrace();
                        }
                        //getting coins
                        for(Shuttle shuttle : shuttles) {
                            if (shuttle != null && !shuttle.isDead()) {
                                for (Drawable drawable : items.getItems())
                                    if (drawable instanceof Coin && conflict(shuttle, drawable)) {
                                        items.remove(drawable);
                                        user.setMoney(user.getMoney() + 1);
                                    }
                            }
                        }
                        //destroying coins
                        for(Drawable drawable1:items.getItems())
                            if(drawable1 instanceof Tir)
                                for(Drawable drawable2:items.getItems())
                                    if(drawable2 instanceof Coin && conflict(drawable1, drawable2)){
                                        items.remove(drawable1);
                                        items.remove(drawable2);
                                    }
                    }
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    animationThread.sleep(delay);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    public User getUser(int id){
        return playingUsers.get(id);
    }
    public Shuttle getShuttle(int id){
        return shuttles.get(id);
    }
    public void startServer(int maxUsers, int port) {
        isServerGameStarted = false;
        Shuttle shuttle = new Shuttle(user.getShuttleType(), assets, this, user.getFireType(), user.getFirePower(), 0);
        mainPanel.setShuttle(shuttle);
        shuttles.add(shuttle);

        playingUsers.add(user);

        isMultiplayer = true;
        id = 0;
        isServer = true;
        this.maxUsers = maxUsers;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket =  new ServerSocket(port);
                    handlers = new ArrayList<>();

                    System.out.println("server is running and waiting to give service to clients");
                    while (playingUsers.size() + waitingUsers.size() + spectatingUsers.size() < maxUsers){
                        Socket socket = serverSocket.accept();
                        System.out.println("new client joined server! :D");
                        ClientHandler handler = new ClientHandler(socket.getInputStream(),socket.getOutputStream(), playingUsers.size(),  mainPanel.getMainFrame());
                        handlers.add(handler);
                        handler.start();
                    }
                }catch (Exception e){
                    System.out.println("problem starting server");

                }
            }
        });
        thread.start();
    }

    public ArrayList<String> getTirs() {
        return tirs;
    }

    public ArrayList<String> getChickens() {
        return chickens;
    }

    public ArrayList<String> getBigegg() {
        return bigegg;
    }

    public ArrayList<String> getSpaceships() {
        return spaceships;
    }

    public ArrayList<String> getEggs() {
        return eggs;
    }

    public ArrayList<String> getMaxtempBoosters() {
        return maxtempBoosters;
    }

    public ArrayList<String> getTirBoosters() {
        return tirBoosters;
    }

    public ArrayList<String> getTirChangers() {
        return tirChangers;
    }

    public ArrayList<String> getCoins() {
        return coins;
    }

    public ArrayList<String> getIngameTextes() {
        return ingameTextes;
    }

    public ArrayList<String> getRockets() {
        return rockets;
    }

    public ArrayList<String> getDatabar() {
        return databar;
    }

    public ArrayList<String> getHeatbar() {
        return heatbar;
    }

    private ArrayList<String> tirs, chickens, bigegg, spaceships, eggs, maxtempBoosters, tirBoosters, tirChangers, coins, ingameTextes, rockets, databar, heatbar;

    boolean serverStarted = false;
    public void startClient(String ip, int port, boolean isSpectator){
        isMultiplayer = true;
        serverStarted = false;
        tirs = new ArrayList<>();
        chickens = new ArrayList<>();
        bigegg = new ArrayList<>();
        spaceships = new ArrayList<>();
        eggs = new ArrayList<>();
        maxtempBoosters = new ArrayList<>();
        tirBoosters = new ArrayList<>();
        tirChangers = new ArrayList<>();
        coins = new ArrayList<>();
        ingameTextes = new ArrayList<>();
        rockets = new ArrayList<>();
        databar = new ArrayList<>();
        heatbar = new ArrayList<>();
        try{
            socket = new Socket(ip, port);
            System.out.println("connected to server! :))");
            PrintStream printer = new PrintStream(socket.getOutputStream());
            Scanner scanner = new Scanner(socket.getInputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    printer.println(gson.toJson(user));
                    printer.println(isSpectator);
                    printer.flush();
                    id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("client id is " + id);
                    while (true){
                        if(serverStarted) {
                            printer.println("get");
                            printer.flush();
                            int n;
                            tirs.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++)
                                tirs.add(scanner.nextLine());
                            chickens.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++)
                                chickens.add(scanner.nextLine());
                            bigegg.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++)
                                bigegg.add(scanner.nextLine());
                            spaceships.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++)
                                spaceships.add(scanner.nextLine());
                            eggs.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++)
                                eggs.add(scanner.nextLine());
                            maxtempBoosters.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++)
                                maxtempBoosters.add(scanner.nextLine());
                            tirBoosters.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++) {
                                tirBoosters.add(scanner.nextLine());
                            }
                            tirChangers.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++) {
                                tirChangers.add(scanner.nextLine());
                            }
                            coins.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++) {
                                coins.add(scanner.nextLine());
                            }
                            ingameTextes.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++) {
                                ingameTextes.add(scanner.nextLine());
                            }
                            rockets.clear();
                            n = scanner.nextInt();scanner.nextLine();
                            for (int i = 0; i < n; i++) {
                                rockets.add(scanner.nextLine());
                            }
                            databar.clear();
                            heatbar.clear();
                            n=1;
                            for (int i = 0; i < n; i++) {
                                databar.add(scanner.nextLine());
                            }
                            for (int i = 0; i < n; i++)
                                heatbar.add(scanner.nextLine());
                            mainPanel.revalidate();
                            mainPanel.repaint();
                        }else{
                            printer.println("gamestarted");
                            printer.flush();
                            String answer = scanner.nextLine();
                            if(answer.equals("yes")) {
                                serverStarted = true;
                                mainPanel.clear();
//                                System.out.println("mouse listener added to mainPanel");
                                mainPanel.addMouseMotionListener(new MouseAdapter() {
                                    @Override
                                    public void mouseDragged(MouseEvent e) {
                                        super.mouseDragged(e);
                                        printer.println("fire");
                                        printer.flush();
                                        mouseMoved(e);
                                    }

                                    @Override
                                    public void mouseMoved(MouseEvent e) {
                                        super.mouseMoved(e);
//                                        if(mainPanel.isInGameMode()) {
                                            printer.println("move " + e.getX() + " " + e.getY());
                                            printer.flush();
//                                        }
                                    }
                                });
                                mainPanel.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mousePressed(MouseEvent e) {
                                        super.mousePressed(e);
                                        printer.println("fire");
                                        printer.flush();
                                    }
                                });
                                mainPanel.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyPressed(KeyEvent e) {
                                        super.keyPressed(e);
                                        if (e.getKeyChar() == ' ') {
                                            printer.println("fire");
                                            printer.flush();
                                        }
                                    }
                                });
                            }
                            else{

                                playingUsers = new ArrayList<>();
                                int n = scanner.nextInt();
                                scanner.nextLine();
//                                System.out.println("read : " + n);
                                for(int i=0; i<n; i++){
                                    String st = scanner.nextLine();
//                                    System.out.println("read : " + st);
                                    User user = gson.fromJson(st, User.class);
                                    playingUsers.add(user);
                                }

                                spectatingUsers = new ArrayList<>();
                                n = scanner.nextInt();
                                scanner.nextLine();
                                for(int i=0; i<n; i++){
                                    String st = scanner.nextLine();
                                    User user = gson.fromJson(st, User.class);
                                    spectatingUsers.add(user);
                                }
                                serverDetalePanel.initLabels();
                            }
                        }
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }catch (Exception e){
            System.out.println("problem connecting to server");
        }
    }
    public void stopServer(){
        System.out.println("terminating all services");
        for(ClientHandler handler:handlers)
            handler.stop();
    }

    public void showText(String textToBeShown){
        this.textToBeShown = textToBeShown;
        setText = true;
    }

    public void startAnimation() {
        if (animationThread == null)
            initAnimationThread();
        animationThread.start();
    }

    public void stopAnimation() {
        if (animationThread != null) {
            animationThread.stop();
        }
    }

    public void userSelected(String user) {
        //TODO fill this shit :D
        System.out.println("User " + user + " is selected! :))");
    }

    public Users getUsers() {
        return users;
    }

    private void initForUserSelection() {
        mainPanel.setCursor(Cursor.getDefaultCursor());

        mainPanel.setLayout(new BorderLayout());
        UsersPanel usersPanel = new UsersPanel(this);
        mainPanel.setUsersPanel(usersPanel);
        mainPanel.add(usersPanel, BorderLayout.NORTH);
        System.out.println("UsersPanel added to mainPanel");
        MenuPanel menuPanel = new MenuPanel(this);
        mainPanel.setMenuPanel(menuPanel);
        mainPanel.add(menuPanel, BorderLayout.SOUTH);
    }

    public void initForStartGameMenu() {
        mainPanel.clear();
        revalidate();
        repaint();

        mainPanel.setCursor(Cursor.getDefaultCursor());

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        main.MenuPanel menuPanel = new main.MenuPanel(this);
        mainPanel.setMainMenuPanel(menuPanel);
        mainPanel.add(Box.createGlue());
        mainPanel.add(mainPanel.getMainMenuPanel());
        mainPanel.add(Box.createGlue());
    }

    private boolean conflict(Drawable a, Drawable b){
        return  isIn(a.getX(), a.getY(), a.getSize().width, a.getSize().height, b.getX(), b.getY(), b.getSize().width, b.getSize().height);
    }
    private boolean isIn(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return !((x1 + w1 / 2 < x2 - w2 / 2) || (x2 + w2 / 2 < x1 - w1 / 2) || (y1 + h1 / 2 < y2 - h2 / 2) || (y2 + h2 / 2 < y1 - h1 / 2));
    }

    public void initForRanking(){
        mainPanel.clear();
        revalidate();
        repaint();
        mainPanel.setCursor(Cursor.getDefaultCursor());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        Ranking ranking = new Ranking(this);
        mainPanel.add(ranking);
        for(Record record : records.getRecords()) {
            ranking.addRecord(record);
        }
        ranking.pack();
    }
    public void initForGameTypePanel(){
        mainPanel.clear();
        revalidate();
        repaint();
        mainPanel.setCursor(Cursor.getDefaultCursor());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        GameTypePanel gameTypePanel = new GameTypePanel(this);
        mainPanel.add(Box.createGlue());
        mainPanel.add(gameTypePanel);
        mainPanel.add(Box.createGlue());
    }
    public void initForPlayerTypePanel(){
        mainPanel.clear();
        revalidate();
        repaint();
        mainPanel.setCursor(Cursor.getDefaultCursor());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        PlayerTypePanel playerTypePanel = new PlayerTypePanel(this);
        mainPanel.add(Box.createGlue());
        mainPanel.add(playerTypePanel);
        mainPanel.add(Box.createGlue());
    }

    public boolean isServerGameStarted() {
        return isServerGameStarted;
    }

    private boolean isServerGameStarted = false;
    public void initForGame() {
        if(isMultiplayer)
            isServerGameStarted = true;
        mainPanel.clear();
        revalidate();
        repaint();
//        setCursor(null);
//        /*
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("icons/handwriting.gif");
        Cursor c = toolkit.createCustomCursor(image , new Point(mainPanel.getX(),
                mainPanel.getY()), "img");
        mainPanel.setCursor (c);
//         */

        if(!isMultiplayer) {
            Shuttle shuttle = new Shuttle(user.getShuttleType(), assets, this, user.getFireType(), user.getFirePower(), 0);
            mainPanel.setShuttle(shuttle);
            shuttles.add(shuttle);
        }
        playingUsers.add(user);

        items.add(new HeatBar(this));
        items.add(mainPanel.getShuttle());
        items.add(new DataBar(this));


        try {
            MouseCorrectRobot mouseCorrectRobot = new MouseCorrectRobot();
            mouseCorrectRobot.myMouseMove((int)mainPanel.getShuttle().getX(), (int)mainPanel.getShuttle().getY());
        } catch (AWTException e) {
            e.printStackTrace();
        }

//        Chicken c = new Chicken(500, 500, 20, 0, 1, assets.getChicken(1, 0), this);
//        items.add(c);
//        c.setNext(1000, 500 );
//        items.add(new RotationalGroup(36, 1, this));
//        this.showText("Round 1");
        if(user.getLastLevel() != -1) {
            makeRound(user.getLastLevel() + 1);
        }

        mainPanel.setInGameMode(true);
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
                if(mainPanel.isInGameMode() && !mainPanel.getShuttle().isDead()) {
//                    System.out.println("oooops");
                    mainPanel.getShuttle().setX(e.getX());
                    mainPanel.getShuttle().setY(e.getY());
                }
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
//                System.out.println("dishdiri didin");
                mainPanel.getShuttle().fire();
            }
        });
        //key listener
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(mainPanel.isInGameMode() && !mainPanel.getShuttle().isDead()) {
                    if (e.getKeyChar() == 'a' || e.getKeyCode() == 37)
                        mainPanel.getShuttle().toLeft();
                    if (e.getKeyChar() == 's' || e.getKeyCode() == 40)
                        mainPanel.getShuttle().toDown();
                    if (e.getKeyChar() == 'd' || e.getKeyCode() == 39)
                        mainPanel.getShuttle().toRight();
                    if (e.getKeyChar() == 'w' || e.getKeyCode() == 38)
                        mainPanel.getShuttle().toUp();
                }
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
                if (e.getKeyChar() == ' ')
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

    public void rocketBoomed(int id) {
        //TODO
        System.out.println("rocket Boomeddd...!! :D");
        for(Drawable drawable:items.getItems())
            if(drawable instanceof ChickenGroup) {
                for(Chicken chicken:((ChickenGroup)drawable).getChickens()) {
                    chicken.killed();
                    getUser(id).setScore(getUser(id).getScore() + chicken.getType());
                }
                items.remove(drawable);
            }else if(drawable instanceof Egg)
                items.remove(drawable);
            else if(drawable instanceof BigEgg){
                BigEgg bigEgg = (BigEgg)drawable;
                bigEgg.reduceLives(50);
//                if(bigEgg.getLife() <= 0)

            }

    }

    public void shuttleDied(int id) {
        getUser(id).setLives(getUser(id).getLives() - 1);
        if (getUser(id).getLives() == 0)
            endGame(id);
        user.setMoney(0);
    }

    public void endGame(int id) {
        //TODO
        System.out.println("game ended");
        records.addRecord(new Record(user.getUsername(), user.getTimePlayed(), user.getLastLevel(), user.getScore()));
        mainPanel.setInGameMode(false);
        initForRanking();
        repaint();
        revalidate();
    }

    public int getLastMouseX() {
        return lastMouseX;
    }

    public void setLastMouseX(int lastMouseX) {
        this.lastMouseX = lastMouseX;
    }

    public int getLastMouseY() {
        return lastMouseY;
    }

    public void setLastMouseY(int lastMouseY) {
        this.lastMouseY = lastMouseY;
    }

    public Records getRecords() {
        return records;
    }

    public void moveShuttleTo(int id, int x, int y){
        getShuttle(id).setX(x);
        getShuttle(id).setY(y);
    }
    public void fireShuttle(int id){
        getShuttle(id).fire();
    }
    public void shootRocket(int id){
        getShuttle(id).shootRocket();
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public String getDataToPrint() {
        return dataToPrint;
    }

    public void setDataToPrint(String dataToPrint) {
        this.dataToPrint = dataToPrint;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(int numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }

    public ServerDetalePanel getServerDetalePanel() {
        return serverDetalePanel;
    }

    private ServerDetalePanel serverDetalePanel;
    public void initForServerDetalePanel() {
        mainPanel.clear();
        revalidate();
        repaint();
        mainPanel.setCursor(Cursor.getDefaultCursor());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        serverDetalePanel = new ServerDetalePanel(this);
        mainPanel.add(Box.createGlue());
        mainPanel.add(serverDetalePanel);
        mainPanel.add(Box.createGlue());
        repaint();
        revalidate();
    }
    public void initForServerDataPanel(){
        mainPanel.clear();
        revalidate();
        repaint();
        mainPanel.setCursor(Cursor.getDefaultCursor());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        ServerDataPanel serverDataPanel = new ServerDataPanel(this);
        mainPanel.add(Box.createGlue());
        mainPanel.add(serverDataPanel);
        mainPanel.add(Box.createGlue());
    }

    public void initForClientDataPanel() {
        mainPanel.clear();
        revalidate();
        repaint();
        mainPanel.setCursor(Cursor.getDefaultCursor());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        ClientDataPanel clientDataPanel = new ClientDataPanel(this);
        mainPanel.add(Box.createGlue());
        mainPanel.add(clientDataPanel);
        mainPanel.add(Box.createGlue());
    }
}
