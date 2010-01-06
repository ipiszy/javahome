package core;

import java.io.IOException;
import java.io.File;

import net.sourceforge.jpcap.net.TCPPacket;

public class TCPRecompose {
	java.io.File directory;

	java.util.Hashtable<Item, java.util.Vector<Tag>> packetList;

	TCPRecompose() {
		setStorageLocation("storage/");
	}

	public boolean setStorageLocation(String name) {
		directory = new java.io.File(name);
		if (!directory.isDirectory()) {
			if (!directory.mkdirs())
				return false;
		}
		packetList = new java.util.Hashtable<Item, java.util.Vector<Tag>>();
		return true;
	}

	public void handleTCPPacket(TCPPacket packet) {
		Tag tag = getTag(packet);
		//System.out.println(tag.file.getAbsolutePath()+":::"+packet.getData().length+","+packet.getPayloadDataLength());
		if (packet.getData().length > 0) {
			try {
				tag.output.seek(packet.getSequenceNumber() - tag.beginSeqNum);
				tag.output.write(packet.getData());
			} catch (IOException e) {
				e.printStackTrace();
			}
			tag.requestSeqNum = packet.getSequenceNumber()+packet.getData().length;
		}
	}

	Tag getTag(TCPPacket packet) {
		java.util.Vector<Tag> tagList = packetList.get(new Item(packet));
		if (tagList == null) {
			tagList = new java.util.Vector<Tag>();
			packetList.put(new Item(packet), tagList);
		}
		java.util.Iterator<Tag> iter = tagList.iterator();
		long seqNum = packet.getSequenceNumber();
		Tag tag;
		while (iter.hasNext()) {
			tag = iter.next();
			if (seqNum >= tag.beginSeqNum && seqNum < tag.requestSeqNum + 5000)
				return tag;
		}
		/*System.out.println(directory.getAbsolutePath() + "/"
				+ packet.getSourceAddress() + "__"
				+ packet.getDestinationAddress() + ":" + tagList.size() + "   "
				+ packet.getSourcePort() + "," + packet.getDestinationPort());*/
		tag = new Tag();
		if (packet.isSyn()) {
			tag.requestSeqNum = tag.beginSeqNum = packet.getSequenceNumber() + 1;
			tag.beginWithSyn = true;
		} else {
			tag.requestSeqNum = tag.beginSeqNum = packet.getSequenceNumber();
			tag.beginWithSyn = false;
		}
		java.io.File t = new java.io.File(directory.getAbsolutePath() + "/"
				+ packet.getSourceAddress() + "__"
				+ packet.getDestinationAddress());
		tag.file = new java.io.File(t.getAbsolutePath() + "/" + tagList.size()
				+ ".pak");
		try {
			t.mkdirs();
			tag.file.createNewFile();
			tag.output = new java.io.RandomAccessFile(tag.file
					.getAbsolutePath(), "rw");
		} catch (IOException e) {
			//System.out.println(tag.file.getAbsolutePath());
			e.printStackTrace();
		}
		tagList.add(tag);
		return tag;
	}

	class Item {
		String srcHost;

		String dstHost;

		Item(TCPPacket packet) {
			this.srcHost = packet.getSourceAddress();
			this.dstHost = packet.getDestinationAddress();
		}

		@Override
		public int hashCode() {
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result
					+ ((dstHost == null) ? 0 : dstHost.hashCode());
			result = PRIME * result
					+ ((srcHost == null) ? 0 : srcHost.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Item other = (Item) obj;
			if (dstHost == null) {
				if (other.dstHost != null)
					return false;
			} else if (!dstHost.equals(other.dstHost))
				return false;
			if (srcHost == null) {
				if (other.srcHost != null)
					return false;
			} else if (!srcHost.equals(other.srcHost))
				return false;
			return true;
		}

	}

	class Tag {
		java.io.File file;

		java.io.RandomAccessFile output;

		long beginSeqNum;

		long requestSeqNum;

		boolean beginWithSyn;
	}

	public java.io.File getDirectory() {
		return directory;
	}

}
