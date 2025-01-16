#ifndef ROSTER_H
#define ROSTER_H
#include "student.h"
#include <iostream>
#include <string>
using namespace std;

class roster {
public:
	roster();
	roster(int max);
	~roster();
	Student* getDegreeAt(int index);
	void stringParse(string row);
	void add(string studentID, string firstName, string lastName, string emailAddress,int age, int days1, int days2, int days3, Degree degree);
	void remove(string studentID);
	void printAll();
	void printDaysInCourse(string studentID);
	void printInvalidEmails();
	void printByDegreeProgram(Degree degree);
	int lastIndex;
	int max;
	Student** classRosterArray;

};

#endif