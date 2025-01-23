package Screens;

import Players.Player;

import javax.swing.*;

public class PlayerView {
    public Player player;
    private JPanel mainPanel;
    private JPanel ratingsPanel;
    private JPanel infoPanel;
    private JLabel nameLabel;

    public PlayerView(Player player){
        this.player = player;

        nameLabel.setText(player.getFirstName() + " " + player.getLastName());
        infoPanel.setVisible(true);

        setInfoTable();





    }

    public void setInfoTable(){
        JTable infoTable = new JTable(5, 2);
        infoTable.setValueAt("Height:",0,0);
        infoTable.setValueAt("Weight:",1,0);
        infoTable.setValueAt("College:",2,0);
        infoTable.setValueAt("Number:",3,0);
        infoTable.setValueAt("Overall:",4,0);
        infoTable.setValueAt(player.getHeight(),0,1);
        infoTable.setValueAt(player.getWeight(), 1,1);
        infoTable.setValueAt(player.getCollege(),2 ,1);
        infoTable.setValueAt(player.getJerseyNumber(),3 ,1);
        infoTable.setValueAt(player.getOverall(), 4,1);



        infoTable.setVisible(true);

        infoPanel.add(infoTable);



    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
