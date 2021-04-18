<template>
  <div>
    <div class="home">
      <router-link v-bind:to="{ name: 'report-list' }">
        <h1 v-if="this.$store.state.role == 'ROLE_EMPLOYEE'">
          Employee Portal
        </h1>
        <h1 v-else-if="this.$store.state.role == 'ROLE_USER'">Public Portal</h1>
        <h1 v-else>Welcome to Pothole Patrol</h1>
        <p>
          Below is a map of the {{ reports.length }} potholes reported so far.
        </p>
        <!-- <img src="@/assets/PotholeLogo.png" /> -->
      </router-link>
    </div>
    <div id="home-map">
      <GmapMap
        ref="mapRef"
        :center="center"
        :zoom="12"
        :options="mapOptions"
        map-type-id="hybrid"
        style="width: 50vmin; height: 50vmin; position: relative"
      >
        <GmapMarker
          :key="report.id"
          v-for="report in reports"
          :position="{ lat: report.lat, lng: report.lng }"
          :clickable="true"
          :draggable="false"
          @click="panToMarker(report)"
        />
      </GmapMap>
      <!-- <GmapMarker
          :key="index"
          v-for="(marker, index) in markers"
          :position="marker.position"
          :clickable="true"
          :draggable="false"
          @click="panToMarker(marker)"
        />
      </GmapMap> -->
    </div>
    <div id="bottom-btns">
      <button @click.prevent="reset" id="reset-btn">
        See All Potholes </button>
      <button @click.prevent="details" id="details-btn">
        See Details of Current Pothole </button>
    </div>
  </div>
</template>

<script>
import reportService from "../services/ReportService.js";

export default {
  name: "home",
  data() {
    return {
      center: { lat: 39.96003851345567, lng: -75.16224869099684 },
      mapOptions: {
        disableDefaultUI: true,
      },
      reports: [],
      currentReport: {},
      // username: "",
    };
  },
  mounted() {
    reportService.list().then((response) => {
      this.reports = response.data;
    });
    // if (this.$store.state.token != ''){
    //   this.username= this.$store.state.user.username;
    // }
  },
  methods: {
    // Moves the map view port to marker
    panToMarker(marker) {
      // sets marker clicked on as current report
      this.reports.forEach((r) => {
        if (r.lat == marker.lat && r.lng == marker.lng) {
          this.currentReport = r;
        }
      });
      this.$refs.mapRef.panTo({ lat: marker.lat, lng: marker.lng });
      this.$refs.mapRef.$mapPromise.then((map) => {
        map.setZoom(20);
      });
    },
    reset() {
      this.center = { lat: 39.96003851345567, lng: -75.16224869099684 };
      this.currentReport = {};
      this.$refs.mapRef.$mapPromise.then((map) => {
        map.setZoom(12);
        map.setCenter({ lat: 39.96003851345567, lng: -75.16224869099684 });
      });
    },
    details() {
      this.$router.push( { name: 'report-details', params: { id: this.currentReport.report_id } } );
    }
  },
};
</script>

<style scoped>
img {
  width: 40vw;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
}
#home-map {
  display: flex;
  /* flex-direction: column; */
  /* align-items: center; */
  justify-content: center;
  margin-bottom: 20px ;
}

#bottom-btns {
  display: inline-block;
  width: 50vmin;
}

#reset-btn {
  display: inline;
}

#details-btn {
  display: inline;
  width: auto;
}
</style>