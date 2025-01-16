#include <iostream>
#include <string>
#include "securityStudent.h"
using namespace std;

SecurityStudent::SecurityStudent() : Student() {
	setDegreeProgram(SECURITY);
}
SecurityStudent::SecurityStudent(string studentID, string firstName, string lastName, string emailAddress, int age, int* days, Degree degree) : Student(studentID, firstName, lastName, emailAddress, age, days) {
	setDegreeProgram(SECURITY);
}
Degree SecurityStudent::getDegreeProgram() {
	return SECURITY;
}
void SecurityStudent::setDegreeProgram(Degree degree) {
	this->degree = SECURITY;
}
void SecurityStudent::print() {
	this->Student::print();
	cout << "SECURITY" << endl;
}
SecurityStudent::~SecurityStudent() {
	Student::~Student();
}

