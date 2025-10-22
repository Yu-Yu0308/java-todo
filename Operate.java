import java.util.List;


public class Operate {

    static void add(List<String> lists, String task, String taskStatus){
        if( taskStatus.equals("0") ){
            System.out.println("「" + task + " ( 状態： 未完了 ） 」が追加されました。");
        }else if( taskStatus.equals("1")){
            System.out.println("「" + task + " ( 状態： 完了 ） 」が追加されました。");
        }
        lists.add(task + "," + taskStatus);
        Todo.overwriting(lists);
    }

    static void delete(List<String> lists, int num){
        lists.remove(num-1);        //リストから任意のタスクを削除
    }


}
