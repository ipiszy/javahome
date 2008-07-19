import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Test {
	public static void main(String[] args) throws IOException{
		InputStreamReader input1=new InputStreamReader(System.in);
		BufferedReader temp1=new BufferedReader(input1);
		InputStreamReader input2=new FileReader("wahahaha.txt");
		BufferedReader temp2=new BufferedReader(input2);
		
		String stringTemp1=temp1.readLine();
		String stringTemp2=temp2.readLine();
		
		
	}
}
