<template>

  <div class="blog-detail">

    <div class="mblog">

      <div class="home-title">
        <h2>{{ blog.title }}</h2>
        <span style="font-size: small;color: blue">创建时间：{{ blog.createTime.split(' ')[0] }}  </span>
        <span style="font-size: small;color: blue">更新时间：{{ blog.updateTime.split(' ')[0] }}  </span>
        <span style="font-size: small;color: blue">分类：{{ blog.typeName }}</span>
        <span style="font-size: small;color: blue"> 字数：{{ blog.words }}</span>

      </div>

      <el-link v-if="ownBlog" icon="el-icon-edit">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}">
          编辑
        </router-link>
      </el-link>

<!--      <div class="markdown-body" v-html="blog.content" v-viewer="{movable: false}"></div>-->
      <markdown-it-vue-light class="md-body" :content="blog.content" v-viewer="{movable: false}" />

    </div>

    <!--版权信息-->
    <div class="author-message">

      <ul class="list">
        <li>作者：fanli
          <router-link style="text-decoration-line: none;" to="/about">（联系作者）</router-link>
        </li>
        <li>发表时间：{{ blog.createTime }}</li>
        <li>最后修改：{{ blog.updateTime }}</li>
        <li>本站点采用<a href="https://creativecommons.org/licenses/by/4.0/" style="text-decoration-line: none;"
                    target="_blank"> 署名 4.0 国际 (CC BY 4.0) </a>创作共享协议。可自由转载、引用，并且允许商业性使用。但需署名作者且注明文章出处。
        </li>
      </ul>
    </div>
    <div>
      <Comment></Comment>

    </div>

  </div>
</template>

<script>

import Comment from "@/components/Comment";

export default {
  name: "Blog",
  components: {Comment},
  data() {
    return {
      types: [],
      blog: {
        id: "",
        title: "",
        content: "",
        createTime: "",
        updateTime: "",
        typeName: "",
        words: 0,
        views: 0,

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
      //console.log(this.types)

    },
    getBlog() {
      const blogId = this.$route.params.blogId

      const _this = this
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.blog.id = blog.id
        _this.blog.title = blog.title
        _this.blog.createTime = blog.createTime
        _this.blog.updateTime = blog.updateTime
        _this.blog.views = blog.views
        _this.blog.words = blog.words

        //maekdown-it 渲染器 开启代码高亮 需要使用Highlight.js 的 CSS



        _this.blog.content = blog.content
        if (_this.$store.getters.getUser) {
          _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)
        } else {
          _this.ownBlog = false
        }


        for (var i in _this.types) {
          if (blog.typeId == _this.types[i].id) {
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
  min-height: 600px;


  padding: 5px 35px 5px 35px;
  text-align: center;
}

.home-title {
  margin-bottom: 40px;
}

.md-body {
  text-align: left;
}

.blog-detail {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background-color: white;
}

.author-message {
  text-align: left;
  background-color: honeydew;
  padding: 10px 5px 10px 5px;
  font-size: 14px;

}

.el-divider {
  margin: 1rem 0 !important;
}
.code {
  background-color: #333333;
}
</style>