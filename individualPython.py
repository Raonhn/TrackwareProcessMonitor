import psutil
mem = psutil.virtual_memory()[2]
print(mem)