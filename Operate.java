import java.util.List;
import java.util.ArrayList;

public class Operate {

    static void add(List<String> lists, String todo, String todoStatus) {

        if (todoStatus.equals(Todo.NOT_COMPLETE_SIGNAL)) {
            System.out.println("「" + todo + " ( 状態： " + Todo.NOT_COMPLETE + "） 」が追加されました。");
            System.out.println("\n");
        } else if (todoStatus.equals("1")) {
            System.out.println("「" + todo + " ( 状態： " + Todo.COMPLETE + " ） 」が追加されました。");
            System.out.println("\n");
        }

        lists.add(lists.size() + 1 + "," + todo + "," + todoStatus);
        Todo.overwriting(lists);
    }

    static void delete(List<String> lists, int num) {

        // 削除したいTodoのレコードを取得 & 要素ごとに分割
        String todoRecode = lists.get(num - 1);                                     
        String[] forSpritRecode = todoRecode.split(Todo.COMMA);
        String todoNum = forSpritRecode[Todo.ELE_NUM_OF_TODO_NUM];
        String todo = forSpritRecode[Todo.ELE_NUM_OF_TODO_CONTENT];           
        String todoStatus = forSpritRecode[Todo.ELE_NUM_OF_TODO_STATE];

        List<String> newLists = new ArrayList<>();
        
        if (todoStatus.equals(Todo.NOT_COMPLETE_SIGNAL)) {
            todoStatus = Todo.NOT_COMPLETE;
        } else {
            todoStatus = Todo.COMPLETE;
        }

        // リストから任意のTodoを削除
        System.out.println("「" + todoStatus + "   " + todoNum + "：" + todo + "」を削除しました。");
        System.out.println("\n");
        lists.remove(num - 1);        

        // Todoの番号振り直し
        for (int i = 0; i < lists.size(); i++) {
            String newTodoRecode = lists.get(i);                                     
            String[] forSpritNewRecode = newTodoRecode.split(Todo.COMMA);
            String newTodo = forSpritNewRecode[Todo.ELE_NUM_OF_TODO_CONTENT];           
            String todoNewStatus = forSpritNewRecode[Todo.ELE_NUM_OF_TODO_STATE];

            newLists.add(i + 1 + "," + newTodo + "," + todoNewStatus);
        }

        Todo.overwriting(newLists);
    }

    // リスト、編集したいリストの要素番号、編集後の内容＆状態
    static void edit(List<String> lists, int num, String newTodo, String newTodoStatus) {

        // 編集したいTodoのレコードを取得 & 要素ごとに分割
        String todoRecode = lists.get(num - 1);                                     
        String[] forSpritRecode = todoRecode.split(Todo.COMMA);
        String todoNum = forSpritRecode[Todo.ELE_NUM_OF_TODO_NUM];

        // 新しいレコードを作成してリストを更新
        String newRecode = todoNum + Todo.COMMA + newTodo + Todo.COMMA + newTodoStatus;
        lists.set(num - 1, newRecode);

        // OverWritingでファイルを上書き
        Todo.overwriting(lists);

        // 編集完了メッセージを表示
        String displayStatus = newTodoStatus.equals(Todo.NOT_COMPLETE_SIGNAL) ? Todo.NOT_COMPLETE : Todo.COMPLETE;
        System.out.println("「" + displayStatus + "   " + todoNum + "：" + newTodo + "」に編集しました。");
        System.out.println("\n");
    }
}