import java.io.*;
import java.util.*;

public class searchAlgos_107_113{
	static int source,dest,ch;
	public static void main(String args[]) throws Exception{
		MapOfRomania map=new MapOfRomania();
		map.displayMap();
                
                Scanner sc=new Scanner(System.in);
                
                
                bfs(map);
		/*
		do
		{ 
		 printf("1.Up(Towards %s)\n",station_names[0]);
		 printf("2.Down(Towards %s)",station_names[nos-1]);
		 printf("\n3.Display Map Representation");
		 printf("\n0.Exit\nEnter your choice:");
		 scanf("%d",&ch);
		 switch(ch)
		 {
		   case 1:display_stations();
			      printf("Enter source: ");
			      scanf("%d",&source);
			      printf("Enter destination:");
			      scanf("%d",&dest);
			      dfs(up,source,dest);
			      break;
		   case 2:display_stations();
			      printf("Enter source: ");
			      scanf("%d",&source);
			      printf("Enter destination:");
			      scanf("%d",&dest);
			      dfs(down,source,dest);
			      break;
		   case 3:bfs(down,0);
			      break;
		 }
		}while(ch!=0);*/
		
		
	}
	
	/*void create_links()
	{
	  int i,j,n,station;
	  for(i=0;i<nos;i++)
	  {
	    printf("Enter number of immediately next stations of %s:",station_names[i]);
	    scanf("%d",&n);
	    if(n!=0)
	    {
	      display_stations();
		printf("Enter corresponding station numbers:\n");
	      for(j=0;j<n;j++)
	      {
		scanf("%d",&station);
		down[i][station]=1;
		up[station][i]=1;
	      }
	    }
	  }
	}
	static void BFS(int list[][MAX],int source,int dest)
	{
            
	}
	void display_stations()
	{
	  int j;
	  for(j=0;j<nos;j++)
	    printf("%d. %s\n",j,station_names[j]);
	}
	void display_list(int stack[],int top)
	{
	  int i;
	  for(i=0;i<top;i++)
	    printf("%s->",station_names[stack[i]]);
	  printf("%s\n",station_names[stack[top]]);
	}*/
	
        static void bfs(MapOfRomania map)
        {
            int list[][]=map.distances;
            int no_of_nodes=map.locationNames.size();
            
            Vector visited=new Vector();
            Vector toBeVisited=new Vector();
            
            int currentVisiting=source;
//            toBeVisited.add(currentVisiting);
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
                        toBeVisited.add(i);
                }
                
                //System.out.println(toBeVisited);
                
                int nextNode=dequeue(toBeVisited);
                if(nextNode==-1)
                    break;
                else
                    currentVisiting=nextNode;
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