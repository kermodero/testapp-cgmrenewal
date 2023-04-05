
export var atsSideMenu = {
	name: "atsSideMenu",
	data: function(){
	    return {
	    	junk: ""
	    }
	},
	methods: {
		testing: function(){
			console.log("side menu");
		}
	},
	template: `<div>
      <md-toolbar class="md-transparent" md-elevation="0">
        <span class="md-title"></span>
      </md-toolbar>
      <md-list>

        <md-list-item>
          <md-icon>assignment</md-icon>
          <router-link to="/p/configuration" class="md-list-item-text">
             Configuration
          </router-link>
        </md-list-item>

        <md-list-item>
          <md-icon>folder</md-icon>
          <router-link to="/manageData" class="md-list-item-text">
             Manage data
          </router-link>
        </md-list-item>        
        
        <md-list-item>
          <md-icon>folder</md-icon>
          <router-link to="/p/loadClaims" class="md-list-item-text">
             Load claims
          </router-link>
        </md-list-item>

        <md-list-item>
          <md-icon>directions_run</md-icon>
			<a href="/exit" class="md-list-item-text">Exit</a>
        </md-list-item>

      </md-list>
    </div>
`

};
