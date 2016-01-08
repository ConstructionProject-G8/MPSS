package edu.birzeit.swen6301.ui;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import edu.birzeit.swen6301.model.IPart;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.util.DataFormatter;
import edu.birzeit.swen6301.util.MultiPartStream;

/**
 * Static class for reading from a file-sequence stream.

 */
public class FileSequenceReader {
	/**
	 * Returns the data from the next sub-file in the given file sequence stream.
	 * <p>
	 * If no sub-files remain, returns null. If the stream ends prematurely,
	 * throws an EOFException.
	 */
	public static byte[] readOneFile(InputStream sequence) throws IOException, EOFException {
		// sequence files consist of a (4-byte) int giving the size of the sub-file,
		// followed by the sub-file, followed by another size, followed by the sub-file,
		// and so on until EOF
		try {
			MultiPartStream multiPartStream = (MultiPartStream) sequence;
			IPart part = multiPartStream.nextPart();
			
			if (part == null || part.isEndLess()) {		//	If end of parts is reached.
				return null;
				//throw new EOFException("EOF, end of parts is reached.");
			}
			
			byte[] dataAndSize = part.download();
			// Discard size bytes.
			byte[] data = DataFormatter.merge(new byte[0], dataAndSize, 4, dataAndSize.length-4);
			
			return data;//part.download();
		} catch (ParseException e) {
			throw new IOException(e.getMessage());
		}
	}
}
