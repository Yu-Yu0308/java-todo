import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class todo {
    public static void main(String[] args) {

        
        final String    CSV_FILE_PATH       =   "todoList.csv";    //ファイルパス
        final String    COMMA               =   ",";
        final String    COMPLETE            =   "[完]";
        final String    NOT_COMPLETE        =   "[未]";
        final String    NOT_COMPLETE_SIGNAL =   "0";
        final String[]  ACTION_SIGNAL       =   {"Delete", "Add", "Edit", "Show"};
        final String[]  SHOW_SIGNAL         =   {"All", "Filter", "Sort", "FilterSort"};
        final String[]  FILTERING_SIGNAL    =   {"Comp", "NComp", "Category", "Priority"};
        final String[]  SORT_SIGNAL         =   {"Priority", "Time", "Status"};     

        int deleteNum = Integer.parseInt(args[0]); 

        List<String> todoLists = new ArrayList<>(getListFromFile(CSV_FILE_PATH));      //ファイルから読み込んだtodoを入れるためのリストの用意

        System.out.println("予定一覧(削除前)");
        int num;
        for(int i = 0; i < todoLists.size(); i++){

            String todoRecode = todoLists.get(i);                       //todoとtodoの状態を取得
            String[] forSpritRecode = todoRecode.split(COMMA);          //todoと状態を分割

            String todo = forSpritRecode[0];                            //todoを取得
            String todoStatus = forSpritRecode[1];                      //todoの状態を取得

            //タスクが「完了」か「未完了」かの判断
            if(todoStatus.equals(NOT_COMPLETE_SIGNAL)){
                todoStatus = COMPLETE;
            }else{
                todoStatus = NOT_COMPLETE;
            }

            num = i + 1;

            System.out.println(todoStatus + "   " + num + "：" + todo);
        }

        todoLists.remove(deleteNum-1);                                 //リストから任意のタスクを削除

        //ファイルの上書き
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH, StandardCharsets.UTF_8));
            for(String list : todoLists){
                bw.write(list);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("予定一覧(削除後)");
        num = 0;
        for(int i = 0; i < todoLists.size(); i++){

            String todoRecode = todoLists.get(i);                       //todoとtodoの状態を取得
            String[] forSpritRecode = todoRecode.split(COMMA);          //todoと状態を分割

            String todo = forSpritRecode[0];                            //todoを取得
            String todoStatus = forSpritRecode[1];                      //todoの状態を取得

            //タスクが「完了」か「未完了」かの判断
            if(todoStatus.equals(NOT_COMPLETE_SIGNAL)){
                todoStatus = COMPLETE;
            }else{
                todoStatus = NOT_COMPLETE;
            }

            num = i + 1;

            System.out.println(todoStatus + "   " + num + "：" + todo);
        }

    }

    static List<String> getListFromFile(String CSV_FILE_PATH){

        List<String> Lists = new ArrayList<>();
        try{
            //ファイルの読み込み（UTF-8エンコーディングを明示的に指定）
            BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH, StandardCharsets.UTF_8));
            
            //ファイルの内容をリストに格納
            String recode = br.readLine();
            while(recode != null){

                // BOM（Byte Order Mark）を除去
                if (recode.startsWith("\uFEFF")) {
                    recode = recode.substring(1);
                }

                Lists.add(recode);
                recode = br.readLine();
            }

            br.close();
        }catch( IOException e ){
            System.out.println(e);
        }

        return Lists;
    }
}
