#include <stdio.h>
#include <stdlib.h>
#include <winsock2.h>
void ErrorHandling(char* message);

/*
 *
 */

int main(int argc, char* argv[])
{
	WSADATA wsaData;
	SOCKET hSocket;
	SOCKADDR_IN servAddr;

	char message[1024];
	int strLen;

	if(argc!=3)
	{
		printf("Usage : %s <IP> <port>\n", argv[0]);
		exit(1);
	}

	if(WSAStartup(MAKEWORD(2, 2), &wsaData) != 0)
		ErrorHandling("WSAStartup() error!");  
	
	hSocket=socket(PF_INET, SOCK_STREAM, 0);
	if(hSocket==INVALID_SOCKET)
		ErrorHandling("hSocketet() error");
	
	memset(&servAddr, 0, sizeof(servAddr));
	servAddr.sin_family=AF_INET;
	servAddr.sin_addr.s_addr=inet_addr(argv[1]);
	servAddr.sin_port=htons(atoi(argv[2]));
	
	if(connect(hSocket, (SOCKADDR*)&servAddr, sizeof(servAddr))==SOCKET_ERROR)
		ErrorHandling("connect() error!");
	
	
	int count;	
		
	fputs("Connected........ \n", stdout);
	fputs("Operand Count : ", stdout);
	
	scanf("%d", &count);
	
	message[0] = (char)count;
		
	int k;
	int op;
	
	for(k = 0 ; k < count ; ++k)
	{
		printf("Operand %d: ",k+1);
		scanf("%d", (int *)&message[k*4+1]);
	}
	
	fputs("Operator: ",stdout);
	
	fgetc(stdin);
	
	scanf("%c", &message[count*4+1]);
	
	send(hSocket, message, count*4+2, 0);
	
	int result[2];
	
	recv(hSocket, result, sizeof(result), 0);
	
	printf("Operation result : %d", result[0]);

	closesocket(hSocket);
	WSACleanup();
	return 0;
}

void ErrorHandling(char* message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}
