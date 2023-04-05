import { adp } from '../js/data-service.js'

export var atsTestData = {
	name: "testData",
	created: function() {
		adp.getAll("test", "0", this.showTable, this.problem);
		this.$root.progress = 'IDLE';
	},
    data: function() { 
    	return  {
    		theObjects: []
    	}
    },
	methods: {		
		showTable: function(values){
			console.log("got em", values);
			this.theObjects = values.content;
		},
		problem: function(message) {
			console.log("error getting data", message);
		}
	},
	template: `
<article>
  <div>

    <md-table v-model="theObjects" >
      <md-table-toolbar>
        <h1 class="md-title">Data</h1>
      </md-table-toolbar>
	  <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
        <md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
        <md-table-cell md-label="Description" md-sort-by="description">{{ item.description }}</md-table-cell>
		<md-table-cell md-label="Edit" md-sort-by="description">
		  <md-button class="md-icon-button md-raised">
        	<md-icon>edit</md-icon>
		  </md-button>
		</md-table-cell>
        <md-table-cell md-label="Delete" md-sort-by="description">
          <md-button class="md-icon-button md-raised">
        	<md-icon>delete</md-icon>
		  </md-button>
		</md-table-cell>

      </md-table-row>    
    </md-table>
    
  </div>

</article>`

};