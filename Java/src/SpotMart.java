import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA D3 9229. 한빈이와 Spot Mart
 * @author youngeun
 *
 * 27,620 kb
 * 136 ms
 *
 * <투포인터 풀이>
 * 과자 무게를 정렬한 후 가장 가벼운, 가장 무거운 과자부터 더해서 최대값 갱신하기
 * 합한 무게가 가방에 안들어가면 end--
 * 합한 무게가 가방에 들어가면 start++
 */
public class SpotMart {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        for(int tc = 1; tc<= TC; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()); // 과자 봉지 개수
            int M = Integer.parseInt(st.nextToken()); // 무게 합 제한

            int[] snacks = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < N; i++){
                snacks[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(snacks);

            // 앞 뒤로 투포인터
            int max = -1;
            int start = 0;
            int end = N-1;
            while(start!=end) { // 둘이 만나면 종료
                int sum = snacks[start] + snacks[end];
                if(sum > M)
                    end--;
                else {
                    max = Math.max(max, sum);
                    start++;
                }
            }
            System.out.println("#" + tc + " " + max);

        }
    }
}
