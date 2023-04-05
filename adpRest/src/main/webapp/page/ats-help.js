import { adp } from '../js/data-service.js'


export var atsESubHelp = {
	name: "eSubHelp",
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
			isValidating:false,
			links:{}
	    }
	},
	methods: {
	     getHelpLinks(){
    			   fetch(adp.baseURL + '/' + "helpinfo")
					.then(response => response.json())
					.then(data => {
						console.log(data.data[1]);
						this.links = data.data;
					});
					
    	}
    
    },
    
    
    created(){
    	this.getHelpLinks();
	},
    

	template: `
<article>


    <div class="md-layout">
      <div class="md-layout-item">
		
		<i>(To update file links, navigate to test apps directory "/config/help-links.json" to modify and add new links.)</i>
		
		<H2>
			The following are helpful links to navigate test cases and test app:
		</H2
		
		
		<div>
			<ul id="example-2">
  				<li v-for="(link, index) in links">
   				 <p style="font-weight:bold;font-size:16px">{{link.description}} :</p> <p>      {{link.link}}</p>
  				</li>
			</ul>
		<div>
	

	  </div>
	  </div>

	</div>
</article>`

};
