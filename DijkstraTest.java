package main.java.com.huawei;
import java.util.HashMap;
import java.util.Map;
public class DijkstraTest {


       public HashMap<String, int[]>  DijkstraTest(int[][] weight, String[] str , Map<String, Road> load_map) {
             HashMap <String,int[]>path_map=new HashMap<>();

           for(int i=0;i<weight.length;i++){
               for(int j=0;j<weight[0].length;j++){
                   if(weight[i][j]==Integer.MAX_VALUE){
                       weight[i][j]=-1;}
                   System.out.print(weight[i][j]+" ");
               }
               System.out.println();
           }
           
           int len = str.length;
           for (int i = 0; i < str.length; i++) {

               Dijkstra dijkstra = new Dijkstra(len);
             HashMap map= dijkstra.dijkstra(weight, str, i); 
             for( int i1 = 0;i1<map.size();i1++){
                 String string_path= map.get(i1).toString();
         
                String [] string_path_array=string_path.split(" ");
                 int [] int_path_array=new int[string_path_array.length];
                 int [] int_path_road_array=new int[int_path_array.length-1];
                for(int i2=0;i2<string_path_array.length;i2++){
                    int_path_array[i2]= Integer.parseInt(string_path_array[i2]);

             
              
                }
             
           
                if(int_path_array.length==1||int_path_array[0]==int_path_array[1]){

                    path_map.put((i + 1) + "+" + (i1 + 1), int_path_array) ;
                }

         
               else {
                     for (int i2 = 0; i2 < int_path_road_array.length; i2++) {
                         int_path_road_array[i2] = cross_to_path(int_path_array[i2], int_path_array[i2 + 1], load_map);
                     }

                     path_map.put((i + 1) + "+" + (i1 + 1), int_path_road_array);
                 }
             }

           }

          for(int i=0;i<path_map.get(1+"+"+8).length;i++) {
              System.out.print(path_map.get(1+"+"+8)[i]+" ");
          }
           System.out.println();

           return path_map;

       }

    private int cross_to_path(int i, int i1,Map<String, Road> load_map) {
           int road_id=load_map.get(i+"+"+i1).id;
           return road_id;
    }

}
