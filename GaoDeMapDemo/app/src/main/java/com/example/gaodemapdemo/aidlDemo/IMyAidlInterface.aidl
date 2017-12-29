// IMyAidlInterface.aidl
package com.example.gaodemapdemo.aidlDemo;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
// You can pass the value of in, out or inout
// The primitive types (int, boolean, etc) are only passed by in
int add( int value1,  int value2);

void changeView(String str);

}
