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

	char message[30];
	int strLen;

	// 3���� �׸� �����̸�, ip, port 3������ ������ ������ ����ó��
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
	
	/*
	 * int connect(int sockfd, const struct sockaddr * serv_addr,
	 *  socklen_t addrlen)
	 *
	 *  sockfd = ������ socket ������ȣ
	 *  serv_addr = ������ ������ ip�ּҿ� port ���� ������ �˷��ִ� ����ü
	 *  addrlen = serv_addr�� ũ��
	 *
	 *  ������ ���� ȣ��Ʈ�� ����
	 *
	 *  ��ȯ = ���� 0, ����  -1
	 */
	if(connect(hSocket, (SOCKADDR*)&servAddr, sizeof(servAddr))==SOCKET_ERROR)
		ErrorHandling("connect() error!");
 
	/*
	 * int recv(int fd, void *buffer, size_t n, int flags)
	 *
	 * fd = ������ ����
	 * *buffer = ������ ���� �޴� �޼���
	 * n = buffer�� ũ��
	 * flags = �÷��� �ɼ�
	 *
	 * ���� fd���� n����Ʈ�� �о� buffer�� �ִ´�
	 */
	strLen=recv(hSocket, message, sizeof(message)-1, 0);
	if(strLen==-1)
		ErrorHandling("read() error!");
	printf("Message from server: %s \n", message);  

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
