#include "stdio.h"
#include "stdlib.h"
#include "time.h"

//�˺��������������0����,1����,2����,3����
int GetRandomDirection()
{
	int i = rand();
	return i % 4;
}

int main()
{
	char a[10][10];

	int i = 0, j = 0;
	for(i = 0; i < 10;++i) //Ϊ���󸳳�ֵ
	{
		for(j = 0;j<10;++j)
		{
			a[i][j] = '.';
		}
	}
	srand( (unsigned)time( NULL ) ); //��ʼ����������� 
	int iDirection;
	char cCurrentChar = 'A';
	int iCurrentRow = 0, iCurrentColumn;
	iCurrentColumn = rand() % 10;
	a[iCurrentRow][iCurrentColumn] = cCurrentChar;
	int iJamCount = 0; //�����м������򱻶���,���ʱ�ɽ���ֽ�Ϊ�����ƣ�000000001������߱�������00000010�����ϱ߱�������
	                   //00000100�����ұ߱�������00001000�����±߱�������000000011������ߺ��ϱ߶���������
						//00000111���������ߡ��ϱߡ��ұ߶���������00001111��������ĸ����򶼱���������ʱiJamCount�պ�Ϊ15��ѭ����ֹ��
	while(iJamCount != 15)
	{
		iDirection = GetRandomDirection(); //0����,1����,2����,3����
		if(iDirection == 0)
		{
			if(iCurrentColumn - 1 < 0 || a[iCurrentRow][iCurrentColumn - 1] != '.')	//���Խ�磬���Ǹ÷��򱻶�����Ȼ���������������
			{
				iJamCount = iJamCount | 1;
				continue;
			}
			else
			{
				cCurrentChar++;

				if(cCurrentChar > 'Z')
				{
					break;
				}
				else
				{
					iCurrentColumn--;
					a[iCurrentRow][iCurrentColumn] = cCurrentChar;
					iJamCount = 0;
					continue;
				}
			}
		}
		if(iDirection == 1)
		{
			if(iCurrentRow - 1 < 0 || a[iCurrentRow-1][iCurrentColumn] != '.')	
			{
				iJamCount = iJamCount | 2;
				continue;
			}
			else
			{
				cCurrentChar++;
				if(cCurrentChar > 'Z')
				{
					break;
				}
				else
				{
					iCurrentRow--;
					a[iCurrentRow][iCurrentColumn] = cCurrentChar;
					iJamCount = 0;
					continue;
				}
			}
		}
		if(iDirection == 2)
		{
			if(iCurrentColumn + 1 > 9 || a[iCurrentRow][iCurrentColumn + 1] != '.')	
			{
				iJamCount = iJamCount | 4;
				continue;
			}
			else
			{
				cCurrentChar++;
				if(cCurrentChar > 'Z')
				{
					break;
				}
				else
				{
					iCurrentColumn++;
					a[iCurrentRow][iCurrentColumn] = cCurrentChar;
					iJamCount = 0;
					continue;
				}
			}
		}
		if(iDirection == 3)
		{
			if(iCurrentRow + 1 > 9|| a[iCurrentRow+1][iCurrentColumn] != '.')	
			{
				iJamCount = iJamCount | 8;
				continue;
			}
			else
			{
				cCurrentChar++;
				if(cCurrentChar > 'Z')
				{
					break;
				}
				else
				{
					iCurrentRow++;
					a[iCurrentRow][iCurrentColumn] = cCurrentChar;
					iJamCount = 0;
					continue;
				}
			}
		}
	}
	for(i = 0; i < 10;++i)
	{
		for(j = 0;j<10;++j)
		{
			printf("%c ", a[i][j]);
		}
		printf("\n");
	}
	return 0;
}
