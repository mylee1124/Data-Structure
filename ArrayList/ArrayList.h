/*
 * ArrayList.h
 *
 *  Created on: 2015. 8. 27.
 *      Author: User
 */

#ifndef ARRAYLIST_H_
#define ARRAYLIST_H_

#define TRUE 1
#define FALSE 0

#define LIST_LEN 100
typedef int LData;

typedef struct __ArrayList
{
	LData arr[LIST_LEN];
	int numOfData;
	int curPosition;
}ArrayList;

typedef ArrayList List;

void ListInit(List * plist);
void LInsert(List * plist, LData data);

int LFirst(List * plist, LData * pdata);
int LNext(List * plist, LData * pdata);

LData LRemove(List * plist);
int LCount(List * plist);
#endif /* ARRAYLIST_H_ */
