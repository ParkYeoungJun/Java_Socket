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

	// 3가지 항목 파일이름, ip, port 3가지가 들어오지 않으면 예외처리
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
	 *  sockfd = 생성한 socket 지정번호
	 *  serv_addr = 연결할 서버의 ip주소와 port 등의 정보를 알려주는 구조체
	 *  addrlen = serv_addr의 크기
	 *
	 *  소켓을 원격 호스트와 연결
	 *
	 *  반환 = 성공 0, 실패  -1
	 */
	if(connect(hSocket, (SOCKADDR*)&servAddr, sizeof(servAddr))==SOCKET_ERROR)
		ErrorHandling("connect() error!");
 
	/*
	 * int recv(int fd, void *buffer, size_t n, int flags)
	 *
	 * fd = 연결한 소켓
	 * *buffer = 서버로 부터 받는 메세지
	 * n = buffer의 크기
	 * flags = 플래그 옵션
	 *
	 * 소켓 fd에서 n바이트를 읽어 buffer에 넣는다
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
