import random
#initial values
number=10


f=open("test","w")
ver=0
con=0
random.seed(5)
vlist=[]
ver_list=[]
for i in range(1,number+1):
	ver_list.append(chr(96+i))
print ver_list
how_many=random.random()*number
how_many=int(how_many-how_many%1+number)
for i in range(1,how_many):
	chosen1=random.random()*len(ver_list)
	chosen1=int(chosen1-chosen1%1)
	chosen2=random.random()*len(ver_list)
	chosen2=int(chosen2-chosen2%1)
	vlist.append([ver_list[chosen1],ver_list[chosen2],int(1+random.random()*20)])
for i in range(1,len(vlist)):
	f.write(str(vlist[i])+"\n")
f.close()
