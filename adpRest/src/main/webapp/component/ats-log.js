import { adp } from '../js/data-service.js'

export var atsLog = {
	name: "log",
	props: ["display"],
	data: function() {
   		return {
       		displayDlg: adp.dlgDisplayed
   		};
	},
	
	/**
	 * This component shows a dialog containing a div with id="log", below
     * Encapsulation is not ideal, here. the method which populates this div is
	 * adp.showOutput in js/data-service.js
	 * This last method is invoked in callbacks.
	 *
	 */	
	methods : {
		killThread: function() {
			console.log('killing process.');
			adp.killThread();
			this.showDlg = false;
			this.$emit("closed", "");
		},
		clear: function() {
			console.log("clear");
			var log = document.querySelector("#log");
			log.innerHTML = "";
		},
		close: function() {
			console.log("close");
			adp.dlgDisplayed = false;
			this.displayDlg = false;
			this.$emit("closed", "");
		}
	},
	template: `
	<article class="md-layout md-gutter">
	  <div class="md-layout-item md-size-100">
	      <md-dialog-title>Test output</md-dialog-title>
	      <div id="progress">
	        <md-progress-bar md-mode="indeterminate"></md-progress-bar>
	      </div>
	  </div>
	  <div class="md-layout-item md-alignment-center md-size-100">

			<!-- disabled! -->

			<div id="log1">
			</div>
	  </div>
	  <div class="md-layout-item md-size-100">
	      <md-dialog-actions>
	        <md-button class="md-raised md-primary" @click="clear">Clear</md-button>
	        <md-button class="md-raised md-primary" @click="close">Close</md-button>
	        <!-- <md-button class="md-raised md-primary" @click="killThread">Kill</md-button> -->
	      </md-dialog-actions>
	  </div>
	</article>`

};


