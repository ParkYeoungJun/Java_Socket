#include <stdio.h>
#include <stdlib.h>
#include <winsock2.h>
void ErrorHandling(char* message);

int main(int argc, char* argv[])
{
	WSADATA	wsaData;
	SOCKET hServSock, hClntSock;		

	/*
	 * SOCKADDR_IN =  IPv4 주소체계에서 사용하는 구조체, 서로 다른 시스템에 있는 프로세스 간의 통신
	 * struct sockaddr_in{
	 * 	sin_family_t sin_family -> 주소체계, ipv4 이므로 AF_INET으로 설정,
	 * 						AF_INET6/AF_LOCAL 3가지
	 * 	unsigned short int sin_port	 -> 16비트 포트번호
	 * 	struct sin_addr -> 32비트 ip주소
	 * 	char sin_zero[8] -> sockaddr과 같은 크기를 유지하기 위해 필요한 패딩공간. 항상 0
	 * 						sockaddr = 소켓 주소를 표현하는 구조체
	 * }
	 *
	 * SOCKADDR = 동일한 시스템에 있는 프로세스 간의 통신
	 * struct sockaddr
	 * {
	 * 	sa_family_t sa_family = 주소
	 * 	char sa_data[14] = protocol 주소
	 * }
	 */
	SOCKADDR_IN servAddr, clntAddr;		


	int szClntAddr;
	char message[]="Hello World!";

	// 파일이름, port number 두가지가 들어와야함. 예외처리, 입력 받음
	if(argc!=2) 
	{
		printf("Usage : %s <port>\n", argv[0]);
		exit(1);
	}
  
	/* window에서 제공되는 윈도우 소켓 라이브러리 ws2_32.lib을 사용 가능하게 초기화, 버전체크 등 사용
	 * int WSAStartup(WORD wVersionRewuested, LPWSADATA IpWSAData)
	 * wVersionRewuested = 버전
	 * WSAData에 대한 구조체 포인터
	 */
	if(WSAStartup(MAKEWORD(2, 2), &wsaData)!=0)
		ErrorHandling("WSAStartup() error!"); 
	
	/* SOCKET socket(int domain, int type, int protocol)
	 *  = socket 생성
	 * domain = 프로토콜 체계 , in socket.h
	 *  PF_INET = IPv4
	 *  PF_INET6 = IPv6
	 *  PF_UNIX = 유닉스 방식
	 *  PF_NS = XEROX network
	 *  PF_PACKET = Linux에서 패킷 캡쳐를 위해 사용
	 * type = 서비스 타입
	 *  SOCK_STREAM = TCP 소켓 생성 -> 연결지향형(전송 중 데이터 소멸 X,
	 *  전송 순서대로 데이터 수신, 데이터 경계가 존재 X, 소켓 대 소켓의 연결이 1:1 구조)
	 *  SOCK_DGRAM = UDP 소켓 생성 -> 비 연결지향형(전송 순서와 상관없이 빠른 속도,
	 *  데이터의 손실 및 파손 가능성, 데이터 경계가 존재, 한번에 전송 할 수 있는 크기가 제한됨)
	 *  SOCK_RAW = Raw 소켓 생성
	 * protocol = 소켓에 사용할 프로토콜
	 *  Socket에서 사용할 프로토콜 지정
	 *  SOCK_STREAM or SOCK_DGRAM을 정한 경우, protocol = 0
	 *  SOCK_RAW 인 경우 구체적으로 상위 프로토콜 지정하는데 사용.
	 *
	 *  socket()함수가 성공적으로 생성 -> 새로 만들어진 socket번호를 return
	 *  잘못 생성되었다면 소켓 변수에 invalid_socket,
	 *   보통 윈속을 초기화 해주지 않았거나 인수 값이 잘못되었을 때 발생
	 */
	hServSock=socket(PF_INET, SOCK_STREAM, 0);
	if(hServSock==INVALID_SOCKET)
		ErrorHandling("socket() error");
  

	// memset으로 servAddr을 0으로 초기화
	memset(&servAddr, 0, sizeof(servAddr));

	/*
	 * IPv4 이므로 AF_INET
	 * IPv6 = AF_INET6
	 * htons, htonl
	 * htons = 리틀 엔디안을 빅엔디안으로 short 만큼 (port 크기 2byte 만큼)
	 * htonl = 리틀 엔디안을 빅엔디안으로 long 만큼 (ip 크기 4byte 만큼)
	 * int 형은 컴퓨터의 bit체계마다 크기가 다르므로 쓰지않음
	 *
	 * h = 리틀엔디안
	 * to = ~에서 ~로
	 * n = network 빅엔디안
	 * s = short
	 * l = long
	 *
	 * 반대함수
	 * {
	 * ntohs
	 * ntohl
	 * }
	 */
	servAddr.sin_family=AF_INET;
	servAddr.sin_addr.s_addr=htonl(INADDR_ANY);
	servAddr.sin_port=htons(atoi(argv[1]));
	
	/*
	 * int bind(int sockfd, struct sockaddr *myaddr,
	 * 		socklen_t addrlen);
	 *
	 * socket에 주소를 할당해주는 함수
	 * sockfd = 소켓 식별자
	 * *myaddr = 주소 정보 할당
	 * addrlen = myaddr 구조체의 크기
	 * 반환 = 0(성공) or -1(실패)
	 */
	if(bind(hServSock, (SOCKADDR*) &servAddr, sizeof(servAddr))==SOCKET_ERROR)
		ErrorHandling("bind() error");  
	
	/*
	 * int listen(int sockfd, int backlog)
	 * 	sockfd = socket함수를 실행해 얻은 소켓의 지정번호
	 * 	backlog = 수신 대기열의 크기, 정해져 있지 않음
	 * 소켓을 통해 들어오는 연결 요청을 기다린다
	 * 반환 = 0(성공) or -1(실패)
	 */
	if(listen(hServSock, 5)==SOCKET_ERROR)
		ErrorHandling("listen() error");

	szClntAddr=sizeof(clntAddr);

	/*
	 * int accept(int sockfd, struct sockaddr *addr
	 * 	, socklen_t *addrlen)
	 *
	 * 	sockfd = 서버의 소켓 지정번호
	 * 	addr = client의 주소 정보를 담은 구조체
	 * 	addrlen = client 주소 정보를 담은 구조체의 크기
	 *
	 * 연결형 소켓으로 통신 할 때 상대의 연결 요청을 수락
	 *
	 * 반환 = 0 or -1
	 */
	hClntSock=accept(hServSock, (SOCKADDR*)&clntAddr,&szClntAddr);
	if(hClntSock==INVALID_SOCKET)
		ErrorHandling("accept() error");  
	
	/*
	 * int send(int fd, void *bufer, size_t n, int flags)
	 *
	 * 	fd = socket의 handle
	 * 	*bufer = 보낼 버퍼 메세지
	 * 	n = 버퍼의 크기, 전송할 바이트 수
	 * 	flags = 플래그 옵션
	 */
	send(hClntSock, message, sizeof(message), 0);

	// closesocket 윈도우 환경에서 소켓 디스크립터를 닫음
	closesocket(hClntSock);
	closesocket(hServSock);

	// 윈속 라이브러리 인스턴스를 종료, 소켓 통신 종료
	// 모든 작업이 끝났을 때 반드시 호출해야 함
	WSACleanup();
	return 0;
}

// 예외처리 print 함수
void ErrorHandling(char* message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}

