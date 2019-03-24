var MyJavaClass = Java.type('com.siraon.js.demo02.ScriptObjectMirrorDemo');

function Person(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.getFullName = function () {
        return this.firstName + " " + this.lastName;
    }
}

var person1 = new Person("Peter", "Parker");
MyJavaClass.fun4(person1);