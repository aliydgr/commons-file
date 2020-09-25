package me.ali.commons.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloader {
	
	private final static int size = 1024;

	private static void downladByUrlConnection(URLConnection c, String output) throws IOException{
        int byteRead, byteWritten = 0;
        try(OutputStream outStream = new BufferedOutputStream(new FileOutputStream(output));
                InputStream is = c.getInputStream()) {
        	System.out.println("Download Started.");
        	byte[] buf = new byte[size];
        	while ((byteRead = is.read(buf)) != -1) {
        		outStream.write(buf, 0, byteRead);
        		byteWritten += byteRead;
        		System.out.println("Downloaded Bytes: " + byteWritten);
        	}
        	System.out.println("Downloaded Successfully.");
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isUrlValid(String url){
		int slashIndex = url.lastIndexOf('/');
		int periodIndex = url.lastIndexOf('.');
		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < url.length() - 1)
			return true;
		
		System.err.println("path or file name.");
		return false;
	}
	
    public static void downloadFile(String downloadAddress, String destinationFile) {
    	if(isUrlValid(downloadAddress)){
        	try {
        		URLConnection c = new URL(downloadAddress).openConnection();
        		downladByUrlConnection(c, destinationFile);
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
    }
    
    public static void downloadFile(String downloadAddress, String destinationFile, String proxyHost, int proxyPort) {
    	if(isUrlValid(downloadAddress)){
        	try {
        		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        		URLConnection c = new URL(downloadAddress).openConnection(proxy);
        		downladByUrlConnection(c, destinationFile);
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
    }
}
