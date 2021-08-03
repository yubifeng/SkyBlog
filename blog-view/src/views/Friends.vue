<template>

  <div class="blog-detail">

    <div class="mblog">

      <div class="home-title">
        <h2>{{ blog.title }}</h2>
      </div>


      <el-link v-if="ownBlog" icon="el-icon-edit">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}">
          编辑
        </router-link>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>


    </div>

    <div class="friends-segment">

        <a :href="item.website" target="_blank" rel="external nofollow noopener" class="card" :style="randomRGB()"
           v-for="(item,index) in friendList" :key="index" @click="addViews(item.nickname)">
          <div class="image">
            <img :src="item.avatar" onerror="this.src = '/img/error.png'">
          </div>
          <div class="content">
            <div class="header">{{ item.nickname }}</div>
            <div class="description">{{ item.description }}</div>
          </div>
        </a>
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
import 'github-markdown-css'
import Comment from "@/components/Comment";

export default {
  name: "Friends",
  components: {Comment},
  data() {
    return {
      blog: {},
      ownBlog: false,
      friendList: [],
      bgColor: [
        '#1abc9c', '#2ecc71', '#3498db', '#9b59b6',
        '#34495e', '#f1c40f', '#e67e22', '#e74c3c',
        '#ee5a24', '#9980fa', '#8c7ae6', '#f79f1f'
      ],
      info: {
        content: '',
        commentEnabled: false
      }

    }
  },
  methods:{
    getFriendBlog(){
      const _this = this
      this.$axios.get('/friends').then(res => {
        _this.blog = res.data.data


        var MardownIt = require("markdown-it")
        var md = new MardownIt()

        var result = md.render(_this.blog.content)
        _this.blog.content = result
        if (_this.$store.getters.getUser) {
          _this.ownBlog = (_this.blog.userId === _this.$store.getters.getUser.id)
        }
      })
    },
    getFriendList(){
      const _this = this
      this.$axios.get('/friend/all').then(res => {
        _this.friendList = res.data.data
      })

    },

    randomRGB() {
      const index = Math.floor((Math.random() * this.bgColor.length))
      return {backgroundColor: this.bgColor[index]}
    },
    addViews(nickname){
      this.$axios.get('/friend/onclick?nickname='+nickname).then(res => {

      })
    }

  },
  created() {
    this.getFriendBlog()
    this.getFriendList()


  }
}
</script>

<style scoped>
.mblog {
  min-height: 250px;


  padding: 5px 35px 5px 35px;
  text-align: left;
}

.home-title {
  margin-bottom: 40px;
}

.markdown-body {
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






.friends-segment {
  min-height: 300px;


  padding: 5px 35px 5px 35px;
  text-align: left;


}

.image{
  width: 70px;
  margin: 16px auto 0px;

}
.image img{
  width:100%;
  height:auto;
  border-radius: 100% !important;

}
.card {
  display: inline-block;
  width: 253px;
  margin: 12px 14px;
  text-align: center;
  border-radius: 5px;
  text-decoration-line: none;

}

.card .content .header {
  font-size: 16px !important;

}
.card .content {
  padding: 10px 2px;

}

.card .content .header {
  margin: -3px 0px 0px ;
}
.card .content .description {
  font-size: 12px !important;
  margin: 5px 0px 9px;
}
.card .content * {
  color: #fff !important;
}


</style>