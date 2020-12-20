package gameClient;
import javax.swing.*;


import Server.Game_Server_Ex2;
import api.game_service;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.text.NumberFormatter;

public class BestGame implements ActionListener,Runnable {

    int count = 0;
    JFrame frame = new JFrame("My Game");
    JPanel panel = new JPanel();
    // JLabel label = new JLabel("Number of clicks: "+ count);
    JPanel panel2 = new JPanel();
    JTextField username = new JTextField("");
    JFormattedTextField scenarioBox = new JFormattedTextField("");
    JLabel wrongId = new JLabel("invalid id");
    JLabel wrongScenario = new JLabel("invalid scenario (0 - 23)");
    public static int id;
    public static int scenario;
    boolean bol = false;




    public static void main(String[] args) {
        //      new BestGame();
//        Thread t1 = new Thread(new Ex2());
//        t1.run();
        new BestGame();
//test();
//        Thread t2 = new Thread(new MyFrame("F"));
//        t2.run();
    }
//    public Ex2(){
//        menu();
//    }
    public int getID(){
        return this.id;
    }
    public int getScenario(){
        return this.scenario;
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
    public JPanel LoginPanel(){
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
//        if(Integer.parseInt(scenarioBox.getText()) >= 24 || Integer.parseInt(scenarioBox.getText()) < 0){
//            wrongScenario.setVisible(true);
//            isValid = false;
//        }
        if (isValid) {//scenrioBox and username is the Fields

            this.id = Integer.parseInt(username.getText());
            this.scenario = Integer.parseInt(scenarioBox.getText());
            this.bol = true;
//....
//        test();
//            BestGame game = new BestGame();
//            game.UpdateData(Integer.parseInt(scenarioBox.getText()),Integer.parseInt(username.getText()));
//            game.run();
           // frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
         //   frame.setVisible(false);
           // frame.dispose();

//            Thread t1 = new Thread(new BestGame());
//            t1.start();

            //count++;
            //frame.setContentPane(panel2);
//            Thread t = new Thread(new BestGame());
//            t.start();
//            frame.setSize(1000,700);
//            frame.setVisible(true);
//            Thread client = new Thread(new Ex2_Client());
//            client.start();


//            dw_graph_algorithms algo = new DWGraph_Algo();
//            algo.load("/Users/yuval/Downloads/Ariel_OOP_2020-master/Assignments/Ex2/data/A5");
//            Arena ar = new Arena();
//            ar.setGraph(algo.getGraph());
//            ar.setPokemons();
//            MyFrame game = new MyFrame("Pokemon game");
//
//            frame.setVisible(false);
//            frame = game;
//            frame.setVisible(true);
//            frame.validate();
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
    @Override
    public void run() {

      Thread client = new Thread(new BestGame());
      client.start();
   game_service game = Game_Server_Ex2.getServer(23); // you have [0,23] games

    }
    public static void test(){
        Ex2 game = new Ex2();
        game.UpdateData(1,2);
        game.run();

    }
}