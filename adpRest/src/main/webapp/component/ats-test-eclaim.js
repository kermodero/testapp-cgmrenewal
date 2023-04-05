import { adp } from '../js/data-service.js'

export var atsTestEClaim = {
	name: "atsTestEClaim",
	props: ["fields"],
	data: function(){
	    return {
		   content: "",
		   outputDlg: false
	    }
	},
	methods : {
		testEClaim : function() {
			//adp.phase1();
			this.outputDlg = true;
		}
	},
	template: `<article class="md-layout">		
		<button class="md-raised md-primary" v-on:click="testEClaim" >run test</button>
		<div>
    		<md-dialog :md-active.sync="outputDlg">
      			<md-dialog-title>output</md-dialog-title>
				<iframe id="consoleIFrame"
	  					title="Inline Frame Example">
				</iframe>					
      			<md-dialog-actions>
		        	<md-button class="md-primary"@click="outputDlg=false" >Close</md-button>
		      </md-dialog-actions>
		    </md-dialog>
		</div>
    </article>`
};


