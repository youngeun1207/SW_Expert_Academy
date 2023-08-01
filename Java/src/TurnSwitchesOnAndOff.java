import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BJ 1244 스위치 켜고 끄기
 * @author youngeun
 *
 * 단순구현 문제
 * 스위치 상태 변경을 (현 상태 + 1) % 2 로 계산하여 조건 분기 없이 토글하였음
 */
public class TurnSwitchesOnAndOff {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] switches = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            switches[i] = Integer.parseInt(st.nextToken());
        }

        int students = Integer.parseInt(br.readLine());
        for(int s = 0; s < students; s++){
            st = new StringTokenizer(br.readLine());
            int node;
            switch(Integer.parseInt(st.nextToken())){
                case (1): // 남학생
                    node = Integer.parseInt(st.nextToken());
                    for (int i = node-1; i < n; i += node){ // node의 배수 토글
                        switches[i] = (switches[i]+1)%2;
                    }
                    break;
                case (2): // 여학생
                    node = Integer.parseInt(st.nextToken()) - 1;
                    switches[node] = (switches[node]+1)%2;
                    for(int i = 1; i <= n/2; i++){ // 노드 앞뒤로 i만큼 떨어진 칸이 같으면 해당 칸들 토글
                        if(0 <= node-i && node+i < n && switches[node-i] == switches[node+i]){
                            switches[node+i] = (switches[node+i]+1)%2;
                            switches[node-i] = (switches[node-i]+1)%2;
                        }else{ // 하나라도 다르면 탈출
                            break;
                        }
                    }
                    break;
            }
        }
        for(int i = 0; i < n; i++){
            System.out.print( switches[i] + " ");
            if((i+1) % 20 == 0){
                System.out.println();
            }
        }

    }
}
