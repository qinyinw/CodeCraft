package main.java.com.huawei;
import java.util.*;
public class Cross implements Comparable{
	int Cross_id;
	int road1;
	int road2;
	int road3;
	int road4;
	int num_neibor;
    int []road_sort=new int[4];
	//TreeSet<Car>car_set=new TreeSet<>();
	Queue<Car>car_set=new PriorityQueue<>();
	ArrayList<Car>car_set_arr=new ArrayList<>();
	//Queue<Car>car_queqe=new PriorityQueue<>();
	ArrayList<Cross> nei_chart=new ArrayList<>();
	ArrayList<Road> nei_out=new ArrayList<Road>();
	ArrayList<Road> nei_in=new ArrayList<Road>();
	public Cross(int a,int b,int c,int d,int e) {
		this.Cross_id=a;
		this.road1=b;
		this.road2=c;
		this.road3=d;
		this.road4=e;
		road_sort[0]=b;
        road_sort[1]=c;
        road_sort[2]=d;
        road_sort[3]=e;
		if(road1>0)
			num_neibor+=1;
		if(road2>0)
			num_neibor+=1;
		if(road3>0)
			num_neibor+=1;
		if(road4>0)
			num_neibor+=1;
		Arrays.sort(road_sort);
	}

	@Override
	public int compareTo(Object o) {
		return 1;
	}
}
