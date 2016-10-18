# -*- coding: utf-8 -*-
"""
Created on Mon Sep 12 22:08:46 2016

@author: Jack aka Hrishikesh 

    CODE OPTIMIZATION
"""

#FOUR TUPLE LIST REPRESENTS THE TAC IN FOURTUPLE FORM AND STORES EACH TUPLE
fourtuplelist=[]
atuple=[]
deadcode=[]


#check if the other tup is similar to atup

def checkifsame(atup,othertup):
    if atup[1]==othertup[1]:
        if atup[1]=='-' or atup[1]=='/':
            for it in range(1,4):
                if atup[it]!=othertup[it]:
                    return False
            return True
        elif atup[1]=='+' or '*':
            if (atup[2]==othertup[2] and atup[3]==othertup[3]):
                return True
            elif (atup[2]==othertup[3] and atup[3]==othertup[2]):
                return True
        else:
            return False




nooftuples=0
nooftuples=input("Enter no. of tuples\n")
#print nooftuples
print '\nEnter the Four Tuple representation of TAC ie. Operator , argument1, argument2, result'
for i in range(nooftuples):
    atuple=[]
    atuple.append(i)
    #print '\nEnter tuple '+str(i)

    tuplestring=input().split(' ')
    
    for element in tuplestring:
        atuple.append(element)
    
    fourtuplelist.append(atuple)

print '\nUnoptimized Four Tuple Reprersentation'
for tup in fourtuplelist:
    print tup

for atup in fourtuplelist:
  for othertup in fourtuplelist:
      #checking if other tuples are same as the atup
      #and performing optimizations accordingly
      if atup[0]!=othertup[0]:
          same=checkifsame(atup,othertup)
          if(same):
              #print 'found same at index '+str(othertup[0])
              #print othertup
              deadcode.append(othertup)
              trueparameter=atup[4]
              changeparameter=othertup[4]
              print '\nRedundant Code Identified on line/tuple '+str(othertup[0])
              print 'Checking for dependencies and correcting...'
              for consecutivetup in fourtuplelist:
                  if consecutivetup[0]>othertup[0]:
                      for no,parameter in enumerate(consecutivetup):
                          if parameter==changeparameter:
                              print '\nFound a parameter from redundant code at tuple '+str(consecutivetup[0])
                              consecutivetup[no]=trueparameter
                              print 'Changing '+str(parameter)+' to '+str(consecutivetup[no])
               
              fourtuplelist.remove(othertup)
        
for atup in fourtuplelist:
    for i,term in enumerate(atup):
        if term == 'pi':
            atup[i]=3.14
        
        
           
              

print '\n'              

print 'Four Tuple Representation After Code Optimization\n'
for tup in fourtuplelist:
    print tup                        
                  
              
        


        
            
                
                    
                    