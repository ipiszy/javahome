/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import net.sourceforge.jpcap.net.Packet;

/**
 *
 * @author Fantasia
 */
public abstract class Filter {
	abstract boolean isPass(Packet p);
	public static Filter createFilter(String str)
	{
		if (str.contains("AND")){
			String[] parts = str.split("AND");
			StandardPacketFilter left = new StandardPacketFilter();
			if (!left.initCondition(parts[0]))
				return null;
			StandardPacketFilter right = new StandardPacketFilter();
			if (!right.initCondition(parts[0]))
				return null;
			AndFilter f = new AndFilter(left, right);
			return f;
		}
		else if (str.contains("OR")){
			String[] parts = str.split("OR");
			StandardPacketFilter left = new StandardPacketFilter();
			if (!left.initCondition(parts[0]))
				return null;
			StandardPacketFilter right = new StandardPacketFilter();
			if (!right.initCondition(parts[0]))
				return null;
			OrFilter f = new OrFilter(left, right);
			return f;
		}
		StandardPacketFilter filter = new StandardPacketFilter();
		if (filter.initCondition(str))
			return filter;
		return null;
	}
}
