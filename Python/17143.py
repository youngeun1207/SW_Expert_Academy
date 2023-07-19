# 백준 17143 낚시왕
# 삼성 SW 역량 테스트 기출
# 메모리 117724 KB
# 시간 240 ms
# 단순 구현 + 해싱 + 이동 거리 계산
class Shark:
    def __init__(self, idx, r, c, s, d, z):
        self.idx = idx # 몇 번 상어인지
        self.r = r # row
        self.c = c # col
        self.s = s # speed
        self.d = d # 이동 방향
        self.z = z # size
        self.dead = False

    def __str__(self): # 디버깅용 출력문
        dirs = ['↑', '↓', '→', '←']
        return '{}번 상어 r:{} c:{} dir:{} dead:{}'.format(self.idx, self.r, self.c, dirs[self.d], self.dead)


R, C, M = map(int, input().split())
shark_state = {} # shark_map 순회 안하고도 상어 이동할 수 있도록 상어 위치와 상태 딕셔너리로 저장(해싱)
shark_map = [[-1] * C for _ in range(R)] # 2차원 배열 상의 상어 위치 -> 낚시 대상 상어 찾는 용으로 쓰임
answer = 0

# 왕복이므로 5칸이면 01234321 순으로 움직임
# 상어가 움직이는 상행, 하행 사이클 저장
cycle_R = []
for r in range(R):
    cycle_R.append(r)
for r in range(2, R):
    cycle_R.append(R-r)
cycle_C = []
for c in range(C):
    cycle_C.append(c)
for c in range(2, C):
    cycle_C.append(C-c)

for m in range(M):
    r, c, s, d, z = map(int, input().split())
    shark_map[r - 1][c - 1] = m
    # 역방향(위, 왼쪽)일 경우 cycle에서의 index로 바꿔 저장해야함
    if d == 1:
        r = 2 * R - r
    elif d == 4:
        c = 2 * C - c
    shark_state[m] = Shark(m, r - 1, c - 1, s, d - 1, z)


def moveShark(shark):
    if shark.d < 2: # 위, 아래 -> r 변경
        distance = shark.s + shark.r # 현재 상어 위치(cycle 내 index) + speed
        shark.r = distance % len(cycle_R) # cycle로 저장했기 때문에 나머지 연산하면 상어 위치 빠르게 구할 수 있음!
    else: # 오른, 왼 -> c 변경
        distance = shark.s + shark.c
        shark.c = distance % len(cycle_C)

def eatShark():
    global shark_map
    global shark_state
    # 상어끼리 잡아 먹은 후의 shark_map -> 초기화해서 사용함
    shark_map = [[-1] * C for _ in range(R)]
    for shark in shark_state.values():
        if not shark.dead:
            # shark 객체 내에는 cycle 내에서의 index 저장했기 때문에 실제 index로 변환
            r, c = cycle_R[shark.r], cycle_C[shark.c]
            if shark_map[r][c] == -1: # 비어있는 경우
                shark_map[r][c] = shark.idx
            elif shark.z > shark_state[shark_map[r][c]].z: # 기존 상어보다 큰 경우 -> 잡아먹기
                shark_state[shark_map[r][c]].dead = True
                shark_map[r][c] = shark.idx
            else: # 기존 상어보다 작은 경우 -> 잡아먹히기
                shark.dead = True

def getShark(idx):
    global shark_map
    global answer
    for r in range(R):
        if shark_map[r][idx] != -1:
            answer += shark_state[shark_map[r][idx]].z # 상어의 크기 더해야 함
            shark_state[shark_map[r][idx]].dead = True
            shark_map[r][idx]= -1
            break # 가장 가까운 상어 낚시하면 바로 반복문 탈출

for c in range(C):
    getShark(c)
    for shark in shark_state.values():
        if not shark.dead: # 죽은 상어는 움직일 필요 없다
            moveShark(shark)
    eatShark()
print(answer)