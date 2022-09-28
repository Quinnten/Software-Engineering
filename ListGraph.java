import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
        if (nodes.containsKey(n)){
            return false;
        } else {
        nodes.put(n, new LinkedList<String>());
            return true;
        }
    }

    public boolean addEdge(String n1, String n2) {
	     if (!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
         } else if(nodes.get(n1).contains(n2)){
            return false;
         } else {
            nodes.get(n1).add(n2);
            return true;
         }
    }

    public boolean hasNode(String n) {
	     if (nodes.containsKey(n)){
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEdge(String n1, String n2) {
	     if (nodes.containsKey(n1) && nodes.containsKey(n2) && nodes.get(n1).contains(n2)) {
            return true;
         } else {
            return false;
         }
    }

    public boolean removeNode(String n) {
	     if (nodes.containsKey(n)) {
            nodes.remove(n);
            for (String k : nodes.keySet()) {
                for (String j : nodes.get(k)) {
                    if (j == n) {
                        nodes.get(k).remove(j);
                    }
                }
            }
            return true;
         } else {
            return false;
         }
    }

    public boolean removeEdge(String n1, String n2) {
	     if (!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
         } else if (!nodes.get(n1).contains(n2)){
            return false;
         } else {
            nodes.get(n1).remove(n2);
            return true;
         }
    }

    public List<String> nodes() {
        ArrayList<String> keys = new ArrayList<>();

        for (String n : nodes.keySet()) { // iterate over key of HashMap
            keys.add(n);
        }
        return keys;
    }

    public List<String> succ(String n) {
        if(!nodes.containsKey(n)) {
            throw new NoSuchElementException();
        }
	     return nodes.get(n);
        
    }

    public List<String> pred(String n) {
        if(!nodes.containsKey(n)) {
            throw new NoSuchElementException();
        }
        ArrayList<String> pred = new ArrayList<>();
	    for (String k : nodes.keySet()) { // iterate over key of HashMap
            if (nodes.get(k).contains(n)) {
                pred.add(k);
            }
        }
        return pred;
    }

    public Graph union(Graph g) {
	    Graph newG = new ListGraph();

        for (String k : nodes.keySet()) { // Add all the nodes from original graph into newg
            newG.addNode(k);
        }

        for (String k : nodes.keySet()) { // Add all the nodes from original graph into newg
             for (String n : nodes.get(k)) { 
                newG.addEdge(k, n);
            }
        }
        List<String> vectors = g.nodes();

        //Need to add all the nodes before any of the edges to avoid errors where I add an edge to a node that doesn't exist yet
        for (int i = 0; i < vectors.size(); i++) {
            if(!newG.hasNode(vectors.get(i))) {
                newG.addNode(vectors.get(i));
            } 
        }

        // Add all the edges of graph g
        for (int i = 0; i < vectors.size(); i++) {
            List<String> edges = g.succ(vectors.get(i));
            for(int j = 0; j < edges.size(); j++) {
                if(!newG.hasEdge(vectors.get(i), edges.get(j))) {
                    newG.addEdge(vectors.get(i), edges.get(j));
                }
            }
        }
            return newG;
    }

    public Graph subGraph(Set<String> nodes) {
	    Graph subG = new ListGraph();
        
        for (String k : this.nodes.keySet()) {
            if(nodes.contains(k)) {
                subG.addNode(k);
            }
        }
        List<String> subNodes = subG.nodes();

        for (int i = 0; i < subNodes.size(); i++) {
            List<String> edgeList = this.nodes.get(subNodes.get(i));

            for(int j = 0; j < edgeList.size(); j++) {
                if(subG.hasNode(edgeList.get(j))) {
                    subG.addEdge(subNodes.get(i), edgeList.get(j));
                }
            }
        }
        return subG;
    }

    public boolean connected(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
         } else {
            Queue<String> queue = new LinkedList<>();
            queue.add(n1);

            HashSet<String> prevNodes = new HashSet<>();
            prevNodes.add(n1);

            while(queue.peek() != null) {
                String front = queue.remove();
                List<String> neighbors = nodes.get(front);

                for(int i = 0; i < neighbors.size(); i++) {
                    if(neighbors.get(i) == n2){
                        return true;
                    } else if(!prevNodes.contains(neighbors.get(i))) {
                        queue.add(neighbors.get(i));
                        prevNodes.add(neighbors.get(i));
                    } else {
                        break;
                    }
                }
            }
            return false;
         }
    }
}
