/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import net.sourceforge.jpcap.net.ARPPacket;
import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;

/**
 *
 * @author Fantasia
 */
public class StandardPacketFilter extends Filter {

	private enum Type {

		SRC_PORT,
		DEST_PORT,
		SRC_ADDR,
		DEST_ADDR,
		PROTOCOL,
		CONTAINS
	}

	public boolean isPass(Packet p) {
		switch (type) {
			case SRC_PORT: {
				if (p instanceof TCPPacket) {
					if (((TCPPacket) p).getSourcePort() == Integer.parseInt(value)) {
						return true;
					}
				}
				if (p instanceof UDPPacket) {
					if (((UDPPacket) p).getSourcePort() == Integer.parseInt(value)) {
						return true;
					}
				}
				break;
			}
			case DEST_PORT: {
				if (p instanceof TCPPacket) {
					if (((TCPPacket) p).getDestinationPort() == Integer.parseInt(value)) {
						return true;
					}
				}
				if (p instanceof UDPPacket) {
					if (((UDPPacket) p).getDestinationPort() == Integer.parseInt(value)) {
						return true;
					}
				}
				break;
			}
			case SRC_ADDR: {
				if (p instanceof IPPacket) {
					if (((IPPacket) p).getSourceAddress().equals(value)) {
						return true;
					}
				}
				break;
			}
			case DEST_ADDR: {
				if (p instanceof IPPacket) {
					if (((IPPacket) p).getDestinationAddress().equals(value)) {
						return true;
					}
				}
				break;
			}
			case PROTOCOL: {
				if (value.equals("TCP")) {
					if (p instanceof TCPPacket) {
						return true;
					}
				} else if (value.equals("UDP")) {
					if (p instanceof UDPPacket) {
						return true;
					}
				} else if (value.equals("ARP")) {
					if (p instanceof ARPPacket) {
						return true;
					}
				} else if (value.equals("ICMP")) {
					if (p instanceof ICMPPacket) {
						return true;
					}
				}
				break;
			}
			case CONTAINS: {
				byte[] bytes = p.getData();
				byte[] pattern = new byte[value.length()];
				for (int i = 0; i < value.length(); ++i) {
					pattern[i] = (byte) value.charAt(i);
				}
				if (strstr(bytes, pattern) != -1)
					return true;
				break;

			}
		}
		return false;
	}
	private String value;
	private Type type;

	public StandardPacketFilter() {
	}

	public boolean initCondition(String condition) {
		if (!condition.contains("=")) {
			return false;
		}
		String[] parts = condition.split("=");
		String typeStr = parts[0].trim();
		if (typeStr.equals("src_port") || typeStr.equals("dest_port")) {
			type = typeStr.equals("src_port") ? Type.SRC_PORT : Type.DEST_PORT;
			int port;
			try {
				port = Integer.parseInt(parts[1].trim());
			} catch (Exception e) {
				return false;
			}
			if (port < 0 || port > 65535) {
				return false;
			}
			value = parts[1].trim();
		} else if (typeStr.equals("src_addr")) {
			type = Type.SRC_ADDR;
			value = parts[1].trim();
		} else if (typeStr.equals("dest_addr")) {
			type = Type.DEST_ADDR;
			value = parts[1].trim();
		} else if (typeStr.equals("protocol")) {
			type = Type.PROTOCOL;
			String protocol = parts[1].trim();
			value = protocol;

		} else if (typeStr.equals("contains")) {
			type = Type.CONTAINS;
			value = parts[1].trim();
		} else {
			return false;
		}
		return true;
	}

	public static int strstr(byte[] b1, byte[] b2) {
		boolean flag = true;
		if (b1 != null && b2 != null) {
			for (int i = 0; i < b1.length; i++) {
				if (b1[i] != b2[0]) {
					continue;
				} else {
					if (b1.length - i >= b2.length) {
						for (int j = 0; j < b2.length; j++) {
							if (b2[j] != b1[i + j]) {
								flag = false;
								break;
							}
						}

						if (flag) {
							return i;
						}
					}
				}
			}
		}

		return -1;
	}
}
