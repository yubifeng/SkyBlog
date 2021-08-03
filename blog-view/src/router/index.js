import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../admin/Login";
import About from "../views/About";
import Archives from "../views/Archives";
import Admin from "../admin/Admin";
import Home from "../views/Home";
import Blog from "../views/Blog";
import BlogEdit from '../admin/BlogEdit.vue';
import BlogList from "../admin/BlogList";
import Friends from "../views/Friends";
import Category from "../views/Category";
import CommentList from "@/admin/CommentList";
import FriendList from "@/admin/FriendList";
import VisitLog from "@/admin/VisitLog";
import VisitorList from "@/admin/VisitorList";
import CategoryList from "@/admin/CategoryList";
import UserManager from "@/admin/UserManager";


Vue.use(VueRouter)
const routes = [


    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {
            title: '登录'
        }
    },
    {
        path: '/admin',
        name: 'Admin',
        component: Admin,
        meta: {
            title: '后台管理',
            requireAuth: true
        },
        children: [
            {
                path: '/writeBlog', // 注意放在 path: '/blog/:blogId'之前
                name: 'BlogWrite',
                component: BlogEdit,
                meta: {
                    requireAuth: true,
                    title: '写博客'
                }
            },
            {
                path: '/blogList', // 注意放在 path: '/blog/:blogId'之前
                name: 'blogList',
                component: BlogList,
                meta: {
                    requireAuth: true,
                    title: '文章管理'
                }
            },
            {
                path: '/blog/edit/:blogId',
                name: 'BlogEdit',
                meta: {
                    requireAuth: true,
                    title: '编辑博客'
                },
                component: BlogEdit
            },
            {
                path: '/type',
                component: CategoryList,
                meta: {
                    requireAuth: true,
                    title: '分类管理'
                }
            },
            {
                path: '/comments',
                component: CommentList,
                meta: {
                    requireAuth: true,
                    title: '评论管理'
                }
            },
            {
                path: '/friendList',
                component: FriendList,
                meta: {
                    requireAuth: true,
                    title: '友链管理'
                }
            },
            {
                path: '/userManager',
                component: UserManager,
                meta: {
                    requireAuth: true,
                    title: '用户管理'
                }
            },
            {
                path: '/visitLog',
                component: VisitLog,
                meta: {
                    requireAuth: true,
                    title: '访问日志'
                }
            },
            {
                path: '/visitor',
                component: VisitorList,
                meta: {
                    requireAuth: true,
                    title: '访客统计'
                }
            },

        ]

    },

    {
        path: '/',
        name: 'Index',

        // 懒加载
        component: () => import('../views/Index.vue'),
        redirect: 'Home',
        children: [
            {
                path: '',
                name: 'Home',
                component: Home,
                meta: {
                    title: '首页'
                }
            },
            {
                path: '/blog/:blogId',
                name: 'Blog',
                component: Blog,
                meta: {
                    title: '博客'
                }
            },
            {
                path: '/archives',
                name: 'Archives',
                component: Archives,
                meta: {
                    title: '归档'
                }
            },
            {
                path: '/about',
                name: 'About',
                component: About,
                meta: {
                    title: '关于我'
                }
            },
            {
                path: '/friends',
                name: 'Friends',
                component: Friends,
                meat: {
                    title: '友链'
                }
            },
            {
                path: '/category/:name',
                name: 'Category',
                component: Category,
                meta: {
                    title: '分类'
                }
            },


        ]

    },


];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
})

export default router

