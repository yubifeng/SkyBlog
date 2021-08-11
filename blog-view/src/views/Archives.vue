<template>
  <div class="archives-container">

    <el-timeline class="archives-timeline">
      <el-timeline-item v-for="blog in blogs" id="timeline" :key="blog.id" color="deepskyblue"
                        placement="top" v-bind:timestamp="blog.createTime">
        <div class="blog-box-card">

          <router-link :to="{name: 'Blog', params: {blogId: blog.id}}" class="blog-link">{{ blog.title }}</router-link>


        </div>
      </el-timeline-item>

      <div  class="home-page" v-if="pageShow===1">
        <el-pagination :current-page=currentPage
                       :page-size=pageSize
                       :total="total"
                       background
                       layout="total,prev, pager, next"
                       @current-change=getData>
        </el-pagination>
      </div>
    </el-timeline>

  </div>
</template>
<script>


export default {
  name: "Archives",

  data() {
    return {
      blogs: {},
      currentPage: 1,
      total: 1,
      pageSize: 10,
      pageShow:  0
    }
  },
  methods: {
    getData(currentPage) {
      this.currentPage = currentPage
      const _this = this
      this.$axios.get('/blog/archives?currentPage='+this.currentPage).then((res) => {

        _this.blogs = res.data.data.records
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size
        _this.pageShow = 1
      });
      //改变页号后返回顶部
      this.scrollToTop()
    }
  },
  created() {
    this.getData(1)

  }
}
</script>


<style scoped>


.blog-box-card {
  background-color: white;
  padding-bottom: 0px;
  height: 50px;
}


.blog-link:link, .blog-link:visited {
  text-decoration: none;
  color: black;

}

.blog-link {
  height: 20px;
  font-weight: bold;
  line-height: 50px;
}

.blog-box-card:hover {
  -webkit-box-shadow: #ccc 0px 10px 10px;
  -moz-box-shadow: #ccc 0px 10px 10px;
  box-shadow: #ccc 0px 10px 10px;
}
.home-page {
  text-align: center;
}

</style>


