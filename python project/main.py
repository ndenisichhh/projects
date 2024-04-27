import sqlite3
import discord
from discord.ui import Button, View
from discord.ext import commands
from config import settings
from firstR import gspin
from horse import horse_racing
from jng import jng
from random import randint as rd
import asyncio

intents = discord.Intents.all()
intents.message_content = True
intents.members = True
bot = commands.Bot(command_prefix = settings['prefix'], intents=intents)
client = commands.Bot(command_prefix = settings['prefix'], intents=intents)

conn = sqlite3.connect("Discord.db")
cursor = conn.cursor()
cursor.execute("""CREATE TABLE IF NOT EXISTS users (
        name TEXT,
        id INT,
        money FLOAT
)""")

@bot.event
async def on_ready():
    f = open('history.txt', 'r+')
    for guild in bot.guilds:
        for member in guild.members:
            if cursor.execute(f"SELECT id FROM users WHERE id = {member.id}").fetchone() is None:
                cursor.execute(f"INSERT INTO users VALUES ('{member}', {member.id}, 1000)")
                f.write(f"{member.id} 1000\n")
            else:
                f = open('history.txt', 'r+')
                for line in f:
                    if str(member.id) in line:
                        cursor.execute(f"UPDATE users SET money = {int(line.split()[1])} WHERE id = {member.id}")
    conn.commit()
    f.close()
@bot.event
async def on_member_join(member):
    if cursor.execute(f"SELECT id from users WHERE id = {member.id}").fetchone() is None:
        cursor.execute(f"INSERT INTO users VALUES ('{member}', {member.id}, 0)")
        with open("history.txt", "r") as f:
            lines = f.readlines()
        with open("history.txt", "w") as f:
            for line in lines:
                f.write(line)
            f.write(f"{member.id} 0\n")
        conn.commit()
@bot.command()
async def check_users(ctx):
    for value in cursor.execute("SELECT * FROM users"):
        print(value)

@client.event
async def on_command_error(ctx, error):
    pass

def nspin(st):
    a = gspin(st)
    b = '**Выпало:**\n\n'
    c = ''
    for i in a[0]:
        sr = ' '.join(map(str, i)) + '\n'
        sr = sr.replace('W', 'Ⓦ')
        sr = sr.replace('0', 'Ⓙ')
        sr = sr.replace('1', 'Ⓠ')
        sr = sr.replace('2', 'Ⓚ')
        sr = sr.replace('3', 'Ⓐ')
        sr = sr.replace('4', 'Ⓣ')
        sr = sr.replace('5', 'Ⓤ')
        sr = sr.replace('6', 'Ⓥ')
        sr = sr.replace('7', 'Ⓧ')
        sr = sr.replace('8', 'Ⓨ')
        sr = sr.replace('9', 'Ⓩ')
        b += sr
    for i in a[2]:
        sr = ''.join(map(str, i))
        if a[2].index(i) != (len(a[2])):
            sr += ' | '
        sr = sr.replace('W', 'Ⓦ')
        sr = sr.replace('0', 'Ⓙ')
        sr = sr.replace('1', 'Ⓠ')
        sr = sr.replace('2', 'Ⓚ')
        sr = sr.replace('3', 'Ⓐ')
        sr = sr.replace('4', 'Ⓣ')
        sr = sr.replace('5', 'Ⓤ')
        sr = sr.replace('6', 'Ⓥ')
        sr = sr.replace('7', 'Ⓧ')
        sr = sr.replace('8', 'Ⓨ')
        sr = sr.replace('9', 'Ⓩ')
        if sr == ' | ' or sr == '':
            sr = ' - '
        c += sr
    b += '**'
    b += '\n' + 'WIN: ' + str(a[1]) + '\n' + c
    b += '**'

    return a, b

@bot.command()
async def spin(ctx, st: str):
    user_id = ctx.author.id
    for value in cursor.execute(f"SELECT * FROM users WHERE id = {user_id}"):
        money = value[2]
        
    if st == 'all' and money > 0:
        st = money
    elif st == 'all' and money == 0:
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ У вас нет денег :(**', color=15548997))
    elif int(st) > 0:
        st = int(st)
    elif st.isalpha():
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ Ставка должна быть числом!**', color=15548997))
    else:
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ Ставка должна быть выше 0!**', color=15548997))

    if int(st) <= money:
        f = open('history.txt', 'r+')
        for line in f:
            if str(user_id) in line:
                cursor.execute(f"UPDATE users SET money = money - {st} WHERE id = {user_id}")
        f.close()

        with open("history.txt", "r") as f:
            lines = f.readlines()
        with open("history.txt", "w") as f:
            for line in lines:
                if f'{user_id}' in line:
                    a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) - int(st)}')
                    f.write(f'{user_id}' + ' ' + str(a) + '\n')
                else:
                    f.write(line)

        button = Button(label="Spin", style=discord.ButtonStyle.green)
        view = View()
        view.add_item(button)
        async def button_callback(interaction):
            await interaction.response.defer()
            for value in cursor.execute(f"SELECT * FROM users WHERE id = {user_id}"):
                money = value[2]
            if int(st) <= money:
                f = open('history.txt', 'r+')
                for line in f:
                    if str(user_id) in line:
                        cursor.execute(f"UPDATE users SET money = money - {st} WHERE id = {user_id}")
                f.close()

                with open("history.txt", "r") as f:
                    lines = f.readlines()
                with open("history.txt", "w") as f:
                    for line in lines:
                        if f'{user_id}' in line:
                            a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) - int(st)}')
                            f.write(f'{user_id}' + ' ' + str(a) + '\n')
                        else:
                            f.write(line)

                a, b = nspin(st)
                f = open('history.txt', 'r+')
                for line in f:
                    if str(user_id) in line:
                        cursor.execute(f"UPDATE users SET money = money + {a[1]} WHERE id = {user_id}")
                f.close()
                with open("history.txt", "r") as f:
                    lines = f.readlines()
                with open("history.txt", "w") as f:
                    for line in lines:
                        if f'{user_id}' in line:
                            a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) + int(a[1])}')
                            f.write(f'{user_id}' + ' ' + str(a) + '\n')
                        else:
                            f.write(line)

                await msg.edit(embed = discord.Embed(title = b, color=5763719), view=view)
            else:
                await ctx.channel.purge(limit=1)
                await ctx.send(embed = discord.Embed(title = f'**{ctx.author.name}, \n❌ У вас недостаточно денег для этой ставки. В настоящее время у вас есть {money}.**', color=15548997))
        button.callback = button_callback

        a, b = nspin(st)

        f = open('history.txt', 'r+')
        for line in f:
            if str(user_id) in line:
                cursor.execute(f"UPDATE users SET money = money + {a[1]} WHERE id = {user_id}")
        f.close()
        with open("history.txt", "r") as f:
            lines = f.readlines()
        with open("history.txt", "w") as f:
            for line in lines:
                if f'{user_id}' in line:
                    a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) + int(a[1])}')
                    f.write(f'{user_id}' + ' ' + str(a) + '\n')
                else:
                    f.write(line)

        msg = await ctx.send(embed = discord.Embed(title = b, color=5763719), view=view)
    else:
        await ctx.send(embed = discord.Embed(title = f'**{ctx.author.name}, \n❌ У вас недостаточно денег для этой ставки. В настоящее время у вас есть {money}.**', color=15548997))

@bot.command()
async def racing(ctx, bet: str, number: str):
        if (number not in "1234567"):
            await ctx.send(embed = discord.Embed(title = f"**{ctx.author.name}, \n❌ Номер лошади должен быть целым числом от 1 до 7**", color=15548997))
        else:
            user_id = ctx.author.id
            for value in cursor.execute(f"SELECT * FROM users WHERE id = {user_id}"):
                money = value[2]
                
            if bet == 'all' and money > 0:
                bet = money
            elif bet == 'all' and money == 0:
                await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ У вас нет денег :(**', color=15548997))
            elif int(bet) > 0:
                bet = int(bet)
            elif bet.isalpha():
                await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ Ставка должна быть числом!**', color=15548997))
            else:
                await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ Ставка должна быть выше 0!**', color=15548997))

            if int(bet) <= money:
                f = open('history.txt', 'r+')
                for line in f:
                    if str(user_id) in line:
                        cursor.execute(f"UPDATE users SET money = money - {bet} WHERE id = {user_id}")
                f.close()

                with open("history.txt", "r") as f:
                    lines = f.readlines()
                with open("history.txt", "w") as f:
                    for line in lines:
                        if f'{user_id}' in line:
                            a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) - int(bet)}')
                            f.write(f'{user_id}' + ' ' + str(a) + '\n')
                        else:
                            f.write(line)

                tr, w, m = horse_racing(int(bet), number)
                b = ''
                msg = await ctx.send(embed = discord.Embed(title = "**Let's go**", color=5763719))
                await asyncio.sleep(0.3)
                for i in range(len(tr[0])):
                    out = ['', '', '', '', '', '', '']
                    for j in range(len(tr)):
                        out[j] = f'|{tr[j][i]}|'
                    b = out[0] + '\n' + out[1] + '\n' + out[2] + '\n' + out[3] + '\n' + out[4] + '\n' + out[5] + '\n' + out[6] + '\n'
                    b = b.replace('1', '①').replace('2', '②').replace('3', '③').replace('4', '④').replace('5', '⑤').replace('6', '⑥').replace('7', '⑦')
                    await msg.edit(embed = discord.Embed(title = b, color=5763719))
                    await asyncio.sleep(1)

                if number in w:
                    b += '\n' + 'YOU WON: ' + str(m)
                    color = 5763719
                else:
                    b += '\n' + 'YOU LOSE'
                    color = 15548997

                f = open('history.txt', 'r+')
                for line in f:
                    if str(user_id) in line:
                        cursor.execute(f"UPDATE users SET money = money + {m} WHERE id = {user_id}")
                f.close()
                with open("history.txt", "r") as f:
                    lines = f.readlines()
                with open("history.txt", "w") as f:
                    for line in lines:
                        if f'{user_id}' in line:
                            a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) + int(m)}')
                            f.write(f'{user_id}' + ' ' + str(a) + '\n')
                        else:
                            f.write(line)
                await msg.edit(embed = discord.Embed(title = b, color=color))

@bot.command()
async def jango(ctx, bet: str):
    user_id = ctx.author.id
    for value in cursor.execute(f"SELECT * FROM users WHERE id = {user_id}"):
        money = value[2]
        
    if bet == 'all' and money > 0:
        bet = money
    elif bet == 'all' and money == 0:
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ У вас нет денег :(**', color=15548997))
    elif int(bet) > 0:
        bet = int(bet)
    elif bet.isalpha():
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ Ставка должна быть числом!**', color=15548997))
    else:
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌ Ставка должна быть выше 0!**', color=15548997))

    if int(bet) <= money:
        f = open('history.txt', 'r+')
        for line in f:
            if str(user_id) in line:
                cursor.execute(f"UPDATE users SET money = money - {bet} WHERE id = {user_id}")
        f.close()

        with open("history.txt", "r") as f:
            lines = f.readlines()
        with open("history.txt", "w") as f:
            for line in lines:
                if f'{user_id}' in line:
                    a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) - int(bet)}')
                    f.write(f'{user_id}' + ' ' + str(a) + '\n')
                else:
                    f.write(line)

        field, money = jng(int(bet))
        b = ""
        for line in field[0]:
            for item in line:
                b += item + " "
            b = b.replace('1', '①').replace('2', '②').replace('3', '③').replace('4', '④').replace('5', '⑤').replace('6', '⑥')
            b += "\n"
        
        msg = await ctx.send(embed = discord.Embed(title = b, color=5763719))
        await asyncio.sleep(1)
        if len(field) > 1:
            for i in range(1, len(field)):
                b = ""
                for line in field[i]:
                    for item in line:
                        b += item + " "
                    b = b.replace('0', '⓪').replace('1', '①').replace('2', '②').replace('3', '③').replace('4', '④').replace('5', '⑤').replace('6', '⑥')
                    b += "\n"
                await msg.edit(embed = discord.Embed(title = b, color=5763719))
                await asyncio.sleep(1)
        b += '\n' + 'YOU WON: ' + str(money)
        f = open('history.txt', 'r+')
        for line in f:
            if str(user_id) in line:
                cursor.execute(f"UPDATE users SET money = money + {money} WHERE id = {user_id}")
        f.close()
        with open("history.txt", "r") as f:
            lines = f.readlines()
        with open("history.txt", "w") as f:
            for line in lines:
                if f'{user_id}' in line:
                    a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) + int(money)}')
                    f.write(f'{user_id}' + ' ' + str(a) + '\n')
                else:
                    f.write(line)
        await msg.edit(embed = discord.Embed(title = b, color=5763719))

@bot.command()
async def balance(ctx, user):
    f = open('history.txt', 'r+')
    user_id = user[2:-1]
    for line in f:
        if str(user_id) in line:
            cursor.execute(f"UPDATE users SET money = {int(line.split()[1])} WHERE id = {user_id}")
    for value in cursor.execute(f"SELECT * FROM users WHERE id = {user_id}"):
        name = value[0]
        if name[:-5] == ctx.author.name:
            await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \nУ вас: {value[2]}**', color=3447003))
        else:
            await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \nУ пользователя {name[:-5]}: {value[2]}**', color=3447003))
    f.close()

@bot.command()
@commands.has_permissions(administrator = True)
async def add_money(ctx, sum: str, user):
    f = open('history.txt', 'r+')
    user_id = user[2:-1]
    if int(sum) > 0:
        await ctx.send(embed = discord.Embed(description = f'**{user}, \n✅ Боги подарили вам {sum} монет.**', color=5763719))
    elif int(sum) < 0:
        await ctx.send(embed = discord.Embed(description = f'**{user}, \n✅ Боги забрали у вас {int(sum)*-1} монет.**', color=5763719))

    for line in f:
        if str(user_id) in line:
            cursor.execute(f"UPDATE users SET money = {int(line.split()[1]) + int(sum)} WHERE id = {user_id}")
            f.close()
            with open("history.txt", "r") as f:
                lines = f.readlines()
            with open("history.txt", "w") as f:
                for line in lines:
                    if f'{user_id}' in line:
                        a = line.split()[1].replace(f'{line.split()[1]}', f'{int(line.split()[1]) + int(sum)}')
                        f.write(f'{user_id}' + ' ' + str(a) + '\n')
                    else:
                        f.write(line)

@bot.command()
async def help_casino(ctx):
    help = '!spin <bet> - крутить слот по ставке bet\n!balance <user> - узнать баланс пользователя (если не указать пользователя, укажет ваш баланс)\n'
    rules = ''
    await ctx.send(embed = discord.Embed(description = f'**{help}\n{rules}**', color=15844367))

@bot.command()
async def test(ctx):
    
    button = Button(label="test", style=discord.ButtonStyle.green)
    view = View()
    view.add_item(button)
    async def button_callback(interaction):
        await interaction.response.defer()
        await msg.edit(embed=discord.Embed(title=f'**Привет {rd(0, 1000)}**', color=15844367), view=view)
    button.callback = button_callback
    msg = await ctx.send(embed=discord.Embed(title=f'**Привет**', color=15844367), view=view)

@balance.error
async def balance_error(ctx, error):
    f = open('history.txt', 'r+')
    for line in f:
        if str(ctx.author.id) in line:
            cursor.execute(f"UPDATE users SET money = {int(line.split()[1])} WHERE id = {ctx.author.id}")
    if isinstance(error, commands.MissingRequiredArgument ):
        for value in cursor.execute(f"SELECT * FROM users WHERE id = {ctx.author.id}"):
            await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \nУ вас: {value[2]}**', color=3447003))

@spin.error
async def spin_error(ctx, error):
    sp = '```!spin <bet>```'
    if isinstance(error, commands.MissingRequiredArgument ):
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌  Приведено слишком мало аргументов.**\n\n**Применение:**\n{sp}', color=15548997))

@racing.error
async def racing_error(ctx, error):
    sp = '```!racing <bet> <number>```'
    if isinstance(error, commands.MissingRequiredArgument ):
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌  Приведено слишком мало аргументов.**\n\n**Применение:**\n{sp}', color=15548997))

@add_money.error
async def add_money_error(ctx, error):
    sp = '```!add_money <money> <user>```'
    if isinstance(error, commands.MissingRequiredArgument ):
        await ctx.send(embed = discord.Embed(description = f'**{ctx.author.name}, \n❌  Приведено слишком мало аргументов.**\n\n**Применение:**\n{sp}', color=15548997))

bot.run(settings['token'])