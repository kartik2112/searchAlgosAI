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
                
                bfs(map);
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
        
	static int dequeue(Vector v)
        {
            if(v.size()>0)
                return (int)v.remove(0);
            else
                return -1;
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


class Node
{
    Node parent;
    int id;
    String name;
    Vector<Node> Children=new Vector<Node>();

    
    
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
    
    
}
