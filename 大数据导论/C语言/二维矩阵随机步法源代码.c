#include "stdio.h"
#include "stdlib.h"
#include "time.h"

//此函数产生随机方向，0向左,1向上,2向右,3向下
int GetRandomDirection()
{
	int i = rand();
	return i % 4;
}

int main()
{
	char a[10][10];

	int i = 0, j = 0;
	for(i = 0; i < 10;++i) //为矩阵赋初值
	{
		for(j = 0;j<10;++j)
		{
			a[i][j] = '.';
		}
	}
	srand( (unsigned)time( NULL ) ); //初始化随机数种子 
	int iDirection;
	char cCurrentChar = 'A';
	int iCurrentRow = 0, iCurrentColumn;
	iCurrentColumn = rand() % 10;
	a[iCurrentRow][iCurrentColumn] = cCurrentChar;
	int iJamCount = 0; //代表有几个方向被堵死,理解时可将其分解为二进制：000000001代表左边被堵死；00000010代表上边被堵死；
	                   //00000100代表右边被堵死；00001000代表下边被堵死；000000011代表左边和上边都被堵死；
						//00000111代表代表左边、上边、右边都被堵死；00001111代表代表四个方向都被堵死，此时iJamCount刚好为15，循环终止。
	while(iJamCount != 15)
	{
		iDirection = GetRandomDirection(); //0向左,1向上,2向右,3向下
		if(iDirection == 0)
		{
			if(iCurrentColumn - 1 < 0 || a[iCurrentRow][iCurrentColumn - 1] != '.')	//如果越界，则标记该方向被堵死，然后重新生成随机数
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
