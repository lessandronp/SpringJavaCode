package com.solucaocriativa.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.exceptions.ConversionException;
import org.eclipse.persistence.internal.helper.ConversionManager;
import org.eclipse.persistence.internal.sessions.AbstractRecord;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.queries.QueryRedirector;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

/***
 * This class is a simple query redirector that intercepts the result of a
 * native query and builds an instance of the specified JavaBean class from each
 * result row. The order of the selected columns musts match the JavaBean class
 * constructor arguments order.
 * 
 * To configure a JavaBeanResult on a native SQL query use:
 * JavaBeanResult.setQueryResultClass(query, SomeBeanClass.class); where query
 * is either a JPA SQL Query or native EclipseLink DatabaseQuery.
 * 
 */
public final class JavaBeanResult implements QueryRedirector {
    private static final long serialVersionUID = 3025874987115503731L;
    protected Class<?> resultClass;

    public static void setQueryResultClass(Query query, Class<?> resultClass) {
	JavaBeanResult javaBeanResult = new JavaBeanResult(resultClass);
	DatabaseQuery databaseQuery = JpaHelper.getDatabaseQuery(query);
	databaseQuery.setRedirector(javaBeanResult);
    }

    public static void setQueryResultClass(DatabaseQuery query,
	    Class<?> resultClass) {
	JavaBeanResult javaBeanResult = new JavaBeanResult(resultClass);
	query.setRedirector(javaBeanResult);
    }

    protected JavaBeanResult(Class<?> resultClass) {
	this.resultClass = resultClass;
    }

    @SuppressWarnings("unchecked")
    public Object invokeQuery(DatabaseQuery query, Record arguments,
	    Session session) {
	List<Object> results = new ArrayList<Object>();
	try {
	    Constructor<?> javaBeanClassConstructor = (Constructor<?>) resultClass
		    .getDeclaredConstructors()[0];
	    Class<?>[] constructorParameterTypes = javaBeanClassConstructor
		    .getParameterTypes();
	    List<Object[]> rows = (List<Object[]>) query.execute(
		    (AbstractSession) session, (AbstractRecord) arguments);
	    for (Object[] columns : rows) {
		Object[] constructorArgs = new Object[constructorParameterTypes.length];
		if (columns.length != constructorParameterTypes.length) {
		    throw new ColumnParameterNumberMismatchException(
			    resultClass);
		}
		for (int j = 0; j < columns.length; j++) {
		    Object columnValue = columns[j];
		    Class<?> parameterType = constructorParameterTypes[j];
		    // convert the column value to the correct type--if possible
		    constructorArgs[j] = ConversionManager.getDefaultManager()
			    .convertObject(columnValue, parameterType);
		}
		results.add(javaBeanClassConstructor
			.newInstance(constructorArgs));
	    }
	} catch (ConversionException e) {
	    throw new ColumnParameterMismatchException(e);
	} catch (IllegalArgumentException e) {
	    throw new ColumnParameterMismatchException(e);
	} catch (InstantiationException e) {
	    throw new ColumnParameterMismatchException(e);
	} catch (IllegalAccessException e) {
	    throw new ColumnParameterMismatchException(e);
	} catch (InvocationTargetException e) {
	    throw new ColumnParameterMismatchException(e);
	}
	return results;
    }

    public final class ColumnParameterMismatchException extends
	    RuntimeException {
	private static final long serialVersionUID = 4752000720859502868L;

	public ColumnParameterMismatchException(Throwable t) {
	    super(
		    "Exception while processing query results-ensure column order matches constructor parameter order",
		    t);
	}
    }

    public final class ColumnParameterNumberMismatchException extends
	    RuntimeException {
	private static final long serialVersionUID = 1776794744797667755L;

	public ColumnParameterNumberMismatchException(Class<?> clazz) {
	    super(
		    "Number of selected columns does not match number of constructor arguments for: "
			    + clazz.getName());
	}
    }
}