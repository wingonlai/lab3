import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.*;
import java.util.Arrays;

public class Generator {
	public static void main(String[] args) throws IOException {
		//file stuff
		BufferedReader bis = null; 
		String currentLine = null;
		File fin = new File("/nfs/ug/homes-5/l/laiyong/workspace/lab3/poisson3.data");
		FileReader fis = new FileReader(fin);
		bis = new BufferedReader(fis);
		
		//network stuff
		InetAddress addr = InetAddress.getByName(args[0]);
		DatagramSocket socket = new DatagramSocket();
		
		currentLine = bis.readLine();
		long nTime = -1;
		while (currentLine != null)
		{
			StringTokenizer st = new StringTokenizer(currentLine); 
			String col1 = st.nextToken(); 
			String col2 = st.nextToken(); 
			String col3  = st.nextToken(); 
			
			int nSeq = Integer.parseInt(col1);
			int nDelay = Integer.parseInt(col2);
			int nSize = Integer.parseInt(col3);
			
			byte[] buf = new byte[nSize];
			DatagramPacket packet =
	                 new DatagramPacket(buf, buf.length, addr, 4444);
			while(nTime != -1 && System.nanoTime()/1000 - nTime < nDelay);
			if(nTime == -1)
				nTime = System.nanoTime()/1000;
			socket.send(packet);
			currentLine = bis.readLine();
		}
		System.out.println("finished!");
		if(bis != null)
			bis.close();
		if(socket != null)
			socket.close();
	}
}
