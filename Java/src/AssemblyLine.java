import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 소프티어 Lv.3 조립라인
 * 전형적인 DP문제
 * 88ms, 10.84Mb
 *
 * A 조립라인 선택 시 최적 시간과, B 조립라인 선택 시 최적 시간을 DP 배열에 저장
 * 다음 순서 때 DP에 저장된 최적값에서 라인을 이동할 시, 이동하지 않을 시 2가지 경우의 수 중 최소값을 저장
 * 최종적으로 N개의 순서 다 돈 이후에 DP 마지막 index의 더 적은 값을 고르면 끝
 */
public class AssemblyLine {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] assembly_line = new int[N][4]; // 0:A, 1:B, 2:다음 줄로 넘어가는데 걸리는 시간, 3:다음 줄로 넘어가는데 걸리는 시간

        for (int i = 0; i < N-1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            assembly_line[i][0] = Integer.parseInt(st.nextToken());
            assembly_line[i][1] = Integer.parseInt(st.nextToken());
            assembly_line[i][2] = Integer.parseInt(st.nextToken());
            assembly_line[i][3] = Integer.parseInt(st.nextToken());
        }
        StringTokenizer st = new StringTokenizer(br.readLine());
        assembly_line[N-1][0] = Integer.parseInt(st.nextToken());
        assembly_line[N-1][1] = Integer.parseInt(st.nextToken());

        // DP
        int[][] dp = new int[N][2];
        dp[0][0] = assembly_line[0][0]; // A 선택 시 최적값
        dp[0][1] = assembly_line[0][1]; // B 선택 시 최적값

        for (int i = 1; i < N; i++) {
            // 이동하지 않았을 때 vs 이동했을 때 중 최소값 + 현재 단계에서 걸리는 시간
            dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + assembly_line[i - 1][3]) + assembly_line[i][0];
            dp[i][1] = Math.min(dp[i - 1][1], dp[i - 1][0] + assembly_line[i - 1][2]) + assembly_line[i][1];
        }

        System.out.println(Math.min(dp[N - 1][0], dp[N - 1][1])); // A, B 조립라인 중 더 작은 값 출력
    }
}