#ifndef STUDENT_H
#define STUDENT_H
#include "degree.h"
#include <string>
using namespace std;

class Student {
	public:
		void SetstudentID(string studentID);
		void Setage(int age);
		void Setdays(int days[]);
		void SetfirstName(string firstName);
		void SetlastName(string lastName);
		void SetemailAddress(string emailAddress);
		string GetstudentID() const;
		int Getage() const;
		int* Getdays() const;
		string GetfirstName() const;
		string GetlastName() const;
		string GetemailAddress() const;
		Student();
		Student(string studentID, string firstName, string lastName, string emailAddress, int age, int days[]);
		~Student();
		virtual void print() = 0;
		virtual Degree getDegreeProgram() = 0;
		virtual void setDegreeProgram(Degree degree) = 0;
		const static int daysArraySize = 3;
		


	protected:
		string studentID;
		int age;
		int* days;
		string firstName;
		string lastName;
		string emailAddress;
		Degree degree;

};


#endif
