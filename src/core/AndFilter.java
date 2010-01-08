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
public class AndFilter extends Filter{
	public Filter left;
	public Filter right;
	public AndFilter(Filter left, Filter right){
		this.left = left;
		this.right = right;
	}

	public boolean isPass(Packet p) {
		return left.isPass(p) && right.isPass(p);
	}

}
