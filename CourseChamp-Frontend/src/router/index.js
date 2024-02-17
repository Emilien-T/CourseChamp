import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import SignUp from '@/components/SignUp'
import SignUpStudent from '@/components/SignUpStudent'
import SignUpAdmin from '@/components/SignUpAdmin'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/signup/student',
      name: 'SignUpStudent',
      component: SignUpStudent
    },
    {
      path: '/signup/admin',
      name: 'SignUpAdmin',
      component: SignUpAdmin
    }

  ]
})
