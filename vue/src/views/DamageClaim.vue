<template>
  <div id="damage-report" class="damage-report">
    <h3>Please Submit Details for Damage Claim</h3>
    <p>
      The City of Philadelphia will review damage claims within 2-3 business
      days.
    </p>
    <form id="damage-form" v-on:submit.prevent>
      <div>
        <label for="name">Full Name: </label>
        <input
          id="name"
          type="text"
          name="name"
          v-model="damageClaim.full_name"
        />
        <label for="phonenumber">Phone Number: </label>
        <input
          id="phone-number"
          type="tel"
          name="phonenumber"
          v-model="damageClaim.phone_number"
        />
      </div>
      <div>
        <label for="email">Email Address: </label>
        <input
          id="email"
          type="email"
          name="email"
          v-model="damageClaim.email"
        />

        <label for="address">Home Address: </label>
        <input
          id="address"
          type="text"
          name="address"
          v-model="damageClaim.address"
        />
      </div>
      <div>
        <label for="incidentdate">Incident Date: </label>
        <input
          id="incident-date"
          type="datetime-local"
          name="incidentdate"
          v-model="damageClaim.incident_date"
        />
      </div>
      <div>
        <label for="car">Car Make and Model: </label>
        <input id="car" type="text" name="car" v-model="damageClaim.car" />
      </div>
      <div>
        <label for="description">Damage Description: </label>
        <textarea
          rows="5"
          cols="50"
          id="description"
          name="description"
          maxlength="400"
          v-model="damageClaim.damage_description"
        />
      </div>
      <button type="submit" v-on:click="saveDamageClaim">Save</button>
     
    </form> 
    <p> A copy of your submission will be emailed to you for your records. </p>
  </div>
</template>

<script>
import ReportService from "../services/ReportService";
import emailjs from "emailjs-com";

export default {
  name: "damage-claim",
  data() {
    return {
      damageClaim: {
        damage_claim_id: 0,
        report_id: 0,
        full_name: "",
        phone_number: "",
        email: "",
        address: "",
        incident_date: "",
        car: "",
        damage_description: "",
      },
      damageClaims: [],
    };
  },
  created() {
    this.damageClaim.report_id = this.$route.params.id;
  },
  methods: {
    saveDamageClaim() {

      ReportService.addDamageClaim(this.damageClaim).then((response) => {
        if (response.status === 201) {

          ReportService.getDamageClaims().then((response) => {
            this.damageClaims = response.data;
            let maxId = 0;
            this.damageClaims.forEach((claim) => {
              if (claim.damage_claim_id > maxId) {
                maxId = claim.damage_claim_id;
              }
            });
            this.damageClaim.damage_claim_id = maxId;

            let template = {
              claimid: this.damageClaim.damage_claim_id,
              from_name: "Philly Pothole Patrol",
              email: this.damageClaim.email,
              pothole: this.damageClaim.report_id,
              name: this.damageClaim.full_name,
              incidentdate: this.damageClaim.incident_date,
              car: this.damageClaim.car,
              description: this.damageClaim.damage_description,
              phonenumber: this.damageClaim.phone_number,
              address: this.damageClaim.address,
            };
            
            emailjs.send(
              "service_6m763ki",
              "template_fyr6lpj",
              template,
              "user_uonguwpbfCY1tRYQaAr3u"
            );
            this.damageClaim = {
              report_id: 0,
              full_name: "",
              phone_number: "",
              email: "",
              address: "",
              incident_date: "",
              car: "",
              damage_description: "",
            };
            this.$router.push("/reports");
          });
        }
      });
    },
  },
};
</script>

<style>
</style>