import { adp } from '../js/data-service.js'

export var atsConfiguration = {
	name: "configuration",
	data: function() {
		return {
			appURLValue: "",
			menuVisible: false,
			remoteDirectory: "VendorClaimsDEV",
			testDataFolder: "\\results",
			sftpConn: "DEV",
			databaseUrl: "",
			databaseUser: "",
			sftpPort: "",
			SftpPortValue: "",
			sftpDir: "",
			SftpDirValue: "",
			sftpPassword: "",
			SftpPasswordValue: "",
			sftpUser: "",
			SftpUserValue: "",
			databasePassword: "",
			DatabaseUrlValue: "",
			DatabasePWValue: "",
			DatabaseUserValue: "",
			valueSftpConn: "",
			database: "DEV",
			valueDatabase: "",
			preset: "Current Settings",
			reportsFolder: "\\reports",
			defaultSettings: {
				DEV: ""
			},
			settings: Object,
			dbConnectionStatus: true,
			sftpConnectionStatus: true,
			connectionStatusUnknown: true,
			connectionStatusRefresh: false,
			browser: '',
		}


	},
	methods: {
		changePreset(event) {
			const options = event.target.options;
			let index = options[options.selectedIndex].index;
			this.database = this.defaultSettings[index].preset;
			this.sftpConn = this.defaultSettings[index].preset;
			this.appURLValue = this.defaultSettings[index].appURL;
		},
		connectionStatus() {
			fetch(adp.baseURL + '/' + "ConnectionStatus")
				.then(response => response.json())
				.then(data => {
					console.log(data.data);
				});
		},

		saveConfigurations: function() {
			//   event.preventDefault();
			adp.saveSettings(this.valueDatabase, this.DatabaseUrlValue,
				this.DatabaseUserValue, this.DatabasePWValue, this.valueSftpConn,
				this.SftpPortValue, this.SftpUserValue, this.SftpPasswordValue, this.SftpDirValue,
				this.testDataFolder, this.reportsFolder, this.appURLValue, this.appServer, this.browser);
			this.connectionStatusUnknown = false;
			this.connectionStatusRefresh = true;

			//this is a hack to sync the 2 async calls happening here. adp.saveSettings needs to finish before next REST call for proper results
			var start = new Date().getTime();
			for (var i = 0; i < 1e7; i++) {
				if ((new Date().getTime() - start) > 1000) {
					break;
				}
			}

			fetch(adp.baseURL + '/' + "ConnectionStatus")
				.then(response => response.json())
				.then(data => {
					console.log(data.data);
					this.sftpConnectionStatus = data.data.sftp;
					this.dbConnectionStatus = data.data.db;
					this.connectionStatusUnknown = false;
					this.connectionStatusRefresh = false;

				});

		},

		getEviromentVariables: function() {
			let path = "DefaultSettings";
			let obj = [];

			fetch(adp.baseURL + '/' + path)
				.then(response => response.json())
				.then(data => {
					return data;
				}).then(data => {
					console.log('data------', data);
					//hack to put current settings to top of list
					for (const [key, value] of Object.entries(data.data)) {
						if (key == "Current Settings") {
							value.preset = key;
							obj.push(value);
						}
					}


					for (const [key, value] of Object.entries(data.data)) {
						if (key != "Current Settings") {
							value.preset = key;
							obj.push(value);
						}
					}
				});


			this.defaultSettings = obj;

		},

	},
	watch: {
		defaultSettings: function(val) {
			if (val[0]) {
				//console.log(val[0]);
				this.preset = val[0].preset;
				this.sftpConn = val[0].preset;
				this.database = val[0].preset;
				this.reportsFolder = val[0].outputFolderPath;
				this.testDataFolder = val[0].inputFolderPath;
				this.appURLValue = val[0].appURL;
				this.browser = val[0].browser;
			}
		},
		settings: function(val) {
			this.reportsFolder = val.report_FOLDER;
			this.testDataFolder = val.result_FOLDER;
		},
		
		database: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.valueDatabase = setting.database;
				this.databasePassword = setting.preset;
				this.databaseUrl = setting.preset;
				this.databaseUser = setting.preset;
			}
		},
		databasePassword: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.DatabasePWValue = setting.databasePassword;
			}
		},
		databaseUrl: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.DatabaseUrlValue = setting.databaseUrl;
			}
		},
		databaseUser: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.DatabaseUserValue = setting.databaseUser;
			}
		},

		sftpConn: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.sftpPort = setting.preset;
				this.sftpUser = setting.preset;
				this.sftpPassword = setting.preset;
				this.sftpDir = setting.preset;
				this.valueSftpConn = setting.sftphost;
			}

		},
		sftpPort: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.SftpPortValue = setting.sftpport;
			}
		},
		sftpUser: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.SftpUserValue = setting.sftpuser;
			}
		},
		sftpPassword: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.SftpPasswordValue = setting.sftppasword;
			}
		},
		sftpDir: function(val) {
			let setting = this.defaultSettings.find(s => s.preset === val);
			if (setting) {
				this.SftpDirValue = setting.sftpdir;
			}
		},
		
		browser: function(val) {
			this.browser = val;
		}

	},
	created() {
		this.getEviromentVariables();
	},

	template: `
<article id="configuration">
    <div class="connectionSettings" style="border-style:solid;font-weight:bold">
        <p>&nbsp &nbsp Databse Connection: &nbsp
            <img src="img/failed.png"
                v-if="!connectionStatusUnknown && !connectionStatusRefresh  && !this.dbConnectionStatus" alt="refresh"
                width="23em" height="43em">
            <img src="img/success.png"
                v-if="!connectionStatusUnknown && !connectionStatusRefresh  && this.dbConnectionStatus" alt="refresh"
                width="23em" height="43em">
            <img src="img/unknown.png" v-if="connectionStatusUnknown" alt="refresh" width="30em" height="43em">
            <img src="img/refresh.gif"
                v-if="!connectionStatusUnknown &&  connectionStatusRefresh && connectionStatusRefresh" alt="refresh"
                width="35em" height="50em">
            &nbsp &nbsp &nbsp Sftp Connection: &nbsp
            <img src="img/failed.png"
                v-if="!connectionStatusUnknown && !connectionStatusRefresh && !this.sftpConnectionStatus" alt="refresh"
                width="23em" height="43em">
            <img src="img/success.png"
                v-if="!connectionStatusUnknown && !connectionStatusRefresh &&  this.sftpConnectionStatus" alt="refresh"
                width="23em" height="43em">
            <img src="img/unknown.png" v-if="connectionStatusUnknown" alt="refresh" width="30em" height="43em">
            <img src="img/refresh.gif" v-if="!connectionStatusUnknown &&  connectionStatusRefresh" alt="refresh"
                width="35em" height="50em">
        </p>
    </div>

    <div>
        <h2>Configuration</h2>
    </div>
    <form>
        <article>
            <div v-if="this.defaultSettings[0]">
                <label> Presets: </label>
                <select id="preset" v-model="preset" @change="changePreset">
                    <option v-for="item in this.defaultSettings">{{item.preset}}</option>
                </select>
            </div>
			<section>
			<div v-if="this.defaultSettings[0]">
				<div>
					<label>App URL: </label>
                    <input v-model="appURLValue" style="width:375px"/>
				</div>
			</div>
		   </section>
           <br/>
            <section>
                <div v-if="this.defaultSettings[0]" style="border: 1px solid">
                    <label> Database(DB): </label>
                    <select v-model="database" id="databaseSelect2">
                        <option v-for="item in this.defaultSettings" v-bind:value="item.preset">{{item.database}}</option>
                    </select>
                    <input v-model="valueDatabase" />
                    <div>
                        <label> DB URL: </label>
                        <select v-model="databaseUrl" id="databaseURLSelect">
                            <option v-for="item in this.defaultSettings" v-bind:value="item.preset"> {{item.database}} </option>
                        </select>
                        <input v-model="DatabaseUrlValue" style="width:400px"/>
                    </div>
                    <div>
                        <label> DB User: </label>
                        <select v-model="databaseUser" id="databaseUserSelect">
                            <option v-for="item in this.defaultSettings" v-bind:value="item.preset"> {{item.database}} </option>
                        </select>
                        <input v-model="DatabaseUserValue" />
                    </div>
                    <div>
                        <label> DB Password: </label>
                        <select v-model="databasePassword" id="databasePWSelect">
                            <option v-for="item in this.defaultSettings" v-bind:value="item.preset"> {{item.database}} </option>
                        </select>
                        <input type="text" v-model="DatabasePWValue" />
                    </div>
                </div>

                <div v-if="!this.defaultSettings[0]">
                    <label>database</label>
                    <select v-model="database" id="databaseSelect">
                        <option>ADP2</option>
                        <option>ADP3</option>
                        <option>DEV</option>
                    </select>
                </div>

            </section>
            <section>
                <div v-if="this.defaultSettings[0]" style="border: 1px solid">
                    <label>SFTP Connection:</label>
                    <select v-model="sftpConn" id="sftpConnSelect">
                        <option v-for="item in this.defaultSettings" v-bind:value="item.preset">{{item.sftphost}}</option>
                    </select>
                    <input v-model="valueSftpConn" />
                    <div>
                        <label>SFTP Port:</label>
                        <select v-model="sftpPort" id="sftpPortSelect">
                            <option v-for="item in this.defaultSettings" v-bind:value="item.preset">{{item.sftphost}}</option>
                        </select>
                        <input v-model="SftpPortValue" />
                    </div>
                    <div>
                        <label>SFTP User:</label>
                        <select v-model="sftpUser" id="sftpUserSelect">
                            <option v-for="item in this.defaultSettings" v-bind:value="item.preset">{{item.sftphost}}</option>
                        </select>
                        <input v-model="SftpUserValue" />
                    </div>
                    <div>
                        <label>SFTP Password:</label>
                        <select v-model="sftpPassword" id="sftpPasswordSelect">
                            <option v-for="item in this.defaultSettings" v-bind:value="item.preset">{{item.sftphost}}</option>
                        </select>
                        <input type="text" v-model="SftpPasswordValue" />
                    </div>
                    <div>
                        <label>SFTP Dir:</label>
                        <select v-model="sftpDir" id="sftpDirSelect">
                            <option v-for="item in this.defaultSettings" v-bind:value="item.preset">{{item.sftphost}}</option>
                        </select>
                        <input v-model="SftpDirValue" />
                    </div>
                </div>
            </section>
            <section>
                <div>
                    <label>Test Data Folder: </label>
                    <input v-model="testDataFolder" id="testDataFolderInput" type="input" value="C:\Temp\testData" />
                </div>
            </section>
            <section>
                <div>
                    <label>Reports Folder: </label>
                    <input v-model="reportsFolder" id="reportsFolderInput" type="input" value="C:\Temp\reports" />
                </div>
            </section>
            <section>
                <div>
                	<label>Browser:</label>
                    <select v-model="browser" id="browserSelect">
                        <option value='CHROME'>Chrome</option>
                        <option value='EDGE'>Edge</option>
                        <option value='IE'>IE</option>
                    </select>
                </div>
            </section>
            <br />
            <section>
                <button id="saveSettings" type="button" v-on:click="saveConfigurations()">Save Configurations</button>
            </section>
        </article>
    </form>
</article>
`

};