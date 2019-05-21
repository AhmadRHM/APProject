package users;

import main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class UsersPanel extends JPanel {
    private MainFrame mainFrame;
    private ArrayList<UsersLabel> usersLabelArrayList;
    private int focus = -1, selected = -1;
    public UsersPanel(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        init();
    }
    private void init(){
        this.setPreferredSize(new Dimension(1600, 800));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setOpaque(false);

        usersLabelArrayList = new ArrayList<UsersLabel>();

        addUserLabels();
    }
    private void clear(){
        removeAll();
        usersLabelArrayList.clear();
    }
    public void addUserLabels(){
        clear();
        ArrayList<User> users = mainFrame.getUsers().getUsers();
        this.add(Box.createGlue());
        for(User user : users) {
            UsersLabel usersLabel = new UsersLabel(user.getUsername(), mainFrame);
            usersLabel.setId(usersLabelArrayList.size());
            this.add(usersLabel);
            usersLabelArrayList.add(usersLabel);
            this.add(Box.createGlue());
        }
    }
    private void initFocus(){
        if(focus == -1) {
            focus = 0;
            for(UsersLabel usersLabel : usersLabelArrayList)
                usersLabel.setNotSelected();
            if(selected != -1)
                usersLabelArrayList.get(selected).setSelected();
        }
    }
    public void deleteFocus(){
        if(focus != -1 && focus != selected)
                usersLabelArrayList.get(focus).setNotSelected();
    }
    public void setFocus(int id){
        deleteFocus();
        focus = id;
        if(focus != selected && focus != -1)
            usersLabelArrayList.get(focus).setHovered();
    }
    public void focusUp(){
        int n = usersLabelArrayList.size();
        initFocus();
        deleteFocus();
        focus++;
        focus %= n;
        setFocus(focus);
    }
    public void focusDown(){
        int n = usersLabelArrayList.size();
        initFocus();
        deleteFocus();
        focus--;
        focus+=n;
        focus %= n;
        setFocus(focus);
    }
    public String getFocusedUser(){
        if(focus == -1)
            return null;
        return usersLabelArrayList.get(focus).getUser();
    }

    public void setSelected() {
        if(selected != -1 && selected < usersLabelArrayList.size())
            usersLabelArrayList.get(selected).setNotSelected();
        selected = focus;
        if(selected != -1)
            usersLabelArrayList.get(selected).setSelected();
        //TODO fill this shit! :D
//        System.out.println("user with id " + selected + " and name " + usersLabelArrayList.get(selected).getUser() + " is selected!");
    }

    public int getSelected(){
        return selected;
    }
}
