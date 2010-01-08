/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import net.sourceforge.jpcap.net.Packet;

/**
 *
 * @author ipiszy
 */
public class OrFilter extends Filter{
	private Filter left;
	private Filter right;
	public OrFilter(Filter left, Filter right){
		this.left = left;
		this.right = right;
	}

	public boolean isPass(Packet p) {
		return left.isPass(p) || right.isPass(p);
	}
}
