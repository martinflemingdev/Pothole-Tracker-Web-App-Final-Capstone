<template>
  <div class="pothole-list">
    <h1>Active Potholes</h1>

    <div class="report" v-if="this.$store.state.role != 'ROLE_EMPLOYEE'">
      <div id="sort-container">
        <div id="severity-sort">
          <h3>
            <label for="severity-sort"> Sort by User Severity:</label>
          </h3>
          <select id="reportFilter" name="severity-sort" v-model="filter.user_severity">
            <option value>Show All</option>
            <option value="Catastrophic">Catastrophic</option>
            <option value="New route advised">New Route Advised</option>
            <option value="Do not drive over me">Do Not Drive Over Me</option>
            <option value="Could bust a tire">Could bust a tire</option>
            <option value="Minor">Minor</option>
          </select>
        </div>
        <div id="user-sort">
          <h3>
            <label for="user-sort">Sort by Username: </label>
          </h3>
          <input type="text" id="usernameFilter" name="user-sort" v-model="filter.username" />
        </div>
        <div id="location-sort">
          <h3>
            <label for="location-sort"> Find by Location:</label>
          </h3>
          <input type="text" id="locationFilter" name="location-sort" v-model="filter.location" />
        </div>
      </div>

      <router-link
        v-bind:to="{ name: 'report-details', params: { id: report.report_id } }"
        v-for="report in filteredReports"
        v-bind:key="report.report_id"
      >
        <report-user v-bind:report="report" />
      </router-link>
    </div>

    <!-- Employee Section -->
    <div v-else>
      <div class="paragraphs">
        <p class="paragraph-margin">
           <strong>Active Reports:</strong> {{activeReports}}
        </p>
        <p class="paragraph-margin">
           <strong>Need to be scheduled for inspection:</strong> {{notInspected}}
        </p>
        <p>
           <strong>Need to be scheduled for repair:</strong> {{notRepaired}}
        </p>
        </div>
      <div id="employee-sort">
        <div id="employee-sort-top">
          <div id="internal-severity-sort">      
            <h3>
              <label for="internal-severity">Sort by Internal Severity:</label>
            </h3>
            <select id="reportFilter" name="internal-severity" v-model="filter.severity">
              <option value>Show All</option>
              <option value="0">0</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
            </select>
          </div>
    
        <div id="status-sort">
            <h3>
              <label for="status-sort">Sort By Status: </label>
            </h3>
            <select id="statusFilter" name="status-sort" v-model="filter.status">
              <option value>Show All</option>
              <option value="1">Reported</option>
              <option value="2">Inspected</option>
              <option value="3">Repaired</option>
            </select>
            </div>   
        </div>
        <!-- this line break breaks up the sort divs -->
        <br />
        <div id="employee-sort-bottom">
          <div id="id-sort">
            <h3>
              <label for="id-sort">Find by Id:</label>
            </h3>
            <input type="text" name="id-sort" id="reportIdFilter" v-model="filter.report_id" />
          </div>
            <div id="internal-location-sort">
              <h3>
                <label for="internal-location-sort"> Find by Location: </label>
              </h3>
              <input type="text" name="internal-location-sort" id="locationFilter" v-model="filter.location" />
            </div>
          </div>
        </div>

      <router-link
        v-bind:to="{ name: 'edit-report', params: { id: report.report_id } }"
        v-for="report in filteredReports"
        v-bind:key="report.report_id"
      >
        <report-employee v-bind:report="report" />
      </router-link>
    </div>
  </div>
</template>

<script>
import reportService from "../services/ReportService.js";
import reportUser from "../components/ReportUser";
import reportEmployee from "../components/ReportEmployee";

export default {
  name: "report-list",
  components: {
    reportUser,
    reportEmployee,
  },
  data() {
    return {
      activeReports: '',
      notInspected: '',
      notRepaired: '',
      reports: [],
      filter: {
        severity: "",
        user_severity: "",
        username: "",
        status: "",
        report_id: "",
        location: "",
      },
    };
  },
  computed: {
    filteredReports() {
      let filteredSeverity = this.reports;
      if (this.filter.severity != "") {
        filteredSeverity = filteredSeverity.filter(
          (report) => report.severity == this.filter.severity
        );
      }
      if (this.filter.username != "") {
        filteredSeverity = filteredSeverity.filter((report) =>
          report.username
            .toLowerCase()
            .includes(this.filter.username.toLowerCase())
        );
      }
      if (this.filter.user_severity != "") {
        filteredSeverity = filteredSeverity.filter(
          (report) => report.user_severity == this.filter.user_severity
        );
      }
      if (this.filter.status != "") {
        filteredSeverity = filteredSeverity.filter(
          (report) => report.status == this.filter.status
        );
      }
      if (this.filter.report_id != "") {
        filteredSeverity = filteredSeverity.filter(
          (report) => report.report_id == this.filter.report_id
        );
      }
      if (this.filter.location != "") {
        filteredSeverity = filteredSeverity.filter((report) =>
          report.location
            .toLowerCase()
            .includes(this.filter.location.toLowerCase())
        );
      }
      return filteredSeverity.sort((a, b) => {
        return b.report_id - a.report_id;
      });
    },
  },
  created() {
    reportService.list().then((response) => {
      this.reports = response.data;
      this.activeReports = this.reports.length;

      let notInspected = 0;
      let notRepaired = 0;

      this.reports.forEach(report => {
        if (report.inspected == null) {
          notInspected++;
        }
        if (report.repaired == null) {
          notRepaired++;
        }
        this.notInspected = notInspected;
        this.notRepaired = notRepaired;
      });



    });
    
  },
  methods: {
    reportStatus(report) {
      if (report.status === 1) {
        return "Reported";
      } else if (report.status === 2) {
        return "Inspected";
      } else if (report.status === 3) {
        return "Repaired";
      }
    },
    reportSeverity(report) {
      if (report.severity === 0) {
        return "TBD";
      } else if (report.severity === 1) {
        return "Minor";
      } else if (report.severity === 2) {
        return "Average";
      } else if (report.severity === 3) {
        return "Major";
      }
    },
  },
};
</script>

<style>
.paragraphs {
  font-size: 20px;
}

.paragraph-margin {
  margin-bottom: -10px;
}
</style>