<template>
  <div id="new-pothole">   

    <p id="add-headline">
        Please drag or click on the map to mark the location of the pothole you
        are reporting.
    </p>

<div id="autocomplete">
      <p class="autocomplete">Or search and move the pin by address:</p>
      <GmapAutocomplete
        class="autocomplete" 
        @place_changed='setPlace'
      />
      <button
        class="autocomplete"
        @click='moveMarker'
      >
        Move Marker
      </button>
    </div>

    <form class="new-pothole-report" v-on:submit.prevent>
      <div>
        <label for="severity">Please rank the pothole's severity:</label>
          <select name="severity" id="severity" v-model="report.user_severity">
            <option value="Minor">Minor</option>
            <option value="Could bust a tire">Could bust a tire</option>
            <option value="Do not drive over me">Do not drive over me</option>
            <option value="New route advised">New route advised</option>
            <option value="Catastrophic">Catastrophic</option>
          </select>
      </div>
    </form>
    <div id="map" class>
      <GmapMap
        :center="center"
        :zoom="18"
        map-type-id="hybrid"
        :options="mapOptions"
        style="width: 100vmin; height: 50vmin; position: relative"
        ref="mapRef"
        @click="handleMapClick"
      >
        <GmapMarker
          :position="marker.position"
          :clickable="true"
          :draggable="true"
          @drag="handleMarkerDrag"
          @click="panToMarker"
        />
      </GmapMap> 
      <button id="save-report" type="submit" v-on:click="saveReport">Save</button>
      <p id="nearest-address">
        Nearest Address to Pin: <u>{{ this.report.location }} </u>
      </p>
      <p id="lat-lng">
      Latitude: {{ marker.position.lat }} Longitude: {{ marker.position.lng }}</p>
    </div>
  </div>
</template>

<script>
import ReportService from "../services/ReportService";
import Vue from "vue";

export default {
  name: "new-pothole-report",
  components: {},
  data() {
    return {
      report: {
        username: "",
        user_severity: "Minor",
        lat: 0,
        lng: 0,
        location: "",
        reported: "",
      },
      marker: { position: { lat: 0, lng: 0 } },
      center: { lat: 0, lng: 0 },
      mapOptions: {
        disableDefaultUI: false,
      },
      reports: [],
      currentPlace: null,
      places: []
    };
  },
  mounted() {
    this.geolocate();
  },
  created() {
    // fills in current user's username on the report
    this.report.username = this.$store.state.user.username;

    ReportService.list().then((response) => {
      this.reports = response.data;
    });
  },
  methods: {
    saveReport() {
      let isReported = false;
      let isPhilly = false;
      let str = String(this.report.location);
      let philly = "Philadelphia";
      if (str.includes(philly)) {
        isPhilly = true;
      }
      if (!isPhilly) {
        window.alert("Pothole Patrol is currently for Philadelphia potholes only. Please select a pothole inside Philadelphia.");
      } else {
        this.reports.forEach((report) => {
          if (
            // Math.abs(report.lat - this.report.lat) < 0.0004 &&
            // Math.abs(report.lng - this.report.lng) < 0.0004
            report.location == this.report.location
          ) {
            isReported = true;
          }
        });
        if (!isReported) {
          // fills in current date and time on the report
          const date = new Date().toLocaleString();
          this.report.reported = date;

          ReportService.addReport(this.report).then((response) => {
            if (response.status === 201) {
              this.report = {
                username: "",
                lat: "",
                lng: "",
                location: "",
                status: "",
                reported: "",
              };
              this.$router.push("/reports");
            }
          });
        } else {
          window.alert("This pothole has already been reported. Thank you.");
        }
        // below curly should be commented out if Philadelphia check is to be bypassed
      }
    },

    // sets report.location to current lat/lng by making API call
    getLocation() {
      Vue.$geocoder.send(
        { lat: this.report.lat, lng: this.report.lng },
        (response) => {
          let address = String(response.results[0].formatted_address);
          this.report.location = address.substring(0, address.length-5);
        }
      );
    },

    //detects location from browser
    geolocate() {
      navigator.geolocation.getCurrentPosition((position) => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
        this.marker.position = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };

        // updates current report coordinates to marker positions
        this.report.lat = this.marker.position.lat;
        this.report.lng = this.marker.position.lng;
        this.panToMarker();

        // updates report.location
        this.getLocation();
      });
    },

    //sets the position of marker when dragged
    handleMarkerDrag(e) {
      this.marker.position = { lat: e.latLng.lat(), lng: e.latLng.lng() };

      // updates current report coordinates to marker positions
      this.report.lat = this.marker.position.lat;
      this.report.lng = this.marker.position.lng;

      // updates report.location
      this.getLocation();
    },

    // Moves the map view port to marker
    panToMarker() {
      this.$refs.mapRef.panTo(this.marker.position);
    },

    //Moves the marker to click position on the map
    handleMapClick(e) {
      this.marker.position = { lat: e.latLng.lat(), lng: e.latLng.lng() };

      // updates current report coordinates to marker positions
      this.report.lat = this.marker.position.lat;
      this.report.lng = this.marker.position.lng;

      // updates report.location
      this.getLocation();
    },

    setPlace(place) {
      this.currentPlace = place;
    },

    moveMarker() {
      if (this.currentPlace) {
        this.marker.position = {
          lat: this.currentPlace.geometry.location.lat(),
          lng: this.currentPlace.geometry.location.lng(),
        };
        this.panToMarker();

        this.report.lat = this.marker.position.lat; 
        this.report.lng = this.marker.position.lng; 

        this.getLocation();
        this.currentPlace = null;
      }
    },
  },
};
</script>

<style>
#map {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

#save-report {
  margin-top: 15px;
}

#new-pothole > p {
  margin-bottom: 5px;
}

#lat-lng {
  margin-top: -5px;
}

#severity  {
  margin-bottom: 10px;
}

.autocomplete {
  display: inline;
}

/* #autocomplete {
  margin-bottom: 10px;
} */
</style>