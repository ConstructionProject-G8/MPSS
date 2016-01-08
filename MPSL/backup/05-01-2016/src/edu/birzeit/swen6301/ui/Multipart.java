package edu.birzeit.swen6301.ui;

import java.io.IOException;
import java.io.InputStream;

import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.MultiPartStream;

/**
 * Static class for reading from a multipart stream.

 */
public class Multipart {	
	/**
	 * Returns an InputStream which streams from the given url.
	 * <p>
	 * If the url ends with the suffix .segments or has the MIME type
	 * text/segments-manifest it is treated as a multipart manifest stream,
	 * and the returned InputStream streams the data represented by the manifest,
	 * not the contents of the manifest itself.
	 * <p>
	 * Otherwise, when the url does not point to a manifest,
	 * the returned input stream streams data directly from the url target.
	 */
	public static InputStream openStream(String url) throws IOException {
		try {
			MultiPartStream multiPartStream = new MultiPartStream(url);
			return multiPartStream;
		} catch (ParseException e) {
			throw new IOException(e.getMessage());
		}
		//throw new RuntimeException("not yet implemented!");
	}
}
