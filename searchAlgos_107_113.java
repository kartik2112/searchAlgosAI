import java.util.*;

public class searchAlgos_107_113{
	static int source,dest,ch;
	public static void main(String args[]){
		MapOfRomania map=new MapOfRomania();
		
		Scanner sc=new Scanner(System.in);
		
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
		}while(ch!=0);
		
		getch();
	}
	
	void create_links()
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
	}
		
	
}

class MapOfRomania{
	int nos;
	
	
	void createList()
	{
	  System.out.println("Enter station names(in order):");
	  for(int i=0;i<nos;i++)
	  {
	    scanf("%s",station_names[i]);
	  }
	}
}