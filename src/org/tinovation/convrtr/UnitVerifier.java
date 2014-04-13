package org.tinovation.convrtr;

import java.util.HashMap;
import java.util.Iterator;

public class UnitVerifier {
	
	private HashMap<String,String> shit;
	
	public UnitVerifier(){
		shit = new HashMap<String,String>();
		shit.put("kilometer","km");
		shit.put("centimeter", "cm");
		shit.put("meter", "m");
		shit.put("millimeter", "mm" );
		shit.put("inch", "in");
		shit.put("foot", "ft");
		shit.put("yard", "yd");
		shit.put("mile","mi");
		shit.put("nanometer", "nm");

		shit.put("kilometers","km");
		shit.put("centimeters", "cm");
		shit.put("meters", "m");
		shit.put("millimeters", "mm" );
		shit.put("inches", "in");
		shit.put("feet", "ft");
		shit.put("yards", "yd");
		shit.put("miles","mi");
		shit.put("nanometers", "nm");

		shit.put("gram","g");
		shit.put("kiloram", "kg");
		shit.put("pound", "lb");
		shit.put("ton", "t" );
		shit.put("ounce","oz");

		shit.put("grams","g");
		shit.put("kilorams", "kg");
		shit.put("pounds", "lb");
		shit.put("pounds", "lbs");
		shit.put("tons", "t" );
		shit.put("ounces","oz");

		shit.put("pint","pt");
		shit.put("quart", "qt");
		shit.put("gallon", "gal");
		shit.put("fluid ounce", "fl oz" );
		shit.put("millileter","mL");
		shit.put("liter","L");
		shit.put("cup", "c");
		shit.put("tablespoon", "tbsp");
		shit.put("centimeter cubed", "cm^3");
		shit.put("meter cubed", "m^3");

		shit.put("pints","pt");
		shit.put("quarts", "qt");
		shit.put("gallons", "gal");
		shit.put("fluid ounces", "fl oz" );
		shit.put("millileters","mL");
		shit.put("liters","L");
		shit.put("tablespoons", "tbsp");
		shit.put("cups", "c");
		shit.put("centimeters cubed", "cm^3");
		shit.put("meters cubed", "m^3");

		shit.put("second","sec");
		shit.put("minute", "min");
		shit.put("hour", "hr");
		shit.put("month", "mo" );
		shit.put("year","yr");
		shit.put("day", "day");
		shit.put("century","cent");
		shit.put("decade", "dec");
		shit.put("nanosecond","ns");
		shit.put("millisecond", "ms");

		shit.put("seconds","sec");
		shit.put("minutes", "min");
		shit.put("hours", "hr");
		shit.put("months", "mo" );
		shit.put("years","yr");
		shit.put("days", "days");
		shit.put("cent","century");
		shit.put("decades", "dec");
		shit.put("nanoseconds","ns");
		shit.put("milliseconds", "ms");
	}
	
	public String getAbbreviatedUnit(String unit){
		
		String n = "";
		for(int i = 0; i < unit.length();i++){
			n += Character.toLowerCase(unit.charAt(i));
		}
		
		Iterator<String> i = shit.keySet().iterator();
		while(i.hasNext()){
			if(((String)i.next()).equals(n)){
				return shit.get(n);
			}
		}
		
		//must be abbreviation
		Iterator<String> j = shit.keySet().iterator();
		while(j.hasNext()){
			if(shit.get(((String)j.next())).equals(n)){
				return n;
			}
		}
		
		
		return "ERROR";
	}

}
