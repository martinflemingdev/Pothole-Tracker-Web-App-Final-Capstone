<template>
  <div id="report-box">
          <table id="user-report-table" class="styled-table">
            <th>Date Reported:</th>
            <th>Address:</th>
            <th>User Severity:</th>
            <th>Reported By:</th>
            <tr>
              <td>{{ this.report.reported }}</td>
              <td>{{ this.report.location }}</td>
              <td>{{ this.report.user_severity }}</td>
              <td>{{ this.report.username }}</td>
            </tr>
          </table>
        </div>
</template>

<script>
export default {
name: 'report-user',
props: ['report']
}
</script>

<style>

</style>