#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>

#define BUF_SIZE 30
void ErrorHandling(char *message);

int main(int argc, char *argv[])
{
	WSADATA wsaData;
	SOCKET sock;
	char message[BUF_SIZE];
	int strLen;
	
	SOCKADDR_IN servAdr,your_addr
	socklen_t adr_sz;;
	if(argc!=3) {
		printf("Usage : %s <IP> <port>\n", argv[0]);
		exit(1);
	}
	if(WSAStartup(MAKEWORD(2, 2), &wsaData)!=0)
		ErrorHandling("WSAStartup() error!"); 

	sock=socket(PF_INET, SOCK_DGRAM, 0);   
	if(sock==INVALID_SOCKET)
		ErrorHandling("socket() error");
	
	memset(&servAdr, 0, sizeof(servAdr));
	servAdr.sin_family=AF_INET;
	servAdr.sin_addr.s_addr=inet_addr(argv[1]);
	servAdr.sin_port=htons(atoi(argv[2]));
	
	connect(sock, (SOCKADDR*)&servAdr, sizeof(servAdr));
	
	int i;
	
	for(i = 0 ; i < 3 ; ++i)
	{
		sleep(5);
		adr_sz=sizeof(your_adr);
		str_len=recvfrom(sock, message, BUF_SIZE, 0, 
								(struct sockaddr*)&your_adr, &adr_sz);     
	
		printf("Message %d: %s\n", i+1, message);
	}
	
	closesocket(sock);
	WSACleanup();
	return 0;
}

void ErrorHandling(char *message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}
