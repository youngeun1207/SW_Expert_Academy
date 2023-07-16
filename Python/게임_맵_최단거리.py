# 프로그래머스 Lv.2 게임 맵 최단거리
# BFS 활용하는 문제
# 해당 노드까지 도달하는데 걸린 이동거리 저장하는 딕셔너리 distance로 거리 계산함
# 특정 노드에 도달하면 그 직전 노드의 distance + 1을 distance 딕셔너리에 저장

from collections import deque
def solution(maps):
    col, low = len(maps), len(maps[0])
    visited = [[False for _ in range(low)] for _ in range(col)] # 방문했던 노드인지 확인하기 위한 배열
    return BFS(maps, 0, 0, visited)

def BFS(maps, x, y, visited):
    col, low = len(maps), len(maps[0])
    queue = deque([(x, y)])
    visited[x][y] = True
    # distance -> 해당 노드까지 갈 때까지 이동한 거리 저장하는 딕셔너리
    distance = {(x, y): 1}  # 시작한 칸도 이동 거리에 포함
    dx = [-1, 1, 0, 0]
    dy = [0, 0, -1, 1]
    while queue:
        x, y = queue.popleft()
        for i in range(4):
            # 좌 우 위 아래 순으로 연산
            nx = x + dx[i]
            ny = y + dy[i]
            # 맵 넘어가지 않았으면서, 방문하지 않았으면서, 막힌 길이 아닐 경우
            if 0 <= nx < col and 0 <= ny < low and not visited[nx][ny] and maps[nx][ny]:
                if (nx, ny) == (col - 1, low - 1):
                    # 도착점에 도달!
                    return distance[(x, y)] + 1  # 도착 칸도 이동 거리에 포함
                queue.append((nx, ny))
                distance[(nx, ny)] = distance[(x, y)] + 1  # 직전 노드에서 1칸 더 이동
                visited[nx][ny] = True  # 방문 완료
    return -1 # 도달할 수 없음