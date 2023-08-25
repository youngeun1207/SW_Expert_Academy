import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 2470 두 용액
 * @author youngeun
 *
 * 31580 KB
 * 296 ms
 *
 * <투포인터>
 * - 용액 정렬 후 맨 앞(start), 맨 뒤(end)에서부터 차례로 좁혀나가기
 *     - 만약 용액 합이 음수면 -> start++
 *     - 용액 합이 양수면 -> end--
 * - start == end 일 때 종료
 * - 현재 최소 차이보다 |차이|가 더 작으면 최소값 갱신하고 인덱스 새로 저장
 */
public class TwoLiquid {
    static int N, start, end, l1, l2;
    static long[] liqids;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        liqids = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int n = 0; n < N; n++){
            liqids[n] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(liqids);

        long answer = Long.MAX_VALUE;
        start = 0;
        end = N-1;

        while (start != end){
            long tmp = liqids[start] + liqids[end];
            if(Math.abs(tmp) < answer){
                answer = Math.abs(tmp);
                l1 = start;
                l2 = end;
            }

            if(tmp == 0){
                break;
            }else if(tmp < 0){
                start++;
            }else {
                end--;
            }
        }
        System.out.println(liqids[l1] + " " + liqids[l2]);
    }
}
