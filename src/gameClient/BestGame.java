package gameClient;
import javax.swing.*;




import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.text.NumberFormatter;

public class BestGame implements ActionListener {

    JFrame frame = new JFrame("My Game");
    JPanel panel = new JPanel();
    JTextField username = new JTextField("");
    JFormattedTextField scenarioBox = new JFormattedTextField("");
    JLabel wrongId = new JLabel("invalid id");
    JLabel wrongScenario = new JLabel("invalid scenario (0 - 23)");
    public static int id;
    public static int scenario;
    boolean bol = false;




    public static void main(String[] args) {
        new BestGame();
    }
    void menu(){
        JButton button = new JButton("Start game");
        button.addActionListener(this);
        frame.setSize(1000,700);
        frame.add(LoginPanel(),BorderLayout.CENTER);
        frame.setTitle("game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public JPanel LoginPanel(){ // make a login panel
        wrongId.setForeground(Color.red);
        wrongId.setVisible(false);
        wrongScenario.setForeground(Color.red);
        wrongScenario.setVisible(false);
        JButton button = new JButton("Start game");
        button.addActionListener(this);
        JLabel idLabel = new JLabel("enter your id in the box below:");
        JLabel scenarioLabel = new JLabel("enter your desired scenario (0 - 23):");
        panel.setSize(1000,700);
        panel.add(wrongId);
        panel.add(idLabel);
        NumberFormatter numbersOnly = new NumberFormatter(NumberFormat.getIntegerInstance());
        numbersOnly.setAllowsInvalid(false);
        scenarioBox = new JFormattedTextField(numbersOnly);
        panel.add(username);
        panel.add(wrongScenario);
        panel.add(scenarioLabel);
        panel.add(scenarioBox);
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0,1));
        panel.add(button);
        return panel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean isValid = true;
        for (int i = 0;i<username.getText().length();i++){
            if(username.getText().charAt(i) < 48 || username.getText().charAt(i) > 57){
                wrongId.setVisible(true);
                isValid = false;
            }
        }


        if (isValid) {//scenrioBox and username is the Fields

            this.id = Integer.parseInt(username.getText());
            this.scenario = Integer.parseInt(scenarioBox.getText());
            this.bol = true;
        }

    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    public static void test(){
        Ex2 game = new Ex2();
        game.UpdateData(1,2);
        game.run();

    }
}