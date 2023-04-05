import { adp } from '../js/data-service.js'

export var atsNoLog = {
	name: "no-log",
	data: function(){
	    return {
	    	menuVisible: false
	    }
	},
	created: function () {
    	console.log("created no log page");
		adp.showLog(); //kick off the logging loop.
  	},
	methods: {
		toggleMenu: function() {
	    	this.menuVisible = !this.menuVisible;
		},
		clear: function() {
			var logDiv = document.querySelector("#log");
			logDiv.innerHTML = '';
		}
	},
	template: `
<section class="page-container md-layout-row">
 <md-app style="min-height: 100%;">
 
    <md-app-toolbar class="md-secondary">
      <md-button class="md-icon-button" @click="toggleMenu" v-if="!menuVisible">
        <md-icon>menu</md-icon>
      </md-button>
      <span class="md-title" style="flex: 1">{{subHeading}}</span>
      <div class="progress">{{$root.progress}}</div>
    </md-app-toolbar>
      
	<md-app-drawer :md-active.sync="menuVisible" md-persistent="full">
		<md-toolbar class="md-transparent" md-elevation="0">
          <span>Tests</span>
          <div class="md-toolbar-section-end">
            <md-button class="md-icon-button md-dense" @click="toggleMenu">
              <md-icon>keyboard_arrow_left</md-icon>
            </md-button>
          </div>
        </md-toolbar>	
		<ats-side-menu></ats-side-menu>
	</md-app-drawer>
	
    <md-app-content>
	  <div class="md-layout md-gutter">
		<div class="md-layout-item  md-size-100">
          <router-view></router-view>
		</div>
	  </div>
    </md-app-content>
    
 </md-app>
</section >`

};
