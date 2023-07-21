from collections import deque, defaultdict as dd
def solution(n, s, a, b, fares):
    # 시작 지점에서 각 노드 간 최단거리 계산
    # 그 중 같이 타는 구간 제외
    
    # 그래프 딕셔너리 형태로 저장
    graph = dd(list)
    for fare in fares:
        # 양방향 간선 그래프 
        # 노드: (노드, cost)
        graph[fare[0]].append((fare[1], fare[2]))
        graph[fare[1]].append((fare[0], fare[2]))
    
    # BFS로 다익스트라 알고리즘 구현
    def BFS(s):
        INF = 10**6
        visited = [INF] * (n + 1)
        
        queue = deque()
        queue.append((s, 0)) # 노드, 최단거리
        
        visited[s] = 0 # 시작점은 거리 0

        while queue:
            p_node, p_dist = queue.popleft()
            for c_node, c_dist in graph[p_node]:
                dist = p_dist + c_dist # 시작노드 ~ 직전 노드 + 다음 노드 cost
                if visited[c_node] > dist: # 저장되어있는 최단거리보다 새로운 경로가 더 cost가 크면 확인할 필요 없음
                    visited[c_node] = dist
                    queue.append((c_node, dist))
        return visited # 시작 노드로부터 각 노드 간의 거리 저장한 리스트 반환
    
    s_dist = BFS(s) # 시작점부터 각 노드까지 최단거리
    answer = s_dist[a] + s_dist[b] # 각자 가는 경우
    
    for i in range(1, n+1):
        new_dist = BFS(i) # 새로운 시작 노드(분기점)로부터 각 노드까지 최단거리 계산
        # 분기점까지 cost + 분기점으로부터 각자 집까지의 cost
        answer = min(answer, s_dist[i] + new_dist[a] + new_dist[b]) # 기존 cost보다, 새로운 cost가 더 작으면 바꿔
    return answer
