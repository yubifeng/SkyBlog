<template>
  <div class="ui top segment" style="text-align: center">
    <h2 class="m-text-500">分类 {{ categoryName }} 下的文章</h2>
    <el-card v-for="blog in blogs" :body-style="{padding: '0px'}" :key="blog.id" class="home-main-column-middle-card">
      <div class="home-title">
        <h2>
          <router-link :to="{name: 'Blog', params: {blogId: blog.id}}" class="blog-link">{{ blog.title }}
          </router-link>
        </h2>
        <span style="font-size: small;color: blue">创建时间：{{ blog.createTime.split(' ')[0] }}  </span>
        <span style="font-size: small;color: blue">更新时间：{{ blog.updateTime.split(' ')[0] }}   </span>
        <span style="font-size: small;color: blue">分类：{{ categoryName }}</span>
      </div>
      <img :src=blog.firstPicture class="image">

      <div class="home-description-markdown-body" v-html="blog.descriptionMd"></div>

    </el-card>
    <div v-if="pageShow" class="home-page">
      <el-pagination :current-page=currentPage
                     :page-size=pageSize
                     :total="total"
                     background
                     layout="prev, pager, next"
                     @current-change=getBlogByTypeName>
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "Category",
  data() {
    return {
      blogs: [],


      types: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      pageShow: 0,
      categoryName: ""
    }


  },


  methods: {

    getBlogByTypeName(currentPage) {

      const _this = this


      this.$axios.get('/blogsByType?currentPage=' + currentPage + '&typeName=' + this.categoryName).then((res) => {

        _this.blogs = res.data.data.records
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size
        _this.pageShow = 1
        console.log(_this.blogs)
        var MardownIt = require("markdown-it")
        var md = new MardownIt()


        for (var i in _this.blogs) {
          var result = md.render(_this.blogs[i].description)
          _this.blogs[i].descriptionMd = result
        }

        //console.log(_this.blogList)
        //console.log(_this.blogs)
      })
    }

  },

  watch: {
    '$route'(to, from) {

      this.categoryName = this.$route.params.name
      this.getBlogByTypeName(1)


    }
  },
  created() {
    this.categoryName = this.$route.params.name

    this.getBlogByTypeName(1)

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