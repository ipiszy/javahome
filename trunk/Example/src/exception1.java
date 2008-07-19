public class exception1
{ static void Proc(int sel)  throws 
                          ArithmeticException,
                          ArrayIndexOutOfBoundsException
  {  
	System.out.println("In Situation"+sel);
    if (sel==0) {
    	System.out.println("no Exception caught");
        return;
        }
    else  if(sel==1) {int iArray[]=new int[4];
                     iArray[10]=3;
                     } 
  }
public static void main(String args[])
{  try {  Proc(0); 
             Proc(1);
   }catch(ArrayIndexOutOfBoundsException e){
        System.out.println("Catch "+e);
   }
}
}
