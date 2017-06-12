#include <stdio.h>
#include <stdlib.h>
#include <winsock2.h>
void ErrorHandling(char* message);

int main(int argc, char* argv[])
{
	WSADATA	wsaData;
	SOCKET hServSock, hClntSock;		

	SOCKADDR_IN servAddr, clntAddr;		


	int szClntAddr;
	char message[30];

	if(argc!=2) 
	{
		printf("Usage : %s <port>\n", argv[0]);
		exit(1);
	}
  
	if(WSAStartup(MAKEWORD(2, 2), &wsaData)!=0)
		ErrorHandling("WSAStartup() error!"); 
	
	hServSock=socket(PF_INET, SOCK_STREAM, 0);
	if(hServSock==INVALID_SOCKET)
		ErrorHandling("socket() error");
  

	memset(&servAddr, 0, sizeof(servAddr));

	servAddr.sin_family=AF_INET;
	servAddr.sin_addr.s_addr=htonl(INADDR_ANY);
	servAddr.sin_port=htons(atoi(argv[1]));
	
	if(bind(hServSock, (SOCKADDR*) &servAddr, sizeof(servAddr))==SOCKET_ERROR)
		ErrorHandling("bind() error");  
	
	if(listen(hServSock, 5)==SOCKET_ERROR)
		ErrorHandling("listen() error");

	szClntAddr=sizeof(clntAddr);
	
	int i = 0;

	for(i = 0 ; i < 5 ; ++i)
	{
		int str_len = 0;
	
		hClntSock=accept(hServSock, (SOCKADDR*)&clntAddr,&szClntAddr);
		if(hClntSock==INVALID_SOCKET)
			ErrorHandling("accept() error");  
		else
			printf("Connected client %d \n", i+1);

		while((str_len = recv(hClntSock, message, sizeof(message)-1, 0)) != 0)
			send(hClntSock, message, str_len, 0);

		closesocket(hClntSock);
	}
	closesocket(hServSock);

	WSACleanup();
	return 0;
}

void ErrorHandling(char* message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}
