import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Method;


class WriteThread extends Thread{
	public PipedOutputStream out;
	public void run() {
		try {
			int counter=0;
			while(true)
			{
			Thread.sleep(1000);
			out.write(counter++);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("wahahaha");
		
	}
}
class ReadThread extends Thread{
	public PipedInputStream in;
	public void run()
	{
		while(true)
			try {
				System.out.println(in.read());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
class Resource
{
	void exec(int id)
	{
		System.out.println(id+"enter");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id+"exit");
	}
}
class SimpleThread extends Thread
{
	public int id;
	public Resource r=new Resource();
	public void run()
	{
		while(true)
		{
			r.exec(id);
		}
	}
}
public class MyThread {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
//		PipedInputStream in=new PipedInputStream();
//		PipedOutputStream out=new PipedOutputStream(in);
//		WriteThread w=new WriteThread();
//		ReadThread r=new ReadThread();
//		w.out=out;
//		r.in=in;
//		w.start();
//		r.start();
//		SimpleThread t1=new SimpleThread();
//		SimpleThread t2=new SimpleThread();
//		Resource r=new Resource();
//		t1.r=r;
//		t2.r=r;
//		t1.id=1;
//		t2.id=2;
//		t1.start();
//		t2.start();
//		Method[] methods=DianPingWang.class.getMethods();
//		for(Method m:methods)
//		{
//			System.out.println(m);
//		}
		Object s=new String();
		Object s1=new StringBuffer();
		System.out.println(s instanceof StringBuffer);
		s=Class.forName("DianPingWang").newInstance();
		Class classInfo=s.getClass();
		System.out.println(classInfo.getName());
	}

}
