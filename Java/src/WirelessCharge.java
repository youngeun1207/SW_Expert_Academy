import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 5644. [모의 SW 역량테스트] 무선 충전
 * @author youngeun
 *
 * 22,392 kb
 * 126 ms
 *
 * 단순구현
 */
public class WirelessCharge {
    static int T, M, A, C, P, answer;
    static int[] pA, pB, coorA, coorB;
    static int[][][] maps;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= T; tc++) {
            answer = 0;
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            pA = new int[M];
            pB = new int[M];

            // 사람  이동 정보
            StringTokenizer stA = new StringTokenizer(br.readLine());
            StringTokenizer stB = new StringTokenizer(br.readLine());
            for(int m = 0; m < M; m++) {
                pA[m] = Integer.parseInt(stA.nextToken());
                pB[m] = Integer.parseInt(stB.nextToken());
            }

            maps = new int[A][11][11]; // 기기, 가로, 세로
            // 충전기 정보
            for(int a = 0; a < A; a++) {
                st = new StringTokenizer(br.readLine());
                int y = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());
                C = Integer.parseInt(st.nextToken());
                P = Integer.parseInt(st.nextToken());

                // |XA – XB| + |YA – YB| 안넘어가는 구간에 P 넣기
                // 삼각형
                for(int row = 0; row < C+1; row++) {
                    int newX = x-C+row;
                    for(int col = y-row; col <= y+row; col++) {
                        if(0 < newX && newX <= 10 && 0 < col && col <= 10) {
                            maps[a][newX][col] = P;
                        }
                    }
                }
                // 역삼각형
                for(int row = 1; row <= C; row++) {
                    int newX = x+row;
                    int newY = y-C+row;
                    for(int col = 0; col < (C-row)*2+1; col++) {
                        if(0 < newX && newX <= 10 && 0 < newY+col && newY+col <= 10) {
                            maps[a][newX][newY+col] = P;
                        }
                    }
                }
            }

            coorA = new int[] {1, 1};
            coorB = new int[] {10, 10};
            getChoice(); // T=0도 계산해야함
            // 이동
            for(int m = 0; m < M; m++) {
                switch (pA[m]) {
                    case 1: // 상
                        coorA[0]--;
                        break;
                    case 2: // 우
                        coorA[1]++;
                        break;
                    case 3: // 하
                        coorA[0]++;
                        break;
                    case 4: // 좌
                        coorA[1]--;
                        break;
                }

                switch (pB[m]) {
                    case 1:
                        coorB[0]--;
                        break;
                    case 2:
                        coorB[1]++;
                        break;
                    case 3:
                        coorB[0]++;
                        break;
                    case 4:
                        coorB[1]--;
                        break;
                }
                getChoice();
            }
            System.out.println("#" + tc + " " + answer);
        }
    }
    static void getChoice() {
        int maxA = 0;
        int maxB = 0;
        List<Integer> availA = new ArrayList<>();
        List<Integer> availB = new ArrayList<>();
        for(int a = 0; a < A; a++) {
            if(maps[a][coorA[0]][coorA[1]] > 0) { // A가 범위 안에 들어옴
                availA.add(a);
                maxA = Math.max(maxA, maps[a][coorA[0]][coorA[1]]);
            }
            if(maps[a][coorB[0]][coorB[1]] > 0) { // B가 범위 안에 들어옴
                availB.add(a);
                maxB = Math.max(maxB, maps[a][coorB[0]][coorB[1]]);
            }
        }
        // 만약 한쪽만 가능하다면 그냥 바로 최대값 넣고 종료
        if(availA.size() == 0 || availB.size() == 0) {
            answer+=(maxA+maxB);
            return;
        }
        // 둘 다 충전기 접근 되면 두 집합에서 하나씩 뽑아서 가능한 경우의 수 만들기
        int max = 0; // 두 충전기 합친 결과
        for(int a = 0; a < availA.size(); a++) {
            int bc1 = availA.get(a);
            for(int b = 0; b < availB.size(); b++) {
                int bc2 = availB.get(b);
                if(bc1 == bc2) {
                    max = Math.max(max, maps[bc1][coorA[0]][coorA[1]]);
                }else {
                    max = Math.max(max, maps[bc1][coorA[0]][coorA[1]] + maps[bc2][coorB[0]][coorB[1]]);
                }
            }
        }
        answer+=max;
    }
}
