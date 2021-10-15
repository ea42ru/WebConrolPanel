package ru.ea42.WebRemoteControl;

// дописать циклический поиск просроченных сессий

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
	public Map<String, AbstractSession> ls = new HashMap<String, AbstractSession>();
	public int MaxPack = 4; // максимальное количество потерянных за раз пакетов.
	public int MaxTime = 200000; // время жизни сессии в миллисек.
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

		// удаляем просроченные сессии, чтобы их сожрал сборщик мусора
		if (ses != null) {
			if (ses.id == "") {
				ls.remove(sSes);
			}
		}
		return null;
	}

	private boolean gudSession(AbstractSession ses, String sSes, int sLast, int sCur) {
		// не обозначена сессия на клиенте
		if (sSes.equals("")) {
			return false;
		}

		// на сервере нет сессии с таким id
		if (ses == null) {
			return false;
		}

		// сессия просрочена
		if ((new Date().getTime() - ses.lastTime.getTime()) > MaxTime) {
			ses.id = "";
			return false;
		}

		// режим начала работы сессии
		if (sLast == 0) {
			if (ses.inEnterMode) {
				ses.inEnterMode = false;
				return true;
			}
			return false;
		}

		// совпадает номер последнего пакета на клиенте и сервере
		// с учетом максимальных потерь пакетов MaxPack
		if (sLast >= ses.lastPack) {
			if (sLast <= (ses.lastPack + MaxPack)) {
				return true;
			}
		}

		// допроверим переход через 0 при >9999
		if (sLast >= 1) {
			if (sLast <= (ses.lastPack - 10000 + MaxPack)) {
				return true;
			}
		}

		return false;

	}
}
