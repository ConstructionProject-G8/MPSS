/**
 * 
 */
package edu.birzeit.swen6301.util;

/**
 * @author Salah.Hussein
 *
 */
public class DataFormatter {
	public static void merge(byte[] a, byte[] b) {
		merge(a, b, 0, b.length);
	}

	public static void merge(byte[] a, byte[] b, int bStart) {
		merge(a, b, bStart, b.length);
	}

	public static void merge(byte[] a, byte[] b, int bStart, int bLength) {
		byte[] tmp = new byte[a.length+bLength];
		System.arraycopy(a, 0, tmp, 0, a.length);	// Copy a bytes array to temporary array.
		System.arraycopy(b, bStart, tmp, a.length, bLength);	// Copy fetched a bytes into temporary array.
		a = tmp;	// Return new expanded array into first array (a).
	}
}
