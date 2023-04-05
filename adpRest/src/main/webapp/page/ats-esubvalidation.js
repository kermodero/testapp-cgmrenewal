import { adp } from '../js/data-service.js'

export var atsESubValidation = {
	name: "eSubValidation",
	created: function() {
		this.$root.progress = 'IDLE';
	},
	data: function(){
	    return {
	    	menuVisible: false,
			outputURL: "",
			showDlg: false,
			timestamp: "unset",
			progress: 10,
			activated: false,
			isValidating:false
	    }
	},
	methods: {
	    validate : function () {
	    	this.isValidating = true;
	    	
			this.$root.progress = 'EXECUTING';
			//ensure timestamp is not empty, else REST call has incorrect # of parameters.
			this.timestamp = (this.timestamp == null || this.timestamp.length == 0) ? '__' : this.timestamp;
			adp.run(['validateEClaims', this.timestamp], (data) => {
				console.log('validating eclaims', data);
				this.$root.progress = 'COMPLETE';
				this.isValidating = false;
			});
			
		},				
		withdrawClaims(){	
			this.$root.progress = 'WITHDRAWING CLAIMS';
			adp.run(['withdrawClaims', 'Validation'], (data) => {
				this.$root.progress = 'COMPLETE';
			});	
		},
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
		activateProgress: function(step){
			this.activated = (step == 'adamSteps'); //Only run if this is the "Execute Batch Download" step.
		}
	},
	
	watch: {
		isValidating: function (val){
			console.log(val);
		}
	},
	
	template: `
<article>


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
	
		  <md-step class="manualStep" id="adamSteps" md-label="(manual step) Execute Batch Download" md-description="Execute Batch Download in ADAM">
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
		
	      <md-step id="fifth" md-label="Main validation" md-description="Main validation of eClaims" >
			<!-- div>
				<label>Timestamp: </label><input type="text" v-model="timestamp" />
			</div>
			<br/ -->
			<button id="validationTestButton" :disabled="isValidating" class="md-raised md-primary" v-on:click="validate" >Validate</button>		
	         <img src="img/refresh.gif" v-if="isValidating" alt="refresh" width="35em" height="50em" >
	
	      </md-step>
	
		</md-steppers> 
		
	  </div>

	</div>
</article>`

};
