# 백준 16236 아기상어
import heapq
from collections import deque

N = int(input())

ocean = []
shark = (0, 0)
size = 2
queue = deque([])
dirs = [(-1, 0), (0, -1), (0, 1), (1, 0)]
answer = 0


def BFS():
    ate = [] # 먹을 수 있는 물고기 모음
    visited = [[False] * N for _ in range(N)]

    while queue:
        time, now_y, now_x = queue.popleft()

        visited[now_y][now_x] = True

        for dir_y, dir_x in dirs:
            y = now_y + dir_y
            x = now_x + dir_x
            if 0 <= y < N and 0 <= x < N and not visited[y][x]:
                if ocean[y][x] == 0 or ocean[y][x] == size: # 이동할 수 있음
                    queue.append([time + 1, y, x])
                elif ocean[y][x] < size: # 먹을 수 있음
                    ate.append([time+1, y, x]) # 우선순위: 이동시간 >> 위->아래 >> 왼->오른
    if ate:
        heapq.heapify(ate)
        min = heapq.heappop(ate)
        ocean[min[1]][min[2]] = 0
        return min[0], (min[1], min[2]) # 이동 시간, 현재 위치 출력
    return False, -1  # 먹을 수 있는 물고기가 없음 -> 엄마 불러


for i in range(N):
    fish_row = list(map(int, input().split()))
    ocean.append(fish_row)

    for j in range(N):
        fish = fish_row[j]
        if fish == 9:
            shark = (i, j)
            ocean[i][j] = 0

cnt = 0  # 물고기 몇마리 먹었는지 저장 -> 현재 크키만큼 먹으면 0으로 리셋하고 상어 크기 ++

while True:
    queue = deque([[0, shark[0], shark[1]]])
    add_time, location = BFS()
    if add_time > 0:
        cnt += 1
        answer += add_time
        shark = location

        if cnt == size:
            size += 1
            cnt = 0
    else:
        break
print(answer)
