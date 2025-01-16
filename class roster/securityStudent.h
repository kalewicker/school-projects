#ifndef SECURITYSTUDENT_H
#define SECURITYSTUDENT_H
#include "student.h"
#include "degree.h"
#include <iostream>
#include <string>
using namespace std;

class SecurityStudent : public Student {
public:
	SecurityStudent();
	SecurityStudent(string studentID, string firstName, string lastName, string emailAddress, int age, int* days, Degree degree);
	~SecurityStudent();
	void print();
	Degree getDegreeProgram();
	void setDegreeProgram(Degree degree);
};







#endif
