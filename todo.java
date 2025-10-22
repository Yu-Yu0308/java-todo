import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Todo {

    private static final String    CSV_FILE_PATH       =   "todoList.csv";    //ファイルパス
    private static final String    COMMA               =   ",";
    private static final String    COMPLETE            =   "[完]";
    private static final String    NOT_COMPLETE        =   "[未]";
    private static final String    NOT_COMPLETE_SIGNAL =   "0";

    public static void main(String[] args) {
        
        final String[]  ACTION_SIGNAL       =   {"Delete", "Add", "Edit", "Show"};
        final String[]  SHOW_SIGNAL         =   {"All", "Filter", "Sort", "FilterSort"};
        final String[]  FILTERING_SIGNAL    =   {"Comp", "NComp", "Category", "Priority"};
        final String[]  SORT_SIGNAL         =   {"Priority", "Time", "Status"};
        Boolean         ACTION_FLAG         =   true;
        Boolean         SELECT_STATUS_FLAG  =   true;      


        Scanner sc = new Scanner(System.in);
        List<String> todoLists = new ArrayList<>(getListFromFile());      //ファイルから読み込んだtodoを入れるためのリストの用意

        System.out.println("予定一覧");
        show(todoLists);

        System.out.println("アクションを選択してください");
        System.out.println("1: Add  2: Delete   3: Edit    4: Show");
        String action = sc.nextLine();

        do{
            switch (action) {
                case Action.ADD:
                    System.out.println("タスク名を入力してください。");
                    String task = sc.nextLine();
                    
                    SELECT_STATUS_FLAG = false;
                    String taskStatus = "";
                    
                    while(!SELECT_STATUS_FLAG){
                        System.out.println("状態を「0（未完了)」または「1（完了）」で入力してください。");
                        taskStatus = sc.nextLine();
                        if(taskStatus.equals( Status.COMPLETE ) || taskStatus.equals( Status.NOT_COMPLETE ) ){
                            SELECT_STATUS_FLAG = true;
                        }
                    }
    
                    Operate.add(todoLists, task, taskStatus);
                    show(todoLists);  // 追加後の一覧を表示

                    ACTION_FLAG = true;
    
                    break;
    
                case Action.DELETE:
                    ACTION_FLAG = true;
                    break;
                
                case Action.EDIT:
                    ACTION_FLAG = true;
                    break;
                
                case Action.SHOW:
                    ACTION_FLAG = true;
                    break;
                
                default:
                    System.out.println("1~4で選択してください。");
                    System.out.println("1: Add  2: Delete   3: Edit    4: Show");
                    action = sc.nextLine();
                    ACTION_FLAG = false;
            }
        }while(!ACTION_FLAG);



        sc.close();

    }

    static List<String> getListFromFile(){

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

    static void show(List<String> lists){
        int num;
        for(int i = 0; i < lists.size(); i++){

            String todoRecode = lists.get(i);                       //todoとtodoの状態を取得
            String[] forSpritRecode = todoRecode.split(COMMA);          //todoと状態を分割

            String todo = forSpritRecode[0];                            //todoを取得
            String todoStatus = forSpritRecode[1];                      //todoの状態を取得

            //タスクが「完了」か「未完了」かの判断
            if(todoStatus.equals(NOT_COMPLETE_SIGNAL)){
                todoStatus = NOT_COMPLETE;
            }else{
                todoStatus = COMPLETE;
            }

            num = i + 1;

            System.out.println(todoStatus + "   " + num + "：" + todo);
        }
    }

    static void overwriting(List<String> lists){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH, StandardCharsets.UTF_8));
            for(String list : lists){
                bw.write(list);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
