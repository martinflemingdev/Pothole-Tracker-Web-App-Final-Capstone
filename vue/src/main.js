import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import * as VueGoogleMaps from 'vue2-google-maps'
import geocoder from "@pderas/vue2-geocoder";

Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyDjR3jy_701Pqd54qSMyahGbu11u0EDZ1Y',
    libraries: 'places'
  }
});

Vue.use(geocoder, {
  defaultCountryCode: null, // e.g. 'CA'
  defaultLanguage:    null, // e.g. 'en'
  defaultMode:        'lat-lng', // or 'address'
  googleMapsApiKey:   'AIzaSyDjR3jy_701Pqd54qSMyahGbu11u0EDZ1Y'
});



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
