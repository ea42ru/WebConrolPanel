package ru.ea42.WebRemoteControl;

// �������� ����������� ����� ������������ ������

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
	public Map<String, AbstractSession> ls = new HashMap<String, AbstractSession>();
	public int MaxPack = 4; // ������������ ���������� ���������� �� ��� �������.
	public int MaxTime = 200000; // ����� ����� ������ � ��������.
	public Class<?> SessionClass = null;

	public AbstractSession getNewSession() {
		AbstractSession as = null;
		if (SessionClass == null) {
			as = (AbstractSession) new WarningSession();
		} else {
			try {
				as = (AbstractSession) SessionClass.newInstance();
			} catch (Exception e) {
				as = (AbstractSession) new WarningSession();
			}
		}

		// as = new Demo_Session();
		as.id = UUID.randomUUID().toString();
		as.inEnterMode = true;
		as.lastTime = new Date();
		ls.put(as.id, as);
		return as;
	}

	public AbstractSession getSession(String sSes, int sLast, int sCur) {
		AbstractSession ses = ls.get(sSes);
		if (gudSession(ses, sSes, sLast, sCur)) {
			ses.lastPack = sCur;
			ses.lastTime = new Date();
			return ses;
		}

		// ������� ������������ ������, ����� �� ������ ������� ������
		if (ses != null) {
			if (ses.id == "") {
				ls.remove(sSes);
			}
		}
		return null;
	}

	private boolean gudSession(AbstractSession ses, String sSes, int sLast, int sCur) {
		// �� ���������� ������ �� �������
		if (sSes.equals("")) {
			return false;
		}

		// �� ������� ��� ������ � ����� id
		if (ses == null) {
			return false;
		}

		// ������ ����������
		if ((new Date().getTime() - ses.lastTime.getTime()) > MaxTime) {
			ses.id = "";
			return false;
		}

		// ����� ������ ������ ������
		if (sLast == 0) {
			if (ses.inEnterMode) {
				ses.inEnterMode = false;
				return true;
			}
			return false;
		}

		// ��������� ����� ���������� ������ �� ������� � �������
		// � ������ ������������ ������ ������� MaxPack
		if (sLast >= ses.lastPack) {
			if (sLast <= (ses.lastPack + MaxPack)) {
				return true;
			}
		}

		// ���������� ������� ����� 0 ��� >9999
		if (sLast >= 1) {
			if (sLast <= (ses.lastPack - 10000 + MaxPack)) {
				return true;
			}
		}

		return false;

	}
}
