window.onbeforeunload = function(e) {
	app.obrExit();
};

app = {
	ModeScrinStop : false,
	session : "",
	lastPack : 0,
	curPack : 0,

	// инициализация
	init : function() {
		app.ModeScrinStop = false;
		webix.ui({
			id : "app",
			rows : [ {}, {
				cols : [ {}, {
					view : "text",
					height : 50,
					width : 200,
					value : "(с) WebRemoteControl v0.18"
				}, {} ]
			}, {} ]
		});
		setInterval(app.tick, 1500);
		//setInterval(app.tick, 300);
	},

	// цикличный запрос обновления
	tick : function() {
		app.servReq("ReqQv", [ app.session, app.curPack ]);
	},

	// определим сессию
	setSession : function() {
		if (app.session != "") {
			return;
		}
		var urlVar = window.location.search;
		var arrayVar = [];
		var valueAndKey = [];
		var resultArray = [];
		arrayVar = (urlVar.substr(1)).split('&');
		for (i = 0; i < arrayVar.length; i++) {
			valueAndKey = arrayVar[i].split('=');
			if (valueAndKey[0] == "session") {
				app.session = valueAndKey[1];
			}
		}
	},

	// обработка выхода со страници, обновления
	obrExit : function() {
		app.ModeScrinStop = true;
		app.setSession();
		if (app.session == "") {
			return;
		}
		if (app.lastPack == 0) {
			return;
		}

		app.curPack = 1 + app.curPack;
		if (app.curPack > 9999) {
			app.curPack = 1
		}

		var req = {
			method : "ScrinStop",
			params : [ app.session ],
			id : 1,
			session : app.session,
			last : app.lastPack,
			current : app.curPack
		};

		webix.ajax().sync().headers({
			"Content-type" : "application/json"
		}).post("./data/data", JSON.stringify(req));

		app.lastPack = 0;
		app.curPack = 0;
	},

	// посылаем запрос серверу
	servReq : function(method, params) {
		app.setSession();
		if (app.session != "") {
			if (app.lastPack == 0) {
				app.servReq1("RefWidget", [ "Form", "app" ]);
			}
		}
		app.servReq1(method, params);
	},

	servReq1 : function(method, params) {
		// webix.message(req);
		// alert(req);
		// if (req=="") return;

		if (app.ModeScrinStop) {
			return;
		}
		app.setSession();
		app.curPack = 1 + app.curPack;
		if (app.curPack > 9999) {
			app.curPack = 1
		}

		var req = {
			method : method,
			params : params,
			id : 1,
			session : app.session,
			last : app.lastPack,
			current : app.curPack
		};
		app.lastPack = app.curPack;

		webix.ajax().headers({
			"Content-type" : "application/json"
		}).post("./data/data", JSON.stringify(req),

		function(resp) {
			if (app.ModeScrinStop) {
				app.lastPack = 0;
				app.curPack = 0;
				return;
			}
			app.sendResp(resp);
		});
	},

	// парсим ответ сервера
	sendResp : function(resp) {
		// если есть элемент интерфейса ajaxResponceText, то в нём показывает
		// ответ сервера
		var oForm = $$("ajaxResponceText");
		if (oForm != undefined) {
			oForm.setHTML("<div>" + resp + "</div>");
		}

		var oResp = null;
		var errResp = 0;
		var cmdResp = "";
		try {
			oResp = JSON.parse(resp);
			errResp = oResp.error;
			cmdResp = oResp.result.command;
		} catch (e) {
			webix.message(e);
			return;
		}

		if (errResp != 0) {
			webix.message("" + errResp + " / " + oResp.errormes);
			return;
		}

		if (cmdResp == "Redirect") {
			app.uiRedirect(oResp)
			return;
		}

		if (cmdResp == "RefWidget") {
			var idForm = "";
			try {
				cmdType = oResp.result.type;
				idForm = oResp.result.id;
				dataResp = oResp.result.data;
			} catch (e) {
				webix.message(e);
				return;
			}

			if (idForm == "") {
				return;
			}
			var oForm = $$(idForm);
			if (oForm == undefined) {
				return;
			}

			if (cmdType == "Form") {
				webix.ui(JSON.parse(dataResp), oForm);
				return;
			}
			if (cmdType == "Val") {
				oForm.Val(dataResp);
				return;
			}
			if (cmdType == "SetHTML") {
				oForm.setHTML(dataResp);
				return;
			}
		}

	},

	// команда сервера: редирект
	uiRedirect : function(oResp) {
		var dataResp = "";
		try {
			dataResp = oResp.result.data;
		} catch (e) {
			webix.message(e);
			return;
		}

		var loc = window.location;
		dataResp = loc.protocol + "//" + loc.host + dataResp;
		loc.replace(dataResp);
	},
};
