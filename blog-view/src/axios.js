import axios from 'axios'
import Element from "element-ui";
import store from "./store";
import router from "./router";

axios.defaults.baseURL = "https://api.skymo.top/"
//请求拦截
axios.interceptors.request.use(config => {
    //console.log("前置拦截")


    if (!config.url.includes("usuuu.com")) {
        //如果有token，统一带上
        const token = window.localStorage.getItem('token')
        if (token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
            config.headers.Authorization = `${token}`;
        }

        // identification存在，可以统一设置请求头
        const identification = window.localStorage.getItem('identification')

        if (identification && !(config.url.startsWith('http://') || config.url.startsWith('https://'))) {
            config.headers.identification = identification
        }

    }





    return config
})
//响应拦截
axios.interceptors.response.use(response => {
        const res = response.data;
        const identification = response.headers.identification
        if (identification) {
            //保存身份标识到localStorage
            window.localStorage.setItem('identification', identification)
        }
        //console.log("后置拦截")
        // 当结果的code是否为200的情况
        if (res.code === 200) {
            return response
        } else {
            // 弹窗异常信息
            Element.Message({
                message: response.data.msg,
                type: 'error',
                duration: 2 * 1000
            })
            // 直接拒绝往下面返回结果信息
            return Promise.reject(response.data.msg)
        }
    },
    error => {
        console.log('err' + error)// for debug
        if (error.response.data) {
            error.message = error.response.data.msg
        }
        // 根据请求状态觉得是否登录或者提示其他
        if (error.response.status === 401||error.response.status === 500) {
            store.commit('REMOVE_INFO');
            router.push({
                path: '/login'
            });
            error.message = '请重新登录';
        }
        if (error.response.status === 403) {
            error.message = '权限不足，无法访问';
        }
        Element.Message({
            message: error.message,
            type: 'error',
            duration: 3 * 1000
        })
        return Promise.reject(error)
    })
