package main.java.com.huawei;

import java.util.*;
public class Road implements Comparable<Road>{
	int id;
	int road_length;
	int Limit_speed;
	int num_channel;   //1,2,3
	int start_id;
	int end_id;
	int duplex;   //是否双向
	int capcity;  //容量  车道*长度
	int zuo;
	int [][]road_message;     //二维矩阵
	//ArrayList<ArrayList<Car>>road_car_sort;   //当前道路各车道车排序
	ArrayList<Car> road_all_car_sort;
	ArrayList<Car> car_state=new ArrayList<>();   //有哪些车
	HashMap<String,Car> road_car_map=new HashMap<>();
	Comparator<Car>cmp=new Comparator<Car>() {
		@Override
		public int compare(Car o1, Car o2) {
			return 	(int)((o1.location[2]+""+o1.location[1]).compareTo(o2.location[2]+""+o2.location[1]));

		}
	};
	Comparator<Car>cmp1=new Comparator<Car>() {
		@Override
		public int compare(Car o1, Car o2) {
			return 	(int)((""+o1.location[1]).compareTo(""+o2.location[1]));

		}
	};
	Queue<Car>car_priority_queue=new PriorityQueue<>(cmp);
	Queue<Car>car_perroad=new PriorityQueue<>(cmp1);
	ArrayList<Queue<Car>>road_car_sort;
	public Road(int a,int b,int c,int d,int e,int f,int g) {
		this.id=a;
		this.road_length=b;
		this.Limit_speed=c;
		this.num_channel=d;
		this.start_id=e;
		this.end_id=f;
		this.duplex=g;
		this.capcity=num_channel*road_length;
	//	this.road_message=new int[num_channel][road_length];
     //   this.road_message=new int[10][10];
        this.road_message=new int[num_channel][road_length];
		this.road_car_sort=new ArrayList();
		this.road_all_car_sort=new ArrayList();
		for(int i=0;i<this.num_channel;i++){
			this.road_car_sort.add(new PriorityQueue<>(cmp1));
		}

	}


	@Override
	public int compareTo(Road road) {
		if (this.id > road.id) return 1;
		else return -1;
	}
	
}
