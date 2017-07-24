
public class TestMatrix {
	
	public static void main(String []args){
	Matrix m = new Matrix();
	m.Print();
	long startTime = System.currentTimeMillis();
	m.solve();
	long endTime   = System.currentTimeMillis();
	long totalTime = endTime - startTime;
	System.out.println("tid för solv i ms:");
	System.out.println(totalTime);
	System.out.println("\n");
	m.Print();
	System.out.println("\n");
	m.PrintAlt();
	System.out.println("\n");
	//System.out.println(m.minAlt().size());
	
	}
}
