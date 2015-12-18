package fr.xs.jtk.helpers.media;

import java.io.*;

public class FLVtoMP3 {

	public static int convert(String input, String output){
        // reading
		FileOutputStream fos = null;
        DataOutputStream dos = null;
        // writting
        FileInputStream fis = null;
        DataInputStream dis = null;

        int bufsize = 4096;

        try{
            fis = new FileInputStream(input);
            BufferedInputStream bis = new BufferedInputStream( fis, bufsize);
            dis = new DataInputStream(bis);

            File file = new File(output);
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);

            int i = dis.readInt(); //FLV
            if (i == 0x464C5601){
            	dis.readByte(); // read flags
            	dis.readInt(); // read headers size
                int prevTagSize = dis.readInt();
                while(prevTagSize >= 0){ //
                	int eTagType = 0;
              	  	eTagType = dis.readByte(); // read tag type
              	    int nLength = getUI24(dis); // read tag lenght
              	    dis.readInt(); // read time stamp
              	    getUI24(dis); // streamID
              	    if (nLength > 0) {
              	        dis.readByte(); // read MediaType

              	        byte[] xbData = new byte[nLength - 1];
              	        dis.read(xbData, 0, nLength -1);

              	        // write only audio data
              	        if (eTagType == 0x08){
              	            dos.write(xbData, 0, nLength - 1);
              	        }
              	    }else{
              	    	break;
              	    }
              	    try{
              	    	prevTagSize = dis.readInt();
	            	}catch (Exception e) {
	                    dos.close();
	                    fos.close();
						return 0;
					}
                }
                dos.close();
                fos.close();
            }else {
            	return -2; // not flv
            }
        } catch (IOException e) {
        	e.printStackTrace();
        	return -1;
		}
		return 0;
	}

    public static void main(String[] args) {
    	int r = convert("C:\\flv.flv", "C:\\mp3.mp3");
    	System.out.print(r);
    }

    private static int getUI24(DataInputStream br){
        byte[] cbuff = new byte[3];
        try {
        	br.read(cbuff, 0, 3);
        } catch (IOException e) {
        }
        return bytesToInt(cbuff);
    }

    private static int bytesToInt(byte[] bytes)
    {
        int value = 0;
        for(int i=0; i<bytes.length; i++){
            value = value << 8;
            value += bytes[i] & 0xff;
        }
        return value;
    }

}
