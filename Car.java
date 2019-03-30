package main.java.com.huawei;

import java.util.*;
public class Car implements Comparable<Car> {
	int id;
	int start_cross_id;
	int end_cross_id;
	int Max_speed;
	int start_time;
	int real_start_time;
	int end_flag=0;
     int index=0;     //鏈�鐭窛绂荤储寮�
     int direct=0;   //鏂瑰悜  1鐩磋 2宸﹁浆 3鍙宠浆
	int []location={0,0,0,0};  //location0:start_id location1锛氳溅浣嶄簬鍝釜杞﹂亾 location2:杞﹀埌璺彛鐨勮窛绂�   location3:end_id  location[1]=0涓虹涓�閬�

	int[] the_best_path;
	int[] the_best_path_nod;
	public Car(int a,int b,int c,int d,int e) {
		this.id=a;
		this.start_cross_id=b;
		this.end_cross_id=c;
		this.Max_speed=d;
		this.start_time=e;
	}


	@Override
	public int compareTo(Car car) {
		if(car.start_time>this.start_time)
			return -1;
		else if(car.start_time==this.start_time)
		{
			if(car.id>this.id){
				return -1;
			} else if(car.id<this.id)
			{return 1;}
			else{return  0;}
		}else return 1;
	}
}
