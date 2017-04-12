package mqy.api.leetcode._463_Island_Perimeter;

import java.util.*;
/**
 * Created by maoqiyun on 2017/4/9.
 */
public class Solution {
    public int islandPerimeter(int[][] grid) {

        int len_b = grid.length; // 行数
        int len_a = grid[0].length; // 列数
        int perimeter = 0;

        if (len_b == 1 && len_a == 1) {
            return 4;
        }
        Map<Coordinate, Integer> initValue = initSpicificP(grid);

        for (int i = 0; i < len_b; i ++) {
            for (int j = 0; j < len_a; j++) {
                if (grid[i][j] == 1) {
                    perimeter = perimeter + initValue.get(new Coordinate(i,j));
                    if (i -1 >= 0 && grid[i-1][j] == 0) { // up
                        perimeter ++;
                    }
                    if (j -1 >= 0 && grid[i][j-1] == 0) { // left
                        perimeter ++;
                    }
                    if (j +1 < len_a && grid[i][j+1] == 0) { // right
                        perimeter ++;
                    }
                    if (i +1 < len_b && grid[i+1][j] == 0) { // right
                        perimeter ++;
                    }
                }
            }
        }
        return perimeter;
    }

    public Map<Coordinate, Integer> initSpicificP(int[][] grid) {
        int len_b = grid.length; // 行数
        int len_a = grid[0].length; // 列数
        Map<Coordinate, Integer> indexs_initvalue = new HashMap<>();
        if (len_a > 1 && len_b > 1) {
            for (int i = 0; i < len_b; i ++) {
                for (int j = 0; j < len_a; j ++) {
                    if ((i == 0 && j == 0)
                            || (i == 0 && j == len_a - 1)
                            || (i == len_b - 1 && j == 0)
                            || (i == len_b - 1 && j == len_a - 1)
                            ) {
                        Coordinate coordinate = new Coordinate(i,j);
                        indexs_initvalue.put(coordinate,2);
                    }
                    else if (
                            (i == 0 && j < len_a - 1 && j > 0)
                                    || (i < len_b - 1 && i > 0 && j == len_a - 1)
                                    || (i == len_b - 1 && j < len_a - 1 && j > 0)
                                    || (i < len_b - 1 && i > 0 && j == 0)
                            ) {
                        Coordinate coordinate = new Coordinate(i,j);
                        indexs_initvalue.put(coordinate,1);
                    }
                    else {
                        Coordinate coordinate = new Coordinate(i,j);
                        indexs_initvalue.put(coordinate, 0);
                    }
                }
            }

            
        }
        else if ( len_b == 1){

                for (int j = 0; j < len_a; j++) {
                    if ((j == 0) ||
                            (j == len_a - 1 )) {
                        Coordinate coordinate = new Coordinate(0,j);
                        indexs_initvalue.put(coordinate,3);
                    }
                    else if (j > 0 && j < len_a - 1) {
                        Coordinate coordinate = new Coordinate(0,j);
                        indexs_initvalue.put(coordinate,2);
                    }
                }

        }
        else if (len_a == 1) {
            for (int i = 0; i < len_b; i++) {
                if ((i == 0) ||
                        (i == len_b - 1 )) {
                    Coordinate coordinate = new Coordinate(i,0);
                    indexs_initvalue.put(coordinate,3);
                }
                else if (i > 0 && i < len_b - 1) {
                    Coordinate coordinate = new Coordinate(i,0);
                    indexs_initvalue.put(coordinate,2);
                }
            }
        }
        return indexs_initvalue;
    }

    class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinate)) return false;

            Coordinate that = (Coordinate) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
    public static void main(String[] args) {
//        int[][] input = {{1}};
        int[][] input = {{1,0}};
//        int[][] input = {{0,1,0,0},{1,1,1,0},{0,1,0,0},{1,1,0,0}};
        Solution solution = new Solution();
        int output = solution.islandPerimeter(input);
        System.out.print(output);
    }
}
