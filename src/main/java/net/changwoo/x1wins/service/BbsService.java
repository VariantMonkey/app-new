package net.changwoo.x1wins.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.changwoo.x1wins.dao.BbsDao;
import net.changwoo.x1wins.dao.ConfigDao;
import net.changwoo.x1wins.dao.UserDao;
import net.changwoo.x1wins.entity.Bbs;
import net.changwoo.x1wins.entity.Config;
import net.changwoo.x1wins.entity.Reply;
import net.changwoo.x1wins.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BbsService {

	private static final Logger logger = LoggerFactory
			.getLogger(BbsService.class);

	@Autowired
	private BbsDao bbsDao;

	@Autowired
	private ConfigDao configDao;

	@Autowired
	private UserDao userDao;

	public void saveBbs(Bbs bbs, int bbsnum, HttpServletRequest request)
			throws Exception {

		Config config = configDao.findAllByProperty("bbsnum", bbsnum).get(0);
		// config.setBbsnum(bbsnum);
		// config.setBbsname("test");
		// config.setUserid("tester");
		HttpSession session = request.getSession(false);
		String userid;
		if (session.getAttribute("userid") != null) {
			userid = session.getAttribute("userid").toString();
		} else {
			userid = "illegal userid";
		}

		bbs.setIp(request.getRemoteAddr());
		bbs.setUserid(userid);
		bbs.setConfig(config);

		bbsDao.saveOrUpdate(bbs);

	}

	public void updateBbs(Bbs bbs, int bbsnum, HttpServletRequest request)
			throws Exception {

		int count = bbsDao.findById(bbs.getNum()).getCount();
		bbs.setCount(count);
		saveBbs(bbs, bbsnum, request);

	}

	public void delete(int bbsnum, int num) throws Exception {

		Bbs bbs = bbsDao.findById(num);
		bbsDao.delete(bbs);
	}

	public void findListAndPaging(int bbsnum, int pageNum, int perPage,
			Map model, HttpServletRequest request) throws Exception {

		// bbs list
		List<Bbs> bbss = bbsDao.findList(bbsnum);

		// total row size
		int totalRowSize = bbss.size();

		// list sublist index value
		int fromIndex = (pageNum - 1) * perPage;
		int toIndex = fromIndex + perPage;
		if (toIndex > totalRowSize) {
			toIndex = totalRowSize;
		}

		// list sublist
		bbss = bbss.subList(fromIndex, toIndex);

		logger.debug("bbss.size() is : " + bbss.size());
		logger.debug("totalRowSize is : " + totalRowSize);

		int pageSize = 0;

		if (totalRowSize % perPage == 0) {
			pageSize = totalRowSize / perPage;
		} else {
			pageSize = totalRowSize / perPage + 1;
		}

		int startPage = 0;
		int endPage = 0;
		startPage=1;
		endPage = startPage + perPage;
		if (pageSize < endPage) {
			endPage = pageSize;
		}
		String paging = "";
		int prePage=(pageNum>1?pageNum-1:pageNum);
		int nextPage=(pageNum<pageSize?pageNum+1:pageNum);
		// bbs list type
		Config config = configDao.findAllByProperty("bbsnum", bbsnum).get(0);
		int listTypeNum = config.getListTypeNum();
		String bbsname = config.getBbsname();

		List<Config> configs = configDao.findAllByProperty("publicYn", 1);
		logger.debug(configs.size() + " configs size");
		model.put("pageSize", pageSize);
		model.put("curPage", pageNum);
		model.put("prePage", prePage);
		model.put("nextPage", nextPage);
		model.put("firstPage", startPage);
		model.put("lastPage", endPage);
		model.put("bbsnum", bbsnum);;
		model.put("bbsname", bbsname);
		model.put("bbss", bbss);
		model.put("paging", paging);
		model.put("listtypenum", listTypeNum);
		model.put("configs", configs);

	}

	public Bbs findDetail(int num) throws Exception {

		Bbs bbs = null;
		try {
			bbs = bbsDao.findBbsDetailById(num);

			int count = bbs.getCount();
			count++;
			bbs.setCount(count);
			bbsDao.saveOrUpdate(bbs);
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		return bbs;

	}

	public boolean validSignin(int bbsnum, HttpServletRequest request)
			throws Exception {

		boolean resultBoolean = false;

		HttpSession session = request.getSession(false);
		// if (request.getSession(false) != null){
		logger.debug("login id " + session.getAttribute("userid"));
		if (session.getAttribute("userid") != null) {
			// login y
			logger.debug("login y ");

			// userid
			// String userid = session.getAttribute("userid").toString();
			// User user = userDao.findAllByProperty("userid", userid).get(0);
			resultBoolean = true;

		} else {

			resultBoolean = false;

		}

		if (resultBoolean == false) {
			String message = "no sigin";
			throw new Exception(message);
		}

		return resultBoolean;
	}

	public boolean validOwn(int num, HttpServletRequest request)
			throws Exception {

		boolean resultBoolean = false;

		HttpSession session = request.getSession(false);
		// if (request.getSession(false) != null){
		logger.debug("login id " + session.getAttribute("userid"));
		if (session.getAttribute("userid") != null) {
			// login y
			logger.debug("login y ");
			Bbs bbs = bbsDao.findById(num);

			String bbsUserid = bbs.getUserid();
			String sessionUserid = session.getAttribute("userid").toString();
			resultBoolean = bbsUserid.equals(sessionUserid);

			// userid
			// String userid = session.getAttribute("userid").toString();
			// User user = userDao.findAllByProperty("userid", userid).get(0);

		} else {

			resultBoolean = false;

		}

		if (resultBoolean == false) {
			String message = "no own";
			throw new Exception(message);
		}

		return resultBoolean;
	}

	public boolean validRead(int bbsnum, HttpServletRequest request)
			throws Exception {

		boolean resultBoolean = false;
		String message = "";

		logger.debug("bbsnum validRead " + bbsnum);
		List configList = configDao.findAllByProperty("bbsnum", bbsnum);

		if (configList.size() <= 0) {
			message = "bbs is not exist";
			throw new Exception(message);
		}

		Config config = (Config) configList.get(0);
		int publicYn = config.getPublicYn();
		logger.debug("publicYn " + publicYn);

		if (publicYn == 0) {
			// no public
			message = "this bbs is close";
			resultBoolean = false;

		} else {
			// yes public

			// read avialble level
			int readFrom = config.getReadFrom();
			int readUntil = config.getReadUntil();

			// user level valid
			if (readFrom == 0 && readUntil == 0) {
				resultBoolean = true;
			} else {

				HttpSession session = request.getSession(false);
				// if (request.getSession(false) != null){
				logger.debug("login id " + session.getAttribute("userid"));
				if (session.getAttribute("userid") != null) {
					// login y
					logger.debug("login y ");

					// userid
					String userid = session.getAttribute("userid").toString();
					User user = userDao.findAllByProperty("userid", userid)
							.get(0);
					int level = user.getLevel();
					if (readFrom <= level && level <= readUntil) {
						resultBoolean = true;
					} else {
						// fail
						resultBoolean = false;
						message = "you have level problem";
					}

				} else {
					// login n
					logger.debug("login n ");
					resultBoolean = false;
					message = "you must login";
				}

			}

		}

		if (resultBoolean == false) {
			throw new Exception(message);
		}

		return resultBoolean;

	}

	public boolean validWrite(int bbsnum, HttpServletRequest request)
			throws Exception {

		boolean resultBoolean = false;
		String message = "";

		logger.debug("bbsnum " + bbsnum);
		Config config = configDao.findAllByProperty("bbsnum", bbsnum).get(0);
		int publicYn = config.getPublicYn();
		logger.debug("publicYn " + publicYn);

		if (publicYn == 0) {
			// no public
			message = "this bbs is close";
			resultBoolean = false;

		} else {
			// yes public
			HttpSession session = request.getSession(false);
			// if (request.getSession(false) != null){
			logger.debug("login id " + session.getAttribute("userid"));
			if (session.getAttribute("userid") != null) {
				// login y
				logger.debug("login y ");

				// read avialble level
				int writeFrom = config.getWriteFrom();
				int writeUntil = config.getWriteUntil();

				// userid
				String userid = session.getAttribute("userid").toString();
				User user = userDao.findAllByProperty("userid", userid).get(0);
				int level = user.getLevel();

				// user level valid
				if (writeFrom <= level && level <= writeUntil) {
					// success
					resultBoolean = true;
				} else if (writeFrom == 0 && writeUntil == 0) {
					// success
					resultBoolean = true;
				} else {
					// fail
					resultBoolean = false;
					message = "you have level problem";
				}
			} else {
				// login n
				logger.debug("login n ");
				resultBoolean = false;
				message = "you must login";
			}
		}

		if (resultBoolean == false) {
			throw new Exception(message);
		}

		return resultBoolean;

	}

}
