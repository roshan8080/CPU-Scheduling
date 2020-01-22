/** RIJAL_ROSHAN_CPU_Sched.java
  * CSCI 4011
  * Assignment 4
  * @author: Roshan Rijal
  * Due Date: 03/28/2019
  * Class that simulates the CPU scheduling algorithms: First Come First Served, Shortest Job Next Non-Preemptive, and
    Priority Non- Preemptive by reading CPU requests inputs from the given file   
  */
  




//imports
import java.io.*;         
import java.util.*;

public class RIJAL_ROSHAN_CPU_Sched{
   public static void main(String[] args){
      //stores the CPU scheduling algorithms used in the program
      String algorithm = "";
      //counter value to represent the line from the file
      int counter = 0;
      //determines what line is being read from the input file  
      int counter1; 
      //determines number of process from the file 
      int noOfProcess= 0;
      //determines what line is being read from the input file 
      int linesInFile=0;
      // counter value to count the number of times the loop is executed
      int index=0; 
      
      //try-catch block to handle exceptions
      try{ 
         //reads input from the file
         Scanner input = new Scanner(new File("in.txt")); 
         //returns true if there is another line in the input of this scanner 
         while (input.hasNextLine()){
            //stores first line of the file
            String eachLine = input.nextLine(); 
            if (counter == 0){
               //stores the CPU scheduling algorithms used in the program
               algorithm = eachLine.substring(2);
               counter++;
            } 
            linesInFile++;   
         }
         //closes the file
         input.close();
         // calculates the number of process by subtracting 1 from total lines in file 
         noOfProcess=linesInFile-1;
         //array to store process ID of processes
         int[] pid=new int[noOfProcess];
         //array to store arrival time of processes
         int[] at=new int[noOfProcess];
         //array to store CPU burst time of processes
         int[] bt=new int[noOfProcess];
         //array to store exit time  of processes
         int[] et=new int[noOfProcess];
         //array to store turn around time of processes
         int[] tat=new int[noOfProcess];
         //array to store wait time of processes
         int[] wt=new int[noOfProcess];
         //array to store clock time of processes
         int[] clock=new int[noOfProcess];
         //stores pid, arrival time,  burst time, priority as specified by the algorithm
         LinkedList<String> object = new LinkedList<String>(); 
         //stores pid, arrival time,  burst time, priority after sorting them as specified by the algorithm 
         LinkedList<String> order = new LinkedList<String>();
         //stores total turn around time of all processes
         int totalTat=0;
         //stores total wait time of all processes
         int totalWt=0;
         //stores average turn around time of all processes
         double avgTat=0;
         //stores average wait time of all processes
         double avgWt=0;
         // stores the removed process after it completes its execution
         String servicedProcess="";   
         
         
      
         
                      //FIRST COME FIRST SERVED
                        
                      
               
         //executes if CPU scheduling algorithm is FIRST COME FIRST SERVED
         if (algorithm.equals("fcfs")){
            //reads input from the file
            Scanner in = new Scanner(new File("in.txt"));
            // determines if the line that is being read from the file is the first line 
            counter1=0;
            //returns true if there is another line in the input of this scanner
            while (in.hasNextLine()){
               //advances this scanner past the current line and returns the input that was skipped
               String line = in.nextLine();
               // executes only if first line is being read  
               if (counter1>0){
                  //reads PID from the file
                  pid[index]=Integer.parseInt(line.substring(2,3));
                  //reads arrival time from the file
                  at[index]=Integer.parseInt(line.substring(4,5));
                  //reads CPU burst time from the file
                  bt[index]=Integer.parseInt(line.substring(6,7));
                  index++;
               }
               counter1++;
            }
            //closes the file  
            in.close();
            //stores pid, arrival time, and CPU time in a linked list 
            for(int i=0;i<noOfProcess;i++){
               object.add(pid[i] + " " + at[i] + " " + bt[i]);
            }
            //selection sort to arrange arays: pid[], at[], and bt[] on the basis of increasing arrival time
            int temp=0;
            for (int i = 0; i < at.length-1; i++){
               int min = i;
               for (int j = i+1; j < at.length; j++)
                  if (at[j] < at[min]) min = j;
               temp = at[i];
               at[i] = at[min];
               at[min] = temp;
               temp = bt[i];
               bt[i] = bt[min];
               bt[min] = temp;
               temp = pid[i];
               pid[i] = pid[min];
               pid[min] = temp;       
            }
            //calculates turn around time and wait time         
            for(int  i = 0 ; i < noOfProcess; i++){
               if( i == 0){	
                  et[i] = at[i] + bt[i];
               }
               else{
                  if( at[i] > et[i-1]){
                     et[i] = at[i] + bt[i];
                  }
                  else
                     et[i] = et[i-1] + bt[i];
               }
               //turnaround time = exit time - arrival time
               tat[i] = et[i] - at[i] ;          
               //waiting time = turnaround time - CPU burst time
               wt[i] = tat[i] - bt[i] ;          
               //total wait time = sum of waiting time of all process
               totalWt += wt[i] ; 
               //total turn around time = sum of turn around time of all process              
               totalTat += tat[i] ;               
            }
            //calculates average turn around time 
            avgTat=totalTat/(double)noOfProcess;
            //calculates average wait time
            avgWt=totalWt/(double)noOfProcess;
            
            clock[0]=0;
            //calculating clock time
            for (int i=1;i<noOfProcess;i++){
               clock[i]=et[i-1];
            }
            //stores pid, arrival time, and CPU time bases on its order of execution after sorting  
            for (int i=0;i<noOfProcess;i++){
               order.add(pid[i] + " " + at[i] + " " + bt[i]);
            }
            //writes in the file 
            PrintWriter out=new PrintWriter("fcfs_out.txt"); 
            out.println("CPU sheduling algorithm: " + algorithm);
            out.println("Total number of CPU requests: " + noOfProcess);
            out.println("---------------------------------------------------------");
            for(int i=0; i<order.size(); i++){
               //writes clock time 
               out.println("Clock: " + clock[i]);
               out.println("Pending CPU request(s):" );  
               //writes pending CPU requests by retrieving data "object" from linked list
               for(int j=0; j<object.size(); j++){
                  out.print(object.get(j) +"\n");
               }
               //gets the index of the process that completed its execution from "order" linked list  and compares 
               //it with "object" linked list so as to find the index of that process in "object" linked list  
               int remove_index= object.indexOf(order.get(i));
               //removes the process from "object" linked with the help of index that is stored in above code  
               servicedProcess = object.remove(remove_index);
               //writes the process that currently completed its execution
               out.println("\nCPU Request serviced during this clock interval: " + servicedProcess);
               out.println("---------------------------------------------------------");
            }         
            out.println("Turn-Around Time Computations\n");
            for(int i=0;i<noOfProcess;i++){
               //writes the turn around time for each process 
               out.println("TAT(" + pid[i] + ")" + " = " + tat[i]);
            }
            out.println("\nAverage TAT = " + avgTat);
            out.println("---------------------------------------------------------");
            out.println("Wait Time Computations\n");
            for(int i=0;i<noOfProcess;i++){
               //writes the wait time for each process
               out.println("WT(" + pid[i] + ")" + " = " + wt[i]);
            }
            out.println("\nAverage WT = " + avgWt);
            //closes the file
            out.close();
         }
                            
                   
                                // SHORTEST JOB NEXT NON-PREEMPTIVE
                                 
                                  
                                                              
         //executes if CPU scheduling algorithm is SHORTEST JOB NEXT NON-PREEMPTIVE          
         if (algorithm.equals("sjnnp")){
            //reads input from the file
            Scanner in = new Scanner(new File("in.txt"));
            // determines if the line that is being read from the file is the first line 
            counter1=0;
            //returns true if there is another line in the input of this scanner
            while (in.hasNextLine()){
               //advances this scanner past the current line and returns the input that was skipped
               String line = in.nextLine();
               // executes only if first line is being read
               if (counter1>0){
                  //reads PID from the file
                  pid[index]=Integer.parseInt(line.substring(2,3));
                  //reads arrival time from the file
                  at[index]=Integer.parseInt(line.substring(4,5));
                  //reads CPU burst time from the file
                  bt[index]=Integer.parseInt(line.substring(6,7));
                  index++;
               }
               counter1++;
            } 
            //closes the file  
            in.close();
            //stores pid, arrival time, and CPU time in a linked list
            for(int i=0;i<noOfProcess;i++){
               object.add(pid[i] + " " + at[i] + " " + bt[i]);
            }
            //selection sort to arrange arays: pid[], at[], and bt[] on the basis of increasing CPU burst time
            int temp=0;
            for (int i = 0; i < at.length-1; i++){
               int min = i;
               for (int j = i+1; j < at.length; j++)
                  if (bt[j] < bt[min]){
                     min = j;
                  }
               temp = at[i];
               at[i] = at[min];
               at[min] = temp;
               temp = bt[i];
               bt[i] = bt[min];
               bt[min] = temp;
               temp = pid[i];
               pid[i] = pid[min];
               pid[min] = temp;       
            }
            //calculates turn around time and wait time
            for(int  i = 0 ; i < noOfProcess; i++){
               if( i == 0){	
                  et[i] = at[i] + bt[i];
               }
               else{
                  if( at[i] > et[i-1]){
                     et[i] = at[i] + bt[i];
                  }
                  else
                     et[i] = et[i-1] + bt[i];
               }
               //turnaround time = exit time - arrival time
               tat[i] = et[i] - at[i] ;          
               //waiting time = turnaround time - CPU burst time
               wt[i] = tat[i] - bt[i] ;          
               //total wait time = sum of waiting time of all process
               totalWt += wt[i] ;               
               //total turn around time = sum of turn around time of all process
               totalTat += tat[i] ;               
            }
            //calculates average turn around time
            avgTat=totalTat/(double)noOfProcess;
            //calculates average wait time
            avgWt=totalWt/(double)noOfProcess;
            
            clock[0]=0;
            //calculating clock time
            for (int i=1;i<noOfProcess;i++){
               clock[i]=et[i-1];
            }
            //stores pid, arrival time, and CPU time bases on its order of execution after sorting
            for (int i=0;i<noOfProcess;i++){
               order.add(pid[i] + " " + at[i] + " " + bt[i]);
            }
            //writes in the file 
            PrintWriter out=new PrintWriter("sjnnp_out.txt"); 
            out.println("CPU sheduling algorithm: " + algorithm);
            out.println("Total number of CPU requests: " + noOfProcess);
            out.println("---------------------------------------------------------");
            for(int i=0; i<order.size(); i++){
               //writes clock time
               out.println("Clock: " + clock[i]);
               out.println("Pending CPU request(s):" );  
               //writes pending CPU requests by retrieving data "object" from linked list
               for(int j=0; j<object.size(); j++){
                  out.print(object.get(j) +"\n");
               }
               //gets the index of the process that completed its execution from "order" linked list  and compares 
               //it with "object" linked list so as to find the index of that process in "object" linked list
               int remove_index= object.indexOf(order.get(i));
               //removes the process from "object" linked with the help of index that is stored in above code
               servicedProcess = object.remove(remove_index);
               out.println("\nCPU Request serviced during this clock interval: " + servicedProcess);
               out.println("---------------------------------------------------------");
            }         
            out.println("Turn-Around Time Computations\n");
            for(int i=0;i<noOfProcess;i++){
               //writes the turn around time for each process
               out.println("TAT(" + pid[i] + ")" + " = " + tat[i]);
            }
            out.println("\nAverage TAT = " + avgTat);
            out.println("---------------------------------------------------------");
            out.println("Wait Time Computations\n");
            for(int i=0;i<noOfProcess;i++){
               //writes the wait time for each process
               out.println("WT(" + pid[i] + ")" + " = " + wt[i]);
            }
            out.println("\nAverage WT = " + avgWt);
            //closes the file
            out.close();   
         } 
         
         
         
                     // PRIORITY NON PREEMPTIVE
         
         
                     
         //executes if CPU scheduling algorithm is PRIORITY NON PREEMPTIVE            
         if (algorithm.equals("pnp")){
            //reads input from the file
            Scanner in = new Scanner(new File("in.txt"));
            // determines if the line that is being read from the file is the first line
            counter1=0;
            // array to store priority number of processes
            int[] pnp=new int[noOfProcess];
            //returns true if there is another line in the input of this scanner
            while (in.hasNextLine()){
               //advances this scanner past the current line and returns the input that was skipped
               String line = in.nextLine();
               // executes only if first line is being read
               if (counter1>0){
                  //reads PID from the file
                  pid[index]=Integer.parseInt(line.substring(2,3));
                  //reads arrival time from the file
                  at[index]=Integer.parseInt(line.substring(4,5));
                  //reads CPU burst time from the file
                  bt[index]=Integer.parseInt(line.substring(6,7));
                  //reads priority from the file
                  pnp[index]=Integer.parseInt(line.substring(8,9));
                  index++;
               }
               counter1++;
            }
            //closes the file  
            in.close();
            //stores pid, arrival time, CPU time, and priority in a linked list
            for(int i=0;i<noOfProcess;i++){
               object.add(pid[i] + " " + at[i] + " " + bt[i] + " " + pnp[i]);
            }
            //selection sort to arrange arays: pid[], at[], and bt[] on the basis of increasing priority 
            int temp=0;
            for (int i = 0; i < at.length-1; i++){
               int min = i;
               for (int j = i+1; j < pnp.length; j++)
                  if (pnp[j] <= pnp[min]){
                     min = j;
                  }
               temp = at[i];
               at[i] = at[min];
               at[min] = temp;
               temp = bt[i];
               bt[i] = bt[min];
               bt[min] = temp;
               temp = pid[i];
               pid[i] = pid[min];
               pid[min] = temp; 
               temp = pnp[i];
               pnp[i] = pnp[min];
               pnp[min] = temp;       
            }
            //calculates turn around time and wait time  
            for(int  i = 0 ; i < noOfProcess; i++){
               if( i == 0){	
                  et[i] = at[i] + bt[i];
               }
               else{
                  if( at[i] > et[i-1]){
                     et[i] = at[i] + bt[i];
                  }
                  else
                     et[i] = et[i-1] + bt[i];
               }
               //turnaround time = exit time - arrival time
               tat[i] = et[i] - at[i] ;          
               //waiting time = turnaround time - CPU burst time
               wt[i] = tat[i] - bt[i] ;          
               //total wait time = sum of waiting time of all process
               totalWt += wt[i] ;               
               //total turn around time = sum of turn around time of all process
               totalTat += tat[i] ;               
            }
            //calculates average turn around time
            avgTat=totalTat/(double)noOfProcess;
            //calculates average wait time
            avgWt=totalWt/(double)noOfProcess;
            
            clock[0]=0;
            //calculating clock time
            for (int i=1;i<noOfProcess;i++){
               clock[i]=et[i-1];
            }
            //stores pid, arrival time, and CPU time bases on its order of execution after sorting
            for (int i=0;i<noOfProcess;i++){
               order.add(pid[i] + " " + at[i] + " " + bt[i] + " " + pnp[i]);
            }
            //writes in the file 
            PrintWriter out=new PrintWriter("pnp_out.txt"); 
            out.println("CPU sheduling algorithm: " + algorithm);
            out.println("Total number of CPU requests: " + noOfProcess);
            out.println("---------------------------------------------------------");
            for(int i=0; i<order.size(); i++){
               //writes clock time
               out.println("Clock: " + clock[i]);
               out.println("Pending CPU request(s):" );  
               //writes pending CPU requests by retrieving data "object" from linked list
               for(int j=0; j<object.size(); j++){
                  out.print(object.get(j) +"\n");
               }
               //gets the index of the process that completed its execution from "order" linked list  and compares 
               //it with "object" linked list so as to find the index of that process in "object" linked list
               int remove_index= object.indexOf(order.get(i));
               //removes the process from "object" linked with the help of index that is stored in above code
               servicedProcess = object.remove(remove_index);          
               out.println("\nCPU Request serviced during this clock interval: " + servicedProcess);
               out.println("---------------------------------------------------------");
            }         
            out.println("Turn-Around Time Computations\n");
            for(int i=0;i<noOfProcess;i++){
               //writes the turn around time for each process
               out.println("TAT(" + pid[i] + ")" + " = " + tat[i]);
            }
            out.println("\nAverage TAT = " + avgTat);
            out.println("---------------------------------------------------------");
            out.println("Wait Time Computations\n");
            for(int i=0;i<noOfProcess;i++){
               //writes the wait time for each process
               out.println("WT(" + pid[i] + ")" + " = " + wt[i]);
            }
            out.println("\nAverage WT = " + avgWt);
            //closes the file
            out.close();
         }
      }
      catch(FileNotFoundException e){
         System.out.println("File not found or cannot be read"); 
      }  
   }
}