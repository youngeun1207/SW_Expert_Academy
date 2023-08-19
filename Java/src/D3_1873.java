import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
/**
 * SWEA D3 1873. 상호의 배틀필드
 *
 * @author youngeun
 *
 * 33,148 kb
 * 196 ms
 *
 * 단순구현
 */
public class D3_1873 {
    static int T, H, W, N;
    static int[] tank = new int[2]; // 현재 전차 위치[h,w]
    static int[] dir = new int[2]; // 현재 전차 방향
    static String cmds;
    static char cmd;

    static char[][] map;
    static Map<Character, int[]> dirs;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());

        dirs = new HashMap<>();
        dirs.put('U', new int[]{-1, 0}); // up
        dirs.put('D', new int[]{1, 0}); // down
        dirs.put('L', new int[]{0, -1}); // left
        dirs.put('R', new int[]{0, 1}); // right

        for(int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            map = new char[H][W];

            for(int h = 0; h < H; h++) {
                cmds = br.readLine();
                for(int w = 0; w < W; w++) {
                    map[h][w] = cmds.charAt(w);
                    if(map[h][w] == '^' || map[h][w] == '<' || map[h][w] == '>' || map[h][w] == 'v') {
                        dir = changeToCmd(map[h][w]);
                        tank[0] = h;
                        tank[1] = w;
                    }
                }
            }
            N = Integer.parseInt(br.readLine());
            cmds = br.readLine();
            for(int i = 0; i < N; i++) {
                cmd = cmds.charAt(i);

                if(cmd == 'S') { // 발사
                    int curH = tank[0];
                    int curW = tank[1];
                    while(true) {
                        curH+=dir[0];
                        curW+=dir[1];
                        if(isOutOfRange(curH, curW) || map[curH][curW] == '#') {
                            break;
                        }
                        if(map[curH][curW] == '*') {
                            map[curH][curW] = '.';
                            break;
                        }
                    }
                }else { // 방향전환
                    dir = dirs.get(cmd);
                    if(!isOutOfRange(tank[0]+dir[0], tank[1]+dir[1]) && map[tank[0]+dir[0]][tank[1]+dir[1]] == '.') {
                        map[tank[0]][tank[1]] = '.';

                        tank[0]+=dir[0];
                        tank[1]+=dir[1];
                    }
                }
            }
            // 전차 지도에 표시
            map[tank[0]][tank[1]] = changeToIcon(dir[0], dir[1]);
            System.out.print("#" + tc + " ");
            for(int h = 0; h < H; h++) {
                for(int w = 0; w < W; w++) {
                    System.out.print(map[h][w]);
                }
                System.out.println();
            }
        }
    }
    static int[] changeToCmd(char icon) {
        switch (icon) {
            case '^':
                return dirs.get('U');
            case '<':
                return dirs.get('L');
            case '>':
                return dirs.get('R');
            default:
                return dirs.get('D');
        }
    }
    static char changeToIcon(int x, int y) {
        if(x == -1 && y == 0) {
            return '^';
        }
        else if(x == 1 && y == 0) {
            return 'v';
        }
        else if(x == 0 && y == -1) {
            return '<';
        }
        else {
            return '>';
        }
    }
    static boolean isOutOfRange(int h, int w) {
        if(0 <= h && h < H && 0 <= w && w < W) {
            return false;
        }
        return true;
    }
}