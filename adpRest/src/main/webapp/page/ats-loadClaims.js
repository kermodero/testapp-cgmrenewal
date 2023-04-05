import { adp } from '../js/data-service.js'

export var atsLoadClaims = {
	name: "loadClaims",
	created: function() {
		this.$root.progress = 'IDLE';
		this.$root.showConsoleLog = true;
	},
	data: function(){
	    return {
			placeHolder: false
	    }
	},
	methods: {		
		loadClaims : function() {
			alert("ok, it worked");
		}
	},
	template: `
<article>

 <md-tabs >
      <md-tab id="tab-generate-l" md-label="Generate"  >
      
    <div class="md-layout">
      <div class="md-layout-item">
		<md-steppers md-vertical>
		
		  <md-step id="first-l" md-label="Generate claims" md-description="Generate claims.">

		  </md-step>
	
		  <md-step id="second-l" md-label="Load claims" md-description="Load the claims">
			<button class="md-raised md-primary" v-on:click="loadClaims">Do it</button>
	      </md-step>
				 	
		</md-steppers> 
		
	  </div>

	</div>
	
	</md-tab>
	<md-tab id="tab-existing-l" md-label="Another tab">
	  <div class="md-layout">
	      <div class="md-layout-item">
			<md-steppers md-vertical>
			
			  <md-step id="firstExisting-l" md-label="Use Existing XML" md-description="Use existing XML from excel files.">
				<section>
					<div>  
					  <label>Device Category</label>
					</div>
				
				
				  </section>	
			  </md-step>
							 
			 <md-step class="manualStep-l" id="fourthExisting" md-label="(manual step) Execute Batch Download" md-description="Execute Batch Download in ADAM">
		     </md-step>
			
		
			</md-steppers> 
			
		  </div>

	</div>
      </md-tab>
</article>`

};