/*
    Author: haochengxia@github
    Date: 17/12/2019
    Contact me: hootch@126.com
*/
#include <iostream>
#include <string>


#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>

using namespace std;

#define BUFFER_LENGTH 1024  // buffer length
char request_buff[BUFFER_LENGTH];

enum{CONNECTED, UNCONNECTED, CONNECTFAIL};
void *listenResponse(void *arg);
class client{
    public:
        // ctor
        client();
        //
        ~client();
        void showFuncMenu();
        
        string getAddr();
        int getPort();
        void setAddr(string addr);
        void setPort(string port);
        int init(pthread_t tid);


        int cliConnect(); 
        int cliClose();
        int cliSend();

        int getServerTime();
        int getServerName();
        int activeList();

        void solveCommand(int command);

    private:
        string ip_addr;
        int ip_port;
        int sock = -1;
        int status;
        struct sockaddr_in serv_addr;
        int rc_tid;
        pthread_t tidp;
};


client::client(void){

}

client::~client(void){

}

void client::showFuncMenu(){
    cout<<"  +-------------------------------------+\n"
	<<"  |      Welcome! client func menu.     |\n"
        <<"  +-------------------------------------+\n"
        <<"  | 1. connect                          |\n"
        <<"  | 2. close                            |\n"
        <<"  | 3. getServerTime                    |\n"
        <<"  | 4. getServerName                    |\n"
        <<"  | 5. activeList                       |\n"
        <<"  | 6. send                             |\n"
        <<"  | 7. exit                             |\n"
        <<"  +-------------------------------------+"<<endl;

    cout<<"Tips: enter a number(from 1 to 7) to choose the function"<<endl; 
}

string client::getAddr(){
    return ip_addr;
}

int client::getPort(){
    return ip_port;
}

void client::setAddr(string addr){
    ip_addr = addr;

    
}

void client::setPort(string port){
    ip_port = stoi(port);
}


int client::init(pthread_t tid){
    /* int serv_sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP); */
    // use socket() to create socket
    status = UNCONNECTED;
    tidp = tid;
    sock = socket(AF_INET, SOCK_STREAM, 0);
    return 0; 
}

int client::cliConnect(){
    // in the connect user enter specific ip_addr & ip_port
    cout<<"please enter ip:"<<endl;
    cin>>ip_addr;
    cout<<"please enter port:"<<endl;
    cin>>ip_port;

    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = inet_addr(ip_addr.c_str());
    serv_addr.sin_port = htons(ip_port);

    /* int connect(int sock, struct sockaddr *serv_addr, socklen_t addrlen);  */
    // do connect
    int res = connect(sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr));
    if (res == 0) status = CONNECTED;


    // create new thread to listen
    rc_tid = pthread_create(&tidp, NULL,&listenResponse,&sock);
    return res;
}

int client::cliClose(){
    int res = close(sock);
    return res;
}

int client::cliSend(){
    char msg[1014];
	char msgno[1024];
	int rv, no;
	printf("Please input the number of the client:\n");
	scanf("%d", &no);
	printf("Please input the message you want to send:\n");
    while(1){
	scanf("%s", msg);	
	    //itoa(no, msgno, 10);
	    sprintf(msgno, "send:%d:", no);
	    strcat(msgno, msg); 
	    rv = send(sock ,msgno, sizeof(msgno), 0); 
	    if (rv == -1)
		    return 1;  // no connnect
	    else
            //printf("Client has not send your message!\n");
		return 0;
    }
}

void *listenResponse(void *arg){
    int sock;
    char server_response[256];
    memset(server_response, 0, sizeof(server_response));
    sock = (int)(*(int*)arg);
    int type = -1;
  int timePackageNum =0;
    // block
    while(true){
        int ret = recv(sock, &server_response, sizeof(server_response),0);
        if (ret > 0){

		// judge the response type
		type = -1;
        string mark="";
		if (!strncmp(server_response, "TIME", 4)){
            // TIME type
            type = 0;
            mark = "Time info\n"; timePackageNum++;
		}else if(!strncmp(server_response, "NAME", 4)){
            type = 1;
            mark = "Name info\n";
        }
        else if(!strncmp(server_response, "LIST", 4)){
            type = 2;
            mark = "List info\n";
        }
        else if (!strncmp(server_response, "SEND", 4)){
            type = 3;
            mark = "Send info\n";
        }
        
            cout<<"count = "<<timePackageNum<<"\n"
	    	<<"[Data received] \n"
            	<<"The data is\n" << mark <<": \n"
		        <<(type==-1?server_response:(server_response+4))
            	<<endl;
            	memset(server_response, 0, sizeof(server_response));
        }
        else if (ret < 0){
            // exception
            pthread_exit(NULL);
        }
        else if (ret == 0){
            cout<<"Error: Socket closed, exiting current receive thread"<<endl;
            pthread_exit(NULL);
        }
    }
    pthread_exit(NULL);
}

int client::getServerName(){
    int recv;
    sprintf(request_buff,"name");
    recv = send(sock,request_buff,BUFFER_LENGTH,0);
    if (recv == -1)
        return 1;
    else
        return 0;
}

int client::getServerTime(){
    int recv;
    sprintf(request_buff,"time");
    for (int i = 0;i<100;i++){
    recv = send(sock,request_buff,BUFFER_LENGTH,0);
    sleep(1);
    }
    if (recv == -1)
        return 1;
    else
        return 0;
}

int client::activeList(){
    int recv;
    sprintf(request_buff,"list");
    recv = send(sock,request_buff,BUFFER_LENGTH,0);
    if (recv == -1)
        return 1;
    else
        return 0;
}


void client::solveCommand(int command){
    int recv;
    switch(command){
        case 1: cliConnect(); break;
        case 2: cliClose();
                cout<<"Connection has been closed"<<endl;
                break;
        case 3: recv = getServerTime();
                break;
        case 4: recv = getServerName();
                break;
        case 5: recv = activeList();
                break;
        case 6: recv = cliSend();
                break;
        case 7: if (sock == -1) exit(0);
                else
                {
                    cliClose();
                    sock = -1;
                    exit(0);
                }
                break;
        default: break;
                
    }
}

int main(int argc, char **argv){
    bool first = true;
    int command;
    pthread_t tidp;
    client *myClient = new client();
    myClient->init(tidp);


    while (true){
        if (first) myClient->showFuncMenu(); // show the func menu when client has been initialized
        first = false;			     // make first false
        cin >> command;			     // wait for user's choice
        myClient->solveCommand(command);     // loop 
    }

    return 0;
}
