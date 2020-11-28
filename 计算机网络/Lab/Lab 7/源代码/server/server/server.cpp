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
#include <vector>
#include <unistd.h>
#include <pthread.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <errno.h>          // added for output Error info

enum{
    TIME,
    NAME,
    LIST,
    SEND
};



using namespace std;

#define MAX_CONN_LIMIT 512  // max connection limit
#define SOCK_PORT 2492      // port number
#define BUFFER_LENGTH 1024  // buffer length
#define ADDR_LENGTH 40      // address length

pthread_t thread_id[MAX_CONN_LIMIT];    // array of pthread
int allfd[MAX_CONN_LIMIT];              // array of fd

struct timeval tv; 
char* alladdr[MAX_CONN_LIMIT];          // array of address
int allport[MAX_CONN_LIMIT];            // array of port
int isConnected[MAX_CONN_LIMIT];          // array of flag 
int id = 0;                             // id of pthread



void *communacation(void *arg){
    int newfd = *((int*)arg);
    int maxfd;
    struct timeval tv;
    fd_set rfds;
    int retval;
    char data_send[BUFFER_LENGTH];  // data send
    char data_recv[BUFFER_LENGTH];  // data receive
    char data_temp[BUFFER_LENGTH];  // data temp
    int sendlen, recvlen;           // length of send and recieve 
    const char* hello = "hello from server!\n";

    int totalsec = 0;
    int hour = 0, min = 0, sec = 0;
    char localtime[BUFFER_LENGTH];
    char hostname[BUFFER_LENGTH];

    int i;
    int j;
    int curID;
    int toID;
    char toMessage[BUFFER_LENGTH];

    // set current ID
    curID = id - 1;
    allfd[id] = newfd;

    // say hello to client
    if( send(newfd, hello, strlen(hello), 0) > 0){
        printf("Log: data send success!\n");
    }
    else{
        perror("Error: server has NOT sent your message!\n");
        exit(errno);
    }

    // operations
    while(1){
        // set rfds
        FD_ZERO(&rfds);         // initialize rfds
        FD_SET(0, &rfds);       // add 0 to set
        maxfd = 0;              // set 0 as maxfd
        FD_SET(newfd, &rfds);   // add sockfd to set
        if(maxfd < newfd){      // set maxfd
            maxfd = newfd;
        }

        // timeout
        tv.tv_sec = 6;  // second
        tv.tv_usec = 0;

        // wait
        retval = select(maxfd+1, &rfds, NULL, NULL, &tv);
        if(retval == -1){
            perror("select error and client quit!\n");
            break;
        }
        else if(retval == 0){
            continue;
        }
        else{
            // server send message
            if(FD_ISSET(0, &rfds)){
                bzero(data_send, BUFFER_LENGTH);        // initialize
                fgets(data_send, BUFFER_LENGTH, stdin); // input
            // solve the exit signal 
                if(!strncasecmp(data_send, "quit", 4) || !strncasecmp(data_send, "exit", 4)){
                    printf("Log: server require to quit!\n");
                    break;
                }
                sendlen = send(newfd, data_send, strlen(data_send), 0);
                if( sendlen > 0){
                    printf("Log: data send success!\n");
                }
                else{
                    perror("Log: server has NOT sent your message!\n");
                    break;
                }
            }

            // receive message from client
            if(FD_ISSET(newfd, &rfds)){
                bzero(data_recv, BUFFER_LENGTH);
                recvlen = recv(newfd, data_recv, BUFFER_LENGTH, 0);
                if( recvlen > 0){
                    printf("data receive: %s", data_recv);

                    // time()
                    if(!strncmp(data_recv, "time", 4)){
                        totalsec = (int)time(0);
                        sec = totalsec % 60;
                        min = totalsec % 3600 / 60;
                        hour = (totalsec % (24 * 60) / 3600 + 8) % 24;
                        sprintf(localtime, "TIME%02d:%02d:%02d\n", hour, min, sec);
                        // send time to client
		
			 if( send(newfd, localtime, strlen(localtime), 0) > 0){
                            printf("Time data send to %d success!\n", newfd);
                        }
                        else{
                            perror("data send: Server has NOT sent your message!\n");
                            exit(errno);
                        }
                    }

                    // name()
                    else if(!strncmp(data_recv, "name", 4)){
                        // get host name
                        char tempName[32];
                        gethostname(tempName, 32);
                        sprintf(hostname, "NAME%s\n", tempName);
                        // send time to client
                        if( send(newfd, hostname, strlen(hostname), 0) > 0){
                            printf("\ndata send success!\n");
                        }
                        else{
                            perror("data send: Server has NOT sent your message!\n");
                            exit(errno);
                        }
                    }

                    // list()
                    if(!strncmp(data_recv, "list", 4)){
                        // traverse the client list
                        for(i = 0; i < id; i++){
                            bzero(data_send, BUFFER_LENGTH);        // initialize
                            // test if is still connected
                            if(isConnected[i] == 1){
                                sprintf(data_send, "LISTid: %d, addr: %s, port: %d\n", i+1, alladdr[i], allport[i]);
                                if( send(newfd, data_send, strlen(data_send), 0) > 0){
                                    printf("\ndata send success!\n");
                                }
                                else{
                                    perror("data send: Server has NOT sent your message!\n");
                                    exit(errno);
                                }
                            }
                        }
                    }

                    
                    if(!strncmp(data_recv, "send", 4)){
                        // get id of the receiver client
                        j = 0;
                        bzero(data_temp, BUFFER_LENGTH);        
                        for(i = 5; i < BUFFER_LENGTH; i++){
                            if(data_recv[i] != ':'){
                                data_temp[j] = data_recv[i];
                                j++;
                            }
                            else{
                                break;
                            }
                        }
                        toID = atoi(data_temp);
                        // get message client want to send
                        j = 0;
                        bzero(toMessage, BUFFER_LENGTH);
                        for(i++; i < BUFFER_LENGTH; i++){
                            if(data_recv[i] != '\n'){
                                toMessage[j] = data_recv[i];
                                j++;
                            }
                            else{
                                break;
                            }
                        }
                        // information to send: client's information + message 
                        bzero(data_send, BUFFER_LENGTH);        // initialize
                        sprintf(data_send, "SENDid: %d, addr: %s, port: %d send you a message:\n%s\n", curID+1, alladdr[curID], allport[curID], toMessage);
                        if( send(allfd[toID], data_send, strlen(data_send), 0) > 0){
                            printf("data send success!\n");
                        }
                        else{
                            perror("data send: Server has NOT sent your message!\n");
                            exit(errno);
                        }
                    }
                }
            }

        }
    }

    // clear current pthread
    printf("terminating current client connection...\n");
    isConnected[id] = 0;  // reset client's status as unconnected
    close(newfd);       // clost fd
    pthread_exit(NULL); // exit current pthread

}


class server{
    public:
        server();
        ~server();

        int init(pthread_t tid);
        int servBind();
        int servListen();
        int servCommunacation();
    private:
        int serv_sock = -1;
        int clnt_sock = -1;
        pthread_t tidp;
        struct sockaddr_in serv_addr;   // lock server
        struct sockaddr_in clnt_addr;   // client addr
        socklen_t clnt_addr_size = sizeof(clnt_addr);

};

server::server(){

}

server::~server(){

}

int server::init(pthread_t tid){
    tidp = tid;
    // if current client is still connect, we need to clean the info
    bzero(isConnected, MAX_CONN_LIMIT);
    // create socket
    serv_sock = socket(AF_INET, SOCK_STREAM, 0);    // ipv4
    if (serv_sock!=-1) cout<<"socket create success!"<<endl;
    else {
        perror("Error: bind failed!");
        exit(errno);
    }
    

}

int server::servBind(){
    // set the attribution of serv_addr
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(SOCK_PORT);
    if(bind(serv_sock, (struct sockaddr*)&serv_addr, sizeof(struct sockaddr)) != -1)
    {
        cout<<"bind success!"<<endl;
    }
    else 
    {
        perror("Error: bind failed!");
        exit(errno);
    }
}

int server::servListen(){
    if (listen(serv_sock,MAX_CONN_LIMIT)!=-1)
        cout<<"listen success!"<<endl;
    else 
    {
        perror("Error: listen failed!");
        exit(errno);
    }
}

int server::servCommunacation(){
    while(true){
        // accept
        
        clnt_sock = accept(serv_sock, (struct sockaddr *)(&clnt_addr),&clnt_addr_size);
        if (clnt_sock == -1){
            perror("Error: accept failed!\n");
            continue;
        }
        else{
            // connect
            cout<<"a new connection request!"<<endl;
            cout<<"the client is :"
            <<inet_ntoa(clnt_addr.sin_addr)
            <<":"
            <<ntohs(clnt_addr.sin_port)
            <<endl;

            // update the list
            // store address and port
            alladdr[id] = (char*)malloc(sizeof(char) * ADDR_LENGTH);    
            strcpy(alladdr[id], inet_ntoa(clnt_addr.sin_addr));
            allport[id] = ntohs(clnt_addr.sin_port);

            isConnected[id] =1;

             // create new thread
             if (pthread_create(&thread_id[id], NULL, &communacation, (void *)(&clnt_sock)) == -1){
                 id --;
                 perror("Error: create thread failed!\n");
                 break;
             }
             id ++;
        }

    }

    // shutdown 
    if(shutdown(serv_sock, SHUT_WR) == -1){
        perror("shut down error!\n");
    }
    else{
        printf("server shuts down!\n");
    }
    return 0;
}



int main(){
    pthread_t tid;
    server *myServer = new server();
    myServer->init(tid);
    myServer->servBind();
    myServer->servListen();
    myServer->servCommunacation();
    return 0;
}
