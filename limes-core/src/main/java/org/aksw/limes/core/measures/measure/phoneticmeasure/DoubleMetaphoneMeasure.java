package org.aksw.limes.core.measures.measure.phoneticmeasure;

import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.measures.measure.string.StringMeasure;
import org.apache.commons.codec.language.DoubleMetaphone;

public class DoubleMetaphoneMeasure extends StringMeasure {

	public static String getCode(String string) {
		DoubleMetaphone doublemetaphone = new DoubleMetaphone();
		return doublemetaphone.encode(string);
	}
	
	public double proximity(String s1, String s2) {
		char[] c1, c2;
		c1 = getCode(s1).toCharArray();
		c2 = getCode(s2).toCharArray();
		int shorter;
		int longer;
		if (c1.length>c2.length) {
			shorter = c2.length; 
			longer = c1.length;
		}else {
			shorter =  c1.length;
			longer = c2.length;
		}
		double distance = 0d;
		for (int i = 0; i < shorter; i++)
			if (c1[i] != c2[i])
				distance += 1d;
		return (1.0d - (distance / (double) longer));
	}

//	public double proximity(String s1, String s2) {
//		char[] c1, c2;
//		c1 = getCode(s1).toCharArray();
//		c2 = getCode(s2).toCharArray();
//		double proximity = 0d;
//		double check;
//	
//				check = getProximity(c1,c2);
//				if (check > proximity) {
//					proximity = check;
//				}
//		
//		return proximity;
//	}

	@Override
	public int getPrefixLength(int tokensNumber, double threshold) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getMidLength(int tokensNumber, double threshold) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double getSizeFilteringThreshold(int tokensNumber, double threshold) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getAlpha(int xTokensNumber, int yTokensNumber, double threshold) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double getSimilarity(int overlap, int lengthA, int lengthB) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean computableViaOverlap() {
		return false;
	}

	@Override
	public double getSimilarity(Object object1, Object object2) {
		return proximity(object1.toString(), object2.toString());
	}

	@Override
	public String getType() {
		return "string";
	}

	@Override
	public double getSimilarity(Instance instance1, Instance instance2, String property1, String property2) {
		double value = 0;
		double sim = 0;
		for (String source : instance1.getProperty(property1)) {
			for (String target : instance2.getProperty(property2)) {
				sim = proximity(source, target);
				if (sim > value) {
					value = sim;
				}
			}
		}
		return sim;
	}

	@Override
	public String getName() {
		return "doublemeta";
	}

	@Override
	public double getRuntimeApproximation(double mappingSize) {
		return mappingSize / 1000d;
	}

}
