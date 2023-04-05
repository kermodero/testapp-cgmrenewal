	import { adp } from '../js/data-service.js'

export var atsRegression = {
	name: "regression",
	data: function(){
		this.$emit('change-view', 'Regression');
	    return {
			active: "zeroeth",
	    	menuVisible: false,
			showDlg: false,
			outputURL: ""
	    }
	},
	methods: {
		executeScript : function(day, scriptName) {
			this.$root.progress = 'EXECUTING';
			//adp.showOutput(true);
			adp.regression(day, scriptName, this.showOutput);
		},
		showOutput: function(result) {
			console.log("result:", result);
			this.$root.progress = 'COMPLETE';
		//	adp.showOutput(false);
		}
	},
	template: `
<article>
  <md-tabs style="height: 100%">
    <md-tab id="day1" md-label="Day 1">

		<md-steppers md-vertical>
		
		  <md-step id="first" md-label="Set-up" md-description="Vendors, manufacturers, etc.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneSetup')">Run</button>
		  </md-step>
		  
		  <md-step id="second" md-label="Load paper claims" md-description="Load all paper claims">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOnePaperClaims')">Run</button>
		  </md-step>

		  <md-step class="manualStep" id="eclaim-upload" md-label="(manual step) Load e-claims" md-description="Load e-claims">
		    <!-- manual step -->
		    <i>Please 
			<ul>
				<li>SFTP e-claims to ADAM</li>
				<li>on Manual Batch processing page, download e-claims, then</li>
				<li>process them.</i>
			</ul>
		  </md-step>
			 
		  <md-step id="update-dates" md-label="Update Claim Received-on Date" md-description="Update received-on date of claims">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneUpdateClaimReceivedDate')">Run</button>
		  </md-step>		
		
		  <!-- RRT -->
		  <md-step class="manualStep" id="rrt" md-label="(manual step) Load Registered Respiratory Therapists (RRTs)" md-description="Load Registered Respiratory Therapists (RRTs).">
		    <!-- manual step -->
		    <i>Please load the registered respiratory therapists (RRTs).</i>
		  </md-step>

		  <md-step id="third" md-label="Update Oxygen Vendors" md-description="Update vendor end dates prior to batches.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneUpdateOxVendors')">Run</button>
		  </md-step>
		
		  <!-- OXF -->
		  <md-step class="manualStep" id="fourth" md-label="(manual step) OXF 90days -  Load eSubmissions Batch1" md-description="Load and process eclaims OXF_90days.">
		    <!-- manual step -->
		    <i>Please load Phase 3 Batch 1 OXF (90 day) eSubmission claims.</i>
		  </md-step>
		  
		  <md-step id="buspref" md-label="Add Dan's Law Client Business Preferences" md-description="Add MCSS and CCAC Business Preferences for Dan's Law clients.">
			    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneAddBusinessPreference')">Run</button>
		  </md-step>

		  <md-step id="fifth" md-label="Force approval" md-description="Force approvals of under-review claims.">
			    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneForceApprovalUnderReviewClaims')">Run</button>
		  </md-step>
			
		  <md-step id="adjusthomeoxygen" md-label="Adjust Home Oxygen Claims" md-description="Adjust Home Oxygen Claims for Dan's Law Clients">
			    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'adjustHomeOxygenClaims')">Run</button>
		  </md-step>
		  
		  <!-- OXR 9 months -->
		  <md-step class="manualStep" id="sixth" md-label="(manual step) OXF 90days and OXR 9months - Load eSubmissions Batch2" md-description="Load and process eclaims OXF_90days and OXR_9months.">
		    <!-- manual step -->
		    <i>Please load Phase 3 Batch 2 OXR (9 month) eSubmission claims.</i>    
		  </md-step>
		  <md-step id="seventh" md-label="Force approval" md-description="Force approvals of under-review claims.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneForceApprovalUnderReviewClaims')">Run</button>
		  </md-step>
		
		  <!-- OXR 12 months -->
		  <md-step class="manualStep" id="eighth" md-label="(manual step) OXR 12months - Load eSubmissions Batch3" md-description="Load and process eclaims OXR_12months.">
		    <!-- manual step -->
		    <i>Please load Phase 3 Batch 3 OXR (12 month) eSubmission claims.</i>    
		  </md-step>  
		  <md-step id="ninth" md-label="Force approval" md-description="Force approvals of under-review claims.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneForceApprovalUnderReviewClaims')">Run</button>
		  </md-step>
		  
		  <md-step id="tenth" md-label="Create paper invoices" md-description="Add new paper invoices.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneCreatePaperInvoices')">Run</button>
		  </md-step>
		
		  <md-step id="eleventh" md-label="Create eInvoices" md-description="Add new eInvoices.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneCreateEInvoices')">Run</button>
		  </md-step>
		
		  <md-step class="manualStep" id="twelfth" md-label="(manual step) Upload eInvoices" md-description="Upload eInvoices.">
		    <!-- manual step -->
		    <i>Please load upload eInvoice files, as described in the Operations Manual.</i>    
		  </md-step>    
		  
		  <md-step id="thirteenth" md-label="Update invoice received dates" md-description="Update the received dates of paper and e-Invoices.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneUpdatePaperAndEInvoice')">Run</button>
		  </md-step>
		
		  <md-step class="manualStep" id="fourteenth" md-label="(manual step) Approve eInvoices" md-description="Force approval of eInvoices.">
		    <!-- manual step -->
		    <i>Please manually approve eInvoices, as described in the Operations Manual.</i>    
		  </md-step>    
		   
		  <md-step id="fifteenth" md-label="Load client receipts" md-description="Load client receipts.">
		    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneLoadClientReceipts')">Run</button>
		  </md-step>
		  
		  <md-step id="sixteenth" md-label="Add client banking info" md-description="Add client banking info.">
		    <label>Add client banking info</label>
			    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'dayOneAddClientBankingInfo')">Run</button>
		  </md-step>
		  
		  <md-step class="manualStep" id="seventeenth" md-label="(manual step) Schedule payment" md-description="Schedule payments.">
		    <!-- manual step -->
		    <i>Please manually schedule the payments.</i>    
		  </md-step>    

		  <md-step id="eighteenth" md-label="TPS parsing" md-description="Run TPS parsing.">
		    <label><i>Parse TPS</i></label>
			    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'tpsParser')" >Run</button>
		  </md-step>
			
		</md-steppers>

    </md-tab>

    <md-tab id="day2" md-label="Day 2">

		<!-- <md-steppers :md-active-step.sync="active" md-vertical> -->
		<md-steppers md-vertical>
		
			<md-step id="first2" md-label="Adjust Home Oxygen Claims" md-description="AdjustHomeOxygenClaims">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'adjustHomeOxygenClaims')">Run</button>
			</md-step>
	
			<md-step id="second2" md-label="Create eHOP File" md-description="CreateEhopFile">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'createEHOPFile')">Run</button>
			</md-step>
	
			<md-step id="third2" class="manualStep" md-label="(manual step) Upload eHOP Updates" md-description="UploadEhopUpdates">
		    	<!-- manual step -->
		    	<i>Please manually upload EHOP updates, as described in the Operations Manual.</i>    			
			</md-step>
	
			<md-step id="fourth2" md-label="Add Credit Notes" md-description="AddCreditNotes">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'addCreditNotes')">Run</button>
			</md-step>
	
			<md-step id="fifth2" md-label="Record Cheque Return" md-description="RecordChequeReturn">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'recordChequeReturn')">Run</button>
			</md-step>
	
			<md-step id="sixth2" md-label="Record Renewal Response" md-description="RecordRenewalResponse">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'recordRenewalResponse')">Run</button>
			</md-step>
	
			<md-step id="seventh2" md-label="Cancel Reactivate Grants" md-description="CancelReactivateGrants">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'cancelReactivateGrants')">Run</button>
			</md-step>
	
			<md-step id="eighth2" md-label="Record Paper Credit Note" md-description="RecordPaperCreditNote">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'recordPaperCreditNote')">Run</button>
			</md-step>
	
			<md-step id="ninth2" class="manualStep" md-label="(manual step) Upload e-Credit Notes" md-description="UploadEcreditNotes">
		    	<!-- manual step -->
		    	<i>Please manually upload e-credi notes, as described in the Operations Manual.</i>
			</md-step>
	
			<md-step id="tenth2" md-label="Record Credit Note For Auto Invoice" md-description="RecordCreditNoteForAutoInvoice">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'recordCreditNoteForAutoInvoice')">Run</button>
			</md-step>
	
			<md-step id="eleventh2" md-label="AdjustVendorPayment" md-description="AdjustVendorPayment">
				<button class="md-raised md-primary" v-on:click="executeScript('day2', 'adjustVendorPayment')">Run</button>
			</md-step>		    
		
		  	<md-step class="manualStep" id="twelfth2" md-label="(manual step) Schedule payment" md-description="Schedule payments.">
		    	<!-- manual step -->
		    	<i>Please manually schedule the overnight payment run.</i>    
		  	</md-step>    

		  <md-step id="thirteenth2" md-label="TPS parsing" md-description="Run TPS parsing.">
		    <label><i>Parse TPS</i></label>
			    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'tpsParser')" >Run</button> <!-- a day1 script-->
		  </md-step>
		
		</md-steppers>
		
    </md-tab>

    <md-tab id="day3" md-label="Day 3">

		<!-- <md-steppers :md-active-step.sync="active" md-vertical> -->
		<md-steppers md-vertical>
		
			<md-step id="first3" md-label="Adjust Paid Claim Vendors" md-description="AdjustPaidClaimVendors">
				    <button class="md-raised md-primary" v-on:click="executeScript('day3', 'adjustPaidClaimVendors')">Run</button>
			</md-step>
	
			<md-step id="second3" md-label="Reactivate Grant" md-description="ReactivateGrant">
				    <button class="md-raised md-primary" v-on:click="executeScript('day3', 'reactivateGrant')">Run</button>
			</md-step>
	
			<md-step id="third3" md-label="Withdraw Paid Claim" md-description="WithdrawPaidClaim">
				    <button class="md-raised md-primary" v-on:click="executeScript('day3', 'withdrawPaidClaim')">Run</button>
			</md-step>
	
			<md-step id="fourth3" md-label="Resolve Vendor Invoices" md-description="ResolveVendorInvoices">
				    <button class="md-raised md-primary" v-on:click="executeScript('day3', 'resolveVendorInvoices')">Run</button>
			</md-step>
	
			<md-step id="fifth3" md-label="Delete On Hold Invoices" md-description="DeleteOnHoldInvoices">
				    <button class="md-raised md-primary" v-on:click="executeScript('day3', 'deleteOnHoldInvoices')">Run</button>
			</md-step>

		  	<md-step class="manualStep" id="sixth3" md-label="(manual step) Schedule payment" md-description="Schedule payments.">
		    	<!-- manual step -->
		    	<i>Please manually schedule the overnight payment run.</i>    
		  	</md-step>    

		  <md-step id="seventh3" md-label="TPS parsing" md-description="Run TPS parsing.">
		    <label><i>Parse TPS</i></label>
			    <button class="md-raised md-primary" v-on:click="executeScript('day1', 'tpsParser')">Run</button> <!-- a day1 script-->
		  </md-step>

		</md-steppers>
		
    </md-tab>
  </md-tabs>

</article>`

};
