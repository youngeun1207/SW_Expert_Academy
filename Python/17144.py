# 백준 17144 미세먼지 안녕!
# 삼성 SW 역량 테스트 기출
R, C, T = map(int, input().split())
dirs = [(-1, 0), (1, 0), (0, -1), (0, 1)]
field = []
for r in range(R):
    field.append(list(map(int, input().split())))

# 공기청정기 위치 파악
fresher = (-1, -1)
flag = False
for r in range(R):
    for c in range(C):
        if field[r][c] == -1:
            fresher = (r, r+1)
            flag = True
            break
    if flag:
        break

def diffusion(): # 확산
    diffused = [[0]*C for _ in range(R)] # 확산된 먼지 상태 저장할 2차원 배열

    for r in range(R):
        for c in range(C):
            cnt = 0
            prev = field[r][c]  # 현재 먼지 상태 저장 -> 확산 후 남은 먼지 계산 위함
            if prev <= 0:
                continue

            to_diffuse = prev // 5

            for dir_r, dir_c in dirs:
                # 확산 될 위치
                new_c = c+dir_c
                new_r = r+dir_r

                if(0 <= new_c < C and 0 <= new_r < R and field[new_r][new_c] >= 0):
                    cnt+=1 # 확산된 칸 수
                    diffused[new_r][new_c] += to_diffuse
            diffused[r][c] += (prev - (cnt * to_diffuse))

    return diffused

def turn():
    turn_clock()
    turn_reverse()

    field[fresher[0]][0] = -1
    field[fresher[1]][0] = -1

def turn_clock(): # 데이터 손실 없도록 왼 -> 위 -> 오 -> 아래 순으로 이동
    field_up = field[:fresher[1]]
    height = len(field_up)-1
    width = C-1

    # 맨 왼쪽 열 -> 위에서 아래로
    prev = field_up[0][0]
    for i in range(1, height+1):
        tmp = field_up[i][0]
        field_up[i][0] = prev
        prev = tmp

    # 맨 윗 줄 -> 오른쪽에서 왼쪽으로
    prev = field_up[0][width]
    for i in range(1, width+1):
        tmp = field_up[0][width-i]
        field_up[0][width-i] = prev
        prev = tmp

    # 맨 오른쪽 열 -> 아래에서 위로
    prev = field_up[height][-1]
    for i in range(1, height+1):
        tmp = field_up[height-i][-1]
        field_up[height-i][-1] = prev
        prev = tmp

    # 맨 밑 즐 -> 왼쪽에서 오른쪽으로
    prev = 0
    for i in range(1, width+1):
        tmp = field_up[-1][i]
        field_up[-1][i] = prev
        prev = tmp

def turn_reverse(): # 데이터 손실 없도록 왼 -> 아래 -> 오 -> 위 순으로 이동
    field_down = field[fresher[1]:]
    height = len(field_down) - 1
    width = C-1
    # 맨 왼쪽 열 -> 아래에서 위로
    prev = field_down[height][0]
    for i in range(1, height+1):
        tmp = field_down[height-i][0]
        field_down[height-i][0] = prev
        prev = tmp

    # 맨 밑 줄 -> 오른쪽에서 왼쪽으로
    prev = field_down[-1][width]
    for i in range(1, width+1):
        tmp = field_down[-1][width-i]
        field_down[-1][width-i] = prev
        prev = tmp

    # 맨 오른쪽 열 -> 위에서 아래로
    prev = field_down[0][-1]
    for i in range(1, height+1):
        tmp = field_down[i][-1]
        field_down[i][-1] = prev
        prev = tmp

    # 맨 윗 줄 -> 왼쪽에서 오른쪽으로
    prev = 0
    for i in range(1, width+1):
        tmp = field_down[0][i]
        field_down[0][i] = prev
        prev = tmp

def printSum():
    ans = 0
    for f in field:
        ans+=sum(f)
    print(ans+2) # 냉장고 위치 표시하는 -1 2개 고려

for t in range(T):
    field = diffusion()
    turn()
printSum()
