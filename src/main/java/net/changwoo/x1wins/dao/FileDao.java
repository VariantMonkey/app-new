package net.changwoo.x1wins.dao;

import java.util.List;

import net.changwoo.x1wins.entity.File;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao extends GenericDaoImpl <File, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(FileDao.class);
	
	@Autowired
	FileDao(SessionFactory sf) {
		super(sf);
	}

	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return File.class;
	}
	
	public File findFirstFile(int snum, String classname) throws Exception{	
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("snum", snum)).add(Restrictions.eq("classname", classname));
		List list = getHibernateTemplate().findByCriteria(criteria);
		
		logger.info(" list size : "+list.size());
		
		File file;
		if(list.size()<1){
			throw new Exception();
//			file = null; 
		}else{
			file = (File)list.get(0);
		}

		return file;
	}
	
	public List<File> findFile(int snum, String classname, String filename) throws Exception{
		
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("snum", snum));
		criteria.add(Restrictions.eq("classname", classname));
		criteria.add(Restrictions.eq("filename", filename));
		List list = getHibernateTemplate().findByCriteria(criteria);
		
		
		logger.info(" list size : "+list.size());
		
		File file = (File)list.get(0);
		
		return list;
	}

}
