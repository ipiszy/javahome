
public interface Runner {
	void run();
	void sleep();
	int java=2;
}
abstract class Pig implements Runner{
	void hahahaha(){
		System.out.print("SB");
	}
	
	public static void main(String[] args){
		Pig pig;
		AD Azureday;
		Azureday=new AD();
		pig=Azureday;
		
		pig.run();
		Azureday.hahahaha();
		System.out.print(java);
	}
	
}

class AD extends Pig{
	public void run(){
		System.out.print("run!");
		
	}
	public void sleep(){
		System.out.print("sleep");
	}
	void hahahaha(){
		System.out.print("hahahaha");
	}
	
	
}