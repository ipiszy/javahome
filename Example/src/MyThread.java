
class MoreThread extends Thread{
	public MoreThread(){System.out.println("wahahaha");}
}
public class MyThread {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		MoreThread.sleep(1000);
		MoreThread more=new MoreThread();
		System.out.println("main  ");

	}

}
