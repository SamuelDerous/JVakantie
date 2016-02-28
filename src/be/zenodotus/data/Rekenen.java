package be.zenodotus.data;



public class Rekenen {
	
	private int uren;
	private int minuten;
	private String soort;
	
	public Rekenen(String tijd, String soort) {
		totaal(tijd);
		this.soort = soort;
	}
	
	public boolean totaal(String tijd) {
		
		String[] uurMinuut = tijd.split(":");
		if(uurMinuut.length == 2 && isNumeric(uurMinuut[0]) && isNumeric(uurMinuut[1])) {
			uren = Integer.parseInt(uurMinuut[0]);
			minuten = Integer.parseInt(uurMinuut[1]);
			return true;
			
		}
		return false;
		
		
	}
	
	public boolean aftrekken(String tijd) {
		String[] uurMinuut = tijd.split(":");
		if(uurMinuut.length == 2 && isNumeric(uurMinuut[0]) && isNumeric(uurMinuut[1])) {
			uren -= Integer.parseInt(uurMinuut[0]);
			minuten -= Integer.parseInt(uurMinuut[1]);
			if (minuten < 0) {
				uren--;
				minuten = Math.abs(minuten);
			}
			return true;
			
		}
		return false;
	}
	
	public String totaal() {
		return uren + ":" + minuten;
	}
	
	
	private boolean isNumeric(String number) {
		try {
			int test = Integer.parseInt(number);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public int getUren() {
		return uren;
	}

	public void setUren(int uren) {
		this.uren = uren;
	}

	public int getMinuten() {
		return minuten;
	}

	public void setMinuten(int minuten) {
		this.minuten = minuten;
	}

	public String getSoort() {
		return soort;
	}

	public void setSoort(String soort) {
		this.soort = soort;
	}
	
	
	
	

}
