package example22.hibernatecache.querycache;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/* To enable querycache update this in hibernate.cfg.xml
* 
* <property name="cache.use_second_level_cache">true</property>
* <property name="cache.use_query_cache">true</property>
* <property name="cache.provider_class">net.sf.ehcache.hibernate.EhCacheProvider</property>
* 
* Also use @Cacheable and @Cache(usage=CacheConcurrencyStrategy.READ_ONLY) annotations on entity class
* Also give query.setCacheable(true);
* 
* After this run TestEmployee again and only single select query will run.
*/

@Entity
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
@Table(name="Employee_info")
public class Employee {

	private int empId;
	private String empName;
	
	@Id
	@GeneratedValue
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
