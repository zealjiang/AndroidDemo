package com.example.libjava.algorithm;

/**
 * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
 * 示例 1：
 *
 *
 * 输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * 输出：4
 * 示例 2：
 *
 *
 * 输入：matrix = [["0","1"],["1","0"]]
 * 输出：1
 * 示例 3：
 *
 * 输入：matrix = [["0"]]
 * 输出：0
 */
public class MaximalSquare {
    public static void main(String[] args) {
        String[][] matrix = new String[][]{
                {"1","0","1","0","0"},
                {"1","0","1","1","1"},
                {"1","1","1","1","1"},
                {"1","0","0","1","0"}
        };
        maximalSquare2(matrix);
    }

    public static int maximalSquare(String[][] matrix) {
        int rowNum = matrix.length;
        if (rowNum == 0) return 0;
        int columnNum = matrix[0].length;
        if (columnNum == 0) return 1;
        int maxArea = 0;

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                System.out.println("check --> 行="+i+" 列="+j);
                if (matrix[i][j] == "1") {
                    maxArea = maxArea > 1 ? maxArea : 1;
                    int boundary = rowNum - i > columnNum - j ? columnNum - j : rowNum - i;
                    inner: for (int k = 0; k < boundary; k++) {
                        System.out.println("inner k ="+k);
                        for (int m = i; m <= i + k; m++) {
                            for (int n = j; n <= j + k; n++) {
                                if (matrix[m][n] == "0") {
                                    System.out.println("break inner m ="+m+" n ="+n);
                                    break inner;
                                }
                            }
                        }

                        int area = (k + 1) * (k + 1);
                        if (area > maxArea) {
                            maxArea = area;
                        }
                    }

                }
            }
        }

        System.out.println("maxArea ="+maxArea);
        return maxArea;
    }


    /**
     * 动态规划
     * 可以使用动态规划降低时间复杂度。我们用 dp(i,j)\textit{dp}(i, j)dp(i,j) 表示以 (i,j)(i, j)(i,j) 为右下角，且只包含 111 的正方形的边长最大值。如果我们能计算出所有 dp(i,j)\textit{dp}(i, j)dp(i,j) 的值，那么其中的最大值即为矩阵中只包含 111 的正方形的边长最大值，其平方即为最大正方形的面积。
     *
     * 那么如何计算 dp\textit{dp}dp 中的每个元素值呢？对于每个位置 (i,j)(i, j)(i,j)，检查在矩阵中该位置的值：
     *
     * 如果该位置的值是 000，则 dp(i,j)=0\textit{dp}(i, j) = 0dp(i,j)=0，因为当前位置不可能在由 111 组成的正方形中；
     *
     * 如果该位置的值是 111，则 dp(i,j)\textit{dp}(i, j)dp(i,j) 的值由其上方、左方和左上方的三个相邻位置的 dp\textit{dp}dp 值决定。具体而言，当前位置的元素值等于三个相邻位置的元素中的最小值加 111，状态转移方程如下：
     *
     * dp(i,j)=min(dp(i−1,j),dp(i−1,j−1),dp(i,j−1))+1dp(i, j)=min(dp(i−1, j), dp(i−1, j−1), dp(i, j−1))+1
     * dp(i,j)=min(dp(i−1,j),dp(i−1,j−1),dp(i,j−1))+1
     * 此外，还需要考虑边界条件。如果 iii 和 jjj 中至少有一个为 000，则以位置 (i,j)(i, j)(i,j) 为右下角的最大正方形的边长只能是 111，因此 dp(i,j)=1\textit{dp}(i, j) = 1dp(i,j)=1。
     *
     *
     * @param matrix
     * @return
     */
    public static int maximalSquare2(String[][] matrix) {
        int maxSide = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return maxSide;
        }
        int rows = matrix.length, columns = matrix[0].length;
        int[][] dp = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == "1") {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        int maxSquare = maxSide * maxSide;
        System.out.println("maxSquare ="+maxSquare);
        return maxSquare;
    }
}
