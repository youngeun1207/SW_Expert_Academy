import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SWEA
 * D3 1289 원재의 데이터 복구하기
 * @author youngeun
 *
 * 단순구현 문제..
 */
public class D3_1289 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        for(int t = 1; t <= testCase; t++){
            String code = br.readLine();
            char prev = '0'; // 초기값은 0
            int answer = 0;
            for(int i = 0; i < code.length(); i++){
                if(code.charAt(i) != prev){ // 만약 비트 바뀌면 고쳐주기
                    answer++;
                    prev = code.charAt(i);
                }
            }
            System.out.printf("#%d %d\n", t, answer);
        }
    }

}
