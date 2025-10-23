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

    private static final String CSV_FILE_PATH = "todoList.csv";    // ファイルパス

    public static final String COMPLETE = "[完]";
    public static final String NOT_COMPLETE = "[未]";
    public static final String NOT_COMPLETE_SIGNAL = "0";
    public static final String COMMA = ",";
    public static final int ELE_NUM_OF_TODO_NUM = 0;
    public static final int ELE_NUM_OF_TODO_CONTENT = 1;
    public static final int ELE_NUM_OF_TODO_STATE = 2;
    public static final String ACTION_CANCEL_SIGNAL = "0";

    public static void main(String[] args) {

        Boolean actionFlag = false;
        Boolean selectStatusFlag = false;
        Boolean selectDeleteOptionFlag = false;
        Boolean selectEditOptionFlag = false;

        Scanner sc = new Scanner(System.in);
        List<String> todoLists = new ArrayList<>(getListFromFile());      // ファイルから読み込んだtodoを入れるためのリストの用意

        do {
            System.out.println("予定一覧");
            show();
            System.out.println("アクションを1~4で選択してください。");
            System.out.println("1: Add  2: Delete   3: Edit   4: Finish");
            
            String action = sc.nextLine();
            actionFlag = false;

            switch (action) {

                case Action.ADD:

                    System.out.println("「1: ADD」が選択されました。" + "\n" + "Todo名を入力してください。アクションをキャンセルしたい場合は「0」を入力してください。");
                    String addTodo = sc.nextLine();

                    if (addTodo.equals(ACTION_CANCEL_SIGNAL)) {
                        System.out.println("アクションがキャンセルされました。次のアクションを選択してください。\n");
                        break;
                    }
                    
                    selectStatusFlag = false;
                    String todoStatus = "";

                    todoLists = new ArrayList<>(getListFromFile());
                    
                    // 正しい入力がされるまで以下をループ
                    while (!selectStatusFlag) {

                        System.out.println("状態を「0（未完了)」または「1（完了）」で入力してください。");
                        todoStatus = sc.nextLine();

                        if (todoStatus.equals(Status.COMPLETE) || todoStatus.equals(Status.NOT_COMPLETE)) {
                            selectStatusFlag = true;
                        }
                    }
    
                    Operate.add(todoLists, addTodo, todoStatus);
                    show();  // 追加後の一覧を表示

                    break;
    
                case Action.DELETE:

                    selectDeleteOptionFlag = false;

                    todoLists = new ArrayList<>(getListFromFile());

                    // 削除できるTodoが入力されるまでループ
                    while (!selectDeleteOptionFlag) {

                        // 消したいTodoの選択
                        System.out.println("\n「2: DELETE」が選択されました。\n 消したいTodoの番号を選択してください。\n アクションをキャンセル場合は「0」を入力してください。\n");
                        show();
                        String deleteTodoNum = sc.nextLine();                   

                        // 削除したいTodoが存在するかの確認
                        for (int i = 0; i < todoLists.size(); i++) {
                            String todo = todoLists.get(i);
                            String[] forSpritRecode = todo.split(COMMA);
                            String todoEleNum = forSpritRecode[ELE_NUM_OF_TODO_NUM];
    
                            if (deleteTodoNum.equals(todoEleNum)) {
                                Operate.delete(todoLists, Integer.parseInt(deleteTodoNum));
                                selectDeleteOptionFlag = true;
                                break;
                            } else if (deleteTodoNum.equals(ACTION_CANCEL_SIGNAL)) {
                                System.out.println("アクションがキャンセルされました。次のアクションを選択してください。\n");
                                selectDeleteOptionFlag = true;
                                break; 
                            }
                        }
                    } 
                    break;
                
                case Action.EDIT:

                    selectEditOptionFlag = false;

                    todoLists = new ArrayList<>(getListFromFile());

                    // 編集できるTodoが入力されるまでループ
                    while (!selectEditOptionFlag) {

                        // 消したいTodoの選択
                        System.out.println("\n「 3: EDIT」が選択されました。\n 編集したいTodoを選択してください。\n アクションをキャンセル場合は「0」を入力してください。\n");
                        show();
                        String editTodoNum = sc.nextLine();

                        // 編集したいTodoが存在するかの確認
                        for (int i = 0; i < todoLists.size(); i++) {

                            String todo = todoLists.get(i);
                            String[] forSpritRecode = todo.split(COMMA);
                            String todoEleNum = forSpritRecode[ELE_NUM_OF_TODO_NUM];

                            if (editTodoNum.equals(todoEleNum)) {

                                System.out.println("新しいTodoに編集してください。\n");
                                String newTodo = sc.nextLine();

                                selectStatusFlag = false;

                                String newTodoStatus = "";

                                while (!selectStatusFlag) {

                                    System.out.println("編集したTodoの状態を「0（未完了)」または「1（完了）」で入力してください。\n");
                                    newTodoStatus = sc.nextLine();
            
                                    if (newTodoStatus.equals(Status.COMPLETE) || newTodoStatus.equals(Status.NOT_COMPLETE)) {
                                        selectStatusFlag = true;
                                    }
                                }

                                Operate.edit(todoLists, Integer.parseInt(editTodoNum), newTodo, newTodoStatus);
                                selectEditOptionFlag = true;
                                break; // 編集後はループを抜ける

                            } else if (editTodoNum.equals(ACTION_CANCEL_SIGNAL)) {
                                System.out.println("アクションがキャンセルされました。次のアクションを選択してください。\n");
                                selectDeleteOptionFlag = true;
                                break;
                            }
                        }
                        break;
                    }
                    break;
                
                case Action.FINISH:
                    System.out.println("「4: FINISH」が選択されました。" + "\n" + "プログラムを終了します。");
                    System.out.println("\n");
                    actionFlag = true;
            }
        } while (!actionFlag);

        sc.close();
    }

    static List<String> getListFromFile() {

        List<String> Lists = new ArrayList<>();
        try {
            // ファイルの読み込み（UTF-8エンコーディングを明示的に指定）
            BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH, StandardCharsets.UTF_8));
            
            // ファイルの内容をリストに格納
            String recode = br.readLine();
            while (recode != null) {

                // BOM（Byte Order Mark）を除去
                if (recode.startsWith("\uFEFF")) {
                    recode = recode.substring(1);
                }

                Lists.add(recode);
                recode = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return Lists;
    }

    static void show() {

        List<String> todoLists = new ArrayList<>(getListFromFile());         // ファイルから読み込んだtodoを入れるためのリストの用意

        for (int i = 0; i < todoLists.size(); i++) {

            String todoRecode = todoLists.get(i);                            // todoとtodoの状態を取得
            String[] forSpritRecode = todoRecode.split(COMMA);              // todoと状態を分割

            String todoNum = forSpritRecode[ELE_NUM_OF_TODO_NUM];
            String todo = forSpritRecode[ELE_NUM_OF_TODO_CONTENT];          // todoを取得
            String todoStatus = forSpritRecode[ELE_NUM_OF_TODO_STATE];      // todoの状態を取得

            // Todoが「完了」か「未完了」かの判断
            if (todoStatus.equals(NOT_COMPLETE_SIGNAL)) {
                todoStatus = NOT_COMPLETE;
            } else {
                todoStatus = COMPLETE;
            }
            System.out.println(todoStatus + "   " + todoNum + "：" + todo);
        }
        System.out.println("\n");
    }

    static void overwriting(List<String> lists) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH, StandardCharsets.UTF_8));
            for (String list : lists) {
                bw.write(list);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void actionCancel(String cancelSignal) {
        if (cancelSignal.equals(ACTION_CANCEL_SIGNAL)) {
            System.out.println("アクションがキャンセルされました。次のアクションを選択してください。" + "\n");
        }
    }
}