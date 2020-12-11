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

public class BestGame implements Runnable{
    private static MyFrame _win;
    private static Arena _ar;

    public static void main(String[] args) {

        Thread t = new Thread(new BestGame());
        t.start();
    }

    @Override
    public void run() {

        int scenario_num = 18;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        int id = 0;
        game.login(id);
        Stack<CL_Pokemon> pokemonContainer = new Stack();

        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(gg);
        init(game);


        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
        int ind=0;
        long dt=100;

        while(game.isRunning()) {
            moveAgents(game, algo);


            try {
                if(ind%1==0) {
                    _win.repaint();}
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
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
     * @param game
     * @param algo
     * @param
     */
    private  void moveAgents(game_service game, dw_graph_algorithms algo) {
        String lg = game.move();//this and 2 down for updating the movement
        java.util.List<CL_Agent> log = Arena.getAgents(lg, game.getJava_Graph_Not_to_be_used());
        _ar.setAgents(log);

        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs); // give the Pokemons to list


        _ar.setPokemons(ffs);
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(fs);
        String pokeJs = game.getPokemons();
        System.out.println(log);
        for (CL_Pokemon pokemon: pokemons){
            Arena.updateEdge(pokemon,algo.getGraph());//update the pokemon edge from null

            CL_Agent ag = log.get(0);
            int dest = ag.getNextNode();
            int id = ag.getID();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            int lastStep = FindSrc(pokemon);/////////////////////////////////////
            // add the fonc of the short way
            ofir(pokemons,ag,algo);
            LinkedList<node_data> pathToPokemon = (LinkedList) algo.shortestPath(src,lastStep);
            pathToPokemon.removeFirst();
            for (node_data n : pathToPokemon) {
                String lgg = game.move();
                java.util.List<CL_Agent> logg = Arena.getAgents(lgg, game.getJava_Graph_Not_to_be_used());
                _ar.setAgents(logg);
                if (dest == -1) {
                    dest = n.getKey();
                    game.chooseNextEdge(id, dest);
                }
            }
            if(src == lastStep){
                game.chooseNextEdge(id,FindDest(pokemon));
                String lgg = game.move();
                java.util.List<CL_Agent> logg = Arena.getAgents(lgg, game.getJava_Graph_Not_to_be_used());
                _ar.setAgents(logg);
            }



        }

    }
    public static int FindSrc(CL_Pokemon pokemon){//Find src of the edge to catch a desired pokemon
        if(pokemon.getType() == -1){
            return  Integer.max(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        if(pokemon.getType() == 1){
            return Integer.min(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        return -1;
    }
    public int FindDest(CL_Pokemon pokemon){//Find dest of the edge to catch a desired pokemon
        if(pokemon.getType() == -1){
            return  Integer.min(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        if(pokemon.getType() == 1){
            return Integer.max(pokemon.get_edge().getSrc(), pokemon.get_edge().getDest());
        }
        return -1;
    }

    public void moveAgent(CL_Agent agent,int dest,game_service game){
        String lg = game.move();
        java.util.List<CL_Agent> log = Arena.getAgents(lg, game.getJava_Graph_Not_to_be_used());
        _ar.setAgents(log);
        int currDest = agent.getNextNode();
        if(currDest == -1){
            game.chooseNextEdge(agent.getID(),dest);
        }

    }


    //   [ 4 | 9 ]
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
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int)(Math.random()*s);
        int i=0;
        while(i<r) {itr.next();i++;}
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
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);

            getLead(game);

        }
        catch (JSONException e) {e.printStackTrace();}



    }
    public static Stack<CL_Pokemon> ofir(List<CL_Pokemon> po,CL_Agent agent, dw_graph_algorithms algo){
        Stack<CL_Pokemon> PPokemon= new Stack();

        double min=Integer.MAX_VALUE;
        double check;
        boolean flag= false;
        // give the nearest pokemon to the agent
        for (CL_Pokemon n:po) {
            check=algo.shortestPathDist(agent.getSrcNode(),n.get_edge().getSrc())+(n.get_edge().getWeight());
            if(check<min && flag==false){
                min=check;
                PPokemon.push(n);
                flag=true;
            }
            if(check<min && flag == true){
                min=check;
                PPokemon.pop();
                PPokemon.push(n);
            }
        }
        flag= false;
       po.remove(PPokemon.peek());

       // צריך למהשיך לסגור את העניין של הסדר פוקימונים+ חישוב נקודת התחלה וחיבור מעבר על
        // make a stack of the shortest way to catch the pokemons
       while(!po.isEmpty()) {
           CL_Pokemon pokemon_check =PPokemon.peek();
            for (CL_Pokemon n:po) {
                // חישוב של מהקודקוד הסופי עד הקודקוד ההתחלתי +מעבר על הצלע של הפוקימון
                check=algo.shortestPathDist(pokemon_check.get_edge().getDest(), n.get_edge().getSrc())+(n.get_edge().getWeight());
               // if is the first adding
                if(check<min && flag== false){
                    min=check;
                    PPokemon.push(n);
                    flag=true;
                }
                // if is not the first adding, delit the pokemon and add the shortest one (עדכון הפוקימון עם הדרך הקצרה ביותר)
                if(check<min && flag == true){
                    min=check;
                    PPokemon.pop();
                    PPokemon.push(n);
                }
            }
            flag=false;
            min=Integer.MAX_VALUE;
           po.remove(PPokemon.peek());
        }
       //הופך ומכניס את הדרך בין כל אחד ואחד למחסנית לפי סדר מעבר על הפוקימונים
        //
        //יובל פה אני חושב שכדי להסתכל על סוגי מבני הנתונים ולהחליט מה כדי לנו כי זה לא יעיל להפוך ושוב להפוך וכו...
        //
        Stack<CL_Pokemon> PPokemon_rotin= new Stack(); // for rotin the pokemon from the end to the first
        for (CL_Pokemon n:PPokemon) {
            PPokemon_rotin.push(PPokemon.pop());
        }

        return PPokemon_rotin ;
    }
}
