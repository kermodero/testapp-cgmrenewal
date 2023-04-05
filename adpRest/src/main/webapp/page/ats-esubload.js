import { adp } from '../js/data-service.js'

export var atsESubLoad = {
	name: "eSubLoad",
	created: function() {
		this.$root.progress = 'IDLE';
	},
	data: function(){
	    return {
	    	menuVisible: false,
			outputURL: "",
			showDlg: false,
			timestamp: "unset",
			isValidating: false
	    }
	},
	methods: {		
		sftpTransfer : function () {
			this.$root.progress = 'TRANSFERRING';			
			adp.run(['sftpFiles'], (data) => {
				console.log('transferring files.', data);
				this.$root.progress = 'COMPLETE';
			});
		},
		xmlSubmitted: function(timestamp) {
			this.timestamp = timestamp;
		},
		genXML : function() {
			var files = document.getElementById("xlsFilesExisting-l").files;
			var fileArray = [];
			for (var i = 0; i < files.length; i++) {
				fileArray.push(files[i]);
			};
			this.$root.progress = 'EXECUTING';
			adp.uploadFiles(0,'exisiting-xml', fileArray, (result) => {
				console.log('monitor uploads', result);
				this.$emit('submitted', result.data);
				this.$root.progress = 'COMPLETE';
			});
		},
		loadTest(){
		this.$root.progress = 'LOAD TEST RUNNING';
		this.isValidating = true;
			adp.run(['loadTests'], (data) => {
				//console.log('result:', data);
				this.$root.progress = 'COMPLETE';
				this.isValidating = false;
			});
		}
	},
	created(){
		this.$root.showConsoleLog = true;
	},
	template: `
<article>

 <md-tabs >
      <md-tab id="tab-generate-l" md-label="Generate"  >
      
    <div class="md-layout">
      <div class="md-layout-item">
		<md-steppers md-vertical>
		
		  <md-step id="first-l" md-label="Generate XML" md-description="Generate XML from excel files.">
			<ats-generate-xml @submitted="xmlSubmitted" ></ats-generate-xml>
		  </md-step>
	
		  <md-step id="second-l" md-label="SFTP XML" md-description="SFTP XML to server">
			<button class="md-raised md-primary" v-on:click="sftpTransfer">SFTP eClaim files</button>
	      </md-step>
				 
		 <md-step class="manualStep-l" id="fourth" md-label="(manual step) Execute Batch Download" md-description="Execute Batch Download in ADAM">
			<i>Please log in to ADAM as Administrator and execute</i> 
				<div class="indent">				
					batch <i>Download eClaim files</i>, 
				</div>	
				<i>then </i>
				<div class="indent">
					batch <i>Process eClaim files</i>.
				</div>								
	      </md-step>
		
	      <md-step id="fouth-l" md-label="Load Tests" md-description="Load Test of eClaims">
			<div>
				<label>Timestamp: </label><input type="text" v-model="timestamp" />
			</div>
			<br/>
			<button class="md-raised md-primary" v-on:click="loadTest" >Test</button>		
	      </md-step>
	
		</md-steppers> 
		
	  </div>

	</div>
	
	</md-tab>
	<md-tab id="tab-existing-l" md-label="Existing">
           <div class="md-layout">
      <div class="md-layout-item">
		<md-steppers md-vertical>
		
		  <md-step id="firstExisting-l" md-label="Use Existing XML" md-description="Use existing XML from excel files.">
			<section>
				<div>  
				  <label>Device Category</label>
				</div>
			
				<form id="filesFormExisting-l" class="field">
				  <input type="file" id="xlsFilesExisting-l" multiple />
				</form>
				
				<button class="md-raised md-primary" v-on:click="genXML" >Use Selected XML eSubmission claim files</button>		
			
			  </section>	
		  </md-step>
	
		  <md-step id="secondExisting-l" md-label="SFTP XML" md-description="SFTP XML to server">
			<button class="md-raised md-primary" v-on:click="sftpTransfer">SFTP eClaim files</button>
	      </md-step>
				 
		 <md-step class="manualStep-l" id="fourthExisting" md-label="(manual step) Execute Batch Download" md-description="Execute Batch Download in ADAM">
			<i>Please log in to ADAM as Administrator and execute</i> 
				<div class="indent">				
					batch <i>Download eClaim files</i>, 
				</div>	
				<i>then </i>
				<div class="indent">
					batch <i>Process eClaim files</i>.
				</div>								
	      </md-step>
		
	      <md-step id="fouthExisting-l" md-label="Load Tests" md-description="Load Test of eClaims">
			<br/>
			<button :disabled="isValidating" class="md-raised md-primary" v-on:click="loadTest" >Test</button>	
			<img src="img/refresh.gif" v-if="isValidating" alt="refresh" width="35em" height="50em" >
		
	      </md-step>
	
		</md-steppers> 
		
	  </div>

	</div>
      </md-tab>
</article>`

};