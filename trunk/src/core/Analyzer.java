package core;

import net.sourceforge.jpcap.net.*;

public class Analyzer {
	public static String getSrcAddress(Packet packet){
		if (packet instanceof ARPPacket){
			return ((ARPPacket)packet).getSourceProtoAddress();
		}
		if (packet instanceof ICMPPacket){
			return ((ICMPPacket)packet).getSourceAddress();
		}
		if (packet instanceof IGMPPacket){
			return ((IGMPPacket)packet).getSourceAddress();
		}
		if (packet instanceof IPPacket){
			return ((IPPacket)packet).getSourceAddress();
		}
		return "-";
	}
	public static String getDstAddress(Packet packet){
		if (packet instanceof ARPPacket){
			return ((ARPPacket)packet).getDestinationProtoAddress();
		}
		if (packet instanceof ICMPPacket){
			return ((ICMPPacket)packet).getDestinationAddress();
		}
		if (packet instanceof IGMPPacket){
			return ((IGMPPacket)packet).getDestinationAddress();
		}
		if (packet instanceof IPPacket){
			return ((IPPacket)packet).getDestinationAddress();
		}
		return "-";
	}
	public static String getProtocolName(Packet packet){
		String result="";
		if (packet instanceof ICMPPacket){
			result=result+"ICMP/";
		}
		if (packet instanceof IGMPPacket){
			result=result+"IGMP/";
		}
		if (packet instanceof IPPacket){
			result=result+"IP/";
		}
		if (packet instanceof ARPPacket){
			result=result+"ARP/";
		}
		if (packet instanceof TCPPacket){
			result=result+"TCP/";
		}
		if (packet instanceof UDPPacket){
			result=result+"UDP/";
		}
		if (packet instanceof EthernetPacket){
			result=result+"EtherenetPacket/";			
		}
		if (!result.equals("")){
			result=result.substring(0,result.length()-1);
		}
		return result;
	}
	public static String getHeaderInform(Packet packet){
		return byteToStr(packet.getHeader());
	}
	public static String getDataInform(Packet packet){
		return byteToStr(packet.getData());
	}
	public static String createASCII(byte[] bytes)
	{
		StringBuffer buffer = new StringBuffer();
		for(byte b : bytes)
		{
			char ch = (char)b;
			if (!Character.isLetterOrDigit(ch) || b < 0)
				ch = '.';
			buffer.append(ch);
		}
		return buffer.toString();
	}
	public static String byteToStr(byte[] byteData){
		byte[] strByte=new byte[16];
		String result="";
		int level;
		if (byteData.length%16==0) level=byteData.length/16-1; else level=byteData.length/16;
		for (int r=0;r<=level;r++){
			result=result+byteToHex(r,5)+"h: ";
			for (int c=0;c<16;c++){
				int o=r*16+c;
				if (o>=byteData.length) {
					result=result+"-- ";
					strByte[c]=46;
				}else{
					strByte[c]=byteData[o];
					if (strByte[c]==0 || strByte[c]==10) strByte[c]=46; // Show 00 as .
					result=result+byteToHex(strByte[c],2)+" ";
				}
			}
			result=result+createASCII(strByte)+"\n";
		}
		return result;
	}
	public static String byteToHex(int num,int l){
		String result=Integer.toHexString(num);
		if (result.length()>l){
			result=result.substring(result.length()-l,result.length());
		}else{
			int k=l-result.length();
			for (int i=0;i<k;i++)
				result="0"+result;
		}
		//System.out.println(result);
		return result;
	}
	public static String EthernetInform(EthernetPacket packet){
		String text="";
		text=text+" Src. HW Address : "+packet.getSourceHwAddress()+"\tDst. HW Address : "+packet.getDestinationHwAddress()+"\n";
		text=text+" Timeval : "+packet.getTimeval()+"\n";
		text=text+" Ethernet Protocol : "+Analyzer.getEthernetProtocol(packet.getEthernetProtocol())+"(0x"+byteToHex(packet.getEthernetProtocol(),4)+")\n";
		text+= "Ethernet Packet Header Length : " + packet.getEthernetHeaderLength() + "\tEthernet Packet Data Length : " + packet.getEthernetData().length + "\n";
		return text;
	}
	public static String TCPInform(TCPPacket packet){
		String text="";
		text=text+" Source Port : "+packet.getSourcePort()+"\t\tDestination Port : "+packet.getDestinationPort()+"\n";
		text=text+" Acknowledgement Number : "+packet.getAcknowledgementNumber()+"\tSequence Numbert :"+packet.getSequenceNumber()+"\n";
		text=text+" Urgent Pointer : "+packet.getUrgentPointer()+"\t\tPayload Data Length : "+packet.getPayloadDataLength()+"\n";
		text=text+" Check Sum : "+packet.getChecksum()+"\t\tWindow Size : "+packet.getWindowSize()+"\n\n";
		text=text+" Ack : "+packet.isAck()+"\t\tFin : "+packet.isFin()+"\t\tPsh : "+packet.isPsh()+"\n";
		text=text+" Rst : "+packet.isRst()+"\t\tSyn : "+packet.isSyn()+"\t\tUrg : "+packet.isUrg()+"\n";
		text+= "TCP Packet Header Length : " + packet.getTCPHeaderLength() + "\tTCP Packet Data Length : " + packet.getTCPData().length + "\n";
		return text;
	}
	public static String IPInform(IPPacket packet){
		String text="";
		text=text+" Source Address : "+packet.getSourceAddress()+"\tDestination Address : "+packet.getDestinationAddress()+"\n";
		text=text+" Check Sum(Valid) : "+packet.getChecksum()+"("+packet.isValidIPChecksum()+")"+"\tTime To Live : "+packet.getTimeToLive()+"\n";
		text=text+" Fragment Flags(DF/MF) : "+packet.getFragmentFlags()+"("+getDF_MF(packet.getFragmentFlags())+")"+"\tFragment Offset : "+packet.getFragmentOffset()+"\n";
		text=text+" IP Packet ID : "+packet.getId()+"\t\tVersion : "+getIPVersion(packet.getVersion())+"\n";
		text=text+" Type of Service : "+getTypeOfService(packet.getTypeOfService())+"(0x"+byteToHex(packet.getTypeOfService(),4)+")"+"\n";
		text=text+" IP Protocol : "+IPProtocol.getDescription(packet.getIPProtocol())+"\n";
		text+= "IP Packet Header Length : " + packet.getIPHeaderLength() + "\tIP Packet Data Length : " + packet.getIPData().length + "\n";
		return text;
	}
	public static String UDPInform(UDPPacket packet){
		String text="";
		text=text+" Source Port : "+packet.getSourcePort()+"\t\tDestination Port : "+packet.getDestinationPort()+"\n";
		text=text+" Check Sum : "+packet.getChecksum()+"\n";
		return text;
	}
	public static String ICMPInform(ICMPPacket packet){
		String text="";
		text=text+" Message Major Code : "+packet.getMessageMajorCode()+"\t\tMessage Minor Code : "+packet.getMessageMinorCode()+"\n";
		text=text+" Message : "+ICMPMessage.getDescription(packet.getMessageCode())+"("+packet.getMessageCode()+")"+"\t\tCheck Sum : "+packet.getICMPChecksum()+"\n";
		text+= "ICMP Packet Header Length : " + packet.getICMPHeader().length + "\tICMP Packet Data Length : " + packet.getICMPData().length + "\n";
		return text;
	}
	public static String IGMPInform(IGMPPacket packet){
		String text="";
		text=text+" Check Sum : "+packet.getIGMPChecksum()+"\t\tGroup Address : "+packet.getGroupAddress()+"\n";
		text=text+" Max Response Time : "+packet.getMaxResponseTime()+"\n";
		text=text+" Message Type : "+IGMPMessage.getDescription(packet.getMessageType())+"("+packet.getMessageType()+")\n";
		return text;
	}
	public static String ARPInform(ARPPacket packet){
		String text="";
		text=text+" Src. HW Address : "+packet.getSourceHwAddress()+"\tDst. HW Address : "+packet.getDestinationHwAddress()+"\n";
		text=text+" Src. Protocol Address : "+packet.getSourceProtoAddress()+"\tDst. Protocol Address : "+packet.getDestinationProtoAddress()+"\n";
		text=text+" Operation : "+getARPOperation(packet.getOperation())+"\n";
		text+= "ARP Packet Header Length : " + packet.getARPHeader().length + "\tARP Packet Data Length : " + packet.getARPData().length + "\n";
		return text;
	}
	public static String getEthernetProtocol(int protocol){
		switch(protocol){
		case 0x0800: return "IP protocol";
		case 0x0806: return "Address resolution protocol";
		case 0x8035: return "Reverse address resolution protocol";
		case 0x0060: return "Ethernet Loopback packet";
		case 0x0200: return "Ethernet Echo packet";		
		case 0x0400: return "Xerox PUP packet";
		case 0x0805: return "CCITT X.25";			
		case 0x08FF: return "G8BPQ AX.25 Ethernet Packet";
		case 0x6000: return "DEC Assigned proto";
		case 0x6001: return "DEC DNA Dump/Load";
		case 0x6002: return "DEC DNA Remote Console";
		case 0x6003: return "DEC DNA Routing";
		case 0x6004: return "DEC LAT";
		case 0x6005: return "DEC Diagnostics";
		case 0x6006: return "DEC Customer use";
		case 0x6007: return "DEC Systems Comms Arch";
		case 0x809B: return "Appletalk DDP"; 
		case 0x80F3: return "Appletalk AARP";
		case 0x8137: return "IPX over DIX";
		case 0x86DD: return "IPv6 over bluebook";
		case 0x0001: return "Dummy type for 802.3 frames";
		case 0x0002: return "Dummy protocol id for AX.25";  
		case 0x0003: return "Every packet";
		case 0x0004: return "802.2 frames";
		case 0x0005: return "Internal only";
		case 0x0006: return "DEC DDCMP: Internal only";
		case 0x0007: return "Dummy type for WAN PPP frames";
		case 0x0008: return "Dummy type for PPP MP frames";
		case 0x0009: return "Localtalk pseudo type";
		case 0x0010: return "Dummy type for Atalk over PPP";
		case 0x0011: return "802.2 frames";
		case 0x0015: return "Mobitex (kaz@cafe.net)";
		case 0x0016: return "Card specific control frames";
		case 0x0017: return "Linux/IR";
		case 0x0026: return "spanning tree bridge protocol";
		case 0x886d: return "intel adapter fault tolerance heartbeats";
		case 0xffff: return "Ethernet protocol mask";
		default: return "Unknown";
		}
	}
	public static String getTypeOfService(int typeOfService){
		String result="";
		if ((typeOfService & 0x10)>0) result=result+"Minimize_Delay ";
		if ((typeOfService & 0x08)>0) result=result+"Maximize_Throughput ";
		if ((typeOfService & 0x04)>0) result=result+"Maximize_Reliability ";
		if ((typeOfService & 0x02)>0) result=result+"Minimize_Monetary_Cost ";
		if (result.equals("") || (typeOfService & 0x01)>0) result=result+"Unused ";
		return result;
	}
	public static String getIPVersion(int version){
		if (version==4) return "IPV4";
		return "IPV6";
	}
	public static String getARPOperation(int operation){
		if (operation==ARPPacket.ARP_OP_REP_CODE) return "ARP Reply";
		return "ARP Request";
	}
	public static String getDF_MF(int flags){
		flags=flags%4;
		if (flags==3) return "1/1";
		if (flags==2) return "1/0";
		if (flags==1) return "0/1";
		return "0/0";
	}
}
