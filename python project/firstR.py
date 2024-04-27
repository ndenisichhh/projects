from random import randint as rd

def gspin(b):
    bet = b
    money = 0

    width = 6
    height = 5

    main = [[rd(0, 9) for x in range(height)] for i in range(width)]

    r = rd(1, 8)
    if r == 1:
        main[rd(0, width-1)][rd(0, height-1)] = 'W'
        
        r = rd(1, 5)
        if r == 1:
            rand1 = rd(0, width-1)
            rand2 = rd(0, height-1)
            while main[rand1][rand2] == 'W':
                rand1 = rd(0, width-1)
                rand2 = rd(0, height-1)
            main[rd(0, width-1)][rd(0, height-1)] = 'W'

            r = rd(1, 3)
            if r == 1:
                rand1 = rd(0, width-1)
                rand2 = rd(0, height-1)
                while main[rand1][rand2] == 'W':
                    rand1 = rd(0, width-1)
                    rand2 = rd(0, height-1)
                main[rd(0, width-1)][rd(0, height-1)] = 'W'
                
                r = rd(1, 3)
                if r == 1:
                    rand1 = rd(0, width-1)
                    rand2 = rd(0, height-1)
                    while main[rand1][rand2] == 'W':
                        rand1 = rd(0, width-1)
                        rand2 = rd(0, height-1)
                    main[rd(0, width-1)][rd(0, height-1)] = 'W'

    out = [[main[i][j] for i in range(width)] for j in range(height)]

    def ch(arr, i):
        a = []
        if ('W' in arr[i]) and (i != len(arr)-1):
            for j in arr[i+1]:
                a.append(j)
            if i != 0:
                for j in arr[i-1]:
                    a.append(j)
        if ('W' in arr[i]) and ('W' not in arr[i-1]) and (i != 0):
            for j in arr[i-1]:
                a.append(j)

        a = (''.join(str(x) for x in set(a))).replace('W', '')
        return a

    def ch2(arr, i):
        a = []
        if 'W' in arr[i]:
            for j in arr[i-1]:
                a.append(j)

        a = (''.join(str(x) for x in set(a))).replace('W', '')
        return a

    def nextW(i, a):
        x = i
        k = 0
        while (x+1 != len(main)) and ('W' in main[x]) and ('W' in main[x+1]):
            k+=1
            x+=1
        for j in range(i+k, i-1, -1):
            if j != len(main)-1:
                a += ch(main, j)
                a = (''.join(str(x) for x in set(a)))
                m = main[j].count('W')
                main[j] = (''.join(str(x) for x in main[j])).replace('W', '') + (a*m)
                main[j] = list(map(int, main[j]))

    def lastW(i, a):
        k = 0
        x = i
        while ('W' in main[x]) and ('W' in main[x-1]):
            k+=1
            x-=1
        a += ch2(main, i-k)
        a = (''.join(str(x) for x in set(a)))
        m = main[i].count('W')
        main[i] = (''.join(str(x) for x in main[i])).replace('W', '') + (a*m)
        main[i] = list(map(int, main[i]))

    lastW(len(main)-1, '')
    for i in range(len(main)-1):
        nextW(i, '')
    lastW(len(main)-1, '')

    stN = set(main[0]) & set(main[1]) & set(main[2])
    all = []
    all.append(stN)
    for n in range(3, width):
        stN = stN & set(main[n])
        all.append(stN)

    def search(n, dictionary):
        for a, b in dictionary.items():
            if a == n:
                return b

    if all[0]:
        for j in all[0]:
            k = 1
            rules = {0: 0.05*bet , 1: 0.05*bet , 2: 0.1*bet , 
                    3: 0.1*bet , 4: 0.15*bet , 5: 0.15*bet , 
                    6: 0.2*bet , 7: 0.35*bet , 8: 0.5*bet , 
                    9: 0.75*bet}

            if j in all[1]:
                rules = {0: 0.1*bet , 1: 0.1*bet , 2: 0.2*bet , 
                        3: 0.2*bet , 4: 0.4*bet , 5: 0.4*bet , 
                        6: 0.5*bet , 7: 0.75*bet , 8: 1.0*bet , 
                        9: 2.0*bet}
            if j in all[2]:
                rules = {0: 0.2*bet , 1: 0.2*bet , 2: 0.3*bet , 
                        3: 0.3*bet , 4: 0.5*bet , 5: 0.5*bet , 
                        6: 0.75*bet , 7: 1.0*bet , 8: 1.5*bet , 
                        9: 3.0*bet}
            if j in all[3]:
                rules = {0: 0.5*bet , 1: 0.5*bet , 2: 1.0*bet , 
                        3: 1.0*bet , 4: 1.5*bet , 5: 1.5*bet , 
                        6: 1.5*bet , 7: 2.0*bet , 8: 3.0*bet , 
                        9: 7.5*bet}
                        
            for i in main:
                if i.count(j) == 0:
                    break
                k *= i.count(j)
            money += search(j, rules) * k

    return out, round(money), all, bet