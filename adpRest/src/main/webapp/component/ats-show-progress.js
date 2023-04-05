import { adp } from '../js/data-service.js'

export var atsShowProgress = {
	name: "showProgress",
	props: {
		timestamp: {
			type: String
		},
		activated: {
			type: Boolean
		}
	},
	data: function(){
	    return {
			downloadProgress: 0, //record created in Staging table
			loadedProgress: 0, //record created in Staging table
			processingProgress: 0
	    }
	},
	watch: { 
		/*
		 * 'activated' should be true only when the step in the flow (e.g. Validation Execute Batch Download)
	     * is open. Once step is closed, 'activated' becomes false and the monitoring of progress should stop.
		 * 
	     * 'increment' calls 'this.advance' (= 'forward'), defined in methods below.
	     * This queries the DB (via REST call) for status of load. 'Complete' stops the process.
		 * 
		 * The baroque, nested function design is used to cope with loss of 'this' in anonymous functions.
	     *
		 */
      	activated: function(newValue, oldValue) {
			this.downloadProgress=0;
			this.processingProgress=0;	
			var increment = function(forward, allDone) {
				forward();
				setTimeout(() => {
					if (!allDone())
						increment(forward, allDone); //recur.
				}, 1000);
			};
			if (newValue) //if this step in the flow is activated
				increment(this.advance, this.complete); 
        }
	},
	methods : {
		advance: function() {
			adp.run(['downloadProgress', this.timestamp], (data) => {
				console.log('progress', data.data.downloadPercentage, data.data.processedPercentage);
				this.downloadProgress = data.data.downloadPercentage;
				this.loadedProgress = data.data.loadedPercentage;
				this.processingProgress = data.data.processedPercentage;				
			});
		},
		complete: function() {
			return ((this.downloadProgress >= 100 &&
						this.processingProgress	>= 100)		
						|| !this.activated);
		}
	},
	template: `
<section >
	<div class="progressIndicator">
		<md-progress-spinner class="md-accent" md-mode="determinate" :md-diameter="50" :md-value="downloadProgress"></md-progress-spinner>
		Download progress: {{downloadProgress}}%
	</div><br/>
	<div class="progressIndicator">
		<md-progress-spinner class="md-accent" md-mode="determinate" :md-diameter="50" :md-value="loadedProgress"></md-progress-spinner>
		Loaded progress: {{loadedProgress}}%
	</div><br/>	
	<div class="progressIndicator">
		<md-progress-spinner class="md-accent" md-mode="determinate"  :md-diameter="50" :md-value="processingProgress"></md-progress-spinner>
		Processing progress: {{processingProgress}}%
	</div>	
</section>`

};


