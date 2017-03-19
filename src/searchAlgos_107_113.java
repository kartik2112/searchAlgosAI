import java.io.*;
import java.util.*;

public class searchAlgos_107_113{
	static int source,dest,ch;
	public static void main(String args[]) throws Exception{
		MapOfRomania map=new MapOfRomania();
		map.displayMap();
                
                Scanner sc=new Scanner(System.in);
                
                System.out.print("Enter source city:");
                String SourceName=sc.next();
                System.out.print("Enter destination city:");
                String DestName=sc.next();
                
                source=map.locationNames.indexOf(SourceName);
                dest=map.locationNames.indexOf(DestName);
                
                //bfs(map);
                AStar(map);
        }
	
        static void bfs(MapOfRomania map)
        {
            int list[][]=map.distances;
            int no_of_nodes=map.locationNames.size();
            
            Node root=new Node();
            
            root.setId(source);
            root.setName(map.locationNames.elementAt(source));
            root.setParent(null);
            
            Vector visited=new Vector();
            Vector toBeVisited=new Vector();
            Vector <Node>toBeVisitedNode=new Vector<Node>();
            int currentVisiting=source;
 //           toBeVisitedNode.add(root);
            Node currentNode=new Node();
            currentNode=root;
//          toBeVisited.add(currentVisiting);
            while(visited.size()<no_of_nodes)
            {
                System.out.println("Current Node Visited:"+currentNode.name);
                if(currentVisiting==dest)
                {
                    break;
                }
                visited.add(currentVisiting);
                for(int i=0;i<no_of_nodes;i++)
                {
                    if(list[currentVisiting][i]>0&&!visited.contains(i)&&!toBeVisited.contains(i))
                    {
                        //System.out.println("Cities near "+map.locationNames.elementAt(currentVisiting)+":"+map.locationNames.elementAt(i));
                        Node child=new Node();
                        child.setId(i);
                        child.setName(map.locationNames.elementAt(i));
                        child.setParent(currentNode);
                        currentNode.Children.add(child);
                        toBeVisitedNode.add(child);
                        toBeVisited.add(i);
                        //System.out.println("Cities near "+map.locationNames.elementAt(currentVisiting)+":"+child.name);
                    }
                }
                
                //System.out.println(toBeVisited);
                
                int nextNode=dequeue(toBeVisited);
                if(nextNode==-1)
                    break;
                else
                {
                    currentVisiting=nextNode;
                    currentNode=toBeVisitedNode.remove(0);
                }
            }
            if(currentVisiting!=dest)
                System.out.println("Node not found!");
            else
            {
                System.out.println("Node Found");
                System.out.println("Path:");
                Vector <String>path=new Vector<String>();
                do
                {
                    //System.out.println(currentNode.getName());
                    path.add(currentNode.getName());
                    currentNode=currentNode.getParent();
                }
                while(currentNode!=null);
                
                String PATH=preparePathFromVector(path);
                
                System.out.println(PATH);
            }
        }
        
        static void AStar(MapOfRomania map){
            int list[][]=map.distances;
            int no_of_nodes=map.locationNames.size();
            
            /**
             * These will hold location no
             */
            Vector<Integer> visitedList=new Vector<Integer>();
            Vector<Integer> adjNodesList=new Vector<Integer>();
                        
            /**
             * These are used to keep objects
             * These will hold distance from source values and parent reference
             */
            Vector<Node> visitedListNodes=new Vector<Node>();
            Vector<Node> adjNodesListNodes=new Vector<Node>();
            
            int currentVisiting=source;
            visitedList.add(source);
            visitedListNodes.add(new Node(null,0,map.locationNames.get(source),source));
            
            while(visitedList.size()<no_of_nodes)
            {
                if(currentVisiting==dest)
                {
                    System.out.println("Destination Node found");
                    String path=Node.displayPathRecursive(visitedListNodes.get(visitedList.indexOf(dest)));
                    System.out.println("\nPath from "+map.locationNames.get(source)+" to "+map.locationNames.get(dest)+" is:");
                    System.out.println(path);
                    break;
                }
                
                for(int i=0;i<no_of_nodes;i++)
                {
                    if(list[currentVisiting][i]>0&&!visitedList.contains(i)){                        
                        
                        /**
                         * Add Node object to adjNodesListNodes
                         * First, the parent Node reference in visitedListNodes is found and stored in @curNodeInVisited
                         * This is used for calculating the distance of the adjacent nodes from source and to store the parent reference in this node
                         */
                        Node curNodeInVisited=visitedListNodes.get(visitedList.indexOf(currentVisiting));
                        if(adjNodesList.contains(i)){
                            Node adjNode=adjNodesListNodes.get(adjNodesList.indexOf(i));
                            if( (list[currentVisiting][i]+curNodeInVisited.distanceFromSource) < adjNode.distanceFromSource){
                                adjNode.setDistanceFromSource(list[currentVisiting][i]+curNodeInVisited.distanceFromSource);
                                adjNode.setParent(curNodeInVisited);
                            }
                        }
                        else{
                            adjNodesList.add(i);
                            adjNodesListNodes.add(new Node(curNodeInVisited,
                                                list[currentVisiting][i]+curNodeInVisited.distanceFromSource,
                                                map.locationNames.get(i),i) );
                        }
                                                
                    }
                }
                
                System.out.print("Visited Nodes List: ");
                displayVectorLocationNames(visitedList, map);
                
                System.out.print("Adjacent Nodes List: ");
                displayVectorLocationNames(adjNodesList, map);
                
                
                int minCost=9999999,tempCurrVisiting=-1;
                
                /**
                 * Here i,tempCurrVisiting is not location No but rather index of Node in vector
                 */
                System.out.println("Adjacent Nodes Costs:");
                for(int i=0;i<adjNodesList.size();i++){
                    int distFrmSrc=adjNodesListNodes.get(i).distanceFromSource;
                    int heur=map.locationHeuristics.get(adjNodesListNodes.get(i).id);
                    int totalCostOfNode=distFrmSrc+heur;
                    System.out.println(adjNodesListNodes.get(i).name+" -> "+distFrmSrc+" + "+heur+" = "+totalCostOfNode);
                    
                    if(totalCostOfNode<minCost){
                        minCost=totalCostOfNode;
                        tempCurrVisiting=i;
                    }
                }
                
                /**
                 * The next node is selected based on heuristics and distance from source to this node.
                 * This node is removed from adjacent nodes list and added to visited list.
                 */
                currentVisiting=adjNodesList.remove(tempCurrVisiting);
                Node selectedNode=adjNodesListNodes.remove(tempCurrVisiting);
                visitedList.add(currentVisiting);
                visitedListNodes.add(selectedNode);
                
                System.out.println("Minimum cost node chosen for next iteration: "+map.locationNames.get(currentVisiting)+"\n");
                
            }
        }
        
	static int dequeue(Vector v)
        {
            if(v.size()>0)
                return (int)v.remove(0);
            else
                return -1;
        }
        
    public static void displayVectorLocationNames(Vector<Integer> v,MapOfRomania map){
            System.out.print("{ ");
            for(int i=0;i<v.size();i++){
                System.out.print(map.locationNames.get(v.get(i)));
                if(i!=v.size()-1){
                    System.out.print(", ");
                }
            }
            System.out.println(" }");
        }
    private static String preparePathFromVector(Vector <String>path) {
        String s=new String();
        s="";
        for(int i=path.size()-1;i>=0;i--)
        {
            s=s+path.elementAt(i);
            if(i>0)
                s=s+"\u2192";
        }
        return s;
    }
}

class MapOfRomania{
    Vector<String> locationNames=new Vector<String>();
    Vector<Integer> locationHeuristics=new Vector<Integer>();
    int distances[][];
        
    public static final String MAP_PROPERTIES_FILE_PATH="/Users/varunrao/Documents/searchAlgosAI/src/MapProperties.txt";
    public static final String MAP_HEURISTICS_FILE_PATH="/Users/varunrao/Documents/searchAlgosAI/src/MapHeuristics.txt";

    /*
    Paths for input file
    
    VR Mac props:/Users/varunrao/Documents/searchAlgosAI/src/MapProperties.txt
    VR Mac heuristics:/Users/varunrao/Documents/searchAlgosAI/src/MapHeuristics.txt
    
    SK PC props:F:\\OneDrive\\Projects\\Netbeans Projects\\searchAlgosAI\\src\\MapProperties.txt
    SK PC heuristics:F:\\OneDrive\\Projects\\Netbeans Projects\\searchAlgosAI\\src\\MapHeuristics.txt
    */
    
    public MapOfRomania() throws FileNotFoundException,IOException{
        
        BufferedReader brProps=new BufferedReader(new FileReader(MAP_PROPERTIES_FILE_PATH));
        BufferedReader brHeurs=new BufferedReader(new FileReader(MAP_HEURISTICS_FILE_PATH));
              
        String line;
        
        /**
         * This loop reads the heuristics and 
         * stores both the names of the locations and the heuristics
         */
        while((line=brHeurs.readLine())!=null){
            StringTokenizer st=new StringTokenizer(line);
            locationNames.add(st.nextToken());
            locationHeuristics.add(Integer.parseInt(st.nextToken()));
        }
        
        distances=new int[locationNames.size()][locationNames.size()];
        
        
        /**
         * This loop reads the properties file and stores the distances between all the locations
         */
        while((line=brProps.readLine())!=null){
            StringTokenizer st=new StringTokenizer(line);
            
            String source=st.nextToken();
            String dest=st.nextToken();
            int dist=Integer.parseInt(st.nextToken());
            
            if(locationNames.indexOf(source)==-1){
                System.out.println(source);
            }
            if(locationNames.indexOf(dest)==-1){
                System.out.println(dest);
            }
            distances[locationNames.indexOf(source)][locationNames.indexOf(dest)]=dist;
            distances[locationNames.indexOf(dest)][locationNames.indexOf(source)]=dist;
        }
    }
	
    
    /**
     * This function displays the graph in matrix form
     */
    void displayMap(){
        System.out.print("        ");
        for(int i=0;i<locationNames.size();i++){
            System.out.print(String.format("%6s", locationNames.get(i).substring(0,locationNames.get(i).length()<6?locationNames.get(i).length():6)));
            //System.out.print(locationNames.get(i));
            if(i!=locationNames.size()-1){
                System.out.print("  ");
            }
        }
        System.out.println();
        
        for(int i=0;i<distances.length;i++){
            System.out.print(String.format("%6s  ", locationNames.get(i).substring(0,locationNames.get(i).length()<6?locationNames.get(i).length():6)));
            for(int j=0;j<distances[i].length;j++){
                System.out.print(String.format("%6s", distances[i][j]));
                //System.out.print();
                if(j!=distances[i].length-1){
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}


/**
 * @parent will hold the reference of the parent Node for the current Node
 * @distanceFromSource will hold the distance of the current node from the source
 * @id will hold the location No as used as an index for the locationNames
 * @Children is a vector containing the references of all child Nodes of the current node
 */
class Node
{
    Node parent;
    int distanceFromSource;
    int id;
    String name;
    Vector<Node> Children=new Vector<Node>();
    
    Node(Node par,int distFromSrc,String name,int id){
        this.parent=par;
        this.distanceFromSource=distFromSrc;
        this.name=new String(name);
        this.id=id;
    }
    
    Node(){
        distanceFromSource=0;
    }

    public int getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(int distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }
    
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        if(this!=parent)
        {
            this.parent=new Node();
            this.parent = parent;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Node> getChildren() {
        return Children;
    }

    public void setChildren(Vector<Node> Children) {
        this.Children = Children;
    }
    
    public void addChild(Node n){
        Children.add(n);
    }
    
    public static String displayPathRecursive(Node n){
        if(n.parent==null){
            return n.name;
        }
        else{
            return displayPathRecursive(n.parent)+"->"+n.name;
        }
    }
    
}
