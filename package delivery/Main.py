# Kale Wicker

import csv
import datetime

#class that creates a chaining hash table
#Source: zyBooks 7.8.2 hash table using chaining
class HashTable:
    def __init__(self, capacity = 40):
        self.table = []
        for i in range(capacity):
            self.table.append([])
#method that adds a new key/value pair to the hash table
    def add(self, key, value):
        bucket = hash(key) % len(self.table)
        bucket_list = self.table[bucket]

        for kv in bucket_list:
            if kv[0] == key:
                kv[1] = value
                return True

        key_value = [key, value]
        bucket_list.append(key_value)
        return True
#method that returns a value by searching for its corresponding key
    def get(self, key):
        bucket = hash(key) % len(self.table)
        bucket_list = self.table[bucket]

        for kv in bucket_list:
            if kv[0] == key:
                return kv[1]
        return None
#method that removes a key/value pair from the hash table
    def delete(self, key):
        bucket = hash(key) % len(self.table)
        bucket_list = self.table[bucket]

        if key in bucket_list:
            bucket_list.remove(key)
#class that creates package objects with any necessary attributes that are to be added to the hash table
class Package:
    def __init__(self, ID, address, city, state, zip, deadline, mass, notes, status, departure, delivered):
        self.ID = ID
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self.deadline = deadline
        self.mass = mass
        self.notes = notes
        self.status = status
        self.departure = departure
        self.delivered = delivered
#method that returns a string for a package object
    def __str__(self):
        return "ID: %s, Address: %s, %s, %s, %s, Deadline: %s, %s, %s, Status: %s, Departed: %s, Delivered: %s" % (self.ID, self.address, self.city, self.state, self.zip, self.deadline, self.mass, self.notes, self.status, self.departure, self.delivered)
#method that loads data from a CSV file and then instantiates package objects that are added to the hash table
def LoadPackages(fileName):
    with open(fileName) as packages:
        packageData = csv.reader(packages, delimiter=',')
        for package in packageData:
            ID = int(package[0])
            address = package[1]
            city = package[2]
            state = package[3]
            zip = package[4]
            deadline = package[5]
            mass = package[6]
            notes = package[7]
            status = ""
            departure = datetime.timedelta()
            delivered = datetime.timedelta()

            package = Package(ID, address, city, state, zip, deadline, mass, notes, status, departure, delivered)
            hashtable.add(ID, package)

#creates a hash table object
hashtable = HashTable()
#loads the packages CSV file
LoadPackages("packages.csv")

#loads the addresses CSV file and returns the index for an address
def addressIndex(add):
    with open("addresses.csv") as addresses:
        addressData = list(csv.reader(addresses))
        for row in addressData:
            if add in row[2]:
                return int(row[0])

#loads the distances CSV file and returns the distance between addresses
def findDistance(add1, add2):
    with open("distances.csv") as distances:
        distanceData = list(csv.reader(distances))
        distance = distanceData[addressIndex(add1)][addressIndex(add2)]
        if distance == "":
            distance = distanceData[addressIndex(add2)][addressIndex(add1)]
        return float(distance)

#class that creates truck objects with the necessary attributes
class Truck:
    def __init__(self, miles, location, departure, packages):
        self.miles = miles
        self.location = location
        self.time = departure
        self.departure = departure
        self.packages = packages
#method that returns a string for a truck object
    def __str__(self):
        return "%s, %s, %s, %s" % (self.miles, self.location, self.departure, self.packages)

#creating truck objects and manually loading the packages
truck1 = Truck(0.0, "4001 South 700 East", datetime.timedelta(hours=8), [1, 15, 13, 14, 16, 19, 20, 29, 30, 31, 34, 37, 40, 2, 4, 5])
truck2 = Truck(0.0, "4001 South 700 East", datetime.timedelta(hours=9, minutes=5),  [3, 6, 18, 25, 28, 32, 36, 38])
truck3 = Truck(0.0, "4001 South 700 East", datetime.timedelta(hours=9, minutes=42), [7, 8, 9, 10, 11, 12, 17, 21, 22, 23, 24, 26, 27, 33, 35, 39])

#method that delivers the packages using the nearest neighbor algorithm
def Delivery(truck):
#creates a list of packages that need to be delivered
    packages = [hashtable.get(ID) for ID in truck.packages]
#loops until all packages have been delivered
    while len(packages) > 0:
        next = None
        nearest = 100
#nearest neighbor implementation and keeping track of package times
        for package in packages:
            if findDistance(truck.location, package.address) < nearest:
                nearest = findDistance(truck.location, package.address)
                next = package
        packages.remove(next)
        truck.location = next.address
        truck.miles += nearest
        next.departure = truck.departure
        truck.time += datetime.timedelta(hours=nearest/18)
        next.delivered = truck.time

#calls the delivery method for each truck
Delivery(truck1)
Delivery(truck2)
#calls the third truck when one of the first two are finished
truck3.departure = min(truck1.time, truck2.time)
Delivery(truck3)

print("Total miles:", (truck1.miles + truck2.miles + truck3.miles))

#loop that creates the user interface and updates the status of packages
while (True):
    entryTime = input("Enter a time in HH:MM format to view the status of packages: ")
    (h, m) = entryTime.split(":")
    time = datetime.timedelta(hours=int(h), minutes=int(m))

    entry = input("Enter a package ID (1-40) or leave blank to view all packages: ")

    packages = [hashtable.get(ID) for ID in range(1, 41)]
#updates package #9 with the correct address at the appropriate time
    if time > datetime.timedelta(hours=10, minutes=20):
        packages[8].address = "410 S State St"
        packages[8].zip = 84111

#updates package status for one or all packages based on the user's entered time
    if entry == "":
        for p in packages:
            if time < p.departure:
                p.status = "At the Hub"
            elif time < p.delivered:
                p.status = "In Transit"
            else:
                p.status = "Delivered"
            print(p)
    else:
        p = packages[int(entry)-1]
        if time < p.departure:
            p.status = "At the Hub"
        elif time < p.delivered:
            p.status = "In Transit"
        else:
            p.status = "Delivered"
        print(p)


