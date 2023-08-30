package algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class BJ_12869_뮤탈리스크_신영은 {
	static Integer[] scv;
	static int N;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		scv = new Integer[N];
		for(int i = 0; i < N; i++) {
			scv[i] = sc.nextInt();
		}
		int[] score = new int[] {9, 3, 1};
		int cnt = 0;	
		
		while(true) {
			boolean flag = false;
			for(int i = 0; i < N; i++) {
				System.out.print(scv[i] + " ");
				if(scv[i] > 0) {
					flag = true;
				}
			}
			System.out.println();
			if(!flag) { // 모든 scv의 체력이 다 나갔음
				break;
			}
			cnt++;
			Arrays.sort(scv,Collections.reverseOrder());
			for(int i = 0; i < N; i++) {
				scv[i]-=score[i];
			}
		}
		
		System.out.println(cnt);
	}

}
