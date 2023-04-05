package moh.adp.db.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import moh.adp.db.common.TestDBException;

public class Convert {

	public static <U,V> V bean2Bean(U srcObject, Class<V> destObjectClass) {
		try {
			V v = destObjectClass.newInstance();
			BeanUtils.copyProperties(v, srcObject);
			return v;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new TestDBException("Error converting ORM or model bean", e);
		}
	}

	public static <U,V> List<V> beans2Beans(List<U> beanList, Class<V> destObjectClass) {
		if (beanList == null)
			return null;
		List<V> out = new ArrayList<>();
		beanList.forEach(bean -> out.add(bean2Bean(bean, destObjectClass)) );
		return out;
	}

}
