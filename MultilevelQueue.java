/*A program for multilevel queue scheduling algorithm. There arethree
queues generated. There is a specific range of priority associated with every queue. I will 
prompt the user to enter number of processes along with their priority and burst time. Each
process will occupy the respective queue with specific priority range according to its priority.
Applied Round robin algorithm with quantum time 4 on queue with highest priority range. 
Applied priority scheduling algorithm on the queue with medium range of priority and First come first
serve algorithm on the queue with lowest range of priority. Each and every queue will get a
quantum time of 10 seconds. Cpu will keep on shifting between queues after every 10
seconds i.e. to apply round robin algorithm of 10 seconds on over all structure.
Calculate Waiting time and turnaround time for every process. The input for number of
processes will be given by the user.
*/

//ALGORITHM
    //Initiate 3 queues and associate specific range of priority with every queue
    //Enter number of processes along with their priority and burst time.
    //Each process should occupy respective queue
    //Apply Round Robin Algorithm (q=4) with highest priority range
    //Apply Priority Scheduling Algorithm on medium priority range
    //First Come First Serve on lowest priority range
    //Each queue will only get 10 seconds
    //Round Robin on overall structure

    //q1 : p1,p2,p3  |RR(4)    |
    //q2 : p4,p5,p6  |PS    ---| Round Robin (10)
    //q3 : p7,p8,p9  |FCFS     |

import java.util.*;

class process{
    int priority;
    int burst_time;
    int tt_time;
    int total_time;// Initialize to 0
}

class queues{
    int priority_start;
    int priority_end;
    int total_time=0;
    int length = 0;
    process[] p;//process *p;
    boolean executed;// = false;
}


class MultilevelQueue{
	
static boolean notComplete(queues q[]){
    boolean a =  false;
    int countInc = 0;
        for(int i = 0;i < 3; i++){
                countInc = 0;
            for(int j = 0;j < q[i].length;j++){
                if(q[i].p[j].burst_time != 0){
                    a = true;
                }
                else{
                    countInc += 1;
                }
            }
            if(countInc == q[i].length){

                q[i].executed = true;
            }
        }
        return a;
}



static void sort_ps(queues q){
    //Queue q has to be sorted according to priority of processes
    for(int i=1;i<q.length;i++){
        for(int j=0;j<q.length-1;j++){
            if(q.p[j].priority<q.p[j+1].priority){
                process temp = q.p[j+1];
                q.p[j+1] = q.p[j];
                q.p[j] = temp;
            }
        }
    }
}


static void checkCompleteTimer(queues q[]){
    boolean a = notComplete(q);
    for(int i=0;i<3;i++){
        if(q[i].executed==false){
            for(int j=0;j<q[i].length;j++){
                if(q[i].p[j].burst_time!=0){
                    q[i].p[j].total_time+=1;
                }
            }
            q[i].total_time+=1;
        }
    }
}
public static void main(String[] args){


	Scanner sc = new Scanner(System.in);
    //Initializing 3 queues
    queues q[] = new queues[3];
	for(int i = 0 ; i < 3; i++)
		q[i] = new queues();
    q[0].priority_start = 7;
    q[0].priority_end = 9;
    q[1].priority_start = 4;
    q[1].priority_end = 6;
    q[2].priority_start = 1;
    q[2].priority_end = 3;

    int no_of_processes,priority_of_process,burst_time_of_process;
    //Prompt User for entering Processes and assigning it to respective queues.
    System.out.println("Enter the number of processes:");
    no_of_processes = sc.nextInt();
	
    process[] p1 = new process[no_of_processes];
	for(int i = 0; i < no_of_processes; i++)
	{
		p1[i] = new process();
	}
    for(int i=0;i<no_of_processes;i++){
         System.out.println("Enter the priority of processes:");
        priority_of_process = sc.nextInt();
         System.out.println("Enter the burst_time of processes:");
         burst_time_of_process = sc.nextInt();
        p1[i].priority = priority_of_process;
        p1[i].burst_time = burst_time_of_process;
        p1[i].tt_time = burst_time_of_process;
        for(int j=0;j<3;j++){
        if(q[j].priority_start<=priority_of_process && priority_of_process<=q[j].priority_end){
            q[j].length++;
        }
        }
    }

    for(int i =0;i<3;i++){
        int len = q[i].length;
        q[i].p = new process[len];
    }


    int a=0;
    int b=0;
    int c=0;

    for(int i =0;i<3;i++){
        for(int j=0;j<no_of_processes;j++){
            if((q[i].priority_start<=p1[j].priority) && (p1[j].priority<=q[i].priority_end)){
                if(i==0){
                    q[i].p[a++] = p1[j];

                    }
                else if(i==1){
                    q[i].p[b++] = p1[j];
                    }
                else{
                    q[i].p[c++] = p1[j];
                    }
            }
        }
    }

    a--;b--;c--;
    for(int i=0;i<3;i++){
        System.out.println("Queue "+(i+1)+" :  ");
        for(int j=0;j<q[i].length;j++){
            System.out.println(q[i].p[j].priority+"->");
        }
        System.out.println("NULL");
    }


    //While RR on multiple queues is not complete, keep on repeating
    int timer = 0;
    int l =-1;
    int rr_timer = 4;
    int counter=0;
    int counterps=0;
    int counterfcfs=0;
    while(notComplete(q)){
        if(timer == 10){
            timer = 0;
        }
        l+=1;
        if(l>=3){
            l=l%3;
        }

        //Process lth queue if its already not executed
        //If its executed change the value of l
        if(q[l].executed == true){
                System.out.println("Queue "+(l+1)+" completed");
            l+=1;
            if(l>=3){
                l=l%3;
            }
            continue;
        }

        //Finally you now have a queue which is not completely executed
        //Process the incomplete processes over it

        if(l==0){
            System.out.println("Queue "+(l+1)+" in hand");
            //Round Robin Algorithm for q=4
            if(rr_timer == 0){
                rr_timer = 4;
            }

            for(int i=0;i<q[l].length;i++){
                if(q[l].p[i].burst_time==0){
                    counter++;
                    continue;
                }
                if(counter == q[l].length){
                    break;
                }
                while(rr_timer>0 && q[l].p[i].burst_time!=0 && timer!=10){
                    System.out.println("Executing queue 1 and "+(i+1)+" process for a unit time. Process has priority of "+(q[l].p[i].priority));
                    q[l].p[i].burst_time--;
                    checkCompleteTimer(q);
                    rr_timer--;
                    timer++;

                }
                if(timer == 10){
                    break;
                }
                if(q[l].p[i].burst_time==0 && rr_timer ==0){
                    rr_timer = 4;
                    if(i == (q[i].length-1)){
                        i=-1;
                    }
                    continue;
                }
                if(q[l].p[i].burst_time==0 && rr_timer > 0){
                    if(i == (q[i].length-1)){
                        i=-1;
                    }
                    continue;
                }
                if(rr_timer <= 0){
                    rr_timer = 4;
                    if(i == (q[i].length-1)){
                        i=-1;
                    }
                    continue;
                }

            }
        }


        else if(l==1){
            System.out.println("Queue "+(l+1)+" in hand");
            sort_ps(q[l]);
            //Priority Scheduling
            for(int i=0;i<q[l].length;i++){
                if(q[l].p[i].burst_time==0){
                    counterps++;
                    continue;
                }
                if(counterps == q[l].length){
                    break;
                }
                while(q[l].p[i].burst_time!=0 && timer!=10){
                    System.out.println("Executing queue 2 and "+(i+1)+" process for a unit time. Process has priority of "+(q[l].p[i].priority));
                    q[l].p[i].burst_time--;
                    checkCompleteTimer(q);
                    timer++;

                }
                if(timer == 10){
                    break;
                }
                if(q[l].p[i].burst_time==0){
                        continue;
                }

            }
        }
        else{
            System.out.println("Queue "+(l+1)+" in hand");
            //FCFS
            for(int i=0;i<q[l].length;i++){
                if(q[l].p[i].burst_time==0){
                    counterfcfs++;
                    continue;
                }
                if(counterfcfs == q[l].length){
                    break;
                }
                while(q[l].p[i].burst_time!=0 && timer!=10){
                    System.out.println("Executing queue 3 and "+(i+1)+" process for a unit time. Process has priority of "+(q[l].p[i].priority));
                    q[l].p[i].burst_time--;
                    checkCompleteTimer(q);

                    timer++;
                }
                if(timer == 10){
                    break;
                }
                if(q[l].p[i].burst_time==0){
                        continue;
                }

            }

        }
        System.out.println("Broke from queue "+(l+1));
    }

    for(int i=0;i<3;i++){
            System.out.println("\nTime taken for queue "+(i+1)+" to execute: "+q[i].total_time);
        for(int j=0;j<q[i].length;j++){
            System.out.println("Process "+(j+1)+" of queue "+(i+1)+" took "+(q[i].p[j].total_time));
        }
    }

    int sum_tt=0;
    int sum_wt=0;

    System.out.println("\n\nProcess     | Turn Around Time | Waiting Time\n");
    for(int i=0;i<3;i++){
            System.out.println("Queue "+(i+1));
        for(int j=0;j<q[i].length;j++){
            System.out.println("Process P"+(j+1)+"\t"+(q[i].p[j].total_time)+"\t\t    "+(q[i].p[j].total_time-q[i].p[j].tt_time));
            sum_tt+=q[i].p[j].total_time;
            sum_wt+=q[i].p[j].total_time-q[i].p[j].tt_time;
        }
    }

    System.out.println("\n The average turnaround time is : "+sum_tt/no_of_processes);
    System.out.println("\n The average waiting time is : "+sum_wt/no_of_processes);

}
}