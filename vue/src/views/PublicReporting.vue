<template>
  <div id="public-reporting">
    <h1>Public Reporting</h1>
    <h3>The City of Philadelphia has been hard at work repairing potholes.</h3> 
    <p>With the help of the Pothole Patrol Reporting App, we have been able to complete the following work: </p>

    <table id="public-details-table" class="styled-table">
          <th>Potholes Reported:</th>
          <th>Potholes Inspected:</th>
          <th>Potholes Repaired:</th>

          <tr>
            <td>{{ reported }}</td>
            <td>{{ inspected }}</td>
            <td>{{ repaired }}</td>
          </tr>

      </table>
  </div>
</template>

<script>
import ReportService from "../services/ReportService";

export default {
  name: "public-reporting",
  data() {
    return {
      reports: {},
      reported: 0,
      inspected: 0,
      repaired: 0,
    };
  },
  created() {
    ReportService.list().then((response) => {
      this.reports = response.data;
      this.getReported();
      this.getStatus();
    });
  },
  methods: {
    getReported() {
      this.reported = this.reports.length;
    },
    getStatus() {
      let status2 = 0;
      let status3 = 0;
      this.reports.forEach((report) => {
        if (report.status == 2) {
          status2++;
        }
      });
      this.reports.forEach((report) => {
        if (report.status == 3) {
          status3++;
        }
      });
      this.inspected = status2 + status3;
      this.repaired = status3;
    }
  },
};
</script>

<style>
</style>