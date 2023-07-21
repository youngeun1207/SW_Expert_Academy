from collections import defaultdict as dd
def solution(info, edges):
    # DFS
    # 양: 0 늑대: 1
    # 양이 늑대보다 많아야 자식 노드로 이동
    
    graph = dd(list)
    visited = [False]*len(info)
    
    for edge in edges:
        graph[edge[0]].append(edge[1]) # 부모 노드 먼저 검증해서 자식 노드 검증 시간 줄이는 용
    
    answer = []
    visited[0] = True # root node: 0번 노드
    
    def DFS(sheep, wolf):
        if sheep > wolf: # 갈 수 있어
            answer.append(sheep)
        else: # 잡아먹힌다!
            return 
        
        for p_node, c_nodes in graph.items():
            if visited[p_node]: # 서브트리 순회 할 분기 포인트인지 확인
                for c_node in c_nodes:
                    if not visited[c_node]:
                        visited[c_node] = True # 아래로 뻗어나갈 분기 포인트 체크
                        if info[c_node] == 0:
                            DFS(sheep+1, wolf)
                        else:
                            DFS(sheep, wolf+1)
                        visited[c_node] = False # 서브트리 다 순회했음
            
    DFS(1, 0)
    
    return max(answer)
