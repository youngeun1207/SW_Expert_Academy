import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Miro {
    static int[] isJumped(int current_row, int current_col, int new_row, int new_col, Coordinate[] jumpers){
        int max_c;
        int min_c;
        int max_r;
        int min_r;

        if(current_col < new_col){
            max_c = new_col;
            min_c = current_col;
        }else{
            min_c = new_col;
            max_c = current_col;
        }

        if(current_row < new_row){
            max_r = new_row;
            min_r = current_row;
        }else{
            min_r = new_row;
            max_r = current_row;
        }

        for(Coordinate jumper : jumpers){
            if(min_c <= jumper.col && max_c >= jumper.col && min_r <= jumper.row && max_r >= jumper.row){
                return new int[] {0, 0};
            }
        }
        return new int[] {new_row, new_col};
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // TC
        int testCase = Integer.parseInt(br.readLine());

        for (int t = 1; t <= testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 미로 크기
            int n = Integer.parseInt(st.nextToken());
            // 초기 위치
            Coordinate current = new Coordinate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            // 점퍼
            int jumperSize = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            Coordinate[] jumpers = new Coordinate[jumperSize];
            for (int i = 0; i < jumperSize; i++) {
                jumpers[i] = new Coordinate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            // 명령(방향, 이동 칸 수)
            int commandSize = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            Command[] commands = new Command[commandSize];
            for (int i = 0; i < commandSize; i++) {
                commands[i] = new Command(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            for(Command cmd : commands){
                int[] rc = new int[] {current.row, current.col};
                switch (cmd.way){
                    case (1): // 상
                        rc[0] -= cmd.dist;
                        break;
                    case (2): // 우
                        rc[1] += cmd.dist;
                        break;
                    case (3): // 하
                        rc[0] += cmd.dist;
                        break;
                    case (4): // 좌
                        rc[1] -= cmd.dist;
                        break;
                }
                rc = isJumped(current.row, current.col, rc[0], rc[1], jumpers);
                if(rc[0] == 0 && rc[1] == 0){
                    current.row = 0;
                    current.col = 0;
                    break ;
                }
                else if( rc[0] < 1 || rc[0] > n || rc[1] < 1 || rc[1] > n){ // 칸 넘어갔는지
                    current.row = 0;
                    current.col = 0;
                    break ;
                }
                current.row = rc[0];
                current.col = rc[1];
            }
            System.out.println("#" + t + " " + current.row + " " + current.col);
        }
    }
}
class Coordinate{
    int row;
    int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Command{
    int way;
    int dist;

    public Command(int way, int dist) {
        this.way = way;
        this.dist = dist;
    }
}