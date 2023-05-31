package moh.adp.test.conversion;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;


public class FieldCopy {
	private static SimpleDateFormat sdf;
	
	static {
		sdf = new SimpleDateFormat("yyyy/MM/dd");
	}
	
	public static <T, U> void copy(T sink, U src, String sinkProperty, List<String> srcGetters) {
		if (sink == null)
			throw new FieldCopyException(null, src.getClass(), sinkProperty, srcGetters, "sink was null");
		if (src == null)
			throw new FieldCopyException(sink.getClass(), null, sinkProperty, srcGetters, "src was null");		
		try {
			Object obj = getProperty(src, src.getClass(), srcGetters, 0);
			if (obj != null) {
				BeanUtils.copyProperty(sink, sinkProperty, convert(obj));
				//System.out.println("should be set... " + BeanUtils.getProperty(sink, sinkProperty));
			} else
				System.out.println("null object corresponding to " + sinkProperty);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new FieldCopyException(sink.getClass(), src.getClass(), sinkProperty, srcGetters, e.getMessage());
		}
	}
	
	private static Object convert(Object obj) {
		if (obj instanceof Date)
			return sdf.format((Date)obj);
		return obj;
	}

	private static <U> Object getProperty(U src, Class<?> srcClass, List<String> srcPropertyNames, int depth) throws Exception {
		if (src == null || srcPropertyNames.size() < depth + 1)
			return null;
		String getName = srcPropertyNames.get(depth);
		try {
			Method m = srcClass.getMethod(getName, new Class<?>[]{});		
			Object out = m.invoke(src, new Object[]{});			
			return (srcPropertyNames.size() == depth + 1) ? out : getProperty(out, m.getReturnType(), srcPropertyNames, ++depth); //recur
		} catch (NoSuchMethodException e) {
			System.out.println("no such method " + getName + " on " + srcClass.getCanonicalName() + " at depth " + depth);
		}
		return null;
	}

}
