package example22.hibernatecache.querycache;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/* This class is created to check what happens when you create same query
 *  in 2 different sessions? 
 * Hibernate will run 2 queries one for each session.
 * How to address this problem with querycache?
 */
public class TestEmployee {

	public static void main(String[] args) {

		Configuration config = new Configuration();
		config.addAnnotatedClass(Employee.class);
		config.configure();
		
		// Run it by disabling only once
		//new SchemaExport(config).create(true, true);
		
		// Hibernate 3.6.0.Final way of creating SessionFactory
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		/* Run this disabled code only once and then disable it again to see the output given below.
		 */		
		/*for(int i=0; i<3; i++) {
			Employee emp = new Employee();
			emp.setEmpName("Emp "+ i);
			session.save(emp);
		}*/
		
		Query query = session.createQuery("from Employee where empId = 1");
		/* This first setCacheable checks If querycache is not already having the values 
		 * Go to DB pull up the record and set the querycache.
		 */		
		query.setCacheable(true); 
		List employess = query.list();
		
		session.getTransaction().commit();
		session.close(); // Not required as session is already closed.
		
		Session session2 = factory.openSession();
		session2.beginTransaction();
		
		/* Since querycache is enabled now it'll run one select query only once and can be seen in console*/
		Query query2 = session2.createQuery("from Employee where empId = 1");
		/* This second setCacheable checks If querycache is already having the value 
		 * then pull up the record from querycache. */
		query2.setCacheable(true);
		List employess2 = query2.list();
		
		session2.getTransaction().commit();

	}
}