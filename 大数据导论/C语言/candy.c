#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#define LEN sizeof(struct child)
struct child{
	int candy_num;
	int share_num;
	struct child *next;
};
struct child* createList(){
	struct child *head=NULL,*p1=NULL,*p2=NULL;
	int i=0;
	/**建立空的头节点*/
	p1=(struct child*)malloc(LEN);
	p1->candy_num=0;
	p1->share_num=0;
	p1->next=NULL;
	head=p1;
	p2=p1;
	/**初始化
	非头节点的第一节点*/ 
	p1=(struct child*)malloc(LEN);
	p2->next=p1;
	p2=p1;
	head->next=p1;
	p1->share_num=0;
	p1->next=NULL;
	scanf("%d",&p1->candy_num);
 	for(i=1;i<10;i++){
	 	p1=(struct child*)malloc(LEN);
	 	p2->next=p1;
	 	p2=p1;
	 	p1->share_num=0;
	 	scanf("%d",&p1->candy_num);
	 }
	/**建立循环
 	尾节点指向头节点下一个节点*/
	p1->next=head->next;
	return head; 
}
struct	child* share(struct child *head){
	struct child *p=NULL;
	int i=0;
	p=head;
	//开始分享 
	do{
		p=p->next;
		p->share_num=p->candy_num/2;
		p->candy_num=p->candy_num/2;
	}while(p->next!=head->next);
	return head;
}
struct child* catch_candy(struct child *head)
{
	struct child *p=NULL;
	p=head;
	
	do{
		p=p->next;
		p->next->candy_num+=p->share_num;
		if(p->next->candy_num%2!=0){
			p->next->candy_num+=1;
		}
	}while(p->next!=head->next);
	return head;
}
int judge(struct child *head)
{
	struct child *p=NULL;
	int i=0;
	p=head;
	p=p->next;
	do{
		if(p->candy_num==p->next->candy_num){
			i++;
			p=p->next;
		}
		else 
			break;
	}while(p->next!=head->next);
	return i;
}
void print(struct child *head)
{
	struct child *p=NULL;
	p=head;
	do{
		p=p->next;
		printf("%d ",p->candy_num);
	}while(p->next!=head->next);
}
int main(){
	struct child *p=NULL,*q=NULL;
	int i=0;
	p=createList();
	while(judge(p)!=9){
		share(p);
		catch_candy(p);
		i++;
	}
	if(judge(p)==9)
		print(p);
	printf("%d",i);
}



