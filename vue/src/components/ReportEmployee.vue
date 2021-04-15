<template>
  <div id="report-box">
            <table id="employee-report-table">
              <th>Report ID: </th>    
              <th>Date Reported: </th>
              <th>Address: </th>
              <th>Reported By:</th>
              
              <tr>
                <td>{{ this.report.report_id }}</td>
                <td>{{ this.report.reported }} </td>
                <td>{{ this.report.location }} </td>
                <td>{{ this.report.username }}</td>
              </tr>

              <th>Official Status Code:</th>
              <th>Official Severity:</th>
              <th>Date Inspected</th>
              <th>Date Repaired</th>

              <tr>
                <td>{{ this.report.status }}</td>
                <td>{{ this.report.severity }}</td>
                <td>{{ this.report.inspected == null ? 'N/A' : this.report.inspected }} </td>
                <td>{{ this.report.repaired == null ? 'N/A' : this.report.repaired}}</td>
              </tr>
            
          </table>
        </div>
</template>

<script>
export default {
name: 'report-employee',
props: ['report']
}
</script>

<style>

</style>