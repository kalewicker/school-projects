#include <iostream>
#include <string>
#include "networkStudent.h"
using namespace std;

NetworkStudent::NetworkStudent() : Student() {
	setDegreeProgram(NETWORKING);
}
NetworkStudent::NetworkStudent(string studentID, string firstName, string lastName, string emailAddress, int age, int* days, Degree degree) : Student(studentID, firstName, lastName, emailAddress, age, days) {
	setDegreeProgram(NETWORKING);
}
Degree NetworkStudent::getDegreeProgram() {
	return NETWORKING;
}
void NetworkStudent::setDegreeProgram(Degree degree) {
	this->degree = NETWORKING;
}
void NetworkStudent::print() {
	this->Student::print();
	cout << "NETWORKING"<< endl;
}
NetworkStudent::~NetworkStudent() {
	Student::~Student();
}

