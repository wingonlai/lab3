import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.*;
import java.util.Arrays;

public class EX2_sink {
	 public static void main(String[] args) throws IOException 
	  {
		 FileOutputStream fout =  new FileOutputStream("possion_output.txt");
		 FileOutputStream fout2 =  new FileOutputStream("movie_output.txt");
		 PrintStream pout = new PrintStream (fout);
		 PrintStream pout2 = new PrintStream (fout2);
		 DatagramSocket socket = new DatagramSocket(4445);
		 byte[] buf = new byte[4096];
		 DatagramPacket p = new DatagramPacket(buf, buf.length);
		 System.out.println("Waiting ..."); 
		 long nLastTime = -1;
		 int nSeq = 1;
		 int nSeq2 = 1;
		 long nInitTime = -1;
		 long nInitTime2 = -1;
		 int Priority = -1;
		 while(true)
		 {
			 long nGap = 0;
			 socket.receive(p);
			 long nCurrentTime = System.nanoTime()/1000;
			 if(nLastTime != -1)
				 nGap = nCurrentTime - nLastTime;
			 if(nInitTime == -1)
				 nInitTime = nCurrentTime;
			 if(nInitTime2 == -1)
				 nInitTime2 = nCurrentTime;
			 nLastTime = nCurrentTime;
			 Priority = (int)p.getData()[0];
			 if (Priority == 1){
				System.out.println("length: " + p.getLength() + " Time since previous: " + nGap);
				if (nSeq == 1)
					pout.println(nSeq+ "\t"+  0 + "\t" + p.getLength()); 
				else
					pout.println(nSeq+ "\t"+  (nCurrentTime - nInitTime) + "\t" + p.getLength()); 
				nSeq ++;
			 }
			 else if (Priority == 2){
				System.out.println("length: " + p.getLength() + " Time since previous: " + nGap);
				if (nSeq2 == 1)
					pout2.println(nSeq2+ "\t"+  0 + "\t" + p.getLength()); 
				else
					pout2.println(nSeq2+ "\t"+  (nCurrentTime - nInitTime2) + "\t" + p.getLength()); 
				nSeq2++;
			 }
			 if(p.getLength() == 0)
				 break;
		 }
		 if(socket != null)
		    socket.close();
		 if(pout != null)
			pout.close();
		 if(pout2 != null)
		 	pout2.close();
	  }
}
