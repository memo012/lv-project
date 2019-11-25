package com.adminsys;

import java.util.Scanner;

/**
 * @Author: qiang
 * @ClassName: TspSolution
 * @Description: TODO
 * @Date: 2019/11/24 下午7:49
 * @Version: 1.0
 **/
public class TspSolution {

    private static int cityNum;
    private static int[][] distance;
    private static int[] visited;
    private static int minDistance;
    private static int startx;

    public static void tsp(int start) {
        System.out.println("路线为：");
        System.out.print(start + "-->");
        for (int i = 1; i < cityNum; i++) {
            int len = Integer.MAX_VALUE;
            int temp = 0;
            for (int j = 1; j <= cityNum; j++) {
                if (visited[j] != 0) {
                    continue;
                }
                if(len > distance[start][j]){
                    len = distance[start][j];
                    temp = j;
                }
            }
            start = temp;
            System.out.print(start + "-->");
            visited[temp] = 1;
            minDistance += len;
        }
        // 最后加上最后一个城市到起点的距离
        minDistance += distance[start][startx];
        System.out.println(startx);
    }

    public static void main(String[] args) {
        System.out.print("请输入城市个数:");
        Scanner sc = new Scanner(System.in);
        cityNum = sc.nextInt();
        distance = new int[cityNum+1][cityNum+1];
        visited = new int[cityNum+1];
        for (int i = 1; i <= cityNum; i++) {
            for (int j = i + 1; j <= cityNum; j++) {
                System.out.print("请输入第" + i + "号城市到" + j + "号城市之间的距离:");
                distance[i][j] = sc.nextInt();
                distance[j][i] = distance[i][j];
            }
        }
        System.out.print("请输入您出发的城市是第几个城市：");
        startx = sc.nextInt();
        visited[startx] = 1;
        tsp(startx);
        System.out.println(minDistance);
    }

}
