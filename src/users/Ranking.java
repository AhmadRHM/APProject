package users;

import assets.Assets;

import main.MainFrame;
import overridedSwingComponents.Button;
import overridedSwingComponents.MouseListenerForButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Ranking extends JPanel {
    private String[] colNames = {"#", "username", "level passed", "score", "time played"};
    private String[][] data = new String[0][5];
    private GridBagConstraints gc;
    private int fontSize = 30;
//    private JTable table;
    private int numberOfDatas = 0;
    private MainFrame mainFrame;

    public Ranking(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        init();
    }
    private JLabel getLabel(String st){
        JLabel label = new JLabel(st);
        label.setOpaque(false);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("ELLA", Font.PLAIN, fontSize));
        return label;
    }
    private void init(){
//        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

        setOpaque(false);

        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        for(int i=0; i<5; i++){
            gc.gridx = i;
            this.add(getLabel(colNames[i]), gc);
        }

//        this.add(Box.createGlue());
//        makeTable();
//        this.add(table);

//        this.add();
    }
    public void addRecord(Record record){
        String[][] temp = new String[numberOfDatas+1][5];
        for(int i=0; i<numberOfDatas; i++)
            System.arraycopy(data[i], 0, temp[i], 0, 5);
        temp[numberOfDatas][0] = String.valueOf(numberOfDatas+1);
        temp[numberOfDatas][1] = record.getUsername();
        temp[numberOfDatas][2] = String.valueOf(record.getLastLevelPassed());
        temp[numberOfDatas][3] = String.valueOf(record.getScore());
        int time = (int)Math.floor(record.getTimePlayed());
        int hour = time/3600;
        int min = (time%3600)/60;
        int sec = (time%60);
        String h = String.valueOf(hour);
        if(hour<10)
            h = "0" + h;
        String m = String.valueOf(min);
        if(min<10)
            m = "0" + m;
        String s = String.valueOf(sec);
        if(sec<10)
            s = "0" + s;
        temp[numberOfDatas][4] = h+":"+m+":"+s;
        data = temp;

//        this.remove(table);
//
//        makeTable();
//
//        this.add(table);

        gc.gridy = numberOfDatas+1;
        for(int i=0; i<5; i++){
            gc.gridx = i;
            this.add(getLabel(data[numberOfDatas][i]), gc);
        }

        numberOfDatas++;

        this.revalidate();
        this.repaint();
    }
    public void pack(){
        Assets assets = mainFrame.getAssets();

        Button backButton = new Button(assets.getBackButtonDefault(), assets.getBackButtonHovered(), assets.getBackButtonPressed(), mainFrame);
        Button quitButton = new Button(assets.getQuitButtonDefault(), assets.getQuitButtonHovered(), assets.getQuitButtonPressed(), mainFrame);

        gc.gridy = numberOfDatas + 1;

        gc.gridx = 1;

        this.add(backButton, gc);

        gc.gridx = 3;

        this.add(quitButton, gc);

        backButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.initForStartGameMenu();
            }
        });

        quitButton.addMouseListener(new MouseListenerForButton() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }
}
