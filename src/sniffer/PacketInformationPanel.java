/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PacketInformationPanel.java
 *
 * Created on Dec 28, 2009, 9:03:33 PM
 */

package sniffer;

import core.Analyzer;
import net.sourceforge.jpcap.net.ARPPacket;
import net.sourceforge.jpcap.net.EthernetPacket;
import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;

/**
 *
 * @author ipiszy
 */
public class PacketInformationPanel extends javax.swing.JPanel {

    /** Creates new form PacketInformationPanel */
    public PacketInformationPanel() {
        initComponents();
    }

    public void showPacketInformation(Packet p)
    {
	    //clear all
	    ethernetHeaderInfoTextArea.setText("");
	    ethernetDataTextArea.setText("");
	    ethernetHeaderDataTextArea.setText("");
	    ipHeaderInfoTextArea.setText("");
	    ipHeaderDataTextArea.setText("");
	    ipDataTextArea.setText("");
	    otherHeaderInfoTextArea.setText("");
	    otherHeaderDataTextArea.setText("");
	    otherDataTextArea.setText("");
	    jTabbedPane1.setTitleAt(2, "Other");
	    if (p instanceof EthernetPacket)
	    {
		    EthernetPacket packet = (EthernetPacket)p;
		    ethernetHeaderInfoTextArea.setText(Analyzer.EthernetInform(packet));
		    ethernetHeaderDataTextArea.setText(Analyzer.byteToStr(packet.getEthernetHeader()));
		    ethernetDataTextArea.setText(Analyzer.byteToStr(packet.getEthernetData()));
	    }
	    if (p instanceof IPPacket)
	    {
		    IPPacket packet = (IPPacket)p;
		    ipHeaderInfoTextArea.setText(Analyzer.IPInform(packet));
		    ipHeaderDataTextArea.setText(Analyzer.byteToStr(packet.getIPHeader()));
		    ipDataTextArea.setText(Analyzer.byteToStr(packet.getIPData()));
	    }
	    if (p instanceof TCPPacket)
	    {
		    TCPPacket packet = (TCPPacket)p;
		    otherHeaderInfoTextArea.setText(Analyzer.TCPInform(packet));
		    otherHeaderDataTextArea.setText(Analyzer.byteToStr(packet.getTCPHeader()));
		    otherDataTextArea.setText(Analyzer.byteToStr(packet.getTCPData()));
		    jTabbedPane1.setTitleAt(2, "TCP"); 
	    }
	    else if (p instanceof UDPPacket)
	    {
		    UDPPacket packet = (UDPPacket)p;
		    otherHeaderInfoTextArea.setText(Analyzer.UDPInform(packet));
		    otherHeaderDataTextArea.setText(Analyzer.byteToStr(packet.getUDPHeader()));
		    otherDataTextArea.setText(Analyzer.byteToStr(packet.getUDPData()));
		    jTabbedPane1.setTitleAt(2, "UDP"); 
	    }
	    else if (p instanceof ARPPacket)
	    {
		    ARPPacket packet = (ARPPacket)p;
		    otherHeaderInfoTextArea.setText(Analyzer.ARPInform(packet));
		    otherHeaderDataTextArea.setText(Analyzer.byteToStr(packet.getARPHeader()));
		    otherDataTextArea.setText(Analyzer.byteToStr(packet.getARPData()));
		    jTabbedPane1.setTitleAt(2, "ARP"); 
	    }
	    else if (p instanceof ICMPPacket)
	    {
		    ICMPPacket packet = (ICMPPacket)p;
		    otherHeaderInfoTextArea.setText(Analyzer.ICMPInform(packet));
		    otherHeaderDataTextArea.setText(Analyzer.byteToStr(packet.getICMPHeader()));
		    otherDataTextArea.setText(Analyzer.byteToStr(packet.getICMPData()));
		    jTabbedPane1.setTitleAt(2, "ICMP"); 
	    }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        ethernetInfoPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ethernetHeaderInfoTextArea = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ethernetDataTextArea = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ethernetHeaderDataTextArea = new javax.swing.JTextArea();
        ipInfoPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        ipHeaderInfoTextArea = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        ipHeaderDataTextArea = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        ipDataTextArea = new javax.swing.JTextArea();
        otherInfoPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        otherHeaderInfoTextArea = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        otherHeaderDataTextArea = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        otherDataTextArea = new javax.swing.JTextArea();

        setName("Form"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        ethernetInfoPanel.setName("ethernetInfoPanel"); // NOI18N
        ethernetInfoPanel.setPreferredSize(new java.awt.Dimension(600, 386));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sniffer.SnifferApp.class).getContext().getResourceMap(PacketInformationPanel.class);
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        ethernetHeaderInfoTextArea.setColumns(20);
        ethernetHeaderInfoTextArea.setEditable(false);
        ethernetHeaderInfoTextArea.setFont(resourceMap.getFont("ethernetHeaderDataTextArea.font")); // NOI18N
        ethernetHeaderInfoTextArea.setRows(5);
        ethernetHeaderInfoTextArea.setName("ethernetHeaderInfoTextArea"); // NOI18N
        jScrollPane1.setViewportView(ethernetHeaderInfoTextArea);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        ethernetDataTextArea.setColumns(20);
        ethernetDataTextArea.setEditable(false);
        ethernetDataTextArea.setFont(resourceMap.getFont("ethernetDataTextArea.font")); // NOI18N
        ethernetDataTextArea.setRows(5);
        ethernetDataTextArea.setName("ethernetDataTextArea"); // NOI18N
        jScrollPane2.setViewportView(ethernetDataTextArea);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel6.border.title"))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        ethernetHeaderDataTextArea.setColumns(20);
        ethernetHeaderDataTextArea.setEditable(false);
        ethernetHeaderDataTextArea.setFont(resourceMap.getFont("ethernetHeaderDataTextArea.font")); // NOI18N
        ethernetHeaderDataTextArea.setRows(5);
        ethernetHeaderDataTextArea.setName("ethernetHeaderDataTextArea"); // NOI18N
        jScrollPane3.setViewportView(ethernetHeaderDataTextArea);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ethernetInfoPanelLayout = new javax.swing.GroupLayout(ethernetInfoPanel);
        ethernetInfoPanel.setLayout(ethernetInfoPanelLayout);
        ethernetInfoPanelLayout.setHorizontalGroup(
            ethernetInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ethernetInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ethernetInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        ethernetInfoPanelLayout.setVerticalGroup(
            ethernetInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ethernetInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(257, 257, 257))
        );

        jTabbedPane1.addTab(resourceMap.getString("ethernetInfoPanel.TabConstraints.tabTitle"), ethernetInfoPanel); // NOI18N

        ipInfoPanel.setName("ipInfoPanel"); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel10.border.title"))); // NOI18N
        jPanel10.setName("jPanel10"); // NOI18N

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        ipHeaderInfoTextArea.setColumns(20);
        ipHeaderInfoTextArea.setEditable(false);
        ipHeaderInfoTextArea.setFont(resourceMap.getFont("ipHeaderDataTextArea.font")); // NOI18N
        ipHeaderInfoTextArea.setRows(5);
        ipHeaderInfoTextArea.setName("ipHeaderInfoTextArea"); // NOI18N
        jScrollPane7.setViewportView(ipHeaderInfoTextArea);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel11.border.title"))); // NOI18N
        jPanel11.setName("jPanel11"); // NOI18N

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        ipHeaderDataTextArea.setColumns(20);
        ipHeaderDataTextArea.setEditable(false);
        ipHeaderDataTextArea.setFont(resourceMap.getFont("ipHeaderDataTextArea.font")); // NOI18N
        ipHeaderDataTextArea.setRows(5);
        ipHeaderDataTextArea.setName("ipHeaderDataTextArea"); // NOI18N
        jScrollPane8.setViewportView(ipHeaderDataTextArea);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel12.border.title"))); // NOI18N
        jPanel12.setName("jPanel12"); // NOI18N

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        ipDataTextArea.setColumns(20);
        ipDataTextArea.setEditable(false);
        ipDataTextArea.setFont(resourceMap.getFont("ipHeaderDataTextArea.font")); // NOI18N
        ipDataTextArea.setRows(5);
        ipDataTextArea.setName("ipDataTextArea"); // NOI18N
        jScrollPane9.setViewportView(ipDataTextArea);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout ipInfoPanelLayout = new javax.swing.GroupLayout(ipInfoPanel);
        ipInfoPanel.setLayout(ipInfoPanelLayout);
        ipInfoPanelLayout.setHorizontalGroup(
            ipInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ipInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ipInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ipInfoPanelLayout.setVerticalGroup(
            ipInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ipInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(323, 323, 323))
        );

        jTabbedPane1.addTab(resourceMap.getString("ipInfoPanel.TabConstraints.tabTitle"), ipInfoPanel); // NOI18N

        otherInfoPanel.setName("otherInfoPanel"); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel7.border.title"))); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        otherHeaderInfoTextArea.setColumns(20);
        otherHeaderInfoTextArea.setEditable(false);
        otherHeaderInfoTextArea.setFont(resourceMap.getFont("otherDataTextArea.font")); // NOI18N
        otherHeaderInfoTextArea.setRows(5);
        otherHeaderInfoTextArea.setName("otherHeaderInfoTextArea"); // NOI18N
        jScrollPane4.setViewportView(otherHeaderInfoTextArea);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel8.border.title"))); // NOI18N
        jPanel8.setName("jPanel8"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        otherHeaderDataTextArea.setColumns(20);
        otherHeaderDataTextArea.setEditable(false);
        otherHeaderDataTextArea.setFont(resourceMap.getFont("otherDataTextArea.font")); // NOI18N
        otherHeaderDataTextArea.setRows(5);
        otherHeaderDataTextArea.setName("otherHeaderDataTextArea"); // NOI18N
        jScrollPane5.setViewportView(otherHeaderDataTextArea);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel9.border.title"))); // NOI18N
        jPanel9.setName("jPanel9"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        otherDataTextArea.setColumns(20);
        otherDataTextArea.setEditable(false);
        otherDataTextArea.setFont(resourceMap.getFont("otherDataTextArea.font")); // NOI18N
        otherDataTextArea.setRows(5);
        otherDataTextArea.setName("otherDataTextArea"); // NOI18N
        jScrollPane6.setViewportView(otherDataTextArea);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout otherInfoPanelLayout = new javax.swing.GroupLayout(otherInfoPanel);
        otherInfoPanel.setLayout(otherInfoPanelLayout);
        otherInfoPanelLayout.setHorizontalGroup(
            otherInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(otherInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(otherInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(otherInfoPanelLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(otherInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(otherInfoPanelLayout.createSequentialGroup()
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap()))))
        );
        otherInfoPanelLayout.setVerticalGroup(
            otherInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(otherInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(327, 327, 327))
        );

        jTabbedPane1.addTab(resourceMap.getString("otherInfoPanel.TabConstraints.tabTitle"), otherInfoPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ethernetDataTextArea;
    private javax.swing.JTextArea ethernetHeaderDataTextArea;
    private javax.swing.JTextArea ethernetHeaderInfoTextArea;
    private javax.swing.JPanel ethernetInfoPanel;
    private javax.swing.JTextArea ipDataTextArea;
    private javax.swing.JTextArea ipHeaderDataTextArea;
    private javax.swing.JTextArea ipHeaderInfoTextArea;
    private javax.swing.JPanel ipInfoPanel;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea otherDataTextArea;
    private javax.swing.JTextArea otherHeaderDataTextArea;
    private javax.swing.JTextArea otherHeaderInfoTextArea;
    private javax.swing.JPanel otherInfoPanel;
    // End of variables declaration//GEN-END:variables

}
