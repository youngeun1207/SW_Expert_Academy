package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 2493 탑
 *
 * @author youngeun
 *
 * 153,444 KB
 * 584 ms
 *
 * DP 메모이제이션으로 접근
 *
 * 나의 직전 탑이 나보다 크면 -> 나의 직전 탑이 수신한 탑임!
 * 나의 직전 탑이 나보다 작으면 ->
 * 		나의 직전 탑 ~ 맨 처음 탑 거꾸로 돌면서
 * 		만약 이전 탑에 저장된 높이가 나보다 크면 -> 이전 탑에서 수신한 탑 인덱스 복붙
 * 		만약 이전 탑에 저장된 높이가 나보다 작으면 -> 이전 탑에서 수신한 탑 ~ 이전 탑 까지는 나보다 작은거임 => 인덱스 건너뛰기
 * 인덱스 건너뛰기를 안하면 시간초과 (500,000 ^2).. 잘 쩜프쩜프 하자
 */
public class Tower {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] dpIdx = new int[N+1];
        int[] dpHeight = new int[N+1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        int heights[] = new int[N+1];
        for(int i = 1; i <= N; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
            if(heights[i-1] > heights[i]) { // 나의 직전 탑이 나보다 크면 내 직전 탑이 수신한 탑
                dpIdx[i] = i-1;
                dpHeight[i] = heights[i-1];
            }else { // 현재 탑 -1 ~ 첫번째 탑까지 순회할거임
                for(int j = i-1; j > 0;) {
                    if(dpHeight[j] > heights[i]) { // 이전 탑의 신호를 수신한 탑이 나보다 더 크면
                        dpIdx[i] = dpIdx[j]; // 나도 그 탑이 수신할거임
                        dpHeight[i] = heights[dpIdx[j]];
                        break;
                    }else { // 인덱스 점프해줘야 시간초과 안남
                        j = dpIdx[j]; // 탑 j ~ j를 수신한 탑 사이 값은 검증할 필요 없음.. 무조건 나보다 작아
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= N; i++) {
            sb.append(dpIdx[i] + " ");
        }
        System.out.print(sb.toString());
    }

}
