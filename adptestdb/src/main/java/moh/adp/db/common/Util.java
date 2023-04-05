package moh.adp.db.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public class Util {
	private static boolean DEV_MODE=false;
	
	public static <T> List<T> notNull(List<T> t) {
		return (t != null) ? t : new ArrayList<>();
	}

	public static <T> void persist(EntityManager em, T t) {
		if (!DEV_MODE)
			em.persist(t);
	}
	
}
