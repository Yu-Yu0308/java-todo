import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class TodoTest {
    
    private static final String TEST_CSV_FILE = "testTodoList.csv";
    private static final String ORIGINAL_CSV_FILE = "todoList.csv";
    
    @BeforeEach
    void setUp() {
        // テスト用のCSVファイルを作成
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(TEST_CSV_FILE));
            bw.write("1,テストTodo1,0");
            bw.newLine();
            bw.write("2,テストTodo2,1");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            fail("テストファイルの作成に失敗しました: " + e.getMessage());
        }
    }
    
    @AfterEach
    void tearDown() {
        // テスト用ファイルを削除
        File testFile = new File(TEST_CSV_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    void testGetListFromFile() {
        // ファイルからリストを読み込むテスト
        List<String> result = Todo.getListFromFile();
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }
    
    @Test
    void testAddTodo() {
        // Todo追加のテスト
        List<String> originalList = new ArrayList<>();
        originalList.add("1,テストTodo1,0");
        
        Operate.add(originalList, "新しいTodo", "0");
        
        assertEquals(2, originalList.size());
        assertTrue(originalList.get(1).contains("新しいTodo"));
    }
    
    @Test
    void testDeleteTodo() {
        // Todo削除のテスト
        List<String> testList = new ArrayList<>();
        testList.add("1,テストTodo1,0");
        testList.add("2,テストTodo2,1");
        
        Operate.delete(testList, 1);
        
        assertEquals(1, testList.size());
        assertTrue(testList.get(0).contains("テストTodo2"));
    }
    
    @Test
    void testEditTodo() {
        // Todo編集のテスト
        List<String> testList = new ArrayList<>();
        testList.add("1,テストTodo1,0");
        
        Operate.edit(testList, 1, "編集されたTodo", "1");
        
        assertTrue(testList.get(0).contains("編集されたTodo"));
        assertTrue(testList.get(0).contains("1"));
    }
}
