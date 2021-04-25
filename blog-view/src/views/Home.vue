<template>
  <div class="home-container">


      <el-card :body-style="{padding: '0px'}" class="home-main-column-middle-card" v-for="blog in blogs">
        <div class="home-title">
          <h2>
          <router-link :to="{name: 'Blog', params: {blogId: blog.id}}" class="blog-link">{{ blog.title }}
          </router-link>
          </h2>
          <span style="font-size: small;color: blue">创建时间：{{ blog.createTime.split(' ')[0]}}  </span>
          <span style="font-size: small;color: blue">更新时间：{{ blog.updateTime.split(' ')[0] }}   </span>
          <span style="font-size: small;color: blue">分类：{{ blog.typeName }}</span>
        </div>
        <img  :src= blog.firstPicture  class="image">

        <div class="home-description-markdown-body" v-html="blog.descriptionMd"></div>

      </el-card>
       <div class="home-page" v-if="pageShow">
      <el-pagination background
                     layout="prev, pager, next"
                     :current-page=currentPage
                     :page-size=pageSize
                     @current-change=page
                     :total="total">
      </el-pagination>
      </div>


  </div>
</template>

<script>
import Navbar from "../components/Navbar";


import 'github-markdown-css'
export default {
  name: "Home",
  data() {
    return {
      blogs: [],
      types: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      pageShow: 0
    }
  },
  methods: {

    getTypes() {
      const _this = this
      this.$axios.get('/types').then(res => {
        _this.types = res.data.data
      })
      //console.log(this.types)
    },
    page(currentPage) {
      const _this = this
      this.$axios.get('/blogs?currentPage=' + currentPage).then((res) => {

        _this.blogs = res.data.data.records
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size
        _this.pageShow = 1
        //console.log(_this.blogs)
        var MardownIt = require("markdown-it")
        var md = new MardownIt()


        for(var i in _this.blogs) {
          var result = md.render(_this.blogs[i].description)
          _this.blogs[i].descriptionMd = result
        }
        for(var i in _this.blogs) {
          for(var j in _this.types){
            if(_this.blogs[i].typeId == _this.types[j].id){
              _this.blogs[i].typeName = _this.types[j].typeName
            }
          }


        }
        //console.log(_this.blogList)
        //console.log(_this.blogs)
      })
    }
  },
  created() {
    this.getTypes()
    this.page(1);
    //console.log(this.blogs)
  }
}
</script>


<style scoped>
.home-container {


}
.home-description-markdown-body {
  text-align: left;
}

.image {
  width: 100%;
  height: auto;
}

.home-main-column-middle-card {
  background-color: white;
  margin-bottom: 40px;

  padding: 20px 41px;
  text-align: center;


}
.home-title {
  margin-bottom: 20px;
}

.blog-link:link, .blog-link:visited {
  text-decoration: none;
  color: black;


}
.home-main-column-middle-card:hover {
  -webkit-box-shadow: #ccc 0px 10px 10px;
  -moz-box-shadow: #ccc 0px 10px 10px;
  box-shadow: #ccc 0px 10px 10px;
}
.home-page {
  text-align: center;
}

</style>