import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 6987 월드컵
 * @author youngeun
 *
 * 11656 KB
 * 80 ms
 *
 * <백트래킹>
 * 1. 먼저 6팀의 겨룰 수 있는 경우의 수 next combination으로 구함
 * 2. DFS 돌면서 두 팀이 각각 이길 경우, 비길 경우, 질 경우로 재귀적 탐색
 *  - 단, 모든 경우의 수를 탐색하기애는 시간이 오래 걸리므로 백트래킹 수행
 *  - 만약 가능한 이긴/비긴/진 경우 초과하면 재귀 더이상 수행하지 않음 -> 입력으로 받은 배열 값 줄여가면서 0이 되기 전까지만 가능
 * 3. 각 팀당 승부 합이 5가 안되는 경우도 틀린 경우이므로 입력 시에 조건 추가해줌.
 */
public class WorldCup {
    static int[][] scores = new int[6][3];
    static int win, same, lose, answer;
    static List<int[]> match; // 겨룰 수 있는 경우의 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        match = new ArrayList<>(); // 가능한 승부 경우의 수 저장할 배열
        int[] p = new int[6];
        int cnt = 0;
        while(++cnt <= 2) { // 6팀 중 2팀 고를것임
            p[6-cnt] = 1;
        }
        do { // next permutation 활용하여 combination 구하기
            int[] m = new int[2]; // 팀 A, B
            cnt = 0;
            for(int i = 0; i < 6; i++){
                if(p[i] == 1) {
                    m[cnt++] = i;
                }
            }
            match.add(Arrays.copyOf(m, 2));
        }while(nextPermutation(p));

        Loop:
        for(int tc = 0; tc < 4; tc++) {
            answer = 0;
            st = new StringTokenizer(br.readLine());
            for(int c = 0; c < 6; c++) {
                win = Integer.parseInt(st.nextToken());
                same = Integer.parseInt(st.nextToken());
                lose = Integer.parseInt(st.nextToken());
                if(win + lose + same != 5){ // 승부의 합이 5가 아니면 불가능한 경우
                    System.out.print(answer + " ");
                    continue Loop;
                }
                scores[c] = new int[] {win, same, lose};
            }
            // DFS
            DFS(0);
            System.out.print(answer + " ");
        }

    }

    /**
     * DFS 백트래킹
     * @param count 몇번째 승부인지 확인 -> 6C2 조합이므로 (6*5)/2 == 15
     */
    static void DFS(int count) {
        if(count == 15) { // 6C2의 모든 조합 확인 완료
            answer = 1;
            return;
        }
        int team = match.get(count)[0];
        int rival = match.get(count)[1];
        // 내가 이김
        if(scores[team][0] > 0 && scores[rival][2] > 0) {
            scores[team][0]--; // 내가 이긴 횟수 -1
            scores[rival][2]--; // 상대 진 횟수 -1
            DFS(count+1);
            scores[team][0]++; // 배열 원복
            scores[rival][2]++;
        }
        // 무승부
        if(scores[team][1] > 0 && scores[rival][1] > 0) {
            scores[team][1]--; // 내가 비긴 횟수 --
            scores[rival][1]--; // 상대가 비긴 횟수 --
            DFS(count+1);
            scores[team][1]++; // 배열 원복
            scores[rival][1]++;
        }
        // 내가 짐
        if(scores[team][2] > 0 && scores[rival][0] > 0) {
            scores[team][2]--; // 내가 진 횟수 --
            scores[rival][0]--; // 상대가 이긴 횟수 ++
            DFS(count+1);
            scores[team][2]++; // 배열 원복
            scores[rival][0]++;
        }
    }


    private static boolean nextPermutation(int[] p) {
        int N = p.length;
        int i = N-1;
        while (i > 0 && p[i-1] >= p[i]) {
            --i;
        }
        if(i==0) {
            return false;
        }
        int j = N-1;
        while (p[i-1] >= p[j]) {
            --j;
        }
        swap(p, i-1, j);
        int k = N-1;
        while(i<k) {
            swap(p, i++, k--);
        }
        return true;
    }
    private static void swap(int[] p, int a, int b) {
        int tmp = p[a];
        p[a] = p[b];
        p[b] = tmp;
    }
}