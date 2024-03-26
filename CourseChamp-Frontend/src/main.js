import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import Vuetify from 'vuetify'
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


// import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader

Vue.use(BootstrapVue)
Vue.use(Vuetify)
Vue.config.productionTip = false
Vue.prototype.logginInEmail=''
Vue.prototype.userType=''

/* eslint-disable no-new */
new Vue({
  Vuetify,
  el: '#app',
  data: {
    loggedInUser: 'Email'
  },
  router,
  render: h => h(App)
})
