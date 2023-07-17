# 프로그래머스 Lv.2 올바른 괄호
# Stack 사용 문제
# '('면 스택에 넣기
# ')'면 스택에서 짝지을 '(' 찾기
# 스택 안에 짝지을 괄호가 없으면 False
# 연산 시간 단축을 위해 deque 사용

from collections import deque
def solution(s):
    answer = True
    stack = []
    s = deque(s)
    while s:
        p = s.popleft()
        if p == '(': # '('는 스택에 넣기
            stack.append('p')
        else: # ')'랑 짝지을 '('를 스택에서 꺼내기
            if not stack: # 스택에서 꺼낼 '(' 없음
                return False
            stack.pop()
    if stack:
        # '(' 스택에 남아있음
        return False
    return True