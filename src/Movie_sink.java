import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.*;
import java.util.Arrays;

public class Movie_sink {
	 public static void main(String[] args) throws IOException 
	  {
		 FileOutputStream fout =  new FileOutputStream("movie_sink.txt");
		 PrintStream pout = new PrintStream (fout);
		 DatagramSocket socket = new DatagramSocket(4445);
		 byte[] buf = new byte[2048];
		 DatagramPacket p = new DatagramPacket(buf, buf.length);
		 System.out.println("Waiting ..."); 
		 long nLastTime = -1;
		 int nSeq = 1;
		 long nInitTime = -1;
		 while(true)
		 {
			 long nGap = 0;
			 socket.receive(p);
			 long nCurrentTime = System.nanoTime()/1000;
			 if(nLastTime != -1)
				 nGap = nCurrentTime - nLastTime;
			 if(nInitTime == -1)
				 nInitTime = nCurrentTime;
			 nLastTime = nCurrentTime;
			 System.out.println("length: " + p.getLength() + " Time since previous: " + nGap);
			 pout.println(nSeq+ "\t"+  (nCurrentTime - nInitTime) + "\t" + p.getLength()); 
			 nSeq++;
			 if(p.getLength() == 0)
				 break;
		 }
		 if(socket != null)
		    socket.close();
		 if(pout != null)
		    pout.close();
	  }
}
