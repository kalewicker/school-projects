#include <iostream>
#include <string>
#include "softwareStudent.h"
using namespace std;

SoftwareStudent::SoftwareStudent() : Student() {
	setDegreeProgram(SOFTWARE);
}
SoftwareStudent::SoftwareStudent(string studentID, string firstName, string lastName, string emailAddress, int age, int* days, Degree degree) : Student(studentID, firstName, lastName, emailAddress, age, days) {
	setDegreeProgram(SOFTWARE);
}
Degree SoftwareStudent::getDegreeProgram() {
	return SOFTWARE;
}
void SoftwareStudent::setDegreeProgram(Degree degree) {
	this->degree = SOFTWARE;
}
void SoftwareStudent::print() {
	this->Student::print();
	cout << "SOFTWARE" << endl;
}
SoftwareStudent::~SoftwareStudent() {
	Student::~Student();
}

