<template>
  <div class="blog-navbar">


    <router-link to="/" style="text-decoration-line: none;color: #333333" >
      <h3 style="display: inline;padding-left: 60px;padding-right: 20px" :class="{'m-mobile-show': mobileHide,'active':$route.name==='About'}" class="ui header item m-blue">Skymo's Blog</h3>
    </router-link>


    <router-link to="/" class="item" :class="{'m-mobile-show': mobileHide,'active':$route.name==='Index'}"
                 style="text-decoration-line: none;color: #333333;margin-left: 30px;padding-left: 0px;padding-right: 20px">
      首页
    </router-link>


    <el-dropdown trigger="click" @mousedown.native="getTypes" >

				<span class="el-dropdown-link item" :class="{'m-mobile-show': mobileHide,'active':$route.name==='Category'}"
              style="text-decoration-line: none;color: #333333; font-size: 16px;padding-left: 20px;padding-right: 20px">
					分类<i class="el-icon-arrow-down el-icon--right"></i>
				</span>

      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="categoryRoute(type.typeName)" v-for="(type,index) in types" :key="index">
          {{ type.typeName }}
        </el-dropdown-item>
      </el-dropdown-menu>

    </el-dropdown>


    <router-link to="/archives" class="item" :class="{'m-mobile-show': mobileHide,'active':$route.name==='Archives'}"
                 style="text-decoration-line: none;color: #333333;padding-left: 20px;padding-right: 20px">
      归档
    </router-link>

    <router-link to="/friends" class="item" :class="{'m-mobile-show': mobileHide,'active':$route.name==='Friends'}"
                 style="text-decoration-line: none;color: #333333;;padding-left: 20px;padding-right: 20px">
      友链
    </router-link>
    <router-link to="/about" class="item" :class="{'m-mobile-show': mobileHide,'active':$route.name==='About'}"
                 style="text-decoration-line: none;color: #333333;padding-left: 20px;padding-right: 20px">
     关于我
    </router-link>


    <el-autocomplete style="margin-left: 500px; " v-model="queryString" :fetch-suggestions="debounceQuery" placeholder="请输入内容"
                     class="right item m-search" suffix-icon="el-icon-search" :class="{'m-mobile-hide': mobileHide}"
                     popper-class="m-search-item" @select="handleSelect">
      <i class="search icon el-input__icon" slot="suffix"></i>
      <template slot-scope="{ item }">
        <div class="title">{{ item.title }}</div>
        <span class="content">{{ item.content }}</span>
      </template>
    </el-autocomplete>


  </div>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      types: {},
      input: '',
      queryString: '',
      queryResult: [],
      timer: null,
      mobileHide: false,


    };
  },

  methods: {
    debounceQuery(queryString, callback) {
      this.timer && clearTimeout(this.timer)
      this.timer = setTimeout(() => this.querySearchAsync(queryString, callback), 1000)
    },
    getIsPhone() {

      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag;
    },


    querySearchAsync(queryString, callback) {
      if (queryString == null
          || queryString.trim() === ''
          || queryString.indexOf('%') !== -1
          || queryString.indexOf('_') !== -1
          || queryString.indexOf('[') !== -1
          || queryString.indexOf('#') !== -1
          || queryString.indexOf('*') !== -1
          || queryString.trim().length > 20) {
        return
      }

      const _this = this
      this.$axios.get('/search?queryString=' + queryString).then((res) => {

        _this.queryResult = res.data.data
        if (_this.queryResult.length === 0) {
          _this.queryResult.push({title: '无相关搜索结果'})
        }


        console.log(_this.queryResult)
        callback(_this.queryResult)


      }).catch(() => {
        _this.msgError("请求失败")
      })


    },
    categoryRoute(name) {
      this.$router.push(`/category/${name}`)
    },


    handleSelect(item) {
      if (item.id) {
        this.$router.push(`/blog/${item.id}`)
      }
    },

    getTypes() {
      const _this = this
      this.$axios.get('/types').then((res) => {

        _this.types = res.data.data;


      })
    },

  },
  mounted() {
    if(this.getIsPhone()){
      this.mobileHide = true
    }

  },



}
</script>

<style scoped>


.el-dropdown-link {
  outline-style: none !important;
  outline-color: unset !important;
  height: 100%;
  cursor: pointer;
}

.el-dropdown-menu {
  margin: 7px 0 0 0 !important;
  padding: 0 !important;
  border: 0 !important;

}


.el-dropdown-menu__item {
  padding: 0px 15px 0px 15px !important;

}

.el-dropdown-menu__item:hover {

}


.el-menu-demo {
  border-bottom-color: #ffffff !important;
}

.blog-navbar {


  height: 70px;
  padding: 0;
  background-color: white;

  margin-bottom: 20px;

  margin-left: auto !important;
  margin-right: auto !important;


}

.m-search {
  min-width: 220px;
  padding: 0 !important;
  margin-top: 10px;
}

.m-search input {
  color: rgba(255, 255, 255, .9);;
  border: 0px !important;
  background-color: inherit;
  padding: .67857143em 2.1em .67857143em 1em;

}

.m-search i {
  color: rgba(255, 255, 255, .9) !important;

}

.m-search-item {


  min-width: 350px !important;

}


.m-search-item li {
  line-height: normal !important;
  padding: 8px 10px !important;
  width: 1000px;


}

.m-search-item li .title {
  text-overflow: ellipsis;
  overflow: hidden;
  color: rgba(0, 0, 0, 0.87);
}

.m-search-item li .content {
  text-overflow: ellipsis;
  font-size: 12px;
  color: rgba(0, 0, 0, .70);
}

.m-mobile-hide{
  display: none !important;

}
.m-mobile-show {
  padding: 0px 1px 0px 1px;
  margin: 20px 1px 0px 1px;

}
</style>

