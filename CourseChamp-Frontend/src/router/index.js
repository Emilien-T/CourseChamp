import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import SignUp from '@/components/SignUp'
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
