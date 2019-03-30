package main.java.com.huawei;

import java.util.HashMap;
import java.util.Map;






public class Dijkstra_nod_test {


        public HashMap<String, int[]> Dijkstra_nod_test(int[][] weight, String[] str , Map<String, Road> load_map) {
            HashMap <String,int[]>path_map=new HashMap<>();

            for(int i=0;i<weight.length;i++){
                for(int j=0;j<weight[0].length;j++){
                    if(weight[i][j]==Integer.MAX_VALUE){
                        weight[i][j]=-1;}
                 //   System.out.print(weight[i][j]+" ");
                }
              //  System.out.println();
            }
            // TODO Auto-generated method stub
       /* int[][] weight=
               { {0,  -1, 10, -1, 30, 100},
                {-1,  0,  5, -1, -1, -1},
                {-1, -1,  0, 50, -1, -1},
                {-1, -1, -1, 0,  -1, 10},
                {-1, -1, -1, 20,  0, 60},
                {-1, -1, -1, -1, -1, 0}};*/
            //   String[] str= {"V1","V2","V3","V4","V5","V6"};
            int len = str.length;

            //渚濇璁╁悇鐐瑰綋婧愮偣锛屽苟璋冪敤dijkstra鍑芥暟
            for (int i = 0; i < str.length; i++) {

                Dijkstra dijkstra = new Dijkstra(len);
                //杩斿洖涓�涓狧ashMap  hashmap璺緞
                HashMap map= dijkstra.dijkstra(weight, str, i);

                //  int [] int_path_array=new int[map.size()];
                for( int i1 = 0;i1<map.size();i1++){
                    String string_path= map.get(i1).toString();
                    //    System.out.print(string_path);
                    String [] string_path_array=string_path.split(" ");
                    int [] int_path_array=new int[string_path_array.length];

                    for(int i2=0;i2<string_path_array.length;i2++){
                        int_path_array[i2]= Integer.parseInt(string_path_array[i2]);


                    }


                    if(int_path_array.length==1||int_path_array[0]==int_path_array[1]){

                        path_map.put((i + 1) + "+" + (i1 + 1), int_path_array) ;
                    }


                    else {

                        path_map.put((i + 1) + "+" + (i1 + 1), int_path_array);
                    }
                }

            }

            //鎵撳嵃浠巑ap涓幏鍙杙ath_map
            for(int i=0;i<path_map.get(1+"+"+8).length;i++) {
                System.out.print(path_map.get(1+"+"+8)[i]+" ");
            }
            System.out.println();

            return path_map;

        }





}
