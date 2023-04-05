import { adp } from '../js/data-service.js'

export var atsConsole = {
	name: "console",
	props: ["url"],
	methods : {

	},
	template: `
<article class="md-layout">
	<iframe id="outputIFrame" :src.sync="url">hello.</iframe>
</article>`

};


