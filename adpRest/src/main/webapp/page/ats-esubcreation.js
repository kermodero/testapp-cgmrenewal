import { adp } from '../js/data-service.js'

export var atsESubCreation = {
	name: "eSubCreation",
	created: function() {
		this.$root.progress = 'IDLE';
	},
	data: function(){
	    return {
	    	menuVisible: false,
			outputURL: "",
			showDlg: false,
			timestamp: "unset",
			isValidating: false,
			updateTimestamp:0,
			lastTimestampUsed:0,
			activated: false,
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
		withdrawClaims(){	
		this.$root.progress = 'WITHDRAWING CLAIMS';
			adp.run(['withdrawClaims', 'Creation'], (data) => {
			    //console.log('result:', data);
				this.$root.progress = 'COMPLETE';
			});	
		},
		deleteInvoices() {
			console.log(this);
			console.log(this.$root);
			this.$root.progress = 'Deleting Invoices';
			adp.run(['deleteInvoices'], (data) => {
			    //console.log('result:', data);
				this.$root.progress = 'COMPLETE';
			});	
		},
		genXML : function() {
			var files = document.getElementById("xlsFilesExisting").files;
			var fileArray = [];
			for (var i = 0; i < files.length; i++) {
				fileArray.push(files[i]);
			};
			this.$root.progress = 'EXECUTING';
			
			adp.uploadFiles(this.updateTimestamp,'exisiting-xml', fileArray, (result) => {
				this.lastTimestampUsed = result.timeStampUsed;
				//console.log('this.lastTimestampUsed ', this.lastTimestampUsed);
				//console.log('monitor uploads', result);
				this.$emit('submitted', result.data);
				this.$root.progress = 'COMPLETE';
			});
		},
		
		startTest(){
		this.$root.progress = 'TEST RUNNING';
		this.isValidating = true;
			adp.run(['startTests'], (data) => {
				//console.log('result:', data);
				this.$root.progress = 'COMPLETE';
				this.isValidating = false;
			});
		},
		
		activateProgress: function(step){
			//console.log(step);
			this.activated = (step == 'fourth'); //Only run if this is the "Execute Batch Download" step.
		},
		activateExistingProgress: function(step){
			//console.log(step);
			this.activated = (step == 'fourthExisting'); //Only run if this is the "Execute Batch Download" step.
		},
	},
	created(){
		this.$root.showConsoleLog = true;
	},
	template: `
<article>
 <md-tabs >
      <md-tab id="tab-generate" md-label="Generate"  >
           <div class="md-layout">
      <div class="md-layout-item">
		<md-steppers md-vertical v-on:md-changed="activateProgress">
		
		  <md-step id="first" md-label="Generate XML" md-description="Generate XML from excel files.">
			<ats-generate-xml @submitted="xmlSubmitted" ></ats-generate-xml>
		  </md-step>
	
		  <md-step id="second" md-label="SFTP XML" md-description="SFTP XML to server">
			<button class="md-raised md-primary" v-on:click="sftpTransfer">SFTP eClaim files</button>
	      </md-step>
	
		  <md-step id="third" md-label="(Optional) Withdraw existing claims" md-description="(Optional) Withdraw existing claims">
			 <button class="md-raised md-primary" v-on:click="withdrawClaims">Withdraw</button>
		  </md-step>

		  <md-step id="third_a" md-label="(Optional) Delete invoices" md-description="(Optional) Delete invoices">
			 <button class="md-raised md-primary" v-on:click="deleteInvoices">Delete</button>
		  </md-step>
				 
		 <md-step class="manualStep" id="fourth" md-label="(manual step) Execute Batch Download" md-description="Execute Batch Download in ADAM">
			<i>Please log in to ADAM as Administrator and execute</i> 
				<div class="indent">				
					batch <i>Download eClaim files</i>, 
				</div>	
				<i>then </i>
				<div class="indent">
					batch <i>Process eClaim files</i>.
				</div>
				<ats-show-progress v-bind:activated="activated" v-bind:timestamp="timestamp"  />																
	      </md-step>
		
	      <md-step id="fith" md-label="Test Creation" md-description="Creation Tests of eClaims">
			<div>
				<label>Timestamp: </label><input type="text" v-model="timestamp" />
			</div>
			<br/>
			<button :disabled="isValidating" class="md-raised md-primary" v-on:click="startTest" >Test</button>	
			<img src="img/refresh.gif" v-if="isValidating" alt="refresh" width="35em" height="50em" >	
	      </md-step>
	
		</md-steppers> 
		
	  </div>

	</div>
      </md-tab>

  <md-tab id="tab-existing" md-label="Existing">
           <div class="md-layout">
      <div class="md-layout-item">
		<md-steppers md-vertical v-on:md-changed="activateExistingProgress">
		
		  <md-step id="firstExisting" md-label="Use Existing XML" md-description="Use existing XML from excel files.">
			<section>
				<div>  
				  <label>Device Category</label>
				</div>
			
				<form id="filesFormExisting" class="field">
				  <input type="file" id="xlsFilesExisting" multiple />
					</form>
				<div>
					<md-checkbox v-model="updateTimestamp" value="1">Update Timestamps</md-checkbox>
		        </div>
		        <div v-if="lastTimestampUsed != 0">
		     	   <p>Last used timestamp: {{lastTimestampUsed}} </p>
				</div>
				<button class="md-raised md-primary" v-on:click="genXML" >Use Selected XML eSubmission claim files</button>		

			  </section>	
		  </md-step>
	
		  <md-step id="secondExisting" md-label="SFTP XML" md-description="SFTP XML to server">
			<button class="md-raised md-primary" v-on:click="sftpTransfer">SFTP eClaim files</button>
	      </md-step>
	
		  <md-step id="thirdExisting" md-label="(Optional) Withdraw existing claims" md-description="(Optional) Withdraw existing claims">
			 <button class="md-raised md-primary" v-on:click="withdrawClaims">Withdraw</button>
		  </md-step>

		  <md-step id="thirdExisting_a" md-label="(Optional) Delete invoices" md-description="(Optional) Delete invoices">
			 <button class="md-raised md-primary" v-on:click="deleteInvoices">Delete</button>
		  </md-step>
				 
		 <md-step class="manualStep" id="fourthExisting" md-label="(manual step) Execute Batch Download" md-description="Execute Batch Download in ADAM">
			<i>Please log in to ADAM as Administrator and execute</i> 
				<div class="indent">				
					batch <i>Download eClaim files</i>, 
				</div>	
				<i>then </i>
				<div class="indent">
					batch <i>Process eClaim files</i>.
				</div>
				<ats-show-progress v-bind:activated="activated" v-bind:lastTimestampUsed="lastTimestampUsed"  />
	      </md-step>
		
	      <md-step id="fithExisting" md-label="Test Creation" md-description="Creation Tests of eClaims">
			<br/>
			<button :disabled="isValidating" class="md-raised md-primary" v-on:click="startTest" >Test</button>	
			<img src="img/refresh.gif" v-if="isValidating" alt="refresh" width="35em" height="50em" >
		
	      </md-step>
	
		</md-steppers> 
		
	  </div>

	</div>
      </md-tab>
</md-tabs>



 
</article>`

};