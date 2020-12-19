package gameClient;

import Server.Game_Server_Ex2;
import akka.parboiled2.Position;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;
import scala.Int;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class BestGame   {
    private static MyFrame _win;
    private static Arena _ar;
    HashMap<Integer,HashMap<Integer,Double>> distmap;
    HashMap<Integer, HashMap<Integer, List<node_data>>> listmap;
    LinkedList<CL_Pokemon> targets;
    private static int  scenario;
    private static int id;

    public static void main(String[] args) {
//
            BestGame game = new BestGame();
            game.LoginPanel();

//       game.run();
//        Thread t = new Thread(new BestGame());
//        t.start();
    }

    public void UpdateData(int scenario , int id ){
        this.scenario = scenario;
        System.out.println(scenario);
        this.id = id;
//        BestGame game = new BestGame();
//        game.run();
    }
    // @Override
    public void run() {

        // int scenario_num = 15;
        game_service game = Game_Server_Ex2.getServer(this.scenario); // you have [0,23] games
        //int id = 313612152;
        game.login(this.id);
        //Stack<CL_Pokemon> pokemonContainer = new Stack();

        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(gg);
        init(game);
        Poke_Hack(algo);
//        for (Integer src : distmap.keySet()){
//            System.out.println();
//            System.out.print(src + " ->" );
//            for (Integer dest :distmap.get(src).keySet()){
//                System.out.print("["+dest+","+distmap.get(src).get(dest)+"]"+",");
//            }
//        }
//                for (Integer src : listmap.keySet()){
//            System.out.println();
//            System.out.print(src + " ->" );
//            for (Integer dest :listmap.get(src).keySet()){
//                System.out.println();
//                System.out.print(+dest+" ->"+"[");
//                for (node_data n : listmap.get(src).get(dest)) {
//                    System.out.print(n.getKey()+ ",");
//                }
//                System.out.print("]");
//            }
//        }
        this.targets = new LinkedList<>();
        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
            moveAgents(game, algo);

            try {
                if (ind % 1 == 0) {
                    _win.repaint();
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     *
     * @param game
     * @param algo
     * @param
     */
    private void moveAgents(game_service game, dw_graph_algorithms algo) {
        //Agent src:5 dest:-1
        //choosenextedge(Agent.ID,6);
        //Agent: src:5  dest:6 J
        String lg = game.move();//this and 2 down for updating the movement
        //Agent src:6 dest:-1
        List<CL_Agent> log = Arena.getAgents(lg, algo.getGraph());
        _ar.setAgents(log);
        //System.out.println(lg);

//
        String fs = game.getPokemons();//json
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs); // give the Pokemons to list
        _ar.setPokemons(ffs);
//
        for (CL_Pokemon pokemon : ffs){
            Arena.updateEdge(pokemon,algo.getGraph());
        }
        for (CL_Agent ag : log) {
            fs = game.getPokemons();//json
            ffs = Arena.json2Pokemons(fs); //
//            if(ag.getNextNode() == -1) {
            Stack<CL_Pokemon> pokemons = ofir(ffs, ag, algo);
            while(!pokemons.isEmpty()) {
                CL_Pokemon pikachu = pokemons.pop();
                ag.set_curr_fruit(pikachu);
                if (!targets.contains(pikachu)) {
//            System.out.println(pikachu.get_edge().getSrc());
//            System.out.println(pikachu.get_edge().getDest());
                    targets.add(pikachu);
                    if(ag.get_curr_fruit() == null) {
                        ag.set_curr_fruit(pikachu);
                        NextNode(game, ag, algo, pikachu);
                    }
                    else{NextNode(game, ag, algo, ag.get_curr_fruit());}
                    //System.out.println("Agent: "+ag.getID()+", val: "+ag.getValue()+"   turned to node: "+ag.getNextNode());
                    break;
                }
            }

            //  }
        }

        //        for (int i = 0; i < 10; i++) {
//
//            int dest = agent.getNextNode();
//            if(dest == -1) {
//                String lgg = game.move();
//                List<CL_Agent> logg = Arena.getAgents(lgg, game.getJava_Graph_Not_to_be_used());
//                _ar.setAgents(logg);
//                game.chooseNextEdge(0, i);
//
//            }
//        }


//        List<CL_Pokemon> pokemons = Arena.json2Pokemons(fs);
//        for (CL_Pokemon pok : pokemons) {
//            Arena.updateEdge(pok, algo.getGraph());//update the pokemon edge from null
////
//        }
//
//        String pokeJs = game.getPokemons();
//        for (CL_Agent agent : log) {
//            Stack<CL_Pokemon> pokemons1 = ofir(pokemons, agent, algo);
//            while (!pokemons1.isEmpty()) {
//                CL_Pokemon pokemon = pokemons1.pop();
//                Arena.updateEdge(pokemon, algo.getGraph());//update the pokemon edge from null
//
//                CL_Agent ag = agent;
//                int dest = ag.getNextNode();
//                int id = ag.getID();
//                int src = ag.getSrcNode();
//                double v = ag.getValue();
//                int lastStep = FindSrc(pokemon);/////////////////////////////////////
//                // add the fonc of the short way
//                //   ofir(pokemons,ag,algo);
//                //  LinkedList<node_data> pathToPokemon = (LinkedList) algo.shortestPath(src, lastStep);
//                LinkedList<node_data> pathToPokemon = (LinkedList) this.listmap.get(src).get(lastStep);
//                 node_data removed = pathToPokemon.removeFirst();
//                for (node_data n : pathToPokemon) {
//                    String lgg = game.move();
//                    java.util.List<CL_Agent> logg = Arena.getAgents(lgg, game.getJava_Graph_Not_to_be_used());
//                    _ar.setAgents(logg);
//                    if (dest == -1) {
//                        dest = n.getKey();
//                        game.chooseNextEdge(id, dest);
//                    }
//                }
//                pathToPokemon.addFirst(removed);
//                if (src == lastStep) {
//                    game.chooseNextEdge(id, FindDest(pokemon));
//                    String lgg = game.move();
//                    java.util.List<CL_Agent> logg = Arena.getAgents(lgg, game.getJava_Graph_Not_to_be_used());
//                    _ar.setAgents(logg);
//                }
//            }
//        }


    }
    public void NextNode (game_service game , CL_Agent agent, dw_graph_algorithms algo, CL_Pokemon pokemon) {
        Arena.updateEdge(pokemon,algo.getGraph());
//        System.out.println(FindSrc(pokemon));
        LinkedList<node_data> path = (LinkedList) listmap.get(agent.getSrcNode()).get(FindSrc(pokemon));
        if (path.size() > 1) {
            game.chooseNextEdge(agent.getID(), path.get(1).getKey());
        } else {
            game.chooseNextEdge(agent.getID(),FindDest(pokemon));
            targets.remove(pokemon);
            agent.set_curr_fruit(null);
//            String lg = game.move();//this and 2 down for updating the movement
//            //Agent src:6 dest:-1
//            List<CL_Agent> log = Arena.getAgents(lg, game.getJava_Graph_Not_to_be_used());
//            _ar.setAgents(log);
        }
//        game.move();
    }
    public static int FindSrc(CL_Pokemon pokemon) {//Find src of the edge to catch a desired pokemon
        if (pokemon.getType() == -1) {
            return Integer.max(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        if (pokemon.getType() == 1) {
            return Integer.min(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        return -1;
    }

    public int FindDest(CL_Pokemon pokemon) {//Find dest of the edge to catch a desired pokemon
        if (pokemon.getType() == -1) {
            return Integer.min(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        if (pokemon.getType() == 1) {
            return Integer.max(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        return -1;
    }

    public void moveAgent(CL_Agent agent, int dest, game_service game) {
        String lg = game.move();
        java.util.List<CL_Agent> log = Arena.getAgents(lg, game.getJava_Graph_Not_to_be_used());
        _ar.setAgents(log);
        int currDest = agent.getNextNode();
        if (currDest == -1) {
            game.chooseNextEdge(agent.getID(), dest);
        }

    }


    public static void getLead(game_service game) {
        String pokeJson = game.getPokemons();
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(pokeJson);
        HashMap<Integer, Integer> startAhead = new HashMap<Integer, Integer>();

        directed_weighted_graph graph = game.getJava_Graph_Not_to_be_used();

        for (CL_Pokemon pokemon : pokemons) {
            Arena.updateEdge(pokemon, graph);
            if (pokemon.getType() == -1) {
                int src = Integer.max(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
                int dest = Integer.min(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
                startAhead.put(src, dest);

            }
            if (pokemon.getType() == 1) {
                int src = Integer.min(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
                int dest = Integer.max(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
                startAhead.put(src, dest);

            }
        }

        for (int src : startAhead.keySet()) {
            game.addAgent(src);
        }
        System.out.println(game.getAgents());
    }

    /**
     * a very simple random walk implementation!
     *
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int) (Math.random() * s);
        int i = 0;
        while (i < r) {
            itr.next();
            i++;
        }
        ans = itr.next().getDest();
        return ans;
    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();

        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);
        _win.show();
        _win.setVisible(true);
        _win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);

            getLead(game);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  Stack<CL_Pokemon> ofir(List<CL_Pokemon> po, CL_Agent agent, dw_graph_algorithms algo) {
        Stack<CL_Pokemon> PPokemon = new Stack();

        double min = Integer.MIN_VALUE;
        double check;
        boolean flag = false;
        // give the nearest pokemon to the agent
        for (CL_Pokemon n : po) {
            Arena.updateEdge(n,algo.getGraph());
            // check = algo.shortestPathDist(agent.getSrcNode(), n.get_edge().getSrc()) + (n.get_edge().getWeight());
            check = this.distmap.get(agent.getSrcNode()).get(FindSrc(n)) + (n.get_edge().getWeight());
            check =  n.getValue()/check;
            if (check > min && flag == false) {
                min = check;
                PPokemon.push(n);
                flag = true;
            }
            if (check > min && flag == true) {
                min = check;
                PPokemon.pop();
                PPokemon.push(n);
            }
        }
        flag = false;
        if (!PPokemon.isEmpty()) po.remove(PPokemon.peek());


        // make a stack of the shortest way to catch the pokemons
        while (!po.isEmpty()) {
            CL_Pokemon pokemon_check = PPokemon.peek();
            for (CL_Pokemon n : po) {
                //   check = algo.shortestPathDist(pokemon_check.get_edge().getDest(), n.get_edge().getSrc()) + (n.get_edge().getWeight());
                check = this.distmap.get(pokemon_check.get_edge().getDest()).get(FindSrc(n)) + (n.get_edge().getWeight());
                check =  n.getValue()/check;
                // if is the first adding
                if (check > min && flag == false) {
                    min = check;
                    PPokemon.push(n);
                    flag = true;
                }
                if (check > min && flag == true) {
                    min = check;
                    PPokemon.pop();
                    PPokemon.push(n);
                }
            }
            flag = false;
            min = Integer.MIN_VALUE;
            po.remove(PPokemon.peek());
        }
        //
        Stack<CL_Pokemon> PPokemon_rotin = new Stack(); // for rotin the pokemon from the end to the first
        while (!PPokemon.isEmpty()) {
            PPokemon_rotin.push(PPokemon.pop());
        }
        return PPokemon_rotin;
    }


    public  void Poke_Hack(dw_graph_algorithms algo) {
        HashMap<Integer,HashMap<Integer,Double>> distmap = new HashMap<>();
        HashMap<Integer, HashMap<Integer, List<node_data>>> listmap = new HashMap<>();
        for (node_data n : algo.getGraph().getV()) {
            HashMap<Integer, List<node_data>> path = new HashMap<>();
            HashMap<Integer,Double> dist = new HashMap<>();
            listmap.put(n.getKey(),path);
            distmap.put(n.getKey(),dist);
            for (node_data d : algo.getGraph().getV()) {
                path.put(d.getKey(),algo.shortestPath(n.getKey(), d.getKey()));
                dist.put(d.getKey(),algo.shortestPathDist(n.getKey(),d.getKey()));
            }
        }
        this.distmap = distmap;
        this.listmap = listmap;
    }
    public  void LoginPanel(){
        Ex2 login = new Ex2();
        login.menu();
        while(login.bol == false){
            System.out.print("");
        }
        UpdateData(login.scenario,login.id);
        System.out.println("id: "+this.id + " and scenario is: "+this.scenario);
        run();

    }
}