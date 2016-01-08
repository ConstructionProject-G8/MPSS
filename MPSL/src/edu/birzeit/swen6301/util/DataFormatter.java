/**
 * 
 */
package edu.birzeit.swen6301.util;

/**
 * @author Salah.Hussein
 *
 */
public class DataFormatter {
	public static byte[] merge(byte[] a, byte[] b) {
		return merge(a, b, 0, b.length);
	}

	public static byte[] merge(byte[] a, byte[] b, int bLength) {
		return merge(a, b, 0, bLength);
	}

	/**
	 * 
	 * Merge array b as source (or part of it) to array a as destination.
	 * 
	 * @param a
	 * @param b
	 * @param bStart
	 * @param bLength
	 */
	public static byte[] merge(byte[] a, byte[] b, int bStart, int bLength) {
		byte[] tmp = new byte[a.length+bLength];
		System.arraycopy(a, 0, tmp, 0, a.length);	// Copy a bytes array to temporary array.
		System.arraycopy(b, bStart, tmp, a.length, bLength);	// Copy fetched a bytes into temporary array.
		a = tmp.clone();	// Return new expanded array into first array (a).
		
		return a;
	}
}
