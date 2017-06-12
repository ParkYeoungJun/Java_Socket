#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>

#define BUF_SIZE 30
void ErrorHandling(char *message);

int main(int argc, char *argv[])
{
	WSADATA wsaData;
	SOCKET servSock;
	char message[BUF_SIZE];
	int strLen;
	int clntAdrSz;
	
	char msg1[]="Hi!";
	char msg2[]="I'm another UDP host!";
	char msg3[]="Nice to meet you";
	
	SOCKADDR_IN servAdr, clntAdr;
	if(argc!=2) {
		printf("Usage : %s <port>\n", argv[0]);
		exit(1);
	}
	if(WSAStartup(MAKEWORD(2, 2), &wsaData)!=0)
		ErrorHandling("WSAStartup() error!"); 
	
	servSock=socket(PF_INET, SOCK_DGRAM, 0);
	if(servSock==INVALID_SOCKET)
		ErrorHandling("UDP socket creation error");
	
	memset(&servAdr, 0, sizeof(servAdr));
	servAdr.sin_family=AF_INET;
	servAdr.sin_addr.s_addr=htonl(INADDR_ANY);
	servAdr.sin_port=htons(atoi(argv[1]));
	
	if(bind(servSock, (SOCKADDR*)&servAdr, sizeof(servAdr))==SOCKET_ERROR)
		ErrorHandling("bind() error");
	
	clntAdrSz=sizeof(clntAdr);
	
	sendto(servSock, msg1, sizeof(msg1), 0, 
							(SOCKADDR*)&clntAdr, sizeof(clntAdr));
	sendto(servSock, msg2, sizeof(msg1), 0, 
							(SOCKADDR*)&clntAdr, sizeof(clntAdr));
	sendto(servSock, msg3, sizeof(msg1), 0, 
							(SOCKADDR*)&clntAdr, sizeof(clntAdr));
		
	closesocket(servSock);
	WSACleanup();
	return 0;
}

void ErrorHandling(char *message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}
