import java.util.Arrays;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

class Instance implements Serializable{
	String name,address,phone,label;
	int taste,service,environment,average;
	boolean delete;
	
	Instance(){}
	Instance(String nameTemp, String addressTemp, String phoneTemp, String labelTemp, int tasteTemp, int serviceTemp, int environmentTemp, int averageTemp, boolean deleteTemp){
		name=nameTemp;
		address=addressTemp;
		phone=phoneTemp;
		label=labelTemp;
		taste=tasteTemp;
		service=serviceTemp;
		environment=environmentTemp;
		average=averageTemp;
	    delete=deleteTemp;	
	}
	
	public void writeObject(long pos,RandomAccessFile dianping) throws IOException{
		ByteArrayOutputStream byteArrayOut;
		ObjectOutputStream objectOut;
		byte[] byteTempOut;
		byte[] bytePosition=new byte[DianPingWang.SIZE];
		   
		byteArrayOut=new ByteArrayOutputStream();
		objectOut=new ObjectOutputStream(byteArrayOut);
		objectOut.writeObject(this);
		byteTempOut=byteArrayOut.toByteArray();
		dianping.seek(pos);
        dianping.write(bytePosition);
        dianping.seek(pos);
        dianping.write(byteTempOut);
		
	}
	
	public static Instance readObject(long pos, RandomAccessFile dianping) throws IOException, ClassNotFoundException{
	    byte[] byteTempIn=new byte[DianPingWang.SIZE];
	    ByteArrayInputStream byteArrayIn=new ByteArrayInputStream(byteTempIn);
	    ObjectInputStream objectIn;
	    
	    dianping.seek(pos);
		dianping.read(byteTempIn);
		byteArrayIn=new ByteArrayInputStream(byteTempIn);
		objectIn=new ObjectInputStream(byteArrayIn);
		return (Instance)(objectIn.readObject());
		
	}
	
	public void print(){
		System.out.println(DianPingWang.str1+"\t"+name);
		System.out.println(DianPingWang.str2+"\t"+taste);
		System.out.println(DianPingWang.str3+"\t"+environment);
		System.out.println(DianPingWang.str4+"\t"+service);
		System.out.println(DianPingWang.str5+"\t"+average);
		System.out.println(DianPingWang.str6+"\t"+address);
		System.out.println(DianPingWang.str7+"\t"+phone);
		System.out.println(DianPingWang.str8+"\t"+label);
	}
}
public class DianPingWang {
	final static String str1="商店名";
	final static String str2="口味";
	final static String str3="环境";
	final static String str4="服务";
	final static String str5="人均";
	final static String str6="地址";
	final static String str7="电话";
	final static String str8="标签";
	static final int SIZE=400;
		
	public static long query(RandomAccessFile dianping, String nameTemp) throws IOException, ClassNotFoundException{
		boolean flag=false;
		long pos=-1;
		long length=dianping.length();
		Instance instance;
		for (long i=0;i<length;i+=SIZE){
			instance=Instance.readObject(i, dianping);
			if (instance.delete==false)
				if (instance.name.equals(nameTemp)){
					flag=true;
					pos=i;
					break;				
				}
		}
		return pos;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{

		//InputStreamReader fIn=new FileReader("input.txt");
		//BufferedReader fBuffer=new BufferedReader(fIn);
		InputStreamReader systemIn=new InputStreamReader(System.in);
		BufferedReader fBuffer=new BufferedReader(systemIn);
		String temp;
		Instance instance;

		String nameTemp,addressTemp,phoneTemp,labelTemp;
		int tasteTemp,serviceTemp,environmentTemp,averageTemp;
		RandomAccessFile dianping=new RandomAccessFile("dianping.dat","rw");
		
	    long length;
	    boolean orderMatch=false;
	    
		temp=fBuffer.readLine();
		while (!Pattern.matches(".*[Ee][Xx][Ii][Tt].*", temp)){
			if (Pattern.matches("[Aa][Dd][Dd] +(.+)", temp)){
				orderMatch=true;
		
				Matcher matcher=(Pattern.compile("[Aa][Dd][Dd] +(.+)")).matcher(temp);
				System.out.println(matcher.matches());
				nameTemp=matcher.group(1);
				tasteTemp=Integer.parseInt(fBuffer.readLine());
				environmentTemp=Integer.parseInt(fBuffer.readLine());
				serviceTemp=Integer.parseInt(fBuffer.readLine());
				averageTemp=Integer.parseInt(fBuffer.readLine());
				addressTemp=fBuffer.readLine();
				phoneTemp=fBuffer.readLine();
				labelTemp=fBuffer.readLine();
				
				instance=new Instance(nameTemp,addressTemp,phoneTemp,labelTemp,tasteTemp,serviceTemp,environmentTemp,averageTemp, false);
								
				length=dianping.length();
				instance.writeObject(length, dianping);
				System.out.println(dianping.length());
			}
			if (Pattern.matches("SELECT ALL",temp)){
				orderMatch=true;
				
				length=dianping.length();
				for (long i=0;i<length;i+=SIZE){
					instance=Instance.readObject(i, dianping);					
					if (instance.delete==false)
						System.out.println(instance.name);
				}
			}
			if (Pattern.matches("DELETE +(.+)", temp)){
				orderMatch=true;
				
				Matcher matcher=(Pattern.compile("DELETE +(.+)")).matcher(temp);
				System.out.println(matcher.matches());
				nameTemp=matcher.group(1);
				
				Long pos=query(dianping,nameTemp);
				System.out.println(pos);
				if ((pos)!=-1){
					instance=new Instance();
					instance=Instance.readObject(pos, dianping);
		        	instance.delete=true;
					System.out.println(instance.name);					
					instance.writeObject(pos, dianping);				
				}
				else
					System.out.println("NOT FOUND!");
				
			}
			
			if (Pattern.matches("UPDATE +(.+)", temp)){
				orderMatch=true;
				
				Matcher matcher=(Pattern.compile("UPDATE +(.+)")).matcher(temp);
				System.out.println(matcher.matches());
				nameTemp=matcher.group(1);
				
				Long pos=query(dianping,nameTemp);
				if ((pos)!=-1){
					instance=new Instance();
					instance=Instance.readObject(pos, dianping);
					System.out.println(temp);
					temp=fBuffer.readLine();
					
					matcher=(Pattern.compile("商店名 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						nameTemp=matcher.group(1);
						instance.name=nameTemp;
					}
					
					matcher=(Pattern.compile("口味 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						tasteTemp=Integer.parseInt(matcher.group(1));
						instance.taste=tasteTemp;
					}
					
					matcher=(Pattern.compile("环境 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						environmentTemp=Integer.parseInt(matcher.group(1));
						instance.environment=environmentTemp;
					}
					
					matcher=(Pattern.compile("服务 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						serviceTemp=Integer.parseInt(matcher.group(1));
						instance.service=serviceTemp;
					}
					
					matcher=(Pattern.compile("人均 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						averageTemp=Integer.parseInt(matcher.group(1));
						instance.average=averageTemp;
					}
					
					matcher=(Pattern.compile("地址 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						addressTemp=matcher.group(1);
						instance.address=addressTemp;
					}
					
					matcher=(Pattern.compile("电话 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						phoneTemp=matcher.group(1);
						instance.phone=phoneTemp;
					}
					
					matcher=(Pattern.compile("标签 +(.+)")).matcher(temp);
					System.out.println(matcher.matches());
					if (matcher.matches()){
						labelTemp=matcher.group(1);
						instance.label=labelTemp;
					}
					instance.writeObject(pos, dianping);
					instance.print();
				}
				else
					System.out.println("NOT FOUND!");
			}
			
			
			if (Pattern.matches("QUERY +(.+)", temp)){
				orderMatch=true;
				
				Matcher matcher=(Pattern.compile("QUERY +(.+)")).matcher(temp);
				System.out.println(matcher.matches());
				nameTemp=matcher.group(1);
				
				Long pos=query(dianping,nameTemp);
				
				if (pos!=-1){
					instance=new Instance();
					instance=Instance.readObject(pos, dianping);
					instance.print();
				}
				else 
					System.out.println("Not Found!");
			}
			
			if (orderMatch==false)
				System.out.println("Wrong Order!");
			
			orderMatch=false;
			
			temp=fBuffer.readLine();
		}	
	}
}
