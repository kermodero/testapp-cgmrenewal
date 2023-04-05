import { atsHeader } from '../component/ats-header.js';
import { atsFooter } from '../component/ats-footer.js';
import { atsSideMenu } from '../component/ats-side-menu.js';
import { atsGenerateXML } from '../component/ats-generate-xml.js';
import { atsShowProgress } from '../component/ats-show-progress.js';
import { atsSFTPXML } from '../component/ats-sftp-xml.js';
import { atsTestEClaim } from '../component/ats-test-eclaim.js';
import { atsConsole } from '../component/ats-console.js';
import { atsLog } from '../component/ats-log.js';
import { atsPrincipal } from '../page/ats-principal.js';
import { atsNoLog } from '../page/ats-nolog.js';
import { atsRegression } from '../page/ats-regression.js';
import { atsESubValidation } from '../page/ats-esubvalidation.js';
import { atsESubCreation } from '../page/ats-esubcreation.js';
import { atsConfiguration } from '../page/ats-configuration.js';
import { atsLoadClaims } from '../page/ats-loadClaims.js';
import { atsTestData } from '../page/ats-testData.js';
import { atsESubHelp } from '../page/ats-help.js';

const routes = [
    { path: "/p",
      component: atsPrincipal,
	  meta: { title: "ADAM Testing" },
      children: [
	  {
	      path: "configuration",
	      component: atsConfiguration,
		  meta: { title: "Configuration" }
	  },
	  {
	      path: "loadClaims",
	      component: atsLoadClaims,
	  	  meta: { title: "Load Claims" }
	  }
      ]
    },
    { path: "/",
      component: atsNoLog,
  	  meta: { title: "ADAM Testing" },
  	  children: [
  	    {
  	        path: "manageData",
  	        component: atsTestData,
  	  	    meta: { title: "Manage Load Claims" }
  	    },
  	    {
  	      path: "/help",
  	      component: atsESubHelp,
  	      meta: { title: "Help" }
  	    }  	    	
  	  ]
    }
];

var router = new VueRouter({
	routes: routes
});

Vue.prototype.subHeading = 'ADAM Testing';
	
router.afterEach((to, from) => {
	console.log('routed ', to.meta.title);
    document.title = to.meta.title;
	Vue.prototype.subHeading = to.meta.title; //set the page subheading - see ats-principal {{subheading}}.
});

export var ats = ats || {};

ats.init = function(){
	//globally registered components
    Vue.component('ats-side-menu', atsSideMenu);
	Vue.component('ats-test-eclaim', atsTestEClaim);
	Vue.component('ats-generate-xml', atsGenerateXML);
	Vue.component('ats-show-progress', atsShowProgress);	
	Vue.component('ats-sftp-xml', atsSFTPXML);
    Vue.component('ats-console', atsConsole);	
    Vue.component('ats-log', atsLog);	
	
	var vm = new Vue({
		el: '#ats-app',
		data: {
			renderCount: 0,
			pageHeading: 'ADP Testing',
			progress: 'IDLE'
		},
		//locally registered components
		components: {
		    'ats-header': atsHeader,
		    'ats-principal': atsPrincipal,
		    'ats-footer': atsFooter
		},
		router: router
	});
	
};
