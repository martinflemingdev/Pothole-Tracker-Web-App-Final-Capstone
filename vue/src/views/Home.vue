<template
>
  <div class="home">
    <router-link v-bind:to="{ name: 'report-list' }" > <h1 v-if="this.$store.state.role == 'ROLE_EMPLOYEE'">Employee Portal</h1>  
    <h1 v-else-if="this.$store.state.role == 'ROLE_USER'">Public Portal</h1>
    <h1 v-else>Welcome to Pothole Patrol</h1>
    <img src="@/assets/PotholeLogo.png">
    </router-link>
  </div>
</template>

<script>
export default {
  name: "home",
  data() {
    return {
      username: "",
    }
  },
  created() {
    if (this.$store.state.token != ''){
      this.username= this.$store.state.user.username;
    }
  }
};
</script>

<style scoped>
img {
  width: 40vw;
   box-shadow: 0 0 20px rgba(0, 0, 0, 0.50);
}
</style>