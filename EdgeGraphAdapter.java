import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
	     if (!g.hasNode(e.getSrc())) {
        g.addNode(e.getSrc());
       }
       if (!g.hasNode(e.getDst())) {
        g.addNode(e.getDst());
       }
       if (!g.hasEdge(e.getSrc(), e.getDst())) {
        g.addEdge(e.getSrc(), e.getDst());
        return true;
       } else {
        return false;
       }
    }

    public boolean hasNode(String n) {
	     return g.hasNode(n);
    }

    public boolean hasEdge(Edge e) {
	     return g.hasEdge(e.getSrc(), e.getDst());
    }

    public boolean removeEdge(Edge e) {
	     if (g.hasEdge(e.getSrc(), e.getDst())) {
        g.removeEdge(e.getSrc(), e.getDst());

        List<String> srcPredList = g.pred(e.getSrc());
        List<String> srcSuccList = g.succ(e.getSrc());
        if (srcPredList.size() == 0 && srcSuccList.size() == 0) {
          g.removeNode(e.getSrc());
        }

        List<String> dstPredList = g.pred(e.getDst());
        List<String> dstSuccList = g.succ(e.getDst());
        if (dstPredList.size() == 0 && dstSuccList.size() == 0) {
          g.removeNode(e.getDst());
        }

        return true;
       } else {
        return false;
       }
    }

    public List<Edge> outEdges(String n) {
      List<Edge> edges = new ArrayList<>();
      List<String> nodes = g.succ(n);

      if(!g.hasNode(n)){
        throw new NoSuchElementException();
      } else {
        for (int i = 0; i < nodes.size(); i++) {
          Edge e = new Edge(n, nodes.get(i));
          edges.add(e);
        }
      }
      return edges;
    }

    public List<Edge> inEdges(String n) {
      
      List<Edge> edges = new ArrayList<>();
      List<String> nodes = g.pred(n);

      if(!g.hasNode(n)){
        throw new NoSuchElementException();
      } else {
        for (int i = 0; i < nodes.size(); i++) {
          Edge e = new Edge(nodes.get(i), n);
          edges.add(e);
        }
      }
      return edges;
    }

    public List<Edge> edges() {
      List<Edge> edges = new ArrayList<>();
      List<String> nodes = g.nodes();

      for(int i = 0; i < nodes.size(); i++) {
        String node = nodes.get(i);
        List<String> nodeEdges = g.succ(node);

        for (int j = 0; j < nodeEdges.size(); j++) {
          Edge edge = new Edge(node, nodeEdges.get(j));
          edges.add(edge);
        }
      }
      return edges;
    }

    public EdgeGraph union(EdgeGraph g) {
      Graph newG = new ListGraph();
      EdgeGraph newEG = new EdgeGraphAdapter(newG);

      // add all the edges of g into newEG
      List<Edge> edges = g.edges();
      for(int i = 0; i < edges.size(); i++) {
        newEG.addEdge(edges.get(i));
      }

      List<String> nodes = this.g.nodes();

      for (int i = 0; i < nodes.size(); i++) {
        List<String> nodeEdges = this.g.succ(nodes.get(i));
        for (int j = 0; j < nodeEdges.size(); j++) {
          Edge edge = new Edge(nodes.get(i), nodeEdges.get(j));
          if(!newEG.hasEdge(edge)) {
            newEG.addEdge(edge);
          }
        }
      }

      return newEG;
    }

    public boolean hasPath(List<Edge> e) {
	     if(e.isEmpty()) {
        return true;
       } else {
        for(int i = 0; i < e.size(); i++){
          
          if((i != e.size() - 1)&&(e.get(i).getDst() != e.get(i+1).getSrc())){
            throw new BadPath();
          } else if(!g.hasEdge(e.get(i).getSrc(), e.get(i).getDst())) {
            return false;
          } else {
            continue;
          }
        }
        return true;
       }

    }

}
