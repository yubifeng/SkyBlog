<template>



    <div class="mblog">
      <div class="home-title">
        <h2>{{ blog.title }}</h2>
        <span style="font-size: small;color: blue">创建时间：{{ blog.createTime.split(' ')[0]}}  </span>
        <span style="font-size: small;color: blue">更新时间：{{ blog.updateTime.split(' ')[0] }}  </span>
        <span style="font-size: small;color: blue">分类：{{ blog.typeName }}</span>
      </div>

      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >
          编辑
        </router-link>
      </el-link>

      <div class="markdown-body" v-html="blog.content"></div>

    </div>


</template>

<script>
import 'github-markdown-css'


export default {
  name: "Blog",

  data() {
    return {
      types: [],
      blog: {
        id: "",
        title: "",
        content: "",
        createTime: "",
        updateTime: "",
        typeName: ""

      },
      ownBlog: false
    }
  },
  methods: {
    getTypes() {
      const _this = this
      this.$axios.get('/types').then(res => {
        _this.types = res.data.data
      })
      console.log(this.types)
    },
    getBlog() {
      const blogId = this.$route.params.blogId
      console.log(blogId)
      const _this = this
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.blog.id = blog.id
        _this.blog.title = blog.title
        _this.blog.createTime = blog.createTime
        _this.blog.updateTime = blog.updateTime


        var MardownIt = require("markdown-it")
        var md = new MardownIt()

        var result = md.render(blog.content)
        _this.blog.content = result
        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)

          for(var i in _this.types){
            if(blog.typeId == _this.types[i].id){
              _this.blog.typeName = _this.types[i].typeName
            }
          }

      })
    }
  },
  created() {
    this.getTypes()
    this.getBlog()

  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background-color: white;
  min-height: 700px;
  padding: 5px 35px 5px 35px;
  text-align: center;
}

.home-title {
  margin-bottom: 40px;
}
.markdown-body {
  text-align: left;
}

</style>