import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import SignUp from '@/components/SignUp'
import SignUpStudent from '@/components/SignUpStudent'
import SignUpAdmin from '@/components/SignUpAdmin'
import Login from '@/components/Login'
import CreateCourse from '@/components/CreateCourse'

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
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/createcourse',
      name: 'CreateCourse',
      component: CreateCourse
    },
  ]
})
