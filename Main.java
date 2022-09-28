import java.util.*;

public class Main {

    // Run "java -ea Main" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)
    
    public static void test1() {
			Graph g = new ListGraph();

			assert g.addNode("a");
			assert g.addNode("b");
			assert g.addNode("c");
			assert g.addEdge("a", "b");
			assert g.addEdge("b", "c");
			assert g.addEdge("a", "a");
			assert g.connected("a", "c");
			assert g.connected("a", "a");


  	}
		
    public static void test2() {
		Graph g1 = new ListGraph();
		EdgeGraph e1 = new EdgeGraphAdapter(g1);
		Edge edge1 = new Edge("a", "b");
		Edge edge15 = new Edge("z", "b");
		Edge edge2 = new Edge("b", "c");
		Edge edge3 = new Edge("c", "d");
		Edge edge4 = new Edge("d", "e");
		Edge edge5 = new Edge("d", "f");


		ArrayList<Edge> edgeList = new ArrayList<>();
		edgeList.add(edge1);
		edgeList.add(edge2);
		edgeList.add(edge3);
		edgeList.add(edge4);



		Graph g2 = new ListGraph();
		EdgeGraph e2 = new EdgeGraphAdapter(g2);
		
		assert e1.addEdge(edge1);
		assert e1.addEdge(edge2);
		assert e1.addEdge(edge3);
		assert e1.addEdge(edge4);

		assert e1.hasPath(edgeList);
		
		return;
    }
    
    public static void main(String[] args) {
	test1();
	System.out.println("******************************************************");
	test2();

    }

}
