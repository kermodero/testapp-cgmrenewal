export var adp = adp || {};

adp.baseURL = "http://localhost:9081/adpt/rs/adam";

adp.pageTitle= "ADP Tests";

adp.run = function(args, callback){
    var url = adp.baseURL;
    args.forEach( e => {
		url += "/" + e;
    });
    console.log(url);
    fetch(url)
	.then(response => response.json())
	.then(data => {
	    callback(data);
	});
};

adp.run2 = function(args){
    var url = adp.baseURL;
    args.forEach( e => {
		url += "/" + e;
    });
   // console.log(url);
   data =  fetch(url)
	.then(response => response.json())
	.then(data => {
	   return data;
	});
	return data;
};

adp.runQuery = function(contextRoot, args, callback){
    var url = adp.baseURL;
	url += "/";
	url += contextRoot
	url += "?";
    for( let [k, v] of args) {
		url += k;
		url += '=';
		url += v;
    }
    //console.log('url', url);
    fetch(url)
	.then(response => response.json())
	.then(data => {
	    console.log(data);
	    callback(data);
	});
};

adp.uploadFiles = function(updateNames,contextRoot, files, callback){	
	var url = adp.baseURL;
	url += "/";
	url += contextRoot
	var formData = new FormData();
	const timestamp = parseInt(Date.now() / 1000);

	for (var i = 0; i < files.length; i++) {
		if (updateNames == 1) {
			//change file name

			let filename = files[i].name.split("-");
			let realName = filename[0] + "-" +
				filename[1] + "-" +
				filename[2] + "-" +
				filename[3] + "-" +
				filename[4] + "-" +
				timestamp + "." +
				filename[5].split(".")[1];


			formData.append("files", files[i], realName);
		} else {
			//leave file name as is
			formData.append("files", files[i], files[i].name);
		}

	}
	//console.log('url', url);
	fetch(url, {
		method: 'POST',
		body: formData
	})
		.then(response => response.json())
		.then(data => {
			if (updateNames == 1) {
				data.timeStampUsed = timestamp;
			} else {
				data.timeStampUsed = 0;
			}

			//console.log(data);
			callback(data);
		});

};

adp.uploadFilenames = function(contextRoot, files, callback){	
    var url = adp.baseURL;
	url += "/";
	url += contextRoot
    console.log('url', url);
    fetch(url, {
		method: 'POST',
		body: files
	})
	.then(response => response.json())
	.then(data => {
	    console.log(data);
	    callback(data);
	});

};

adp.saveSettings = function(databaseV,databaseURL,databaseUser,databasePassword, sftpConnectionV,sftpPort,sftpUser,sftpPassword,sftpDir, testDataFolderV, reportsFolderV, appURL, currentServer, browser){
      
     let ConfigurationSettings ={};
     ConfigurationSettings.database = databaseV;
     ConfigurationSettings.sftpConnection = sftpConnectionV;
     ConfigurationSettings.remoteDirectory = sftpDir;
     ConfigurationSettings.testDataFolder= testDataFolderV;
     ConfigurationSettings.reportsFolder = reportsFolderV;
     ConfigurationSettings.databaseURL = databaseURL;
     ConfigurationSettings.databaseUser = databaseUser;
	 ConfigurationSettings.databasePassword = databasePassword;
	 ConfigurationSettings.sftpPort = sftpPort;
	 ConfigurationSettings.sftpUser = sftpUser;
	 ConfigurationSettings.sftpPassword = sftpPassword;
	 ConfigurationSettings.appURL = appURL;
	 ConfigurationSettings.currentServer = currentServer;
     ConfigurationSettings.browser = browser;
       
     
   // console.log(ConfigurationSettings);	
    var url = adp.baseURL;
	url += "/";
	url += "configuration";
    console.log('url', url);
    fetch(url, {
		method: 'POST',
		headers:{
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
		//ConfigurationSettings : {
			database : databaseV,
    		sftpConnection: sftpConnectionV,
     		remoteDirectory : sftpDir,
     		testDataFolder: testDataFolderV,
    		reportsFolder : reportsFolderV,
    		databaseURL : databaseURL,
    		databaseUser : databaseUser,
	        databasePassword : databasePassword,
	        sftpPort : sftpPort,
	        sftpUser : sftpUser,
	        sftpPassword : sftpPassword,
	        appURL : appURL,
			appServer : currentServer,
			browser: browser,
		//}
		})
	})
	.then(response => response.json())
	.then(data => {
	    console.log(data);
	    //callback(data);
	});
     
  
};

adp.phase1 = function(deviceTypesParm){
	  adp.runQuery("generate-xml", deviceTypesParm, function(data){
	     alert(data.data);
		});
};

adp.killThread = function(){
    adp.run(["kill", adp.processUUID], function(data){
		console.log("result? " + data);
    });
};

adp.regression = function(day, scriptName, callback){
    adp.run(["regression", day, scriptName], function(data){
		adp.processUUID = data.data;
		callback(data.data);
    });
}

adp.getAll = function(level, parentId, callback, failure){
	console.log("data?? ", level, parentId);
    adp.run(["data", level, parentId], function(data) {
		console.log("data? ", data);
		callback(data);
    });
}


/**
 * NOTE: NOT IN USE (JUN 07 21). Originally the log was designed to use a 
 * web socket. The connection was failing however,
 * so the design was downgraded to the polling mechanism defined 
 * in showOutput and showLog
 *
 * Populate console screen with ongoing output.
 * On server, a websocket method is used to 
 * keep a socket open and to pump out log messages.
 * Here a reader receives the messages as they come in.
 * Note recursion at end of the method, to keep the 
 * thread going.
 */
adp.showOutput1 = function() {
	if (adp.logReader != null) {
		console.log("re-initializing logging.");
		adp.logReader.cancel();
	}
	var td = new TextDecoder(); 
	//For message format, see moh.adp.test.common.log.UILog
	//Application returns an array of messages.
	fetch(adp.baseURL + '/data/')
	.then(response => {
		const reader = response.body.getReader();
		adp.logReader = reader;
		return reader.read().then(function readChunk({ done, value }) {
  			if (done) {
				console.log("logging complete.");
      			return;
  			}
			var msg = td.decode(value).trim();
			var msgs = JSON.parse(msg);			
			msgs.forEach ( (logMsg) => {
				console.log("message", logMsg.message)
				var message = "";
				switch (logMsg.significance) {
					case 'I':
						message = "<div>" + logMsg.message + "<div>";
						break;
					case 'W':
						message = "<div class=\"warn\">" + logMsg.message + "<div>";
						break;
					case 'C':
						message = "<div class=\"complete\">" + logMsg.message + "<div>";
						break;
					case 'E':
						message = "<div class=\"error\">" + logMsg.message + "<div>";
						break;
					default:
						return;
				}
				var log = document.querySelector("#log");
				var entry = document.createElement("template");
				entry.innerHTML = message;
				log.appendChild(entry.content);
				log.scrollTop = log.scrollHeight - log.clientHeight; //scroll to bottom.
			});
			return reader.read().then(readChunk); //recur
		});
	});
};

adp.logMessage = function(data){
	data.forEach ((datum) => {
		var logMsg = JSON.parse(datum);
		var cssClass = ""; //none by default
		switch (logMsg.significance) {
			case 'I':
				cssClass = "";
				break;
			case 'W':
				cssClass = "warn";
				break;
			case 'C':
				cssClass = "complete";
				break;
			case 'E':
				cssClass = "error";
				break;
			case 'H': //for 'highlight'
				cssClass = "highlight";
				break;				
			default:
				return;
		}
	    var message = "<div class=\"" + cssClass + "\">" + logMsg.message + "<div>";
		var log = document.querySelector("#log");
		var entry = document.createElement("template");
		entry.innerHTML = message;
		log.appendChild(entry.content);
		log.scrollTop = log.scrollHeight - log.clientHeight; //scroll to bottom.	
	});
};

/**
 * Logging loop.
 * This is kicked-off on creation of principal page.
 * SHOULD NOT be invoked elsewhere.
 * */
adp.showLog = function() {
	//// Return a Promise that resolves after "ms" milliseconds
/*	const wait = ms => new Promise(resolve => setTimeout(function() { resolve() }.bind(this), ms));
	(async () => {
		while (true) {
			const res = await fetch(adp.baseURL + '/pollLog');
			const blocks = await res.json();
			adp.logMessage(blocks);
			await wait(1000);
		}
	})(); */
};


