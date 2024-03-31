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
import UserPortal from '@/components/UserPortal'
import EditReview from '../components/EditReview.vue'
import UpdateCourse from '@/components/UpdateCourse'
import StudentNavBar from '@/components/StudentNavBar'
import AdminNavBar from '@/components/AdminNavBar'
import ManageCourses from '@/components/ManageCourses'
import CreateCourseForm from '../components/CreateCourseForm.vue'
import UpdateCourseForm from '../components/UpdateCourseForm.vue'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/updatecourseform',
      name: 'UpdateCourseForm',
      component: UpdateCourseForm
    },
    {
      path: '/createcourseform',
      name: 'CreateCourseForm',
      component: CreateCourseForm
    },
    {
      path: '/admin/courses',
      name: 'ManageCourses',
      component: ManageCourses
    },
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
      path: '/editreview/:reviewId',
      name: 'EditReview',
      component: EditReview
    },
    {
      path: '/userportal',
      name: 'UserPortal',
      component: UserPortal
    },
    {
      path: '/updatecourse',
      name: 'UpdateCourse',
      component: UpdateCourse
    },
    {
      path: '/studentnavbar',
      name: 'StudentNavBar',
      component: StudentNavBar
    },
    {
      path: '/adminnavbar',
      name: 'AdminNavBar',
      component: AdminNavBar
    }
  ]
})
