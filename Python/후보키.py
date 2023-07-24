from itertools import combinations
# 프로그래머스 Lv.2 후보키
# 2019 KAKAO BLIND RECRUITMENT
# 4.00ms, 10.2MB
# 단순 구현 문제
def solution(relation):
    answer = 0
    l = list(range(len(relation[0])))
    ck_list = []
    for i in range(len(relation[0])):
        for ck_idx in combinations(l, i+1): # 조합 사용
            isDuplicate = False
            for ck in ck_list:
                if len(set(ck + list(ck_idx))) == i+1: # 최소성 만족을 위해 이미 CK로 판명 된 인덱스를 포함하고 있는 경우는 제외
                    isDuplicate = True
                if isDuplicate:
                    break
            if isDuplicate:
                continue
            ck_col = []
            for j in range(len(relation)):
                ck_str = ""
                for k in range(i+1): # value를 합친 문자열로 바꿔서
                    ck_str += ' '
                    ck_str += relation[j][ck_idx[k]]
                ck_col.append(ck_str)
            if len(relation) == len(set(ck_col)): # 겹치는지 set으로 확인한 후, 안 겹치면 CK 충족
                ck_list.append(list(ck_idx)) # CK인 경우, 최소성 체크를 위해 CK 충족하는 리스트 따로 저장
    return len(ck_list)