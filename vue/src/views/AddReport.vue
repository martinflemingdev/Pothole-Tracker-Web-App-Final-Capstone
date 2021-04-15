<template>
  <div>
    <h1>Add New Report</h1>
    <new-pothole-report />
  </div>
</template>

<script>
import NewPotholeReport from '../components/NewPotholeReport.vue';

export default {
    name: 'add-report',
    components: {
      NewPotholeReport
    }
};
</script>

<style>
</style>