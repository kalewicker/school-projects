#ifndef NETWORKSTUDENT_H
#define NETWORKSTUDENT_H
#include "student.h"
#include "degree.h"
#include <iostream>
#include <string>
using namespace std;

class NetworkStudent : public Student {
public:
	NetworkStudent();
	NetworkStudent(string studentID, string firstName, string lastName, string emailAddress, int age, int* days, Degree degree);
	~NetworkStudent();
	void print();
	Degree getDegreeProgram();
	void setDegreeProgram(Degree degree);
};




#endif
