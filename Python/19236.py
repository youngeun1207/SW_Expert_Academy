# 백준 19236 청소년 상어
# 삼성 SW 역량 테스트 기출
# DFS + 단순구현

import copy


class Fish:
    def __init__(self, r, c, dir):
        self.r = r
        self.c = c
        self.dir = dir
        self.dead = False

    def __str__(self): # 디버깅용 프린트문
        dirs = ['↑', '↖', '←', '↙', '↓', '↘', '→', '↗' ]
        return 'r:{} c:{} dir:{} dead:{}'.format(self.r, self.c, dirs[self.dir], self.dead)


fish_map = [] # 물고기 지도상에 저장
fish_state = {} # 물고기 위치와 방향 상태 저장
dirs_r = [-1, -1, 0, 1, 1, 1, 0, -1]
dirs_c = [0, -1, -1, -1, 0, 1, 1, 1]

answer = 0

for r in range(4):
    row = list(map(int, input().split()))
    fish_map.append([])
    for c in range(0, 8, 2):
        fish_state[row[c]] = Fish(r, c // 2, row[c + 1] - 1)
        fish_map[r].append(row[c])


def cannotMove(new_r, new_c, shark): # 물고기 움직일 수 없는지 확인
    # 맵을 벗어나거나, 상어가 있는 경우 움직일 수 없음
    if 0 <= new_r < 4 and 0 <= new_c < 4 and not (new_r == shark.r and new_c == shark.c):
        return False
    else:
        return True


def moveFish(shark, fish_map, fish_state):
    for i in range(1, 17): # 1~16번 물고기까지 순서대로 움직이기
        fish = fish_state[i]
        if fish.dead: # 잡아먹힌 물고기는 옮길 필요 없음
            continue

        new_r = fish.r + dirs_r[fish.dir]
        new_c = fish.c + dirs_c[fish.dir]

        while cannotMove(new_r, new_c, shark): # 움직일 수 없으면 반시계 회전
            fish.dir = (fish.dir + 1) % 8
            new_r = fish.r + dirs_r[fish.dir]
            new_c = fish.c + dirs_c[fish.dir]

        # 물고기 바꾸기
        # 물고기 지도와 물고기 상태 모두 변경해야함
        fish_map[fish.r][fish.c] = fish_map[new_r][new_c]
        fish_state[fish_map[new_r][new_c]].r = fish.r
        fish_state[fish_map[new_r][new_c]].c = fish.c

        fish_map[new_r][new_c] = i
        fish.r = new_r
        fish.c = new_c


def DFS(eat, r, c, fish_map, fish_state): # 먹은 물고기 점수, 상어 위치 r, 상어 위치 c, 물고기 저장된 배열
    fish_state[fish_map[r][c]].dead = True
    eat += fish_map[r][c]

    global answer
    answer = max(eat, answer) # 현재 상태가 먹을 수 있는 최대 값이면 결과 갱신

    shark = Fish(r, c, fish_state[fish_map[r][c]].dir)
    moveFish(copy.deepcopy(shark), fish_map, fish_state) # 상어 객체이므로 순회하면서 내부 바뀌지 않도록 copy해서 넘김

    for i in range(1, 4): # 방향만 맞으면 여러 칸 한번에 이동할 수 있음
        new_r = r + (dirs_r[shark.dir])*i
        new_c = c + (dirs_c[shark.dir])*i

        if 0 <= new_r < 4 and 0 <= new_c < 4 and not fish_state[fish_map[new_r][new_c]].dead:
            # 상태랑 물고기 지도 또한 객체이므로 내부 바뀌지 않도록 copy해서 넘김
            copy_state = copy.deepcopy(fish_state)
            copy_map = copy.deepcopy(fish_map)

            DFS(eat, new_r, new_c, copy_map, copy_state) # DFS 순회하면서 최적의 값 구하기

DFS(0, 0, 0, fish_map, fish_state) # 처음에는 0, 0에 상어 위치

print(answer)
