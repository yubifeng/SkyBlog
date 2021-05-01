<template>
  <div class="archives-container">

          <el-timeline class="archives-timeline">
            <el-timeline-item id="timeline"  color="deepskyblue" v-bind:timestamp="blog.createTime" placement="top" v-for="blog in blogs" :key="blog.id">
              <div class="blog-box-card">

                <router-link :to="{name: 'Blog', params: {blogId: blog.id}}" class="blog-link">{{blog.title}}</router-link>


              </div>
            </el-timeline-item>
          </el-timeline>

  </div>
</template>
<script>



export default {
  name: "Archives",

  data() {
    return {
      blogs: {}
    }
  },
  methods: {
    page() {
      const _this = this
      this.$axios.get('/blogsIndex').then((res) => {
        console.log(res.data.data.records)
        _this.blogs = res.data.data
      })
    }
  },
  mounted () {
    this.page()

  }
}
</script>


<style scoped>





.blog-box-card {
  background-color: white;
  padding-bottom: 0px;
  height: 50px;
}



.blog-link:link, .blog-link:visited{
  text-decoration: none;
  color: black;

}
.blog-link{
  height: 20px;
  font-weight: bold;
  line-height: 50px;
}

.blog-box-card:hover{
  -webkit-box-shadow: #ccc 0px 10px 10px;
  -moz-box-shadow: #ccc 0px 10px 10px;
  box-shadow: #ccc 0px 10px 10px;
}

</style>


