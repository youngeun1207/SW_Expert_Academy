# 프로그래머스 Lv.3 네트워크
# BFS로 edge로 이어진 node set 구하는 문제

def solution(n, computers):
    answer = 0

    queue = [] # BFS 사용할 것이라 queue
    visited = [False]*n

    for a in range(n):
        if not visited[a]: # visited면 이미 set에 있는 것
            queue.append(a)
            answer += 1

            while queue : # BFS 돌면서 연결된 노드 set 첮기
                node = queue.pop(0)
                for i in range(n):
                    if computers[node][i] and not visited[i]:
                        visited[i] = True # 메모리 절약 위해 visited 체크를 queue에 넣을 때 수행
                        queue.append(i)
    return answer