package example22.hibernatecache.firstlevel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/* This class is created to check what happens when you try to fetch the same object from Db
 *  in 2 different sessions? 
 * Hibernate will run 2 select queries one for each session.
 * How to address this problem with second level cache?
 */
public class TestEmployeeAgain {

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
		
		session.getTransaction().commit();
		session.close(); // Not required as session is already closed.
		
		Session session2 = factory.openSession();
		session2.beginTransaction();
		
		/* Now if you try to fetch the same object in a different session it'll result in a new select query.*/
		Employee emp2 = (Employee) session2.get(Employee.class, 2);
		
		session2.getTransaction().commit();

	}
}