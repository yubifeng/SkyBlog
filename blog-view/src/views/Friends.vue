<template>



  <div class="mblog">
    <h2> {{ blog.title }}</h2>
    <el-link icon="el-icon-edit" v-if="ownBlog">
      <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >
        编辑
      </router-link>
    </el-link>
    <el-divider></el-divider>
    <div class="markdown-body" v-html="blog.content"></div>

  </div>


</template>

<script>
import 'github-markdown-css'


export default {
  name: "Friends",
  data() {
    return {
      blog: {},
      ownBlog: false
    }
  },
  created() {

    const _this = this
    this.$axios.get('/friends' ).then(res => {
      _this.blog = res.data.data


      var MardownIt = require("markdown-it")
      var md = new MardownIt()

      var result = md.render(_this.blog.content)
      _this.blog.content = result
      if(_this.$store.getters.getUser.id){
        _this.ownBlog = (_this.blog.userId === _this.$store.getters.getUser.id)
      }


    })
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background-color: white;
  min-height: 700px;
  padding: 20px 15px;
}

</style>