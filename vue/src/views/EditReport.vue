<template>
<div id ="map">

<h1> Edit Report #{{report.report_id}} </h1>
    <table id="employee-edit-table">
      <th> Address: </th>
      <th> Date Reported: </th>
      <th> Reported By: </th>
      <th> Status: </th>

      <tr>
        <td>{{ report.location }}</td>
        <td>{{ report.reported }}</td>
        <td>{{ report.username }}</td>
        <td>{{ report.status }} </td>
      </tr>
      <th> Schedule For Inspection: </th>
      <th> Schedule For Repair: </th>
      <th> Change Severity: </th>
      <th> Change Status: </th>

      <tr>
        <td>
          <label for="inspected"/>
            <input name="inspected" type="date" v-model="report.inspected"/>
        </td>
        <td>
          <label for="repaired"/>
            <input name="repaired" type="date" v-model="report.repaired"/>
        </td>
        <td>
          <label for="severity"/>
            <select name="severity" id="severity" v-model="report.severity">
              <option value="0">TBD</option>
              <option value="1">Minor</option>
              <option value="2">Average</option>
              <option value="3">Major</option>
            </select>
        </td>
        
        <td> 
          <label for="status"/>
          <select name="status" id="status" v-model="report.status">
            <option value="1">Reported</option>
            <option value="2">Inspected</option>
            <option value="3">Repaired</option>
          </select> 
      </td>
      </tr>
    </table>
    <h3>
    <button class="btn-style" id="update" v-on:click.prevent="updateReport(report)">Update</button> 
    <button class="btn-style" id="delete" v-on:click.prevent="deleteReport(report.report_id)">Delete</button>
    </h3>
    <br>
      <div id="edit-map">
      <GmapMap
        :center="center"
        :zoom="18"
        map-type-id="hybrid"
        :options="mapOptions"
        style="width: 100vmin; height: 50vmin"
        ref="mapRef"
      >
        <GmapMarker
          :position="marker.position"
          :clickable="false"
          :draggable="false"
          @drag="handleMarkerDrag"
          @click="panToMarker"
        />
      </GmapMap>

    </div>
    <router-link v-bind:to="{ name: 'report-list' }" > <h3>Return to List</h3> </router-link>
  </div>
</template>

<script>

import ReportService from "../services/ReportService"

export default {
  name: 'edit-report',
data() {
    return {
      center: {lat: 0, lng: 0},
      marker: {
          position: {lat: 0, lng: 0} },
      mapOptions: {
        disableDefaultUI: false,
      },
      reports: [],
      report: {
          report_id: 0,
          username: '',
          lat: 0,
          lng: 0,
          reported: '',
          inspected: '',
          repaired: '',
          status: 0,
          severity: 0,
          img: ''

      }
    };
  },
  created() {
      // grab all reports on load
    ReportService.list().then((response) => {
      this.reports = response.data;

      // find current report with route param id 
      this.report = this.reports.find( report => {
          return report.report_id == this.$route.params.id;
      })

      // set marker to report lat/lng
      this.marker.position.lat = this.report.lat;
      this.marker.position.lng = this.report.lng;

      // set center to marker
      this.center.lat = this.marker.position.lat;
      this.center.lng = this.marker.position.lng;
    });
  },
  methods: {
    deleteReport() {
      let confirmDelete = confirm("Please confirm delete");
      if (confirmDelete == true) {
      ReportService.deleteReport(this.report.report_id).then((response) => {
        if (response.status === 204) {
          this.$router.push( { name: 'report-list'} )
        }
      })
  }   
    },
    updateReport() {
      ReportService.updateReport(this.report).then( (response) => {
        if (response.status === 200) {
          this.$router.push( {name: 'report-list'} )
        }
      })
    },
    reportStatus(report) {
      if (report.status === 1) {
        return "Reported";
      }
      else if (report.status === 2) {
        return "Inspected"
      } 
      else if (report.status === 3) {
        return "Repaired"
      }
    },
    reportSeverity(report) {
      if (report.severity === 0) {
        return "TBD"
      } 
      else if (report.severity === 1) {
        return "Minor"
      }
      else if (report.severity === 2) {
        return "Average"
      }
      else if (report.severity === 3) {
        return "Major"
      }
    },
      //sets the position of marker when dragged
    handleMarkerDrag(e) {
      this.marker.position = { lat: e.latLng.lat(), lng: e.latLng.lng() };
    },

    //Moves the map view port to marker
    panToMarker() {
      this.$refs.mapRef.panTo(this.marker.position);
      this.$refs.mapRef.setZoom(18);
    },

    //Moves the marker to click position on the map
    handleMapClick(e) {
      this.marker.position = { lat: e.latLng.lat(), lng: e.latLng.lng() };
      console.log(e);
    },
  }

};
</script>

<style> 

.btn-style {
  margin: 0 10px;
  padding: 5px;
}


</style>