#ifndef SOFTWARESTUDENT_H
#define SOFTWARESTUDENT_H
#include "student.h"
#include "degree.h"
#include <iostream>
#include <string>
using namespace std;

class SoftwareStudent : public Student {
public:
	SoftwareStudent();
	SoftwareStudent(string studentID, string firstName, string lastName, string emailAddress, int age, int* days, Degree degree);
	~SoftwareStudent();
	void print();
	Degree getDegreeProgram();
	void setDegreeProgram(Degree degree);
};





#endif
