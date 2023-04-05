import { adp } from '../js/data-service.js'

export var atsSFTPXML = {
	name: "sftpXML",
	props: ["fields"],
	data: function(){
	    return {
		   content: "",
		   outputDlg: false
	    }
	},
	methods : {
		testEClaim : function() {
			adp.phase1();
			this.outputDlg = true;
		}
	},
	template: `<article class="md-layout">		
		<button class="md-raised md-primary" v-on:click="testEClaim" >run test</button>
		<div>
			SFTP the XML.
		</div>
    </article>`
};


