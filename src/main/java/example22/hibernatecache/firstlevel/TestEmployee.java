package example22.hibernatecache.firstlevel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestEmployee {

	public static void main(String[] args) {

		Configuration config = new Configuration();
		config.addAnnotatedClass(Employee.class);
		config.configure();
		
		// Run it by disabling only once
		//new SchemaExport(config).create(true, true);
		
		// Hibernate 3.6.9 way of creating SessionFactory
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		/* Run this disabled code only once and then disable it again to see the output given below.
		 */		
		/*for(int i=0; i<3; i++) {
			Employee emp = new Employee();
			emp.setEmpName("Emp "+ i);
			session.save(emp);
		}
		
		Query query = session.createQuery("from Employee");*/
		
		Employee emp1 = (Employee) session.get(Employee.class, 2);
		emp1.setEmpName("Karan"); // select before update is happening by default
		
		/* If you try to fetch the same value again from DB then Hibernate provides 
		 * the value from first level cache instead of running the select query again 
		 * and for confirming that check the console output you'll see 
		 * only one select query running. */
		Employee emp2 = (Employee) session.get(Employee.class, 2);
		
		session.getTransaction().commit();
		
	}
}
