import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;

// adb forward tcp:12345 tcp:12345
public class Client {
  public static void main(String[] args) throws Exception {
    //Check if folder is empty.If it's not we clear it.
    String fileNameA,fileNameB;
    File folder1 = new File("C:\\Users\\kosta\\Desktop\\asyrmata\\filesA");
    File folder2 = new File("C:\\Users\\kosta\\Desktop\\asyrmata\\filesB");

    if (folder1.exists() && folder1.isDirectory()) {
        File[] files = folder1.listFiles();
        if (files.length > 0) {
            for (File file : files) {
                file.delete();
            }
          }
    }

    if (folder2.exists() && folder2.isDirectory()) {
        File[] files = folder2.listFiles();
        if (files.length > 0) {
            for (File file : files) {
                file.delete();
            }
          }
    }
    Scanner console = new Scanner(System.in);
    long startTime = System.currentTimeMillis();

    boolean a=true;
    String client="",IP_A="",IP_B="";
    int n_A=0,n_B=0;
    while(a){
      System.out.print("Give the information like this(<<client n_A n_B IP_A IP_B>>)\n");
      String input = console.nextLine();
      client=input.substring(0,6);
      if(client.equals("client")){
        a=false;
      }else{
        a=true;
      }
      n_A=Integer.parseInt(input.substring(7, input.indexOf(" ",input.indexOf(" ")+1)));
      int index=input.indexOf(" ",input.indexOf(" ")+1);
      n_B=Integer.parseInt(input.substring(index+1,input.indexOf(" ",index+1)));
      if(n_B>0 && n_B<168 &&n_A>0 && n_A<=168 && n_A+n_B<=168){
        a=false;
      }else{
        a=true;
      }
      index=input.indexOf(" ",index+1);
      IP_A=input.substring(index+1, input.indexOf(" ",index+1));
      if(IP_A.length()>=7 && IP_A.length()<=15){
        a=false;
      }else{
        a=true;
      }
      index=index=input.indexOf(" ",index+1);
      IP_B=input.substring(index+1,input.length());
      if(IP_B.length()>=7 && IP_B.length()<=15){
        a=false;
      }else{
        a=true;
      }

    }
    if(n_A<=n_B){
      int index1=1;
      int index2=index1+n_B;
      while(true){
        // Set the server address and port number
        String serverAddressA = IP_A;
        int portNumberA = 4000;
        for(int i=index1;i<index1+n_A;i++){
          // Connect to the server
          Socket socketA = new Socket(serverAddressA, portNumberA);
          // Create a PrintWriter to write to the socket
          PrintWriter outA = new PrintWriter(socketA.getOutputStream(), true);
          // Create a BufferedReader to read from the socket
          BufferedReader inA = new BufferedReader(new InputStreamReader(socketA.getInputStream()));

          // Modify fileName for each case
          if(i<=9&&i>=0){
            fileNameA="s00"+i+".m4s";
          }else if(i<=99&&i>=0){
            fileNameA="s0"+i+".m4s";
          }else{
            fileNameA="s"+i+".m4s";
          }
          // Send the file name to the server
          outA.println(fileNameA);

          // Create a FileOutputStream to write to the local file
          FileOutputStream fileOutA = new FileOutputStream("C:\\Users\\kosta\\Desktop\\asyrmata\\filesA\\" + fileNameA);

          // Read the contents of the file from the socket
          String lineA;
          while ((lineA = inA.readLine()) != null) {
            // Write the line to the local file
            fileOutA.write((lineA + "\n").getBytes());
          }
          // Close the local file
          fileOutA.close();
          // Close the socket
          socketA.close();
          //if all files sent break
          if(i==160){
            break;
          }
        }
        // Set the server address and port number for serverB
        String serverAddressB = IP_B;
        int portNumberB = 12345;
        for(int i=index1+1;i<=index2;i++){
          if(i>160){
            break;
          }
          // Connect to the serverB
          Socket socketB = new Socket(serverAddressB, portNumberB);
          // Create a PrintWriter to write to the socket for Server B
          PrintWriter outB = new PrintWriter(socketB.getOutputStream(), true);
          // Create a BufferedReader to read from the socket for Server B
          BufferedReader inB = new BufferedReader(new InputStreamReader(socketB.getInputStream()));

          if(i<=9&&i>=0){
            fileNameB="s00"+i+".m4s";
          }else if(i<=99&&i>=0){
            fileNameB="s0"+i+".m4s";
          }else{
            fileNameB="s"+i+".m4s";
          }
          // Send the file name to the server
          outB.println(fileNameB);
          // Create a FileOutputStream to write to the local file
          FileOutputStream fileOutB = new FileOutputStream("C:\\Users\\kosta\\Desktop\\asyrmata\\filesB\\" + fileNameB);
          // Read the contents of the file from the socket for Server B
          String lineB;
          while ((lineB = inB.readLine()) != null) {
            // Write the line to the local file 
            fileOutB.write((lineB + "\n").getBytes());
          }
          // Close the local file
          fileOutB.close();
        
          socketB.close();
          if(i==160){
            break;
          }
        }
        // Close the socket
        //break;
        if(index1>=160 || index2>=160){
          break;
        }
        index1=index2+1;
        index2=index1+n_B;
      }
    }else{
      int index1=1;
      int index2=index1+n_A;
      while(true){
        String serverAddressA = IP_A;
        int portNumberA = 4000;
        for(int i=index1;i<index1+n_A;i++){
          Socket socketA = new Socket(serverAddressA, portNumberA);
          PrintWriter outA = new PrintWriter(socketA.getOutputStream(), true);
          BufferedReader inA = new BufferedReader(new InputStreamReader(socketA.getInputStream()));
          if(i<=9&&i>=0){
            fileNameA="s00"+i+".m4s";
          }else if(i<=99&&i>=0){
            fileNameA="s0"+i+".m4s";
          }else{
            fileNameA="s"+i+".m4s";
          }
          outA.println(fileNameA);
          FileOutputStream fileOutA = new FileOutputStream("C:\\Users\\kosta\\Desktop\\asyrmata\\filesA\\" + fileNameA);
          String lineA;
          while ((lineA = inA.readLine()) != null) {
            // Write the line to the local file
            fileOutA.write((lineA + "\n").getBytes());
          }
          // Close the local file
          fileOutA.close();
          // Close the socket
          socketA.close();
          if(i==160){
            break;
          }
        }
        String serverAddressB = IP_B;
        int portNumberB = 12345;
        for(int i=index1+n_A;i<=index2;i++){
          if(i>160){
            break;
          }
          Socket socketB = new Socket(serverAddressB, portNumberB);
          PrintWriter outB = new PrintWriter(socketB.getOutputStream(), true);
          BufferedReader inB = new BufferedReader(new InputStreamReader(socketB.getInputStream()));

          if(i<=9&&i>=0){
            fileNameB="s00"+i+".m4s";
          }else if(i<=99&&i>=0){
            fileNameB="s0"+i+".m4s";
          }else{
            fileNameB="s"+i+".m4s";
          }

          outB.println(fileNameB);

          FileOutputStream fileOutB = new FileOutputStream("C:\\Users\\kosta\\Desktop\\asyrmata\\filesB\\" + fileNameB);
          String lineB;
          while ((lineB = inB.readLine()) != null) {
            fileOutB.write((lineB + "\n").getBytes());
          }

          fileOutB.close();
        
          socketB.close();
          if(i==160){
            break;
          }
        }
        
        if(index1>=160 || index2>=160){
          break;
        }
        index1=index2+1;
        index2=index1+n_A;
      }
    }
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    double minutes = Math.round((duration/60000.0)*100000.0)/100000.0;

    System.out.println("Duration of transfering all files is: "+minutes+" minutes");
  }
}