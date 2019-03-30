package main.java.com.huawei;
import java.util.*;
import java.io.*;


public class Main  {



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
        Main test = new Main();
        //鐠囪褰囧Ч鍊熸簠娣団剝浼�,鏉╂柨娲栨稉锟芥稉鐚歛rlist
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
        //鐠囪褰囬柆鎾圭熅娣団剝浼� 鏉╂柨娲栨稉锟芥稉鐚簅adlist
        File file_road = new File("road.txt");
        BufferedReader bReader_road = new BufferedReader(new FileReader(file_road));
        String tem_road = null;
        int num_road = 0;
        HashSet<Road> load_list = new HashSet();
        Map<String, Road> load_map = new HashMap<>();//閺嶈宓佸锟芥慨瀣嫲缂佹挻娼柆鎾圭熅鏉╂柨娲栭柆鎾圭熅鐎电钖�
        Map<String, Road> load_map_id = new HashMap<>();//閺嶈宓侀柆鎾圭熅start_id and end_id 閼惧嘲褰噐oad鐎电钖�
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
        //鐠囪褰囩捄顖氬經閺傚洣娆�  鏉╂柨娲栨稉锟芥稉鐚歳osslist
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
        //閹垫儳鍩屾稉銈嗘蒋闁捁鐭鹃惃鍕Ν閻愶拷
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
        System.out.println("閸欏矂浜剧捄顖濆Ν閻愰�涢嚋閺侊拷" + two_node_set.size());*/
        //鏉╂柨娲栨稉锟芥稉锟�
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

        //娑撳瓨甯撮惌鈺呮█
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
    /*    //鏉堟挸鍤稉瀛樺复閻晠妯�
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
        //閸掆晝鏁ijkstra閺傝纭剁粻妤�鍤稉銈囧仯闂堟瑦锟戒焦娓堕惌顓＄熅瀵帮拷 鏉╂柨娲栨稉锟芥稉鐚礱th_list
        int count = 0;
        String[] cross_id_info = new String[cross_set.size()];
        Iterator<Cross> iterator2 = cross_set.iterator();
        while (iterator2.hasNext()) {
            cross_id_info[count++] = String.valueOf((iterator2.next().Cross_id));
        }

        //dijstra鏉╂柨娲栨稉锟芥稉鐚礱thmap
        HashMap<String, int[]> path_map = new HashMap<>();
        DijkstraTest test1 = new DijkstraTest();
        //path_list=test1.diget(distance,cross_id_info);
        path_map = test1.DijkstraTest(distance, cross_id_info, load_map);

        //dijstra鏉╂柨娲栨稉锟芥稉鐚礱thmapnod
        HashMap<String, int[]> path_map_nod = new HashMap<>();
        Dijkstra_nod_test test2 = new Dijkstra_nod_test();
        //path_list=test1.diget(distance,cross_id_info);
        path_map_nod = test2.Dijkstra_nod_test(distance, cross_id_info, load_map);


  /*      DijkstraTest1 test2 = new DijkstraTest1();
        //path_list=test1.diget(distance,cross_id_info);
        path_map = test1.diget(distance, cross_id_info);*/


        //鏉╂柨娲栨稉锟芥稉鐚歛r_set
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
        //閸掓繂顫愬ú鎹愭簠    缁楊兛绔撮弮鍫曟？閻楋拷
       // System.out.println(car_list.size()+"car_sizeis");
        Iterator<Cross> crossIterator = cross_set.iterator();
        //闁秴宸婚幍锟介張澶庣熅閸欙拷
//        int num_select_paichedian_init = load_list.size() / 20;
//        TreeSet<Integer> suijishuzu_init= new TreeSet<>();
//        Random random_init = new Random();
//        for (int j = 0; j < num_select_paichedian_init; j++) {
//            suijishuzu_init.add( random_init.nextInt(load_list.size() - 1));}
//        int coun_ind_init=0;
        while (crossIterator.hasNext()) {

            //闁秴宸昏ぐ鎾冲鐠侯垰褰涢幍锟介張澶庢簠
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
                        //娴犲孩娣虫潪顩檈t娑擃厼鍨归梽銈堟簠鏉╂瑤閲滅�电钖勯妴锟�
                        System.out.print(cross.car_set.size() + "che");
                        //cross.car_set_arr.remove(k);
                        cross.car_set.poll();
                        System.out.println(cross.car_set.size() + "che");
                        //濞叉崘婧呯亸鍙樻叏閺�纭呮簠閻ㄥ嫪淇婇幁锟�   location0:鏉烇缚缍呮禍搴℃憿娑擃亪浜剧捄锟� location1閿涳拷 location1閿涙俺婧呮担宥勭艾閸濐亙閲滄潪锕備壕 location2:鏉烇箑鍩岀捄顖氬經閻ㄥ嫯绐涚粋锟�
                        //閼惧嘲褰囨潪锕佺窢鐠嬪啫瀹虫穱鈩冧紖
                        int[] car_adjust_message = car_adjust(load_map_id, path_map_nod, car);
                        car.location[0] = car_adjust_message[0];
                        car.location[1] = car_adjust_message[1];
                        car.location[2] = car_adjust_message[2];
                        car.location[3] = car_adjust_message[3];
                        car.index++;
                        car.real_start_time = 1;
                        //娣囶喗鏁奸柆鎾圭熅娣団剝浼�  鐏忓棜婧呴崝鐘插弳閸掍即浜剧捄顖欒厬
                       //load_map_id.get(car.location[0] + "+" + car.location[3]);
                       // tiaosi.car_priority_queue.add(car);
                       load_map_id.get(car.location[0] + "+" + car.location[3]).road_car_sort.get(car_adjust_message[4] - 1).add(car);
                        load_map_id.get(car.location[0] + "+" + car.location[3]).car_priority_queue.add(car);
                        {
                        	
                        }
//                        load_map_id.get(car.location[0]+"+"+car.location[1]).road_message[car.location[1] - 1][car.location[2]] = 1;
//                        load_map_id.get(car.location[0]+"+"+car.location[1]).road_car_map.put(((car.location[1] - 1) + "" + car.location[2]), car);
                    }
                }
            }cross_que_sys_arr(cross);}
//            else{
//                coun_ind_init+=1;
//                crossIterator.next();
//                continue;
//
//            }
            //cross_que_sys_arr(cross);
//        }
        int nummm=0;
        Iterator<Cross>crossIterator11=cross_set.iterator();
        while(crossIterator11.hasNext()){
            nummm+=crossIterator11.next().car_set.size();
        }
        System.out.println(nummm+"car_sizeis");
        int end_all_car = 1;
        int time_count = 1;
        while (end_all_car != car_list.size()) {     //缁楊兛绨╅弮鍫曟？閻楀洣绗夐弬顓＄殶鎼达拷
            time_count++;
           // System.out.println(time_count+"+"+end_all_car);
            //end_all_car = 0;
            //閹稿々ross鎼村繐褰块柆宥呭坊
            Iterator<Cross> crossIterator3 = cross_set.iterator();
            while (crossIterator3.hasNext()) {
                Cross cross = crossIterator3.next();
//                for (Road road : cross.nei_in) {
//                    sort_road(load_map_id, road);
//                }
                //閹垫儳鍤捄顖氬經閹碉拷閺堝浜剧捄顖滄畱閸欘垰鍤捄顖氬經鏉烇箓娉﹂崥锟�
                Car first_car_road1 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[0]);
                Car first_car_road2 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[1]);
                Car first_car_road3 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[2]);
                Car first_car_road4 = get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[3]);
                
                if(first_car_road1 == null && first_car_road2 == null && first_car_road3 == null
                       &&first_car_road4 == null) {
                	continue;
                }
                ArrayList<Car> cross_road1_car_list = new ArrayList<>();
                ArrayList<Car> cross_road2_car_list = new ArrayList<>();
                ArrayList<Car> cross_road3_car_list = new ArrayList<>();
                ArrayList<Car> cross_road4_car_list = new ArrayList<>();
                // 鐠侯垰褰涚拫鍐ㄥ
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
                    for (int h = 0; h < 4; h++)     //4閺壜ょ熅瀵邦亞骞嗙拫鍐ㄥ
                    {
                        if (cross.road_sort[h] == -1) {
                            continue;
                        }
                        Road road_now = get_roadfrom_cross(cross, cross.road_sort[h]);
                        //first_car_road1=get_first_priority(cross, load_map_id, path_map_nod, cross.road_sort[h]);
                        if (first_car_road1 != null) {

                        }
                        // 閹垫儳鍩岀捄顖滄畱閸欘垰鍤捄顖氬經鏉烇箓娉﹂崥锟�
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
                        //瀵拷婵鐨熸惔锟�
                        if(current_diaodu==null)
                            continue;
                        Road road = load_map_id.get(current_diaodu.location[0] + "+" + current_diaodu.location[3]);
                        int[] the_best_path = path_map.get(current_diaodu.start_cross_id + "+" + current_diaodu.end_cross_id);
                        int[] the_best_path_nod = path_map_nod.get(current_diaodu.start_cross_id + "+" + current_diaodu.end_cross_id);
                        current_diaodu.the_best_path = the_best_path;
                        current_diaodu.the_best_path_nod = the_best_path_nod;
                        Road next_road = load_map_id.get(the_best_path_nod[current_diaodu.index] + "+" + the_best_path_nod[current_diaodu.index + 1]);
                        //閸掋倖鏌囨潪锔芥Ц閻╃顢�1 瀹革箒娴� 2閸欏疇娴� 3
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
                            // 鏉烇箑鐡欓惄纾嬭泲
                            System.out.println("鏉╂稑鍙嗛惄纾嬵攽鐠嬪啫瀹�");
                            //int[] the_best_path = path_map.get(current_diaodu.start_cross_id + "+" + current_diaodu.end_cross_id);
                            // int[] the_best_path_nod=path_map_nod.get(car.start_cross_id + "+" + car.end_cross_id);
//                                current_diaodu.the_best_path=the_best_path;
//                                car.the_best_path_nod=the_best_path_nod;
                            //Road next_road = load_map_id.get(current_diaodu.the_best_path_nod[current_diaodu.index]+"+"+current_diaodu.the_best_path_nod[current_diaodu.index+1]);
                            //婵″倹鐏夋稉瀣蒋鐠侯垱鐥呴張澶庢簠
                            int result[] = Location_fresh(current_diaodu, next_road, load_map_id, path_map_nod);
                            //   濞ｈ濮為崚鎵儑娑擄拷鏉烇箓浜�   娴犮儴鍤滃杈╂畱闁喎瀹�
                            //娣囶喗鏁兼稉濠佺閺夛繝浜剧捄顖欎繆閹拷 娴犲骸缍嬮崜宥変壕鐠侯垰鍨归幒锟�
                            if (result[4] != -1) {
                                road.car_priority_queue.poll();
                                road.road_car_sort.get(result[4] - 1).poll();
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_message[car.location[1] - 1][car.location[2]] = 0;
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_map.remove(((car.location[1] - 1) + "" + car.location[2]));

//                                    get_road_car_sort(load_map_id, load_map_id.get(car.location[0]+"+"+car.location[3]));
                                //鏉烇箑鐡欓幒銉ユ躬閸氬酣娼�
                                current_diaodu.location[0] = next_road.start_id;
                                current_diaodu.location[1] = result[1];
                                current_diaodu.location[2] = result[2];
                                current_diaodu.location[3] = next_road.end_id;
                                current_diaodu.index++;
                                //娣囶喗鏁奸柆鎾圭熅娣団剝浼�    鐏忓棜婧呴崝鐘插弳閸掍即浜剧捄顖欒厬
                                next_road.car_priority_queue.add(current_diaodu);
                                next_road.road_car_sort.get(result[4] - 1).add(current_diaodu);
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_message[car.location[1] ][car.location[2]] = 1;
//                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_map.put(((car.location[1] - 1) + "" + car.location[2]), car);
//                                    get_road_car_sort(load_map_id, load_map_id.get(car.location[0]+"+"+car.location[3]));
//                                    //閸欘垰鍤捄顖氬經鏉烇箓娉﹂崥鍫熸纯閺傦拷
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
                            // 閸掋倖鏌囬張澶屾纯鐞涘苯鍏遍幍锟�    閹垫儳鍩岄惄纾嬵攽闁拷
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

                            //get_road_car_sort(load_map_id, road_straight);   //閻╃顢戠�瑰绮柆锟� 鏉烇附甯撴惔锟�
                            //Car car1 = road_straight.road_all_car_sort.get(0);
                            if (car_stright_ganrao.direct == 1) {
                                break;     //閹广垼鐨熸惔锔芥綀
                            } else {
                                //   閺囧瓨鏌婂锕佹祮娴ｅ秶鐤�
                                int result[] = Location_fresh(current_diaodu, next_road, load_map_id, path_map_nod);
                                //   濞ｈ濮為崚鎵儑娑擄拷鏉烇箓浜�   娴犮儴鍤滃杈╂畱闁喎瀹�
                                //娣囶喗鏁兼稉濠佺閺夛繝浜剧捄顖欎繆閹拷 娴犲骸缍嬮崜宥変壕鐠侯垰鍨归幒锟�
                                if (result[4] != -1) {
                                    road.car_priority_queue.poll();
                                    road.road_car_sort.get(result[4] - 1).poll();
                                    //鏉烇箑鐡欓幒銉ユ躬閸氬酣娼�
                                    current_diaodu.location[0] = next_road.start_id;
                                    current_diaodu.location[1] = result[1];
                                    current_diaodu.location[2] = result[2];
                                    current_diaodu.location[3] = next_road.end_id;
                                    current_diaodu.index++;
                                    //娣囶喗鏁奸柆鎾圭熅娣団剝浼�    鐏忓棜婧呴崝鐘插弳閸掍即浜剧捄顖欒厬
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
                            //閹靛墽娲跨悰宀勪壕
                            // 閸掋倖鏌囬張澶屾纯鐞涘苯鍏遍幍锟�    閹垫儳鍩岄惄纾嬵攽闁拷
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

                            // get_road_car_sort(load_map_id, road_straight_right);   //閻╃顢戠�瑰绮柆锟� 鏉烇附甯撴惔锟�

                            //Car car2 = road_straight_right.road_all_car_sort.get(0);
                            if (road_stright_ganrao.direct == 1) {
                                break;     //閹广垼鐨熸惔锔芥綀
                            }
                            //閹垫儳涔忔潪顒勪壕
                            // 閸掋倖鏌囬張澶婁箯鏉烆剙鍏遍幍锟�    閹垫儳鍩屽锕佹祮闁拷
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
                            //get_road_car_sort(load_map_id, road_left_right);  //鐎电懓涔忛幏鎰笓鎼达拷

                            //Car car3 = road_left_right.road_all_car_sort.get(0);


                            if (car_left_ganrao.direct == 2) {
                                break;    //娴溿倖宕茬拫鍐ㄥ閺夛拷
                            } else {
                                //   閺囧瓨鏌婇崣瀹犳祮娴ｅ秶鐤�
                                //
                                int result[] = Location_fresh(current_diaodu, next_road, load_map_id, path_map_nod);
                                //   濞ｈ濮為崚鎵儑娑擄拷鏉烇箓浜�   娴犮儴鍤滃杈╂畱闁喎瀹�
                                //娣囶喗鏁兼稉濠佺閺夛繝浜剧捄顖欎繆閹拷 娴犲骸缍嬮崜宥変壕鐠侯垰鍨归幒锟�
                                if (result[4] != -1) {
                                    road.car_priority_queue.poll();
                                    road.road_car_sort.get(result[4] - 1).poll();
                                    //鏉烇箑鐡欓幒銉ユ躬閸氬酣娼�
                                    current_diaodu.location[0] = next_road.start_id;
                                    current_diaodu.location[1] = result[1];
                                    current_diaodu.location[2] = result[2];
                                    current_diaodu.location[3] = next_road.end_id;
                                    current_diaodu.index++;
                                    //娣囶喗鏁奸柆鎾圭熅娣団剝浼�    鐏忓棜婧呴崝鐘插弳閸掍即浜剧捄顖欒厬
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
//                                //娣囶喗鏁奸柆鎾圭熅娣団剝浼�    鐏忓棜婧呴崝鐘插弳閸掍即浜剧捄顖欒厬
//                                next_road.car_priority_queue.add(current_diaodu);
//                                next_road.road_car_sort.get(result[4]-1).add(current_diaodu);
////                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_message[car.location[1] ][car.location[2]] = 1;
////                                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_map.put(((car.location[1] - 1) + "" + car.location[2]), car);
////                                    get_road_car_sort(load_map_id, load_map_id.get(car.location[0]+"+"+car.location[3]));
////                                    //閸欘垰鍤捄顖氬經鏉烇箓娉﹂崥鍫熸纯閺傦拷
////                                    cross_road_car_list_now.remove(car);
                        }

                    }
                }

                //闁捁鐭剧拫鍐ㄥ
                //濡ょ厧褰涚粭顑跨閺夛繝浜剧捄锟�  娑撳秴褰查崙楦跨熅閸欙綀婧呴梿鍡楁値
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
                //閸氬酣娼伴弮鍫曟？閻楀洦娣虫潪锟�
            }
            //閼板啳妾婚崚婵嗩潗濞叉崘婧呴柌鍥╂暏闂呭繑婧�閼哄倻鍋�    閸愬秷锟藉啳妾婚柅澶婂絿鏉烇箑鐨惃鍕壕鐠侯垰褰傛潪锟�   娑擃亝鏆熸稉娲閺堝搫寮弫锟�  閸欘垵锟藉啳妾籲um闂呭繑妞傞梻鏉戝З閹礁褰夌亸锟�
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
                                ////閸掋倖鏌囪ぐ鎾冲鏉烇箑婀ぐ鎾冲闁捁鐭鹃弰顖氭儊閹枫儱鐗敍灞筋洤閺嬫粈绗夐幏銉ョ壄閿涘苯鍨ú鎹愭簠
                                int[] the_best_path = path_map.get(car.start_cross_id + "+" + car.end_cross_id);
                                int[] the_best_path_nod=path_map_nod.get(car.start_cross_id + "+" + car.end_cross_id);
                                car.the_best_path=the_best_path;
                                car.the_best_path_nod=the_best_path_nod;
                                if (car.start_time <=time_count) {
//
                                    ////閸掋倖鏌囪ぐ鎾冲鏉烇箑婀ぐ鎾冲闁捁鐭鹃弰顖氭儊閹枫儱鐗敍灞筋洤閺嬫粈绗夐幏銉ョ壄閿涘苯鍨ú鎹愭簠
                                    if (judge_Current_Cogestion(load_map_id, car, the_best_path_nod,load_list)) {
                                        //娴犲孩娣虫潪顩檈t娑擃厼鍨归梽銈堟簠鏉╂瑤閲滅�电钖勯妴锟�
                                        System.out.print(cross.car_set.size() + "chedesjp");
                                        cross.car_set.poll();
                                        System.out.print(cross.car_set.size() + "chedesjp");
                                        // cross.car_set_arr.remove(k);
                                        //濞叉崘婧呯亸鍙樻叏閺�纭呮簠閻ㄥ嫪淇婇幁锟�   location0:鏉烇缚缍呮禍搴℃憿娑擃亪浜剧捄锟� location1閿涳拷 location1閿涙俺婧呮担宥勭艾閸濐亙閲滄潪锕備壕 location2:鏉烇箑鍩岀捄顖氬經閻ㄥ嫯绐涚粋锟�
                                        //閼惧嘲褰囨潪锕佺窢鐠嬪啫瀹虫穱鈩冧紖
                                        int[] car_adjust_message = car_adjust(load_map_id, path_map_nod, car);
                                        if(car_adjust_message[4]!=-1){

                                            car.location[0] = car_adjust_message[0];
                                            car.location[1] = car_adjust_message[1];
                                            car.location[2] = car_adjust_message[2];
                                            car.location[3] = car_adjust_message[3];
                                            car.index++;
                                            car.real_start_time = time_count;
                                           
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

                //閸掋倖鏌囬弰顖氭儊鏉烇箑鐡欓柈钘夊嚒濞叉儳鍤�

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
//        System.out.println("閺傚洣娆㈡潏鎾冲毉");
//        //閺傚洣娆㈡潏鎾冲毉
//          print_txt(car_list);
//        System.out.println("閺傚洣娆㈡潏鎾冲毉");

            }
        System.out.println(time_count + "sjp");
        }


    private static void print_txt(HashSet<Car> car_list) {
        File answer_txt=new File("d:\\answer.txt");    //閺傛壆鏁撻弬鍥︽鐠侯垰绶�
        if(answer_txt.exists()){         //婵″倹鐏夐弬鎵晸閺傚洣娆㈢�涙ê婀敍灞炬煀閻㈢喐鏋冩禒鍫曞櫢閸涜棄鎮�
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
                    bw.write(string);   //閸氭垶鏌婇悽鐔告瀮娴犺泛鍟撻崗銉攽閺佺増宓�
                    bw.newLine();     //閹广垼顢�
                }

         bw.flush();
         bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private static void sort_road(Map<String, Road> load_map_id,Road road1) {
//        //鐏忓棜鐭鹃惃鍒o limian car鏉╂稖顢戦幒鎺戠碍
//
//        ArrayList<Car> channel_sort = new ArrayList();
//        road1.road_car_sort=new ArrayList<ArrayList<Car>>();
//        int count=0;
//        //閹垫儳鍩岀捄顖欑瑐閻ㄥ嫯婧�    楠炶泛顦╅悶锟�
//        for (int i1 = 0; i1 < road1.num_channel; i1++) {
//            for (int j1 = 0; j1 < road1.road_length; j1++)
//            {
//                if (road1.road_message[i1][j1] == 1) {
//                    //閺嶈宓侀柆鎾圭熅娴ｅ秶鐤嗛懢宄板絿閸掔増顒濇担宥囩枂閻ㄥ嫯婧�
//                    Car car = road1.road_car_map.get((i1 + "+" + j1));
//                    //閹垫儳鍩岃ぐ鎾冲鏉烇拷 閹碉拷閸︺劏婧呴柆鎾舵畱閹碉拷閺堝婧呴敍鍫熷瘻j閹烘帒銈芥惔蹇庣啊閿涳拷
//                    channel_sort.add(car);
//                    ArrayList cars_list = channel_sort;
//                    load_map_id.get(car.location[0]+"+"+car.location[3]).road_car_sort.add(cars_list);
//
//                  /*  if (car == cars_list.get(0)) {   //閹垫儳鍩岄幒鎺戙偨鎼村繒娈戠粭顑跨娑擃亣婧� 濞屸剝婀侀崜宥堟簠
//
//                    } else {     //car_list閸氬酣娼伴惃鍕簠閿涘矁顩﹂懓鍐閸撳秷婧呴柅鐔峰鐎电懓鎮楁潪锔炬畱瑜板崬鎼�
//
//                    }*/
//                }
//            }
//        }
//
//    }
    private static boolean judge_Current_Cogestion_last(Map<String, Road> load_map_id, Car car) {
		//鐏忓棗缍嬮崜宥堟簠閹碉拷閸︺劏婧呴柆鎾蹭繆閹垰鍨介弬锟�
		//婵″倹鐏夎ぐ鎾冲闁捁鐭炬潪锕佺窢鏉堟儳鍩岀�瑰湱鎾奸柌蹇曟畱  1/3 鐏忓崬鍨介弬顓濊礋閹枫儱鐗璯najue
		if((load_map_id.get(car.location[0]).car_state.size())/(load_map_id.get(car.location[0]).capcity)>1/4){
			return  false;
		}else
			return true;
	}
	//鏉烇箒绶犳潻娑樺弳闁捁鐭鹃弮鎯扮殶鎼达拷
	private static int[] car_adjust(Map<String,Road>load_map_id,HashMap<String ,int []> path_map_nod,Car car) {
		//瑜版挸澧犳潪锔炬畱閺堬拷娴兼鐭惧锟�
		int[] path_array= path_map_nod.get(car.start_cross_id+"+"+car.end_cross_id);
		Road temroad=load_map_id.get(path_array[0]+"+"+path_array[1]);
		int num_channel=temroad.num_channel;
		int road_length=temroad.road_length;
		int[][] road_message=temroad.road_message;
		int []result=new int[5];
		int []i_j=new int[2];   //閺堬拷閸氬簼绔存稉顏囨簠閻ㄥ嫪缍呯純锟�

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
//			if(speed>temroad.road_length-last_car.location[2]&&last_car.location[2]==temroad.road_length-1)//閺堬拷閸氬簼绔存潏鍡氭簠閺勵垯璐熼張顐ゎ伂
//			{//
//				car.location[0]=temroad.start_id;
//				car.location[1]=last_car.location[1]+1;
//				car.location[2]=road_length-speed;
//				car.location[3]=temroad.end_id;
//			}//閸掋倖鏌囬張锟介崥搴濈鏉堝棜婧呴弰顖氭儊瑜板崬鎼烽崥搴ゆ簠闁喎瀹�
//			if(speed>temroad.road_length-last_car.location[2]&&last_car.location[2]!=temroad.road_length-1)//閺堬拷閸氬簼绔存潏鍡氭簠閸氾缚璐熼張顐ゎ伂
//			{
//                car.location[0]=temroad.start_id;
//				car.location[1]=last_car.location[1] ;
//				car.location[2]=last_car.location[2]+1;
//                car.location[3]=temroad.end_id;
//			}
//			if(speed<temroad.road_length-last_car.location[2])//閺堬拷閸氬簼绔存潏鍡氭簠閸氾缚璐熼張顐ゎ伂
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
	//閸掋倖鏌囨潪锕佺窢閸戝搫褰傞崜宥変壕鐠侯垱瀚㈤崼鍨剰閸愶拷
	private static boolean judge_Current_Cogestion( Map<String,Road>load_map_id,Car car,int[]the_best_path_nod,HashSet<Road> load_list) {
		//鐏忓棗缍嬮崜宥堟簠閹碉拷閸︺劏婧呴柆鎾蹭繆閹垰鍨介弬锟�
		//婵″倹鐏夎ぐ鎾冲闁捁鐭炬潪锕佺窢鏉堟儳鍩岀�瑰湱鎾奸柌蹇曟畱  1/2 鐏忓崬鍨介弬顓濊礋閹枫儱鐗�
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
          //鐎电鐭鹃柌宀勬桨閹碉拷閺堝婧呴幒鎺戠碍
//    public static void get_road_car_sort(Map<String, Road> load_map_id,Road road1){
//        ArrayList[] channel_sort = new ArrayList[road1.num_channel];
//    road1.road_all_car_sort=new ArrayList<Car>();
//    //閹垫儳鍩岀捄顖欑瑐閻ㄥ嫯婧�    楠炶泛顦╅悶锟�
//    for (int j1 = 0; j1 < road1.road_length; j1++)  {
//        for (int i1 = 0; i1 < road1.num_channel; i1++)
//        {
//            if (road1.road_message[i1][j1] == 1) {
//                //閺嶈宓侀柆鎾圭熅娴ｅ秶鐤嗛懢宄板絿閸掔増顒濇担宥囩枂閻ㄥ嫯婧�
//                Car car = road1.road_car_map.get((i1 + "+" + j1));
//                //閹垫儳鍩岃ぐ鎾冲鏉烇拷 閹碉拷閸︺劏婧呴柆鎾舵畱閹碉拷閺堝婧呴敍鍫熷瘻j閹烘帒銈芥惔蹇庣啊閿涳拷
//                load_map_id.get(car.location[0]+"+"+car.location[3]).road_all_car_sort.add(car);
//            }
//            }
//        }
//    }
//          //鏉╂柨娲栭惄纾嬵攽闁捁鐭�
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

          //鏉╂柨娲栧锕佹祮闁捁鐭�
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
        //鏉╂稖顢戞潪锕傦拷鐔峰灲閺傦拷
        int speed1;
        int speed2;
        int s1;
        int s2;
        speed1 = Math.min(load_map_id.get(car.location[0]+"+"+car.location[3]).Limit_speed, car.Max_speed);
        //閹垫儳鍩岄崙楦跨熅閸欙絿娈戞潪锟�
        if (speed1 > car.location[2]) {
            int[] the_best_path = path_map_nod.get(car.start_cross_id + "+" + car.end_cross_id);
            if (car.index + 1 == the_best_path.length-1) {
                //kai ru cheku
                car.end_flag=1;

                //cross_road1_car_list.add(car);
            }
            else{
                Road next_road = load_map_id.get(the_best_path[car.index] + "+" + the_best_path[car.index + 1]);
                speed2 = Math.min(next_road.Limit_speed, car.Max_speed);
                s1 = car.location[2];
                if (speed2 - s1 > 0 && s1 < speed2) {     //閸欘垰鍤捄顖氬經閺夆�叉2閸滃本娼禒鏈电瑏
                    //cross_road1_car_list.add(car);
                    result=car;
                }
//                else {                 //鏉烇附顒滄總钘夊煂鐠侯垰褰�
//                    car.location[2] = 0;
//                }
            }
        }
    return  result;
    }
    // 閸︺劍鐦″▎陇绻冪捄顖氬經閺冭埖娲块弬棰佺秴缂冾噯绱濇稉鏃囩箲閸ョ偞娲块弬鏉挎躬閸濐亝娼柆鎾茬瑐閿涙稑顩ч弸婊冩躬-1闁挷绗傞敍灞藉灟娴溿倕鍤拫鍐ㄥ閺夛拷
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
        //閼汇儰绗呮稉锟介弶陇鐭鹃崼鍨劥  鐎佃壈鍤ч崣顖氬毉鐠侯垰褰涙潪锔跨瑝閼充粙锟芥俺顢戦敍灞藉灟鏉╂柨娲�-1閿涘本顒濋弮鏈垫唉閹广垼鐨熸惔锔芥綀閿涳拷
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
