#include "degree.h"
#include "student.h"
#include "networkStudent.h"
#include "securityStudent.h"
#include "softwareStudent.h"
#include "roster.h"
#include <iostream>
#include <string>
using namespace std;

roster::roster() {
	this->max = 0;
	this->lastIndex = -1;
	this->classRosterArray = nullptr;
}
roster::roster(int max) {
	this->max = max;
	this->lastIndex = -1;
	this->classRosterArray = new Student*[max];
}
Student* roster::getDegreeAt(int index) {
	return classRosterArray[index];
}
void roster::stringParse(string row) {
	if (lastIndex < max) {
		++lastIndex;
		Degree degree;
		if (row.find('N') != string::npos) {
			this->classRosterArray[lastIndex] = new NetworkStudent();
			classRosterArray[lastIndex]->setDegreeProgram(NETWORKING);
			degree = Degree::NETWORKING;
		}
		if (row.find("SE") != string::npos) {
			this->classRosterArray[lastIndex] = new SecurityStudent();
			classRosterArray[lastIndex]->setDegreeProgram(SECURITY);
			degree = Degree::SECURITY;
		}
		if (row.find("SO") != string::npos) {
			this->classRosterArray[lastIndex] = new SoftwareStudent();
			classRosterArray[lastIndex]->setDegreeProgram(SOFTWARE);
			degree = Degree::SOFTWARE;
		}
		int rs = row.find(",");
		string studentID = row.substr(0, rs);
		int ls = rs + 1;
		rs = row.find(",", ls);
		string firstName = row.substr(ls, rs - ls);
		ls = rs + 1;
		rs = row.find(",", ls);
		string lastName = row.substr(ls, rs - ls);
		ls = rs + 1;
		rs = row.find(",", ls);
		string emailAddress = row.substr(ls, rs - ls);
		ls = rs + 1;
		rs = row.find(",", ls);
		int age = stoi(row.substr(ls, rs - ls));
		ls = rs + 1;
		rs = row.find(",", ls);
		int days1 = stoi(row.substr(ls, rs - ls));
		ls = rs + 1;
		rs = row.find(",", ls);
		int days2 = stoi(row.substr(ls, rs - ls));
		ls = rs + 1;
		rs = row.find(",", ls);
		int days3 = stoi(row.substr(ls, rs - ls));
		add(studentID, firstName, lastName, emailAddress, age, days1, days2, days3, degree);
	}
	else {
		exit(-1);
	}
}
	void roster::add(string studentID, string firstName, string lastName, string emailAddress,int age, int days1, int days2, int days3, Degree degree) {
		int classDays[Student::daysArraySize];
		classDays[0] = days1;
		classDays[1] = days2;
		classDays[2] = days3;
		if (degree == NETWORKING) classRosterArray[lastIndex] = new NetworkStudent(studentID, firstName, lastName, emailAddress, age, classDays, degree);
		else if (degree == SECURITY) classRosterArray[lastIndex] = new SecurityStudent(studentID, firstName, lastName, emailAddress, age, classDays, degree);
		else classRosterArray[lastIndex] = new SoftwareStudent(studentID, firstName, lastName, emailAddress, age, classDays, degree);
	}
	void roster::printAll() {
		cout << endl;
		for (int i = 0; i <= this->lastIndex; ++i) (this->classRosterArray)[i]->print();
	}
	void roster::remove(string studentID) {
		bool removed = false;
		for (int i = 0; i <= lastIndex; ++i) {
			if (this->classRosterArray[i]->GetstudentID() == studentID) {
				delete this->classRosterArray[i];
				this->classRosterArray[i] = this->classRosterArray[lastIndex];
				--lastIndex;
				removed = true;
			}
		}
		if (removed == true) {
			cout << "Student not found" << endl;
		}
	}
	void roster::printDaysInCourse(string studentID) {
		for (int i = 0; i <= lastIndex; ++i) {
			if (this->classRosterArray[i]->GetstudentID() == studentID) {
				int* n = classRosterArray[i]->Getdays();
				cout << studentID << "'s average number of days in a course is: " << (n[0] + n[1] + n[2]) / 3.0 << endl;
			}
		}
	}
	void roster::printInvalidEmails() {
		bool valid = false;
		for (int i = 0; i <= lastIndex; ++i) {
			string email = classRosterArray[i]->GetemailAddress();
			if (((email.find('@') == string::npos) || (email.find('.') == string::npos)) || (email.find(' ') != string::npos)) valid = false;
			else {
				valid = true;
			}
			if (valid == false) {
				cout << email << ": is invalid" << endl;
			}
		}
		cout << endl;
	}
	void roster::printByDegreeProgram(Degree degree) {
		for (int i = 0; i <= lastIndex; ++i) {
			if (this->classRosterArray[i]->getDegreeProgram() == degree) this->classRosterArray[i]->print();
		}
		cout << endl;
	}
	roster::~roster() {
		for (int i = 0; i <= lastIndex; ++i) {
			delete this->classRosterArray[i];
		}
		delete classRosterArray;
	}



int main() {

	int numStudents = 5;
	const string studentData[5] =
	   {"A1,	John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
		"A2,Suzan,Erickson,Erickson_1990@gmailcom,19	,50,30,40,NETWORK",
		"A3,	Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
		"A4,Erin,Black,Erin.black@comcast.net,22	,50,58,40,SECURITY",
		"A5,	Kale,Wicker,kwicke8@my.wgu.edu,28,7,8,9,SOFTWARE"};

	roster* classRoster = new roster(numStudents);
	for (int i = 0; i < numStudents; ++i) {
		classRoster->stringParse(studentData[i]);
	}
	cout << "Scripting and Programming Applications C867, Programming Language: C++, Student ID: 001254935, Kale Wicker" << endl;
	classRoster->printAll();
	cout << endl;
	classRoster->printInvalidEmails();
	for (int i = 0; i < numStudents; ++i) {
		classRoster->printDaysInCourse(classRoster->classRosterArray[i]->GetstudentID());
	}
	cout << endl;
	classRoster->printByDegreeProgram(SOFTWARE);
	classRoster->remove("A3");
	classRoster->remove("A3");
	classRoster->~roster();

	system("pause");
	return 0;
}

























