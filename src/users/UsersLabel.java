package users;

import main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UsersLabel extends JLabel {
    private int fontSize = 70;
    private MainFrame mainFrame;
    private String user;
    private int id;
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    private Color selected = Color.WHITE, hovered = new Color(0, 121, 255), notSelected = new Color(43,13,193);

    public UsersLabel(String user, MainFrame mainFrame){
        super(user);
        this.mainFrame = mainFrame;
        this.user = user;

//        this.setFontSize(fontSize);
        this.setForeground(notSelected);
        this.setFont(new Font("ELLA", Font.PLAIN, fontSize));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mainFrame.getMainPanel().getUsersPanel().setSelected();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mainFrame.getMainPanel().getUsersPanel().setFocus(id);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                mainFrame.getMainPanel().getUsersPanel().setFocus(-1);
            }
        });
        this.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    }
    public void setSelected(){ setForeground(selected);}
    public void setNotSelected(){ setForeground(notSelected);}
    public void setHovered(){setForeground(hovered);}
    public String getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
