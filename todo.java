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

    private static final String    CSV_FILE_PATH              =   "todoList.csv";    //ファイルパス

    public static final String    COMPLETE                    =   "[完]";
    public static final String    NOT_COMPLETE                =   "[未]";
    public static final String    NOT_COMPLETE_SIGNAL         =   "0";
    public static final String    COMMA                       =   ",";
    public static final int       ELE_NUM_OF_TODO_NUM         =   0;
    public static final int       ELE_NUM_OF_TODO_CONTENT     =   1;
    public static final int       ELE_NUM_OF_TODO_STATE       =   2;

    public static void main(String[] args) {
        
        final String[]  ACTION_SIGNAL               =   {"Delete", "Add", "Edit", "Show"};
        final String[]  SHOW_SIGNAL                 =   {"All", "Filter", "Sort", "FilterSort"};
        final String[]  FILTERING_SIGNAL            =   {"Comp", "NComp", "Category", "Priority"};
        final String[]  SORT_SIGNAL                 =   {"Priority", "Time", "Status"};
        Boolean         actionFlag                  =   false;
        Boolean         selectStatusFlag            =   false;
        Boolean         selectDeleteOptionFlag      =   false;
           


        Scanner sc = new Scanner(System.in);
        List<String> todoLists = new ArrayList<>(getListFromFile());      //ファイルから読み込んだtodoを入れるためのリストの用意

        System.out.println("予定一覧");
        show();

        System.out.println("アクションを選択してください");
        System.out.println("1: Add  2: Delete   3: Edit    4: Show");
        String action = sc.nextLine();

        do{
            switch (action) {

                case Action.ADD:
                //操作をキャンセルするときの処理を後で追加
                    System.out.println("Todo名を入力してください。");
                    String addTodo = sc.nextLine();
                    
                    selectStatusFlag = false;
                    String todoStatus = "";
                    
                    while(!selectStatusFlag){

                        System.out.println("状態を「0（未完了)」または「1（完了）」で入力してください。");
                        todoStatus = sc.nextLine();

                        if(todoStatus.equals( Status.COMPLETE ) || todoStatus.equals( Status.NOT_COMPLETE ) ){

                            selectStatusFlag = true;

                        }

                    }
    
                    Operate.add(todoLists, addTodo, todoStatus);
                    show();  // 追加後の一覧を表示

                    actionFlag = true;
    
                    break;
    
                case Action.DELETE:

                    //消したいTodoがリスト上に存在するかの確認
                    selectDeleteOptionFlag = false;

                    while(!selectDeleteOptionFlag){

                        //消したいTodoの選択
                        System.out.println("消したいTodoの番号を選択してください。");
                        show();
                        String deleteTodoNum = sc.nextLine();

                        // 拡張for文の代わりに通常のfor文を使用
                        for (int i = 0; i < todoLists.size(); i++) {
                            String todo = todoLists.get(i);
                            String[] forSpritRecode = todo.split(COMMA);
                            String todoEleNum = forSpritRecode[ELE_NUM_OF_TODO_NUM];
    
                            if ( deleteTodoNum.equals(todoEleNum) ) {
    
                                Operate.delete(todoLists, Integer.parseInt(deleteTodoNum));
                                selectDeleteOptionFlag = true;
                                break; // 削除後はループを抜ける
    
                            }else if ( deleteTodoNum.equals("0")){
    
                                System.out.println("「削除」をキャンセルしました。");
                                selectDeleteOptionFlag = true;
                                //ここの機能を後で追加
                                break; // キャンセル時もループを抜ける
    
                            }
                        }

                    }
                    
                    show();  // 追加後の一覧を表示
                    actionFlag = true;
                    
                    break;
                
                case Action.EDIT:
                    actionFlag = true;
                    break;
                
                case Action.SHOW:
                    actionFlag = true;
                    break;
                
                default:
                    System.out.println("1~4で選択してください。");
                    System.out.println("1: Add  2: Delete   3: Edit    4: Show");
                    action = sc.nextLine();
                    actionFlag = false;
            }
        }while(!actionFlag);



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

    static void show(){

        List<String> todoLists = new ArrayList<>(getListFromFile());         //ファイルから読み込んだtodoを入れるためのリストの用意

        for(int i = 0; i < todoLists.size(); i++){

            String todoRecode = todoLists.get(i);                            //todoとtodoの状態を取得
            String[] forSpritRecode = todoRecode.split(COMMA);              //todoと状態を分割

            String todoNum = forSpritRecode[ELE_NUM_OF_TODO_NUM];
            String todo = forSpritRecode[ELE_NUM_OF_TODO_CONTENT];          //todoを取得
            String todoStatus = forSpritRecode[ELE_NUM_OF_TODO_STATE];      //todoの状態を取得

            //Todoが「完了」か「未完了」かの判断
            if(todoStatus.equals(NOT_COMPLETE_SIGNAL)){
                todoStatus = NOT_COMPLETE;
            }else{
                todoStatus = COMPLETE;
            }
            System.out.println(todoStatus + "   " + todoNum + "：" + todo);
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
