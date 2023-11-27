import psutil
usada = psutil.virtual_memory().used
total = psutil.virtual_memory().total
mem = (usada / total) * 100
print(mem)