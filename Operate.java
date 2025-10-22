import java.util.List;
import java.util.ArrayList;


public class Operate {

    static void add(List<String> lists, String todo, String todoStatus){

        if( todoStatus.equals("0") ){

            System.out.println("「" + todo + " ( 状態： 未完了 ） 」が追加されました。");

        }else if( todoStatus.equals("1")){

            System.out.println("「" + todo + " ( 状態： 完了 ） 」が追加されました。");

        }

        lists.add(lists.size() + 1 + "," + todo + "," + todoStatus);
        Todo.overwriting(lists);

    }

    static void delete(List<String> lists, int num){

        //削除したいTodoのレコードを取得 & 要素ごとに分割
        String   todoRecode       =   lists.get( num-1 );                                     
        String[] forSpritRecode   =   todoRecode.split(Todo.COMMA);
        String   todoNum          =   forSpritRecode[Todo.ELE_NUM_OF_TODO_NUM];
        String   todo             =   forSpritRecode[Todo.ELE_NUM_OF_TODO_CONTENT];           
        String   todoStatus       =   forSpritRecode[Todo.ELE_NUM_OF_TODO_STATE];

        List<String> newLists = new ArrayList<>();
        
        
        if(todoStatus.equals(Todo.NOT_COMPLETE_SIGNAL)){
            todoStatus = Todo.NOT_COMPLETE;
        }else{
            todoStatus = Todo.COMPLETE;
        }

        //リストから任意のTodoを削除
        System.out.println("「" + todoStatus + "   " + todoNum + "：" + todo + "」を削除しました。");
        lists.remove(num-1);        

        //Todoの番号振り直し
        for(int i = 0; i < lists.size(); i++){
            String   newTodoRecode       =   lists.get( i );                                     
            String[] forSpritNewRecode   =   newTodoRecode.split(Todo.COMMA);
            String   newTodo             =   forSpritNewRecode[Todo.ELE_NUM_OF_TODO_CONTENT];           
            String   todoNewStatus       =   forSpritNewRecode[Todo.ELE_NUM_OF_TODO_STATE];


            newLists.add(i + 1 + "," + newTodo + "," + todoNewStatus);
        }

        Todo.overwriting(newLists);
    }


}
