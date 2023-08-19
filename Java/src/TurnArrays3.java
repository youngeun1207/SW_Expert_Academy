import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 16935 배열 돌리기 3
 * @author youngeun
 * 71212 KB
 * 440 ms
 *
 * 단순구현 문제
 * 새로 저장할 배열 new_arr을 만든 후 변경한 인덱스에 재배치 함
 * 그 후 기존 arr을 new_arr로 바꾸기
 */
public class TurnArrays3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        int[][] arr = new int[N][M];
        int[][] new_arr = null;
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        int tmp;
        for(int op = 0; op < R; op++) {
            switch (st.nextToken()) {
                case "1": // 위 아래 뒤집기
                    new_arr = new int[N][M];
                    for(int i = 0; i < N; i++) {
                        for(int j = 0; j < M; j++) {
                            new_arr[i][j] = arr[N-1-i][j];
                        }
                    }
                    break;

                case "2": // 좌우 뒤집기
                    new_arr = new int[N][M];
                    for(int i = 0; i < N; i++) {
                        for(int j = 0; j < M; j++) {
                            new_arr[i][j] = arr[i][M-1-j];
                        }
                    }
                    break;

                case "3": // 왼쪽으로 90도
                    new_arr = new int[M][N];
                    for(int i = 0; i < N; i++) {
                        for(int j = 0; j < M; j++) {
                            new_arr[j][N-1-i] = arr[i][j];
                        }
                    }
                    tmp = N;
                    N = M;
                    M = tmp;
                    break;

                case "4": // 오른쪽으로 90도
                    new_arr = new int[M][N];
                    for(int i = 0; i < N; i++) {
                        for(int j = 0; j < M; j++) {
                            new_arr[M-1-j][i] = arr[i][j];
                        }
                    }
                    tmp = N;
                    N = M;
                    M = tmp;
                    break;

                case "5":
                    new_arr = new int[N][M];
                    // 1 -> 2
                    for(int i = 0; i < N/2; i++) {
                        for(int j = 0; j < M/2; j++) {
                            new_arr[i][j+M/2] = arr[i][j];
                        }
                    }
                    // 2 -> 3
                    for(int i = 0; i < N/2; i++) {
                        for(int j = M/2; j < M; j++) {
                            new_arr[i+N/2][j] = arr[i][j];
                        }
                    }
                    // 3 -> 4
                    for(int i = N/2; i < N; i++) {
                        for(int j = M/2; j < M; j++) {
                            new_arr[i][j-M/2] = arr[i][j];
                        }
                    }
                    // 4 -> 1
                    for(int i = N/2; i < N; i++) {
                        for(int j = 0; j < M/2; j++) {
                            new_arr[i-N/2][j] = arr[i][j];
                        }
                    }
                    break;
                case "6":
                    new_arr = new int[N][M];
                    // 1 -> 4
                    for(int i = 0; i < N/2; i++) {
                        for(int j = 0; j < M/2; j++) {
                            new_arr[i+N/2][j] = arr[i][j];
                        }
                    }
                    // 4 -> 3
                    for(int i = 0; i < N/2; i++) {
                        for(int j = M/2; j < M; j++) {
                            new_arr[i][j-M/2] = arr[i][j];
                        }
                    }
                    // 3 -> 2
                    for(int i = N/2; i < N; i++) {
                        for(int j = M/2; j < M; j++) {
                            new_arr[i-N/2][j] = arr[i][j];
                        }
                    }
                    // 2 -> 1
                    for(int i = N/2; i < N; i++) {
                        for(int j = 0; j < M/2; j++) {
                            new_arr[i][j+M/2] = arr[i][j];
                        }
                    }
                    break;
            }
            arr = new_arr;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                sb.append(arr[i][j] + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
