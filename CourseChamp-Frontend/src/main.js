import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import vuetify from './plugins/vuetify'
import 'roboto-fontface/css/roboto/roboto-fontface.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.use(BootstrapVue)
Vue.config.productionTip = false
Vue.prototype.logginInEmail=''
Vue.prototype.userType=''

/* eslint-disable no-new */
new Vue({
  el: '#app',

  data: {
    loggedInUser: 'Email'
  },

  router,
  vuetify,
  render: h => h(App)
})
