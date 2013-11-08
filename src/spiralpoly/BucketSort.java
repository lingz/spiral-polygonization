package spiralpoly;
/*
 * @author Abhijai Garg
 * 
 */


public class BucketSort{

    public static LinkedList sort(LinkedList unsortedList){
    	
    	double maxVal = findMax(unsortedList);
    	
    	int [] bucket=new int[convertfromDec(maxVal)+1];
        
        for (int i=0; i<bucket.length; i++){
            bucket[i]=0;
        }
        
        LinkedListItr elt = unsortedList.first();
        
        while(elt.current.next!=null){
        	bucket[convertfromDec(elt.retrieve( )[0])]++;
        	elt.advance();
        }
    
        LinkedList sorted = new LinkedList();
        
        for(int i=0; i < bucket.length; i++){
        	//assuming a normal arrangement of points, where not more than 2 points have the same 
        	// x or y axes individually
        	
        	for(int j=0; j < bucket[i];j++){
        		double[] nodeElement = getNode(convertfromInt(i), unsortedList);
        		ListNode node = new ListNode(nodeElement);
        		sorted.append(node);
        	}
        }
        //sorted.printList(sorted);
        LinkedList finalList = new LinkedList();
        finalList = sortY(sorted);
        return finalList;
    }
  
    
    public static double[] getNode(double x, LinkedList list){
    	LinkedListItr elt = list.first();
    	do{
    		if(convertfromInt(convertfromDec(elt.retrieve()[0]))==x){
    			double[] node = {x,elt.retrieve()[1]};
    			list.remove(node);
    			return node;
    		}
    		elt.advance();
    	}while(elt.current.next!=null);
    	return null;
    }
    
    public static LinkedList sortY(LinkedList data){
    	LinkedListItr itr = data.first();
    	while(itr.current.next.next !=null){
    		if(itr.current.element[0]==itr.current.next.element[0]){
    			if(itr.current.element[1]>itr.current.next.element[1]){
    				double temp = itr.current.element[1];
    				itr.current.element[1] = itr.current.next.element[1];
    				itr.current.next.element[1] = temp;
    			}
    		}
    		itr.advance();
    	}
    	return data;
    }
    
    /**
	 * 
	 * @param x the x coordinate of point to be searched in set of points
	 * @param a the linkedlist with original values
	 * @return an array of the y coordinates of the the corresponding x value
	 */
    
    public static int convertfromDec(double elt){//decimal number between 0 and 1
    	double coord = elt;
    	int val = (int)(coord*1000);
    	return val;
    	//to return first three digits of the decimal
    }
    
    public static double convertfromInt(int elt){
    	int coord = elt;
    	double val = (double)(coord/1000.0);
    	return val;
    }
    
    public static void print(int[] bucket){
    	for (int i = 0; i <= bucket.length-1; i++)
            System.out.print(bucket[i] + " ");
    	System.out.println();
    }
    
    public static double findMax(LinkedList val) {
 	   LinkedListItr Node = val.first();
 	   double max = Node.retrieve()[0];
 	   while(Node.current.next!=null){
 		   if(Node.retrieve()[0]>max) max=Node.retrieve()[0];
 		   Node.advance();
 	   }
 	  return max;
 	}
    
    public static void main(String[] args){
    	//unsortedList is the input LinkedList
    	LinkedList data = new LinkedList();
    	LinkedListItr p = data.zeroth();
    	
    	double[] x={0.2313,0.281};
    	data.insert(x,p);
    	p.advance();
    	double[] y={0.4214,0.291};
    	data.insert(y,p);
    	p.advance();
    	double[] a={0.4214,0.191};
    	data.insert(a,p);
    	p.advance();
    	double[] z={0.2315,0.261};
    	data.insert(z,p);
    	p.advance();
    	double[] w={0.8312,0.231};
    	data.insert(w,p);
    	p.advance();
    	double[] v={0.9913,0.786};
    	data.insert(v,p);
    	p.advance();
    	// dummy data
    	
    	LinkedList.printList(data);
    	
        LinkedList sortedList = sort(data);
        
        LinkedList.printList(sortedList);
        
    }

}