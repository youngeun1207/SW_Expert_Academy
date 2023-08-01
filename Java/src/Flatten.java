import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * SWEA D3 1208 Flatten
 * @author youngeun
 * 18,564 kb
 * 116 ms
 *
 * 투 포인터
 * O(N)
 * 1 ~ 100의 높이를 가지는 박스 -> 박스 높이 별로 갯수 저장
 * max 높이와 min 높이 박스 갯수에서 1개씩 뺀 다음 min+1, max+1에 하나씩 추가
 * 만약 현재 min or max 높이의 박스 갯수가 0개면 min or max 변경
 * min과 max가 동일하면 이미 다 flatten 된 것임
 * dump 안에 해결 못하면 max와 min의 차이가 최대 높이 차
 */
public class Flatten {
    static int dump;
    static int[] blocks;

    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(int tc = 1; tc <= 10; tc++) {
            dump = Integer.parseInt(br.readLine());
            int min = 99;
            int max = 0;
            int idx;
            StringTokenizer st = new StringTokenizer(br.readLine());

            blocks = new int[100]; // 1 ~ 100의 높이를 가지는 박스

            for(int i = 0; i < 100; i++) {
                idx = Integer.parseInt(st.nextToken())-1;
                blocks[idx]++; // 박스 높이 별로 갯수 저장
                max = Math.max(max, idx); // 최소 높이 갱신
                min = Math.min(min, idx); // 최대 높이 갱신
            }
            // 투포인터로 풀기
            for(int d = 0; d < dump; d++) {
                if(min == max) { // min과 max가 동일하면 이미 다 flatten 된 것임
                    break;
                }
                // max 높이와 min 높이 박스 갯수에서 1개씩 뺀 다음 min+1, max+1에 하나씩 추가
                blocks[min]--;
                blocks[min+1]++;
                blocks[max]--;
                blocks[max-1]++;

                // 해당 높이의 박스가 더이상 없으면 포인터 중앙으로 한칸씩 이동
                if(blocks[min] == 0) {
                    min++;
                }
                if(blocks[max] == 0) {
                    max--;
                }
            }
            System.out.printf("#%d %d\n", tc, max - min); // dump 안에 해결 못하면 max와 min의 차이가 최대 높이 차
        }
    }
}

