import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import axios from 'axios'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import "./axios"
// 全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import $ from 'jquery'
Vue.prototype.$axios = axios //
Vue.use(Element)
Vue.config.productionTip = false
// use
Vue.use(mavonEditor)


const cubic = value => Math.pow(value, 3);
const easeInOutCubic = value => value < 0.5 ? cubic(value * 2) / 2 : 1 - cubic((1 - value) * 2) / 2;
//滚动至页面顶部，使用 Element-ui 回到顶部 组件中的算法
Vue.prototype.scrollToTop = function () {
    const el = document.documentElement
    const beginTime = Date.now()
    const beginValue = el.scrollTop
    const rAF = window.requestAnimationFrame || (func => setTimeout(func, 16))
    const frameFunc = () => {
        const progress = (Date.now() - beginTime) / 500;
        if (progress < 1) {
            el.scrollTop = beginValue * (1 - easeInOutCubic(progress))
            rAF(frameFunc)
        } else {
            el.scrollTop = 0
        }
    }
    rAF(frameFunc)
}

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
