import java.io.*;
import java.util.*;

public class searchAlgos_107_113{
	static int source,dest,ch;
	public static void main(String args[]) throws Exception{
		MapOfRomania map=new MapOfRomania();
		map.displayMap();
                
                Scanner sc=new Scanner(System.in);
                
                String SourceName=sc.next();
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
            toBeVisitedNode.add(root);
            Node currentNode;
            currentNode=root;
//          toBeVisited.add(currentVisiting);
            while(currentVisiting!=dest&&visited.size()<no_of_nodes)
            {
                
                System.out.println("Current Node Visited:"+currentVisiting);
                if(currentVisiting==dest)
                {
                    System.out.println("Node found");
                    break;
                }
                visited.add(currentVisiting);
                for(int i=0;i<no_of_nodes;i++)
                {
                    if(list[currentVisiting][i]==1&&!visited.contains(i)&&!toBeVisited.contains(i))
                    {
                        Node child=new Node();
                        child.setId(i);
                        child.setName(map.locationNames.elementAt(currentVisiting));
                        child.setParent(currentNode);
                        currentNode.Children.add(child);
                        toBeVisited.add(i);
                    }
                }
                
                //System.out.println(toBeVisited);
                
                int nextNode=dequeue(toBeVisited);
                if(nextNode==-1)
                    break;
                else
                {
                    currentVisiting=nextNode;
                    currentNode=toBeVisitedNode.elementAt(0);
                }
            }
            if(currentVisiting!=dest)
                System.out.println("Node not found!");
            else
                System.out.println("Node Found");
        }
        
	static int dequeue(Vector v)
        {
            if(v.size()>0)
                return (int)v.remove(0);
            else
                return -1;
        }
}

class MapOfRomania{
    Vector<String> locationNames=new Vector<String>();
    Vector<Integer> locationHeuristics=new Vector<Integer>();
    int distances[][];
        
    public static final String MAP_PROPERTIES_FILE_PATH="F:\\OneDrive\\Projects\\Netbeans Projects\\searchAlgosAI\\src\\MapProperties.txt";
    public static final String MAP_HEURISTICS_FILE_PATH="F:\\OneDrive\\Projects\\Netbeans Projects\\searchAlgosAI\\src\\MapHeuristics.txt";

    public MapOfRomania() throws FileNotFoundException,IOException{
        
        BufferedReader brProps=new BufferedReader(new FileReader(MAP_PROPERTIES_FILE_PATH));
        BufferedReader brHeurs=new BufferedReader(new FileReader(MAP_HEURISTICS_FILE_PATH));
              
        String line;
        while((line=brHeurs.readLine())!=null){
            StringTokenizer st=new StringTokenizer(line);
            locationNames.add(st.nextToken());
            locationHeuristics.add(Integer.parseInt(st.nextToken()));
        }
        
        distances=new int[locationNames.size()][locationNames.size()];
        
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
        }
    }
	
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
    Node parent=new Node();
    int id;
    String name;
    Vector<Node> Children=new Vector<Node>();

    
    
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
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