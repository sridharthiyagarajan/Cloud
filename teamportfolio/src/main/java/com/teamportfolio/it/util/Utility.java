package com.teamportfolio.it.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Utility {

	public static Timestamp retrieveCurrentTimeStamp() {

		return Timestamp.valueOf(LocalDateTime.now(Constants.UTC_ZONE_ID));
	}
}
