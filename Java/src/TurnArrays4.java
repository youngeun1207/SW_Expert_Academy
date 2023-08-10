import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 17406 배열 돌리기 4
 * @author youngeun
 *
 * 22628 KB
 * 232 ms
 *
 * 단순구현 + 순열
 * 6개로 순열이지만 nextPermutation 연습을 위해 np로 구해보았음
 */
public class TurnArrays4 {
    public static int N, M, K;
    public static int[][] original; // 원본 배열
    public static boolean[] visited;
    public static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 원본 배열 저장
        original = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= M; j++) {
                original[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] op = new int[K][3]; // r, c, s

        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            op[k] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
        }
        // 순열 구하기 -> op 배열 대신 op배열을 가리킬 index로 순열 생성
        // 1 ≤ K ≤ 6 이므로 시간에 큰 무리는 없지만 nextPermutation을 활용해 볼 예정
        int[] index = new int[K];
        for(int i = 0; i < K; i++){ // 이미 정렬된 형태
            index[i] = i;
        }
        int[] thisOp;
        int[][] copyArr;
        do{
            copyArr = deepCopy(original);
            for(int i = 0; i < K; i++){
                thisOp = op[index[i]]; // 순열 순으로 고른 명령
                findVertex(thisOp[0], thisOp[1], thisOp[2], copyArr); // r, c, s, 원본 배열
            }
            answer = Math.min(answer, getMinRow(copyArr));
        }while (nextPermutation(index));

        System.out.println(answer);
    }

    /**
     * 순열 구하는 함수
     * @param p : 다음 순열을 원하는 기존 순열의 배열
     * @return : 다음 순열이 존재하는지 여부
     */
    private static boolean nextPermutation(int[] p) {
        // 1. 맨 뒤에서부터 탐색하며 꼭대기 찾기
        int N = p.length;
        int i = N-1;
        while (i > 0 && p[i-1] >= p[i]) {
            --i;
        }
        if(i==0) { // 다음 순열 없음(가장 큰 순열의 형태)
            return false;
        }
        // 2. 꼭대기 직전(i-1) 위치에 교환할 한단계 큰 수 뒤쪽부터 찾기
        int j = N-1;
        while (p[i-1] >= p[j]) {
            --j;
        }
        // 3. 꼭대기 직전(i-1) 위치의 수와 한단계 큰 수(j)를 교환
        swap(p, i-1, j);
        // 4. 꼭대기 자리부터 맨 뒤까지의 수를 오름차순의 형태로 바꿈
        int k = N-1;
        while(i<k) {
            swap(p, i++, k--);
        }
        return true; // 순열 완성!
    }

    /**
     * next Permutation에서 i-1 <-> j 바꾸는 용도
     * @param p : 순열 배열
     * @param a : i-1
     * @param b : j
     */
    static void swap(int[] p, int a, int b) {
        int tmp = p[a];
        p[a] = p[b];
        p[b] = tmp;
    }

    /**
     * 꼭짓점 찾기
     * @param r
     * @param c
     * @param s
     * @param arr
     */
    static void findVertex(int r, int c, int s, int[][] arr) {
        for (int i = 0; i < s; i++) { // 한 줄 씩 안으로 들어가기
            // 위, 왼 -> i만큼 더하기
            int top = r - s + i;
            int left = c - s + i;
            // 아래, 오른 -> i만큼 빼기
            int bottom = r + s - i;
            int right = c + s - i;
            // 해당 줄 돌리기
            rotate(arr, top, left, bottom, right);
        }
    }

    /**
     * 시계 방향으로 배열 돌리기
     * @param arr : 원본 배열
     * @param top : 위
     * @param left : 왼
     * @param bottom : 아래
     * @param right : 오른
     */
    static void rotate(int[][] arr, int top, int left, int bottom, int right) {
        int tmp, prev;

        // 위: 왼 -> 오
        // 맨 오른쪽 칸 부터 자기 직전(바로 왼쪽) 칸 값 끌고오기
        tmp = arr[top][right];
        for (int y = right; y > left; y--) {
            arr[top][y] = arr[top][y - 1];
        }

        // 오른쪽: 위 -> 아래
        // 맨 아래 칸 부터 자기 위에 칸 끌고오기
        prev = tmp;
        tmp = arr[bottom][right];
        for (int x = bottom; x > top; x--) {
            if (x - 1 == top) {
                arr[x][right] = prev; // 윗줄 맨 오른쪽 칸 저장해둔 값 집어넣기
                continue;
            }
            arr[x][right] = arr[x - 1][right];
        }

        // 아래: 오 -> 왼
        // 맨 왼쪽 칸부터 자기 오른쪽 칸 끌고오기
        prev = tmp;
        tmp = arr[bottom][left];
        for (int y = left; y < right; y++) {
            if (y + 1 == right) {
                arr[bottom][y] = prev; // 오른쪽 줄 맨 아래 칸 저장해둔 값 집어넣기
                continue;
            }
            arr[bottom][y] = arr[bottom][y + 1];
        }

        // 왼: 아래 -> 위
        // 맨 윗쪽 칸부터 자기 아래쪽 칸 끌고오기
        for (int x = top; x < bottom; x++) {
            if (x + 1 == bottom) {
                arr[x][left] = tmp; // 아랫 줄 맨 왼쪽 칸 집어넣기
                continue;
            }
            arr[x][left] = arr[x + 1][left];
        }
    }

    /**
     * 각 행에 있는 모든 수의 합 중 최솟값 구하기
     * @param arr
     * @return
     */
    static int getMinRow(int[][] arr) {
        int answer = Integer.MAX_VALUE;

        for(int i =1; i <= N; i++){
            int sum = 0;
            for(int j = 1; j <= M; j++){
                sum += arr[i][j];
            }
            answer = Math.min(answer, sum);
        }
        return answer;
    }

    /**
     * 조합에 따라 배열 회전에 영향 없도록 2차원 배열 복사해서 넘기기 위함
     * @param arr : 원본 배열
     * @return : 복사된 배열
     */
    static int[][] deepCopy(int[][] arr){
        int[][] copied = new int[arr.length][];

        for(int i = 0; i < arr.length; i++){
            copied[i] = arr[i].clone();
        }
        return copied;
    }

}
