package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;
//import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Ex2 {
    private static MyFrame _win;
    private static Arena _ar;
    HashMap<Integer,HashMap<Integer,Double>> distmap;
    HashMap<Integer, HashMap<Integer, List<node_data>>> listmap;
    LinkedList<CL_Pokemon> targets;
    private static int  scenario;
    private static int id;
    private static directed_weighted_graph g;

    public static void main(String[] args) {
        if(args.length == 0) {
            Ex2 game = new Ex2();
            game.LoginPanel();
        }
       else {
         id = Integer.parseInt(args[0]);
         scenario = Integer.parseInt(args[1]);
         Ex2 ex2 = new Ex2();
         ex2.run();
        }

    }

    public void UpdateData(int scenario , int id ){
        this.scenario = scenario;
        System.out.println(scenario);
        this.id = id;
    }
    public void run() {

        game_service game = Game_Server_Ex2.getServer(this.scenario); // you have [0,23] games
        game.login(this.id);
        directed_weighted_graph gg = Json2Graph(game.getGraph());
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(gg);
        init(game);
        Poke_Hack(algo);
        this.targets = new LinkedList<>();
        _ar.agents = Arena.getAgents(game.getAgents(),Json2Graph(game.getGraph()));
        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
            moveAgents(game, algo);

            try {
                if (ind % 1 == 0) {
                    _ar.agents = Arena.getAgents(game.getAgents(),Json2Graph(game.getGraph()));
                    _ar.time = game.timeToEnd();
                    _win.update(_ar);

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
        String lg = game.move();//this and 2 down for updating the movement
        List<CL_Agent> log = Arena.getAgents(lg, algo.getGraph());
        _ar.setAgents(log);


        String fs = game.getPokemons();//json
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs); // give the Pokemons to list
        _ar.setPokemons(ffs);

        for (CL_Pokemon pokemon : ffs){
            Arena.updateEdge(pokemon,algo.getGraph());
        }
        for (CL_Agent ag : log) {
            fs = game.getPokemons();//json
            ffs = Arena.json2Pokemons(fs); //
            Stack<CL_Pokemon> pokemons = ofir(ffs, ag, algo);
            while(!pokemons.isEmpty()) {
                CL_Pokemon pikachu = pokemons.pop();
                ag.set_curr_fruit(pikachu);
                if (!targets.contains(pikachu)) {
                    targets.add(pikachu);
                    if(ag.get_curr_fruit() == null) {
                        ag.set_curr_fruit(pikachu);
                        NextNode(game, ag, algo, pikachu);
                    }
                    else{NextNode(game, ag, algo, ag.get_curr_fruit());}
                    break;
                }
            }

        }



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
        java.util.List<CL_Agent> log = Arena.getAgents(lg, Json2Graph(game.getGraph()));
        _ar.setAgents(log);
        int currDest = agent.getNextNode();
        if (currDest == -1) {
            game.chooseNextEdge(agent.getID(), dest);
        }

    }


    public  void getLead(game_service game) {
        String pokeJson = game.getPokemons();
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(pokeJson);
        HashMap<Integer, Integer> startAhead = new HashMap<Integer, Integer>();

        directed_weighted_graph graph = Json2Graph(game.getGraph());

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
    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = Json2Graph(game.getGraph());

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
        BestGame login = new BestGame();
        login.menu();
        while(login.bol == false){
            System.out.print("");
        }
        UpdateData(login.scenario,login.id);
        System.out.println("id: "+this.id + " and scenario is: "+this.scenario);
        run();

    }
    private directed_weighted_graph Json2Graph(String s){
        directed_weighted_graph graph = new DW_GraphDS();
        JsonObject js = (JsonObject) new JsonParser().parse(s);
        for (JsonElement jsonedNode : js.getAsJsonArray("Nodes")) {
            int id = ((JsonObject) jsonedNode).get("id").getAsInt();
            node_data node = new NodeData(id);
            graph.addNode(node);
            String posString = ((JsonObject) jsonedNode).get("pos").getAsString();
            String[] locations = posString.split(",");
            double x = Double.parseDouble(locations[0]);
            double y = Double.parseDouble(locations[1]);
            double z = Double.parseDouble(locations[2]);
            geo_location pos = new Point3D(x, y, z);
            node.setLocation(pos);
        }
        for (JsonElement jsonedEdge : js.getAsJsonArray("Edges")) {
            int src = ((JsonObject) jsonedEdge).get("src").getAsInt();
            double w = ((JsonObject) jsonedEdge).get("w").getAsDouble();
            int dest = ((JsonObject) jsonedEdge).get("dest").getAsInt();
            graph.connect(src, dest, w);
        }
        return graph;
    }
}