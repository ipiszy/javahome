package core;


import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;
import sniffer.SnifferView;

public class Capture implements Runnable {
	private final int INFINITE = -1;
	private final int PACKET_COUNT = INFINITE;
	private int m_counter = 0;
	private long startTime = 0;

	static private PacketCapture m_pcap;
	boolean show;
	Storage storage;
	public String[] m_device;
	int selectingDevice;
	boolean protocolFilter;
	boolean TCPFilter, IPFilter, ICMPFilter, ARPFilter, UDPFilter;
	boolean addressFilter;
	String[] addressList;
	boolean portFilter;
	String[] portList;
	TCPRecompose tcpRecompose;
	boolean applyTCPRecompose;
        public SnifferView view;

	public void setProtocolFilter(boolean protocolFilter, boolean TCPFilter,
			boolean IPFilter, boolean ICMPFilter, boolean ARPFilter,
			boolean UDPFilter) {
		this.protocolFilter = protocolFilter;
		this.TCPFilter = TCPFilter;
		this.IPFilter = IPFilter;
		this.ICMPFilter = ICMPFilter;
		this.ARPFilter = ARPFilter;
		this.UDPFilter = UDPFilter;
	}

	public void setAddressFilter(boolean addressFilter, String[] addressList) {
		this.addressFilter = addressFilter;
		this.addressList = addressList;
	}

	public void setPortFilter(boolean portFilter, String[] portList) {
		this.portFilter = portFilter;
		this.portList = portList;
	}

	public Capture(Storage s) {
		m_pcap = new PacketCapture();
		try {
			m_device = m_pcap.lookupDevices();
//			for(String device : m_device){
//				System.out.println(device);
//			}
		} catch (CaptureDeviceLookupException e) {

			e.printStackTrace();
		}
		selectingDevice = 4;
		//System.out.println(m_device[selectingDevice]);
		storage = s;
		m_pcap.addPacketListener(new PacketHandler());
		addressList = new String[0];
		portList = new String[0];
		tcpRecompose = new TCPRecompose();
		applyTCPRecompose = true;
		//init();
	}

	public void selectDevice(int d) {
		selectingDevice = d;
	}

	public int getSelectingDevice() {
		return selectingDevice;
	}

	public void init() {
		String[] strs;
		strs = m_device[selectingDevice].split("\n");
		m_pcap.close();
		//System.out.println(strs[0]);
		try {
			m_pcap.open(strs[0], 9999999, true, 1000);
			//m_pcap.setFilter(initFilter(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		startTime = System.currentTimeMillis();
	}

	public String[] getDevice() {
		return m_device;
	}

	public Storage getStorage() {
		return storage;
	}

	public void start() {

	}

	public void pause() {
		m_pcap.endCapture();
	}

	class PacketHandler implements PacketListener {
		public void packetArrived(Packet packet) {
			m_counter++;
			storage.addNewPacket(System.currentTimeMillis() - startTime, packet);
			System.out
					.println(Analyzer.EthernetInform((EthernetPacket) packet));
                        view.refreshUI(false);
			// layout.freshTable(storage.export());
			if (packet instanceof TCPPacket && applyTCPRecompose) {
				tcpRecompose.handleTCPPacket((TCPPacket) packet);
			}

			/*
			 * if (packet instanceof ARPPacket){
			 * System.out.println("APRPacket "+new
			 * String(packet.getData())+" "); } if (packet instanceof
			 * EthernetPacket){ System.out.println("EthernetPacket "+new
			 * String(packet.getData())+" "); } if (packet instanceof
			 * ICMPPacket){ System.out.println("ICMPPacket "+new
			 * String(packet.getData())+" "); } if (packet instanceof
			 * IGMPPacket){ System.out.println("IGMPPacket "+new
			 * String(packet.getData())+" "); } if (packet instanceof IPPacket){
			 * System.out.println("IPPacket "+new String(packet.getData())+" ");
			 * } if (packet instanceof TCPPacket){
			 * System.out.println("TCPPacket "+new
			 * String(packet.getData())+" "); } if (packet instanceof
			 * UDPPacket){ System.out.println("UDPPacket "+new
			 * String(packet.getData())+" "); } System.out.println();
			 */
		}
	}

	String initFilter() {
		String result = "";
		if (TCPFilter) {
			result = result + "ip proto \\tcp or ";
		}
		if (IPFilter) {
			result = result + "ether proto \\ip or ";
		}
		if (ICMPFilter) {
			result = result + "ip proto \\icmp or ";
		}
		if (ARPFilter) {
			result = result + "ether proto \\arp or ";
		}
		if (UDPFilter) {
			result = result + "ip proto \\udp or ";
		}
		if (addressFilter) {
			for (int i = 0; i < addressList.length; i++) {
				String[] strs = addressList[i].split(" ");
				if (strs[0].equals("(Src.)"))
					result = result + "src host " + strs[1] + " or ";
				else
					result = result + "dst host " + strs[1] + " or ";
			}
		}
		if (portFilter) {
			for (int i = 0; i < portList.length; i++) {
				String[] strs = portList[i].split(" ");
				if (strs[0].equals("(Src.)"))
					result = result + "src port " + strs[1] + " or ";
				else
					result = result + "dst port " + strs[1] + " or ";
			}
		}
		if (!result.equals("")) {
			result = result.substring(0, result.length() - 4);
		}
		return result;
	}

	public void run() {
		try {
			m_pcap.capture(PACKET_COUNT);
		} catch (CapturePacketException e) {
			e.printStackTrace();
		}
	}

	public boolean isAddressFilter() {
		return addressFilter;
	}

	public String[] getAddressList() {
		return addressList;
	}

	public boolean isARPFilter() {
		return ARPFilter;
	}

	public boolean isICMPFilter() {
		return ICMPFilter;
	}

	public boolean isIPFilter() {
		return IPFilter;
	}

	public boolean isPortFilter() {
		return portFilter;
	}

	public String[] getPortList() {
		return portList;
	}

	public boolean isProtocolFilter() {
		return protocolFilter;
	}

	public boolean isTCPFilter() {
		return TCPFilter;
	}

	public boolean isUDPFilter() {
		return UDPFilter;
	}

	public TCPRecompose getTcpRecompose() {
		return tcpRecompose;
	}

	public boolean isApplyTCPRecompose() {
		return applyTCPRecompose;
	}

	public void setApplyTCPRecompose(boolean applyTCPRecompose) {
		this.applyTCPRecompose = applyTCPRecompose;
	}

	public void setM_counter(int m_counter) {
		this.m_counter = m_counter;
	}

	public static void main(String[] args) throws Exception {
		Capture c = new Capture(new Storage());
		Thread t = new Thread(c);
		t.start();
		t.join();
	}
}
