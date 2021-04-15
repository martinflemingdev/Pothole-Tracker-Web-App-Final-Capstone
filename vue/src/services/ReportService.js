import axios from 'axios'; 

export default {

    list() {
        return axios.get('/reports');
    },

    addReport(report) {
      return axios.post('/reports', report);
    },

    deleteReport(report_id) {
      return axios.delete(`/reports/${report_id}`);
    },

    updateReport(report) {
      return axios.put('/reports', report);
    },

    addDamageClaim(damageClaim) {
      return axios.post('/damage-claims', damageClaim);
    },

    getDamageClaims() {
      return axios.get('/damage-claims');
    }

}