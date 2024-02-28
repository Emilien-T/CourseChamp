import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import SignUp from '@/components/SignUp'
import SignUpStudent from '@/components/SignUpStudent'
import SignUpAdmin from '@/components/SignUpAdmin'
import Login from '@/components/Login'
import CreateCourse from '@/components/CreateCourse'
import CoursePage from '@/components/CoursePage'
import CreateReview from '@/components/CreateReview'
import AdminHome from '@/components/AdminHome'
import StudentHome from '@/components/StudentHome'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'SignUp',
      component: SignUp
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
    {
      path: '/viewreview',
      name: 'ViewReview',
      component: CoursePage
    },
    {
      path: '/createreview',
      name: 'CreateReview',
      component: CreateReview
    },
    {
      path: '/adminhome',
      name: 'AdminHome',
      component: AdminHome
    },
    {
      path: '/studenthome',
      name: 'StudentHome',
      component: StudentHome
    },

  ]
})
