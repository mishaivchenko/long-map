# long-map

Finish development of class LongMapImpl, which implements a map with keys of type long. It has to be a hash table (like HashMap). Requirements:
* it should not use any known Map implementations; 
* it should use as less memory as possible and have adequate performance;
* the main aim is to see your codestyle and teststyle 


#T[] values();

I managed to return the generalized array only with the help of reflection.  
In the original HashMap, this method returns a collection and therefore there are no problems with casting.