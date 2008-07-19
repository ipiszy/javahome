import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public abstract class Vehicle {
	abstract float velocity(int a,int b,int c);
	abstract float velocity(int a,int b,float c);
}
abstract class Type1Vehicle extends Vehicle{
	float velocity(int a,int b,float c)
	{
		return -1;
	}
}
abstract class Type2Vehicle extends Vehicle{
	float velocity(int a,int b,int c)
	{
		return -1;
	}
}
class Car extends Type1Vehicle{

	@Override
	float velocity(int a, int b, int c) {
		// TODO Auto-generated method stub
		return a+b/c;
	}
	
}
//class Ship extends Type2Vehicle{
	//float velocity(int a,int )
//}