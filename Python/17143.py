# 백준 17143 낚시왕
# 삼성 SW 역량 테스트 기출
# 메모리 117724 KB
# 시간 240 ms
# 단순 구현 + 해싱 + 이동 거리 계산
class Shark:
    def __init__(self, idx, r, c, s, d, z):
        self.idx = idx
        self.r = r
        self.c = c
        self.s = s
        self.d = d
        self.z = z
        self.dead = False

    def __str__(self):
        dirs = ['↑', '↓', '→', '←']
        return '{}번 상어 r:{} c:{} dir:{} dead:{}'.format(self.idx, self.r, self.c, dirs[self.d], self.dead)


R, C, M = map(int, input().split())
shark_state = {}
shark_map = [[-1] * C for _ in range(R)]
answer = 0

cycle_R = []
cycle_C = []

for r in range(R):
    cycle_R.append(r)
for r in range(2, R):
    cycle_R.append(R-r)

for c in range(C):
    cycle_C.append(c)
for c in range(2, C):
    cycle_C.append(C-c)

for m in range(M):
    r, c, s, d, z = map(int, input().split())
    shark_map[r - 1][c - 1] = m
    if d == 1:
        r = 2 * R - r
    elif d == 4:
        c = 2 * C - c
    shark_state[m] = Shark(m, r - 1, c - 1, s, d - 1, z)


def moveShark(shark):
    if shark.d < 2:
        distance = shark.s + shark.r
        shark.r = distance % len(cycle_R)
    else:
        distance = shark.s + shark.c
        shark.c = distance % len(cycle_C)
def eatShark():
    global shark_map
    global shark_state
    shark_map = [[-1] * C for _ in range(R)]
    for shark in shark_state.values():
        if not shark.dead:
            r, c = cycle_R[shark.r], cycle_C[shark.c]
            if shark_map[r][c] == -1:
                shark_map[r][c] = shark.idx
            elif shark.z > shark_state[shark_map[r][c]].z:
                shark_state[shark_map[r][c]].dead = True
                shark_map[r][c] = shark.idx

            else:
                shark.dead = True

def getShark(idx):
    global shark_map
    global answer
    for r in range(R):
        if shark_map[r][idx] != -1:
            answer += shark_state[shark_map[r][idx]].z
            shark_state[shark_map[r][idx]].dead = True
            shark_map[r][idx]= -1
            break

for c in range(C):
    getShark(c)
    for shark in shark_state.values():
        if not shark.dead:
            moveShark(shark)
    eatShark()
print(answer)