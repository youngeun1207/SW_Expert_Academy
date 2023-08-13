import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 10800 컬러볼
 * @author youngeun
 *
 * 109,144 KB
 * 1,128 ms
 *
 * <누적합>
 * 공을 크기 순으로 정렬
 * 작은 공부터 순서대로 현재 공보다 작은 공들의 크기 누적 더하기
 * 단, 같은 색은 제외이므로 색깔 별로 누적합 저장한 후 빼주기
 * 문제에서 입력 순으로 출력하도록 하므로, 크기 순 sort한 배열과, 입력 순서를 유지할 배열을 따로 만들었음
 */
public class ColorBall {
    static int N, idx = 0, sum = 0;
    static Ball[] ballIdx, ballSort;
    static int[] sameColor;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        ballIdx = new Ball[N]; // 입력 순서대로 출력해야하므로 입력 순서 그대로 저장하는 용
        ballSort = new Ball[N];
        sameColor = new int[N+1]; // 색 범위: 1 ≤ Ci ≤ N

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            ballIdx[i] = new Ball(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            ballSort[i] = ballIdx[i];
        }

        Arrays.sort(ballSort);


        for(int i = 0; i < N; i++) {
            Ball currentBall = ballSort[i]; // 현재 공
            while(ballSort[idx].size < currentBall.size) { // 나보다 작을 때 까지
                sum += ballSort[idx].size; // 나보다 작은 공들의 크기 누적 더하기
                sameColor[ballSort[idx].color] += ballSort[idx].size; // 이 떄 나랑 같은 색이면 빼야하므로, 색깔 별 크기 누적합도 저장함
                idx++;
            }
            currentBall.sum = sum - sameColor[currentBall.color]; // 나보자 작은 공들 크기 누적합 - 같은 색 공 크기 누적합
        }

        for(int i = 0; i < N; i++){
            sb.append(ballIdx[i]);
        }
        System.out.println(sb);
    }
    static class Ball implements Comparable<Ball> {
        int color;
        int size;
        int sum = 0;
        public Ball(int color, int size){
            this.color = color;
            this.size = size;
        }

        @Override
        public int compareTo(Ball b) {
            return this.size - b.size;
        }

        @Override
        public String toString() {
            return this.sum + "\n";
        }
    }
}
