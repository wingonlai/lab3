import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.*;
import java.util.Arrays;

public class EX3_generator {
	public static void main(String[] args) throws IOException {
		//file stuff
		BufferedReader bis = null; 
		String currentLine = null;
		File fin = new File("poisson3.data");
		FileReader fis = new FileReader(fin);
		int N = -1;
		int Priority = -1;
		bis = new BufferedReader(fis);
		
		//network stuff
		InetAddress addr = InetAddress.getByName(args[0]);
		DatagramSocket socket = new DatagramSocket();
		
		//Configure N & generator priority
		N = Integer.parseInt(args[1]);
		Priority = Integer.parseInt(args[2]);

		if(N <= 0 || Priority <= 0){
			System.out.println("Error: Did not set up N or Priority correctly. N and Priority should be all greater than 0");
			if(bis != null)
				bis.close();
			if(socket != null)
				socket.close();
			return;
		}
		currentLine = bis.readLine();
		long nTime = 0;
		long nLasttime = 0;		
		while (currentLine != null)
		{
			StringTokenizer st = new StringTokenizer(currentLine); 
			String col1 = st.nextToken(); 
			String col2 = st.nextToken(); 
			String col3  = st.nextToken(); 
			
			int nSeq = Integer.parseInt(col1);
			int nATime = Integer.parseInt(col2);
			int nSize = Integer.parseInt(col3);
            
            long nDelay = (nATime - nLasttime) * 1000;
			byte[] buf = new byte[nSize];
			buf[0] = (byte)Priority;
			System.out.println("Packt priority: "+ buf[0]);
			DatagramPacket packet =
					 new DatagramPacket(buf, buf.length, addr, 4444);
			nDelay = nDelay / N;
			while(System.nanoTime() - nTime < nDelay);
			socket.send(packet);
			nLasttime = nATime;
			nTime = System.nanoTime();
			currentLine = bis.readLine();
		}
		System.out.println("finished!");
		if(bis != null)
			bis.close();
		if(socket != null)
			socket.close();
	}
}
