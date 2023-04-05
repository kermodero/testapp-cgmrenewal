import { adp } from '../js/data-service.js'

export var atsGenerateXML = {
	name: "generateXML",
	props: ["fields"],
	data: function(){
	    return {
		   content: "",
		   selectedCategories : [],
		   selectedFiles : null,
		   timestamp: "unset",
		   showDlg: false,
		   updateTimestamp: 0
	    }
	},
	methods : {
		genXML : function() {
			var files = document.getElementById("xlsFiles").files;
			var fileArray = [];
			for (var i = 0; i < files.length; i++) {
				fileArray.push(files[i]);
			};
			this.$root.progress = 'EXECUTING';
			adp.uploadFiles(0,'generate-xml', fileArray, (result) => {
				console.log('monitor uploads', result);
				this.$emit('submitted', result.data);
				if (result.outcome == "SUCCESS") {
					this.timestamp = result.data;
				} else {
					this.timestamp = result.data;
				}
				this.$root.progress = 'COMPLETE';
			});
		},
		genXMLFromFileNames : function() {
			var files = document.getElementById("xlsFiles").files;
			var fileArray = [];
			for (var i = 0; i < files.length; i++) {
				fileArray.push(files[i].name);
			};
			this.$root.progress = 'EXECUTING';
			adp.uploadFilenames('generate-xml-filenames', fileArray, (result) => {
				console.log('monitor uploads', result);
				this.$emit('submitted', result.data);
				if (result.outcome == "SUCCESS") {
					this.timestamp = result.data;
				} else {
					this.timestamp = result.data;
				}
				this.$root.progress = 'COMPLETE';
			});
		},
		saveToClipbpard : function() {
			navigator.clipboard.writeText(this.timestamp);
		}
	},
	template: `
<article class="md-layout">
  <section>

	<div>  
	  <label>Device Category</label>
	</div>

	<form id="filesForm" class="field">
	  <input type="file" id="xlsFiles" multiple />
	</form>
	
	<button class="md-raised md-primary" v-on:click="genXMLFromFileNames" >Generate XML eSubmission claim files</button>		

	<div class="timestamp">  
	  <label>Timestamp:</label><div id="time">{{timestamp}}</div>
	  <button v-on:click="saveToClipbpard" title="Copy to clipboard."><span class="material-icons">content_paste</span></button>
	</div>

  </section>	

</article>`

};


