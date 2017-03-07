package experiment.pkg4;

import java.util.*;
import java.io.*;

public class searchAlgos_107_113{
	static int source,dest,ch;
        static int no_of_nodes;  //Store number of nodes here.
        static int list[][]; //Store adjacency matrix in this.
	public static void main(String args[]) throws IOException{
		MapOfRomania map=new MapOfRomania();
		
                
                //Following is only to test validity of BFS Function
                String FILENAME="C:\\Users\\hp\\Documents\\searchAlgosAI\\testFile.txt";
                
                FileReader fr=new FileReader(FILENAME);
                
                BufferedReader br = new BufferedReader(fr);
                String sCurrentLine="";
                int i=0;
                int lineNum=0;
                while ((sCurrentLine = br.readLine()) != null)
                {
                    if(lineNum==0)
                    {
                        no_of_nodes=Integer.parseInt(sCurrentLine);
                        list=new int[no_of_nodes][no_of_nodes];
                    }
                    else
                    {
                        String adj[]=sCurrentLine.split(" ");
                        for(int k=0;k<adj.length;k++)
                        {
                            list[i][k]=Integer.parseInt(adj[k]);
                        }
                        i++;
                    }
                    lineNum++;
                }
                
                for (int k = 0; k < no_of_nodes; k++) {
                    for (int j = 0; j < no_of_nodes; j++) {
                        System.out.print(list[k][j]+" ");
                    }
                    System.out.println();
                }
                
                Scanner sc=new Scanner(System.in);
                try
                {
                    System.out.print("Enter Source node:");
                    source=sc.nextInt();
                    System.out.print("Enter Destination node:");
                    dest=sc.nextInt();
                    if(source>no_of_nodes||dest>no_of_nodes||source<0||dest<0)
                        throw new Exception();
                }
                catch(Exception e)
                {
                    System.out.println("Invalid source or destination entered!");
                    System.out.print("Enter Source node:");
                    source=sc.nextInt();
                    System.out.print("Enter Destination node:");
                    dest=sc.nextInt();
                }
                
                bfs();
		/*Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter number of locations: ");
		nos=sc.nextInt();
		create_list();
		create_links();
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
	
        static void bfs()
	{
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
		
	
}

class MapOfRomania{
	int nos;
	
	
	void createList()
	{
	  System.out.println("Enter station names(in order):");
	  for(int i=0;i<nos;i++)
	  {
	    //scanf("%s",station_names[i]);
	  }
	}
}