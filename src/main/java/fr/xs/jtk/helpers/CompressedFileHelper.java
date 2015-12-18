package fr.xs.jtk.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class CompressedFileHelper extends MediaHelper {

	/**
	 * URL _dest has to use "file://" as protocol !!!
	 * @param _url
	 * @param _dest
	 */
	public static void unzip(URL _url, URL _dest) {
		unzip(_url, _dest, false);
	}
	public static void unzip(URL _url, URL _dest, boolean _remove_top) {
	    int BUFFER_SZ = 2048;
	    File file = new File(_url.getPath());
	    
	    String dest = _dest.getPath();
	    new File(dest).mkdir();

		try {
		    ZipFile zip = new ZipFile(file);
		    Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();

		    // Process each entry
		    while (zipFileEntries.hasMoreElements()) {
		        // grab a zip file entry
		        ZipEntry entry        = (ZipEntry) zipFileEntries.nextElement();
		        String   currentEntry = entry.getName();
		        
		        if(_remove_top)
		        	currentEntry = currentEntry.substring(currentEntry.indexOf('/'), currentEntry.length());
		        
		        File     destFile     = new File(dest, currentEntry);
		        //destFile = new File(newPath, destFile.getName());
		        File destinationParent = destFile.getParentFile();
	
		        // create the parent directory structure if needed
		        destinationParent.mkdirs();
	
		        if (!entry.isDirectory())
		        {
		            BufferedInputStream is;
					is = new BufferedInputStream(zip.getInputStream(entry));
		            int currentByte;
		            // establish buffer for writing file
		            byte data[] = new byte[BUFFER_SZ];
	
		            // write the current file to disk
		            FileOutputStream fos = new FileOutputStream(destFile);
		            BufferedOutputStream dst = new BufferedOutputStream(fos, BUFFER_SZ);
	
		            // read and write until last byte is encountered
		            while ((currentByte = is.read(data, 0, BUFFER_SZ)) != -1) {
		                dst.write(data, 0, currentByte);
		            }
		            dst.flush();
		            dst.close();
		            is.close();
		        }
		        /*
		        if (currentEntry.endsWith(".zip"))
		        {
		            // found a zip file, try to open
		            extractFolder(destFile.getAbsolutePath());
		        }*/
		    }
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Untar an input file into an output file.
	 * 
	 * The output file is created in the output folder, having the same name as
	 * the input file, minus the '.tar' extension.
	 * 
	 * @param inputFile
	 *            the input .tar file
	 * @param outputDir
	 *            the output directory file.
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 * @return The {@link List} of {@link File}s with the untared content.
	 * @throws ArchiveException
	 */
	private static List<File> unTar(final File inputFile, final File outputDir) throws FileNotFoundException, IOException, ArchiveException {
		// LOG.info(String.format("Untaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

		final List<File> untaredFiles = new LinkedList<File>();
		final InputStream is = new FileInputStream(inputFile);
		final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
		TarArchiveEntry entry = null;
		while((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
			final File outputFile = new File(outputDir, entry.getName());
			if(entry.isDirectory()) {
				// LOG.info(String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
				if(!outputFile.exists()) {
					// LOG.info(String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
					if(!outputFile.mkdirs()) {
						throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
					}
				}
			} else {
				// LOG.info(String.format("Creating output file %s.", outputFile.getAbsolutePath()));
				final OutputStream outputFileStream = new FileOutputStream(outputFile);
				IOUtils.copy(debInputStream, outputFileStream);
				outputFileStream.close();
			}
			untaredFiles.add(outputFile);
		}
		debInputStream.close();

		return untaredFiles;
	}

	/**
	 * Ungzip an input file into an output file.
	 * <p>
	 * The output file is created in the output folder, having the same name as
	 * the input file, minus the '.gz' extension.
	 * 
	 * @param inputFile
	 *            the input .gz file
	 * @param outputDir
	 *            the output directory file.
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 * @return The {@File} with the ungzipped content.
	 */
	private static File unGzip(final File inputFile, final File outputDir) throws FileNotFoundException, IOException {
		// LOG.info(String.format("Ungzipping %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

		final File outputFile = new File(outputDir, inputFile.getName().substring(0, inputFile.getName().length() - 3));

		final GZIPInputStream in = new GZIPInputStream(new FileInputStream(inputFile));
		final FileOutputStream out = new FileOutputStream(outputFile);

		IOUtils.copy(in, out);

		in.close();
		out.close();

		return outputFile;
	}

	public static void untar(URL _url, String _dest) {
		try {
			ReadableByteChannel rbc = Channels.newChannel(_url.openStream());
			FileOutputStream fos = new FileOutputStream(_dest);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
