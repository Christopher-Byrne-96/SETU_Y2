package Lab9aq2;

public interface ImportDuty {
	
	final double  CARTAXRATE = 0.10;
	final double  HGVTAXRATE = 0.15;     // Used final as I think const is used in C and I assume final is the same thing. Also it cant be private and final?
										// is that because it cant be modified regardless so it doesnt need top be made private.
	
	public double calculateDuty();
}
