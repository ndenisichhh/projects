from random import randint as rd
import time
import os

def horse_racing(b, horse):
    bet = b
    money = 0
    k = horse

    track = [
        ['[1]', '', '', '', '', '', ''],
        ['[2]', '', '', '', '', '', ''],
        ['[3]', '', '', '', '', '', ''],
        ['[4]', '', '', '', '', '', ''],
        ['[5]', '', '', '', '', '', ''],
        ['[6]', '', '', '', '', '', ''],
        ['[7]', '', '', '', '', '', ''],
    ]
    everyTrack = [[], [], [], [], [], [], []]

    def move():
        for line in track:
            for i in range(len(line)):
                if line[i] != '':
                    r = rd(1, 3)
                    if (r == 1) and (i != len(line)-1):
                        line[i], line[i+1] = line[i+1], line[i]
                    break

    while (track[0][6] == '') and (track[1][6] == '') and (track[2][6] == '') and (track[3][6] == '') and (track[4][6] == '') and (track[5][6] == '') and (track[6][6] == ''):
        for i in range(len(everyTrack)):
            everyTrack[i].append('')
            everyTrack[i][len(everyTrack[i])-1] = track[i].copy()
        move()
    for i in range(len(everyTrack)):
            everyTrack[i].append('')
            everyTrack[i][len(everyTrack[i])-1] = track[i].copy()

    def winners(arr):
        a = []
        wins = []
        for line in arr:
            a.append(line[len(line)-1])
        a = ''.join(a)
        a = a.replace('[', '').replace(']', '')
        for i in a:
            wins.append(i)
        return wins

    if k in winners(track):
        money += bet * len(winners(track))

    return everyTrack, winners(track), round(money, 2)

# tr, w, m = horse_racing(1000, '2')
# b = ''
# for i in range(len(tr[0])):
#     out = ['', '', '', '', '', '', '']
#     for j in range(len(tr)):
#         out[j] = f'|{tr[j][i]}|'
#     b = out[0] + '\n' + out[1] + '\n' + out[2] + '\n' + out[3] + '\n' + out[4] + '\n' + out[5] + '\n' + out[6] + '\n'
#     print(b)
#     time.sleep(1)
#     os.system('CLS')