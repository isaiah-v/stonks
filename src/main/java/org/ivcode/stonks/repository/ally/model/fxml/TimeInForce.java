package org.ivcode.stonks.repository.ally.model.fxml;

public enum TimeInForce {
	/** Day (or session) */
	DAY(0),
	/** Good Till Cancel (GTC) */
	GTC(1),
	/** At the Opening (OPG) */
	OPG(2),
	/** Immediate Or Cancel (IOC) */
	IOC(3),
	/** Fill Or Kill (FOK) */
	FOK(4),
	/** Good Till Crossing (GTX) */
	GTX(5),
	/** Good Till Date (GTD) */
	GTD(6),
	/** At the Close */
	CLOSE(7);

	private final int id;

	private TimeInForce(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static TimeInForce find(Integer id) {
		if(id==null) {
			return null;
		}
		
		for(TimeInForce tif : TimeInForce.values()) {
			if(id.equals(tif.id)) {
				return tif;
			}
		}
		
		return null;
	}
	
	public static Integer getId(TimeInForce timeInForce) {
		return timeInForce==null ? null : timeInForce.id;
	}
}
