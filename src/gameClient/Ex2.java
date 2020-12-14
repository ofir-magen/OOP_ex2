package gameClient;
import javax.swing.*;


import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.dw_graph_algorithms;
import api.game_service;
import gameClient.util.Point3D;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.text.NumberFormatter;

    public class Ex2  implements ActionListener,Runnable {
//
        int count = 0;
        MyFrame frame = new MyFrame("My Game");
    JPanel panel = new JPanel();
    // JLabel label = new JLabel("Number of clicks: "+ count);
    JPanel panel2 = new JPanel();
    JTextField username = new JTextField("");
    JFormattedTextField scenarioBox = new JFormattedTextField("");
    JLabel wrongId = new JLabel("invalid id");
    JLabel wrongScenario = new JLabel("invalid scenario (0 - 23)");




    public static void main(String[] args) {
  //      new BestGame();
        Thread t1 = new Thread(new Ex2());
        t1.run();
//        Thread t2 = new Thread(new MyFrame("F"));
//        t2.run();
    }
    public Ex2(){
        menu();
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
                System.out.println("Wrong id");
                isValid = false;
            }
        }
        if(Integer.parseInt(scenarioBox.getText()) >= 24 || Integer.parseInt(scenarioBox.getText()) < 0){
            wrongScenario.setVisible(true);
            isValid = false;
        }
        if (isValid) {
            //count++;
            //frame.setContentPane(panel2);
            frame.setSize(1000,700);
            frame.setVisible(false);
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

        Thread client = new Thread(new Ex2());
        client.start();
       // game_service game = Game_Server_Ex2.getServer(23); // you have [0,23] games

    }
}