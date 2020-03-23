package com.fr.web.core;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.base.ConfigManager;
import com.fr.base.FRContext;
import com.fr.file.ClusterConfigManager;
import com.fr.general.Inter;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.LicUtils;
import com.fr.stable.StableUtils;
import com.fr.stable.StringUtils;
import com.fr.stable.web.SessionProvider;
import com.fr.stable.web.Weblet;
import com.fr.web.NoPrivilegeException;
import com.fr.web.WebletException;
import com.fr.web.core.A._D;
import com.fr.web.factory.WebletFactory;
import com.fr.web.utils.WebUtils;

public class SessionDealWith {
	public static Timer SESSION_DEAL_WITH_TIMER = null;
	private static boolean registed = false;
	private static Random sessionIDRandom = null;
	private static int MAXADDRESS_COUNT = 2;
	private static final long __INIT_TIME__ = System.currentTimeMillis();
	private static final Object SESSION_ID_MAP_LOCK;
	private static Map sessionIDMap;
	private static final Object ADDRESS_MANAGER_LOCK;
	private static ConcurrentHashMap addressManager;
	private static final int ONE = 1;
	private static final int TEN = 10;
	private static final int MILLISECOND_PER_HOUR = 3600000;
	private static final int HOUR_PER_DAY = 24;

	private static void authenticateLicense() {
		LicUtils.retryLicLock();
		byte[] arrayOfByte = StableUtils.getBytes();
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try {
			LicUtils.decode(arrayOfByte, localByteArrayOutputStream);
		} catch (Exception localException1) {
		}
		try {
			JSONObject localJSONObject = new JSONObject(new String(localByteArrayOutputStream.toByteArray(), "UTF-8"));
			if (localJSONObject.getLong("DEADLINE") > Calendar.getInstance().getTimeInMillis()) {
				registed = true;
				int i = 0;
				if (localJSONObject.has("CONCURRENCY"))
					i = localJSONObject.getInt("CONCURRENCY");
				MAXADDRESS_COUNT = i <= 0 ? 2147483647 : i;
			}
			System.out.println("Winky Test MAXADDRESS_COUNT: " + MAXADDRESS_COUNT);
		} catch (Exception localException2) {
		}
	}

	public static String getOrGenerateSessionIDWithCheckRegister(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String str = WebUtils.getHTTPRequestParameter(paramHttpServletRequest, "sessionID");
		if (str == null)
			str = generateSessionIDWithCheckRegister(paramHttpServletRequest, paramHttpServletResponse);
		return str;
	}

	public static String generateSessionIDWithCheckRegister(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		Weblet localWeblet;
		if (generateSessionID_isPromptRegisted(paramHttpServletRequest))
			localWeblet = WebletFactory.createEmbeddedWeblet("/com/fr/web/tpl/lic.frm");
		else
			try {
				localWeblet = WebletFactory.createWebletByRequest(paramHttpServletRequest, paramHttpServletResponse);
			} catch (NoPrivilegeException localNoPrivilegeException) {
				return null;
			}
		return generateSessionID(paramHttpServletRequest, paramHttpServletResponse, localWeblet);
	}

	public static String generateSessionID(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Weblet paramWeblet) throws Exception {
		if (paramWeblet == null)
			throw new WebletException("Need to specify parameter \"reportlet\" or \"resultlet\" or \"formllet\".");
		String str1 = WebUtils.getIpAddr(paramHttpServletRequest);
		Map localMap = WebUtils.parameters4SessionIDInforContainMPCache(paramHttpServletRequest);
		String str2 = WebUtils.getHTTPRequestParameter(paramHttpServletRequest, "op");
		if (((str2 == null) || (paramWeblet.isSessionOccupy())) && (generateSessionID_dealWithOverFlow(paramHttpServletRequest, paramHttpServletResponse, str1)))
			return null;
		SessionProvider localSessionProvider = null;
		if (paramWeblet != null)
			localSessionProvider = paramWeblet.createSessionIDInfor(paramHttpServletRequest, str1, localMap);
		return generateSessionID_addSessionIDInfor(localSessionProvider, str1, paramWeblet);
	}

	private static String generateSessionID_addSessionIDInfor(SessionProvider paramSessionProvider, String paramString, Weblet paramWeblet) throws Exception {
		String str = addSessionIDInfor(paramSessionProvider);
		if (paramWeblet.isSessionOccupy()) {
			Object localObject = new HashSet();
			Set localSet = (Set) addressManager.putIfAbsent(paramString, localObject);
			localObject = localSet == null ? localObject : localSet;
			((Set) localObject).add(str);
		}
		return str;
	}

	private static boolean generateSessionID_dealWithOverFlow(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString) throws Exception {
		if (isAddressOverFlow(paramString))
			try {
				if (WebUtils.getDevice(paramHttpServletRequest).isMobile())
					throw new Exception(Inter.getLocText("NS_register_ipFull"));
				FRContext.getLogger().error(Inter.getLocText("NS_register_ipFull"));
				System.out.println("Winky Test 打开并发数已满页面 addressManager.keySet(): " + addressManager.keySet());
				WebUtils.getPageWhenOverflow(paramHttpServletResponse, addressManager.keySet());
				return true;
			} catch (Exception localException) {
				throw new Exception(Inter.getLocText("EX-IP_Address_Overflow"));
			}
		return false;
	}

	private static boolean isAddressOverFlow(String paramString) {
		return (100 < addressManager.size()) && ((StringUtils.isBlank(paramString)) || (!addressManager.containsKey(paramString)));
	}

	public static boolean generateSessionID_isPromptRegisted(HttpServletRequest paramHttpServletRequest) throws Exception {
		if (generateSessionID_checkReg(paramHttpServletRequest))
			return false;
		int i = Math.random() * 10.0D <= (System.currentTimeMillis() - __INIT_TIME__) / 3600000L / 24L + 1L ? 1 : 0;
		if ((i != 0) && (WebUtils.getDevice(paramHttpServletRequest).isMobile()))
			throw new Exception(Inter.getLocText("Need_Purchase_Info"));
		return false;
	}

	private static boolean generateSessionID_checkReg(HttpServletRequest paramHttpServletRequest) {
		return (registed) || (isLocalHost(paramHttpServletRequest));
	}

	public static void writeRegistrationDiv(HttpServletRequest paramHttpServletRequest, PrintWriter paramPrintWriter) {
		if (!generateSessionID_checkReg(paramHttpServletRequest)) {
			// Tag localTag = new Tag("div");
			// localTag.css("left", "2px");
			// localTag.css("bottom", "36px");
			// localTag.css("font", "normal bolder 10pt Arial");
			// localTag.css("position", "absolute");
			//// localTag.sub(new TextHtml("<a
			// href='http://www.finereporthelp.com/help/9/11.html'
			// target='_blank'>" +
			// Inter.getLocText("Registration-Please_Purchase") + "</a>"));
			// localTag.writeHtml(paramPrintWriter);
		}
	}

	protected static String addSessionIDInfor(SessionProvider paramSessionProvider) throws Exception {
		String str = null;
		synchronized (SESSION_ID_MAP_LOCK) {
			if (paramSessionProvider != null) {
				for (str = randomSessionID(); sessionIDMap.containsKey(str); str = randomSessionID())
					;
				sessionIDMap.put(str, paramSessionProvider);
				Calculator.putThreadSavedNameSpace(SessionIDInfor.asNameSpace(str));
				paramSessionProvider.setSessionID(str);
			}
		}
		return str;
	}

	private static boolean isLocalHost(HttpServletRequest paramHttpServletRequest) {
		String str = WebUtils.getIpAddr(paramHttpServletRequest);
		return WebUtils.isLocalHost(str);
	}

	private static String randomSessionID() {
		if (sessionIDRandom == null)
			sessionIDRandom = new Random();
		String str = String.valueOf(sessionIDRandom.nextInt(100000));
		if (ClusterConfigManager.getInstance().isUseCluster())
			str = _D.E(str);
		return str;
	}

	public static void closeSession(Object paramObject) {
		FRContext.getLogger().info(Inter.getLocText("LOG-Close_Session") + ":" + paramObject);
		SessionIDInfor localSessionIDInfor = null;
		synchronized (SESSION_ID_MAP_LOCK) {
			localSessionIDInfor = (SessionIDInfor) sessionIDMap.remove(paramObject);
		}
		if (localSessionIDInfor != null) {
			localSessionIDInfor.release();
			String address = localSessionIDInfor.getRemoteAddress();
			synchronized (ADDRESS_MANAGER_LOCK) {
				Set localSet = (Set) addressManager.get(address);
				if (localSet != null)
					localSet.remove(paramObject);
				if ((localSet == null) || (localSet.isEmpty()))
					addressManager.remove(address);
			}
		}
	}

	public static void closeAllSession() {
		synchronized (SESSION_ID_MAP_LOCK) {
			HashSet localHashSet = new HashSet(sessionIDMap.keySet());
			Iterator localIterator = localHashSet.iterator();
			while (localIterator.hasNext()) {
				Object localObject1 = localIterator.next();
				closeSession(localObject1);
			}
		}
	}

	public static void writeSessionTimeout(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (WebUtils.getDevice(paramHttpServletRequest).isMobile()) {
			JSONObject localJSONObject = new JSONObject();
			localJSONObject.put("message", "session");
			localJSONObject.put("exception", "session is timeout");
			PrintWriter localPrintWriter = WebUtils.createPrintWriter(paramHttpServletResponse);
			localPrintWriter.println(localJSONObject);
			localPrintWriter.flush();
			localPrintWriter.close();
		} else {
			WebUtils.dealWithTemplate("/com/fr/web/core/session.html", paramHttpServletResponse);
		}
	}

	public static void responseSessionID(String paramString, HttpServletResponse paramHttpServletResponse) throws Exception {
		if ((paramString != null) && (!hasSessionID(paramString)))
			paramString = null;
		if (paramString != null) {
			PrintWriter localPrintWriter = WebUtils.createPrintWriter(paramHttpServletResponse);
			localPrintWriter.write(paramString);
			localPrintWriter.flush();
			localPrintWriter.close();
		}
	}

	public static void updateSessionID(String paramString) {
		if (StringUtils.isBlank(paramString))
			return;
		SessionIDInfor localSessionIDInfor = null;
		synchronized (SESSION_ID_MAP_LOCK) {
			localSessionIDInfor = (SessionIDInfor) sessionIDMap.get(paramString);
		}
		if (localSessionIDInfor != null)
			localSessionIDInfor.updateTime();
	}

	public static SessionIDInfor getSessionIDInfor(String paramString) {
		if (StringUtils.isBlank(paramString))
			return null;
		synchronized (SESSION_ID_MAP_LOCK) {
			return (SessionIDInfor) sessionIDMap.get(paramString);
		}
	}

	public static boolean hasSessionID(String paramString) {
		synchronized (SESSION_ID_MAP_LOCK) {
			return sessionIDMap.containsKey(paramString);
		}
	}

	public static void viewSessions(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		JSONArray localJSONArray = new JSONArray();
		synchronized (SESSION_ID_MAP_LOCK) {
			Iterator localIterator = sessionIDMap.values().iterator();
			while (localIterator.hasNext()) {
				SessionIDInfor localSessionIDInfor = (SessionIDInfor) localIterator.next();
				localJSONArray.put(new JSONObject().put("startTime", new Date(localSessionIDInfor.getStartTime()).toLocaleString()).put("name", localSessionIDInfor.getWebTitle()).put("ip", localSessionIDInfor.getRemoteAddress()).put("user", localSessionIDInfor.getParameterValue("fr_username")));
			}
		}
		PrintWriter pw = WebUtils.createPrintWriter(paramHttpServletResponse);
		pw.print(localJSONArray);
		pw.flush();
		pw.close();
	}

	public static List<SessionIDInfor> getAllSession() {
		ArrayList localArrayList = new ArrayList();
		synchronized (SESSION_ID_MAP_LOCK) {
			Iterator localIterator = sessionIDMap.values().iterator();
			while (localIterator.hasNext()) {
				SessionIDInfor localSessionIDInfor = (SessionIDInfor) localIterator.next();
				localArrayList.add(localSessionIDInfor);
			}
		}
		return localArrayList;
	}

	public static List getVisitInforFromSession() throws JSONException {
		ArrayList localArrayList = new ArrayList();
		synchronized (SESSION_ID_MAP_LOCK) {
			Iterator localIterator = sessionIDMap.values().iterator();
			while (localIterator.hasNext()) {
				SessionIDInfor localSessionIDInfor = (SessionIDInfor) localIterator.next();
				localArrayList.add(new JSONObject().put("startTime", new Date(localSessionIDInfor.getStartTime()).toLocaleString()).put("name", localSessionIDInfor.getWebTitle()).put("ip", localSessionIDInfor.getRemoteAddress()).put("user", localSessionIDInfor.getParameterValue("fr_username") == null ? Inter.getLocText(new String[] { "Not_Logged_In", "User" }) : localSessionIDInfor.getParameterValue("fr_username")));
			}
		}
		return localArrayList;
	}

	private static void removeTimeoutSessions() {
		synchronized (SESSION_ID_MAP_LOCK) {
			if (sessionIDMap == null)
				return;
			SessionIDInfor[] arrayOfSessionIDInfor = (SessionIDInfor[]) sessionIDMap.values().toArray(new SessionIDInfor[0]);
			for (int i = 0; i < arrayOfSessionIDInfor.length; i++) {
				SessionIDInfor localSessionIDInfor = arrayOfSessionIDInfor[i];
				if (!localSessionIDInfor.isTimeout())
					continue;
				SessionDealWith.closeSession(localSessionIDInfor.getSessionID());
			}
		}
	}

	public static void setSessionIDRandom(Random paramRandom) {
		sessionIDRandom = paramRandom;
	}

	static {
		authenticateLicense();
		SESSION_DEAL_WITH_TIMER = new Timer();
		SESSION_DEAL_WITH_TIMER.schedule(new TimerTask() {
			@Override
			public void run() {
				// if (ConfigManager.getInstance().isLicUseLock())
				// SessionDealWith.access$000();
				// SessionDealWith.access$100();
			}
		}, ConfigManager.getInstance().getSessionDeadCheckTime(), ConfigManager.getInstance().getSessionDeadCheckTime());
		SESSION_ID_MAP_LOCK = new Object();
		sessionIDMap = new Hashtable();
		ADDRESS_MANAGER_LOCK = new Object();
		addressManager = new ConcurrentHashMap();
	}
}

/*
 * Location: C:\Users\Administrator\Desktop\fr-server-7.1.jar Qualified Name:
 * com.fr.web.core.SessionDealWith JD-Core Version: 0.6.0
 */