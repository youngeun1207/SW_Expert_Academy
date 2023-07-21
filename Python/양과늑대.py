from collections import defaultdict as dd
def solution(info, edges):
    # DFS
    # 양: 0 늑대: 1
    # 양이 늑대보다 많아야 자식 노드로 이동
    
    graph = dd(list)
    visited = [False]*len(info) # 새로운 양 수집하면 visited 체크
    
    for edge in edges:
        graph[edge[0]].append(edge[1]) # 부모 노드 먼저 검증해서 자식 노드 검증 시간 줄이는 용
    
    answer = []
    visited[0] = True # root node: 0번 노드
    
    def DFS(node, sheep, wolf):
        if sheep > wolf: # 갈 수 있어
            answer.append(sheep)
        else: # 잡아먹힌다!
            return 
        
        for p_node, c_nodes in graph.items():
            if visited[p_node]: # 아까 도전했는데 안된 부분이면 재도전 하기 위해 부모 방문 여부 체크
                for c_node in c_nodes:
                    if not visited[c_node]:
                        visited[c_node] = True # 아래로 뻗어나갈 분기 포인트 체크
                        if info[c_node] == 0:
                            DFS(c_node, sheep+1, wolf)
                        else:
                            DFS(c_node, sheep, wolf+1)
                        visited[c_node] = False # 서브트리 다 순회했음
            
    DFS(0, 1, 0)
    
    return max(answer)
