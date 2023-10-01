import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 14889 스타트와 링크
 *
 * @author youngeun
 *
 * 15696 KB
 * 404 ms
 *
 * <next permutation을 사용한 Combination>
 * 그냥 np를 씀..
 */
public class BJ_14889_스타트와링크_신영은 {
    static int N, answer = Integer.MAX_VALUE;
    static int[][] status;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        status = new int[N][N];
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                status[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] npArray = new int[N];
        int cnt = 0;
        while (++cnt <= N/2) {
            npArray[N-cnt] = 1;
        }

        do {
            int start = 0;
            int link = 0;
            // 2개니까 그냥 반복문으로 2개 골라~
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (npArray[i] == 0 && npArray[j] == 0) {
                        start += status[i][j];
                    } else if (npArray[i] == 1 && npArray[j] == 1) {
                        link += status[i][j];
                    }
                }
            }

            answer = Math.min(answer, Math.abs(start - link));

        }while(nextPermutation(npArray));

        System.out.println(answer);
    }
    static boolean nextPermutation(int[] p) {
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
