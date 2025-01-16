#include "degree.h"
#include "student.h"
#include <iostream>
#include <string>
using namespace std;

void Student::SetstudentID(string studentID) {
	this->studentID = studentID;
}
void Student::Setage(int age) {
	this->age = age;
}
void Student::Setdays(int days[]) {
	for (int i = 0; i < daysArraySize; ++i)
		this->days[i] = days[i];
}
void Student::SetfirstName(string firstName) {
	this->firstName = firstName;
}
void Student::SetlastName(string lastName) {
	this->lastName = lastName;
}
void Student::SetemailAddress(string emailAddress) {
	this->emailAddress = emailAddress;
}
string Student::GetstudentID() const {
	return studentID;
}
int Student::Getage() const {
	return age;
}
int* Student::Getdays() const {
	return days;
}
string Student::GetfirstName() const {
	return firstName;
}
string Student::GetlastName() const {
	return lastName;
}
string Student::GetemailAddress() const {
	return emailAddress;
}
Student::Student() {
	this->studentID = "";
	this->age = 0;
	this->firstName = "";
	this->lastName = "";
	this->emailAddress = "";
	this->days = new int[daysArraySize];
	for (int i = 0; i < daysArraySize; ++i) this->days[i] = 0;
}
Student::Student(string studentID, string firstName, string lastName, string emailAddress, int age, int days[]) {
	this->studentID = studentID;
	this->age = age;
	this->days = new int[daysArraySize];
	for (int i = 0; i < daysArraySize; ++i) this->days[i] = days[i];
	this->firstName = firstName;
	this->lastName = lastName;
	this->emailAddress = emailAddress;
}
Student::~Student() {
}
void Student::print() {
	string studentID = GetstudentID();
	string firstName = GetfirstName();
	string lastName = GetlastName();
	string emailAddress = GetemailAddress();
	int age = Getage();
	int* days = Getdays();
	string degree;
	if (getDegreeProgram() == NETWORKING) degree = NETWORKING;
	else if (getDegreeProgram() == SECURITY) degree = SECURITY;
	else if (getDegreeProgram() == SOFTWARE) degree = SOFTWARE;
	cout << studentID <<" "<< firstName <<" "<< lastName<<" "<< emailAddress <<" "<< age<<" "<< days[0] << " " << days[1] << " " << days[2] <<" "<< degree;
}




