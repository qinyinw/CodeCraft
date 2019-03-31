package test;

import sun.swing.PrintingStatus;

import java.util.*;
import java.io.*;


public class Main_test  {



    public Cross get(int id, TreeSet<Cross> crosses) {
        Iterator<Cross> iterator = crosses.iterator();
        while (iterator.hasNext()) {
            Cross aCross = iterator.next();
            if (id == aCross.Cross_id) {
                return aCross;
            }
        }
        return null;

    }
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Main_test test = new Main_test();
        //读取汽车信息,返回一个carlist
        File file = new File("car.txt");
        BufferedReader bReader = new BufferedReader(new FileReader(file));
        String tem = null;
        String value = "";
        int num = 0;
        HashSet<Car> car_list = new HashSet<>();
        while ((tem = bReader.readLine()) != null) {
            value = value + tem;
            num = num + 1;
            if (tem.charAt(0) == '#')
                continue;
            String[] string2 = tem.substring(1, tem.length() - 1).split(", ");
            //System.out.println(string2[string2.length-1]);
            int[] pa = new int[5];
            for (int j = 0; j < 5; j++) {
                pa[j] = Integer.valueOf(string2[j]);
                //System.out.println(pa[j]);
            }
            Car car = new Car(pa[0], pa[1], pa[2], pa[3], pa[4]);
            car_list.add(car);
        }
        System.out.println(car_list.size());
        //读取道路信息 返回一个roadlist
        File file_road = new File("road.txt");
        BufferedReader bReader_road = new BufferedReader(new FileReader(file_road));
        String tem_road = null;
        int num_road = 0;
        HashSet<Road> load_list = new HashSet();
        Map<String, Road> load_map = new HashMap<>();//根据开始和结束道路返回道路对象
        Map<String, Road> load_map_id = new HashMap<>();//根据道路start_id and end_id 获取road对象
        while ((tem_road = bReader_road.readLine()) != null) {
            value = value + tem;
            num_road = num_road + 1;
            if (tem_road.charAt(0) == '#')
                continue;
            String[] string2 = tem_road.substring(1, tem_road.length() - 1).split(", ");
            //System.out.println(string2[string2.length-1]);
            int[] pa = new int[7];
            for (int j = 0; j < 7; j++) {
                pa[j] = Integer.valueOf(string2[j]);
                //System.out.println(pa[j]);
            }
            Road road = new Road(pa[0], pa[1], pa[2], pa[3], pa[4], pa[5], pa[6]);
            load_list.add(road);
            load_map.put(road.start_id + "+" + road.end_id, road);
            load_map_id.put(road.start_id + "+" + road.end_id, road);
            if (pa[6] == 1) {
                Road road1 = new Road(pa[0], pa[1], pa[2], pa[3], pa[5], pa[4], pa[6]);
                load_list.add(road1);
                load_map.put(road1.start_id + "+" + road1.end_id, road1);
                load_map_id.put(road1.start_id + "+" + road1.end_id, road1);
            }
        }
        System.out.println("load_list_size" + load_list.size());
        //读取路口文件  返回一个crosslist
        File file_cross = new File("cross.txt");
        BufferedReader bReader_cross = new BufferedReader(new FileReader(file_cross));
        String tem_cross = null;
        int num_cross = 0;
        TreeSet<Cross> cross_set = new TreeSet<>();

        while ((tem_cross = bReader_cross.readLine()) != null) {
            value = value + tem;
            num_road = num_road + 1;
            if (tem_cross.charAt(0) == '#')
                continue;
            String[] string2 = tem_cross.substring(1, tem_cross.length() - 1).split(", ");
            //System.out.println(string2[string2.length-1]);
            int[] pa = new int[5];
            for (int j = 0; j < 5; j++) {
                pa[j] = Integer.valueOf(string2[j]);
                //System.out.println(pa[j]);
            }
            Cross cross = new Cross(pa[0], pa[1], pa[2], pa[3], pa[4]);
            cross_set.add(cross);
        }

        //System.out.println(" cross_set"+cross_set.size());
        //找到两条道路的节点
      /*  ArrayList<Cross> two_node_set = new ArrayList<>();
        Iterator<Cross> iterator = cross_set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i = i + 1;
            Cross a = iterator.next();
            int b = a.num_neibor;
            if (b == 2)
                two_node_set.add(a);
        }
        System.out.println("双道路节点个数" + two_node_set.size());*/
        //返回一个
        Iterator<Cross> cross_iterator = cross_set.iterator();
        while (cross_iterator.hasNext()) {
            Cross cross = cross_iterator.next();
            Iterator<Road> iterator2 = load_list.iterator();
            while (iterator2.hasNext()) {
                Road abRoad = iterator2.next();
                if (cross.Cross_id == abRoad.start_id) {
                    cross.nei_out.add(abRoad);
                    Cross temp = test.get(abRoad.end_id, cross_set);
                    if (temp != null) {
                        cross.nei_chart.add(temp);
                    }
                }
                if (cross.Cross_id == abRoad.end_id) {
                    cross.nei_in.add(abRoad);
                    Cross temp = test.get(abRoad.start_id, cross_set);
                    if (temp != null) {
                        cross.nei_chart.add(temp);
                    }
                }
            }
        }

        //临接矩阵
        int[][] distance = new int[cross_set.size()][cross_set.size()];
        for (int j = 0; j < cross_set.size(); j++) {
            for (int k = 0; k < cross_set.size(); k++) {
                if (k == j)
                    distance[k][j] = 0;
                else
                    distance[k][j] = Integer.MAX_VALUE;
            }
        }

        Iterator<Road> iterator_road = load_list.iterator();
        while (iterator_road.hasNext()) {
            Road a = iterator_road.next();
            int x = a.start_id;
            int y = a.end_id;
            distance[x - 1][y - 1] = a.road_length;
            if (a.duplex == 1)
                distance[y - 1][x - 1] = a.road_length;
        }
    /*    //输出临接矩阵
		for(int k=0;k<distance.length;k++){
			for(int j=0;j<distance[0].length;j++){
				if(distance[k][j]!=Integer.MAX_VALUE)
					System.out.print(distance[k][j]+" ");
				else{
                    distance[k][j]=-1;
                    System.out.print(distance[k][j]+" ");
				}
			}
			System.out.println("lin");
		}*/
        //利用dijkstra方法算出两点静态最短路径 返回一个path_list
        int count = 0;
        String[] cross_id_info = new String[cross_set.size()];
        Iterator<Cross> iterator2 = cross_set.iterator();
        while (iterator2.hasNext()) {
            cross_id_info[count++] = String.valueOf((iterator2.next().Cross_id));
        }

        //dijstra返回一个pathmap
        HashMap<String, int[]> path_map = new HashMap<>();
        DijkstraTest test1 = new DijkstraTest();
        //path_list=test1.diget(distance,cross_id_info);
        path_map = test1.DijkstraTest(distance, cross_id_info, load_map);

        //dijstra返回一个pathmapnod
        HashMap<String, int[]> path_map_nod = new HashMap<>();
        Dijkstra_nod_test test2 = new Dijkstra_nod_test();
        //path_list=test1.diget(distance,cross_id_info);
        path_map_nod = test2.Dijkstra_nod_test(distance, cross_id_info, load_map);


  /*      DijkstraTest1 test2 = new DijkstraTest1();
        //path_list=test1.diget(distance,cross_id_info);
        path_map = test1.diget(distance, cross_id_info);*/


        //返回一个car_set
        Iterator<Cross> iterator3 = cross_set.iterator();
        while (iterator3.hasNext()) {
            Cross tmp = iterator3.next();
            Iterator<Car> iterator4 = car_list.iterator();
            while (iterator4.hasNext()) {
                Car l = iterator4.next();
                if (tmp.Cross_id == l.start_cross_id)
                    tmp.car_set.add(l);
            }
        }
        //初始派车    第一时间片
       // System.out.println(car_list.size()+"car_sizeis");
        Iterator<Cross> crossIterator = cross_set.iterator();
        //遍历所有路口
//        int num_select_paichedian_init = load_list.size() / 20;
//        TreeSet<Integer> suijishuzu_init= new TreeSet<>();
//        Random random_init = new Random();
//        for (int j = 0; j < num_select_paichedian_init; j++) {
//            suijishuzu_init.add( random_init.nextInt(load_list.size() - 1));}
//        int coun_ind_init=0;
        while (crossIterator.hasNext()) {

            //遍历当前路口所有车
//            if(if_in_array(coun_ind_init, suijishuzu_init)){
//                count+=1;
                Cross cross=crossIterator.next();
                cross_que_sys_arr(cross);
            for (int k = 0; k < cross.car_set_arr.size(); k++) {
                Car car = cross.car_set_arr.get(k);
                int[] the_best_path = path_map.get(car.start_cross_id + "+" + car.end_cross_id);
                int[] the_best_path_nod = path_map_nod.get(car.start_cross_id + "+" + car.end_cross_id);
                car.the_best_path = the_best_path;
                car.the_best_path_nod = the_best_path_nod;
                if (car.start_time == 1) {
//
                    if (judge_Current_Cogestion(load_map_id, car, the_best_path_nod, load_list)) {
                        //从派车set中删除车这个对象。
                        System.out.print(cross.car_set.size() + "che");
                        //cross.car_set_arr.remove(k);
                        cross.car_set.poll();
                        System.out.println(cross.car_set.size() + "che");
                        //派车就修改车的信息   location0:车位于哪个道路 location1： location1：车位于哪个车道 location2:车到路口的距离
                        //获取车辆调度信息
                        int[] car_adjust_message = car_adjust(load_map_id, path_map_nod, car);
                        car.location[0] = car_adjust_message[0];
                        car.location[1] = car_adjust_message[1];
                        car.location[2] = car_adjust_message[2];
                        car.location[3] = car_adjust_message[3];
                        car.index++;
                        car.real_start_time = 1;
                        //修改道路信息  将车加入到道路中
                        Road tiaosi = load_map_id.get(car.location[0] + "+" + car.location[3]);
                        tiaosi.road_car_sort.get(car_adjust_message[4] - 1).add(car);
                        load_map_id.get(car.location[0] + "+" + car.location[3]).car_priority_queue.add(car);
//                        load_map_id.get(car.location[0]+"+"+car.location[1]).road_message[car.location[1] - 1][car.location[2]] = 1;
//                        load_map_id.get(car.location[0]+"+"+car.location[1]).road_car_map.put(((car.location[1] - 1) + "" + car.location[2]), car);
                    }
                }
            }cross_que_sys_arr(cross);}
//            else{
//                coun_ind_init+=1;
//                crossIterator.next();
//                continue;

//            }
            //cross_que_sys_arr(cross);

        int nummm=0;
        Iterator<Cross>crossIterator11=cross_set.iterator();
        while(crossIterator11.hasNext()){
            nummm+=crossIterator11.next().car_set.size();
        }
        System.out.println(nummm+"car_sizeis");
        int end_all_car = 1;
        int time_count = 1;
        while (end_all_car != car_list.size()) {     //第二时间片不断调度
            time_count++;
           // System.out.println(time_count+"+"+end_all_car);
            //end_all_car = 0;
            //按cross序号遍历
            Iterator<Cross> crossIterator3 = cross_set.iterator();
            while (crossIterator3.hasNext()) {
                Cross cross = crossIterator3.next();
//                for (Road road : cross.nei_in) {
//                    sort_road(load_map_id, road);
//                }
                //找出路口所有道路的可出路口车集合
                Car first_car_road1 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[0]);
                Car first_car_road2 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[1]);
                Car first_car_road3 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[2]);
                Car first_car_road4 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[3]);
                ArrayList<Car> cross_road1_car_list = new ArrayList<>();
                ArrayList<Car> cross_road2_car_list = new ArrayList<>();
                ArrayList<Car> cross_road3_car_list = new ArrayList<>();
                ArrayList<Car> cross_road4_car_list = new ArrayList<>();
                // 路口调度
                while (first_car_road1 != null || first_car_road2 != null || first_car_road3 != null
                        || first_car_road4 != null) {
                    first_car_road1 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[0]);
                    first_car_road2 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[1]);
                    first_car_road3 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[2]);
                    first_car_road4 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[3]);
                    if (first_car_road1 == null && first_car_road2 == null && first_car_road3 == null
                            && first_car_road4 == null) {
                        break;
                    }
                    for (int h = 0; h < 4; h++)     //4条路循环调度
                    {
                        if (cross.road_sort[h] == -1) {
                            continue;
                        }
                        Road road_now = get_roadfrom_cross(cross, cross.road_sort[h]);
                        //first_car_road1=get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[h]);
                        if (first_car_road1 != null) {

                        }
                        // 找到路的可出路口车集合
                        int road_num = 0;
                        Car current_diaodu = null;
                        // ArrayList<Car> cross_road_car_list_now = new ArrayList<>();
                        for (int i2 = 0; i2 < 4; i2++) {
                            if (cross.road_sort[i2] == road_now.id) {
                                road_num = i2 + 1;
                                break;
                            }
                        }
                        if (road_num == 1) {
                            current_diaodu = first_car_road1;
                        }
                        if (road_num == 2) {
                            current_diaodu = first_car_road2;
                        }
                        if (road_num == 3) {
                            current_diaodu = first_car_road3;
                        }
                        if (road_num == 4) {
                            current_diaodu = first_car_road4;
                        }
                        //开始调度
                        if(current_diaodu==null)
                            continue;
                        Road road = load_map_id.get(current_diaodu.location[0] + "+" + current_diaodu.location[3]);
                        int[] the_best_path = path_map.get(current_diaodu.start_cross_id + "+" + current_diaodu.end_cross_id);
                        int[] the_best_path_nod = path_map_nod.get(current_diaodu.start_cross_id + "+" + current_diaodu.end_cross_id);
                        current_diaodu.the_best_path = the_best_path;
                        current_diaodu.the_best_path_nod = the_best_path_nod;
                        Road next_road = load_map_id.get(the_best_path_nod[current_diaodu.index] + "+" + the_best_path_nod[current_diaodu.index + 1]);
                        //判断车是直行1 左转 2右转 3
                        if (current_diaodu.end_flag == 1)
                            current_diaodu.direct = 4;
                        else {
                            if (road.id == cross.road1) {
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road2) {
                                    current_diaodu.direct = 2;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road3) {
                                    current_diaodu.direct = 1;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road4) {
                                    current_diaodu.direct = 3;
                                }
                            }
                            if (road.id == cross.road2) {
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road1) {
                                    current_diaodu.direct = 3;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road3) {
                                    current_diaodu.direct = 2;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road4) {
                                    current_diaodu.direct = 1;
                                }
                            }
                            if (road.id == cross.road3) {
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road2) {
                                    current_diaodu.direct = 3;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road1) {
                                    current_diaodu.direct = 1;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road4) {
                                    current_diaodu.direct = 2;
                                }
                            }
                            if (road.id == cross.road4) {
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road2) {
                                    current_diaodu.direct = 1;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road3) {
                                    current_diaodu.direct = 3;
                                }
                                if (current_diaodu.the_best_path[current_diaodu.index] == cross.road1) {
                                    current_diaodu.direct = 2;
                                }
                            }
                        }
                        if (current_diaodu.direct == 1) {
                            // 车子直走
                            System.out.println("进入直行调度");
                            //int[] the_best_path = path_map.get(current_diaodu.start_cross_id + "+" + current_diaodu.end_cross_id);
                            // int[] the_best_path_nod=path_map_nod.get(car.start_cross_id + "+" + car.end_cross_id);
//                                current_diaodu.the_best_path=the_best_path;
//                                car.the_best_path_nod=the_best_path_nod;
                            //Road next_road = load_map_id.get(current_diaodu.the_best_path_nod[current_diaodu.index]+"+"+current_diaodu.the_best_path_nod[current_diaodu.index+1]);
                            //如果下条路没有车
                            int result[] = Location_fresh(current_diaodu, next_road, load_map_id, path_map_nod);
                            //   添加到第一车道   以自己的速度
                            //修改上一条道路信息 从当前道路删掉
                            if (result[4] != -1) {
                                road.car_priority_queue.poll();
                                road.road_car_sort.get(result[4] - 1).poll();
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_message[car.location[1] - 1][car.location[2]] = 0;
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_map.remove(((car.location[1] - 1) + "" + car.location[2]));

//                                    get_road_car_sort(load_map_id, load_map_id.get(car.location[0]+"+"+car.location[3]));
                                //车子接在后面
                                current_diaodu.location[0] = next_road.start_id;
                                current_diaodu.location[1] = result[1];
                                current_diaodu.location[2] = result[2];
                                current_diaodu.location[3] = next_road.end_id;
                                current_diaodu.index++;
                                //修改道路信息    将车加入到道路中
                                next_road.car_priority_queue.add(current_diaodu);
                                next_road.road_car_sort.get(result[4] - 1).add(current_diaodu);
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_message[car.location[1] ][car.location[2]] = 1;
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_map.put(((car.location[1] - 1) + "" + car.location[2]), car);
//                                    get_road_car_sort(load_map_id, load_map_id.get(car.location[0]+"+"+car.location[3]));
//                                    //可出路口车集合更新
//                                    cross_road_car_list_now.remove(car);
                            } else {
                                if (current_diaodu.id == first_car_road1.id)
                                    first_car_road1 = null;
                                if (current_diaodu.id == first_car_road2.id)
                                    first_car_road2 = null;
                                if (current_diaodu.id == first_car_road3.id)
                                    first_car_road3 = null;
                                if (current_diaodu.id == first_car_road4.id)
                                    first_car_road4 = null;
                                continue;
                            }
                        }


                        if (current_diaodu.direct == 2) {
                            // 判断有直行干扰    找到直行道
                            //Road road_next = load_map_id.get(current_diaodu.the_best_path_nod[current_diaodu.index]+"+"+current_diaodu.the_best_path_nod[current_diaodu.index+1]);

                            Road road_straight = get_Straight_road(current_diaodu, cross, load_map_id);

                            int road_num1 = 0;
                            Car car_stright_ganrao = null;
                            //ArrayList<Car> cross_road_car_list_straight = new ArrayList<>();
                            for (int i2 = 0; i2 < 4; i2++) {
                                if (cross.road_sort[i2] == road_straight.id) {
                                    road_num1 = i2 + 1;
                                    break;
                                }
                            }
                            if (road_num1 == 1) {
                                car_stright_ganrao = first_car_road1;
                            }
                            if (road_num1 == 2) {
                                car_stright_ganrao = first_car_road2;
                            }
                            if (road_num1 == 3) {
                                car_stright_ganrao = first_car_road3;
                            }
                            if (road_num1 == 4) {
                                car_stright_ganrao = first_car_road4;
                            }

                            //get_road_car_sort(load_map_id, road_straight);   //直行安绕道 车排序
                            //Car car1 = road_straight.road_all_car_sort.get(0);
                            if(car_stright_ganrao!=null)
                            {
                                if (car_stright_ganrao.direct == 1) {
                                    break;
                                }
                                //换调度权
                            } else {
                                //   更新左转位置
                                int result[] = Location_fresh(current_diaodu, next_road, load_map_id, path_map_nod);
                                //   添加到第一车道   以自己的速度
                                //修改上一条道路信息 从当前道路删掉
                                if (result[4] != -1) {
                                    road.car_priority_queue.poll();
                                    road.road_car_sort.get(result[4] - 1).poll();
                                    //车子接在后面
                                    current_diaodu.location[0] = next_road.start_id;
                                    current_diaodu.location[1] = result[1];
                                    current_diaodu.location[2] = result[2];
                                    current_diaodu.location[3] = next_road.end_id;
                                    current_diaodu.index++;
                                    //修改道路信息    将车加入到道路中
                                    next_road.car_priority_queue.add(current_diaodu);
                                    next_road.road_car_sort.get(result[4] - 1).add(current_diaodu);

                                } else {
                                    if (current_diaodu.id == first_car_road1.id)
                                        first_car_road1 = null;
                                    if (current_diaodu.id == first_car_road2.id)
                                        first_car_road2 = null;
                                    if (current_diaodu.id == first_car_road3.id)
                                        first_car_road3 = null;
                                    if (current_diaodu.id == first_car_road4.id)
                                        first_car_road4 = null;
                                    continue;
                                }
                            }

                        }
                        if (current_diaodu.direct == 3) {
                            //找直行道
                            // 判断有直行干扰    找到直行道
                            Road road_straight_right = get_Straight_road(current_diaodu, cross, load_map_id);

                            int road_num2 = 0;
                            Car road_stright_ganrao = null;
                            //ArrayList<Car> cross_road_car_list_straight_right = new ArrayList<>();
                            for (int i2 = 0; i2 < 4; i2++) {
                                if (cross.road_sort[i2] == road_straight_right.id) {
                                    road_num2 = i2 + 1;
                                    break;
                                }
                            }
                            if (road_num2 == 1) {
                                road_stright_ganrao = first_car_road1;
                            }
                            if (road_num2 == 2) {
                                road_stright_ganrao = first_car_road2;
                            }
                            if (road_num2 == 3) {
                                road_stright_ganrao = first_car_road3;
                            }
                            if (road_num2 == 4) {
                                road_stright_ganrao = first_car_road4;
                            }

                            // get_road_car_sort(load_map_id, road_straight_right);   //直行安绕道 车排序

                            //Car car2 = road_straight_right.road_all_car_sort.get(0);
                            if(road_stright_ganrao!=null)
                            {if (road_stright_ganrao.direct == 1) {
                                break;     //换调度权
                            }}
                            //找左转道
                            // 判断有左转干扰    找到左转道
                            Road road_left_right = get_left_road(current_diaodu, cross, load_map_id);
                            int road_num3 = 0;
                            Car car_left_ganrao = null;
                            for (int i2 = 0; i2 < 4; i2++) {
                                if (cross.road_sort[i2] == road_left_right.id) {
                                    road_num3 = i2 + 1;
                                    break;
                                }
                            }
                            if (road_num3 == 1) {
                                car_left_ganrao = first_car_road1;
                            }
                            if (road_num3 == 2) {
                                car_left_ganrao = first_car_road2;
                            }
                            if (road_num3 == 3) {
                                car_left_ganrao = first_car_road3;
                            }
                            if (road_num3 == 4) {
                                car_left_ganrao = first_car_road4;
                            }
                            //get_road_car_sort(load_map_id, road_left_right);  //对左拐排序

                            //Car car3 = road_left_right.road_all_car_sort.get(0);

                            if(car_left_ganrao!=null){
                            if (car_left_ganrao.direct == 2) {
                                break;    //交换调度权
                            } }else {
                                //   更新右转位置
                                //
                                int result[] = Location_fresh(current_diaodu, next_road, load_map_id, path_map_nod);
                                //   添加到第一车道   以自己的速度
                                //修改上一条道路信息 从当前道路删掉
                                if (result[4] != -1) {
                                    road.car_priority_queue.poll();
                                    road.road_car_sort.get(result[4] - 1).poll();
                                    //车子接在后面
                                    current_diaodu.location[0] = next_road.start_id;
                                    current_diaodu.location[1] = result[1];
                                    current_diaodu.location[2] = result[2];
                                    current_diaodu.location[3] = next_road.end_id;
                                    current_diaodu.index++;
                                    //修改道路信息    将车加入到道路中
                                    next_road.car_priority_queue.add(current_diaodu);
                                    next_road.road_car_sort.get(result[4] - 1).add(current_diaodu);
                                } else {
                                    if (current_diaodu.id == first_car_road1.id)
                                        first_car_road1 = null;
                                    if (current_diaodu.id == first_car_road2.id)
                                        first_car_road2 = null;
                                    if (current_diaodu.id == first_car_road3.id)
                                        first_car_road3 = null;
                                    if (current_diaodu.id == first_car_road4.id)
                                        first_car_road4 = null;
                                    continue;
                                }

                            }
                        }
                        if (current_diaodu.direct == 4) {
                            road.car_priority_queue.poll();
                            road.road_car_sort.get(current_diaodu.location[1] - 1).poll();
//                                current_diaodu.location[0] = next_road.start_id;
//                                current_diaodu.location[1] = result[1];
//                                current_diaodu.location[2] = result[2];
//                                current_diaodu.location[3]=next_road.end_id;
//                                current_diaodu.index++;
//                                //修改道路信息    将车加入到道路中
//                                next_road.car_priority_queue.add(current_diaodu);
//                                next_road.road_car_sort.get(result[4]-1).add(current_diaodu);
////                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_message[car.location[1] ][car.location[2]] = 1;
////                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_map.put(((car.location[1] - 1) + "" + car.location[2]), car);
////                                    get_road_car_sort(load_map_id, load_map_id.get(car.location[0]+"+"+car.location[3]));
////                                    //可出路口车集合更新
////                                    cross_road_car_list_now.remove(car);
                        }

                    }
                }

                //道路调度
                //楼口第一条道路  不可出路口车集合
                if (cross.road_sort[0] != -1) {
                    Road now_road = null;
                    for (int i3 = 0; i3 < cross.nei_in.size(); i3++) {
                        if (cross.nei_in.get(i3).id == cross.road_sort[0])
                            now_road = cross.nei_in.get(i3);

                    }
                    for (int j = 0; j < now_road.road_car_sort.size(); j++) {
                        Queue<Car> car_on_road = now_road.road_car_sort.get(j);
                        ArrayList<Car> cars_road = new ArrayList<>();
                        Iterator<Car> iteratorsss = car_on_road.iterator();
                        while (iteratorsss.hasNext()) {
                            cars_road.add(car_on_road.poll());
                        }
                        //int speed_now=Math.min(, )
                        for (int m = 0; m < cars_road.size(); m++) {
                            if (m == 0) {
                                int speed_now = Math.min(cars_road.get(0).Max_speed, now_road.Limit_speed);
                                if (speed_now > cars_road.get(0).location[2])
                                    cars_road.get(0).location[2] = 0;
                                else
                                    cars_road.get(0).location[2] = +speed_now;
                            } else {
                                int speed_now = Math.min(cars_road.get(m).Max_speed, now_road.Limit_speed);
                                int speed_last = Math.min(cars_road.get(m - 1).Max_speed, now_road.Limit_speed);
                                if (cars_road.get(m).location[2] - speed_now >= cars_road.get(m - 1).location[2]) {
                                } else {
                                    cars_road.get(m).location[2] -= speed_now;
                                }
                            }

                        }
                        for (int k = 0; k < cars_road.size(); k++) {
                            car_on_road.add(cars_road.get(k));
                        }
                    }
                }
                if (cross.road_sort[1] != -1) {
                    Road now_road = null;
                    for (int i3 = 0; i3 < cross.nei_in.size(); i3++) {
                        if (cross.nei_in.get(i3).id == cross.road_sort[1])
                            now_road = cross.nei_in.get(i3);

                    }
                    for (int j = 0; j < now_road.road_car_sort.size(); j++) {
                        Queue<Car> car_on_road = now_road.road_car_sort.get(j);
                        ArrayList<Car> cars_road = new ArrayList<>();
                        Iterator<Car> iteratorsss = car_on_road.iterator();
                        while (iteratorsss.hasNext()) {
                            cars_road.add(car_on_road.poll());
                        }
                        //int speed_now=Math.min(, )
                        for (int m = 0; m < cars_road.size(); m++) {
                            if (m == 0) {
                                int speed_now = Math.min(cars_road.get(0).Max_speed, now_road.Limit_speed);
                                if (speed_now > cars_road.get(0).location[2])
                                    cars_road.get(0).location[2] = 0;
                                else
                                    cars_road.get(0).location[2] = +speed_now;
                            } else {
                                int speed_now = Math.min(cars_road.get(m).Max_speed, now_road.Limit_speed);
                                int speed_last = Math.min(cars_road.get(m - 1).Max_speed, now_road.Limit_speed);
                                if (cars_road.get(m).location[2] - speed_now >= cars_road.get(m - 1).location[2]) {
                                } else {
                                    cars_road.get(m).location[2] -= speed_now;
                                }
                            }

                        }
                        for (int k = 0; k < cars_road.size(); k++) {
                            car_on_road.add(cars_road.get(k));
                        }
                    }
                }
                if (cross.road_sort[2] != -1) {
                    Road now_road = null;
                    for (int i3 = 0; i3 < cross.nei_in.size(); i3++) {
                        if (cross.nei_in.get(i3).id == cross.road_sort[2])
                            now_road = cross.nei_in.get(i3);

                    }
                    if (now_road == null)
                        break;
                    for (int j = 0; j < now_road.road_car_sort.size(); j++) {
                        Queue<Car> car_on_road = now_road.road_car_sort.get(j);
                        ArrayList<Car> cars_road = new ArrayList<>();
                        Iterator<Car> iteratorsss = car_on_road.iterator();
                        while (iteratorsss.hasNext()) {
                            cars_road.add(car_on_road.poll());
                        }
                        //int speed_now=Math.min(, )
                        for (int m = 0; m < cars_road.size(); m++) {
                            if (m == 0) {
                                int speed_now = Math.min(cars_road.get(0).Max_speed, now_road.Limit_speed);
                                if (speed_now > cars_road.get(0).location[2])
                                    cars_road.get(0).location[2] = 0;
                                else
                                    cars_road.get(0).location[2] = +speed_now;
                            } else {
                                int speed_now = Math.min(cars_road.get(m).Max_speed, now_road.Limit_speed);
                                int speed_last = Math.min(cars_road.get(m - 1).Max_speed, now_road.Limit_speed);
                                if (cars_road.get(m).location[2] - speed_now >= cars_road.get(m - 1).location[2]) {
                                } else {
                                    cars_road.get(m).location[2] -= speed_now;
                                }
                            }

                        }
                        for (int k = 0; k < cars_road.size(); k++) {
                            car_on_road.add(cars_road.get(k));
                        }
                    }
                }
                if (cross.road_sort[3] != -1) {
                    Road now_road = null;
                    for (int i3 = 0; i3 < cross.nei_in.size(); i3++) {
                        if (cross.nei_in.get(i3).id == cross.road_sort[3])
                            now_road = cross.nei_in.get(i3);

                    }
                    if (now_road == null)
                        break;
                    ;
                    for (int j = 0; j < now_road.road_car_sort.size(); j++) {
                        Queue<Car> car_on_road = now_road.road_car_sort.get(j);
                        ArrayList<Car> cars_road = new ArrayList<>();
                        Iterator<Car> iteratorsss = car_on_road.iterator();
                        while (iteratorsss.hasNext()) {
                            cars_road.add(car_on_road.poll());
                        }
                        //int speed_now=Math.min(, )
                        for (int m = 0; m < cars_road.size(); m++) {
                            if (m == 0) {
                                int speed_now = Math.min(cars_road.get(0).Max_speed, now_road.Limit_speed);
                                if (speed_now > cars_road.get(0).location[2])
                                    cars_road.get(0).location[2] = 0;
                                else
                                    cars_road.get(0).location[2] = +speed_now;
                            } else {
                                int speed_now = Math.min(cars_road.get(m).Max_speed, now_road.Limit_speed);
                                int speed_last = Math.min(cars_road.get(m - 1).Max_speed, now_road.Limit_speed);
                                if (cars_road.get(m).location[2] - speed_now >= cars_road.get(m - 1).location[2]) {
                                } else {
                                    cars_road.get(m).location[2] -= speed_now;
                                }
                            }

                        }
                        for (int k = 0; k < cars_road.size(); k++) {
                            car_on_road.add(cars_road.get(k));
                        }
                    }
                }
                //后面时间片派车
            }
            //考虑初始派车采用随机节点    再考虑选取车少的道路发车   个数为随机参数  可考虑num随时间动态变小
            Iterator<Cross> paiche_itetator = cross_set.iterator();
            int num_select_paichedian = load_list.size() / 15;
            TreeSet<Integer> suijishuzu= new TreeSet<>();
            Random random = new Random();
            for (int j = 0; j < num_select_paichedian; j++) {
                suijishuzu.add( random.nextInt(load_list.size() - 1));}
            int coun_ind=0;
            while (paiche_itetator.hasNext()) {
                    if(if_in_array(coun_ind, suijishuzu)){
                        coun_ind+=1;
                        Cross cross=paiche_itetator.next();
                        cross_que_sys_arr(cross);
                        for (int k=0;k<cross.car_set_arr.size();k++)
                        {   Car car=cross.car_set_arr.get(k);
                            if (car.start_time <= time_count) {
                                ////判断当前车在当前道路是否拥堵，如果不拥堵，则派车
                                int[] the_best_path = path_map.get(car.start_cross_id + "+" + car.end_cross_id);
                                int[] the_best_path_nod=path_map_nod.get(car.start_cross_id + "+" + car.end_cross_id);
                                car.the_best_path=the_best_path;
                                car.the_best_path_nod=the_best_path_nod;
                                if (car.start_time <=time_count) {
//
                                    ////判断当前车在当前道路是否拥堵，如果不拥堵，则派车
                                    if (judge_Current_Cogestion(load_map_id, car, the_best_path_nod,load_list)) {
                                        //从派车set中删除车这个对象。
                                        System.out.print(cross.car_set.size() + "chedesjp");
                                        cross.car_set.poll();
                                        System.out.print(cross.car_set.size() + "chedesjp");
                                        // cross.car_set_arr.remove(k);
                                        //派车就修改车的信息   location0:车位于哪个道路 location1： location1：车位于哪个车道 location2:车到路口的距离
                                        //获取车辆调度信息
                                        int[] car_adjust_message = car_adjust(load_map_id, path_map_nod, car);
                                        if(car_adjust_message[4]!=-1){

                                            car.location[0] = car_adjust_message[0];
                                            car.location[1] = car_adjust_message[1];
                                            car.location[2] = car_adjust_message[2];
                                            car.location[3] = car_adjust_message[3];
                                            car.index++;
                                            car.real_start_time = time_count;
                                            //修改道路信息  将车加入到道路中
                                            load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_sort.get(car_adjust_message[4]-1).add(car);
                                            load_map_id.get(car.location[0]+"+"+car.location[3]).car_priority_queue.add(car); }
                                    }else{
                                        continue;
                                    }
                                }
                            }
                        }cross_que_sys_arr(cross);
                    }
                    else{
                        paiche_itetator.next();
                        coun_ind+=1;
                        continue;
                    }
                }

                //判断是否车子都已派出

                end_all_car = 0;
                Iterator<Cross> iterator_cross1 = cross_set.iterator();
                //System.out.println(iterator_cross1.next().car_set.size());
                while (iterator_cross1.hasNext()) {
                    Cross crossss = iterator_cross1.next();
                    //System.out.println(crossss.car_set.size());
                    end_all_car = end_all_car + crossss.car_set.size();
                }
//            System.out.println(end_all_car+"car"+time_count);
            end_all_car = 0;
            Iterator<Car> iterator_cross = car_list.iterator();
            //System.out.println(iterator_cross.next().car_set.size());
            while (iterator_cross.hasNext()) {
                Car crossss = iterator_cross.next();
//                System.out.println();
                end_all_car = end_all_car + crossss.end_flag;
            }
           // System.out.println(end_all_car+"car_sizeis");
//        System.out.println("文件输出");
//        //文件输出
//          print_txt(car_list);
//        System.out.println("文件输出");

            }
        System.out.println(time_count + "sjp");
        }


    private static void print_txt(HashSet<Car> car_list) {
        File answer_txt=new File("d:\\answer.txt");    //新生文件路径
        if(answer_txt.exists()){         //如果新生文件存在，新生文件重命名
                String pathname="d:\\answer"+Math.random()+".txt";
                answer_txt=new File(pathname);
        }
        try {
            FileWriter fw=new FileWriter(answer_txt);
            BufferedWriter bw=new BufferedWriter(fw);
            Iterator<Car> carIterator=car_list.iterator();
                while(carIterator.hasNext()){
                    Car car=carIterator.next();
                    int car_id=car.id;
                    int car_real_start_time=car.real_start_time;
                   int [] car_the_best_path=car.the_best_path;
                    String S="";
                    for(int i =0;i<car_the_best_path.length;i++){

                        S =S+","+car_the_best_path[i];
                    }
                    String string="("+car_id+","+car_real_start_time+S+")";
                    bw.write(string);   //向新生文件写入行数据
                    bw.newLine();     //换行
                }

         bw.flush();
         bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private static void sort_road(Map<String, Road> load_map_id,Road road1) {
//        //将路的dao limian car进行排序
//
//        ArrayList<Car> channel_sort = new ArrayList();
//        road1.road_car_sort=new ArrayList<ArrayList<Car>>();
//        int count=0;
//        //找到路上的车    并处理
//        for (int i1 = 0; i1 < road1.num_channel; i1++) {
//            for (int j1 = 0; j1 < road1.road_length; j1++)
//            {
//                if (road1.road_message[i1][j1] == 1) {
//                    //根据道路位置获取到此位置的车
//                    Car car = road1.road_car_map.get((i1 + "+" + j1));
//                    //找到当前车 所在车道的所有车（按j排好序了）
//                    channel_sort.add(car);
//                    ArrayList cars_list = channel_sort;
//                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_sort.add(cars_list);
//
//                  /*  if (car == cars_list.get(0)) {   //找到排好序的第一个车 没有前车
//
//                    } else {     //car_list后面的车，要考虑前车速度对后车的影响
//
//                    }*/
//                }
//            }
//        }
//
//    }
    private static boolean judge_Current_Cogestion_last(Map<String, Road> load_map_id, Car car) {
		//将当前车所在车道信息判断
		//如果当前道路车辆达到容纳量的  1/3 就判断为拥堵gnajue
		if((load_map_id.get(car.location[0]).car_state.size())/(load_map_id.get(car.location[0]).capcity)>1/4){
			return  false;
		}else
			return true;
	}
	//车辆进入道路时调度
	private static int[] car_adjust(Map<String,Road>load_map_id,HashMap<String ,int []> path_map_nod,Car car) {
		//当前车的最优路径
		int[] path_array= path_map_nod.get(car.start_cross_id+"+"+car.end_cross_id);
		Road temroad=null;
		if(car.index==0)
        {
            temroad=load_map_id.get(path_array[0]+"+"+path_array[1]);
        }
		else{
            temroad=load_map_id.get(car.location[0]+"+"+car.location[3]);
        }
		//temroad=load_map_id.get(path_array[0]+"+"+path_array[1]);
		int num_channel=temroad.num_channel;
		int road_length=temroad.road_length;
		int[][] road_message=temroad.road_message;
		int []result=new int[5];
		int []i_j=new int[2];   //最后一个车的位置

//		outer:for(int i=num_channel-1;i>=0;i--){
//					for(int j=road_length-1;j>=0;j--){
//						if(road_message[i][j]==1)
//						{ i_j[0]=i;
//						i_j[1]=j;
//						break outer;
//						}
//					}
//				}
        Car last_car=null;
        int loc_channal=0;
        for(int j=0;j<temroad.road_car_sort.size();j++){
            ArrayList<Car> tttt=new ArrayList<>();
            Car tmp=null;
            for(int k=0;k<temroad.road_car_sort.get(j).size();k++){
                tttt.add(temroad.road_car_sort.get(j).poll());
            }
//            Object[] obj=temroad.road_car_sort.get(j).toArray();
            int speed=Math.min(car.Max_speed,load_map_id.get(path_array[0]+"+"+path_array[1]).Limit_speed);
            if(tttt.size()==0){
                result[0] =temroad.start_id;
                result[1]=j+1;
                result[2]=speed;
                result[3]=temroad.end_id;
                result[4]=j+1;
                return  result;
        }
            tmp=tttt.get(tttt.size()-1);
            for(int k=0;k<tttt.size();k++){
                temroad.road_car_sort.get(j).add(tttt.get(k));
            }

            if(tmp.location[1]==temroad.road_length-1){
                continue;
            }
            else{
                last_car=tmp;
                loc_channal=j+1;
                break;
            }
        }
//		Iterator<Car>carIterator=temroad.car_state.iterator();
//
//		while(carIterator.hasNext()){
//			Car tcar=carIterator.next();
//			int channel=tcar.location[1];
//			int dis_cross=tcar.location[2];
//			if(i_j[0]+1==channel&&dis_cross==i_j[1])
//			{
//				last_car=tcar;
//			break;
//			}
//		}

			int speed=Math.min(car.Max_speed,load_map_id.get(path_array[0]+"+"+path_array[1]).Limit_speed);
            car.location[0]=temroad.start_id;
			car.location[1]=loc_channal;
			if(speed>=road_length-last_car.location[2]){
			    car.location[2]=last_car.location[2]+1;
            }
			else{
			    car.location[2]=road_length-speed;
            }
			//car.location[2]=road_length-speed;
            car.location[3]=temroad.end_id;

//			int last_speed=last_car.Max_speed;
//			int speed=car.Max_speed;
//			if(speed>temroad.road_length-last_car.location[2]&&last_car.location[2]==temroad.road_length-1)//最后一辆车是为末端
//			{//
//				car.location[0]=temroad.start_id;
//				car.location[1]=last_car.location[1]+1;
//				car.location[2]=road_length-speed;
//				car.location[3]=temroad.end_id;
//			}//判断最后一辆车是否影响后车速度
//			if(speed>temroad.road_length-last_car.location[2]&&last_car.location[2]!=temroad.road_length-1)//最后一辆车否为末端
//			{
//                car.location[0]=temroad.start_id;
//				car.location[1]=last_car.location[1] ;
//				car.location[2]=last_car.location[2]+1;
//                car.location[3]=temroad.end_id;
//			}
//			if(speed<temroad.road_length-last_car.location[2])//最后一辆车否为末端
//			{
//                car.location[0]=temroad.start_id;
//				car.location[1]=last_car.location[1] ;
//				car.location[2]=road_length-speed;
//                car.location[3]=temroad.end_id;
//			}
		//result={car.location[0],car.location[1],car.location[2],car.location[3],loc_channal};
		result[0]=car.location[0];result[1]=car.location[1];result[2]=car.location[2];result[3]=car.location[3];result[4]=loc_channal;
		return result;
	}
	//判断车辆出发前道路拥堵情况
	private static boolean judge_Current_Cogestion( Map<String,Road>load_map_id,Car car,int[]the_best_path_nod,HashSet<Road> load_list) {
		//将当前车所在车道信息判断
		//如果当前道路车辆达到容纳量的  1/2 就判断为拥堵
      /*  if((load_map_id.get(the_best_path[0])).car_state==null){
            return false;
        }*/
      Road temp=load_map_id.get(the_best_path_nod[0]+"+"+the_best_path_nod[1]);
		if(((temp).car_priority_queue.size())/((temp).capcity)>=1.0/15){
	return  false;}
	    if(car_count_on_road(load_list)>0.1*load_list.size()){
	        return false;
        }

//		else
			return true;
	}
          //对路里面所有车排序
    public static void get_road_car_sort(Map<String, Road> load_map_id,Road road1){
        ArrayList[] channel_sort = new ArrayList[road1.num_channel];
    road1.road_all_car_sort=new ArrayList<Car>();
    //找到路上的车    并处理
    for (int j1 = 0; j1 < road1.road_length; j1++)  {
        for (int i1 = 0; i1 < road1.num_channel; i1++)
        {
            if (road1.road_message[i1][j1] == 1) {
                //根据道路位置获取到此位置的车
                Car car = road1.road_car_map.get((i1 + "+" + j1));
                //找到当前车 所在车道的所有车（按j排好序了）
                load_map_id.get(car.location[0]+"+"+car.location[3]).road_all_car_sort.add(car);
            }
            }
        }
    }
          //返回直行道路
    private static Road  get_Straight_road(Car car,Cross cross,Map<String, Road> load_map_id){
        int[] cross_all_road={cross.road1,cross.road2,cross.road3,cross.road4};
        int k=0;
        for(int i2=0;i2<4;i2++){
            if(load_map_id.get(car.location[0]+"+"+car.location[3]).id == cross_all_road[i2]){
                k=i2; break;
            }
        }
        if(k>1){
            k=k-2;
        }else{
            k=k+2;
        }
        Road road_straight=new Road(0,0,0,0,0,0,0);
        for(int i4=0;i4<cross.nei_in.size();i4++){
            if (cross.nei_in.get(i4).id==cross_all_road[k]){
                road_straight=cross.nei_in.get(i4);
            }
        }
        return road_straight;
    }

          //返回左转道路
    private static Road get_left_road(Car car, Cross cross, Map<String, Road> load_map_id) {
        int[] cross_all_road={cross.road1,cross.road2,cross.road3,cross.road4};
        int k2=0;
        for(int i2=0;i2<4;i2++){
            if(load_map_id.get(car.location[0]+"+"+car.location[3]).id== cross_all_road[i2]){
                k2=i2;
                break;
            }
        }
        if(k2>2){
            k2=0;
        }else{
            k2=k2+1;
        }
        Road road_left=new Road(0,0,0,0,0,0,0);
        for(int i4=0;i4<cross.nei_in.size();i4++){
            if (cross.nei_in.get(i4).id==cross_all_road[k2]){
                road_left=cross.nei_in.get(i4);
            }
        }
        return road_left;
    }
    private  static  Road get_roadfrom_cross(Cross cross,int roadid){

        for(int i1=0;i1<cross.nei_in.size();i1++){
            if (cross.nei_in.get(i1).id==roadid){
              Road   road=cross.nei_in.get(i1);
                return road;
            }
        }
      return  null;
    }
    private  static  Car get_first_priority(Cross cross,Map<String, Road> load_map_id,HashMap<String ,int []> path_map_nod,int sortid){
        //int[] path_array= path_map_nod.get(car.start_cross_id+"+"+car.end_cross_id);
       if(sortid==-1){
           return  null;
       }
        Road temroad=get_roadfrom_cross(cross,sortid);
        Car car=null;
        if(temroad==null)
            return  null;
        if(temroad.car_priority_queue.size()==0)
            return  null;
        if(temroad.car_priority_queue.size()!=0)
            car=temroad.car_priority_queue.peek();
        if (car==null)
          return  null;
        //int[] path_array= path_map_nod.get(car.start_cross_id+"+"+car.end_cross_id);
        Car result=null;
        //进行车速判断
        int speed1;
        int speed2;
        int s1;
        int s2;
        speed1 = Math.min(load_map_id.get(car.location[0]+"+"+car.location[3]).Limit_speed, car.Max_speed);
        //找到出路口的车
        if (speed1 > car.location[2]) {
            int[] the_best_path = path_map_nod.get(car.start_cross_id + "+" + car.end_cross_id);
            if (car.index == the_best_path.length-1) {
                //kai ru cheku
                car.end_flag=1;

                //cross_road1_car_list.add(car);
            }
            else{
                Road next_road = load_map_id.get(the_best_path[car.index] + "+" + the_best_path[car.index + 1]);
                speed2 = Math.min(next_road.Limit_speed, car.Max_speed);
                s1 = car.location[2];
                if (speed2 - s1 > 0 && s1 < speed2) {     //可出路口条件2和条件三
                    //cross_road1_car_list.add(car);
                    result=car;
                }
//                else {                 //车正好到路口
//                    car.location[2] = 0;
//                }
            }
        }
    return  result;
    }
    // 在每次过路口时更新位置，且返回更新在哪条道上；如果在-1道上，则交出调度权
    private static int[] Location_fresh(Car car,Road road,Map<String, Road> load_map_id,HashMap<String ,int []> path_map_nod){
        int[] path_array= path_map_nod.get(car.start_cross_id+"+"+car.end_cross_id);

        Road temroad=road;
        int []result=new int[5];
        Car last_car=null;
        int loc_channal=-1;
        for(int j=0;j<temroad.road_car_sort.size();j++){
            ArrayList<Car> tttt=new ArrayList<>();
            Car tmp=null;
            int speed=Math.min(car.Max_speed,load_map_id.get(path_array[0]+"+"+path_array[1]).Limit_speed);
            for(int k=0;k<temroad.road_car_sort.get(j).size();k++){
                tttt.add(temroad.road_car_sort.get(j).poll());
            }
            if(tttt.size()==0){
                result[0] =temroad.start_id;
                result[1]=j+1;
                result[2]=speed;
                result[3]=temroad.end_id;
                result[4]=j+1;
                return  result;
            }
            tmp=tttt.get(tttt.size()-1);
            for(int k=0;k<tttt.size();k++){
                temroad.road_car_sort.get(j).add(tttt.get(k));
            }
            if(tmp.location[1]==temroad.road_length-1){
                continue;
            }
            else{
                last_car=tmp;
                loc_channal=j+1;
                break;
            }
        }
        //若下一条路堵死  导致可出路口车不能通行，则返回-1，此时交换调度权；
        if(loc_channal>0){
            int speed=(car.Max_speed-temroad.Limit_speed);
            car.location[0]=temroad.start_id;
            car.location[1]=loc_channal;
            if(speed>=temroad.road_length-last_car.location[2]){
                car.location[2]=last_car.location[2]+1;
            }
            else{
                car.location[2]=temroad.road_length-speed;
            }
            car.location[3]=temroad.end_id;
        }


      result[0]=car.location[0];result[1]=car.location[1];result[2]=car.location[2];result[3]=car.location[3];result[4]=loc_channal;
        return result;
    }
    private static void cross_que_sys_arr(Cross cross){
        Queue<Car>tmp_que=cross.car_set;
        ArrayList<Car>tmp_arr=cross.car_set_arr;
        for(int j=0;j<tmp_arr.size();j++){
            tmp_arr.remove(j);
        }
        Iterator<Car>tmp_it=tmp_que.iterator();
        while(tmp_it.hasNext()){
            tmp_arr.add(tmp_it.next());
        }
    }
    private static  void cross_arr_sys_que(Cross cross){
        Queue<Car>tmp_que=cross.car_set;
        ArrayList<Car>tmp_arr=cross.car_set_arr;
        for(int k=0;k<tmp_que.size();k++){
            tmp_que.poll();
        }
        for(int k=0;k<tmp_arr.size();k++){
            tmp_que.add(tmp_arr.get(k));
        }
    }
    private  static int car_count_on_road(HashSet<Road> load_list){
        int result=0;
        Iterator<Road>roadIterator=load_list.iterator();
        while (roadIterator.hasNext()){
            result+=roadIterator.next().car_priority_queue.size();
        }
        return result;
    }
    private  static boolean if_in_array(int x,TreeSet<Integer>z){
        Iterator<Integer>iterator=z.iterator();
        while(iterator.hasNext()){
            if(iterator.next()==x)
                return true;
        }
        return  false;
    }
    }
