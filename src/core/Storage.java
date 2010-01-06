package core;

import java.util.ArrayList;
import net.sourceforge.jpcap.net.*;

public class Storage {
	String fileName;
	java.io.PrintStream fileStream;
	boolean outToFile;
	int nowPos;
	int bufferNum=100;
	java.util.Vector<Item> data;
	void setFileName(String f){
		fileName=f;
	}
	public String getFileName(){
		return fileName;
	}
	public Storage(){
		try {
			setOutputFile("data.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data=new java.util.Vector<Item>();		
		nowPos=0;
	}

	public Packet getPacket(int o, Filter f){
		ArrayList<Item> items = new ArrayList<Item>();
		for(Item t : data)
		{
			if(f !=null)
			{
				if(f.isPass(t.packet))
					items.add(t);
			}
			else
				items.add(t);
		}
		//System.out.println(o);
		if (o>=0 && o<items.size())
			return items.get(o).packet;
		else
			return null;
	}
	public boolean isOutToFile(){
		return outToFile;
	}
	public void setOutputFile(String fileName)throws Exception{
		this.fileName=fileName;
		fileStream=new java.io.PrintStream(fileName);
		outToFile=true;		
	}
	public void flush(){
		data.removeAllElements();
	}
	public int query(String str){
		nowPos++;
		if (nowPos<0) nowPos=0;
		if (nowPos>=data.size()) nowPos=data.size()-1;
		for (int i=0;i<data.size();i++){
			String content=new String(data.elementAt(nowPos).packet.getData());
			if (content.contains(str.subSequence(0, str.length()-1))) return nowPos;
			nowPos++;
			if (nowPos==data.size()) nowPos=0;
		}
		return -1;
	}
	void addNewPacket(long timeOffset,Packet packet){
		data.add(new Item(timeOffset, packet));
		/*
		if (data.size()>bufferNum){
			data.removeElementAt(0);
		}
		 */
		if (outToFile){
			String result="Packet "+Long.toString(timeOffset)+" :\n";
			if (packet instanceof EthernetPacket){
				result=result+"Ethernet Protocol Area:\n";
				result=result+Analyzer.EthernetInform((EthernetPacket)packet);
			}
			if (packet instanceof TCPPacket){
				result=result+"TCP Protocol Area:\n";
				result=result+Analyzer.TCPInform((TCPPacket)packet);
			}
			if (packet instanceof UDPPacket){
				result=result+"UDP Protocol Area:\n";
				result=result+Analyzer.UDPInform((UDPPacket)packet);
			}
			if (packet instanceof ARPPacket){
				result=result+"ARP Protocol Area:\n";
				result=result+Analyzer.ARPInform((ARPPacket)packet);
			}
			if (packet instanceof IPPacket){
				result=result+"IP Protocol Area:\n";
				result=result+Analyzer.IPInform((IPPacket)packet);
			}
			if (packet instanceof ICMPPacket){
				result=result+"ICMP Protocol Area:\n";
				result=result+Analyzer.ICMPInform((ICMPPacket)packet);
			}
			if (packet instanceof IGMPPacket){
				result=result+"IGMP Protocol Area:\n";
				result=result+Analyzer.IGMPInform((IGMPPacket)packet);
			}
			result=result+"-------------------------------------\n";
			fileStream.println(result);
		}

	}
	public String[][] export(){
		return export(null);
	}
	public String[][] export(Filter filter){
		//String[][] result=new String[data.size()][];
		ArrayList<String[]> arrayList = new ArrayList<String[]>();
		for (int i=0;i<data.size();i++){
			Item item=data.elementAt(i);
			if (filter != null && filter.isPass(item.packet) == false)
				continue;
			Packet packet=item.packet;
			String[] result=new String[4];
			result[0]=new Long(item.timeOffset).toString();
			result[1]=Analyzer.getSrcAddress(packet);
			result[2]=Analyzer.getDstAddress(packet);
			result[3]=Analyzer.getProtocolName(packet);
			arrayList.add(result);
		}
		String[][] tmp = new String[0][0];
		return arrayList.toArray(tmp);
	}
	public void clear()
	{
		this.data.clear();
	}
	public void setOutToFile(boolean outToFile) {
		this.outToFile = outToFile;
	}
	public int getBufferNum() {
		return bufferNum;
	}
	public void setBufferNum(int bufferNum) {
		while (data.size()>bufferNum){
			data.removeElementAt(0);
		}
		this.bufferNum = bufferNum;
	}
}
class Item{
	Item(long i,Packet p){
		timeOffset=i;
		packet=p;
	}
	long timeOffset;
	Packet packet;
}







