# OOP_ex2


For Installation https://github.com/simon-pikalov/Ariel_OOP_2020/tree/master/Assignments/Ex0


### Authors:
Yuval Ben Yaakov , Ofir Magen  
-----------


## Summary:
-------
The project focus on "catch em' all" game, the main idea of the game is to move the agents in order to catch the Pokemons that appear on the screen we have 2 main packages
- api and gameClient 
and a test directory - tests

api:
1. geo_location - an interface that represents a (x,y,z) point and his geographical location implemented by Point3D
2. node_data - an interface that represents a node in the graph implemented by NodeData.
3. edge_data - an interface that represents an edge in the graph implemented by EdgeData.
4‫.‬ edge‫_‬location ‫-‬ an interface that represents the location of an edge implemented by EdgeLocation (not used in the whole project).
5. graph - an interface that represents a directed weighted graph implemented by DW_GraphDS.
6. graph_algorithms - an interface that represents a set of algorithms to perform on a weighted directed graph implemented by DWGraph_Algo.
7. game_service - an interface that represents the "catch em' all" game and containing all the methods needed in order to play the game , not implemented. 	
 
## Setup and data structure
my Setup is using 3 Hashmaps in order to get most methods in DWGraph_DS in O(1), 1 Hashmap is used to store all the nodes (node_data) in the graph and the other 2 is to store Entry edges and Exit edges.
## NodeData class methods:
------ 
all the methods below are O(1)
1. getKey – returns the key (id) of the node 
2‫.‬ getLocation - returns the geo_location (Point3D) of the node
3‫.‬ setLocation - set the geo_location of the node
4. getWeight - returns the weight (double) of the node
5. setWeight - set the weight (double) of the node
6. getInfo  – returns the info (String) of the node
7. setInfo – set the info (String) of the node 
8. getTag – returns the tag (int) of the node 
9. setTag - set the tag (int) of the node

## WGraph_DS class methods: 
-----
1. getNode – returns a node contained in the graph O(1)
2. getEdge – returns the edge_data of the edge between 2 nodes (src --> dest)
3. addNode – Add a node to the graph O(1)
4. connect – connects an edge between 2 nodes (src --> dest) with a given weight O(1)
5. getV() - returns a collection of all the nodes in the graph O(1)
6. getE(node_id) - returns a collection of all the adjacent nodes of a node with the node_id O(1)
7. removeNode -  removes a node from the graph and all edges that start with this node  O(n)
8. removeEdge – Removes an edge between 2 given nodes from the graph O(1)
9. nodeSize – Returns the number of nodes in the graph O(1)
10. edgeSize – returns the number of edges in the graph O(1)
11. getMC – returns the number of changes made in the graph O(1)
## EdgeData class methods:
1. getSrc - returns id of the src node of this edge.
2. getDest - returns id of the dest node of this edge.
4. getWeight - returns the weight (double) of the edge
5. setWeight - set the weight (double) of the edge
6. getInfo  – returns the info (String) of the edge
7. setInfo – set the info (String) of the edge 
8. getTag – returns the tag (int) of the edge 
9. setTag - set the tag (int) of the node


## WGraph_Algo class methods: 
-----
1. init - inits a graph that the algorithms performs on O(1)
2. GetGraph - returns the graph the algorithms performs on
3. copy - makes a deep copy of the graph O(V+E)
4. isConnected - checks if there is a path from every 2 nodes in the graph ,
this method iterates threw all the nodes in the graph and then traverse the whole graph        using BFS and trying to reach every possible node (checks literally if there's a valid path from from every node to each other node).
5. shortestPathDist - returns the shortest path (by edges weight ),
this method traverse the graph starting from src node using BFS and adding every node minimal distance to a HashMap and returning the index of the dest node in the end of the run.
6. shortestPath - returns a list  containing the node's in the shortest path 
this method calls shortestPathDist method in order to mark every node info with his previous node id in the shortest path to dest, splitting the info by "," and then getting every key in the path ordered.
7. save - save the graph that the algorithms performs on JSONFormat
8. load - loads a graph from a file (JSONFormat) to perform algorithms on
