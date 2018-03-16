import java.util.ArrayList;
import java.util.Arrays;

public class Testing {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> arrayList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        int[] col = {0, 5, 10};
        int[] row = {0, 3, 6, 9, 12};
        int[][] array = {{25, 25, 25, 25, 25, 255, 255, 255, 255, 255, 485, 485, 485, 485, 485}, {0, 0, 0, 123, 123, 123, 246, 246, 246, 369, 369, 369, 492, 492, 492}};
        for (int i = 0; i < 14; i++){
            x.add(i);
            y.add(i);
        }
        arrayList.add(x);
        arrayList.add(y);
        int[][] gameGrid = new int[5][3];
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 3; i++) {
                System.out.println(i + ", " + j);
                gameGrid[j][i] = 5;
                for (int k = 0; k < 5; k++) {
                    System.out.println(Arrays.toString(gameGrid[k]));
                }
                System.out.println("Done");
            }
        }
        for (int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(gameGrid[i]));
        }
    }
}
