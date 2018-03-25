import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.*;
import java.util.Arrays;

public class Movie_generator {
	public static void main(String[] args) throws IOException {
		//file stuff
		BufferedReader bis = null; 
		String currentLine = null;
		File fin = new File("movietrace.data");
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
            String col4 = st.nextToken(); 
			
			int nSeq = Integer.parseInt(col1);
			int fSize = Integer.parseInt(col4);
            
            // Check if the delay is long enough. If not, go into the while lock.
            while(nTime != -1 && System.nanoTime() - nTime < 33*1000000);
			if(nTime == -1)
                nTime = System.nanoTime();
                
            while(fSize > 1480){
                byte[] buf = new byte[1480];
			//	Arrays.fill(buf, (byte)'a');
				buf[0] = (byte)2;
                DatagramPacket packet =
					 new DatagramPacket(buf, buf.length, addr, 4444);
				System.out.println("Packt priority: "+ buf[0]);
                socket.send(packet);
                fSize = fSize - 1480;
            }
			byte[] buf = new byte[fSize];
			//	Arrays.fill(buf, (byte)'a');
			buf[0] = (byte)2;
			DatagramPacket packet =
					 new DatagramPacket(buf, buf.length, addr, 4444);
			System.out.println("Packt priority: "+ buf[0]);
			socket.send(packet);
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
