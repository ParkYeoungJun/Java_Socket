#include <stdio.h>
#include <stdlib.h>
#include <winsock2.h>
void ErrorHandling(char* message);

int main(int argc, char* argv[])
{
	WSADATA	wsaData;
	SOCKET hServSock, hClntSock;		

	/*
	 * SOCKADDR_IN =  IPv4 �ּ�ü�迡�� ����ϴ� ����ü, ���� �ٸ� �ý��ۿ� �ִ� ���μ��� ���� ���
	 * struct sockaddr_in{
	 * 	sin_family_t sin_family -> �ּ�ü��, ipv4 �̹Ƿ� AF_INET���� ����,
	 * 						AF_INET6/AF_LOCAL 3����
	 * 	unsigned short int sin_port	 -> 16��Ʈ ��Ʈ��ȣ
	 * 	struct sin_addr -> 32��Ʈ ip�ּ�
	 * 	char sin_zero[8] -> sockaddr�� ���� ũ�⸦ �����ϱ� ���� �ʿ��� �е�����. �׻� 0
	 * 						sockaddr = ���� �ּҸ� ǥ���ϴ� ����ü
	 * }
	 *
	 * SOCKADDR = ������ �ý��ۿ� �ִ� ���μ��� ���� ���
	 * struct sockaddr
	 * {
	 * 	sa_family_t sa_family = �ּ�
	 * 	char sa_data[14] = protocol �ּ�
	 * }
	 */
	SOCKADDR_IN servAddr, clntAddr;		


	int szClntAddr;
	char message[]="Hello World!";

	// �����̸�, port number �ΰ����� ���;���. ����ó��, �Է� ����
	if(argc!=2) 
	{
		printf("Usage : %s <port>\n", argv[0]);
		exit(1);
	}
  
	/* window���� �����Ǵ� ������ ���� ���̺귯�� ws2_32.lib�� ��� �����ϰ� �ʱ�ȭ, ����üũ �� ���
	 * int WSAStartup(WORD wVersionRewuested, LPWSADATA IpWSAData)
	 * wVersionRewuested = ����
	 * WSAData�� ���� ����ü ������
	 */
	if(WSAStartup(MAKEWORD(2, 2), &wsaData)!=0)
		ErrorHandling("WSAStartup() error!"); 
	
	/* SOCKET socket(int domain, int type, int protocol)
	 *  = socket ����
	 * domain = �������� ü�� , in socket.h
	 *  PF_INET = IPv4
	 *  PF_INET6 = IPv6
	 *  PF_UNIX = ���н� ���
	 *  PF_NS = XEROX network
	 *  PF_PACKET = Linux���� ��Ŷ ĸ�ĸ� ���� ���
	 * type = ���� Ÿ��
	 *  SOCK_STREAM = TCP ���� ���� -> ����������(���� �� ������ �Ҹ� X,
	 *  ���� ������� ������ ����, ������ ��谡 ���� X, ���� �� ������ ������ 1:1 ����)
	 *  SOCK_DGRAM = UDP ���� ���� -> �� ����������(���� ������ ������� ���� �ӵ�,
	 *  �������� �ս� �� �ļ� ���ɼ�, ������ ��谡 ����, �ѹ��� ���� �� �� �ִ� ũ�Ⱑ ���ѵ�)
	 *  SOCK_RAW = Raw ���� ����
	 * protocol = ���Ͽ� ����� ��������
	 *  Socket���� ����� �������� ����
	 *  SOCK_STREAM or SOCK_DGRAM�� ���� ���, protocol = 0
	 *  SOCK_RAW �� ��� ��ü������ ���� �������� �����ϴµ� ���.
	 *
	 *  socket()�Լ��� ���������� ���� -> ���� ������� socket��ȣ�� return
	 *  �߸� �����Ǿ��ٸ� ���� ������ invalid_socket,
	 *   ���� ������ �ʱ�ȭ ������ �ʾҰų� �μ� ���� �߸��Ǿ��� �� �߻�
	 */
	hServSock=socket(PF_INET, SOCK_STREAM, 0);
	if(hServSock==INVALID_SOCKET)
		ErrorHandling("socket() error");
  

	// memset���� servAddr�� 0���� �ʱ�ȭ
	memset(&servAddr, 0, sizeof(servAddr));

	/*
	 * IPv4 �̹Ƿ� AF_INET
	 * IPv6 = AF_INET6
	 * htons, htonl
	 * htons = ��Ʋ ������� �򿣵������ short ��ŭ (port ũ�� 2byte ��ŭ)
	 * htonl = ��Ʋ ������� �򿣵������ long ��ŭ (ip ũ�� 4byte ��ŭ)
	 * int ���� ��ǻ���� bitü�踶�� ũ�Ⱑ �ٸ��Ƿ� ��������
	 *
	 * h = ��Ʋ�����
	 * to = ~���� ~��
	 * n = network �򿣵��
	 * s = short
	 * l = long
	 *
	 * �ݴ��Լ�
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
	 * socket�� �ּҸ� �Ҵ����ִ� �Լ�
	 * sockfd = ���� �ĺ���
	 * *myaddr = �ּ� ���� �Ҵ�
	 * addrlen = myaddr ����ü�� ũ��
	 * ��ȯ = 0(����) or -1(����)
	 */
	if(bind(hServSock, (SOCKADDR*) &servAddr, sizeof(servAddr))==SOCKET_ERROR)
		ErrorHandling("bind() error");  
	
	/*
	 * int listen(int sockfd, int backlog)
	 * 	sockfd = socket�Լ��� ������ ���� ������ ������ȣ
	 * 	backlog = ���� ��⿭�� ũ��, ������ ���� ����
	 * ������ ���� ������ ���� ��û�� ��ٸ���
	 * ��ȯ = 0(����) or -1(����)
	 */
	if(listen(hServSock, 5)==SOCKET_ERROR)
		ErrorHandling("listen() error");

	szClntAddr=sizeof(clntAddr);

	/*
	 * int accept(int sockfd, struct sockaddr *addr
	 * 	, socklen_t *addrlen)
	 *
	 * 	sockfd = ������ ���� ������ȣ
	 * 	addr = client�� �ּ� ������ ���� ����ü
	 * 	addrlen = client �ּ� ������ ���� ����ü�� ũ��
	 *
	 * ������ �������� ��� �� �� ����� ���� ��û�� ����
	 *
	 * ��ȯ = 0 or -1
	 */
	hClntSock=accept(hServSock, (SOCKADDR*)&clntAddr,&szClntAddr);
	if(hClntSock==INVALID_SOCKET)
		ErrorHandling("accept() error");  
	
	/*
	 * int send(int fd, void *bufer, size_t n, int flags)
	 *
	 * 	fd = socket�� handle
	 * 	*bufer = ���� ���� �޼���
	 * 	n = ������ ũ��, ������ ����Ʈ ��
	 * 	flags = �÷��� �ɼ�
	 */
	send(hClntSock, message, sizeof(message), 0);

	// closesocket ������ ȯ�濡�� ���� ��ũ���͸� ����
	closesocket(hClntSock);
	closesocket(hServSock);

	// ���� ���̺귯�� �ν��Ͻ��� ����, ���� ��� ����
	// ��� �۾��� ������ �� �ݵ�� ȣ���ؾ� ��
	WSACleanup();
	return 0;
}

// ����ó�� print �Լ�
void ErrorHandling(char* message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}

